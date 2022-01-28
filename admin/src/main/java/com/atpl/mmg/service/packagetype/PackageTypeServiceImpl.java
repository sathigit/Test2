package com.atpl.mmg.service.packagetype;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.dao.packagetype.PackageTypeDao;
import com.atpl.mmg.domain.packagetype.PackageTypeDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.PACKAGE_TYPE_AlREADY_EXISTS;
import com.atpl.mmg.mapper.packagetype.PackageTypeMapper;
import com.atpl.mmg.model.packagetype.PackageTypeModel;
import com.atpl.mmg.utils.CommonUtils;

@Service("PackageTypeService")
public class PackageTypeServiceImpl implements PackageTypeService {

	@Autowired
	PackageTypeDao packageTypeDao;

	@Autowired
	PackageTypeMapper packageTypeMapper;

	public PackageTypeServiceImpl() {
		// constructor
	}

	public String save(PackageTypeModel packageTypeModel) throws Exception {
		validatePackageTypeName(packageTypeModel.getName().toUpperCase());
		PackageTypeDomain packageTypeDomain = new PackageTypeDomain();
		BeanUtils.copyProperties(packageTypeModel, packageTypeDomain);
		packageTypeDomain.setUuid(CommonUtils.generateRandomId());
		return packageTypeDao.save(packageTypeDomain);
	}

	@Override
	public List<PackageTypeModel> getPackageType(Boolean status) throws Exception {
		List<PackageTypeDomain> packageTypeDomain = packageTypeDao.getPackageType(status);
		return packageTypeMapper.entityList(packageTypeDomain);
	}

	@Override
	public String update(PackageTypeModel packageTypeModel) throws Exception {
		validatePackageTypeName(packageTypeModel.getName());
		validateUuid(packageTypeModel);
		PackageTypeDomain packageTypeDomain = new PackageTypeDomain();
		BeanUtils.copyProperties(packageTypeModel, packageTypeDomain);
		return packageTypeDao.update(packageTypeDomain);

	}

	@Override
	public PackageTypeModel getPackageTypeById(String uuId) throws Exception {
		PackageTypeDomain packageTypeDomain = packageTypeDao.getPackageTypeById(uuId);
		PackageTypeModel packageTypeModel = new PackageTypeModel();
		if (null == packageTypeDomain)
			throw new NOT_FOUND("PackageType not found");
		BeanUtils.copyProperties(packageTypeDomain, packageTypeModel);
		return packageTypeModel;
	}

	@Override
	public String delete(String uuId) throws Exception {
		if (CommonUtils.isNullCheck(uuId)) {
			throw new NOT_FOUND("Please mention the uuid");
		} else
			getPackageTypeById(uuId);
		return packageTypeDao.delete(uuId);
	}

	private void validatePackageTypeName(String name) throws Exception {
		if (CommonUtils.isNullCheck(name)) {
			throw new NOT_FOUND("Please mention the packageType");
		} else {
			PackageTypeDomain packageType = packageTypeDao.getPackageTypeByName(name);
			if (null != packageType)
				throw new PACKAGE_TYPE_AlREADY_EXISTS("Package Type already exists");
		}
	}

	private void validateUuid(PackageTypeModel packageTypeModel) throws Exception {
		if (CommonUtils.isNullCheck(packageTypeModel.getUuid())) {
			throw new NOT_FOUND("Please mention the uuid");
		} else
			getPackageTypeById(packageTypeModel.getUuid());
	}

	@Override
	public String updateStatus(String uuid, boolean status) throws Exception {
		packageTypeDao.getPackageTypeById(uuid);
		return packageTypeDao.updateStatus(uuid, status);
	}
}
