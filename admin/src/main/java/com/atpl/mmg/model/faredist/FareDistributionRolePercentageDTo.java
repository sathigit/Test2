package com.atpl.mmg.model.faredist;

import java.util.List;

import com.atpl.mmg.model.faredisttype.FareDistributionTypeModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class FareDistributionRolePercentageDTo {
	private List<RolePercentageDTo> roleBasedDistribution;
	private String fareDistributionTypeId;
	private Boolean isPercentage;
	private Boolean isActive;
	private FareDistributionTypeModel fareDistributionType;

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

	public List<RolePercentageDTo> getRoleBasedDistribution() {
		return roleBasedDistribution;
	}

	public void setRoleBasedDistribution(List<RolePercentageDTo> roleBasedDistribution) {
		this.roleBasedDistribution = roleBasedDistribution;
	}



}
