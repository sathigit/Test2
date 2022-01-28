package com.atpl.mmg.model.goodstype;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class GoodstypeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2196266153406082047L;

	public Integer id;
	public String name;
	private boolean isProfitable;
	private boolean status;
    private String creationDate;
    private String modificationDate;


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isProfitable() {
		return isProfitable;
	}

	public void setProfitable(boolean isProfitable) {
		this.isProfitable = isProfitable;
	}

}