package com.atpl.mmg.domain.specialvehicles;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class SpecialCategoryDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9214577333673656830L;
	
	private Integer specialCategoryId;
	private String specialCategory;
	public boolean status;
	
	
	public Integer getSpecialCategoryId() {
		return specialCategoryId;
	}
	public void setSpecialCategoryId(Integer specialCategoryId) {
		this.specialCategoryId = specialCategoryId;
	}
	public String getSpecialCategory() {
		return specialCategory;
	}
	public void setSpecialCategory(String specialCategory) {
		this.specialCategory = specialCategory;
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
