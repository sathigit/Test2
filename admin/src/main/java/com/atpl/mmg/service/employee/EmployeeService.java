package com.atpl.mmg.service.employee;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.model.ValidateModel.ValidateModel;
import com.atpl.mmg.model.employee.EmployeeModel;

public interface EmployeeService {

	public EmployeeModel saveEmployee(EmployeeModel employeeModel) throws Exception;
	
	public List<EmployeeModel> getActiveInactiveEmployeeList(boolean status) throws Exception;

	public List<EmployeeModel> getEmployeeList(int city, boolean status) throws Exception;

	public String activateEmployee(EmployeeModel employeeModel) throws Exception;

	public List<EmployeeModel> getEmployeeListByState(int state, boolean status) throws Exception;

	public String inactivateEmployee(EmployeeModel employeeModel) throws Exception;

	public EmployeeModel getEmployeeDetails(int profileId) throws Exception;

	public EmployeeModel getEmployeeDetail(int employeeId) throws Exception;

	public String updateFleetId(EmployeeModel employeeModel) throws Exception;

	public String updateFranchiseId(EmployeeModel employeeModel) throws Exception;

	public String updateWarehouseId(EmployeeModel employeeModel) throws Exception;

	public String updateOTStateId(EmployeeModel employeeModel) throws Exception;

	public EmployeeModel getFleetId(int profileId) throws Exception;

	public EmployeeModel getFranchiseId(int profileId) throws Exception;

	public EmployeeModel getWarehouseId(int profileId) throws Exception;

	public EmployeeModel getOTStateId(int profileId) throws Exception;

	public String updateEmployee(EmployeeModel employeeModel) throws Exception;

	public String updateBank(EmployeeModel employeeModel) throws Exception;

	public String deleteEmployee(int profileId) throws Exception;
	
	public ValidateModel validateAadharNumber(BigInteger aadharNumber, boolean updateStatus) throws Exception;

	public ValidateModel validatePanNumber(String panNumber, boolean updateStatus) throws Exception;

	public ValidateModel validateAccountNumber(BigInteger accountNumber, boolean updateStatus) throws Exception;

}
