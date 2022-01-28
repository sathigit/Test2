package com.atpl.mmg.dao.vehiclecategory;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.vehiclecategory.VehicleCategoryDomain;


public interface VehicleCategoryDAO {
	
	public String saveVehicleCategory(VehicleCategoryDomain vehicleCategoryDomain) throws Exception;
	
	public VehicleCategoryDomain getVehicleCategoryByNameAndWeight(String category,int weightId) throws Exception;
	
	public List<VehicleCategoryDomain> getVehicleCategory(int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getVehicleCategoryCount() throws Exception;
	
	public String updateVehicleCategory(VehicleCategoryDomain vehicleCategoryId) throws Exception;
	
	public String enableDisableVehicleCategory(int vehicleCategoryId,boolean status) throws Exception;
	
	public VehicleCategoryDomain getVehicleCategorybyId(Integer vehicleCategoryId) throws Exception;
	
	public List<VehicleCategoryDomain> getVehicleCategorybyWeight(String kerbWeight,int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getVehicleCategorybyWeightCount(String kerbWeight) throws Exception;
	
	public VehicleCategoryDomain getVehicleCategorybyId() throws Exception;
	
	public List<VehicleCategoryDomain> getVehicle(String goodsTypeId,int weightId,int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getVehicleCount(String goodsTypeId,int weightId) throws Exception;
	
	public List<VehicleCategoryDomain> getVehiclesByKerbweightId(int weightId) throws Exception;
	
	public List<VehicleCategoryDomain> getVehiclesByDirectBooking(int kerbWeight) throws Exception;
	
}
