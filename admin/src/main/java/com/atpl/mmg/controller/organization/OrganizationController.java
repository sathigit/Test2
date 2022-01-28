package com.atpl.mmg.controller.organization;

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
import com.atpl.mmg.model.organization.OrganizationModel;
import com.atpl.mmg.service.organization.OrganizationService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings({"rawtypes","unused"})
public class OrganizationController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	OrganizationService organizationService;

	@RequestMapping(value = "/organization", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveOrganization(@RequestBody OrganizationModel organizationModel)
			throws Exception {
		return prepareSuccessResponse(organizationService.saveOrganization(organizationModel));
	}

	@RequestMapping(value = "/organization", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getOrganization(@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(organizationService.getOrganization(reqParam));
	}

	@RequestMapping(value = "/organization", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateOrganization(@RequestBody OrganizationModel organizationModel)
			throws Exception {
		return prepareSuccessResponse(organizationService.updateOrganization(organizationModel));
	}

	@RequestMapping(value = "/organization/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getOrganization(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(organizationService.getOrganization(id));
	}

	@RequestMapping(value = "/organization/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteOrganization(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(organizationService.deleteOrganization(id));
	}
}
