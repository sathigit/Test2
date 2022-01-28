package com.atpl.mmg.model.route;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RouteDetailsModel {

	private static final long serialVersionUID = -2196266153406082047L;

	private String uuid;
	private AddressDetailsModel source;
	private AddressDetailsModel destination;
	private String createdBy;
	private int roleId;
	private Boolean status;

	public RouteDetailsModel() {
		super();
	}

	public RouteDetailsModel(String uuid) {
		super();
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	
	
}
