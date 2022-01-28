package com.atpl.mmg.service.report;

import java.util.List;

import com.atpl.mmg.model.report.ExpenseModel;


public interface ExpenseService {

	public String addExpense(ExpenseModel expenseModel) throws Exception;

	public String addExpenseSource(ExpenseModel expenseModel) throws Exception;

	public ExpenseModel getExpenseSource(int id) throws Exception;

	public String updateExpenseSource(ExpenseModel id) throws Exception;

	public String deleteExpenseSource(int id) throws Exception;

	public List<ExpenseModel> getExpenseSource() throws Exception;
}
