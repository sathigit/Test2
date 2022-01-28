package com.atpl.mmg.dao.packagetype;

import java.util.List;

import com.atpl.mmg.domain.packagetype.PackageTypeDomain;

public interface PackageTypeDao {

	public String save(PackageTypeDomain packageTypeDomain) throws Exception;

	public List<PackageTypeDomain> getPackageType(Boolean status) throws Exception;

	public PackageTypeDomain getPackageTypeById(String uuId) throws Exception;
	
	public PackageTypeDomain getPackageTypeByName(String name) throws Exception;

	public String update(PackageTypeDomain packageTypeDomain) throws Exception;
	
	public String updateStatus(String uuid,boolean status) throws Exception;

	public String delete(String uuId) throws Exception;
}
