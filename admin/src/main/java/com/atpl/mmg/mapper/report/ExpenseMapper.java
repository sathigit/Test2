package com.atpl.mmg.mapper.report;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.report.ExpenseDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.report.ExpenseModel;

@Component
public class ExpenseMapper extends AbstractModelMapper<ExpenseModel, ExpenseDomain> {

	@Override
	public Class<ExpenseModel> entityType() {
		return ExpenseModel.class;
	}

	@Override
	public Class<ExpenseDomain> modelType() {
		return ExpenseDomain.class;
	}

}
