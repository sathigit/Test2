package com.atpl.mmg.dao.report;

import java.util.List;

import com.atpl.mmg.domain.report.RevenueDomain;


public interface RevenueDAO {
	public String addRevenue(RevenueDomain revenueDomain) throws Exception;
	
	public String addRevenueSource(RevenueDomain revenueDomain) throws Exception;
	
	public RevenueDomain getRevenueSource(int id) throws Exception;
	
	public String updateRevenueSource(RevenueDomain id) throws Exception;
	
	public String deleteRevenueSource(int id)throws Exception;
	
	public List<RevenueDomain> getRevenueSource() throws Exception;

}
