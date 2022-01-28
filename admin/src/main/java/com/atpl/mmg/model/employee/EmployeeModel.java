package com.atpl.mmg.model.employee;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeModel {

	private static final long serialVersionUID = -2196266153406082047L;

	private String employeeId;
	private String firstName;
	private String lastName;
	private String dob;
	private int gender;
	private String doorNumber;
	private String street;
	private BigInteger cityId;
	private BigInteger stateId;
	private BigInteger countryId;
	private BigInteger pincode;
	private BigInteger mobileNumber;
	private BigInteger alternativeNumber;
	private BigInteger aadharNumber;
	private BigInteger accountNumber;
	private BigInteger bankId;
	private String ifscCode;
	private boolean work;
	private boolean status;
	private String franchiseId;
	private String panNumber;
	private int profileId;
	private Integer roleId;
	private String emailId;
	private int inactiveMember;
	private int activeMember;
	private int totalMember;
	private int otStateId;
	private String fleetId;
	private String warehouseId;
	private String creationDate;
	private String modificationDate;
	private String password;
	private String confirmPassword;

	private int city;
	private int state;
	private int country;
	
	public EmployeeModel(String firstName, String lastName, String dob, int gender, String doorNumber, String street,
			BigInteger cityId, BigInteger stateId, BigInteger countryId, BigInteger pincode, BigInteger mobileNumber,
			BigInteger alternativeNumber, BigInteger aadharNumber, BigInteger accountNumber, String panNumber,
			int profileId, Integer roleId, String emailId, String password, String confirmPassword) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.gender = gender;
		this.doorNumber = doorNumber;
		this.street = street;
		this.cityId = cityId;
		this.stateId = stateId;
		this.countryId = countryId;
		this.pincode = pincode;
		this.mobileNumber = mobileNumber;
		this.alternativeNumber = alternativeNumber;
		this.aadharNumber = aadharNumber;
		this.accountNumber = accountNumber;
		this.panNumber = panNumber;
		this.profileId = profileId;
		this.roleId = roleId;
		this.emailId = emailId;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public EmployeeModel(String employeeId, int profileId) {
		this.employeeId = employeeId;
		this.profileId = profileId;
	}
	
	public EmployeeModel(String employeeId) {
		this.employeeId = employeeId;
	}

	public EmployeeModel() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
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

	public BigInteger getCityId() {
		return cityId;
	}

	public void setCityId(BigInteger cityId) {
		this.cityId = cityId;
	}

	public BigInteger getStateId() {
		return stateId;
	}

	public void setStateId(BigInteger stateId) {
		this.stateId = stateId;
	}

	public BigInteger getCountryId() {
		return countryId;
	}

	public void setCountryId(BigInteger countryId) {
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

	public BigInteger getAlternativeNumber() {
		return alternativeNumber;
	}

	public void setAlternativeNumber(BigInteger alternativeNumber) {
		this.alternativeNumber = alternativeNumber;
	}

	public BigInteger getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(BigInteger aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public BigInteger getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigInteger getBankId() {
		return bankId;
	}

	public void setBankId(BigInteger bankId) {
		this.bankId = bankId;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public boolean isWork() {
		return work;
	}

	public void setWork(boolean work) {
		this.work = work;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getInactiveMember() {
		return inactiveMember;
	}

	public void setInactiveMember(int inactiveMember) {
		this.inactiveMember = inactiveMember;
	}

	public int getActiveMember() {
		return activeMember;
	}

	public void setActiveMember(int activeMember) {
		this.activeMember = activeMember;
	}

	public int getTotalMember() {
		return totalMember;
	}

	public void setTotalMember(int totalMember) {
		this.totalMember = totalMember;
	}

	public int getOtStateId() {
		return otStateId;
	}

	public void setOtStateId(int otStateId) {
		this.otStateId = otStateId;
	}

	public String getFleetId() {
		return fleetId;
	}

	public void setFleetId(String fleetId) {
		this.fleetId = fleetId;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
