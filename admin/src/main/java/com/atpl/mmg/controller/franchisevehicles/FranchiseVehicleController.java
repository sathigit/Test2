package com.atpl.mmg.controller.franchisevehicles;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.atpl.mmg.service.franchisevehicles.FranchiseVehicleService;

@RestController
@RequestMapping("v1")
@SuppressWarnings("rawtypes")
public class FranchiseVehicleController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(FranchiseVehicleController.class);

	@Autowired
	FranchiseVehicleService franchiseVehicleService;

	@RequestMapping(value = "/franchise/vehicle/images/{goodsTypeId}/{kerbWeightId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleCategory(@PathVariable("goodsTypeId") String goodsTypeId,
			@PathVariable("kerbWeightId") String kerbWeightId, @RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(franchiseVehicleService.getAllVehicleImages(goodsTypeId, kerbWeightId,reqParam));
	}

	@RequestMapping(value = "/single/vehicle/images/{goodsTypeId}/{kerbWeightId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getSingleVehicleImages(@PathVariable("goodsTypeId") String goodsTypeId,
			@PathVariable("kerbWeightId") String kerbWeightId, @RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(franchiseVehicleService.getSingleVehicleImages(goodsTypeId, kerbWeightId,reqParam));
	}
}
