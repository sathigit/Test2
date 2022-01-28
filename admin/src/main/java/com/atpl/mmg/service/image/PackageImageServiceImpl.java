package com.atpl.mmg.service.image;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.atpl.mmg.dao.image.PackageImageDAO;
import com.atpl.mmg.dao.packagedimension.PackageDimensionDao;
import com.atpl.mmg.domain.image.PackageImageDomain;
import com.atpl.mmg.exception.MmgRestException.EMPTY_FILE;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.PACKAGE_IMAGE_ALREADY_EXISTS;
import com.atpl.mmg.service.packagedimension.PackageDimensionService;
import com.atpl.mmg.utils.AwsFileUtils;
import com.atpl.mmg.utils.CommonUtils;

@Service("PackageImageService")
public class PackageImageServiceImpl implements PackageImageService {

	@Autowired
	AwsFileUtils awsFileUtils;

	@Autowired
	PackageImageDAO packageImageDAO;

	@Autowired
	PackageDimensionDao packageDimensionDao;

	@Autowired
	PackageDimensionService packageDimensionService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String savePackageImage(String packageId, String category, MultipartFile file, Map<String, String> req)
			throws Exception {
		if (file.isEmpty())
			throw new EMPTY_FILE("Please select a image to upload");
		if (!(file.getContentType().equalsIgnoreCase("image/jpeg")
				|| file.getContentType().equalsIgnoreCase("image/png")
				|| file.getContentType().equalsIgnoreCase("image/jpg")))
			throw new NOT_FOUND("Please select jpeg or png image");
		List<PackageImageDomain> packageImageDomainList = new ArrayList<PackageImageDomain>();
		if (!(req.containsKey("update") && (Boolean.parseBoolean(req.get("update"))))) {
			packageImageDomainList = packageImageDAO.getImage(packageId, true);
			if (!packageImageDomainList.isEmpty()) {
				throw new PACKAGE_IMAGE_ALREADY_EXISTS();
			}
		}
		final String SUFFIX = "/";
		String folderName = "package-dimension-mmg";
		if (category.equalsIgnoreCase("PACKAGE_DIMENSION")) {
			packageDimensionDao.getPackageDimensionByuuId(packageId);
			folderName = "package-dimension-mmg";
		} else
			throw new NOT_FOUND("Please mention the proper category!!");
		
		if (req.containsKey("update") && (Boolean.parseBoolean(req.get("update")))) {
			packageImageDomainList = packageImageDAO.getImage(packageId, true);
			if (!packageImageDomainList.isEmpty()) {
				for (PackageImageDomain image : packageImageDomainList) {
					packageImageDAO.updateImageIsActive(image.getImageId(), false);
				}
			}
		}
		PackageImageDomain packageImageDomain = new PackageImageDomain();
		packageImageDomain.setPackageId(packageId);
		packageImageDomain.setImageId(CommonUtils.generateRandomId());
		packageImageDomain.setName(file.getOriginalFilename());
		packageImageDomain.setType(file.getContentType());
		packageImageDomain.setSize(file.getSize());
		packageImageDomain.setCategory(category);

		String fileName = folderName + SUFFIX + packageImageDomain.getName();
		awsFileUtils.uploadFile(fileName, file);
		packageImageDomain.setPath(fileName);
		if (category.equalsIgnoreCase("PACKAGE_DIMENSION")) {
			packageDimensionService.updatePath(packageId, packageImageDomain.getPath());
		}
		return packageImageDAO.save(packageImageDomain);
	}

}
