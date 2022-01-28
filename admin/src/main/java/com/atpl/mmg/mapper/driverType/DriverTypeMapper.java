package com.atpl.mmg.mapper.driverType;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.driverType.DriverTypeDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.driverType.DriverTypeModel;

@Component
public class DriverTypeMapper  extends AbstractModelMapper<DriverTypeModel, DriverTypeDomain>{

	@Override
	public Class<DriverTypeModel> entityType() {
		// TODO Auto-generated method stub
		return DriverTypeModel.class;
	}

	@Override
	public Class<DriverTypeDomain> modelType() {
		// TODO Auto-generated method stub
		return DriverTypeDomain.class;
	}

}
