package com.atpl.mmg.dao.route;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.route.RouteDomain;

public interface RouteDao {

	public RouteDomain getRouteById(String uuid) throws Exception;

	public List<RouteDomain> getAllRoute(Boolean status, int lowerBound, int upperBound) throws Exception;

	public String updateRoute(RouteDomain routeDomain) throws Exception;

	public DashboardDomain getRouteCount(Boolean status) throws Exception;

	public String deleteRoute(String routeId) throws Exception;

	public String updateRouteStatus(String uuid, boolean status) throws Exception;

	public List<RouteDomain> getRouteBySourceAndDestination(Boolean status, String source, String destination,
			int lowerBound, int upperBound) throws Exception;

	public DashboardDomain getCountRouteBySourceAndDestination(Boolean status, String source, String destination)
			throws Exception;

	public String addRoute(RouteDomain routeDomain) throws Exception;

	public RouteDomain validateRouteDetails(RouteDomain routeDomain) throws Exception;

	public RouteDomain validateRouteCityDetails(String source, String destination) throws Exception;

	public RouteDomain validateRouteDetailsOnLatLong(String sourcelat, String sourcelong, String dstlat, String dstlong)
			throws Exception;
}
