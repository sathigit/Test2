package com.atpl.mmg.AandA.model.profile;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BDO {
	private String bdoId;
	private String franchiseId;
	private String franchiseName;
	private String bdmId;
	@JsonIgnore
	private String profileId;
	@JsonIgnore
	private Date createdDate;
	@JsonIgnore
	private Date modifiedDate;

	public String getBdmId() {
		return bdmId;
	}
	public void setBdmId(String bdmId) {
		this.bdmId = bdmId;
	}
	public String getBdoId() {
		return bdoId;
	}
	public void setBdoId(String bdoId) {
		this.bdoId = bdoId;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
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
	public String getFranchiseId() {
		return franchiseId;
	}
	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}
	public String getFranchiseName() {
		return franchiseName;
	}
	public void setFranchiseName(String franchiseName) {
		this.franchiseName = franchiseName;
	}
	
}
