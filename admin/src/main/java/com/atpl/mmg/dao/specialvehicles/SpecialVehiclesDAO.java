package com.atpl.mmg.dao.specialvehicles;

import java.util.List;

import com.atpl.mmg.domain.specialvehicles.SpecialVehiclesDomain;
import com.atpl.mmg.exception.GenericRes;

public interface SpecialVehiclesDAO {

	public SpecialVehiclesDomain addSpecialVehicle(SpecialVehiclesDomain specialVehiclesDomain) throws Exception;

	public String UpdateSpecialVehicle(SpecialVehiclesDomain specialVehiclesDomain) throws Exception;

	public List<SpecialVehiclesDomain> getSpecialVehicle() throws Exception;

	public String deleteSpecialVehicle(int vehicleCategoryId) throws Exception;
}
