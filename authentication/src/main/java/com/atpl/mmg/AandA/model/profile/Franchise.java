package com.atpl.mmg.AandA.model.profile;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Franchise {

	private String franchiseId;
	private String companyName;
	private int organisationType;
	private int yearOfContract;
	private String gstNo;
	private String licenseNo;
	private String proprietorName;
	@JsonIgnore
	private String profileId;
	private Double latitude;
	private Double longitude;
	private Date startDate;
	private Date endDate;
	private String boardingEnquiryId;
	private String modifiedBy;
	private String reason;
	private String channelPartnerId;

	// Other fields
	private String organizationName;
	private Boolean isTag;

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBoardingEnquiryId() {
		return boardingEnquiryId;
	}

	public void setBoardingEnquiryId(String boardingEnquiryId) {
		this.boardingEnquiryId = boardingEnquiryId;
	}

	public String getChannelPartnerId() {
		return channelPartnerId;
	}

	public void setChannelPartnerId(String channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getOrganisationType() {
		return organisationType;
	}

	public void setOrganisationType(int organisationType) {
		this.organisationType = organisationType;
	}

	public int getYearOfContract() {
		return yearOfContract;
	}

	public void setYearOfContract(int yearOfContract) {
		this.yearOfContract = yearOfContract;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getProprietorName() {
		return proprietorName;
	}

	public void setProprietorName(String proprietorName) {
		this.proprietorName = proprietorName;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
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

	public Boolean getIsTag() {
		return isTag;
	}

	public void setIsTag(Boolean isTag) {
		this.isTag = isTag;
	}

//	public boolean isIsTag() {
//		return isTag;
//	}
//
//	public void setIsTag(boolean isTag) {
//		this.isTag = isTag;
//	}

}
