package com.atpl.mmg.mapper.vehicle;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.vehicle.VehicleDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.vehicle.VehicleModel;

@Component
public class VehicleMapper extends AbstractModelMapper<VehicleModel, VehicleDomain> {

	@Override
	public Class<VehicleModel> entityType() {
		return VehicleModel.class;
	}

	@Override
	public Class<VehicleDomain> modelType() {
		return VehicleDomain.class;
	}

}
