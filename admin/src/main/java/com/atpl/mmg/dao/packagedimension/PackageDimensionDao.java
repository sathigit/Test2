package com.atpl.mmg.dao.packagedimension;

import java.util.List;

import com.atpl.mmg.domain.packageDimension.PackageDimensionDomain;

public interface PackageDimensionDao {

	public String save(PackageDimensionDomain packageDimensionDomain) throws Exception;

	public List<PackageDimensionDomain> getPackageDimension(Boolean status,String packageTypeId) throws Exception;

	public PackageDimensionDomain getPackageDimensionByuuId(String uuId) throws Exception;

	public PackageDimensionDomain getPackageDimensionByDetails(double length, double width, double height,
			String measuringUnit,String packageTypeId) throws Exception;

	public String update(PackageDimensionDomain uuId) throws Exception;

	public String updateStatus(String uuId, boolean status) throws Exception;

	public String updateImagePath(String uuId, String imagePath) throws Exception;

	public String delete(String uuId) throws Exception;

}