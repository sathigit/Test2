package com.atpl.mmg.domain.blood;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class BloodDomain {

	private BigInteger bloodId;
	private String bloodName;
	private boolean status;
	private String createdDate;
	private String modifiedDate;

	public BigInteger getBloodId() {
		return bloodId;
	}

	public void setBloodId(BigInteger bloodId) {
		this.bloodId = bloodId;
	}

	public String getBloodName() {
		return bloodName;
	}

	public void setBloodName(String bloodName) {
		this.bloodName = bloodName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
