package com.atpl.mmg.controller.leadstatus;

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

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.lead.LeadModel;
import com.atpl.mmg.service.leadstatus.LeadStatusService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class LeadStatusController {

	private static final Logger logger = LoggerFactory.getLogger(LeadStatusController.class);

	@Autowired
	LeadStatusService leadStatusService;

	@RequestMapping(value = "/leadstatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody LeadModel leadModel) throws Exception {
		return prepareSuccessResponse(leadStatusService.save(leadModel));
	}

	@RequestMapping(value = "/leadstatus", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> update(@RequestBody LeadModel leadModel) throws Exception {
		return prepareSuccessResponse(leadStatusService.update(leadModel));
	}

	@RequestMapping(value = "/leadstatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getLeadStatus(@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(leadStatusService.getLeadStatus(reqParam));
	}
	
	@RequestMapping(value = "/leadstatus/{uuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> delete(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(leadStatusService.delete(uuid));
	}
}
