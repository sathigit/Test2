package com.atpl.mmg.model.report;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class DirectCostModel {

	private BigInteger id;
	private BigInteger directCostSourceId;
	private String source;
	private BigInteger directCostYear;
	private BigInteger directCostAmount;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getDirectCostSourceId() {
		return directCostSourceId;
	}

	public void setDirectCostSourceId(BigInteger directCostSourceId) {
		this.directCostSourceId = directCostSourceId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigInteger getDirectCostYear() {
		return directCostYear;
	}

	public void setDirectCostYear(BigInteger directCostYear) {
		this.directCostYear = directCostYear;
	}

	public BigInteger getDirectCostAmount() {
		return directCostAmount;
	}

	public void setDirectCostAmount(BigInteger directCostAmount) {
		this.directCostAmount = directCostAmount;
	}

}
