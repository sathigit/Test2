package com.atpl.mmg.AandA.domain.profile;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.atpl.mmg.AandA.domain.bankaccount.BankAccountDomain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ProfileDomain {

	@Override
	public String toString() {
		return "ProfileDomain [id=" + id + ", totalCustomer=" + totalCustomer + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", middelName=" + middleName + ", roleId=" + roleId + ", stateId="
				+ stateId + ", cityId=" + cityId + ", countryId=" + countryId + ", doorNumber=" + doorNumber
				+ ", street=" + street + ", mobileNumber=" + mobileNumber + ", alternativeNumber=" + alternativeNumber
				+ ", emailId=" + emailId + ", password=" + password + ", confirmPassword=" + confirmPassword
				+ ", pincode=" + pincode + ", dob=" + dob + ", genderId=" + genderId + ", otp=" + otp + "]";
	}

	private int totalCustomer;
	private String id;
	private String firstName;
	private String lastName;
	private String middleName;
	@NotEmpty(message = "Mobile number cannot be empty")
	@NotNull(message = "Mobile number is a required field")
	private String mobileNumber;
	private BigInteger alternativeNumber;
	private String emailId;
	private int genderId;
	private Date dob;
	private String password;
	private String confirmPassword;
	private String doorNumber;
	private String street;
	private Integer cityId;
	private Integer stateId;
	private int countryId;
	private BigInteger pincode;
	private String refferCode;
	private boolean isFreqCustomer;
	private String profileSource;
	private Date createdDate;
	private Date modifiedDate;
	private int defaultRoleId;
	private String appTokenId;
	private String webTokenId;
	private String franchiseId;
	private String fleetId;
	private String wareHouseId;
	private String bdmId;
	private String cpId;
	private String link;

	private int roleId;
	private String otp;
	private String tokenId;
	private String webToken;
	private String countryName;
	private String stateName;
	private String cityName;
	private boolean otpIsActive;
	private String oldPassword;
	private String panNumber;
	private BigInteger accountNumber;
	private BigInteger aadharNumber;
	private String gstNo;
	private double credit;
	private boolean frequentCustomer;
	private boolean enrolledCrm;
	private String action;
	private boolean isTermsAndCondition;
	private String termsAndConditionsId;
	private String enterpriseId;
	
	private Boolean isActive;
	
	private List<BankAccountDomain> bankAccount;

	private FranchiseDomainV2 franchise;
	
	private FleetOperatorDomain fleet;
	
	private EnterpriseDomain enterprise;
	
	private WareHouseDomain warehouse;
	
	
	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public EnterpriseDomain getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(EnterpriseDomain enterprise) {
		this.enterprise = enterprise;
	}

	public WareHouseDomain getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(WareHouseDomain warehouse) {
		this.warehouse = warehouse;
	}

	public FleetOperatorDomain getFleet() {
		return fleet;
	}

	public void setFleet(FleetOperatorDomain fleet) {
		this.fleet = fleet;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public FranchiseDomainV2 getFranchise() {
		return franchise;
	}

	public void setFranchise(FranchiseDomainV2 franchise) {
		this.franchise = franchise;
	}

	public List<BankAccountDomain> getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(List<BankAccountDomain> bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public int getDefaultRoleId() {
		return defaultRoleId;
	}

	public void setDefaultRoleId(int defaultRoleId) {
		this.defaultRoleId = defaultRoleId;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getFleetId() {
		return fleetId;
	}

	public void setFleetId(String fleetId) {
		this.fleetId = fleetId;
	}

	public String getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(String wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public String getBdmId() {
		return bdmId;
	}

	public void setBdmId(String bdmId) {
		this.bdmId = bdmId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public boolean getIsFreqCustomer() {
		return isFreqCustomer;
	}

	public void setIsFreqCustomer(boolean isFreqCustomer) {
		this.isFreqCustomer = isFreqCustomer;
	}

	public String getProfileSource() {
		return profileSource;
	}

	public void setProfileSource(String profileSource) {
		this.profileSource = profileSource;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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

	public boolean isEnrolledCrm() {
		return enrolledCrm;
	}

	public void setEnrolledCrm(boolean enrolledCrm) {
		this.enrolledCrm = enrolledCrm;
	}

	public ProfileDomain() {
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public int getTotalCustomer() {
		return totalCustomer;
	}

	public void setTotalCustomer(int totalCustomer) {
		this.totalCustomer = totalCustomer;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public int getGenderId() {
		return genderId;
	}

	public void setGenderId(int genderId) {
		this.genderId = genderId;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
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

	public String getWebToken() {
		return webToken;
	}

	public void setWebToken(String webToken) {
		this.webToken = webToken;
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

	public boolean isFrequentCustomer() {
		return frequentCustomer;
	}

	public void setFrequentCustomer(boolean frequentCustomer) {
		this.frequentCustomer = frequentCustomer;
	}
	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public boolean isIsTermsAndCondition() {
		return isTermsAndCondition;
	}

	public void setIsTermsAndCondition(boolean isTermsAndCondition) {
		this.isTermsAndCondition = isTermsAndCondition;
	}

	public String getTermsAndConditionsId() {
		return termsAndConditionsId;
	}

	public void setTermsAndConditionsId(String termsAndConditionsId) {
		this.termsAndConditionsId = termsAndConditionsId;
	}

}