package com.atpl.mmg.AandA.service.profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.atpl.mmg.AandA.common.ApiUtility;
import com.atpl.mmg.AandA.common.GenericHttpClient;
import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.common.MmgEnum;
import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.constant.ImageCategory;
import com.atpl.mmg.AandA.domain.DBUpdate;
import com.atpl.mmg.AandA.domain.profile.DriverDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.BADGE_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.DL_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.model.profile.Driver;
import com.atpl.mmg.AandA.model.profile.DriverImageModelV2;

@Component
public class AuthFranchiseCommonService {

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	AdminCommonService adminCommonService;

	/*
	 * public String saveFrequentCustomer(FrequentCustomerModel
	 * frequentCustomerModel) throws Exception { String reqBody =
	 * JsonUtil.toJsonString(frequentCustomerModel); Map<String, Object>
	 * httpResponse = GenericHttpClient.doHttpPost( mmgProperties.getFranchiseUrl()
	 * + "v2" + "/frequent/customer",
	 * ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(),
	 * mmgProperties.getAccessTokenKey()), reqBody, MmgEnum.FRANCHISE.name()); int
	 * statusCode = (int) httpResponse.get("statusCode"); JSONObject jsonResponse =
	 * (JSONObject) httpResponse.get("response"); if (jsonResponse != null) { if
	 * (statusCode != HttpStatus.OK.value()) throw new
	 * SAVE_FAILED("Frequent Customer save failed"); else return "Save Succefully";
	 * } return null; }
	 */

	/*
	 * public boolean checkGstNumber(String gstNumber) throws Exception { if
	 * (gstNumber != null && gstNumber.trim().length() > 0) { Map<String, Object>
	 * httpResponse = GenericHttpClient.doHttpGet( mmgProperties.getFranchiseUrl() +
	 * "v2/frequent/check/gstNo/" + gstNumber,
	 * ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null),
	 * MmgEnum.FRANCHISE.name()); int statusCode = (int)
	 * httpResponse.get("statusCode"); JSONObject jsonResponse = (JSONObject)
	 * httpResponse.get("response"); if (jsonResponse != null) if (statusCode !=
	 * HttpStatus.OK.value()) throw new GST_NUMBER_ALREADY_EXIST(); else return
	 * true; } else throw new NOT_FOUND("Please enter the gst number"); return true;
	 * }
	 */

