package com.atpl.mmg.service.blood;

import java.util.Map;

import com.atpl.mmg.model.blood.BloodModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface BloodService {

	ListDto getBloodGroup(Map<String, String> reqParam) throws Exception;

	public BloodModel getBloodGroupById(int id) throws Exception;

}
