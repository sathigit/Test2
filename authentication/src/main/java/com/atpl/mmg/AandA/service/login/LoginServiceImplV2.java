package com.atpl.mmg.AandA.service.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.atpl.mmg.AandA.common.ApiUtility;
import com.atpl.mmg.AandA.common.GenericHttpClient;
import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.common.MmgEnum;
import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.login.LoginDAOV2;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.dao.profilerole.ProfileRoleDAO;
import com.atpl.mmg.AandA.dao.session.SessionDAO;
import com.atpl.mmg.AandA.domain.login.LoginDomain;
import com.atpl.mmg.AandA.domain.login.OauthResponse;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.domain.profilerole.ProfileRoleDomain;
import com.atpl.mmg.AandA.domain.session.SessionDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.INACTIVE_ACCOUNT;
import com.atpl.mmg.AandA.exception.MmgRestException.INVALID_TOKEN;
import com.atpl.mmg.AandA.exception.MmgRestException.MULTIPLE_ROLES;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.USER_NOT_EXISTS;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.mapper.login.LoginMapper;
import com.atpl.mmg.AandA.model.login.LoginModel;
import com.atpl.mmg.AandA.service.profile.ProfileUtil;
import com.atpl.mmg.AandA.utils.DataValidation;
import com.atpl.mmg.AandA.utils.IDGeneration;

@Service("LoginServiceV2")
public class LoginServiceImplV2 implements LoginServiceV2 {

	@Autowired
	DataValidation dataValidation;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Autowired
	ProfileRoleDAO profileRoleDAO;

	@Autowired
	LoginDAOV2 loginDAOV2;

	@Autowired
	IDGeneration idGeneration;

	@Autowired
	SessionDAO sessionDAO;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	LoginMapper loginMapper;

	@Autowired
	ProfileUtil profileUtil;

	@Override
	public LoginModel authenticate(LoginModel loginModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LOGIN_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Login Request: " + JsonUtil.toJsonString(loginModel)));
		Role role = Role.getRole(loginModel.getRoleId() + "");
		ProfileDomainV2 profileDomain = validateProfile(loginModel);

		if (null == role) {
			List<ProfileRoleDomain> profileRoleDomainList = profileRoleDAO.getRoleIdDetails(profileDomain.getId(),
					true);
			if (profileRoleDomainList.size() > 1) {
				throw new MULTIPLE_ROLES(loginModel.getUserName());
			} else {
				loginModel.setRoleId(profileDomain.getDefaultRoleId());
				role = Role.getRole(loginModel.getRoleId() + "");
			}
		}

		checkRoleIsActiveOrInactive(loginModel);

		LoginDomain loginDomain = new LoginDomain();
		BeanUtils.copyProperties(loginModel, loginDomain);

		return authenticateProfile(loginDomain);
	}

	private ProfileDomainV2 validateProfile(LoginModel loginModel) throws Exception {
		if (null == loginModel.getUserName())
			throw new NOT_FOUND("Please enter user name");

		if (null == loginModel.getPassword())
			throw new NOT_FOUND("Please enter password");

		if (!dataValidation.isValidate(loginModel.getUserName(), DataValidation.EMAIL_PATTERN)) {
			if (!dataValidation.isValidate(loginModel.getUserName(), DataValidation.PHONENUMBER_PATTERN))
				throw new NOT_FOUND("User name is not valid! Please enter mobile number/ email id");
		}

		ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetByMobileOrEmail(loginModel.getUserName(), 0);

		if (null == profileDomain)
			throw new USER_NOT_EXISTS(loginModel.getUserName());

		return profileDomain;
	}

	private ProfileDomainV2 checkRoleIsActiveOrInactive(LoginModel loginModel) throws Exception {
		ProfileDomainV2 profileDomain = profileDAOV2.getProfileByMobileNumberOrEmailId(loginModel.getUserName(),
				loginModel.getUserName(), loginModel.getRoleId());

		if (null != profileDomain) {
			if (!profileDomain.getIsActive())
				throw new INACTIVE_ACCOUNT();
		}

		return profileDomain;
	}

