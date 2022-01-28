package com.atpl.mmg.controller.vehiclecategory;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.vehiclecategory.VehicleCategoryModel;
import com.atpl.mmg.model.vehiclecategory.WeightModel;
import com.atpl.mmg.service.vehiclecategory.VehicleCategoryService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class VehicleCategoryController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(VehicleCategoryController.class);

	@Autowired
	VehicleCategoryService vehicleCategoryService;

	@RequestMapping(value = "/vehicleCategory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveVehicleCategory(@RequestBody VehicleCategoryModel vehicleCategoryModel)
			throws Exception {
		return prepareSuccessResponse(vehicleCategoryService.saveVehicleCategory(vehicleCategoryModel));
	}

	@RequestMapping(value = "/vehicleCategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleCategory(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(vehicleCategoryService.getVehicleCategory(reqParam));
	}

	@RequestMapping(value = "/vehicleCategory", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateVehicleCategory(@RequestBody VehicleCategoryModel vehicleCategoryModel)
			throws Exception {
		return prepareSuccessResponse(vehicleCategoryService.updateVehicleCategory(vehicleCategoryModel));
	}

	@RequestMapping(value = "/enable/vehicleCategory/{vehicleCategoryId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> enableVehicleCategory(@PathVariable("vehicleCategoryId") int vehicleCategoryId)
			throws Exception {
		return prepareSuccessResponse(vehicleCategoryService.enableVehicleCategory(vehicleCategoryId));
	}

	@RequestMapping(value = "/vehicleCategory/{vehicleCategoryId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteVehicleCategory(@PathVariable("vehicleCategoryId") int vehicleCategoryId)
			throws Exception {
		return prepareSuccessResponse(vehicleCategoryService.deleteVehicleCategory(vehicleCategoryId));
	}

	@RequestMapping(value = "/vehicleCategory/Id/{vehicleCategoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleCategory(@PathVariable("vehicleCategoryId") Integer vehicleCategoryId)
			throws Exception {
		return prepareSuccessResponse(vehicleCategoryService.getVehicleCategorybyId(vehicleCategoryId));
	}

	@RequestMapping(value = "/getVehicleCategorybyId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleCategorybyId() throws Exception {
		return prepareSuccessResponse(vehicleCategoryService.getVehicleCategorybyId());
	}

	@RequestMapping(value = "/vehicleCategory/{goodsTypeId}/{weightId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleCategorybyWeight(@PathVariable("goodsTypeId") String goodsTypeId,
			@PathVariable("weightId") int weightId,@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(vehicleCategoryService.getVehicle(goodsTypeId, weightId,reqParam));
	}
	
	@RequestMapping(value = "/vehicleCategory/weight", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehiclesByKerbweightId(@RequestBody WeightModel weightModel) throws Exception {
		return prepareSuccessResponse(vehicleCategoryService.getVehiclesByKerbweightId(weightModel));
	}

	@RequestMapping(value = "/direct/vehicles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehiclesByDirectBooking() throws Exception {
		return prepareSuccessResponse(vehicleCategoryService.getVehiclesByDirectBooking());
	}
}
