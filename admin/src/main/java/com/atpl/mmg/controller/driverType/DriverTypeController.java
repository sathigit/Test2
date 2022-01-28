package com.atpl.mmg.controller.driverType;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.service.driverType.DriverTypeService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class DriverTypeController implements Constants {

	@Autowired
	DriverTypeService driverTypeService;

	@RequestMapping(value = "/getDriverType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDriverType(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(driverTypeService.getDriverType(reqParam));
	}
	
	@RequestMapping(value = "/getDriverType/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDriverTypeById(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(driverTypeService.getDriverTypeById(id));
	}
	@RequestMapping(value = "/getLicenceCat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getLicenceCategory(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(driverTypeService.getLicenceCategory(reqParam));
	}
	@RequestMapping(value = "/getLicenceCat/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getLicenceCategoryById(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(driverTypeService.getLicenceCategoryById(id));
	}
	
}
