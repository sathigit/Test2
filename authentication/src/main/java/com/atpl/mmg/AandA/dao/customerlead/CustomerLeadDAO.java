package com.atpl.mmg.AandA.dao.customerlead;

import java.util.List;

import com.atpl.mmg.AandA.domain.customerlead.CustomerLeadDomain;

public interface CustomerLeadDAO {
	
	public String save(List<CustomerLeadDomain> customerLeadDomain) throws Exception;
	
	public CustomerLeadDomain getCustomerLeadsByUuId(String uuid) throws Exception;
	
	public String update(CustomerLeadDomain customerLeadDomain) throws Exception;
	
	public List<CustomerLeadDomain> getCustomerLeads(int lowerBound, int upperBound) throws Exception;
	
	public List<CustomerLeadDomain> getCustomerLeadsByStsAndAssignedId(String status, String assignedId,int lowerBound, int upperBound) throws Exception;
	
	public List<CustomerLeadDomain> getCustomerLeadsByStsAndAssignedIdAndUploadedId(String status, String assignedId,String uploadedById,int lowerBound, int upperBound) throws Exception;
	
	public List<CustomerLeadDomain> getCustomerLeadsByStsAndUploadedById(String status, String uploadedById,int lowerBound, int upperBound) throws Exception;
	
	public String updateAssignDetailsByUuid(CustomerLeadDomain customerLeadDomain) throws Exception;

}
