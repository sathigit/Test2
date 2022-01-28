package com.atpl.mmg.domain.fdtransdetail;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class FareDistributionTransactionDetailDomain {

	private String uuid;
	private String fdTransactionId;
	private String fareDistributionId;
	private double amount;
	private boolean status;
	private Date createdDate;
	private Date modifiedDate;
	private double percentage;

	public FareDistributionTransactionDetailDomain() {

	}

	public FareDistributionTransactionDetailDomain(String uuid, double amount) {
		this.uuid = uuid;
		this.amount = amount;
	}

	public FareDistributionTransactionDetailDomain(String uuid, String fdTransactionId, String fareDistributionId,
			double amount, boolean status) {
		this.uuid = uuid;
		this.fdTransactionId = fdTransactionId;
		this.fareDistributionId = fareDistributionId;
		this.amount = amount;
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFdTransactionId() {
		return fdTransactionId;
	}

	public void setFdTransactionId(String fdTransactionId) {
		this.fdTransactionId = fdTransactionId;
	}

	public String getFareDistributionId() {
		return fareDistributionId;
	}

	public void setFareDistributionId(String fareDistributionId) {
		this.fareDistributionId = fareDistributionId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
