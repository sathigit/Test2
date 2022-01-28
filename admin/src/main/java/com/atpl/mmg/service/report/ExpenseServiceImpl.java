package com.atpl.mmg.service.report;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.report.ExpenseDAO;
import com.atpl.mmg.domain.report.ExpenseDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.report.ExpenseMapper;
import com.atpl.mmg.model.report.ExpenseModel;

@Service("expenseService")
@SuppressWarnings("rawtypes")
public class ExpenseServiceImpl implements ExpenseService, Constants {

	@Autowired
	ExpenseDAO expenseDAO;

	@Autowired
	ExpenseMapper expenseMapper;

	private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

	@Override
	public String addExpense(ExpenseModel expenseModel) throws Exception {
		ExpenseDomain expenseDomain = new ExpenseDomain();
		BeanUtils.copyProperties(expenseModel, expenseDomain);
		return expenseDAO.addExpense(expenseDomain);
	}

	@Override
	public String addExpenseSource(ExpenseModel expenseModel) throws Exception {
		ExpenseDomain expenseDomain = new ExpenseDomain();
		BeanUtils.copyProperties(expenseModel, expenseDomain);
		return expenseDAO.addExpenseSource(expenseDomain);
	}

	@Override
	public ExpenseModel getExpenseSource(int id) throws Exception {
		ExpenseDomain expenseDomain = expenseDAO.getExpenseSource(id);
		ExpenseModel expenseModel = new ExpenseModel();
		if (expenseDomain == null)
			throw new NOT_FOUND("ExpenseSource not found");
		BeanUtils.copyProperties(expenseDomain, expenseModel);
		return expenseModel;
	}

	@Override
	public String updateExpenseSource(ExpenseModel expenseModel) throws Exception {
		ExpenseDomain expenseDomain = new ExpenseDomain();
		BeanUtils.copyProperties(expenseModel, expenseDomain);
		return expenseDAO.updateExpenseSource(expenseDomain);
	}

	@Override
	public String deleteExpenseSource(int id) throws Exception {
		return expenseDAO.deleteExpenseSource(id);

	}

	@Override
	public List<ExpenseModel> getExpenseSource() throws Exception {
		List<ExpenseDomain> expenseDomain = expenseDAO.getExpenseSource();
		return expenseMapper.entityList(expenseDomain);
	}
}
