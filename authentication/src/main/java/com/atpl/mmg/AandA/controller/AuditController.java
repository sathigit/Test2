package com.atpl.mmg.AandA.controller;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

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

import com.atpl.mmg.AandA.domain.Audit;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.service.AuditService;

@RestController
@RequestMapping("/v2")
@SuppressWarnings("rawtypes")
public class AuditController {

	@Autowired
	AuditService auditService;
	
	@RequestMapping(value = "/audit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody Audit audit) throws Exception {
		return prepareSuccessResponse(auditService.save(audit));
	}

	@RequestMapping(value = "/audit/userId/{userId}/roleId/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getAudit(@PathVariable("userId") String userId,@PathVariable("roleId") int roleId,@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(auditService.getAudit(userId,roleId,reqParam));
	}
	
	@RequestMapping(value = "/audit/{auditId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getAuditByUuid(@PathVariable("auditId") String auditId) throws Exception {
		return prepareSuccessResponse(auditService.getAuditByUuid(auditId));
	}
	
}
