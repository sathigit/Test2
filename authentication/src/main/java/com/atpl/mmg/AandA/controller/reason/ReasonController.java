package com.atpl.mmg.AandA.controller.reason;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.model.reason.ReasonModel;
import com.atpl.mmg.AandA.service.reason.ReasonService;

@RestController
@RequestMapping("/v2")
@SuppressWarnings("rawtypes")
public class ReasonController {

	@Autowired
	ReasonService reasonService;
	
	@RequestMapping(value = "/reason", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody ReasonModel reasonModel) throws Exception {
		return prepareSuccessResponse(reasonService.save(reasonModel,false));
	}

	@RequestMapping(value = "/reason/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getReasonsByRoleId(@PathVariable("roleId") int roleId,@RequestParam Map<String, String> reqParam, HttpServletRequest req) throws Exception {
		return prepareSuccessResponse(reasonService.getReasonsByRoleId(roleId,reqParam));
	}
	
	@RequestMapping(value = "/reason/role/{roleId}/profile/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getReasonByRoleAndProfile(@PathVariable("roleId") int roleId,@PathVariable("profileId") String profileId,@RequestParam Map<String, String> reqParam, HttpServletRequest req) throws Exception {
		return prepareSuccessResponse(reasonService.getReasonsByRoleAndProfileId(roleId,profileId,reqParam));
	}
	
	@RequestMapping(value = "/reason/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getReasonsByUuid(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(reasonService.getReasonByUuid(uuid));
	}
	
}
