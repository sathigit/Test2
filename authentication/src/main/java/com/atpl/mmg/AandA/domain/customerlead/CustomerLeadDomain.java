package com.atpl.mmg.AandA.domain.customerlead;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class CustomerLeadDomain {

	private String uuid;
	private String platform;
	private String firstName;
	private String lastName;
	private String doorNumber;
	private String street;
	private int cityId;
	private int stateId;
	private int countryId;
	private BigInteger pincode;
	private BigInteger mobileNumber;
	private String emailId;
	private String leadStatusId;
	private String leadRemarksId;
	private String comment;
	private String leadProfessionId;
	private String uploadedById;
	private String assignedId;
	private Date assignedDate;
	private Date callDate;
	private String status;
	private Date createdDate;
	private Date modfiedDate;

	public CustomerLeadDomain() {

	}

	public CustomerLeadDomain(String uuid, String platform, String firstName, String lastName, String uploadedById,
			int stateId, BigInteger mobileNumber, String status) {
		this.uuid = uuid;
		this.platform = platform;
		this.firstName = firstName;
		this.lastName = lastName;
		this.uploadedById = uploadedById;
		this.stateId = stateId;
		this.mobileNumber = mobileNumber;
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
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

	public String getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public BigInteger getPincode() {
		return pincode;
	}

	public void setPincode(BigInteger pincode) {
		this.pincode = pincode;
	}

	public BigInteger getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(BigInteger mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getLeadStatusId() {
		return leadStatusId;
	}

	public void setLeadStatusId(String leadStatusId) {
		this.leadStatusId = leadStatusId;
	}

	public String getLeadRemarksId() {
		return leadRemarksId;
	}

	public void setLeadRemarksId(String leadRemarksId) {
		this.leadRemarksId = leadRemarksId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLeadProfessionId() {
		return leadProfessionId;
	}

	public void setLeadProfessionId(String leadProfessionId) {
		this.leadProfessionId = leadProfessionId;
	}

	public String getUploadedById() {
		return uploadedById;
	}

	public void setUploadedById(String uploadedById) {
		this.uploadedById = uploadedById;
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

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModfiedDate() {
		return modfiedDate;
	}

	public void setModfiedDate(Date modfiedDate) {
		this.modfiedDate = modfiedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
