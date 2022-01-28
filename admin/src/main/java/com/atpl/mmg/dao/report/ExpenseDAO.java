package com.atpl.mmg.dao.report;

import java.util.List;

import com.atpl.mmg.domain.report.ExpenseDomain;


public interface ExpenseDAO {
	
	public String addExpense(ExpenseDomain expenseDomain) throws Exception;
	
	public String addExpenseSource(ExpenseDomain expenseDomain) throws Exception;
	
	public ExpenseDomain getExpenseSource(int id) throws Exception;
	
	public String updateExpenseSource(ExpenseDomain id) throws Exception;
	
	public String deleteExpenseSource(int id)throws Exception;
	
	public List<ExpenseDomain> getExpenseSource() throws Exception;	
}
