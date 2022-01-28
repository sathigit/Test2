package com.atpl.mmg.controller.admin;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.admin.AdminDetails;
import com.atpl.mmg.service.admin.AdminDetailsService;

@RestController
@RequestMapping("/v1")
public class AdminDetailsController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminDetailsController.class);

	@Autowired
	AdminDetailsService adminDetailsService;
	
	@RequestMapping(value = "/admin/details", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> adminDetails(@RequestBody AdminDetails adminDetails) throws Exception {
		return prepareSuccessResponse(adminDetailsService.fetchAdminDetails(adminDetails));
	}


}
