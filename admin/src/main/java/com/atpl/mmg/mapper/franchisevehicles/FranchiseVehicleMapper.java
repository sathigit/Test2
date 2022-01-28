package com.atpl.mmg.mapper.franchisevehicles;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.franchisevehicles.FranchiseVehicleDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.franchisevehicles.FranchiseVehicleModel;

@Component
public class FranchiseVehicleMapper extends AbstractModelMapper<FranchiseVehicleModel, FranchiseVehicleDomain> {

	@Override
	public Class<FranchiseVehicleModel> entityType() {
		return FranchiseVehicleModel.class;
	}

	@Override
	public Class<FranchiseVehicleDomain> modelType() {
		return FranchiseVehicleDomain.class;
	}

}
