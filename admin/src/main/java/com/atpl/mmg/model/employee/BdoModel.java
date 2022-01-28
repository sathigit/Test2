package com.atpl.mmg.model.employee;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/* Author:Sindhu
 * creationDate:17-11-2019
 * Description:Bdo Mapping to Bdm*/

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BdoModel {

	private static final long serialVersionUID = -2196266153406082047L;

	private int id;
	private String bdoId;
	private String bdmId;
	private String franchiseId;
	private String creationDate;
	private String modificationDate;

	private int profileId;
	private String firstName;
	private String lastName;
	private String emailId;
	private BigInteger mobileNumber;

	private String franchiseName;
	private int bdoCount;

	private String spreadsheet;
	private int beId;
	
	private int businessExecutive;

	public String getSpreadsheet() {
		return spreadsheet;
	}

	public void setSpreadsheet(String spreadsheet) {
		this.spreadsheet = spreadsheet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBdoId() {
		return bdoId;
	}

	public void setBdoId(String bdoId) {
		this.bdoId = bdoId;
	}

	public String getBdmId() {
		return bdmId;
	}

	public void setBdmId(String bdmId) {
		this.bdmId = bdmId;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public BigInteger getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(BigInteger mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFranchiseName() {
		return franchiseName;
	}

	public void setFranchiseName(String franchiseName) {
		this.franchiseName = franchiseName;
	}

	public int getBdoCount() {
		return bdoCount;
	}

	public void setBdoCount(int bdoCount) {
		this.bdoCount = bdoCount;
	}

	public int getBeId() {
		return beId;
	}

	public void setBeId(int beId) {
		this.beId = beId;
	}

	public int getBusinessExecutive() {
		return businessExecutive;
	}

	public void setBusinessExecutive(int businessExecutive) {
		this.businessExecutive = businessExecutive;
	}

}
