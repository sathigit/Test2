package com.atpl.mmg.model.route;

import java.io.Serializable;
import java.util.List;

import com.atpl.mmg.model.profile.Profile;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class VendorDetails implements Serializable {

	private static final long serialVersionUID = -2196266153406082047L;

	private String vendorId;
	private String vendorName;
	private String vendorRole;

	public VendorDetails() {
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorRole() {
		return vendorRole;
	}

	public void setVendorRole(String vendorRole) {
		this.vendorRole = vendorRole;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

}
