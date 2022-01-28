package com.atpl.mmg.model.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class CatalogTransactionFeeModel {

	private String uuid;
	private String issueType;
	private int paymentMode;
	private String goodsCategoryId;
	private String timeBound;
	private double modifyPercent;
	private double modifyFee;
	private double cancelPercent;
	private double cancelFee;
	private boolean refund;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public int getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}

	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}

	public String getTimeBound() {
		return timeBound;
	}

	public void setTimeBound(String timeBound) {
		this.timeBound = timeBound;
	}

	public double getModifyPercent() {
		return modifyPercent;
	}

	public void setModifyPercent(double modifyPercent) {
		this.modifyPercent = modifyPercent;
	}

	public double getModifyFee() {
		return modifyFee;
	}

	public void setModifyFee(double modifyFee) {
		this.modifyFee = modifyFee;
	}

	public double getCancelPercent() {
		return cancelPercent;
	}

	public void setCancelPercent(double cancelPercent) {
		this.cancelPercent = cancelPercent;
	}

	public double getCancelFee() {
		return cancelFee;
	}

	public void setCancelFee(double cancelFee) {
		this.cancelFee = cancelFee;
	}

	public boolean isRefund() {
		return refund;
	}

	public void setRefund(boolean refund) {
		this.refund = refund;
	}

}
