package com.atpl.mmg.AandA.domain;

import java.io.Serializable;
import java.util.Map;

public class DBUpdate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4748328299026055363L;
	private String tableName;
	private Map<String, Object> expression;
	private Map<String, Object> conditions;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, Object> getExpression() {
		return expression;
	}

	public void setExpression(Map<String, Object> expression) {
		this.expression = expression;
	}

	public Map<String, Object> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

}
