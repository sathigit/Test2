package com.atpl.mmg.AandA.service.profileImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.constant.ImageCategory;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.dao.profileImage.ProfileImageDAO;
import com.atpl.mmg.AandA.dao.role.RoleDAO;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.domain.profileImage.ProfileImageDomain;
import com.atpl.mmg.AandA.domain.role.RoleDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.EMPTY_FILE;
import com.atpl.mmg.AandA.exception.MmgRestException.FILE_UPLOAD_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.IMAGE_ALREADY_EXISTS;
import com.atpl.mmg.AandA.exception.MmgRestException.IMAGE_CATEGORY_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.IMAGE_ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.mapper.profileImage.ProfileImageMapper;
import com.atpl.mmg.AandA.model.profileImage.ProfileImageModel;
import com.atpl.mmg.AandA.utils.CommonUtils;

@Service("ImageService")
public class ProfileImageServiceImpl implements ProfileImageService {

	@Autowired
	ProfileImageDAO profileImageDAO;

	@Autowired
	ProfileImageMapper profileImageMapper;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	private void validateImage(String profileId, int roleId, String category, MultipartFile file) throws Exception {
		if (file.isEmpty())
			throw new EMPTY_FILE("Please select a image to upload");
		if (!(file.getContentType().equalsIgnoreCase("image/jpeg")
				|| file.getContentType().equalsIgnoreCase("image/png")
				|| file.getContentType().equalsIgnoreCase("image/jpg")))
			throw new NOT_FOUND("Please select jpeg or png image");
		if (0 >= roleId)
			throw new NOT_FOUND("Please mention the roleId");
		if (roleId == Integer.parseInt(Role.DRIVER.getCode()))
			throw new IMAGE_ROLE_NOT_FOUND("Not able to upload the image for this role!!!");
		if (null == category)
			throw new NOT_FOUND("Please mention the category");
		ImageCategory image = ImageCategory.getImageCategory(category);
		if (null == image)
			throw new IMAGE_CATEGORY_NOT_FOUND("Please mention the proper category!!!");
		Role roles = Role.getRole(roleId + "");
		if (null == roles)
			throw new ROLE_NOT_FOUND(roleId + "");
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String saveImage(String profileId, int roleId, String category, MultipartFile file, Map<String, String> req)
			throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_IMAGE_SERVICE.name(),
				SeverityTypes.CRITICAL.ordinal(), "saveImage")+JsonUtil.toJsonString(profileId));

		validateImage(profileId, roleId, category, file);
		ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetails(profileId);
		if (null == profileDomain)
			throw new NOT_FOUND("Profile details not found!!");
		String fileExt = "";
		int ver = 1;
		String version = String.valueOf(ver);
		fileExt = CommonUtils.getLastString(file.getContentType(), "/");
		RoleDomain role = roleDAO.getRoleName(roleId);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_IMAGE_SERVICE.name(),
				SeverityTypes.CRITICAL.ordinal(), "Get Images if exists ")+JsonUtil.toJsonString(profileId));


		ProfileImageDomain profileImageDomain = profileImageDAO.getImage(profileId, roleId, category, true);

		if (req.containsKey("update") && (Boolean.parseBoolean(req.get("update")))) {
			if (null != profileImageDomain) {
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_IMAGE_SERVICE.name(),
						SeverityTypes.CRITICAL.ordinal(), "Update the verison if image already exists ")+JsonUtil.toJsonString(req.get("update")));

				profileImageDAO.updateImageIsActive(profileImageDomain.getUuid(), false);
				String vName = "";
				vName = CommonUtils.getLastString(profileImageDomain.getName(), "_");
				vName = vName.replaceAll("\\D+", "");
				int verNumber = Integer.valueOf(vName);
				verNumber = verNumber + 1;
				version = String.valueOf(verNumber);
			} else
				throw new NOT_FOUND("Images not found for this category.Please upload!!");
		} else {
			if (null != profileImageDomain)
				throw new IMAGE_ALREADY_EXISTS("Image Already exists for this " + category + "!!");
		}

		final String SUFFIX = "/";
		String folderName = null;
		String roleName = role.getRoleName().replaceAll("\\s", "_");
		folderName = roleName;
		String fName = roleName + "_" + profileDomain.getFirstName() + "_" + category + "_" + version + "." + fileExt;
		ProfileImageDomain imageDomain = new ProfileImageDomain();
		imageDomain.setProfileId(profileId);
		imageDomain.setCategory(category);
		imageDomain.setUuid(CommonUtils.generateRandomId());
