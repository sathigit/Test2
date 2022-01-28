package com.atpl.mmg.model.driverType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class DriverTypeModel {

	private int id;
	private String driverType;
	private String licenceCategoryName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLicenceCategoryName() {
		return licenceCategoryName;
	}
	public void setLicenceCategoryName(String licenceCategoryName) {
		this.licenceCategoryName = licenceCategoryName;
	}
	public String getDriverType() {
		return driverType;
	}
	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}
	
}
