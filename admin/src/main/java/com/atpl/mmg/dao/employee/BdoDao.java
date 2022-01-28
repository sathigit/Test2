package com.atpl.mmg.dao.employee;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.employee.BdoDomain;
import com.atpl.mmg.exception.GenericRes;

/* Author:Sindhu
 * creationDate:17-11-2019
 * Description:Bdo Mapping to Bdm*/

@SuppressWarnings("unused")
public interface BdoDao {

	public String saveBdo(BdoDomain BdoDomain) throws Exception;

	public List<BdoDomain> getbdoList(int bdmId,int lowerBound,int upperBound) throws Exception;

	public BdoDomain getbdoCount(int bdmId) throws Exception;

	public BdoDomain getbdoFranchise(int bdoId) throws Exception;

	public BdoDomain getbdo(String franchiseId) throws Exception;

	public BdoDomain getBusinessExecutiveCountByBdmId(int bdmId) throws Exception;
	
}
