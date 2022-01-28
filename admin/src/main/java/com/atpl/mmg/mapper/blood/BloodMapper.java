package com.atpl.mmg.mapper.blood;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.blood.BloodDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.blood.BloodModel;

@Component
public class BloodMapper extends AbstractModelMapper<BloodModel, BloodDomain> {

	@Override
	public Class<BloodModel> entityType() {
		return BloodModel.class;
	}

	@Override
	public Class<BloodDomain> modelType() {
		return BloodDomain.class;
	}

}
