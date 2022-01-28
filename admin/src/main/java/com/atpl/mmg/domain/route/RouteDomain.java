package com.atpl.mmg.domain.route;

import java.io.Serializable;

import com.atpl.mmg.model.route.Address;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RouteDomain implements Serializable {
	
	private static final long serialVersionUID = -2196266153406082047L;

	private String uuid;
	private Address sourceAddress;
	private Address destinationAddress;
	private String source;
	private String destination;
	private String createdBy;
	private int roleId;
	private Boolean status;
	
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
	public Address getSourceAddress() {
		return sourceAddress;
	}
	public void setSourceAddress(Address sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	public Address getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(Address destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}


}
