package com.atpl.mmg.dao.leadremarks;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.lead.LeadDomain;

public interface LeadRemarksDAO {

	public String save(LeadDomain leadDomain) throws Exception;

	public String update(LeadDomain leadDomain) throws Exception;

	public List<LeadDomain> getLeadRemarks(int innerBound,int lowerBound) throws Exception;
	
	public LeadDomain getLeadRemarks(String uuid) throws Exception;
	
	public DashboardDomain getLeadRemarksCount() throws Exception;

	public String delete(String uuid) throws Exception;
}
