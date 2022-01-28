package com.atpl.mmg.controller.agriVehicle;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.agriVehicle.AgriVehicleModel;
import com.atpl.mmg.service.agriVehicle.AgriVehicleService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class AgriVehicleController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(AgriVehicleController.class);

	@Autowired
	AgriVehicleService agriVehicleService;

	@RequestMapping(value = "/saveAgriVehcile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody 
	public ResponseEntity<GenericRes> saveAgriVehcile(@RequestBody @Valid AgriVehicleModel agriVehicleModel) throws Exception {
		return prepareSuccessResponse(agriVehicleService.saveAgriVehcile(agriVehicleModel));
	}

	@RequestMapping(value = "/agriVehicle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getAgriVehcile() throws Exception {
		return prepareSuccessResponse(agriVehicleService.getAgriVehcile());
	}

	@RequestMapping(value = "/agriVehicle/{vehicleCategoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody 
	public ResponseEntity<GenericRes> agriVehcile(@PathVariable("vehicleCategoryId") int vehicleCategoryId)
			throws Exception {
		return prepareSuccessResponse(agriVehicleService.agriVehcile(vehicleCategoryId));
	}

	@RequestMapping(value = "/agri/vehicle", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateAgriVehicle(@RequestBody AgriVehicleModel agriVehicleModel)
			throws Exception {
		return prepareSuccessResponse(agriVehicleService.updateAgriVehicle(agriVehicleModel));
	}

	@RequestMapping(value = "/agri/vehicle/delete/{vehicleCategoryId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteAgriVehicleCategory(
			@PathVariable("vehicleCategoryId") int vehicleCategoryId) throws Exception {
		return prepareSuccessResponse(agriVehicleService.deleteAgriVehicleCategory(vehicleCategoryId));
	}

}