package com.atpl.mmg.controller.location;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.service.location.LocationService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class LocationController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

	@Autowired
	LocationService locationService;

	
	@RequestMapping(value = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getLocation() throws Exception {
		return prepareSuccessResponse(locationService.getLocation());
	}
	
	@RequestMapping(value = "/location/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getLocationById(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(locationService.getLocation(id));
	}
	
	
}