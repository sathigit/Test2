package com.atpl.mmg.model.route;

import java.io.Serializable;
import java.util.List;

import com.atpl.mmg.model.admin.VehicleDetailsModel;
import com.atpl.mmg.model.profile.Profile;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RouteModelDTO implements Serializable {

	private static final long serialVersionUID = -2196266153406082047L;

	private String uuid;
	private Address source;
	private Address destination;
	private String createdBy;
	private List<RouteTagDetailsModel> sourceTaggedVendors;
	private List<RouteTagDetailsModel> destinationTaggedVendors;
	private List<Profile> sourceFranchiseDetails;
	private List<Profile> sourceCoordinatorDetails;
	private List<Profile> destinationFranchiseDetails;
	private List<Profile> destinationCoordinatorDetails;
	private List<VehicleDetailsModel> taggedVehicles;
	private int roleId;
	private Boolean status;
	private int taggedVendorsCount;
	private int taggedVehiclesCount;
	private RouteDashboard routeDashboard;

	public RouteModelDTO() {
	}

	public RouteModelDTO(String uuid, Address source, Address destination, String createdBy,
			List<RouteTagDetailsModel> sourceTaggedVendors, List<RouteTagDetailsModel> destinationTaggedVendors,
			Boolean status, int taggedVendorsCount, int taggedVehiclesCount,RouteDashboard routeDashboard) {
		super();
		this.uuid = uuid;
		this.source = source;
		this.destination = destination;
		this.createdBy = createdBy;
		this.sourceTaggedVendors = sourceTaggedVendors;
		this.destinationTaggedVendors = destinationTaggedVendors;
		this.status = status;
		this.taggedVendorsCount = taggedVendorsCount;
		this.taggedVehiclesCount = taggedVehiclesCount;
		this.routeDashboard = routeDashboard;
	}

	public RouteModelDTO(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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

	public List<Profile> getSourceFranchiseDetails() {
		return sourceFranchiseDetails;
	}

	public void setSourceFranchiseDetails(List<Profile> sourceFranchiseDetails) {
		this.sourceFranchiseDetails = sourceFranchiseDetails;
	}

	public List<Profile> getSourceCoordinatorDetails() {
		return sourceCoordinatorDetails;
	}

	public void setSourceCoordinatorDetails(List<Profile> sourceCoordinatorDetails) {
		this.sourceCoordinatorDetails = sourceCoordinatorDetails;
	}

	public List<Profile> getDestinationFranchiseDetails() {
		return destinationFranchiseDetails;
	}

	public void setDestinationFranchiseDetails(List<Profile> destinationFranchiseDetails) {
		this.destinationFranchiseDetails = destinationFranchiseDetails;
	}

	public List<Profile> getDestinationCoordinatorDetails() {
		return destinationCoordinatorDetails;
	}

	public void setDestinationCoordinatorDetails(List<Profile> destinationCoordinatorDetails) {
		this.destinationCoordinatorDetails = destinationCoordinatorDetails;
	}

	public List<RouteTagDetailsModel> getSourceTaggedVendors() {
		return sourceTaggedVendors;
	}

	public void setSourceTaggedVendors(List<RouteTagDetailsModel> sourceTaggedVendors) {
		this.sourceTaggedVendors = sourceTaggedVendors;
	}

	public List<RouteTagDetailsModel> getDestinationTaggedVendors() {
		return destinationTaggedVendors;
	}

	public void setDestinationTaggedVendors(List<RouteTagDetailsModel> destinationTaggedVendors) {
		this.destinationTaggedVendors = destinationTaggedVendors;
	}

	public List<VehicleDetailsModel> getTaggedVehicles() {
		return taggedVehicles;
	}

	public void setTaggedVehicles(List<VehicleDetailsModel> taggedVehicles) {
		this.taggedVehicles = taggedVehicles;
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

	public RouteDashboard getRouteDashboard() {
		return routeDashboard;
	}

	public void setRouteDashboard(RouteDashboard routeDashboard) {
		this.routeDashboard = routeDashboard;
	}

}
