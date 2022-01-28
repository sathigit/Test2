package com.atpl.mmg.AandA.model.customerlead;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class CustomerLeadTemplateModel {

	private String state;
	private String platform;
	private String firstName;
	private String lastName;
	private BigInteger mobileNumber;
	private List<String> uuid;
	private String assignedId;
	private Date assignedDate;
	private String status;
	
	public CustomerLeadTemplateModel() {
	}
	public CustomerLeadTemplateModel(String state,String platform, String firstName, String lastName,
			BigInteger mobileNumber) {
		this.state = state;
		this.platform = platform;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public BigInteger getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(BigInteger mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public List<String> getUuid() {
		return uuid;
	}
	public void setUuid(List<String> uuid) {
		this.uuid = uuid;
	}
	public String getAssignedId() {
		return assignedId;
	}
	public void setAssignedId(String assignedId) {
		this.assignedId = assignedId;
	}
	public Date getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}
