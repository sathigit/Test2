package com.atpl.mmg.controller.vehicle;

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
import com.atpl.mmg.model.vehicle.VehicleModel;
import com.atpl.mmg.service.vehicle.VehicleService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class VehicleController implements Constants {

	@Autowired
	VehicleService vehicleService;
	private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

	@RequestMapping(value = "/vehicle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveVehicle(@RequestBody VehicleModel vehicleModel) throws Exception {
		return prepareSuccessResponse(vehicleService.saveVehicle(vehicleModel));
	}

	@RequestMapping(value = "/vehicle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicle(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(vehicleService.getVehicle(reqParam));
	}

	@RequestMapping(value = "/vehicle/{goodsTypeId}/{kerbWeightId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicle(@PathVariable("goodsTypeId") String goodsTypeId,
			@PathVariable("kerbWeightId") String kerbWeightId,@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(vehicleService.getVehicle(goodsTypeId, kerbWeightId,reqParam));
	}

	@RequestMapping(value = "/vehicle/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicle(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(vehicleService.deleteVehicle(id));
	}

	@RequestMapping(value = "/vehicle/{vehicleCategoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicle(@PathVariable("vehicleCategoryId") String vehicleCategoryId) throws Exception {
		return prepareSuccessResponse(vehicleService.getVehicle(vehicleCategoryId));
	}
	
	@RequestMapping(value = "/vehicle/weight/{kerbWeightId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleByKerbWeightId(@PathVariable("kerbWeightId") String kerbWeightId,@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(vehicleService.getVehicleByKerbWeightId(kerbWeightId,reqParam));
	}

}
