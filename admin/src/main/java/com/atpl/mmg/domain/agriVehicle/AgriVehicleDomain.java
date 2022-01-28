package com.atpl.mmg.domain.agriVehicle;

import java.io.Serializable;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class AgriVehicleDomain implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2919150471776952992L;
	
	public Integer vehicleCategoryId;
	public String company;
	public String category;
	public Integer bookingTypeId;
	public boolean status;
	public Integer perHour;
	
	
	
	public Integer getPerHour() {
		return perHour;
	}
	public void setPerHour(Integer perHour) {
		this.perHour = perHour;
	}
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
