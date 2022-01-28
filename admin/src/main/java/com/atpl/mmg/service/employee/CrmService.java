package com.atpl.mmg.service.employee;

import com.atpl.mmg.model.employee.CrmModel;

public interface CrmService {

	public String savePerformance(CrmModel crmModel) throws Exception;
	public CrmModel getPerformance(int profileId) throws Exception;
	public String updatePerformance(CrmModel crmModel) throws Exception;

	
}
