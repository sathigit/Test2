package com.atpl.mmg.domain.specialvehicles;

import java.io.Serializable;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class SpecialVehiclesDomain implements Serializable {
	
	private static final long serialVersionUID = 2360804721918919568L;

	public Integer vehicleCategoryId;
	public Integer specialCategoryId;
	public String model;
	public boolean status;
	public BigInteger perHour;
	public Integer getVehicleCategoryId() {
		return vehicleCategoryId;
	}
	public void setVehicleCategoryId(Integer vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}
	public Integer getSpecialCategoryId() {
		return specialCategoryId;
	}
	public void setSpecialCategoryId(Integer specialCategoryId) {
		this.specialCategoryId = specialCategoryId;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public BigInteger getPerHour() {
		return perHour;
	}
	public void setPerHour(BigInteger perHour) {
		this.perHour = perHour;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
