package com.atpl.mmg.dao.leadstatus;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.lead.LeadDomain;

public interface LeadStatusDAO {

	public String save(LeadDomain leadDomain) throws Exception;

	public String update(LeadDomain leadDomain) throws Exception;

	public List<LeadDomain> getLeadStatus(int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getLeadStatusCount() throws Exception;

	public LeadDomain getLeadStatus(String uuid) throws Exception;

	public String delete(String uuid) throws Exception;
}
