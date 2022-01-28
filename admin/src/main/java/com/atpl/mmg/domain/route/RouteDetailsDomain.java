package com.atpl.mmg.domain.route;

import com.atpl.mmg.model.route.AddressDetailsModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RouteDetailsDomain {

	private static final long serialVersionUID = -2196266153406082047L;

	private String uuid;
	private AddressDetailsModel sourceAddress;
	private AddressDetailsModel destinationAddress;

	private String source;
	private String destination;
	private String createdBy;
	private int roleId;
	private Boolean status;

	public RouteDetailsDomain() {
		super();
	}

	public RouteDetailsDomain(String uuid) {
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

	public AddressDetailsModel getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(AddressDetailsModel sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public AddressDetailsModel getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(AddressDetailsModel destinationAddress) {
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
