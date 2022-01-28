package com.atpl.mmg.mapper.faredist;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.faredist.FareDistributionDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.faredist.FareDistributionModel;

@Component
public class FareDistributionMapper extends AbstractModelMapper<FareDistributionModel, FareDistributionDomain> {

	@Override
	public Class<FareDistributionModel> entityType() {
		return FareDistributionModel.class;
	}

	@Override
	public Class<FareDistributionDomain> modelType() {
		return FareDistributionDomain.class;
	}

}