	public void validateDl(Driver driverModel, String id, boolean updateStatus) throws Exception {
		JSONObject data = null;
		String profileId = "0";
		String uri = mmgProperties.getFranchiseUrl() + "v2/driver/validateDlNumber/" + driverModel.getDlNumber() + "/status/"
				+ updateStatus;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(builder.toUriString(),
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (updateStatus) {
			if (jsonResponse.has("data"))
				if (((data = (JSONObject) jsonResponse.get("data")) != null)
						|| ((data = (JSONObject) jsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) jsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new DL_NUMBER_ALREADY_EXIST();
				}
		} else if (statusCode != HttpStatus.OK.value()) {
			throw new DL_NUMBER_ALREADY_EXIST();
		}
	}

	public void validateBadge(Driver driverModel, String id, boolean updateStatus) throws Exception {
		JSONObject data = null;
		String profileId = "0";
		String uri = mmgProperties.getFranchiseUrl() + "v2/driver/validateBadgeNumber/" + driverModel.getBadgeNumber()
		+ "/status/" + updateStatus;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(builder.toUriString(),
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (updateStatus) {
			if (jsonResponse.has("data"))
				if (((data = (JSONObject) jsonResponse.get("data")) != null)
						|| ((data = (JSONObject) jsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) jsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new BADGE_NUMBER_ALREADY_EXIST();
				}
		} else if (statusCode != HttpStatus.OK.value()) {
			throw new BADGE_NUMBER_ALREADY_EXIST();
		}
	}

	public String commonUpdate(DBUpdate DBUpdate) throws Exception {
		String reqBody = JsonUtil.toJsonString(DBUpdate);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpPost(
				mmgProperties.getFranchiseUrl() + "v1/db/update",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), reqBody, MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value()) {
				throw new SAVE_FAILED("Update failed");
			}
		return "Updated successfully";
	}

	public String saveDriver(DriverDomain driver) throws Exception {
		// Profile driverDetails = new Profile();
		String reqBody = JsonUtil.toJsonString(driver);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpPost(mmgProperties.getFranchiseUrl() + "v2/driver",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), reqBody, MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value()) {
				throw new SAVE_FAILED("Driver save failed");
			} else {
				JSONObject data = (JSONObject) jsonResponse.get("data");
				String driverId = data.getString("driverId");
				// driverDetails.setDriverId(driverId);
			}
		return "Driver details saved successfully";
	}

	public Driver getDriverDetails(String profileId) throws Exception {
		Driver driverModel = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFranchiseUrl() + "v2/driver/profile/" + profileId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND(jsonResponse.getString("message"));
			else {
				driverModel = new Driver();
				driverModel = JsonUtil.fromJson(jsonResponse.get("data").toString(), Driver.class);
				if (driverModel.getDriverTypeId() == 1)
					driverModel.setDriverTypeName("Full Time");
				else
					driverModel.setDriverTypeName("Part Time");
				try {
					driverModel.setLicenseCategoryName(
							adminCommonService.licenseCategoryNameById(driverModel.getLicenceCategory()));
				} catch (Exception e) {
					driverModel.setLicenseCategoryName(null);
				}
				try {
					driverModel.setBloodName(adminCommonService.bloodNameById(driverModel.getBloodId()));
				} catch (Exception e) {
					driverModel.setBloodName(null);
				}
				return driverModel;
			}
		return driverModel;
	}

	public DriverDomain getDriverDetailsByDriverId(String driverId) throws Exception {
		DriverDomain driverDomain = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFranchiseUrl() + "v2/driver/" + driverId + "/isCallingFrmProfile/" + true,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND(jsonResponse.getString("message"));
			else {
				driverDomain = new DriverDomain();
				driverDomain = JsonUtil.fromJson(jsonResponse.get("data").toString(), DriverDomain.class);
				return driverDomain;
			}
		return driverDomain;
	}

	public Map<String, Driver> getAllDriverList(String requestedBy) throws Exception {
		Map<String, Driver> driverModelMap = null;
		List<DriverDomain> driverModelV2List = null;
		String url = null;
		if (null == requestedBy)
			url = "v2/driver";
		else
			url = "v2/driver?requestedBy=" + requestedBy;
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getAllDriverList in FranchiseCommonService"));

		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(mmgProperties.getFranchiseUrl() + url,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND(jsonResponse.getString("message"));
			else {
				driverModelV2List = JsonUtil.parseJsonArray(
						(jsonResponse.getJSONObject("data")).getJSONArray("list").toString(), DriverDomain.class);
				driverModelMap = new HashMap<>();
				for (DriverDomain driverDomain : driverModelV2List) {
					Driver driver = new Driver();
					BeanUtils.copyProperties(driverDomain, driver);
					driverModelMap.put(driver.getProfileId(), driver);
				}
			}
		return driverModelMap;
	}

	public Map<String, Driver> getDriversByFranchiseId(String franchiseId, Map<String, String> reqParam, String status)
			throws Exception {
		Map<String, Driver> driverModelMap = null;
		List<DriverDomain> driverModelV2List = null;
		Map<String, Object> httpResponse = null;
		int pageNo = 0, pageSize = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("pageNo")) {
				pageNo = Integer.parseInt(reqParam.get("pageNo"));
			}
			if (reqParam.containsKey("pageSize")) {
				pageSize = Integer.parseInt(reqParam.get("pageSize"));
			}
		}
		if (pageNo != 0 && pageSize != 0)
			httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getFranchiseUrl() + "v2/driver/franchise/" + franchiseId + "?pageNo=" + pageNo
							+ "&pageSize=" + pageSize + "&status=" + status,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.FRANCHISE.name());
		else
			httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getFranchiseUrl() + "v2/driver/franchise/" + franchiseId + "?status=" + status,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.FRANCHISE.name());

		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode == HttpStatus.OK.value()) {
				driverModelV2List = JsonUtil.parseJsonArray(
						(jsonResponse.getJSONObject("data")).getJSONArray("list").toString(), DriverDomain.class);
				driverModelMap = new HashMap<>();
				for (DriverDomain driverDomain : driverModelV2List) {
					Driver driver = new Driver();
					BeanUtils.copyProperties(driverDomain, driver);
					driverModelMap.put(driverDomain.getProfileId(), driver);
				}
			}
		return driverModelMap;
	}

	public boolean getDriverImagesByDriverId(String profileId) throws Exception {
		Driver driverModelV2 = getDriverDetails(profileId);
		List<DriverImageModelV2> driverModel = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFranchiseUrl() + "v2/driver/all/image/" + driverModelV2.getDriverId() + "?isActive="
						+ true,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND(jsonResponse.getString("message"));
			else {
				driverModel = JsonUtil.parseJsonArray(jsonResponse.get("data").toString(), DriverImageModelV2.class);
				ArrayList<String> list = new ArrayList<>();
				for (DriverImageModelV2 driverImageModelV2 : driverModel) {
					list.add(driverImageModelV2.getCategory());
				}
				ArrayList<String> imagelist = new ArrayList<>();
				imagelist.add(ImageCategory.PROFILE.getCode());
				imagelist.add(ImageCategory.LICENSE.getCode());
				imagelist.add(ImageCategory.PASSBOOK.getCode());
				imagelist.add(ImageCategory.PANCARD.getCode());
				imagelist.add(ImageCategory.AADHAR.getCode());
				if (imagelist.size() != list.size())
					throw new NOT_FOUND("Please upload the images of all category!!");
			}
		return true;

	}

	public void updateDriver(Driver driver) throws Exception {
		String reqBody = JsonUtil.toJsonString(driver);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpPut(mmgProperties.getFranchiseUrl() + "v2/driver",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()), reqBody,
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new UPDATE_FAILED("Update Failed");
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public String dbUpdate(DBUpdate dbUpdate) throws Exception {
		String reqBody = JsonUtil.toJsonString(dbUpdate);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpPost(
				mmgProperties.getFranchiseUrl() + "v1/db/update",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), reqBody, MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value()) {
				throw new SAVE_FAILED("update  failed");
			}
		return "updated successfully";
	}

	public Driver getDriverCountByFranchiseId(String franchiseId) throws Exception {
		Driver driverModel = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFranchiseUrl() + "v2/driver/dashboard/franchise/" + franchiseId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND(jsonResponse.getString("message"));
			else {
				driverModel = new Driver();
				driverModel = JsonUtil.fromJson(jsonResponse.get("data").toString(), Driver.class);
			}
		return driverModel;
	}
}
