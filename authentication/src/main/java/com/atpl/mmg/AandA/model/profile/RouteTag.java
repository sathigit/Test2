package com.atpl.mmg.AandA.model.profile;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RouteTag  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String vendorId;
	private int vendorRoleId;

	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public int getVendorRoleId() {
		return vendorRoleId;
	}
	public void setVendorRoleId(int vendorRoleId) {
		this.vendorRoleId = vendorRoleId;
	}

	
}
