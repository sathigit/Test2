package com.atpl.mmg.controller.faredisttype;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

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

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.faredisttype.FareDistributionTypeModel;
import com.atpl.mmg.service.faredisttype.FareDistributionTypeService;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/v1")
public class FareDistributionTypeController {

	@Autowired
	FareDistributionTypeService fareDistributionTypeService;

	@RequestMapping(value = "/fare/distribution/type", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody FareDistributionTypeModel fareDistributionTypeModel)
			throws Exception {
		return prepareSuccessResponse(fareDistributionTypeService.save(fareDistributionTypeModel));
	}

	@RequestMapping(value = "/fare/distribution/type", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> update(@RequestBody FareDistributionTypeModel fareDistributionTypeModel)
			throws Exception {
		return prepareSuccessResponse(fareDistributionTypeService.update(fareDistributionTypeModel));
	}

	@RequestMapping(value = "/fare/distribution/type/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFareDistributionType(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(fareDistributionTypeService.getFareDistributionType(uuid));
	}

	@RequestMapping(value = "/fare/distribution/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFareDistributionType(@RequestParam Map<String, String> reqParam)
			throws Exception {
		if (reqParam.size() == 0)
			return prepareSuccessResponse(fareDistributionTypeService.getFareDistributionTypes(reqParam));
		else
			return prepareSuccessResponse(fareDistributionTypeService.getFareDistributionTypes(reqParam));
	}

	@RequestMapping(value = "/fare/distribution/type/{uuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> delete(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(fareDistributionTypeService.delete(uuid));
	}

	@RequestMapping(value = "/fare/distribution/type/activate/{uuid}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> activateFDType(@PathVariable("uuid") String uuid,
			@RequestParam Map<String, String> reqParam) throws Exception {
		if (reqParam.size() == 0)
			return prepareSuccessResponse(fareDistributionTypeService.activateFDType(uuid, reqParam));
		else
			return prepareSuccessResponse(fareDistributionTypeService.activateFDType(uuid,reqParam));
	}

	@RequestMapping(value = "/fare/distribution/type/deactivate/{uuid}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deActivateFDType(@PathVariable("uuid") String uuid,
			@RequestParam Map<String, String> reqParam) throws Exception {
		if (reqParam.size() == 0)
			return prepareSuccessResponse(fareDistributionTypeService.deActivateFDType(uuid,reqParam));
		else
			return prepareSuccessResponse(fareDistributionTypeService.deActivateFDType(uuid,reqParam));
	}

}
