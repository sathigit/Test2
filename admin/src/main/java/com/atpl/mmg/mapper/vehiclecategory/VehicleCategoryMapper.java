package com.atpl.mmg.mapper.vehiclecategory;




import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.vehiclecategory.VehicleCategoryDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.vehiclecategory.VehicleCategoryModel;

@Component
public class VehicleCategoryMapper extends AbstractModelMapper<VehicleCategoryModel, VehicleCategoryDomain> {

	@Override
	public Class<VehicleCategoryModel> entityType() {
		return VehicleCategoryModel.class;
	}

	@Override
	public Class<VehicleCategoryDomain> modelType() {
		return VehicleCategoryDomain.class;
	}

}
