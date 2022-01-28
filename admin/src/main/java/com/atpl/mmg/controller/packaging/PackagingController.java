package com.atpl.mmg.controller.packaging;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.math.BigInteger;
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

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.packaging.PackagingModel;
import com.atpl.mmg.service.packaging.PackagingService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class PackagingController implements Constants {

	@Autowired
	PackagingService packagingService;

	@RequestMapping(value = "/save/packaging", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> savePackaging(@RequestBody PackagingModel packagingModel) throws Exception {
		return prepareSuccessResponse(packagingService.savePackaging(packagingModel));
	}

	@RequestMapping(value = "/packaging/{packagingId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getPackageDetails(@PathVariable("packagingId") BigInteger packagingId)
			throws Exception {
		return prepareSuccessResponse(packagingService.getPackageDetails(packagingId));
	}

	@RequestMapping(value = "/packaging/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getPackagingList(@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(packagingService.getPackagingList(reqParam));
	}

	@RequestMapping(value = "/packaging/status", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updatePackagingStatus(@RequestBody PackagingModel packagingModel) throws Exception {
		return prepareSuccessResponse(packagingService.updatePackagingStatus(packagingModel));
	}

	@RequestMapping(value = "/packaging/name", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updatePackagingName(@RequestBody PackagingModel packagingModel) throws Exception {
		return prepareSuccessResponse(packagingService.updatePackagingName(packagingModel));
	}
	
	@RequestMapping(value = "/delete/packaging/{packagingId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deletePackaging(@PathVariable("packagingId") BigInteger packagingId)
			throws Exception {
		return prepareSuccessResponse(packagingService.deletePackaging(packagingId));
	}
}
