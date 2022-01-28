package com.atpl.mmg.controller.termsandcondition;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.termsandcondition.TandCModel;
import com.atpl.mmg.service.termsandcondition.TandCService;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/v1")
public class TandCController {
	private static final Logger logger = LoggerFactory.getLogger(TandCController.class);

	@Autowired
	TandCService tandCService;

	@RequestMapping(value = "/terms/conditions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveTandC(@RequestBody TandCModel tandCModel) throws Exception {
		return prepareSuccessResponse(tandCService.saveTandC(tandCModel));
	}

	@RequestMapping(value = "/terms/conditions/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getTandCListByRoleB(@PathVariable("roleId") int roleId,
			@RequestParam Map<String, String> reqParam, HttpServletRequest req) throws Exception {
			return prepareSuccessResponse(tandCService.getTandCListByRoleAndStatus(roleId, reqParam));
	}
	
	@RequestMapping(value = "/terms/conditions/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getTandCById(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(tandCService.getTandCById(uuid));
	}

	@RequestMapping(value = "/terms/conditions/isActive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getTandCList(@RequestParam(name = "isActive") boolean isActive,@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(tandCService.getTandCList(isActive,reqParam));
	}
	
	@RequestMapping(value = "/terms/conditions/activate/{uuid}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> activateTandC(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(tandCService.activateTandC(uuid));
	}
	
	@RequestMapping(value = "/terms/conditions/deactivate/{uuid}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deActivateTandC(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(tandCService.deActivateTandC(uuid));
	}

}
