package com.atpl.mmg.AandA.model.faredist;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class FareDistributionDTo {
	private String franchiseId;
	private List<FareDistributionRolePercentageDTo> fareDistribution;

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public List<FareDistributionRolePercentageDTo> getFareDistribution() {
		return fareDistribution;
	}

	public void setFareDistribution(List<FareDistributionRolePercentageDTo> fareDistribution) {
		this.fareDistribution = fareDistribution;
	}

	

}
