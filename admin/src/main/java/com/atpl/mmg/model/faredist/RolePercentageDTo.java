package com.atpl.mmg.model.faredist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RolePercentageDTo {
	private int roleId;
	private String roleName;
	private String uuid;
	private double percentage;
	private double fixedCost;

	public RolePercentageDTo() {

	}

	public RolePercentageDTo(int roleId, String roleName, double percentage, String uuid) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.percentage = percentage;
		this.uuid = uuid;
	}

	public RolePercentageDTo(int roleId, String roleName, String uuid) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.uuid = uuid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public double getFixedCost() {
		return fixedCost;
	}

	public void setFixedCost(double fixedCost) {
		this.fixedCost = fixedCost;
	}

}
