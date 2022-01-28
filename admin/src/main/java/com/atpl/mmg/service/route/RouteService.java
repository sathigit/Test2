package com.atpl.mmg.service.route;

import java.util.Map;

import com.atpl.mmg.model.route.RouteDetailsModel;
import com.atpl.mmg.model.route.RouteModel;
import com.atpl.mmg.model.route.RouteModelDTO;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface RouteService {

	public RouteModel addRouteDetails(RouteDetailsModel routeModel) throws Exception;

	public RouteModel getRouteDetailsById(String routeId) throws Exception;

	public RouteModelDTO getRouteDetailsByRouteId(String uuid) throws Exception;
	
	public RouteModelDTO getRouteById(String uuid) throws Exception;

	public RouteModel editRoute(RouteDetailsModel routeDetailsModel) throws Exception;

	public String deleteRoute(String routeId) throws Exception;

	public String updateRouteStatus(String uuid, Map<String, String> reqParam) throws Exception;

	public ListDto getAllRouteBySourceAndDestination(String[] sCities, Map<String, String> reqParam) throws Exception;

	public ListDto getAllRouteDetails(Map<String, String> reqParam) throws Exception;

}