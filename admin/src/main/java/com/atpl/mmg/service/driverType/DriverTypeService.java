package com.atpl.mmg.service.driverType;

import java.util.Map;

import com.atpl.mmg.model.driverType.DriverTypeModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface DriverTypeService {

	public ListDto getDriverType(Map<String, String> reqParam) throws Exception;
	
	public DriverTypeModel getDriverTypeById(int id) throws Exception;
	
	public ListDto getLicenceCategory(Map<String, String> reqParam) throws Exception;
	
	public DriverTypeModel getLicenceCategoryById(int id) throws Exception;
}
