package com.atpl.mmg.AandA.controller.datamigration;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.service.datamigration.MigrationService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings({ "rawtypes", "unused" })
public class MigrationController {

	@Autowired
	MigrationService migrationService;

	@RequestMapping(value = "/import/profile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<GenericRes> saveProfilesByRoles(@RequestParam("file") MultipartFile file,@RequestParam("roleId") String roleId, HttpServletRequest request) throws Exception {
		return prepareSuccessResponse(migrationService.saveProfilesByRoles(file,roleId));
	}
	
	@RequestMapping(value = "/import/onboard/request", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<GenericRes> saveOnboardRequest(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
		return prepareSuccessResponse(migrationService.saveOnboardRequest(file));
	}
	
	@RequestMapping(value = "/import/profile/image", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<GenericRes> saveImagesByRole(@RequestParam("file") MultipartFile file,@RequestParam("roleId") String roleId, HttpServletRequest request) throws Exception {
		return prepareSuccessResponse(migrationService.saveImagesByRole(file,roleId));
	}
}