	private LoginModel authenticateProfile(LoginDomain loginDomain) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.LOGIN_SERVICE.name(), SeverityTypes.INFORMATIONAL.ordinal(),
						"authenticateProfile Request: " + JsonUtil.toJsonString(loginDomain)));
		LoginModel loginModel = new LoginModel();

		LoginDomain oauthDomain = new LoginDomain();
		BeanUtils.copyProperties(loginDomain, oauthDomain);

		loginDomain = loginDAOV2.authenticate(loginDomain);
		BeanUtils.copyProperties(loginDomain, loginModel);
		saveSession(loginModel, oauthDomain);

		OauthResponse oauthResponse = getOauthAuthentication(oauthDomain, Constants.OAUTH_PASSWORD_GRANT);
		loginModel.setAccessToken(oauthResponse.getAccess_token());
		loginModel.setRefreshToken(oauthResponse.getRefresh_token());
		loginModel.setTokenExpires(oauthResponse.getExpires_in());
		return loginModel;
	}

	private LoginModel saveSession(LoginModel loginModel, LoginDomain oauthDomain) throws Exception {
		SessionDomain sessionDomain = new SessionDomain();
		sessionDomain = sessionDAO.getSessionByProfileId(loginModel.getId(), oauthDomain.getRoleId());
		if (null != sessionDomain) {
			if (loginModel.getRoleId() == Integer.parseInt(Role.DRIVER.getCode())) {
				sessionDomain.setIsActive(false);
				sessionDAO.updateSession(sessionDomain);
				deleteToken("Bearer " + sessionDomain.getAccessToken(), true);

				sessionDomain = saveNewSession(loginModel, oauthDomain);
			} else {
				OauthResponse oauthResponse = getOauthAuthentication(oauthDomain, "password");
				loginModel.setAccessToken(oauthResponse.getAccess_token());
				loginModel.setRefreshToken(oauthResponse.getRefresh_token());
				loginModel.setTokenExpires(oauthResponse.getExpires_in());
			}
		} else
			sessionDomain = saveNewSession(loginModel, oauthDomain);

		loginModel.setSessionId(sessionDomain.getSessionId());
		loginModel.setActive(true);

		return loginModel;
	}

	private SessionDomain saveNewSession(LoginModel loginModel, LoginDomain oauthDomain) throws Exception {
		OauthResponse oauthResponse = getOauthAuthentication(oauthDomain, "password");
		loginModel.setAccessToken(oauthResponse.getAccess_token());
		loginModel.setRefreshToken(oauthResponse.getRefresh_token());
		loginModel.setTokenExpires(oauthResponse.getExpires_in());

		SessionDomain sessionDomain = new SessionDomain();
		sessionDomain.setEmailId(loginModel.getUserName());
		sessionDomain.setUserId(loginModel.getId());
		sessionDomain.setIsActive(true);
		sessionDomain.setRoleId(loginModel.getRoleId());
		sessionDomain.setSessionId(idGeneration.RandomNumberUserSession());
		sessionDomain.setAccessToken(loginModel.getAccessToken());
		sessionDomain = sessionDAO.saveSession(sessionDomain);

		return sessionDomain;
	}

	private OauthResponse getOauthAuthentication(LoginDomain loginDomain, String grantType) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.LOGIN_SERVICE.name(), SeverityTypes.INFORMATIONAL.ordinal(),
						"getOauthAuthentication Request: " + JsonUtil.toJsonString(loginDomain)));
		OauthResponse oauthResponse = null;
		String uri = mmgProperties.getOauthUrl() + "oauth/token";
		Map<String, String> params = new HashMap<String, String>();

		if (grantType.equalsIgnoreCase(Constants.OAUTH_PASSWORD_GRANT)) {
			params.put("username", loginDomain.getUserName());
			params.put("password", loginDomain.getPassword());
			params.put("grant_type", grantType);
		} else if (grantType.equalsIgnoreCase(Constants.OAUTH_REFRESH_TOKEN_GRANT)) {
			params.put("grant_type", grantType);
			params.put("refresh_token", loginDomain.getRefreshToken());
		}

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}

		Map<String, Object> httpResponse = GenericHttpClient.doHttpPost(builder.toUriString(),
				ApiUtility.getOauthHeaders(mmgProperties.getOauthClientId(), mmgProperties.getOauthClientSecret()),
				null, MmgEnum.OAUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				oauthResponse = JsonUtil.fromJson(jsonResponse.toString(), OauthResponse.class);
				if (grantType.equalsIgnoreCase(Constants.OAUTH_REFRESH_TOKEN_GRANT))
					throw new INVALID_TOKEN(oauthResponse.getError_description()); // INVALID_TOKEN
				else
					throw new NOT_FOUND(oauthResponse.getError_description());
			} else {
				oauthResponse = JsonUtil.fromJson(jsonResponse.toString(), OauthResponse.class);
				return oauthResponse;
			}
		} else
			throw new BACKEND_SERVER_ERROR();
	}

	@Override
	public LoginModel getTotalRole(String profileId) throws Exception {
		profileUtil.validateProfileId(profileId);
		LoginDomain loginDomain = loginDAOV2.getTotalRole(profileId);
		LoginModel loginModel = new LoginModel();
		if (loginDomain == null)
			return null;
		BeanUtils.copyProperties(loginDomain, loginModel);
		return loginModel;
	}

	@Override
	public List<LoginModel> getRoleNames(String profileId) throws Exception {
		profileUtil.validateProfileId(profileId);
		List<LoginDomain> loginDomain = loginDAOV2.getRoleNames(profileId);
		return loginMapper.entityList(loginDomain);
	}

	@Override
	public LoginModel getAccessToken(LoginModel loginModel) throws Exception {
		if (null == loginModel.getRefreshToken())
			throw new NOT_FOUND("Enter refresh token");
		else {
			LoginDomain loginDomain = new LoginDomain();
			BeanUtils.copyProperties(loginModel, loginDomain);

			OauthResponse oauthResponse = getOauthAuthentication(loginDomain, Constants.OAUTH_REFRESH_TOKEN_GRANT);
			loginModel.setAccessToken(oauthResponse.getAccess_token());
			loginModel.setRefreshToken(oauthResponse.getRefresh_token());
			loginModel.setTokenExpires(oauthResponse.getExpires_in());
		}
		return loginModel;
	}

	@Override
	public String deleteToken(String accessToken, boolean isDriverActive) throws Exception {
		Map<String, Object> httpResponse = GenericHttpClient.doHttpDelete(
				mmgProperties.getOauthUrl() + "v1/user/logout", ApiUtility.getHeaders("", accessToken),
				MmgEnum.OAUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				if (!isDriverActive)
					throw new NOT_FOUND(jsonResponse.getString("message"));
				else
					return "error";
			} else
				return jsonResponse.getString("data");
		} else
			throw new BACKEND_SERVER_ERROR();
	}

}
