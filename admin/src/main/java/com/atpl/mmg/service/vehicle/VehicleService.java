package com.atpl.mmg.service.vehicle;

import java.util.List;
import java.util.Map;

import com.atpl.mmg.model.vehicle.VehicleModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface VehicleService {

	public String saveVehicle(VehicleModel VehicleModel) throws Exception;

	public ListDto getVehicle(Map<String, String> reqParam) throws Exception;

	public List<VehicleModel> getVehicle(String vehicleCategoryId) throws Exception;
	
	public ListDto getVehicleByKerbWeightId(String kerbWeightId,Map<String, String> reqParam) throws Exception;

	public ListDto getVehicle(String goodsTypeId, String kerbWeightId,Map<String, String> reqParam) throws Exception;

	public String deleteVehicle(int id) throws Exception;

}
