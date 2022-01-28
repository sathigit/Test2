package com.atpl.mmg.AandA.controller.customerlead;

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
import org.springframework.web.multipart.MultipartFile;

import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.model.customerlead.CustomerLeadModel;
import com.atpl.mmg.AandA.model.customerlead.CustomerLeadTemplateModel;
import com.atpl.mmg.AandA.service.customerlead.CustomerLeadService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings({ "rawtypes", "unused" })
public class CustomerLeadController {

	@Autowired
	CustomerLeadService customerLeadService;

	@RequestMapping(value = "/upload/customerlead", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<GenericRes> uploadCustomerLeadsFile(@RequestParam("file") MultipartFile file,
			@RequestParam("bdmId") String bdmId, HttpServletRequest request) throws Exception {
		return prepareSuccessResponse(customerLeadService.uploadCustomerLeadsFile(file, bdmId));
	}

	@RequestMapping(value = "/customerlead/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerLeadsByUuId(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(customerLeadService.getCustomerLeadsByUuId(uuid));
	}

	@RequestMapping(value = "/customerlead", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> update(@RequestBody CustomerLeadModel customerLeadModel) throws Exception {
		return prepareSuccessResponse(customerLeadService.update(customerLeadModel));
	}

	@RequestMapping(value = "/customerlead/status/{status}/assigned/{assignedId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerLeadsByStsAndAssignedId(@PathVariable("status") String status,
			@PathVariable("assignedId") String assignedId, @RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(customerLeadService.getCustomerLeadsByStsAndAssignedId(status, assignedId,reqParam));
	}

	@RequestMapping(value = "/customerlead/status/{status}/assigned/{assignedId}/uploaded/{uploadedById}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerLeadsByStsAndAssignedIdAndUploadedId(
			@PathVariable("status") String status, @PathVariable("assignedId") String assignedId,
			@PathVariable("uploadedById") String uploadedById,@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(
				customerLeadService.getCustomerLeadsByStsAndAssignedIdAndUploadedId(status, assignedId, uploadedById,reqParam));
	}

	@RequestMapping(value = "/customerlead/status/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerLeadsByStsAndUploadedById(@PathVariable("status") String status,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(customerLeadService.getCustomerLeadsByStsAndUploadedById(status, reqParam));
	}

	@RequestMapping(value = "/businessexecutive/assign", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> businessExecutiveAssignByListOfUuid(
			@RequestBody CustomerLeadTemplateModel customerLeadModel) throws Exception {
		return prepareSuccessResponse(customerLeadService.businessExecutiveAssignByListOfUuid(customerLeadModel));
	}

	@RequestMapping(value = "/customerlead/status", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateStsAndCallDateByUuid(@RequestBody CustomerLeadModel customerLeadModel)
			throws Exception {
		return prepareSuccessResponse(customerLeadService.updateStsAndCallDateByUuid(customerLeadModel));
	}

}
