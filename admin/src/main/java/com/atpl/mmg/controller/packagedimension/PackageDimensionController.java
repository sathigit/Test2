package com.atpl.mmg.controller.packagedimension;

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
import com.atpl.mmg.model.packagedimension.PackageDimensionModel;
import com.atpl.mmg.service.packagedimension.PackageDimensionService;

@RestController
@RequestMapping("/v1")

public class PackageDimensionController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(PackageDimensionController.class);

	@Autowired
	PackageDimensionService packageDimensionService;

	@RequestMapping(value = "/package/dimension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody PackageDimensionModel packageDimensionModel) throws Exception {
		return prepareSuccessResponse(packageDimensionService.save(packageDimensionModel));
	}

	@RequestMapping(value = "/package/dimension", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getPackageDimension( @RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(packageDimensionService.getPackageDimension(reqParam));
	}

	@RequestMapping(value = "/package/dimension/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getPackageDimensionByuuId(@PathVariable("uuid") String uuId) throws Exception {
		return prepareSuccessResponse(packageDimensionService.getPackageDimensionByuuId(uuId));
	}

	@RequestMapping(value = "/package/dimension", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> update(@RequestBody PackageDimensionModel packageDimensionModel)
			throws Exception {
		return prepareSuccessResponse(packageDimensionService.update(packageDimensionModel));
	}

	@RequestMapping(value = "/package/dimension/{uuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> delete(@PathVariable("uuid") String uuId) throws Exception {
		return prepareSuccessResponse(packageDimensionService.delete(uuId));
	}

	@RequestMapping(value = "/package/dimension/{uuid}/status/{status}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateStatus(@PathVariable("uuid") String uuid,
			@PathVariable("status") boolean status) throws Exception {
		return prepareSuccessResponse(packageDimensionService.updateStatus(uuid, status));
	}

}
