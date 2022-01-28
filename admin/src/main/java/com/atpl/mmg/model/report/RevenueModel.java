package com.atpl.mmg.model.report;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RevenueModel {

	private BigInteger revenueSourceId;
	private BigInteger revenueAmount;
	private BigInteger revenueYear;
	private String source;
	private BigInteger id;

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

	public BigInteger getRevenueSourceId() {
		return revenueSourceId;
	}

	public void setRevenueSourceId(BigInteger revenueSourceId) {
		this.revenueSourceId = revenueSourceId;
	}

	public BigInteger getRevenueAmount() {
		return revenueAmount;
	}

	public void setRevenueAmount(BigInteger revenueAmount) {
		this.revenueAmount = revenueAmount;
	}

	public BigInteger getRevenueYear() {
		return revenueYear;
	}

	public void setRevenueYear(BigInteger revenueYear) {
		this.revenueYear = revenueYear;
	}

}
