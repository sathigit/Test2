package com.atpl.mmg.dao.leadprofession;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.lead.LeadDomain;

public interface LeadProfessionDAO {

	public String save(LeadDomain leadDomain) throws Exception;

	public String update(LeadDomain leadDomain) throws Exception;

	public List<LeadDomain> getLeadProfession(int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getLeadProfessionCount() throws Exception;
	
	public LeadDomain getLeadProfession(String uuid) throws Exception;

	public String delete(String uuid) throws Exception;
}
