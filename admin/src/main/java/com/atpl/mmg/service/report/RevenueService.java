package com.atpl.mmg.service.report;

import java.util.List;

import com.atpl.mmg.model.report.RevenueModel;

public interface RevenueService {
	
	public String addrevenue(RevenueModel revenueModel) throws Exception;
	
	public String addrevenueSource(RevenueModel revenueModel) throws Exception;
	
	public RevenueModel getRevenueSource(int id) throws Exception;

	public String updateRevenueSource(RevenueModel id) throws Exception;

	public String deleteRevenueSource(int id)throws Exception;

	public List<RevenueModel> getRevenueSource() throws Exception;
}