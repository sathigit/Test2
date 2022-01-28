package com.atpl.mmg.service.specialvehicles;

import java.util.List;

import com.atpl.mmg.model.specialvehicles.SpecialVehiclesModel;

public interface SpecialVehiclesService {

	public SpecialVehiclesModel addSpecialVehicle(SpecialVehiclesModel specialVehiclesModel) throws Exception;

	public String UpdateSpecialVehicle(SpecialVehiclesModel specialVehiclesModel) throws Exception;

	public List<SpecialVehiclesModel> getSpecialVehicle() throws Exception;

	public String deleteSpecialVehicle(int vehicleCategoryId) throws Exception;
}
