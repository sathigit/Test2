package com.atpl.mmg.AandA.service.customerlead;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.atpl.mmg.AandA.model.customerlead.CustomerLeadModel;
import com.atpl.mmg.AandA.model.customerlead.CustomerLeadTemplateModel;
import com.atpl.mmg.AandA.model.profile.ListDto;

@SuppressWarnings("rawtypes")
public interface CustomerLeadService {
	
	public String uploadCustomerLeadsFile(MultipartFile file,String bdmId) throws Exception;
	
	public CustomerLeadModel getCustomerLeadsByUuId(String uuid) throws Exception;
	
	public String update(CustomerLeadModel customerLeadodel) throws Exception;
	
	public ListDto getCustomerLeadsByStsAndAssignedId(String status, String assignedId,Map<String, String> reqParam) throws Exception;
	
	public ListDto getCustomerLeadsByStsAndAssignedIdAndUploadedId(String status, String assignedId,String uploadedId,Map<String, String> reqParam) throws Exception;
	
	public ListDto getCustomerLeadsByStsAndUploadedById(String status, Map<String, String> reqParam) throws Exception;

	public String businessExecutiveAssignByListOfUuid(CustomerLeadTemplateModel customerLeadModel) throws Exception;
	
	public String updateStsAndCallDateByUuid(CustomerLeadModel customerLeadModel) throws Exception;
}
