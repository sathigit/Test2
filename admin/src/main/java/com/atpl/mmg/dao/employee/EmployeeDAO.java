package com.atpl.mmg.dao.employee;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.domain.employee.EmployeeDomain;
import com.atpl.mmg.model.employee.EmployeeModel;

/*Author:Sindhu 
 * modifiedDate:26-2-2020
 * */

@SuppressWarnings("unused")
public interface EmployeeDAO {

	public String saveEmployee(EmployeeDomain employeeDomain) throws Exception;
	
	public List<EmployeeDomain> getActiveInactiveEmployeeList(boolean status) throws Exception ;

	public List<EmployeeDomain> getEmployeeList(int city, boolean status) throws Exception;

	public String activateEmployee(EmployeeDomain employeeDomain) throws Exception;

	public List<EmployeeDomain> getEmployeeListByState(int state, boolean status) throws Exception;

	public String inactivateEmployee(EmployeeDomain employeeDomain) throws Exception;

	public EmployeeDomain getEmployeeDetails(int profileId) throws Exception;

	public EmployeeDomain getEmployeeDetail(int employeeId) throws Exception;

	public String updateFleetId(EmployeeDomain employeeDomain) throws Exception;

	public String updateFranchiseId(EmployeeDomain employeeDomain) throws Exception;

	public String updateWarehouseId(EmployeeDomain employeeDomain) throws Exception;

	public String updateOTStateId(EmployeeDomain employeeDomain) throws Exception;

	public EmployeeDomain getFleetId(int profileId) throws Exception;

	public EmployeeDomain getFranchiseId(int profileId) throws Exception;

	public EmployeeDomain getWarehouseId(int profileId) throws Exception;

	public EmployeeDomain getOTStateId(int profileId) throws Exception;

	public String updateEmployee(EmployeeDomain employeeDomain) throws Exception;

	public String updateBank(EmployeeDomain employeeDomain) throws Exception;

	public EmployeeDomain validateAadharNumber(BigInteger aadharNumber) throws Exception;

	public EmployeeDomain validatePanNumber(String panNumber) throws Exception;

	public EmployeeDomain validateAccountNumber(BigInteger accountNumber) throws Exception;

	public String deleteEmployee(int profileId) throws Exception;

}
