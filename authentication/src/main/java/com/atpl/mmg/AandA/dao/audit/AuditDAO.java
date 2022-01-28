package com.atpl.mmg.AandA.dao.audit;

import java.util.List;

import com.atpl.mmg.AandA.domain.Audit;

public interface AuditDAO {

	public Audit save(Audit audit) throws Exception;
	
	public Audit getAuditByUuid(String auditId) throws Exception;
	
	public List<Audit> getAudit(String userId,int roleId,String activity) throws Exception;
	
}
