package com.atpl.mmg.service.leadstatus;

import java.util.Map;

import com.atpl.mmg.model.lead.LeadModel;
import com.atpl.mmg.utils.ListDto;

public interface LeadStatusService {

	public String save(LeadModel leadModel) throws Exception;

	public String update(LeadModel leadModel) throws Exception;

	@SuppressWarnings("rawtypes")
	public ListDto getLeadStatus(Map<String,String> reqParam) throws Exception;

	public String delete(String uuid) throws Exception;
}
