package com.atpl.mmg.AandA.service.profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.atpl.mmg.AandA.common.ApiUtility;
import com.atpl.mmg.AandA.common.GenericHttpClient;
import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.common.MmgEnum;
import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.model.faredist.FareDistributionDTo;
import com.atpl.mmg.AandA.model.profile.BankModel;
import com.atpl.mmg.AandA.model.profile.City;
import com.atpl.mmg.AandA.model.profile.CustomerTypeModel;
import com.atpl.mmg.AandA.model.profile.State;
import com.atpl.mmg.AandA.model.profile.TandCModel;

@Component
public class AdminCommonService {

	@Autowired
	MMGProperties mmgProperties;

	public String cityNameById(int cityId) throws Exception {
		String cityName = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1" + "/city/id/" + cityId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				cityName = data.getString("name");
			} else
				throw new NOT_FOUND(jsonResponse.getString("message"));
		} else
			throw new BACKEND_SERVER_ERROR();
		return cityName;
	}

	public String stateNameById(int stateId) throws Exception {
		String stateName = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1" + "/state/" + stateId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new NOT_FOUND(jsonResponse.getString("message"));
			} else {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				stateName = data.getString("name");
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return stateName;
	}

	public int getStateId(String stateName) throws Exception {
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1" + "/state/name/" + stateName,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new NOT_FOUND(jsonResponse.getString("message"));
			} else {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				int stateId = data.getInt("id");
				return stateId;
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public String countryNameById(int countryId) throws Exception {
		String countryName = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1" + "/country/id/" + countryId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new NOT_FOUND(jsonResponse.getString("message"));
			} else {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				countryName = data.getString("name");
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return countryName;
	}

	public List<TandCModel> getActiveTermsAndConditions(int roleId) throws Exception {
		String uri = mmgProperties.getAdminUrl() + "v1/terms/conditions/role/" + roleId + "?isActive=true";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		List<TandCModel> tandCModel = new ArrayList<TandCModel>();
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(builder.toUriString(),
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value()) {
				throw new NOT_FOUND("Terms and Conditions not found");
			} else {
				tandCModel = JsonUtil.parseJsonArray(
						(jsonResponse.getJSONObject("data")).getJSONArray("list").toString(), TandCModel.class);
				return tandCModel;
			}
		return tandCModel;
	}

	public String bankNameById(int bankId) throws Exception {
		String bankName = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1/bank/" + bankId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				bankName = data.getString("name");
			} else
				throw new NOT_FOUND(jsonResponse.getString("message"));
		} else
			throw new BACKEND_SERVER_ERROR();
		return bankName;
	}

	public String organizationNameById(int organizationId) throws Exception {
		String organizationName = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1/organization/" + organizationId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				organizationName = data.getString("name");
			} else
				throw new NOT_FOUND(jsonResponse.getString("message"));
		} else
			throw new BACKEND_SERVER_ERROR();
		return organizationName;
	}

	public String bloodNameById(int bloodId) throws Exception {
		String bloodName = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1/blood/" + bloodId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				bloodName = data.getString("bloodName");
			} else
				throw new NOT_FOUND(jsonResponse.getString("message"));
		} else
			throw new BACKEND_SERVER_ERROR();
		return bloodName;
	}

	public String licenseCategoryNameById(int id) throws Exception {
		String licenseCategoryName = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1/getLicenceCat/" + id,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				licenseCategoryName = data.getString("licenceCategoryName");
			} else
				throw new NOT_FOUND(jsonResponse.getString("message"));
		} else
			throw new BACKEND_SERVER_ERROR();
		return licenseCategoryName;
	}

	public List<FareDistributionDTo> getFDListByFranchiseId(String franchiseId, boolean isActive) throws Exception {
		List<FareDistributionDTo> fareDistributionList = null;
		Map<String, Object> httpResponse = null;
		if (isActive) {
			httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getAdminUrl() + "v1/fare/distribution?isActive=" + isActive + "&franchiseId="
							+ franchiseId,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.OWNER.name());
		} else {
			httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getAdminUrl() + "v1/fare/distribution/franchise/" + franchiseId,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.OWNER.name());
		}
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				fareDistributionList = JsonUtil.parseJsonArray(jsonResponse.getJSONArray("data").toString(),
						FareDistributionDTo.class);
			}
		}
		return fareDistributionList;
	}

	// Get indian cities
	public Map<Integer, City> getAllCities() throws Exception {
		List<City> cityList = null;
		Map<Integer, City> cityMap = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1" + "/city?getCities=true",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				cityMap = new HashMap<>();
				cityList = JsonUtil.parseJsonArray((jsonResponse.getJSONObject("data")).getJSONArray("list").toString(),
						City.class);
				for (City city : cityList) {
					cityMap.put(city.getId(), city);
				}
			} else
				throw new NOT_FOUND(jsonResponse.getString("message"));
		} else
			throw new BACKEND_SERVER_ERROR();
		return cityMap;
	}

	// Get indian states
	public Map<Integer, State> getAllStates(int countryId) throws Exception {
		List<State> stateList = null;
		Map<Integer, State> stateMap = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1/State/country/" + countryId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				stateMap = new HashMap<>();
				stateList = JsonUtil.parseJsonArray(
						(jsonResponse.getJSONObject("data")).getJSONArray("list").toString(), State.class);
				for (State state : stateList) {
					stateMap.put(state.getId(), state);
				}
			} else
				throw new NOT_FOUND(jsonResponse.getString("message"));
		} else
			throw new BACKEND_SERVER_ERROR();
		return stateMap;
	}

	public Map<Integer, BankModel> getAllBanks() throws Exception {
		List<BankModel> bankList = null;
		Map<Integer, BankModel> bankMap = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(mmgProperties.getAdminUrl() + "v1" + "/bank",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				bankMap = new HashMap<>();
				bankList = JsonUtil.parseJsonArray((jsonResponse.getJSONObject("data")).getJSONArray("list").toString(),
						BankModel.class);
				for (BankModel bankModel : bankList) {
					bankMap.put(bankModel.getId(), bankModel);
				}
			} else
				throw new NOT_FOUND(jsonResponse.getString("message"));
		} else
			throw new BACKEND_SERVER_ERROR();
		return bankMap;
	}

	public String customerTypeById(int customerTypeId) throws Exception {
		String typeName = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1/customer/type/" + customerTypeId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new NOT_FOUND(jsonResponse.getString("message"));
			} else {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				typeName = data.getString("typeName");
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return typeName;
	}

	public int getCustomerTypeByName(String typeName) throws Exception {
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1//customer/type/name/" + typeName,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new NOT_FOUND(jsonResponse.getString("message"));
			} else {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				int customerTypeId = data.getInt("id");
				return customerTypeId;
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public Map<Integer, CustomerTypeModel> getAllCustomerType() throws Exception {
		List<CustomerTypeModel> customerTypeList = null;
		Map<Integer, CustomerTypeModel> customerTypeMap = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(mmgProperties.getAdminUrl() + "v1/customer/type",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				customerTypeMap = new HashMap<>();
				customerTypeList = JsonUtil.parseJsonArray(
						(jsonResponse.getJSONArray("data").toString()), CustomerTypeModel.class);
				for (CustomerTypeModel customerType : customerTypeList) {
					customerTypeMap.put(customerType.getId(), customerType);
				}
			} else
				throw new NOT_FOUND(jsonResponse.getString("message"));
		} else
			throw new BACKEND_SERVER_ERROR();
		return customerTypeMap;
	}

}
