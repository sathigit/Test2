package com.atpl.mmg.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.atpl.mmg.model.image.VehicleImageModel;

public interface VehicleImageService {

	public String saveVehicleImage(int vehicleCategoryId, MultipartFile file) throws Exception;

	public List<VehicleImageModel> getVehicleImagesbyCategory() throws Exception;

	public VehicleImageModel getVehicleImagesbyCategoryId(int vehicleCategoryId) throws Exception;

	public List<VehicleImageModel> getVehicleImagesbyType() throws Exception;

	public List<VehicleImageModel> getVehicleImagesbyTypeId(int vehicleTypeId) throws Exception;

	public List<VehicleImageModel> getVehicleImagesFranchise(String goodsTypeId, String kerbWeightId, String origin)
			throws Exception;
}
