package com.atpl.mmg.controller.faredist;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.List;
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
import com.atpl.mmg.model.faredist.FareDistributionDTo;
import com.atpl.mmg.model.faredist.FareDistributionModel;
import com.atpl.mmg.service.faredist.FareDistributionService;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/v1")
public class FareDistributionController {

	@Autowired
	FareDistributionService fareDistributionService;

	@RequestMapping(value = "/fare/distribution", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody FareDistributionModel fareDistributionModel) throws Exception {
		return prepareSuccessResponse(fareDistributionService.save(fareDistributionModel));
	}

	@RequestMapping(value = "/fare/distribution", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> update(@RequestBody FareDistributionDTo fareDistributionModel)
			throws Exception {
		return prepareSuccessResponse(fareDistributionService.update(fareDistributionModel));
	}

	@RequestMapping(value = "/fare/distribution/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFareDistribution(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(fareDistributionService.getFareDistribution(uuid));
	}

	@RequestMapping(value = "/fare/distribution", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFareDistribution(@RequestParam Map<String, String> reqParam) throws Exception {
		if (reqParam.size() == 0)
			return prepareSuccessResponse(fareDistributionService.getFareDistributiones(reqParam));
		else
			return prepareSuccessResponse(fareDistributionService.getFareDistributiones(reqParam));
	}

	@RequestMapping(value = "/fare/distribution/franchise/{franchiseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFareDistributionByFDTypeId(@PathVariable("franchiseId") String franchiseId,
			@RequestParam Map<String, String> reqParam) throws Exception {
		if (reqParam.size() == 0)
			return prepareSuccessResponse(fareDistributionService.getFareDistributiones(franchiseId, reqParam));
		else
			return prepareSuccessResponse(fareDistributionService.getFareDistributiones(franchiseId, reqParam));
	}

	@RequestMapping(value = "/fare/distribution/franchise/{franchiseId}/fDType/{fDTypeId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> delete(@PathVariable("franchiseId") String franchiseId,@PathVariable("fDTypeId") List<String> fDTypeId)
			throws Exception {
		return prepareSuccessResponse(fareDistributionService.delete(franchiseId,fDTypeId));
	}

	@RequestMapping(value = "/fare/distribution/activate/{fareDistributionTypeId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> activateFD(@PathVariable("fareDistributionTypeId") String fareDistributionTypeId)
			throws Exception {
		return prepareSuccessResponse(fareDistributionService.activateFD(fareDistributionTypeId, null));
	}

	@RequestMapping(value = "/fare/distribution/deactivate/{fareDistributionTypeId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deActivateFD(
			@PathVariable("fareDistributionTypeId") String fareDistributionTypeId) throws Exception {
		return prepareSuccessResponse(fareDistributionService.deActivateFD(fareDistributionTypeId, null));
	}
	
}
