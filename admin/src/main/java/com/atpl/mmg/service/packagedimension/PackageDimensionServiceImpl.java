package com.atpl.mmg.service.packagedimension;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.constant.MeasuringUnit;
import com.atpl.mmg.dao.packagedimension.PackageDimensionDao;
import com.atpl.mmg.domain.packageDimension.PackageDimensionDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.PACKAGE_DIMENSION_ALREADY_EXISTS;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.packagedimension.PackageDimensionMapper;
import com.atpl.mmg.model.packagedimension.PackageDimensionModel;
import com.atpl.mmg.service.packagetype.PackageTypeService;
import com.atpl.mmg.utils.CommonUtils;

@Service("PackageDimensionService")
public class PackageDimensionServiceImpl implements PackageDimensionService {

	@Autowired
	PackageDimensionDao packageDimensionDao;

	@Autowired
	PackageDimensionMapper packageDimensionMapper;
	
	@Autowired
	PackageTypeService packageTypeService;
	
	@Autowired
	MMGProperties mmgProperties;

	public String save(PackageDimensionModel packageDimensionModel) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
						"save in PackageDimensionServiceImpl ") + JsonUtil.toJsonString(packageDimensionModel));

		validate(packageDimensionModel, false);
		PackageDimensionDomain packageDimensionDomain = new PackageDimensionDomain();
		BeanUtils.copyProperties(packageDimensionModel, packageDimensionDomain);
		packageDimensionDomain.setUuid(CommonUtils.generateRandomId());
		return packageDimensionDao.save(packageDimensionDomain);
	}

	@Override
	public List<PackageDimensionModel> getPackageDimension(Map<String, String> reqParam) throws Exception {
		Boolean status = null;
		String packageTypeId = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("status")) {
				status = Boolean.valueOf(reqParam.get("status"));
			}
			if (reqParam.containsKey("packageTypeId")) {
				packageTypeId = reqParam.get("packageTypeId");
				packageTypeService.getPackageTypeById(packageTypeId);
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(),
				SeverityTypes.CRITICAL.ordinal(), "getPackageDimension in PackageDimensionServiceImpl "));
		List<PackageDimensionDomain> packageDimensionDomain = packageDimensionDao.getPackageDimension(status,
				packageTypeId);
		return packageDimensionMapper.entityList(packageDimensionDomain);
	}

	@Override
	public PackageDimensionModel getPackageDimensionByuuId(String uuId) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
						"getPackageDimensionByuuId in PackageDimensionServiceImpl ") + JsonUtil.toJsonString(uuId));

		PackageDimensionDomain packageDimensionDomain = packageDimensionDao.getPackageDimensionByuuId(uuId);
		PackageDimensionModel packageDimensionModel = new PackageDimensionModel();
		BeanUtils.copyProperties(packageDimensionDomain, packageDimensionModel);
		return packageDimensionModel;
	}

	@Override
	public String update(PackageDimensionModel packageDimensionModel) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
						"update in PackageDimensionServiceImpl ") + JsonUtil.toJsonString(packageDimensionModel));

		validate(packageDimensionModel, true);
		PackageDimensionDomain packageDimensionDomain = new PackageDimensionDomain();
		BeanUtils.copyProperties(packageDimensionModel, packageDimensionDomain);
		return packageDimensionDao.update(packageDimensionDomain);
	}

	@Override
	public String delete(String uuId) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
						"delete in PackageDimensionServiceImpl ") + JsonUtil.toJsonString(uuId));

		return packageDimensionDao.delete(uuId);
	}

	public void validate(PackageDimensionModel packageDimensionModel, boolean isUpdate) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
						"validate in PackageDimensionServiceImpl ") + JsonUtil.toJsonString(packageDimensionModel));

		if (packageDimensionModel.getLength() == null || packageDimensionModel.getLength() <= 0.0) {
			throw new NOT_FOUND("Please mention the length");
		} else if (packageDimensionModel.getWidth() == null || packageDimensionModel.getWidth() <= 0.0) {
			throw new NOT_FOUND("Please mention the width");
		} else if (packageDimensionModel.getHeight() == null || packageDimensionModel.getHeight() <= 0.0) {
			throw new NOT_FOUND("Please mention the height");
		}
		if (CommonUtils.isNullCheck(packageDimensionModel.getMeasuringUnit()))
			throw new NOT_FOUND("Please mention the measuring unit");
		else {
			MeasuringUnit measuringUnit = MeasuringUnit.getMeasuringUnit(packageDimensionModel.getMeasuringUnit() + "");
			if (null == measuringUnit)
				throw new NOT_FOUND(packageDimensionModel.getMeasuringUnit() + "not Found");
		}
		
		PackageDimensionDomain packageDimensionDomain = packageDimensionDao.getPackageDimensionByDetails(
				packageDimensionModel.getLength(), packageDimensionModel.getWidth(), packageDimensionModel.getHeight(),
				packageDimensionModel.getMeasuringUnit(), packageDimensionModel.getPackageTypeId());
		if (CommonUtils.isNullCheck(packageDimensionModel.getPackageTypeId())) {
			throw new NOT_FOUND("Please mention the proper package Type");
		} else {
			packageTypeService.getPackageTypeById(packageDimensionModel.getPackageTypeId());
		}
		if (null != packageDimensionDomain)
			throw new PACKAGE_DIMENSION_ALREADY_EXISTS("Package dimension already exists");
		if (isUpdate) {
			if (CommonUtils.isNullCheck(packageDimensionModel.getUuid())) {
				throw new NOT_FOUND("Please mention the proper dimensionId");
			} else {
				PackageDimensionModel packageDimension = getPackageDimensionByuuId(packageDimensionModel.getUuid());
				if (CommonUtils.isNullCheck(packageDimensionModel.getDescription())) {
					if (!CommonUtils.isNullCheck(packageDimension.getDescription()))
						packageDimensionModel.setDescription(packageDimension.getDescription());
				}
			}
		}
	}

	@Override
	public String updateStatus(String uuId, boolean status) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
						"updatePath in PackageDimensionServiceImpl ") + JsonUtil.toJsonString(uuId));

		packageDimensionDao.getPackageDimensionByuuId(uuId);
		return packageDimensionDao.updateStatus(uuId, status);
	}

	@Override
	public String updatePath(String uuId, String imagePath) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
						"updatePath in PackageDimensionServiceImpl ") + JsonUtil.toJsonString(imagePath));
		packageDimensionDao.getPackageDimensionByuuId(uuId);
		if (!CommonUtils.isNullCheck(imagePath)) {
			StringBuilder image = new StringBuilder();
			image.append(Constants.PACKAGE_DIMENSION_IMAGE_PATH);
			image.append(mmgProperties.getMasterBucket());
			image.append("/");
			image.append(imagePath);
			imagePath = image.toString();
			return packageDimensionDao.updateImagePath(uuId, imagePath);
		}
		return imagePath;
	}
}
