package com.atpl.mmg.model.report;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ExpenseModel {

	private BigInteger id;
	private BigInteger expenseSourceId;
	private String source;
	private BigInteger expensesYear;
	private BigInteger expensesAmount;

	public BigInteger getExpenseSourceId() {
		return expenseSourceId;
	}

	public void setExpenseSourceId(BigInteger expenseSourceId) {
		this.expenseSourceId = expenseSourceId;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigInteger getExpensesYear() {
		return expensesYear;
	}

	public void setExpensesYear(BigInteger expensesYear) {
		this.expensesYear = expensesYear;
	}

	public BigInteger getExpensesAmount() {
		return expensesAmount;
	}

	public void setExpensesAmount(BigInteger expensesAmount) {
		this.expensesAmount = expensesAmount;
	}

}
