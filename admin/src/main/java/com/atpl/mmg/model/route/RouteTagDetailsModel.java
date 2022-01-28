package com.atpl.mmg.model.route;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RouteTagDetailsModel implements Serializable {

	private static final long serialVersionUID = -2196266153406082047L;

	private String uuid;
	private RouteModel routeDetails;
	private VendorDetails vendorDetails;
	private String vehicleId;
	private Integer roleId;
	private String tagLocation;
	private String tagBy;
	private int tagByRoleId;
	private Boolean status;

	public RouteTagDetailsModel() {
		super();
	}

	public RouteTagDetailsModel(String vehicleId, VendorDetails vendorDetails, String tagLocation, boolean status) {
		super();
		this.vehicleId = vehicleId;
		this.vendorDetails = vendorDetails;
		this.tagLocation = tagLocation;
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public RouteModel getRouteDetails() {
		return routeDetails;
	}

	public void setRouteDetails(RouteModel routeDetails) {
		this.routeDetails = routeDetails;
	}

	public VendorDetails getVendorDetails() {
		return vendorDetails;
	}

	public void setVendorDetails(VendorDetails vendorDetails) {
		this.vendorDetails = vendorDetails;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getTagLocation() {
		return tagLocation;
	}

	public void setTagLocation(String tagLocation) {
		this.tagLocation = tagLocation;
	}

	public String getTagBy() {
		return tagBy;
	}

	public void setTagBy(String tagBy) {
		this.tagBy = tagBy;
	}

	public int getTagByRoleId() {
		return tagByRoleId;
	}

	public void setTagByRoleId(int tagByRoleId) {
		this.tagByRoleId = tagByRoleId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}



}
