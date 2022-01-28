package com.atpl.mmg.service.leadremarks;

import java.util.Map;

import com.atpl.mmg.model.lead.LeadModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface LeadRemarksService {

	public String save(LeadModel leadModel) throws Exception;

	public String update(LeadModel leadModel) throws Exception;

	public ListDto getLeadRemarks(Map<String,String> reqParam) throws Exception;

	public String delete(String uuid) throws Exception;
}
