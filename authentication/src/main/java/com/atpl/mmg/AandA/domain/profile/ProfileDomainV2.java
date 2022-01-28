package com.atpl.mmg.AandA.domain.profile;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.atpl.mmg.AandA.domain.bankaccount.BankAccountDomain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ProfileDomainV2 {

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
	private String password;
	private String confirmPassword;
	private BigInteger aadharNumber;
	private String panNumber;
	private String profileSource;
	private Date createdDate;
	private Date modifiedDate;
	
	private List<AddressDomain> address;
	private List<BankAccountDomain> bankAccount;

	private FranchiseDomainV2 franchise;
	private FleetOperatorDomain fleet;
	private EnterpriseDomain enterprise;
	private WareHouseDomain warehouse;
	private CustomerDomain customer;
    private OperationalTeamDomain operationalTeam;
    private ChannelPartnerDomain channelPartner;
	private BDMDomain bdm;
	private CoordinatorDomain coordinator;
	private FieldOfficerDomain fieldOfficer;
	private BDODomain bdo;
	
	//Other columns	
	private int roleId;
	private Boolean isActive;
	private String appTokenId;
	private String webTokenId;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
	public List<AddressDomain> getAddress() {
		return address;
	}
	public void setAddress(List<AddressDomain> address) {
		this.address = address;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	public List<BankAccountDomain> getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(List<BankAccountDomain> bankAccount) {
		this.bankAccount = bankAccount;
	}
	public FranchiseDomainV2 getFranchise() {
		return franchise;
	}
	public void setFranchise(FranchiseDomainV2 franchise) {
		this.franchise = franchise;
	}
	public FleetOperatorDomain getFleet() {
		return fleet;
	}
	public void setFleet(FleetOperatorDomain fleet) {
		this.fleet = fleet;
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
	public CustomerDomain getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDomain customer) {
		this.customer = customer;
	}
	public OperationalTeamDomain getOperationalTeam() {
		return operationalTeam;
	}
	public void setOperationalTeam(OperationalTeamDomain operationalTeam) {
		this.operationalTeam = operationalTeam;
	}
	public ChannelPartnerDomain getChannelPartner() {
		return channelPartner;
	}
	public void setChannelPartner(ChannelPartnerDomain channelPartner) {
		this.channelPartner = channelPartner;
	}
	public BDMDomain getBdm() {
		return bdm;
	}
	public void setBdm(BDMDomain bdm) {
		this.bdm = bdm;
	}
	public CoordinatorDomain getCoordinator() {
		return coordinator;
	}
	public void setCoordinator(CoordinatorDomain coordinator) {
		this.coordinator = coordinator;
	}
	public FieldOfficerDomain getFieldOfficer() {
		return fieldOfficer;
	}
	public void setFieldOfficer(FieldOfficerDomain fieldOfficer) {
		this.fieldOfficer = fieldOfficer;
	}
	public BDODomain getBdo() {
		return bdo;
	}
	public void setBdo(BDODomain bdo) {
		this.bdo = bdo;
	}
	
}