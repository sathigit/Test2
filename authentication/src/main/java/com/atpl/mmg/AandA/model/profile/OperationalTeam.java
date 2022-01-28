package com.atpl.mmg.AandA.model.profile;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationalTeam {
	private String operationalTeamId;
	@JsonIgnore
	private String profileId;
	private int assignedStateId;
	@JsonIgnore
	private Date createdDate;
	@JsonIgnore
	private Date modifiedDate;
	
	public String getOperationalTeamId() {
		return operationalTeamId;
	}
	public void setOperationalTeamId(String operationalTeamId) {
		this.operationalTeamId = operationalTeamId;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public int getAssignedStateId() {
		return assignedStateId;
	}
	public void setAssignedStateId(int assignedStateId) {
		this.assignedStateId = assignedStateId;
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
	
}
