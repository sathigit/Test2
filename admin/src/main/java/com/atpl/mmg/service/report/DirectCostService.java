package com.atpl.mmg.service.report;

import java.util.List;

import com.atpl.mmg.model.report.DirectCostModel;


public interface DirectCostService {
	
	public String addDirectCost(DirectCostModel directCostModel) throws Exception;
	
	public String addExpenseSource(DirectCostModel directCostModel) throws Exception;
	
	public DirectCostModel getDirectCostSource(int id) throws Exception;
	
	public String updateDirectCost(DirectCostModel id) throws Exception;

	public String deleteDirectCost(int id)throws Exception;
	
	public List<DirectCostModel> getDirectCostSource() throws Exception;
}
