package com.atpl.mmg.service.faredisttype;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.model.faredisttype.FareDistributionTypeDto;
import com.atpl.mmg.model.faredisttype.FareDistributionTypeModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface FareDistributionTypeService {

	public String save(FareDistributionTypeModel fareDistributionTypeModel) throws Exception;

	public String update(FareDistributionTypeModel fareDistributionTypeModel) throws Exception;

	public ListDto getFareDistributionTypes(@RequestParam Map<String, String> reqParam) throws Exception;
	
	public FareDistributionTypeDto getFareDistributionType(String uuid) throws Exception;

	public String delete(String uuid) throws Exception;
	
	public String activateFDType(String uuid,Map<String, String> reqParam) throws Exception;
	
	public String deActivateFDType(String uuid,Map<String, String> reqParam) throws Exception;
}
