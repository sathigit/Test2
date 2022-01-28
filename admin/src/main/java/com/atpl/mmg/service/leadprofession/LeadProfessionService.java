package com.atpl.mmg.service.leadprofession;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.model.lead.LeadModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface LeadProfessionService {

	public String save(LeadModel leadModel) throws Exception;

	public String update(LeadModel leadModel) throws Exception;

	public ListDto getLeadProfession(@RequestParam Map<String,String> reqParam) throws Exception;

	public String delete(String uuid) throws Exception;
}
