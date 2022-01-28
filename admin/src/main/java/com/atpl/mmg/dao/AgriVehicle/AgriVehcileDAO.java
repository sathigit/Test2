package com.atpl.mmg.dao.AgriVehicle;

import java.util.List;

import com.atpl.mmg.domain.agriVehicle.AgriVehicleDomain;


public interface AgriVehcileDAO {

	public String saveAgriVehcile(AgriVehicleDomain agriVehicleDomain) throws Exception;

	public List<AgriVehicleDomain> getAgriVehcile() throws Exception;

	public AgriVehicleDomain agriVehcile(int vehicleCategoryId) throws Exception;

	public String updateAgriVehicle(AgriVehicleDomain agriVehicleDomain) throws Exception;

	public String deleteAgriVehicleCategory(int vehicleCategoryId) throws Exception;

}
