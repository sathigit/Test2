package com.atpl.mmg.controller.leadprofession;

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
import com.atpl.mmg.service.leadprofession.LeadProfessionService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class LeadProfessionController {

	private static final Logger logger = LoggerFactory.getLogger(LeadProfessionController.class);

	@Autowired
	LeadProfessionService leadProfessionService;

	@RequestMapping(value = "/leadprofession", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody LeadModel leadModel) throws Exception {
		return prepareSuccessResponse(leadProfessionService.save(leadModel));
	}

	@RequestMapping(value = "/leadprofession", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> update(@RequestBody LeadModel leadModel) throws Exception {
		return prepareSuccessResponse(leadProfessionService.update(leadModel));
	}

	@RequestMapping(value = "/leadprofession", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getLeadStatus(@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(leadProfessionService.getLeadProfession(reqParam));
	}

	@RequestMapping(value = "/leadprofession/{uuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> delete(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(leadProfessionService.delete(uuid));
	}
}
