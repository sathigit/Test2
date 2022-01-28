package com.atpl.mmg.AandA.model.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordinatorDetail {
	private String coordinatorId;

	private String franchiseId;
	private Double latitude;
	private Double longitude;
	private Boolean isAgency;

	private Profile profile;
	
	public String getCoordinatorId() {
		return coordinatorId;
	}
	public void setCoordinatorId(String coordinatorId) {
		this.coordinatorId = coordinatorId;
	}

	public String getFranchiseId() {
		return franchiseId;
	}
	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
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
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
