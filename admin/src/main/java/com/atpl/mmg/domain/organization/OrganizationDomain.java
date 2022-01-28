package com.atpl.mmg.domain.organization;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class OrganizationDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2196266153406082047L;
	
	public Integer id;
	public String name;
	
	
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
	
	
}