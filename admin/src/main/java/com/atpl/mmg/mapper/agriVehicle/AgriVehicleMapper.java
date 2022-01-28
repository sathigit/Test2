package com.atpl.mmg.mapper.agriVehicle;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.agriVehicle.AgriVehicleDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.agriVehicle.AgriVehicleModel;

@Component
public class AgriVehicleMapper extends AbstractModelMapper<AgriVehicleModel, AgriVehicleDomain> {

	@Override
	public Class<AgriVehicleModel> entityType() {
		// TODO Auto-generated method stub
		return AgriVehicleModel.class;
	}

	@Override
	public Class<AgriVehicleDomain> modelType() {
		// TODO Auto-generated method stub
		return AgriVehicleDomain.class;
	}

}
