package com.atpl.mmg.mapper.specialvehicles;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.specialvehicles.SpecialVehiclesDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.specialvehicles.SpecialVehiclesModel;

@Component
public class SpecialVehiclesMapper extends AbstractModelMapper<SpecialVehiclesModel, SpecialVehiclesDomain> {

	@Override
	public Class<SpecialVehiclesModel> entityType() {
		// TODO Auto-generated method stub
		return SpecialVehiclesModel.class;
	}

	@Override
	public Class<SpecialVehiclesDomain> modelType() {
		// TODO Auto-generated method stub
		return SpecialVehiclesDomain.class;
	}

}
