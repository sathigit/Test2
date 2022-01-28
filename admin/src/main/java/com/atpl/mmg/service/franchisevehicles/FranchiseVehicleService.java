package com.atpl.mmg.service.franchisevehicles;

import java.util.List;
import java.util.Map;

import com.atpl.mmg.model.franchisevehicles.FranchiseVehicleModel;

public interface FranchiseVehicleService {
	
	public List<FranchiseVehicleModel> getAllVehicleImages(String goodsTypeId,
			String kerbWeightId,Map<String, String> reqParam) throws Exception;

	public List<FranchiseVehicleModel> getSingleVehicleImages(String goodsTypeId,
			String kerbWeightId,Map<String, String> reqParam) throws Exception;
}
