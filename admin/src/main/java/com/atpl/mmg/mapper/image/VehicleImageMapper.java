package com.atpl.mmg.mapper.image;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.image.VehicleImageDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.image.VehicleImageModel;

@Component
public class VehicleImageMapper extends AbstractModelMapper<VehicleImageModel, VehicleImageDomain>{

	@Override
	public Class<VehicleImageModel> entityType() {
		// TODO Auto-generated method stub
		return VehicleImageModel.class;
	}

	@Override
	public Class<VehicleImageDomain> modelType() {
		// TODO Auto-generated method stub
		return VehicleImageDomain.class;
	}

}
