package com.atpl.mmg.mapper.fdtrans;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.fdtrans.FareDistributionTransactionDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.fdtrans.FareDistributionTransactionModel;

@Component
public class FareDistributionTransactionMapper
		extends AbstractModelMapper<FareDistributionTransactionModel, FareDistributionTransactionDomain> {

	@Override
	public Class<FareDistributionTransactionModel> entityType() {
		return FareDistributionTransactionModel.class;
	}

	@Override
	public Class<FareDistributionTransactionDomain> modelType() {
		return FareDistributionTransactionDomain.class;
	}

}