//		imageDomain.setName(file.getOriginalFilename());
		imageDomain.setName(fName);
		imageDomain.setType(file.getContentType());
		imageDomain.setSize(file.getSize());
		imageDomain.setRoleId(roleId);

		String fileName = folderName + SUFFIX + imageDomain.getProfileId() + SUFFIX + imageDomain.getCategory() + SUFFIX
				+ imageDomain.getName();
		String bucketName = mmgProperties.getBucketName();

		uploadFile(fileName, file, bucketName);
		imageDomain.setPath(fileName);
		imageDomain.setIsActive(true);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_IMAGE_SERVICE.name(),
				SeverityTypes.CRITICAL.ordinal(), "saveImage")+JsonUtil.toJsonString(imageDomain));
		return profileImageDAO.saveImage(imageDomain);
	}

	private void uploadFile(String fileName, MultipartFile file, String bucketName) throws IOException {
		try {
			AWSCredentials credentials = new BasicAWSCredentials(mmgProperties.getAccessKey(),
					mmgProperties.getSecretKey());
			@SuppressWarnings("deprecation")
			AmazonS3 s3client = new AmazonS3Client(credentials);
			s3client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));

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
	public List<ProfileImageModel> getImage(String id, Map<String, String> req) throws Exception {
		List<ProfileImageDomain> imageDomain = new ArrayList<ProfileImageDomain>();
		boolean isActive = false;
		String category = null;
		int roleId = 0;
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_IMAGE_SERVICE.name(),
				SeverityTypes.CRITICAL.ordinal(), "getImage By Id")+JsonUtil.toJsonString(id));

		if (req.size() > 0) {
			if (req.containsKey("category") && req.containsKey("isActive") && req.containsKey("roleId")  ) {
				category = req.get("category");
				isActive = Boolean.parseBoolean(req.get("isActive"));
				roleId = Integer.valueOf(req.get("roleId"));
				imageDomain = profileImageDAO.getImageByCategoryOnIsactive(id, category,isActive,roleId);
			} else {
				if (req.containsKey("category")) {
					category = req.get("category");
					imageDomain = profileImageDAO.getActiveImageByCategoryAndRole(id,category,0);
				}
				if (req.containsKey("isActive")) {
					isActive = Boolean.parseBoolean(req.get("isActive"));
					imageDomain = profileImageDAO.getImage(id, isActive);
				}
				if (req.containsKey("roleId")) {
					roleId =Integer.valueOf(req.get("roleId"));
					imageDomain = profileImageDAO.getActiveImageByCategoryAndRole(id,null,roleId);
				}
			}

		} else
			imageDomain = profileImageDAO.getAllImages(id);
		return profileImageMapper.entityList(imageDomain);
	}

	@Override
	public List<ProfileImageModel> getAllImage(Map<String, String> req) throws Exception {
		List<ProfileImageDomain> imageDomain = new ArrayList<ProfileImageDomain>();
		int roleId = 0;
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_IMAGE_SERVICE.name(),
				SeverityTypes.CRITICAL.ordinal(), "getAllImage"));
		if (req.size() > 0) {
			if (req.containsKey("roleId")) {
				roleId = Integer.parseInt(req.get("roleId"));
				Role roles = Role.getRole(roleId + "");
				if (null == roles)
					throw new ROLE_NOT_FOUND(roleId + "");
				imageDomain = profileImageDAO.getAllImagesOnRole(roleId);
			} else
				throw new NOT_FOUND("Please pass the request param as roleId");
		} else
			imageDomain = profileImageDAO.getAllImages(null);
		return profileImageMapper.entityList(imageDomain);
	}

	/*
	 * @Transactional(rollbackFor = Exception.class)
	 * 
	 * @Override public String updateImage(String profileId, int roleId, String
	 * category, String uuid, MultipartFile file) throws Exception { if
	 * (file.isEmpty()) throw new EMPTY_FILE("Please select a document to upload");
	 * if (!(file.getContentType().equalsIgnoreCase("image/jpeg") ||
	 * file.getContentType().equalsIgnoreCase("image/png") ||
	 * file.getContentType().equalsIgnoreCase("image/jpg"))) throw new
	 * NOT_FOUND("Please select jpeg or png image");
	 * 
	 * ProfileDomain profileDomain = profileDAOV2.getProfileDetails(profileId); if
	 * (null == profileDomain) throw new NOT_FOUND("Profile details not found!!");
	 * if (0 >= roleId) throw new NOT_FOUND("Please mention the roleId"); if (null
	 * == category) throw new NOT_FOUND("Please mention the category");
	 * ImageCategory image = ImageCategory.getImageCategory(category); if (null ==
	 * image) throw new
	 * IMAGE_CATEGORY_NOT_FOUND("Please mention the proper category"); Role roles =
	 * Role.getRole(roleId + ""); if (null == roles) throw new ROLE_NOT_FOUND(roleId
	 * + ""); ProfileImageDomain profileImageDomain =
	 * profileImageDAO.getImage(profileId, category); if (null !=
	 * profileImageDomain)
	 * profileImageDAO.updateImageIsActive(profileImageDomain.getUuid(), false);
	 * ProfileImageDomain imageDomain = new ProfileImageDomain();
	 * imageDomain.setProfileId(profileId); imageDomain.setCategory(category);
	 * imageDomain.setUuid(CommonUtils.generateRandomId());
	 * imageDomain.setName(file.getOriginalFilename());
	 * imageDomain.setType(file.getContentType());
	 * imageDomain.setSize(file.getSize()); imageDomain.setRoleId(roleId);
	 * 
	 * final String SUFFIX = "/"; String folderName = null;
	 * 
	 * RoleDomain roleName = roleDAO.getRoleName(roleId); folderName =
	 * roleName.getRoleName(); String fileName = folderName + SUFFIX +
	 * imageDomain.getProfileId() + SUFFIX + imageDomain.getCategory() + SUFFIX +
	 * imageDomain.getName(); String bucketName = mmgProperties.getBucketName();
	 * uploadFile(fileName, file, bucketName); imageDomain.setPath(fileName); return
	 * profileImageDAO.updateImage(imageDomain); }
	 */
}
