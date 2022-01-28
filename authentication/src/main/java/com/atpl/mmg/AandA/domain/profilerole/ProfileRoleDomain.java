package com.atpl.mmg.AandA.domain.profilerole;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class ProfileRoleDomain {
	private String uuid;
	private int roleId;
	private String profileId;
	private String appTokenId;
	private String webTokenId;
	private boolean isActive;
	private String createdDate;
	private String modifiedDate;
	
	private String roleName;
	
	
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

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
