package com.atpl.mmg.mapper.bank;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.bank.BankDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.bank.BankModel;

@Component
public class BankMapper extends AbstractModelMapper<BankModel, BankDomain> {

	@Override
	public Class<BankModel> entityType() {
		return BankModel.class;
	}

	@Override
	public Class<BankDomain> modelType() {
		return BankDomain.class;
	}

}