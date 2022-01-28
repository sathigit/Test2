package com.atpl.mmg.service.dashboard;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.model.dashboard.DashboardModel;
import com.atpl.mmg.model.dashboard.ReportDashboard;
import com.atpl.mmg.model.route.RouteDashboard;

public interface DashboardService {

	public DashboardModel getVehicle() throws Exception;

	public int getTrip() throws Exception;

	public DashboardModel getDriver() throws Exception;

	public int dashboardVehicle() throws Exception;

	public DashboardModel getHomePageCounts() throws Exception;

	public int getQuotation(String customerId) throws Exception;

	public DashboardModel getTotalEarning(int roleId, @RequestParam Map<String, String> reqParam) throws Exception;
	
	public DashboardModel getRouteCount(@RequestParam Map<String, String> reqParam) throws Exception;

	public DashboardModel getRouteCountOnStateCity(@RequestParam Map<String, String> reqParam) throws Exception;
	
	public RouteDashboard getRouteReport(String routeId,ReportDashboard reportDashboard) throws Exception;


}
