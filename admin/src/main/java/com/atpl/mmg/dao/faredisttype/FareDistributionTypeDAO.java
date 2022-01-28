package com.atpl.mmg.dao.faredisttype;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.faredisttype.FareDistributionTypeDomain;

public interface FareDistributionTypeDAO {

	public String save(FareDistributionTypeDomain fareDistributionTypeDomain) throws Exception;

	public String update(FareDistributionTypeDomain fareDistributionTypeDomain) throws Exception;

	public List<FareDistributionTypeDomain> getFareDistributionTypes(boolean isActive,int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getFareDistributionTypesCount(boolean isActive) throws Exception;

	public List<FareDistributionTypeDomain> getFareDistributionTypes(int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getFareDistributionTypesCount() throws Exception;
	
	public FareDistributionTypeDomain getFareDistributionType(String uuid) throws Exception;
	
	public FareDistributionTypeDomain getFareDistributionTypeByType(String type) throws Exception;
	
	public String updateIsActive(boolean isActive,String uuid) throws Exception;

	public String delete(String uuid) throws Exception;
}
