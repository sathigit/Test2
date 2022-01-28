package com.atpl.mmg.dao.organization;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.organization.OrganizationDomain;
import com.atpl.mmg.exception.GenericRes;

@SuppressWarnings("unused")
public interface OrganizationDAO {
	
	public String saveOrganization(OrganizationDomain organizationDomain) throws Exception;
	
	public List<OrganizationDomain> getOrganization(int lowerBound,int upperBound) throws Exception;
	
	public OrganizationDomain getOrganization(int id) throws Exception;
	
	public DashboardDomain getOrganizationCount() throws Exception;
	
	public String updateOrganization(OrganizationDomain id) throws Exception;
	
	public String deleteOrganization(int id)throws Exception;
	
}
