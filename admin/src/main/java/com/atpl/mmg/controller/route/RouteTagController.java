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
import com.atpl.mmg.model.route.RouteTagModel;
import com.atpl.mmg.service.route.RouteTagService;

@RestController
@RequestMapping("/v1")
public class RouteTagController {
	
	@Autowired
	RouteTagService routeTagService;
	
	
	@RequestMapping(value = "/route/tag", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addRouteTag(@RequestBody RouteTagModel routeTagModel) throws Exception {
		return prepareSuccessResponse(routeTagService.addRouteTag(routeTagModel));
	}

	@RequestMapping(value = "/route/tag", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRouteTag(@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(routeTagService.getRouteTag(reqParam));
	}
	
	@RequestMapping(value = "/route/tag", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateRouteTag(@RequestBody RouteTagModel routeTagModel) throws Exception {
		return prepareSuccessResponse(routeTagService.updateRouteTag(routeTagModel));
	}
	
	@RequestMapping(value = "/route/tag/{uuid}/status/{status}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> activateRoute(@PathVariable("uuid") String uuid,@PathVariable("status") boolean status) throws Exception {
		return prepareSuccessResponse(routeTagService.updateRouteTagStatus(uuid,status));
	}

	@RequestMapping(value = "/route/tag/{uuid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteRouteTag(@PathVariable("uuid") String uuid)
			throws Exception {
		return prepareSuccessResponse(routeTagService.deleteRouteTag(uuid));
	}


}