package com.atpl.mmg.service.packagetype;

import java.util.List;

import com.atpl.mmg.model.packagetype.PackageTypeModel;

public interface PackageTypeService {

	public String save(PackageTypeModel packageTypeModel) throws Exception;

	public PackageTypeModel getPackageTypeById(String uuId) throws Exception;

	public List<PackageTypeModel> getPackageType(Boolean status) throws Exception;

	public String update(PackageTypeModel packageTypeModel) throws Exception;
	
	public String updateStatus(String uuid,boolean status) throws Exception;

	public String delete(String uuId) throws Exception;

}
