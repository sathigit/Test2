package com.atpl.mmg.service.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.atpl.mmg.domain.dbupdate.DBUpdate;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.model.admin.VehicleDetailsModel;
import com.atpl.mmg.utils.DateUtility;


@Component
public class FranchiseCommonService {

	@Autowired
	MMGProperties mmgProperties;



	public VehicleDetailsModel getVehicleDetails(String vehicleId) throws Exception {
		VehicleDetailsModel vehicleModel = new VehicleDetailsModel();
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFranchiseUrl() + "v2/vehicle/" + vehicleId+"?fetchRoute=false",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Vehicle not found");
			else {
				vehicleModel = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(), VehicleDetailsModel.class);
			}

		return vehicleModel;
	}

	public List<VehicleDetailsModel> vehicleListOnRouteId(String routeId) throws Exception {
		List<VehicleDetailsModel> vehicleModel = new ArrayList<VehicleDetailsModel>();
		List<VehicleDetailsModel> vehicleList = new ArrayList<VehicleDetailsModel>();
		String uri = mmgProperties.getFranchiseUrl() + "v2/vehicle/route/" + routeId;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(builder.toUriString(),
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Vehicle not found");
			else {
				vehicleModel = JsonUtil.parseJsonArray(jsonResponse.getJSONArray("data").toString(), VehicleDetailsModel.class);
				for(VehicleDetailsModel veh:vehicleModel) {
					VehicleDetailsModel vehicleDetails = new VehicleDetailsModel(veh.getVehicleId(), veh.getRegistrationNumber(), veh.getOwnerName(), veh.getCategory()) ;
					vehicleList.add(vehicleDetails);
				}
			}
		return vehicleList;
	}


	public boolean updateVehicleIsTag(String vehicleId,boolean isTag) throws Exception {
		DBUpdate dbUpdate = new DBUpdate();
		dbUpdate.setTableName("vehicle");
		Map<String, Object> expression = new HashMap<String, Object>();
		Map<String, Object> conditions = new HashMap<String, Object>();

		expression.put("isTag", isTag);
		conditions.put("vehicleId", vehicleId);
		dbUpdate.setExpression(expression);
		dbUpdate.setConditions(conditions);
		String reqBody = JsonUtil.toJsonString(dbUpdate);

		Map<String, Object> httpResponse = GenericHttpClient.doHttpPost(
				mmgProperties.getFranchiseUrl() + "v1/db/update",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()), reqBody,
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new UPDATE_FAILED("Update failed");
		}
		return true;
	}

}
