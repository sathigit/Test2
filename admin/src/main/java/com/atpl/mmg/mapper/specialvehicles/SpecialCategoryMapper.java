package com.atpl.mmg.mapper.specialvehicles;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.specialvehicles.SpecialCategoryDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.specialvehicles.SpecialCategoryModel;

@Component
public class SpecialCategoryMapper extends AbstractModelMapper<SpecialCategoryModel, SpecialCategoryDomain> {

	@Override
	public Class<SpecialCategoryModel> entityType() {
		// TODO Auto-generated method stub
		return SpecialCategoryModel.class;
	}

	@Override
	public Class<SpecialCategoryDomain> modelType() {
		// TODO Auto-generated method stub
		return SpecialCategoryDomain.class;
	}

}
