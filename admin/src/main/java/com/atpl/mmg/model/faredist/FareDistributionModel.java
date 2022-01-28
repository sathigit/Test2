package com.atpl.mmg.model.faredist;

import java.util.List;

import com.atpl.mmg.model.faredisttype.FareDistributionTypeModel;
import com.atpl.mmg.model.profile.RoleModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class FareDistributionModel {

	private String franchiseId;
	private List<FareDistributionRolePercentage> fareDistribution;
	private String fareDistributionTypeId;
	private int roleId;
	private Boolean isPercentage;
	private Boolean isActive;
	private FareDistributionTypeModel fareDistributionType;
	private RoleModel role;

	public FareDistributionModel() {

	}

	public String getFareDistributionTypeId() {
		return fareDistributionTypeId;
	}

	public void setFareDistributionTypeId(String fareDistributionTypeId) {
		this.fareDistributionTypeId = fareDistributionTypeId;
	}

	public Boolean getIsPercentage() {
		return isPercentage;
	}

	public void setIsPercentage(Boolean isPercentage) {
		this.isPercentage = isPercentage;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public List<FareDistributionRolePercentage> getFareDistribution() {
		return fareDistribution;
	}

	public void setFareDistribution(List<FareDistributionRolePercentage> fareDistribution) {
		this.fareDistribution = fareDistribution;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public FareDistributionTypeModel getFareDistributionType() {
		return fareDistributionType;
	}

	public void setFareDistributionType(FareDistributionTypeModel fareDistributionType) {
		this.fareDistributionType = fareDistributionType;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
}
