package com.atpl.mmg.mapper.faredisttype;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.faredisttype.FareDistributionTypeDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.faredisttype.FareDistributionTypeModel;

@Component
public class FareDistributionTypeMapper  extends AbstractModelMapper<FareDistributionTypeModel, FareDistributionTypeDomain>  {

	@Override
	public Class<FareDistributionTypeModel> entityType() {
		return FareDistributionTypeModel.class;
	}

	@Override
	public Class<FareDistributionTypeDomain> modelType() {
		return FareDistributionTypeDomain.class;
	}

}
