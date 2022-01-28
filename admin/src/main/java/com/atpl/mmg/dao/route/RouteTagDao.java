package com.atpl.mmg.dao.route;

import java.util.List;

import com.atpl.mmg.domain.route.RouteTagDomain;

public interface RouteTagDao {	
	
	public String addRouteTag(RouteTagDomain RouteTagDomain) throws Exception;
	
	public List<RouteTagDomain> getAllRouteTag() throws Exception;
	
	public List<RouteTagDomain> getAllRouteTag(String routeId,String vendorId,String tagLocation,String vehicleId) throws Exception;
	
	public List<RouteTagDomain> getAllRouteTagVehiclesVendors(String routeId,boolean isVehicle,int roleId) throws Exception;
	
	public RouteTagDomain getRouteTagById(String uuid) throws Exception;
	
	public RouteTagDomain validateRouteTag(String routeId,String vendorId,int roleId,String vehicleId) throws Exception;
	
	public String updateRouteTagStatus(String uuid,boolean status) throws Exception;
	
	public String updateRouteTag(RouteTagDomain RouteTagDomain) throws Exception;
	
	public String deleteRouteTag(String uuid) throws Exception;


}


