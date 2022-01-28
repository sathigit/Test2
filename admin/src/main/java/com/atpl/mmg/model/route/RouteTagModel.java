package com.atpl.mmg.model.route;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RouteTagModel implements Serializable {

	private static final long serialVersionUID = -2196266153406082047L;

	private String uuid;
	private String vendorId;
	private Integer roleId;
	private String routeId;
	private String vehicleId;
	private String tagLocation;
	private String tagBy;
	private int tagByRoleId;
	private Date createdDate;
	private Date modifiedDate;
	private boolean status;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

}
