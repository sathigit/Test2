package com.atpl.mmg.AandA.model.boardingRequest;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnquiryReasonModel {

	private static final long serialVersionUID = 5226810068624464041L;

	private String uuid;
	private String userId;
	private String enquiryId;
	private String reason;
	private String previousStatus;
	private String changedStatus;
	private Date createdDate;
	private Date modifiedDate;
	
	private String status;
	private String userName;
	
	
	

	public EnquiryReasonModel(String userId, String reason, String previousStatus, String changedStatus,
			Date modifiedDate, String userName) {
		super();
		this.userId = userId;
		this.reason = reason;
		this.previousStatus = previousStatus;
		this.changedStatus = changedStatus;
		this.modifiedDate = modifiedDate;
		this.userName = userName;
	}

	public EnquiryReasonModel() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEnquiryId() {
		return enquiryId;
	}

	public void setEnquiryId(String enquiryId) {
		this.enquiryId = enquiryId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}

	public String getChangedStatus() {
		return changedStatus;
	}

	public void setChangedStatus(String changedStatus) {
		this.changedStatus = changedStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
