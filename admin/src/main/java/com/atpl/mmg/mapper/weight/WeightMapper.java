package com.atpl.mmg.mapper.weight;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.weight.WeightDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.weight.WeightModel;

@Component
public class WeightMapper extends AbstractModelMapper<WeightModel, WeightDomain> {

	@Override
	public Class<WeightModel> entityType() {

		return WeightModel.class;
	}

	@Override
	public Class<WeightDomain> modelType() {

		return WeightDomain.class;
	}

}
