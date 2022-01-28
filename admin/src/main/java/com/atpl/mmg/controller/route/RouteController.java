package com.atpl.mmg.controller.route;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.route.RouteDetailsModel;
import com.atpl.mmg.service.route.RouteService;

@RestController
@RequestMapping("/v1")
public class RouteController {
	
	@Autowired
	RouteService routeService;
	
	@RequestMapping(value = "/route", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addRoute(@RequestBody RouteDetailsModel route) throws Exception {
		return prepareSuccessResponse(routeService.addRouteDetails(route));
	}

	@RequestMapping(value = "/route/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRouteDetailsById(@PathVariable("uuid") String uuid)
			throws Exception {
		return prepareSuccessResponse(routeService.getRouteDetailsByRouteId(uuid));
	}

	@RequestMapping(value = "/route/details/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRouteById(@PathVariable("uuid") String uuid)
			throws Exception {
		return prepareSuccessResponse(routeService.getRouteById(uuid));
	}

	@RequestMapping(value = "/route", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getAllRoute(@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(routeService.getAllRouteDetails(reqParam));
	}

	@RequestMapping(value = "/route", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateRoute(@RequestBody RouteDetailsModel routeDetailsModel) throws Exception {
		return prepareSuccessResponse(routeService.editRoute(routeDetailsModel));
	}

	@RequestMapping(value = "/route/{uuid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteRoute(@PathVariable("uuid") String routeId)
			throws Exception {
		return prepareSuccessResponse(routeService.deleteRoute(routeId));
	}
	
	@RequestMapping(value = "/route/status/{uuid}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> activateRoute(@PathVariable("uuid") String uuid,@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(routeService.updateRouteStatus(uuid,reqParam));
	}
	
	@RequestMapping(value = "/route/source/destination/{sCities}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getAllRouteBySourceAndDestination(@PathVariable("sCities") String[] sCities,@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(routeService.getAllRouteBySourceAndDestination(sCities,reqParam));
	}
	

}