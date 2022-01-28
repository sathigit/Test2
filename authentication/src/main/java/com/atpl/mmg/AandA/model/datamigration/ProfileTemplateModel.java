package com.atpl.mmg.AandA.model.datamigration;

import java.math.BigInteger;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.atpl.mmg.AandA.model.bankaccount.BankAccountModel;
import com.atpl.mmg.AandA.model.profile.Driver;
import com.atpl.mmg.AandA.model.profile.Franchise;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileTemplateModel {
	private int id;
	private String firstName;
	private String lastName;
	private String middleName;
	private int roleId;
	private Integer stateId;
	private Integer cityId;
	private int countryId;
	private String countryName;
	private String stateName;
	private String cityName;
	private String doorNumber;
	private String street;
	@NotEmpty(message = "Mobile number cannot be empty")
	@NotNull(message = "Mobile number is a required field")
	private String mobileNumber;
	private String alternativeNumber;
	private String emailId;
	private String password;
	private String confirmPassword;
	private BigInteger pincode;
	private Date dob;
	private int genderId;
	private String otp;
	private boolean status;
	private String tokenId;
	private String webToken;

	private boolean otpIsActive;
	private String oldPassword;

	private String panNumber;
	private BigInteger accountNumber;
	private BigInteger aadharNumber;
	private String gstNo;
	private int customerId;
	private String refferCode;
	private double credit;
	private int profileId;
	private boolean frequentCustomer;
	private Date creationDate;
	private Date modificationDate;
	private boolean enrolledCrm;
	private String action;
	private boolean isTermsAndCondition;
	private String termsAndConditionsId;
	private Franchise franchise;
	private BankAccountModel bank;
	
	private Driver driver;

	public ProfileTemplateModel() {
	}

	public ProfileTemplateModel(String firstName, String lastName, String middleName, int roleId, Integer stateId,
			Integer cityId, int countryId, String doorNumber, String street, String mobileNumber,
			String alternativeNumber, String emailId, String password, String confirmPassword, BigInteger pincode,
			Date dob, int genderId, String tokenId, String webToken, String termsAndConditionsId, Date creationDate,
			Date modificationDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.roleId = roleId;
		this.stateId = stateId;
		this.cityId = cityId;
		this.countryId = countryId;
		this.doorNumber = doorNumber;
		this.street = street;
		this.mobileNumber = mobileNumber;
		this.alternativeNumber = alternativeNumber;
		this.emailId = emailId;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.pincode = pincode;
		this.dob = dob;
		this.genderId = genderId;
		this.tokenId = tokenId;
		this.webToken = webToken;
		this.termsAndConditionsId = termsAndConditionsId;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
	}

	public ProfileTemplateModel(String firstName, String lastName, String middleName, int roleId, Integer stateId,
			Integer cityId, int countryId, String doorNumber, String street, String mobileNumber,
			String alternativeNumber, String emailId, String password, String confirmPassword, BigInteger pincode,
			Date dob, int genderId, String tokenId, String webToken, Date creationDate, Date modificationDate,
			boolean status, String panNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.roleId = roleId;
		this.stateId = stateId;
		this.cityId = cityId;
		this.countryId = countryId;
		this.doorNumber = doorNumber;
		this.street = street;
		this.mobileNumber = mobileNumber;
		this.alternativeNumber = alternativeNumber;
		this.emailId = emailId;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.pincode = pincode;
		this.dob = dob;
		this.genderId = genderId;
		this.tokenId = tokenId;
		this.webToken = webToken;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.status = status;
		this.panNumber = panNumber;
	}

	
	public ProfileTemplateModel(String firstName, String lastName, String middleName, int roleId, Integer stateId,
			Integer cityId, int countryId, String doorNumber, String street, String mobileNumber,
			String alternativeNumber, String emailId, String password, String confirmPassword, BigInteger pincode,
			Date dob, int genderId, String tokenId, String webToken, Date creationDate, Date modificationDate,
			boolean status, String panNumber,BigInteger aadharNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.roleId = roleId;
		this.stateId = stateId;
		this.cityId = cityId;
		this.countryId = countryId;
		this.doorNumber = doorNumber;
		this.street = street;
		this.mobileNumber = mobileNumber;
		this.alternativeNumber = alternativeNumber;
		this.emailId = emailId;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.pincode = pincode;
		this.dob = dob;
		this.genderId = genderId;
		this.tokenId = tokenId;
		this.webToken = webToken;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.status = status;
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAlternativeNumber() {
		return alternativeNumber;
	}

	public void setAlternativeNumber(String alternativeNumber) {
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public BigInteger getPincode() {
		return pincode;
	}

	public void setPincode(BigInteger pincode) {
		this.pincode = pincode;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getGenderId() {
		return genderId;
	}

	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getWebToken() {
		return webToken;
	}

	public void setWebToken(String webToken) {
		this.webToken = webToken;
	}

	public boolean isOtpIsActive() {
		return otpIsActive;
	}

	public void setOtpIsActive(boolean otpIsActive) {
		this.otpIsActive = otpIsActive;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public BigInteger getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigInteger getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(BigInteger aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getRefferCode() {
		return refferCode;
	}

	public void setRefferCode(String refferCode) {
		this.refferCode = refferCode;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public boolean isFrequentCustomer() {
		return frequentCustomer;
	}

	public void setFrequentCustomer(boolean frequentCustomer) {
		this.frequentCustomer = frequentCustomer;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public boolean isEnrolledCrm() {
		return enrolledCrm;
	}

	public void setEnrolledCrm(boolean enrolledCrm) {
		this.enrolledCrm = enrolledCrm;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isTermsAndCondition() {
		return isTermsAndCondition;
	}

	public void setTermsAndCondition(boolean isTermsAndCondition) {
		this.isTermsAndCondition = isTermsAndCondition;
	}

	public String getTermsAndConditionsId() {
		return termsAndConditionsId;
	}

	public void setTermsAndConditionsId(String termsAndConditionsId) {
		this.termsAndConditionsId = termsAndConditionsId;
	}

	public Franchise getFranchise() {
		return franchise;
	}

	public void setFranchise(Franchise franchise) {
		this.franchise = franchise;
	}

	public BankAccountModel getBank() {
		return bank;
	}

	public void setBank(BankAccountModel bank) {
		this.bank = bank;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

}
