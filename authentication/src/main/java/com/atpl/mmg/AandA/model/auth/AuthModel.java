package com.atpl.mmg.AandA.model.auth;

import java.util.List;

import com.atpl.mmg.AandA.model.profile.CoordinatorDetail;
import com.atpl.mmg.AandA.model.profile.Franchise;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.model.profile.ProfileModel;

public class AuthModel {

	private List<Franchise> franchise;
	private List<CoordinatorDetail> coordinators;

	private List<Profile> franchiseList;
	private List<Profile> coordinatorList;
	private List<Profile> customerList;
	private List<Profile> driverList;
	private List<Profile> bdmList;
	private List<Profile> bdoList;
	private List<Profile> channelPartnerList;
	private List<Profile> fieldOfficerList;
	private List<Profile> warehouseList;
	private List<Profile> fleetList;
	private List<Profile> enterpriseList;
	private List<Profile> operationTeamList;	

	public AuthModel() {
	}

	public AuthModel(List<Profile> franchiseList, List<Profile> coordinatorList, List<Profile> customerList,
			List<Profile> driverList, List<Profile> bdmList, List<Profile> bdoList, List<Profile> channelPartnerList,
			List<Profile> fieldOfficerList, List<Profile> warehouseList, List<Profile> fleetList,
			List<Profile> enterpriseList, List<Profile> operationTeamList) {
		super();
		this.franchiseList = franchiseList;
		this.coordinatorList = coordinatorList;
		this.customerList = customerList;
		this.driverList = driverList;
		this.bdmList = bdmList;
		this.bdoList = bdoList;
		this.channelPartnerList = channelPartnerList;
		this.fieldOfficerList = fieldOfficerList;
		this.warehouseList = warehouseList;
		this.fleetList = fleetList;
		this.enterpriseList = enterpriseList;
		this.operationTeamList = operationTeamList;
	}

	public List<Franchise> getFranchise() {
		return franchise;
	}

	public void setFranchise(List<Franchise> franchise) {
		this.franchise = franchise;
	}

	public List<CoordinatorDetail> getCoordinators() {
		return coordinators;
	}

	public void setCoordinators(List<CoordinatorDetail> coordinators) {
		this.coordinators = coordinators;
	}

	public List<Profile> getFranchiseList() {
		return franchiseList;
	}

	public void setFranchiseList(List<Profile> franchiseList) {
		this.franchiseList = franchiseList;
	}

	public List<Profile> getCoordinatorList() {
		return coordinatorList;
	}

	public void setCoordinatorList(List<Profile> coordinatorList) {
		this.coordinatorList = coordinatorList;
	}

	public List<Profile> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Profile> customerList) {
		this.customerList = customerList;
	}

	public List<Profile> getDriverList() {
		return driverList;
	}

	public void setDriverList(List<Profile> driverList) {
		this.driverList = driverList;
	}

	public List<Profile> getBdmList() {
		return bdmList;
	}

	public void setBdmList(List<Profile> bdmList) {
		this.bdmList = bdmList;
	}

	public List<Profile> getBdoList() {
		return bdoList;
	}

	public void setBdoList(List<Profile> bdoList) {
		this.bdoList = bdoList;
	}

	public List<Profile> getChannelPartnerList() {
		return channelPartnerList;
	}

	public void setChannelPartnerList(List<Profile> channelPartnerList) {
		this.channelPartnerList = channelPartnerList;
	}

	public List<Profile> getFieldOfficerList() {
		return fieldOfficerList;
	}

	public void setFieldOfficerList(List<Profile> fieldOfficerList) {
		this.fieldOfficerList = fieldOfficerList;
	}

	public List<Profile> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<Profile> warehouseList) {
		this.warehouseList = warehouseList;
	}

	public List<Profile> getFleetList() {
		return fleetList;
	}

	public void setFleetList(List<Profile> fleetList) {
		this.fleetList = fleetList;
	}

	public List<Profile> getEnterpriseList() {
		return enterpriseList;
	}

	public void setEnterpriseList(List<Profile> enterpriseList) {
		this.enterpriseList = enterpriseList;
	}

	public List<Profile> getOperationTeamList() {
		return operationTeamList;
	}

	public void setOperationTeamList(List<Profile> operationTeamList) {
		this.operationTeamList = operationTeamList;
	}
}
