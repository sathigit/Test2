package com.atpl.mmg.AandA.service.login;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.forgotPassword.ForgotPasswordDAO;
import com.atpl.mmg.AandA.dao.login.LoginDAO;
import com.atpl.mmg.AandA.dao.profile.ProfileDAO;
import com.atpl.mmg.AandA.dao.session.SessionDAO;
import com.atpl.mmg.AandA.domain.forgotPassword.ForgotPasswordDomain;
import com.atpl.mmg.AandA.domain.login.LoginDomain;
import com.atpl.mmg.AandA.domain.login.OauthResponse;
import com.atpl.mmg.AandA.domain.session.SessionDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.INACTIVE_ACCOUNT;
import com.atpl.mmg.AandA.exception.MmgRestException.INVALID_TOKEN;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.USER_NOT_EXISTS;
import com.atpl.mmg.AandA.filter.JwtTokenUtil;
import com.atpl.mmg.AandA.mapper.login.LoginMapper;
import com.atpl.mmg.AandA.model.login.LoginModel;
import com.atpl.mmg.AandA.model.session.SessionModel;
import com.atpl.mmg.AandA.utils.DataValidation;
import com.atpl.mmg.AandA.utils.IDGeneration;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {
	@Autowired
	LoginDAO loginDAO;

	@Autowired
	ProfileDAO profileDAO;

	@Autowired
	LoginMapper loginMapper;

	@Autowired
	DataValidation dataValidation;

	@Autowired
	SessionDAO sessionDAO;

	@Autowired
	ForgotPasswordDAO forgotPasswordDAO;

	@Autowired
	IDGeneration idGeneration;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	MMGProperties mmgProperties;

	public LoginServiceImpl() {
		// TODO
	}

	static int otp, otp1;

	/**
	 * 1. Pass userName(mobileNumber and emailId) and password to Customer table If
	 * data exsist then its simply retrun true 2. If userName(mobileNumber and
	 * emailId) and password are not present in customer then make a call to profile
	 * table
	 * 
	 */

	@Override
	public LoginModel authenticate(LoginModel loginModel) throws Exception {
		LoginDomain loginDomain = new LoginDomain();

		BeanUtils.copyProperties(loginModel, loginDomain);
		Role role = Role.getRole(loginModel.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role) {
			getRoleId(loginModel);
			role = Role.getRole(loginModel.getRoleId() + "");
		}
		switch (role) {
		case CUSTOMER:
			validateCustomer(loginModel);
			return authenticateCustomer(loginDomain);
		case DRIVER:
		case FRANCHISE:
		case FLEET_OPERATOR:
		case WAREHOUSE:
		case EMPLOYEE:
		case OWNER:
		case TIEUPS:
		case OPERATIONAL_TEAM:
		case ENTERPRISE:
		case BDM:
		case BDO:
		case FLEETDRIVER:
		case COORDINATOR:
		case FIELDOFFICER:
		case HELPCENTER:
		case HELPCENTERLEVEL2:
		case HELPCENTERLEVEL3:
		case CHANNEL_PARTNER:
			return authenticateProfile(loginDomain);

		}
		return loginModel;
	}

	// To get Access token based on refresh token
	@Override
	public LoginModel getAccessToken(LoginModel loginModel) throws Exception {
		if (null == loginModel.getRefreshToken())
			throw new NOT_FOUND("Enter refresh token");
		else {
			LoginDomain loginDomain = new LoginDomain();
			BeanUtils.copyProperties(loginModel, loginDomain);

			OauthResponse oauthResponse = getOauthAuthentication(loginDomain, "refresh_token");
			loginModel.setAccessToken(oauthResponse.getAccess_token());
			loginModel.setRefreshToken(oauthResponse.getRefresh_token());
			loginModel.setTokenExpires(oauthResponse.getExpires_in());
		}
		return loginModel;
	}

	private LoginModel getRoleId(LoginModel loginModel) throws Exception {
		String mobileNumber = null;
		if (!dataValidation.isValidate(loginModel.getUserName(), DataValidation.EMAIL_PATTERN))
			mobileNumber = loginModel.getUserName();
		ForgotPasswordDomain forgotProfilePasswordDomain = forgotPasswordDAO
				.getProfileByMobileNumberOrEmailId(mobileNumber, loginModel.getUserName(), 0);
		if (null == forgotProfilePasswordDomain) {
			throw new USER_NOT_EXISTS(loginModel.getUserName());
		}
		if (!forgotProfilePasswordDomain.isStatus())
			throw new INACTIVE_ACCOUNT();
		loginModel.setRoleId(forgotProfilePasswordDomain.getRoleId());
		return loginModel;

	}

	private LoginModel authenticateCustomer(LoginDomain loginDomain) throws Exception {
		LoginModel loginModel = new LoginModel();
		LoginDomain oauthDomain = new LoginDomain();
		BeanUtils.copyProperties(loginDomain, oauthDomain);

		loginDomain = loginDAO.customerAuthenticate(loginDomain);
		BeanUtils.copyProperties(loginDomain, loginModel);
		
		SaveSession(loginModel,oauthDomain);
		
		return loginModel;
	}

	private LoginModel authenticateProfile(LoginDomain loginDomain) throws Exception {
		LoginModel loginModel = new LoginModel();

		LoginDomain oauthDomain = new LoginDomain();
		BeanUtils.copyProperties(loginDomain, oauthDomain);

		loginDomain = loginDAO.authenticate(loginDomain);
		BeanUtils.copyProperties(loginDomain, loginModel);
		
		SaveSession(loginModel,oauthDomain);
		
		return loginModel;
	}

	private OauthResponse getOauthAuthentication(LoginDomain loginDomain, String grantType) throws Exception {
		OauthResponse oauthResponse = null;
		String uri = mmgProperties.getOauthUrl() + "oauth/token";
		Map<String, String> params = new HashMap<String, String>();
		if(grantType.equalsIgnoreCase("password")) {
			params.put("username", loginDomain.getUserName());
			params.put("password", loginDomain.getPassword());
			params.put("grant_type", grantType);
		} else if (grantType.equalsIgnoreCase("refresh_token")) {
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
				if (grantType.equalsIgnoreCase("refresh_token"))
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

	private LoginModel SaveSession(LoginModel loginModel, LoginDomain oauthDomain) throws Exception {
		String timeExpires = timeExpireDate();
		loginModel.setTokenExpires(timeExpires);
		SessionDomain sessionDomain = new SessionDomain();
		sessionDomain = sessionDAO.getSession(loginModel.getId());
		
		if(null != sessionDomain)
		{
			if(loginModel.getRoleId() == Integer.parseInt(Role.DRIVER.getCode())){
				sessionDomain.setIsActive(false);
				sessionDAO.updateSession(sessionDomain);
				deleteToken("Bearer "+sessionDomain.getAccessToken(),true);
				
				sessionDomain = saveNewSession(loginModel,oauthDomain);
			} else {
				OauthResponse oauthResponse = getOauthAuthentication(oauthDomain, "password");
				loginModel.setAccessToken(oauthResponse.getAccess_token());
				loginModel.setRefreshToken(oauthResponse.getRefresh_token());
				loginModel.setTokenExpires(oauthResponse.getExpires_in());
			}
		} else 
			sessionDomain = saveNewSession(loginModel,oauthDomain);
		
		loginModel.setSessionId(sessionDomain.getSessionId());
		loginModel.setActive(true);
		BeanUtils.copyProperties(loginModel, loginModel);
		return loginModel;

	}
	
	private SessionDomain saveNewSession(LoginModel loginModel,LoginDomain oauthDomain) throws Exception {
		OauthResponse oauthResponse = getOauthAuthentication(oauthDomain, "password");
		loginModel.setAccessToken(oauthResponse.getAccess_token());
		loginModel.setRefreshToken(oauthResponse.getRefresh_token());
		loginModel.setTokenExpires(oauthResponse.getExpires_in());
		
		SessionDomain sessionDomain = new SessionDomain();
		sessionDomain.setEmailId(loginModel.getUserName());
		sessionDomain.setUserId(loginModel.getId());
		sessionDomain.setIsActive(true);
		sessionDomain.setSessionId(idGeneration.RandomNumberUserSession());
		sessionDomain.setAccessToken(loginModel.getAccessToken());
		sessionDomain = sessionDAO.saveSession(sessionDomain);
		
		return sessionDomain;
	}

	private String timeExpireDate() throws Exception {
		String afteroneHour = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = formatter.parse(simpleDateFormat.format(new Date()).toString());
		TimeZone tzInAmerica = TimeZone.getTimeZone("Asia/Calcutta");
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(date);
		calendar1.setTimeZone(tzInAmerica);
		calendar1.add(Calendar.HOUR, 1);
		Date date6 = formatter.parse(formatter.format(calendar1.getTime()).toString());
		SimpleDateFormat formatter_12_hours = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
		afteroneHour = formatter_12_hours.format(date6);
		return afteroneHour;
	}

	private void validateCustomer(LoginModel loginModel) throws Exception {
		String mobileNumber = null;
		if (!dataValidation.isValidate(loginModel.getUserName(), DataValidation.EMAIL_PATTERN))
			mobileNumber =loginModel.getUserName();
		ForgotPasswordDomain forgotCustomerPasswordDomain = forgotPasswordDAO
				.getCustomerByMobileNumberOrEmailId(mobileNumber, loginModel.getUserName());
		if (null == forgotCustomerPasswordDomain)
			throw new USER_NOT_EXISTS(loginModel.getUserName());
	}

	@Override
	public List<LoginModel> getCustomerPermission(int roleId) throws Exception {
		List<LoginDomain> loginDomain = loginDAO.getPermission(roleId);
		return loginMapper.entityList(loginDomain);
	}

	@Override
	public List<LoginModel> getDriverPermission(int roleId) throws Exception {
		List<LoginDomain> loginDomain = loginDAO.getPermission(roleId);
		return loginMapper.entityList(loginDomain);
	}

	@Override
	public List<LoginModel> getFleetOperatorPermission(int roleId) throws Exception {
		List<LoginDomain> loginDomain = loginDAO.getPermission(roleId);
		return loginMapper.entityList(loginDomain);
	}

	@Override
	public List<LoginModel> getOwnerPermission(int roleId) throws Exception {
		List<LoginDomain> loginDomain = loginDAO.getPermission(roleId);
		return loginMapper.entityList(loginDomain);

	}

	@Override
	public String logout(LoginModel loginModel) throws Exception {
		SessionDomain sessionDomain = new SessionDomain();
		SessionModel sessionModel = new SessionModel();
		BeanUtils.copyProperties(sessionModel, sessionDomain);
		return sessionDAO.updateSession(sessionDomain);

	}

	@Override
	public String savePermission(LoginModel loginModel) throws Exception {
		LoginDomain loginDomain = new LoginDomain();
		BeanUtils.copyProperties(loginModel, loginDomain);
		boolean status = true;
		loginDomain.setStatus(status);
		return loginDAO.savePermission(loginDomain);
	}

	@Override
	public LoginModel roleCount(int id) throws Exception {
		LoginDomain loginDomain = loginDAO.roleCount(id);
		LoginModel loginModel = new LoginModel();
		if (loginDomain == null)
			return null;
		BeanUtils.copyProperties(loginDomain, loginModel);
		return loginModel;
	}

	@Override
	public List<LoginModel> getRoleName(int id) throws Exception {
		List<LoginDomain> loginDomain = loginDAO.getRoleName(id);
		return loginMapper.entityList(loginDomain);
	}

	@Override
	public LoginModel rolePath(int id) throws Exception {
		LoginDomain loginDomain = loginDAO.rolePath(id);
		LoginModel loginModel = new LoginModel();
		if (loginDomain == null)
			return null;
		BeanUtils.copyProperties(loginDomain, loginModel);
		return loginModel;
	}

	@Override
	public String deleteProle(int proleId) throws Exception {
		LoginDomain loginDomain = new LoginDomain();
		LoginModel loginModel = new LoginModel();
		BeanUtils.copyProperties(loginModel, loginDomain);
		return loginDAO.deleteProle(proleId);
	}

	@Override
	public LoginModel getProle(int id, int roleId) throws Exception {
		LoginDomain loginDomain = loginDAO.getProle(id, roleId);
		LoginModel loginModel = new LoginModel();
		if (loginDomain == null)
			return null;
		BeanUtils.copyProperties(loginDomain, loginModel);
		return loginModel;
	}

	@Override
	public String deleteToken(String accessToken,boolean isDriverActive) throws Exception {
		Map<String, Object> httpResponse = GenericHttpClient.doHttpDelete(
				mmgProperties.getOauthUrl() + "v1/user/logout",
				ApiUtility.getHeaders("", accessToken), MmgEnum.OAUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				if(!isDriverActive)
					throw new NOT_FOUND(jsonResponse.getString("message"));
				else
					return "error";
			}
			else
				return jsonResponse.getString("data");
		} else
			throw new BACKEND_SERVER_ERROR();
	}

}
