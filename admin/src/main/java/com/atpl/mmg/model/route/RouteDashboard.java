package com.atpl.mmg.model.route;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RouteDashboard { 
	
	private int totalTrips;
	private double totalWeight;
	private double totalEarnings;
	public int getTotalTrips() {
		return totalTrips;
	}
	public void setTotalTrips(int totalTrips) {
		this.totalTrips = totalTrips;
	}
	public double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public double getTotalEarnings() {
		return totalEarnings;
	}
	public void setTotalEarnings(double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}
	
	

}
