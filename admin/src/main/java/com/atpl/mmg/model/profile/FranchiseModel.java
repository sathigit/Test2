package com.atpl.mmg.model.profile;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class FranchiseModel {

	private String franchiseId;
	private String companyName;
	private String organisationType;
	private String address;
	private BigInteger countryId;
	private BigInteger stateId;
	private String cityId;
	private BigInteger mobileNumber;
	private BigInteger alternativeNumber;
	private String emailId;
	private String yearOfContract;
	private String password;
	private String confirmPassword;
	private String bankName;
	private BigInteger accountNumber;
	private String ifscCode;
	private String branchName;
	private String panNumber;
	private String gstNo;
	private String licenceNo;
	private String proprietorName;
	private boolean status;
	private int id;
	private String profileId;
	private Double latitude;
	private Double longitude;
	private int cityName;
	private String requestNumber;
	private int roleId;
	private String onboarding;
	private String inactiveReason;
	private int otStateId;

	private String roleName;
	private String status1;
	private String countryName;
	private String stateName;
	private String organisationName;
	private String bank;
	private String city;
	private Double sourcelatitude;
	private Double sourcelongitude;

	public FranchiseModel() {

	}

	public FranchiseModel(String franchiseId, String companyName) {
		this.franchiseId = franchiseId;
		this.companyName = companyName;

	}

	public Double getSourcelatitude() {
		return sourcelatitude;
	}

	public void setSourcelatitude(Double sourcelatitude) {
		this.sourcelatitude = sourcelatitude;
	}

	public Double getSourcelongitude() {
		return sourcelongitude;
	}

	public void setSourcelongitude(Double sourcelongitude) {
		this.sourcelongitude = sourcelongitude;
	}

	public FranchiseModel(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public void setStateId(BigInteger stateId) {
		this.stateId = stateId;
	}

	public String getOnboarding() {
		return onboarding;
	}

	public void setOnboarding(String onboarding) {
		this.onboarding = onboarding;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getYearOfContract() {
		return yearOfContract;
	}

	public String getOrganisationType() {
		return organisationType;
	}

	public void setOrganisationType(String organisationType) {
		this.organisationType = organisationType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setYearOfContract(String yearOfContract) {
		this.yearOfContract = yearOfContract;
	}

	public BigInteger getCountryId() {
		return countryId;
	}

	public void setCountryId(BigInteger countryId) {
		this.countryId = countryId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public String getProprietorName() {
		return proprietorName;
	}

	public void setProprietorName(String proprietorName) {
		this.proprietorName = proprietorName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigInteger getStateId() {
		return stateId;
	}

	public void setStatesId(BigInteger stateId) {
		this.stateId = stateId;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigInteger getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getCityName() {
		return cityName;
	}

	public void setCityName(int cityName) {
		this.cityName = cityName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getInactiveReason() {
		return inactiveReason;
	}

	public void setInactiveReason(String inactiveReason) {
		this.inactiveReason = inactiveReason;
	}

	public int getOtStateId() {
		return otStateId;
	}

	public void setOtStateId(int otStateId) {
		this.otStateId = otStateId;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
