package com.atpl.mmg.service.faredist;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.atpl.mmg.common.ApiUtility;
import com.atpl.mmg.common.GenericHttpClient;
import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.common.MmgEnum;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.model.profile.DriverModel;

@Component
public class AdminFranchiseCommonService {
	@Autowired
	MMGProperties mmgProperties;

	public DriverModel getDriverDetailsByDriverId(String driverId) throws Exception {
		DriverModel driverModel = new DriverModel();
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFranchiseUrl() + "/driver/information/" + driverId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.OWNER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) 
				throw new NOT_FOUND("Driver detail not found");
			else
				driverModel = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(), DriverModel.class);
		} else
			throw new BACKEND_SERVER_ERROR();
		return driverModel;
	}

}
