package com.atpl.mmg.AandA.model.profilerole;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ProfileRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1381151727495971296L;
	private String uuid;
	private int roleId;
	private String profileId;
	private String appTokenId;
	private String webTokenId;
	private boolean isActive;
	private String createdDate;
	private String modificationDate;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
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
	public boolean isIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}
}
