package com.atpl.mmg.controller.image;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.service.image.VehicleImageService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class VehicleImageController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(VehicleImageController.class);

	@Autowired
	VehicleImageService vehicleImageService;

	@PostMapping(value = "/vehicleImage")
	@ResponseBody
	public ResponseEntity<GenericRes> saveVehicleImage(@RequestParam("file") MultipartFile file,
			@RequestParam("vehicleCategoryId") Integer vehicleCategoryId, RedirectAttributes redirectAttributes) throws Exception {
		return prepareSuccessResponse(vehicleImageService.saveVehicleImage(vehicleCategoryId, file));
	}

	@RequestMapping(value = "/vehicleImage/category", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleImagesbyCategory() throws Exception {
		return prepareSuccessResponse(vehicleImageService.getVehicleImagesbyCategory());
	}

	@RequestMapping(value = "/vehicleImage/category/id/{vehicleCategoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleImagesbyCategoryId(
			@PathVariable("vehicleCategoryId") int vehicleCategoryId) throws Exception {
		return prepareSuccessResponse(vehicleImageService.getVehicleImagesbyCategoryId(vehicleCategoryId));
	}

	@RequestMapping(value = "/vehicleImage/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleImagesbyType() throws Exception {
		return prepareSuccessResponse(vehicleImageService.getVehicleImagesbyType());
	}

	@RequestMapping(value = "/vehicleImage/type/id/{vehicleTypeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleImagesbyTypeId(@PathVariable("vehicleTypeId") int vehicleTypeId)
			throws Exception {
		return prepareSuccessResponse(vehicleImageService.getVehicleImagesbyTypeId(vehicleTypeId));
	}

	@RequestMapping(value = "/vehicleImages/franchise/{goodsTypeId}/{kerbWeightId}/{origin}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicleImagesbyTypeId(@PathVariable("goodsTypeId") String goodsTypeId,
			@PathVariable("kerbWeightId") String kerbWeightId, @PathVariable("origin") String origin) throws Exception {
		return prepareSuccessResponse(vehicleImageService.getVehicleImagesFranchise(goodsTypeId, kerbWeightId, origin));
	}

}