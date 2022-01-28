package com.atpl.mmg.AandA.model.login;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class LoginModel {

	private String userName;
	private String password;
	private int roleId;
	private String roleName;
	private BigInteger sessionId;
	private String accessToken;
	private String tokenExpires;
	private String refreshToken;
	private String path;
	private boolean isActive;
	private String id;
	private int total;
	
	
	private String firstName;
	private String emailId;
	private String mobileNumber;
	private BigInteger permissionId;
	private String permissionName;
	private int userId;
	private Date lastAccessTime;
	private int otp;
	private String profileId;
	private boolean status;
	private int proleId;
	private boolean frequentCustomer;
	private boolean user;
	
	
	
	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public BigInteger getSessionId() {
		return sessionId;
	}

	public void setSessionId(BigInteger sessionId) {
		this.sessionId = sessionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(BigInteger permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getProleId() {
		return proleId;
	}

	public void setProleId(int proleId) {
		this.proleId = proleId;
	}

	public boolean isFrequentCustomer() {
		return frequentCustomer;
	}

	public void setFrequentCustomer(boolean frequentCustomer) {
		this.frequentCustomer = frequentCustomer;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenExpires() {
		return tokenExpires;
	}

	public void setTokenExpires(String tokenExpires) {
		this.tokenExpires = tokenExpires;
	}

	/**
	 * @return the user
	 */
	public boolean isUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(boolean user) {
		this.user = user;
	}


	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
}
