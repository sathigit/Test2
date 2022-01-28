package com.atpl.mmg.service.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.model.profile.AddressModel;
import com.atpl.mmg.model.profile.AuthModel;
import com.atpl.mmg.model.profile.ProfileModel;
import com.atpl.mmg.model.profile.RoleModel;
import com.atpl.mmg.model.route.RouteTag;
import com.atpl.mmg.model.route.RouteTagModel;
import com.atpl.mmg.service.faredist.FareDistributionUtil;
import com.atpl.mmg.utils.CommonUtils;

@Component
public class AdminAuthService {

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	CityDAO cityDAO;

	@Autowired
	FareDistributionUtil fareDistributionUtil;

	public String getRoleInfo(int roleId) throws Exception {
		String roleName = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "/v1/role/" + roleId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Role not found");
			else {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				roleName = data.get("roleName").toString();
			}
		return roleName;
	}

	public Map<Integer, RoleModel> getRolesList() throws Exception {
		List<RoleModel> roleModelList = null;
		Map<Integer, RoleModel> roleMap = new HashMap<>();
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(mmgProperties.getAuthUrl() + "/v1/role/allRoles",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				roleModelList = JsonUtil.parseJsonArray(
						(jsonResponse.getJSONObject("data")).getJSONArray("list").toString(), RoleModel.class);
				for (RoleModel roleModel : roleModelList) {
					roleMap.put(roleModel.getId(), roleModel);
				}
			} else
				throw new NOT_FOUND("Role list is empty");
			return roleMap;
		} else
			throw new NOT_FOUND("Role not found");

	}

	public Map<Integer, RoleModel> getAllRolesList() throws Exception {
		List<RoleModel> roleModelList = null;
		Map<Integer, RoleModel> roleMap = new HashMap<>();
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(mmgProperties.getAuthUrl() + "/getRole",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				roleModelList = JsonUtil.parseJsonArray(jsonResponse.getJSONArray("data").toString(), RoleModel.class);
				for (RoleModel roleModel : roleModelList) {
					roleMap.put(roleModel.getId(), roleModel);
				}
			} else
				throw new NOT_FOUND("Role list is empty");
			return roleMap;
		} else
			throw new NOT_FOUND("Role not found");

	}

	public ProfileModel getDetails(String role, String companyProfileId, String companyRoleId) throws Exception {
		ProfileModel profileModel = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v2/profile/companyProfile?" + companyRoleId + "=" + companyProfileId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND(role + " detail not found");
			else {
				profileModel = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(), ProfileModel.class);
				List<AddressModel> addressList = profileModel.getAddress();
				if (null != addressList) {
					for (AddressModel address : addressList) {
						if (role.equals(Role.CUSTOMER.getCode()) || role.equals(Role.DRIVER.getCode())) {
							if (address.getType().equalsIgnoreCase("HOME"))
								profileModel.setAddr(fareDistributionUtil.getAddress(address.getAddress1(),
										address.getAddress2(), address.getCityId(), address.getStateId(),
										address.getCountryId(), address.getPincode()));
						} else {
							if (address.getType().equalsIgnoreCase("OFFICE"))
								profileModel.setAddr(fareDistributionUtil.getAddress(address.getAddress1(),
										address.getAddress2(), address.getCityId(), address.getStateId(),
										address.getCountryId(), address.getPincode()));
						}
					}
				}
			}
		}
		return profileModel;
	}

	public int getDashboardByRole(String roleId, boolean status) throws Exception {
		int totalCount = 0;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "/v1/dashboard/profile/role/" + roleId + "/status/" + status,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Role not found");
			else {
				totalCount = jsonResponse.getInt("data");
			}
		return totalCount;
	}

	public ProfileModel getProfileInfo(String profileId, String roleId) throws Exception {
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v2/profile/" + profileId + "/role/" + roleId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else {
				ProfileModel profileInfo = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(),
						ProfileModel.class);
				return profileInfo;
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public ProfileModel getProfileDetByProfileId(String profileId, int roleId) throws Exception {
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v2/profile/" + profileId + "?roleId=" + roleId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else {
				ProfileModel profileInfo = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(),
						ProfileModel.class);
				List<AddressModel> addressList = profileInfo.getAddress();
				if (null != addressList) {
					for (AddressModel address : addressList) {
						if (roleId == Integer.parseInt(Role.CUSTOMER.getCode())
								|| roleId == Integer.parseInt(Role.DRIVER.getCode())) {
							if (address.getType().equalsIgnoreCase("HOME")) {
								profileInfo.setAddressObject(address);
								break;
							}
						} else {
							if (address.getType().equalsIgnoreCase("OFFICE"))
								profileInfo.setAddressObject(address);
							break;
						}
					}
				}
				return profileInfo;
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public ProfileModel getProfileById(String profileId, boolean isCustomer) throws JSONException, Exception {
		ProfileModel profile = null;
		Map<String, Object> httpResponse = null;
		if (isCustomer) {
			httpResponse = GenericHttpClient.doHttpGet(mmgProperties.getAuthUrl() + "/profile/customer/" + profileId,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.AUTH.name());
		} else {
			httpResponse = GenericHttpClient.doHttpGet(mmgProperties.getAuthUrl() + "/profile/" + profileId,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.AUTH.name());
		}
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else {
				profile = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(), ProfileModel.class);
				return profile;
			}
		} else
			throw new BACKEND_SERVER_ERROR();
	}

	public String getCityId(String cities) throws Exception {
		String[] cityList = cities.split(",");
		List<String> cityIds = new ArrayList<String>();
		for (String city : cityList) {
			CityDomain cityDomain = cityDAO.getCityId(city);
			cityIds.add(String.valueOf(cityDomain.getId()));
		}
		String cityId = String.join(",", cityIds);
		System.out.println(cityId);
		return cityId;
	}

	public AuthModel getProfileDetails(String allCities) throws Exception {
		List<String> roles = new ArrayList<String>();
		roles.add(Role.COORDINATOR.getCode());
		roles.add(Role.FRANCHISE.getCode());
		String roleId = String.join(",", roles);
		String cityids = getCityId(allCities);
		AuthModel authModel = getprofileInformationByRoleIdAndCity(roleId, cityids);
		return authModel;
	}

	public AuthModel getprofileInformationByRoleIdAndCity(String roles, String cities) throws Exception {
		AuthModel authModel = new AuthModel();
		Map<String, Object> httpResponse = null;

		httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v2/profile/details/role/" + roles + "?status=true&cityId=" + cities,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else
				authModel = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(), AuthModel.class);
		} else
			throw new BACKEND_SERVER_ERROR();
		return authModel;
	}

	public boolean tagVendor(RouteTagModel routeTag) throws Exception {
		RouteTag route = new RouteTag();
			route.setVendorId(routeTag.getVendorId());
			route.setVendorRoleId(routeTag.getRoleId());
			String reqBody = JsonUtil.toJsonString(route);
			Map<String, Object> httpResponse = GenericHttpClient.doHttpPost(
					mmgProperties.getAuthUrl() + "v2/tag/vendor",
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), reqBody, MmgEnum.AUTH.name());

			int statusCode = (int) httpResponse.get("statusCode");
			JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
			if (jsonResponse != null)
				if (statusCode != HttpStatus.OK.value())
					throw new UPDATE_FAILED("Save Failed");
			return true;
	}

}
