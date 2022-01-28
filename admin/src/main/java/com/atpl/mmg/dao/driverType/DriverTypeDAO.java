package com.atpl.mmg.dao.driverType;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.driverType.DriverTypeDomain;

public interface DriverTypeDAO {

	public List<DriverTypeDomain> getDriverType(int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getDriverTypeCount() throws Exception;
	
	public DriverTypeDomain getDriverTypeById(int id) throws Exception;
	
	public List<DriverTypeDomain> getLicenceCategory(int lowerBound,int upperBound) throws Exception;
	
	public DriverTypeDomain getLicenceCategoryById(int id) throws Exception;

	public DashboardDomain getLicenceCategoryCount() throws Exception;
}
