package com.atpl.mmg.model.profile;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.atpl.mmg.model.bankaccount.BankAccountModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5908668469618466804L;
	
	//Profile basic info
	private String id;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String alternativeNumber;
	private String emailId;
	private int defaultRoleId;
	private int genderId;
	private Date dob;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String confirmPassword;
	private BigInteger aadharNumber;
	private String panNumber;
	private String profileSource;
	
	@JsonIgnore
	private Date createdDate;
	
	@JsonIgnore
	private Date modifiedDate;
	
	@JsonIgnoreProperties(value= {"profileId","createdDate","modifiedDate"})
	private List<AddressModel> address;
	
	@JsonIgnoreProperties(value= {"profileId","createdDate","modifiedDate"})
	private List<BankAccountModel> bankAccount;

	private FranchiseModel franchise;
	private DriverModel driver;
	private Coordinator coordinator;

	
	//Other fields 
	private int roleId;
	private Boolean isActive;
	private String appTokenId;
	private String webTokenId;
	private boolean otpIsActive;
	private boolean isChangePassword;
	private String oldPassword;
	private boolean isMigration;
	private String modifiedBy;
	private String reason;
	private int modifiedByRoleId;
	private boolean isRegisterByMmg;
	
	public boolean isOtpIsActive() {
		return otpIsActive;
	}
	public void setOtpIsActive(boolean otpIsActive) {
		this.otpIsActive = otpIsActive;
	}
	public String getAppTokenId() {
		return appTokenId;
	}
	public void setAppTokenId(String appTokenId) {
		this.appTokenId = appTokenId;
	}
	public String getWebTokenId() {
		return webTokenId;
	}
	public void setWebTokenId(String webTokenId) {
		this.webTokenId = webTokenId;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public int getDefaultRoleId() {
		return defaultRoleId;
	}
	public void setDefaultRoleId(int defaultRoleId) {
		this.defaultRoleId = defaultRoleId;
	}
	public int getGenderId() {
		return genderId;
	}
	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
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
	public BigInteger getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(BigInteger aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getProfileSource() {
		return profileSource;
	}
	public void setProfileSource(String profileSource) {
		this.profileSource = profileSource;
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
	public List<AddressModel> getAddress() {
		return address;
	}
	public void setAddress(List<AddressModel> address) {
		this.address = address;
	}
	public List<BankAccountModel> getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(List<BankAccountModel> bankAccount) {
		this.bankAccount = bankAccount;
	}
	public Coordinator getCoordinator() {
		return coordinator;
	}
	public void setCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
	}

	public boolean isIsChangePassword() {
		return isChangePassword;
	}
	public void setIsChangePassword(boolean isChangePassword) {
		this.isChangePassword = isChangePassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public boolean isIsMigration() {
		return isMigration;
	}
	public void setIsMigration(boolean isMigration) {
		this.isMigration = isMigration;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getModifiedByRoleId() {
		return modifiedByRoleId;
	}
	public void setModifiedByRoleId(int modifiedByRoleId) {
		this.modifiedByRoleId = modifiedByRoleId;
	}
	public boolean isIsRegisterByMmg() {
		return isRegisterByMmg;
	}
	public void setIsRegisterByMmg(boolean isRegisterByMmg) {
		this.isRegisterByMmg = isRegisterByMmg;
	}
	public FranchiseModel getFranchise() {
		return franchise;
	}
	public void setFranchise(FranchiseModel franchise) {
		this.franchise = franchise;
	}
	public DriverModel getDriver() {
		return driver;
	}
	public void setDriver(DriverModel driver) {
		this.driver = driver;
	}
	

}
