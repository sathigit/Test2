package com.atpl.mmg.model.packaging;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class PackagingModel {

	private BigInteger packagingId;
	private String packagingName;
	private boolean status;
	private String creationDate;
	private String modificationDate;

	public BigInteger getPackagingId() {
		return packagingId;
	}

	public void setPackagingId(BigInteger packagingId) {
		this.packagingId = packagingId;
	}

	public String getPackagingName() {
		return packagingName;
	}

	public void setPackagingName(String packagingName) {
		this.packagingName = packagingName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

}
