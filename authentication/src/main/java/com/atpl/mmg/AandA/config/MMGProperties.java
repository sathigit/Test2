package com.atpl.mmg.AandA.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@ConfigurationProperties("MMG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MMGProperties {
	private String headerApiKey;
	private String accessTokenKey;
	private String bucketName;
	private String fleetUrl;
	private String adminUrl;
	private String franchiseUrl;
	private String fareUrl;
	private String accessKey;
	private String secretKey;
	private String enableSms;
	private String oauthUrl;
	private String oauthClientId;
	private String oauthClientSecret;
	private String skipToken;
	private int franchiseYearOfContract;
	private int otpExpiryInMinutes;
	private String profileSecHeaderKey;
	public String otpVisible;
	private String xApiSource;

	public String getHeaderApiKey() {
		return headerApiKey;
	}

	public void setHeaderApiKey(String headerApiKey) {
		this.headerApiKey = headerApiKey;
	}

	public String getAccessTokenKey() {
		return accessTokenKey;
	}

	public void setAccessTokenKey(String accessTokenKey) {
		this.accessTokenKey = accessTokenKey;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFleetUrl() {
		return fleetUrl;
	}

	public void setFleetUrl(String fleetUrl) {
		this.fleetUrl = fleetUrl;
	}

	public String getAdminUrl() {
		return adminUrl;
	}

	public void setAdminUrl(String adminUrl) {
		this.adminUrl = adminUrl;
	}

	public String getFranchiseUrl() {
		return franchiseUrl;
	}

	public void setFranchiseUrl(String franchiseUrl) {
		this.franchiseUrl = franchiseUrl;
	}

	public String getFareUrl() {
		return fareUrl;
	}

	public void setFareUrl(String fareUrl) {
		this.fareUrl = fareUrl;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getEnableSms() {
		return enableSms;
	}

	public void setEnableSms(String enableSms) {
		this.enableSms = enableSms;
	}

	public String getOauthUrl() {
		return oauthUrl;
	}

	public void setOauthUrl(String oauthUrl) {
		this.oauthUrl = oauthUrl;
	}

	public String getOauthClientId() {
		return oauthClientId;
	}

	public void setOauthClientId(String oauthClientId) {
		this.oauthClientId = oauthClientId;
	}

	public String getOauthClientSecret() {
		return oauthClientSecret;
	}

	public void setOauthClientSecret(String oauthClientSecret) {
		this.oauthClientSecret = oauthClientSecret;
	}

	public String getSkipToken() {
		return skipToken;
	}

	public void setSkipToken(String skipToken) {
		this.skipToken = skipToken;
	}

	public int getFranchiseYearOfContract() {
		return franchiseYearOfContract;
	}

	public void setFranchiseYearOfContract(int franchiseYearOfContract) {
		this.franchiseYearOfContract = franchiseYearOfContract;
	}

	public int getOtpExpiryInMinutes() {
		return otpExpiryInMinutes;
	}

	public void setOtpExpiryInMinutes(int otpExpiryInMinutes) {
		this.otpExpiryInMinutes = otpExpiryInMinutes;
	}

	public String getProfileSecHeaderKey() {
		return profileSecHeaderKey;
	}

	public void setProfileSecHeaderKey(String profileSecHeaderKey) {
		this.profileSecHeaderKey = profileSecHeaderKey;
	}

	public String getOtpVisible() {
		return otpVisible;
	}

	public void setOtpVisible(String otpVisible) {
		this.otpVisible = otpVisible;
	}

	public String getxApiSource() {
		return xApiSource;
	}

	public void setxApiSource(String xApiSource) {
		this.xApiSource = xApiSource;
	}

}
