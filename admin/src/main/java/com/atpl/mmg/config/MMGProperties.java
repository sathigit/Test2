package com.atpl.mmg.config;

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
	private String imagePath;
	private int apiSessionTime;
	private String fromMail;
	private String enquiryMail;
	private String fleetUrl;
	private String authUrl;
	private String franchiseUrl;
	private String fareUrl;
	private String customerUrl;
	private String accessTokenKey;
	private String accessKey;
	private String secretKey;
	private String enableSms;
	private String oauthUrl;
	private String skipToken;
	private String masterBucket;
	private String bucketName;

	public String getHeaderApiKey() {
		return headerApiKey;
	}

	public void setHeaderApiKey(String headerApiKey) {
		this.headerApiKey = headerApiKey;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getApiSessionTime() {
		return apiSessionTime;
	}

	public void setApiSessionTime(int apiSessionTime) {
		this.apiSessionTime = apiSessionTime;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getEnquiryMail() {
		return enquiryMail;
	}

	public void setEnquiryMail(String enquiryMail) {
		this.enquiryMail = enquiryMail;
	}

	public String getFleetUrl() {
		return fleetUrl;
	}

	public void setFleetUrl(String fleetUrl) {
		this.fleetUrl = fleetUrl;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
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

	public String getCustomerUrl() {
		return customerUrl;
	}

	public void setCustomerUrl(String customerUrl) {
		this.customerUrl = customerUrl;
	}

	public String getAccessTokenKey() {
		return accessTokenKey;
	}

	public void setAccessTokenKey(String accessTokenKey) {
		this.accessTokenKey = accessTokenKey;
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

	public String getSkipToken() {
		return skipToken;
	}

	public void setSkipToken(String skipToken) {
		this.skipToken = skipToken;
	}

	public String getMasterBucket() {
		return masterBucket;
	}

	public void setMasterBucket(String masterBucket) {
		this.masterBucket = masterBucket;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

}
