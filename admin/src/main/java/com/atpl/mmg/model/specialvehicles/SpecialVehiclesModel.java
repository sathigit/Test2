package com.atpl.mmg.model.specialvehicles;

import java.io.Serializable;
import java.math.BigInteger;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class SpecialVehiclesModel implements Serializable {

	private static final long serialVersionUID = 2360804721918919568L;

	public Integer vehicleCategoryId;
	public Integer specialCategoryId;
	@Size(min = 2, max = 126, message = "Model name should between 2 to 127 characters")
	public String model;
	public boolean status;
	@NotNull
	@Min(value = 1, message = "must be equal or greater than 1")
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
