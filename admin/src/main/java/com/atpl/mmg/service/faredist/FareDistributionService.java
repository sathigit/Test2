package com.atpl.mmg.service.faredist;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.model.faredist.EarningsModel;
import com.atpl.mmg.model.faredist.FareDistributionDTo;
import com.atpl.mmg.model.faredist.FareDistributionModel;

public interface FareDistributionService {

	public String save(FareDistributionModel fareDistributionModel) throws Exception;

	public String update(FareDistributionDTo fareDistributionModel) throws Exception;

	public List<FareDistributionDTo> getFareDistributiones(Map<String, String> reqParam) throws Exception;

	public List<FareDistributionDTo> getFareDistributiones(String franchiseId, Map<String, String> reqParam)
			throws Exception;

	public FareDistributionModel getFareDistribution(String uuid) throws Exception;

	public String delete(String franchiseId,List<String> fdTypeId) throws Exception;

	public String activateFD(String fareDistributionTypeId, String franchiseId) throws Exception;

	public String deActivateFD(String fareDistributionTypeId, String franchiseId) throws Exception;
	
}
