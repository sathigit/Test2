package com.atpl.mmg.dao.employee;

import com.atpl.mmg.domain.employee.CrmDomain;

public interface CrmDao {
	
	public String savePerformance(CrmDomain crmDomain) throws Exception;
	public CrmDomain getPerformance(int profileId) throws Exception ;
	public String updatePerformance(CrmDomain crmDomain) throws Exception;	
	

}
