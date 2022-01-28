package com.atpl.mmg.controller.admin;

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

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.admin.AdminModel;
import com.atpl.mmg.service.admin.AdminService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class AdminController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	AdminService adminService;

	@RequestMapping(value = "/getService", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getService() throws Exception {
		return prepareSuccessResponse(adminService.getService());
	}

	@RequestMapping(value = "/getSeverity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getSeverity() throws Exception {
		return prepareSuccessResponse(adminService.getSeverity());
	}

	@RequestMapping(value = "/getCustomerIssue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerIssue() throws Exception {
		return prepareSuccessResponse(adminService.getCustomerIssue());
	}

	@RequestMapping(value = "/getFranchiseIssue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFranchiseIssue() throws Exception {
		return prepareSuccessResponse(adminService.getFranchiseIssue());
	}

	@RequestMapping(value = "/getFleetIssue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFleetIssue() throws Exception {
		return prepareSuccessResponse(adminService.getFleetIssue());
	}

	@RequestMapping(value = "/addDriverIssue", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addDriverIssue(@RequestBody AdminModel adminModel) throws Exception {
		return prepareSuccessResponse(adminService.addDriverIssue(adminModel));
	}

	@RequestMapping(value = "/addCustomerIssue", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addCustomerIssue(@RequestBody AdminModel adminModel) throws Exception {
		return prepareSuccessResponse(adminService.addCustomerIssue(adminModel));
	}

	@RequestMapping(value = "/addFranchiseIssue", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addFranchiseIssue(@RequestBody AdminModel adminModel) throws Exception {
		return prepareSuccessResponse(adminService.addFranchiseIssue(adminModel));
	}

	@RequestMapping(value = "/addFleetIssue", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addFleetIssue(@RequestBody AdminModel adminModel) throws Exception {
		return prepareSuccessResponse(adminService.addFleetIssue(adminModel));
	}

	@RequestMapping(value = "/update/customerIssue", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateCustomerIssue(@RequestBody AdminModel adminModel) throws Exception {
		return prepareSuccessResponse(adminService.updateCustomerIssue(adminModel));
	}

	@RequestMapping(value = "/update/driverIssue", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateDriverIssue(@RequestBody AdminModel adminModel) throws Exception {
		return prepareSuccessResponse(adminService.updateDriverIssue(adminModel));
	}

	@RequestMapping(value = "/update/franchiseIssue", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateFranchiseIssue(@RequestBody AdminModel adminModel) throws Exception {
		return prepareSuccessResponse(adminService.updateFranchiseIssue(adminModel));
	}

	@RequestMapping(value = "/update/fleetIssue", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateFleetIssue(@RequestBody AdminModel adminModel) throws Exception {
		return prepareSuccessResponse(adminService.updateFleetIssue(adminModel));
	}

	@RequestMapping(value = "/customerIssue/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteCustomerIssue(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(adminService.deleteCustomerIssue(id));
	}

	@RequestMapping(value = "/driverIssue/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteDriverIssue(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(adminService.deleteDriverIssue(id));
	}

	@RequestMapping(value = "/franchiseIssue/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteFranchiseIssue(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(adminService.deleteFranchiseIssue(id));
	}

	@RequestMapping(value = "/fleetIssue/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteFleetIssue(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(adminService.deleteFleetIssue(id));
	}

	@RequestMapping(value = "/addSeverity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addSeverity(@RequestBody AdminModel adminModel) throws Exception {
		return prepareSuccessResponse(adminService.addSeverity(adminModel));
	}

	@RequestMapping(value = "/update/severity", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateSeverity(@RequestBody AdminModel adminModel) throws Exception {
		return prepareSuccessResponse(adminService.updateSeverity(adminModel));
	}

	@RequestMapping(value = "/severity/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteSeverity(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(adminService.deleteSeverity(id));
	}

	@RequestMapping(value = "/getCustomerIssue/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerIssue(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(adminService.getCustomerIssue(id));
	}

	@RequestMapping(value = "/getDriverIssue/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDriverIssue(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(adminService.getDriverIssue(id));
	}

	@RequestMapping(value = "/getFranchiseIssue/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFranchiseIssue(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(adminService.getFranchiseIssue(id));
	}

	@RequestMapping(value = "/getFleetIssue/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFleetIssue(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(adminService.getFleetIssue(id));
	}

	@RequestMapping(value = "/getSeverity/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getSeverity(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(adminService.getSeverity(id));
	}

	@RequestMapping(value = "/getEmployeeList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getEmployeeList() throws Exception {
		return prepareSuccessResponse(adminService.getEmployeeList());
	}
	


}
