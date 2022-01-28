package com.atpl.mmg.controller.employee;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.math.BigInteger;

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
import com.atpl.mmg.model.employee.EmployeeModel;
import com.atpl.mmg.service.employee.EmployeeService;

/*Author:Sindhu
 * modificationDate:26-2-2020
 * */
@SuppressWarnings({"rawtypes","unused"})
@RestController
@RequestMapping("/v1")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveEmployee(@RequestBody EmployeeModel employeeModel) throws Exception {
		return prepareSuccessResponse(employeeService.saveEmployee(employeeModel));
	}
	
	@RequestMapping(value = "/employeeList/active", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getActiveEmployeeList() throws Exception {
		return prepareSuccessResponse(employeeService.getActiveInactiveEmployeeList(true));
	}

	@RequestMapping(value = "/employeeList/city/{cityId}/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getActiveEmployeeLists(@PathVariable("cityId") int cityId,@PathVariable("status") boolean status) throws Exception {
		return prepareSuccessResponse(employeeService.getEmployeeList(cityId,status));
	}

	@RequestMapping(value = "/employee/activate", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> activateEmployee(@RequestBody EmployeeModel employeeModel) throws Exception {
		return prepareSuccessResponse(employeeService.activateEmployee(employeeModel));
	}
	
	@RequestMapping(value = "/employeeList/state/{stateId}/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getEmployeeListByState(@PathVariable("stateId") int stateId,@PathVariable("status") boolean status) throws Exception {
		return prepareSuccessResponse(employeeService.getEmployeeListByState(stateId,status));
	}
	
	@RequestMapping(value = "/employeeList/inactive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getInactiveEmployeeList() throws Exception {
		return prepareSuccessResponse(employeeService.getActiveInactiveEmployeeList(false));
	}
	
	@RequestMapping(value = "/employee/inactivate", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> inactivateEmployee(@RequestBody EmployeeModel employeeModel) throws Exception {
		return prepareSuccessResponse(employeeService.inactivateEmployee(employeeModel));
	}

	@RequestMapping(value = "/employeeDetails/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getEmployeeDetails(@PathVariable("profileId") int profileId) throws Exception {
		return prepareSuccessResponse(employeeService.getEmployeeDetails(profileId));
	}
	
	@RequestMapping(value = "/employee/Detail/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getEmployeeDetail(@PathVariable("employeeId") int employeeId) throws Exception {
		return prepareSuccessResponse(employeeService.getEmployeeDetail(employeeId));
	}
	
	@RequestMapping(value = "/update/fleetId", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateFleetId(@RequestBody EmployeeModel employeeModel) throws Exception {
		return prepareSuccessResponse(employeeService.updateFleetId(employeeModel));
	}
	
	@RequestMapping(value = "/update/franchiseId", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateFranchiseId(@RequestBody EmployeeModel employeeModel) throws Exception {
		return prepareSuccessResponse(employeeService.updateFranchiseId(employeeModel));
	}

	@RequestMapping(value = "/update/warehouseId", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateWarehouseId(@RequestBody EmployeeModel employeeModel) throws Exception {
		return prepareSuccessResponse(employeeService.updateWarehouseId(employeeModel));
	}
	
	@RequestMapping(value = "/update/otStateId", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateOTStateId(@RequestBody EmployeeModel employeeModel) throws Exception {
		return prepareSuccessResponse(employeeService.updateOTStateId(employeeModel));
	}
	
	@RequestMapping(value = "/getFleetId/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFleetId(@PathVariable("profileId") int profileId) throws Exception {
		return prepareSuccessResponse(employeeService.getFleetId(profileId));
	}

	@RequestMapping(value = "/getFranchiseId/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFranchiseId(@PathVariable("profileId") int profileId) throws Exception {
		return prepareSuccessResponse(employeeService.getFranchiseId(profileId));
	}
	
	@RequestMapping(value = "/getWarehouseId/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getWarehouseId(@PathVariable("profileId") int profileId) throws Exception {
		return prepareSuccessResponse(employeeService.getWarehouseId(profileId));
	}
	
	@RequestMapping(value = "/getOTStateId/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getOTStateId(@PathVariable("profileId") int profileId) throws Exception {
		return prepareSuccessResponse(employeeService.getOTStateId(profileId));
	}

	@RequestMapping(value = "/updateEmployee", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateEmployee(@RequestBody EmployeeModel employeeModel) throws Exception {
		return prepareSuccessResponse(employeeService.updateEmployee(employeeModel));
	}
	
	@RequestMapping(value = "/updateBankDetails", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateBank(@RequestBody EmployeeModel employeeModel) throws Exception {           
		return prepareSuccessResponse(employeeService.updateBank(employeeModel));
	}
	
	@RequestMapping(value = "/deleteEmployee/{profileId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteEmployee(@PathVariable("profileId") Integer profileId) throws Exception {
		return prepareSuccessResponse(employeeService.deleteEmployee(profileId));
	}	
	
	@RequestMapping(value = "/validateAadharNumber/{aadharNumber}/{updateStatus}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> validateAadharNumber(@PathVariable("aadharNumber") BigInteger aadharNumber,@PathVariable("updateStatus") boolean updateStatus) throws Exception {
		return prepareSuccessResponse(employeeService.validateAadharNumber(aadharNumber,updateStatus));
	}
	
	@RequestMapping(value = "/validateAccountNumber/{accountNumber}/{updateStatus}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> validateAccountNumber(@PathVariable("accountNumber") BigInteger accountNumber,@PathVariable("updateStatus") boolean updateStatus) throws Exception {
		return prepareSuccessResponse(employeeService.validateAccountNumber(accountNumber,updateStatus));
	}

	@RequestMapping(value = "/validatePanNumber/{panNumber}/{updateStatus}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> validatePanNumber(@PathVariable("panNumber") String panNumber,@PathVariable("updateStatus") boolean updateStatus) throws Exception {
		return prepareSuccessResponse(employeeService.validatePanNumber(panNumber,updateStatus));
	}

}
