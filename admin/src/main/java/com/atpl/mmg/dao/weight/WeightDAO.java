package com.atpl.mmg.dao.weight;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.weight.WeightDomain;

public interface WeightDAO {

	public List<WeightDomain> getWeight(int lowerBound,int upperBound) throws Exception;

	public WeightDomain getWeight(int id) throws Exception;
	
	public WeightDomain getWeight(String weight) throws Exception;
	
	public DashboardDomain getWeightCount() throws Exception;
}
