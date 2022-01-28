package com.atpl.mmg.service.vehiclecategory;

import java.util.List;
import java.util.Map;

import com.atpl.mmg.model.vehiclecategory.VehicleCategoryModel;
import com.atpl.mmg.model.vehiclecategory.WeightModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface VehicleCategoryService {

	public String saveVehicleCategory(VehicleCategoryModel vehicleCategoryModel) throws Exception;

	public ListDto getVehicleCategory(Map<String,String> reqParam) throws Exception;

	public String updateVehicleCategory(VehicleCategoryModel vehicleCategoryId) throws Exception;
	
	public String enableVehicleCategory(int vehicleCategoryId) throws Exception; 

	public String deleteVehicleCategory(int vehicleCategoryId) throws Exception;

	public VehicleCategoryModel getVehicleCategorybyId(Integer vehicleCategoryId) throws Exception;

	public VehicleCategoryModel getVehicleCategorybyId() throws Exception;

	public ListDto getVehicle(String goodsTypeId, int weightId,Map<String,String> reqParam) throws Exception;
	
	public List<VehicleCategoryModel> getVehiclesByKerbweightId(WeightModel weightModel) throws Exception;

	public List<VehicleCategoryModel> getVehiclesByDirectBooking() throws Exception;

}
