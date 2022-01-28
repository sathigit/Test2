package com.atpl.mmg.controller.employee;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.employee.CrmModel;
import com.atpl.mmg.service.employee.CrmService;

@SuppressWarnings({"rawtypes","unused"})
@RestController
@RequestMapping("/v1")
public class CrmController {

	private static final Logger logger = LoggerFactory.getLogger(CrmController.class);

	@Autowired
	CrmService crmService;
	

	@RequestMapping(value = "/savePerformance", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> savePerformance(@RequestBody CrmModel crmModel) throws Exception {
		return prepareSuccessResponse(crmService.savePerformance(crmModel));
	}
	
	@RequestMapping(value = "/getPerformance/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getPerformance(@PathVariable("profileId") int profileId) throws Exception {
		return prepareSuccessResponse(crmService.getPerformance(profileId));
	}
		
	@RequestMapping(value = "/updatePerformance", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updatePerformance(@RequestBody CrmModel crmModel) throws Exception {
		return prepareSuccessResponse(crmService.updatePerformance(crmModel));

	}

}
