package com.atpl.mmg.model.faredist;

public class FareDistributionRolePercentage {

	private double[][] roleBasedDistribution;
	private String fareDistributionTypeId;
	private Boolean isPercentage;

	public FareDistributionRolePercentage() {

	}

	public String getFareDistributionTypeId() {
		return fareDistributionTypeId;
	}

	public void setFareDistributionTypeId(String fareDistributionTypeId) {
		this.fareDistributionTypeId = fareDistributionTypeId;
	}

	public Boolean isIsPercentage() {
		return isPercentage;
	}

	public void setIsPercentage(Boolean isIsPercentage) {
		this.isPercentage = isIsPercentage;
	}

	public double[][] getRoleBasedDistribution() {
		return roleBasedDistribution;
	}

	public void setRoleBasedDistribution(double[][] roleBasedDistribution) {
		this.roleBasedDistribution = roleBasedDistribution;
	}

}
