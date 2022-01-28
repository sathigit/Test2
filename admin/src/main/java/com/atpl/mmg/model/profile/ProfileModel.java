package com.atpl.mmg.model.profile;

import java.util.List;

import com.atpl.mmg.model.bankaccount.BankAccountModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ProfileModel {

	private String id;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String emailId;
	private String dob;
	private int genderId;
	private boolean status;
	private String appTokenId;
	private String webTokenId;
	private String profileId;
	private int roleId;
	private int defaultRoleId;

	private List<AddressModel> address;
	private FranchiseModel franchise;
	private List<BankAccountModel> bankAccount;

	private DriverModel driver;
	
	private Coordinator Coordinator;


	// other fields
	private String addr;
	
	//Other fields
	private AddressModel addressObject;

	public ProfileModel() {

	}

	public ProfileModel(String firstName, String lastName, String mobileNumber, String emailId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
	}

	public ProfileModel(String firstName, String lastName, String mobileNumber, String emailId,
			FranchiseModel franchise, String addr, DriverModel driver) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.franchise = franchise;
		this.addr = addr;
		this.driver = driver;
	}

	public ProfileModel(String firstName, String lastName, String mobileNumber, String emailId,
			FranchiseModel franchise) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.franchise = franchise;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public List<AddressModel> getAddress() {
		return address;
	}

	public void setAddress(List<AddressModel> address) {
		this.address = address;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public int getGenderId() {
		return genderId;
	}

	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public FranchiseModel getFranchise() {
		return franchise;
	}

	public void setFranchise(FranchiseModel franchise) {
		this.franchise = franchise;
	}

	public List<BankAccountModel> getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(List<BankAccountModel> bankAccount) {
		this.bankAccount = bankAccount;
	}

	public int getDefaultRoleId() {
		return defaultRoleId;
	}

	public void setDefaultRoleId(int defaultRoleId) {
		this.defaultRoleId = defaultRoleId;
	}

	public DriverModel getDriver() {
		return driver;
	}

	public void setDriver(DriverModel driver) {
		this.driver = driver;
	}

	public AddressModel getAddressObject() {
		return addressObject;
	}

	public void setAddressObject(AddressModel addressObject) {
		this.addressObject = addressObject;
	}
	
	
}
