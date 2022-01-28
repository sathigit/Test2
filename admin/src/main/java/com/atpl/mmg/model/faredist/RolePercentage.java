package com.atpl.mmg.model.faredist;

import java.util.List;

public class RolePercentage {
	
	private List<Integer> role;
	private List<Double> percentage;
	private List<String> uuid;
	private List<Double> fixedCost;;
	
	
	public List<Integer> getRole() {
		return role;
	}
	public void setRole(List<Integer> role) {
		this.role = role;
	}
	public List<Double> getPercentage() {
		return percentage;
	}
	public void setPercentage(List<Double> percentage) {
		this.percentage = percentage;
	}
	public List<String> getUuid() {
		return uuid;
	}
	public void setUuid(List<String> uuid) {
		this.uuid = uuid;
	}
	public List<Double> getFixedCost() {
		return fixedCost;
	}
	public void setFixedCost(List<Double> fixedCost) {
		this.fixedCost = fixedCost;
	}
	
	

}
