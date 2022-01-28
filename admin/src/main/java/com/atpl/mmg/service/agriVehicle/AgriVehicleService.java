package com.atpl.mmg.service.agriVehicle;

import java.util.List;

import com.atpl.mmg.model.agriVehicle.AgriVehicleModel;


public interface AgriVehicleService {

	public String saveAgriVehcile(AgriVehicleModel agriVehicleModel) throws Exception;

	public List<AgriVehicleModel> getAgriVehcile() throws Exception;

	public AgriVehicleModel agriVehcile(int vehicleCategoryId) throws Exception;

	public String updateAgriVehicle(AgriVehicleModel agriVehicleModel) throws Exception;

	public String deleteAgriVehicleCategory(int vehicleCategoryId) throws Exception;

}
