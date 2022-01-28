package com.atpl.mmg.dao.image;


import java.util.List;

import com.atpl.mmg.domain.image.VehicleImageDomain;

public interface VehicleImageDAO {
	
	public String saveVehicleImage(VehicleImageDomain vehicleImageDomain) throws Exception;
	
	public VehicleImageDomain getVehicleImagesbyCategoryId(int vehicleCategoryId) throws Exception;
	
	public List<VehicleImageDomain> getVehicleImagesbyCategory() throws Exception;
	
	public List<VehicleImageDomain> getVehicleImagesbyType() throws Exception;
	
	public List<VehicleImageDomain> getVehicleImagesbyTypeId(int vehicleTypeId) throws Exception;
	
	public List<VehicleImageDomain> getVehicleImagesFranchise(String goodsTypeId,String kerbWeightId,String origin) throws Exception;
}