package com.atpl.mmg.model.specialvehicles;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class SpecialCategoryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6748592594181899972L;

	private Integer specialCategoryId;
	@Size(min = 2, max = 126, message = "special Category should between 2 to 127 characters")
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
