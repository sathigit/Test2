package com.atpl.mmg.dao.franchisevehicles;

import java.util.List;
import java.util.Map;

import com.atpl.mmg.domain.franchisevehicles.FranchiseVehicleDomain;

public interface FranchiseVehicleDAO {

	public List<FranchiseVehicleDomain> getAllVehicleImages(String goodsTypeId,
			String kerbWeightId,double numberOfTon) throws Exception;
	
	public List<FranchiseVehicleDomain> getSingleVehicleImages(String goodsTypeId,
			String kerbWeightId,double numberOfTon) throws Exception;
	
}
