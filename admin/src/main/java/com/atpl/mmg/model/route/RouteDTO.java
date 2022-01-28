package com.atpl.mmg.model.route;

import java.io.Serializable;
import java.util.List;

import com.atpl.mmg.model.profile.Profile;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RouteDTO implements Serializable {

	private static final long serialVersionUID = -2196266153406082047L;

	private String uuid;
	private AddressDetailsModel source;
	private AddressDetailsModel destination;
	private String createdBy;
	private int roleId;
	private Boolean status;

	public RouteDTO() {
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public AddressDetailsModel getSource() {
		return source;
	}

	public void setSource(AddressDetailsModel source) {
		this.source = source;
	}

	public AddressDetailsModel getDestination() {
		return destination;
	}

	public void setDestination(AddressDetailsModel destination) {
		this.destination = destination;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
