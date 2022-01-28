package com.atpl.mmg.domain.faredist;

import java.util.Date;
import java.util.List;

import com.atpl.mmg.model.faredist.FareDistributionRolePercentage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class FareDistributionDomain {
	private String uuid;
	private String name;
	private String franchiseId;
	private int roleId;
	private String fareDistributionTypeId;
	private double percentage;
	private double fixedCost;
	private boolean isPercentage;
	private boolean isActive;
	private Date createdDate;
	private Date modifiedDate;
	private String role;
	private String percentages;
	private String isPercentages;
	private String uuids;
	private String isActives;
	private String fixedCosts;
	private boolean status;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getFareDistributionTypeId() {
		return fareDistributionTypeId;
	}

	public void setFareDistributionTypeId(String fareDistributionTypeId) {
		this.fareDistributionTypeId = fareDistributionTypeId;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getFixedCost() {
		return fixedCost;
	}

	public void setFixedCost(double fixedCost) {
		this.fixedCost = fixedCost;
	}

	public boolean isIsPercentage() {
		return isPercentage;
	}

	public void setIsPercentage(boolean isPercentage) {
		this.isPercentage = isPercentage;
	}

	public boolean isIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPercentages() {
		return percentages;
	}

	public void setPercentages(String percentages) {
		this.percentages = percentages;
	}

	public String getIsPercentages() {
		return isPercentages;
	}

	public void setIsPercentages(String isPercentages) {
		this.isPercentages = isPercentages;
	}

	public String getUuids() {
		return uuids;
	}

	public void setUuids(String uuids) {
		this.uuids = uuids;
	}

	public String getIsActives() {
		return isActives;
	}

	public void setIsActives(String isActives) {
		this.isActives = isActives;
	}

	public String getFixedCosts() {
		return fixedCosts;
	}

	public void setFixedCosts(String fixedCosts) {
		this.fixedCosts = fixedCosts;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
