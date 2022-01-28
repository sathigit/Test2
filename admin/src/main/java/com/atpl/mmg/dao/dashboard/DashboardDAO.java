package com.atpl.mmg.dao.dashboard;

import com.atpl.mmg.domain.dashboard.DashboardDomain;

public interface DashboardDAO {
	
	public DashboardDomain getRouteCount(Boolean status) throws Exception;
	
	public DashboardDomain getRouteCountOnStateCity(Boolean status,String source,String destination,int cityId,int stateId) throws Exception;

}
