package com.atpl.mmg.model.agriVehicle;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class AgriVehicleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2360804721918919568L;

	public Integer vehicleCategoryId;
	@Size(min = 2, max = 126, message = "Comapany name should between 2 to 127 characters")
	public String company;
	@Size(min = 2, max = 126, message = "category name should between 2 to 127 characters")
	public String category;
	public Integer bookingTypeId;
	public boolean status;
	@NotNull
	@Min(value = 1, message = "must be equal or greater than 1")
	public Integer perHour;

	public Integer getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(Integer vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getBookingTypeId() {
		return bookingTypeId;
	}

	public void setBookingTypeId(Integer bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getPerHour() {
		return perHour;
	}

	public void setPerHour(Integer perHour) {
		this.perHour = perHour;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
