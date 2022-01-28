package com.atpl.mmg.AandA.domain.profile;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordinatorDomain {
	
	private String coordinatorId;
	private String profileId;
	private String franchiseId;
	private Double latitude;
	private Double longitude;
	private Boolean isAgency;
	private Date createdDate;
	private Date modifiedDate;
	
	public String getCoordinatorId() {
		return coordinatorId;
	}
	public void setCoordinatorId(String coordinatorId) {
		this.coordinatorId = coordinatorId;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getFranchiseId() {
		return franchiseId;
	}
	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
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
	public Boolean getIsAgency() {
		return isAgency;
	}
	public void setIsAgency(Boolean isAgency) {
		this.isAgency = isAgency;
	}
	
}
