package com.atpl.mmg.AandA.mapper.bankaccount;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.bankaccount.BankAccountDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.bankaccount.BankAccountModel;

@Component
public class BankAccountMapper extends AbstractModelMapper<BankAccountModel, BankAccountDomain>{

	@Override
	public Class<BankAccountModel> entityType() {
		return BankAccountModel.class;
	}

	@Override
	public Class<BankAccountDomain> modelType() {
		return BankAccountDomain.class;
	}

}
