package com.atpl.mmg.dao.report;

import java.util.List;

import com.atpl.mmg.domain.report.DirectCostDomain;

public interface DirectCostDAO {

	public String addDirectCost(DirectCostDomain directCostDomain) throws Exception;

	public String addDirectCostSource(DirectCostDomain directCostDomain) throws Exception;

	public DirectCostDomain getDirectCostSource(int id) throws Exception;

	public String updateDirectCost(DirectCostDomain id) throws Exception;

	public String deleteDirectCost(int id) throws Exception;

	public List<DirectCostDomain> getDirectCostSource() throws Exception;
}
