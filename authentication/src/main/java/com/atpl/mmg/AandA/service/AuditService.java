package com.atpl.mmg.AandA.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.AandA.domain.Audit;

public interface AuditService {

	public String save(Audit audit) throws Exception;
	
	public List<Audit> getAudit(String userId,int roleId,@RequestParam Map<String,String> reqParam) throws Exception;
	
	public Audit getAuditByUuid(String uuid) throws Exception;

}
