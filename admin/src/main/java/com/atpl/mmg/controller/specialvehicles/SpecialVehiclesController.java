package com.atpl.mmg.controller.specialvehicles;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

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
import com.atpl.mmg.model.specialvehicles.SpecialVehiclesModel;
import com.atpl.mmg.service.specialvehicles.SpecialVehiclesService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class SpecialVehiclesController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(SpecialCategoryController.class);

	@Autowired
	SpecialVehiclesService specialVehiclesService;

	@RequestMapping(value = "/saveSpecialVehicle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addSpecialVehicle(@RequestBody SpecialVehiclesModel specialVehiclesModel)
			throws Exception {
		return prepareSuccessResponse(specialVehiclesService.addSpecialVehicle(specialVehiclesModel));
	}

	@RequestMapping(value = "/specialVehicle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getSpecialVehicle() throws Exception {
		return prepareSuccessResponse(specialVehiclesService.getSpecialVehicle());
	}

	@RequestMapping(value = "/special/vehcile", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> UpdateSpecialVehicle(@RequestBody SpecialVehiclesModel specialVehiclesModel)
			throws Exception {
		return prepareSuccessResponse(specialVehiclesService.UpdateSpecialVehicle(specialVehiclesModel));
	}

	@RequestMapping(value = "/special/vehicle/{vehicleCategoryId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteSpecialVehicle(@PathVariable("vehicleCategoryId") int vehicleCategoryId)
			throws Exception {
		return prepareSuccessResponse(specialVehiclesService.deleteSpecialVehicle(vehicleCategoryId));
	}

}
