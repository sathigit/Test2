package com.atpl.mmg.service.weight;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.model.weight.WeightModel;
import com.atpl.mmg.utils.ListDto;

public interface WeightService {

	@SuppressWarnings("rawtypes")
	public ListDto getWeight(@RequestParam Map<String,String> reqParam) throws Exception;

	public WeightModel getWeight(int id) throws Exception;
	
	public WeightModel getWeight(String weight) throws Exception;
}
