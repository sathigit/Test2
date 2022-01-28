package com.atpl.mmg.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.atpl.mmg.common.ApiUtility;
import com.atpl.mmg.common.GenericHttpClient;
import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.common.MmgEnum;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.constant.Role;
import com.atpl.mmg.dao.city.CityDAO;
import com.atpl.mmg.domain.city.CityDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.model.profile.AddressModel;
import com.atpl.mmg.model.profile.AuthModel;
import com.atpl.mmg.model.profile.ProfileModel;

@Component
public class AuthCommonService {

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	CityDAO cityDAO;

	/** Customer Validation **/
	public ProfileModel profileInformation(String profileId, String roleId) throws Exception {
		ProfileModel profileModel = new ProfileModel();
		Map<String, Object> httpResponse = null;
		httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v2/profile/" + profileId + "?roleId=" + roleId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else {
				profileModel = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(), ProfileModel.class);
				List<AddressModel> addressList = profileModel.getAddress();
				if (null != addressList) {
					for (AddressModel address : addressList) {
						if (roleId.equalsIgnoreCase(Role.CUSTOMER.getCode())
								|| roleId.equalsIgnoreCase(Role.DRIVER.getCode())) {
							if (address.getType().equalsIgnoreCase("HOME")) {
								profileModel.setAddressObject(address);
								break;
							}
						} else {
							if (address.getType().equalsIgnoreCase("OFFICE"))
								profileModel.setAddressObject(address);
							break;
						}
					}
				}
			}
		} else
			throw new BACKEND_SERVER_ERROR();
		return profileModel;
	}

	public Map<String, ProfileModel> profileInfoOnRole(int roleId) throws Exception {
		List<ProfileModel> profileModelList = null;
		Map<String, ProfileModel> profileMap = new HashMap<>();
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "/v2/profile/role/" + roleId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				profileModelList = JsonUtil.parseJsonArray(
						(jsonResponse.getJSONObject("data")).getJSONArray("list").toString(), ProfileModel.class);
				for (ProfileModel profileModel : profileModelList) {
					profileMap.put(profileModel.getId(), profileModel);
				}
			} else
				throw new NOT_FOUND("Profile list is empty");
			return profileMap;
		} else
			throw new NOT_FOUND("Profile not found");
	}

	public ProfileModel validateRole(String mobileNumber, String roleId, boolean isFlag) throws Exception {
		ProfileModel profileModel = new ProfileModel();
		Map<String, Object> httpResponse = null;
		if (isFlag) {
			httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getAuthUrl() + "v2/profile/details?mobileNumber=" + mobileNumber + "&roleId=" + roleId
							+ "&isRequired=" + isFlag,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.AUTH.name());
		} else {
			httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getAuthUrl() + "v2/profile/details?mobileNumber=" + mobileNumber + "&roleId="
							+ roleId,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.AUTH.name());
		}
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else {
				profileModel = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(), ProfileModel.class);
			}
		} else
			throw new BACKEND_SERVER_ERROR();
		return profileModel;
	}

	public List<ProfileModel> profileInformationByRoleId(String roleId) throws Exception {
		List<ProfileModel> profileModel = null;
		Map<String, Object> httpResponse = null;
		httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v2/profile/role/" + roleId + "/status/" + true,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else
				profileModel = JsonUtil.parseJsonArray(
						(jsonResponse.getJSONObject("data")).getJSONArray("list").toString(), ProfileModel.class);
		} else
			throw new BACKEND_SERVER_ERROR();
		return profileModel;
	}

	public List<ProfileModel> profileInformationByRoleIdAndCity(String roleId, int cityId) throws Exception {

		List<ProfileModel> profileModel = null;
		Map<String, Object> httpResponse = null;
		httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v2/profile/role/" + roleId + "/status/" + true + "?cityId=" + cityId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else
				profileModel = JsonUtil.parseJsonArray(
						(jsonResponse.getJSONObject("data")).getJSONArray("list").toString(), ProfileModel.class);
		} else
			throw new BACKEND_SERVER_ERROR();
		return profileModel;
	}

	public AuthModel getprofileInformationByRoleIdAndCity(String roleId, String cities ) throws Exception {
		AuthModel authModel = new AuthModel();
    	Map<String, Object> httpResponse = null;
    	String []  cityList = cities.split(",");
    	List<String> cityIds=new ArrayList<String>();
    	for( String city: cityList) {
    		CityDomain cityId = cityDAO.getCityId(city);
    		cityIds.add(String.valueOf(cityId.getId()));
    	}
		httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v2/profile/details/role/" + roleId + "?status=true?cityId="
						+ cityIds,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else
				authModel = JsonUtil.fromJson(
						jsonResponse.getJSONObject("data").toString(), AuthModel.class);
		} else
			throw new BACKEND_SERVER_ERROR();
		return authModel;
	}

	public ProfileModel getCompanyDetails(String role, String companyProfileId) throws Exception {
		ProfileModel profileModel = null;
		/** tieups micro service **/
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v2/profile/companyProfile?" + role + "=" + companyProfileId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND(role + " detail not found");
			else
				profileModel = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(), ProfileModel.class);
			List<AddressModel> addressList = profileModel.getAddress();
			if (null != addressList) {
				for (AddressModel address : addressList) {
					if (address.getType().equalsIgnoreCase("OFFICE"))
						profileModel.setAddressObject(address);
					break;
				}
			}
		}
		return profileModel;
	}

	public List<ProfileModel> getOTMemberDetails(int stateId) throws Exception {
		List<ProfileModel> profileModel = null;
		String uri = mmgProperties.getAuthUrl() + "v2/profile/operationalTeam/" + stateId;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(builder.toUriString(),
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Operation Team MembersList not found");
			else
				profileModel = JsonUtil.parseJsonArray(
						(jsonResponse.getJSONObject("data")).getJSONArray("list").toString(), ProfileModel.class);

		return profileModel;
	}

}
