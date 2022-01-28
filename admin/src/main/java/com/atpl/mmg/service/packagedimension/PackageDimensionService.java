package com.atpl.mmg.service.packagedimension;

import java.util.List;
import java.util.Map;

import com.atpl.mmg.model.packagedimension.PackageDimensionModel;

public interface PackageDimensionService {

	public String save(PackageDimensionModel packageDimensionModel) throws Exception;
	
	public List<PackageDimensionModel> getPackageDimension(Map<String,String> reqParam) throws Exception;

	public PackageDimensionModel getPackageDimensionByuuId(String uuId) throws Exception;
	
	public String update(PackageDimensionModel uuId) throws Exception;
	
	public String updateStatus(String uuId,boolean status) throws Exception;
	
	public String updatePath(String uuId,String imagePath) throws Exception;
	
	public String delete(String uuId) throws Exception;

}
