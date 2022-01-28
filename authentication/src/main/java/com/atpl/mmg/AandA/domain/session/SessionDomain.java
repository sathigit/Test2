package com.atpl.mmg.AandA.domain.session;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class SessionDomain implements Serializable{

	private static final long serialVersionUID = -6533408917283148542L;
	private BigInteger sessionId;
	private String userId;
	private int roleId;
	private Date lastAccessTime;
	private String emailId;
	private boolean isActive;
	private BigInteger mobileNumber;
	private String accessTime;
	private String firstName;
	private int cityId;
	private String accessToken;
	
	public SessionDomain() {

	}
	public SessionDomain(String userId) {
		this.userId = userId;
	}

	public SessionDomain(String userId, BigInteger mobileNumber, String accessTime, int cityId,String firstName) {
		this.userId = userId;
		this.mobileNumber = mobileNumber;
		this.accessTime = accessTime;
		this.cityId = cityId;
		this.firstName = firstName;
	}

	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BigInteger getSessionId() {
		return sessionId;
	}

	public void setSessionId(BigInteger sessionId) {
		this.sessionId = sessionId;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public boolean isIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public BigInteger getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(BigInteger mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
