package com.atpl.mmg.service.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.atpl.mmg.common.ApiUtility;
import com.atpl.mmg.common.GenericHttpClient;
import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.common.MmgEnum;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.image.VehicleImageDAO;
import com.atpl.mmg.domain.image.VehicleImageDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.COUNT_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.EMPTY_FILE;
import com.atpl.mmg.exception.MmgRestException.FILE_UPLOAD_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.image.VehicleImageMapper;
import com.atpl.mmg.model.franchisevehicles.FranchiseVehicleModel;
import com.atpl.mmg.model.image.VehicleImageModel;
import com.atpl.mmg.utils.CommonUtils;

@Service("vehicleImageService")
public class VehicleImageServiceImpl implements VehicleImageService, Constants {

	private static final Logger logger = LoggerFactory.getLogger(VehicleImageServiceImpl.class);

	@Autowired
	VehicleImageDAO vehicleImageDAO;

	@Autowired
	VehicleImageMapper vehicleImageMapper;

	@Autowired
	MMGProperties mmgProperties;

	@Override
	public String saveVehicleImage(int vehicleCategoryId, MultipartFile file) throws Exception {
		if (file.isEmpty())
			throw new EMPTY_FILE("Please select a file to upload");

		VehicleImageDomain vehicleImageDomain = new VehicleImageDomain();
		vehicleImageDomain.setVehicleCategoryId(vehicleCategoryId);
		vehicleImageDomain.setImageId(CommonUtils.generateRandomId());
		vehicleImageDomain.setName(file.getOriginalFilename());
		vehicleImageDomain.setType(file.getContentType());
		vehicleImageDomain.setSize(file.getSize());

		final String SUFFIX = "/";
		String folderName = "vehiclecategory-mmg";
		String fileName = folderName + SUFFIX + vehicleImageDomain.getName();

		uploadFile(fileName, file);

		vehicleImageDomain.setPath(fileName);

		return vehicleImageDAO.saveVehicleImage(vehicleImageDomain);

	}

	private void uploadFile(String fileName, MultipartFile file) throws IOException {
		try {
			AWSCredentials credentials = new BasicAWSCredentials(mmgProperties.getAccessKey(),
					mmgProperties.getSecretKey());
			@SuppressWarnings("deprecation")
			AmazonS3 s3client = new AmazonS3Client(credentials);
			s3client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
			String bucketName = "admin-mmg";

			ObjectMetadata md = new ObjectMetadata();
			md.setContentLength(file.getSize());
			md.setContentType(file.getContentType());

			InputStream is = file.getInputStream();
			s3client.putObject(new PutObjectRequest(bucketName, fileName, is, md));
		} catch (Exception e) {
			throw new FILE_UPLOAD_FAILED();
		}

	}

	@Override
	public List<VehicleImageModel> getVehicleImagesbyCategory() throws Exception {
		List<VehicleImageDomain> vehicleImageDomain = vehicleImageDAO.getVehicleImagesbyCategory();
		return vehicleImageMapper.entityList(vehicleImageDomain);
	}

	@Override
	public VehicleImageModel getVehicleImagesbyCategoryId(int vehicleCategoryId) throws Exception {
		VehicleImageDomain vehicleImageDomain = vehicleImageDAO.getVehicleImagesbyCategoryId(vehicleCategoryId);
		VehicleImageModel vehicleImageModel = new VehicleImageModel();
		if (vehicleImageDomain == null)
			throw new NOT_FOUND("VehicleImages not found");
		BeanUtils.copyProperties(vehicleImageDomain, vehicleImageModel);
		return vehicleImageModel;
	}

	@Override
	public List<VehicleImageModel> getVehicleImagesbyType() throws Exception {
		List<VehicleImageDomain> vehicleImageDomain = vehicleImageDAO.getVehicleImagesbyType();
		return vehicleImageMapper.entityList(vehicleImageDomain);

	}

	@Override
	public List<VehicleImageModel> getVehicleImagesbyTypeId(int vehicleTypeId) throws Exception {
		List<VehicleImageDomain> vehicleImageDomain = vehicleImageDAO.getVehicleImagesbyTypeId(vehicleTypeId);
		return vehicleImageMapper.entityList(vehicleImageDomain);
	}

	@Override
	public List<VehicleImageModel> getVehicleImagesFranchise(String goodsTypeId, String kerbWeightId, String origin)
			throws Exception {
		List<VehicleImageDomain> vehicleImageDomain1 = vehicleImageDAO.getVehicleImagesFranchise(goodsTypeId,
				kerbWeightId, origin);
		for (VehicleImageDomain vehicleImageDomain : vehicleImageDomain1) {
			int id = vehicleImageDomain.getId();
			int vehicleCategoryId = vehicleImageDomain.getVehicleCategoryId();
			/*tieups Micro service*/
			Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
					null + "v1" + "/getFranchiseCity/" + id,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.TIEUPS.name());
			int statusCode = (int) httpResponse.get("statusCode");
			JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
			if (jsonResponse != null) {
				if (statusCode != HttpStatus.OK.value())
					throw new COUNT_NOT_FOUND("Vehicle not found");
				else {
					List<FranchiseVehicleModel> franchiseVehicleModel = JsonUtil
							.parseJsonArray(jsonResponse.getJSONArray("data").toString(), FranchiseVehicleModel.class);
					for (FranchiseVehicleModel franchiseVehicle : franchiseVehicleModel) {
						vehicleImageDomain.setId(franchiseVehicle.getCityId());
						vehicleImageDomain.setFranchiseId(franchiseVehicle.getFranchiseId());
					}
				}
			} else {
				throw new BACKEND_SERVER_ERROR();
			}

			Map<String, Object> httpResponse1 = GenericHttpClient.doHttpGet(
					mmgProperties.getFranchiseUrl() + "v1" + "/getVehiclebyCategory/" + vehicleCategoryId,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.FRANCHISE.name());
			int statusCode1 = (int) httpResponse1.get("statusCode");
			JSONObject jsonResponse1 = (JSONObject) httpResponse1.get("response");
			if (jsonResponse1 != null) {
				if (statusCode1 != HttpStatus.OK.value())
					throw new COUNT_NOT_FOUND("Vehicle not found");
				else {
					List<FranchiseVehicleModel> franchiseVehicleModel = JsonUtil
							.parseJsonArray(jsonResponse.getJSONArray("data").toString(), FranchiseVehicleModel.class);
					for (FranchiseVehicleModel franchiseVehicle : franchiseVehicleModel) {
						vehicleImageDomain.setVehicleCategoryId(franchiseVehicle.getVehicleCategoryId());
					}
				}
			} else {
				throw new BACKEND_SERVER_ERROR();
			}
		}
		return vehicleImageMapper.entityList(vehicleImageDomain1);

	}

}
