package com.atpl.mmg.service.route;

import java.util.List;
import java.util.Map;

import com.atpl.mmg.model.route.RouteTagDetailsModel;
import com.atpl.mmg.model.route.RouteTagModel;

public interface RouteTagService {
	
	public String addRouteTag(RouteTagModel routeTag) throws Exception;
	
    public List<RouteTagDetailsModel> getRouteTag(Map<String,String> reqParam)throws Exception;
    
	public String updateRouteTag(RouteTagModel routeTag) throws Exception;
	
	public String updateRouteTagStatus(String uuid, boolean status)throws Exception;
	
	public String deleteRouteTag(String uuid) throws Exception;

}