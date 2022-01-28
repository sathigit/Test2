package com.atpl.mmg.dao.vehicle;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.vehicle.VehicleDomain;


public interface VehicleDAO {

	public String saveVehicle(VehicleDomain VehicleDomain) throws Exception;

	public List<VehicleDomain> getVehicle(int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getVehicleCount() throws Exception;

	public List<VehicleDomain> getVehicle(String vehicleCategoryId) throws Exception;

	public List<VehicleDomain> getVehicle(String goodsTypeId, String kerbWeightId,int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getVehicleByTypeAndKerbweightCount(String goodsTypeId, String kerbWeightId) throws Exception;
	
	public List<VehicleDomain> getVehicleByKerbWeightId(String kerbWeightId,int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getVehicleByKerbWeightIdCount(String kerbWeightId) throws Exception;

	public String deleteVehicle(int id) throws Exception;

}
