package com.atpl.mmg.service.organization;

import java.util.Map;

import com.atpl.mmg.model.organization.OrganizationModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface OrganizationService {
	
	public String saveOrganization(OrganizationModel organizationModel) throws Exception;
	
	public OrganizationModel getOrganization(int  id) throws Exception;
	
	public ListDto getOrganization(Map<String,String> reqParam) throws Exception;

	public String updateOrganization(OrganizationModel id) throws Exception;

	public String deleteOrganization(int id)throws Exception;
}
