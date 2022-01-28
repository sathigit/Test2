package com.atpl.mmg.AandA.domain.boardingRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnquiryDashboardDomain {

	private int franchise;
	private int fleet;
	private int warehouse;
	private int enterprise;
	
	public int getFranchise() {
		return franchise;
	}
	public void setFranchise(int franchise) {
		this.franchise = franchise;
	}
	public int getFleet() {
		return fleet;
	}
	public void setFleet(int fleet) {
		this.fleet = fleet;
	}
	public int getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(int warehouse) {
		this.warehouse = warehouse;
	}
	public int getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(int enterprise) {
		this.enterprise = enterprise;
	}
	
	
}
