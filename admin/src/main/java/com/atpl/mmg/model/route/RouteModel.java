package com.atpl.mmg.model.route;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RouteModel implements Serializable {

	private static final long serialVersionUID = -2196266153406082047L;

	private String uuid;
	private Address source;
	private Address destination;
//	private AddressDetailsModel source;
//	private AddressDetailsModel destination;
	
	private String createdBy;
	private int roleId;
	private Boolean status;
	private int taggedVendorsCount;
	private int taggedVehiclesCount;

	public RouteModel() {
		super();
	}

	public RouteModel(String uuid) {
		super();
		this.uuid = uuid;
	}

	public RouteModel(Address source, Address destination) {
		super();
		this.source = source;
		this.destination = destination;
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

//	public AddressDetailsModel getSource() {
//		return source;
//	}
//
//	public void setSource(AddressDetailsModel source) {
//		this.source = source;
//	}
//
//	public AddressDetailsModel getDestination() {
//		return destination;
//	}
//
//	public void setDestination(AddressDetailsModel destination) {
//		this.destination = destination;
//	}

	public Address getSource() {
		return source;
	}

	public void setSource(Address source) {
		this.source = source;
	}

	public Address getDestination() {
		return destination;
	}

	public void setDestination(Address destination) {
		this.destination = destination;
	}

	public int getTaggedVendorsCount() {
		return taggedVendorsCount;
	}

	public void setTaggedVendorsCount(int taggedVendorsCount) {
		this.taggedVendorsCount = taggedVendorsCount;
	}

	public int getTaggedVehiclesCount() {
		return taggedVehiclesCount;
	}

	public void setTaggedVehiclesCount(int taggedVehiclesCount) {
		this.taggedVehiclesCount = taggedVehiclesCount;
	}
	
}
