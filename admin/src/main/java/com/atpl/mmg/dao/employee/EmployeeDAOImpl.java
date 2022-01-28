package com.atpl.mmg.dao.employee;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.domain.employee.EmployeeDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.model.employee.EmployeeModel;
import com.atpl.mmg.utils.DataValidation;
import com.atpl.mmg.utils.DateUtility;

@SuppressWarnings("rawtypes")
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataValidation dataValidation;

	@Override
	public String saveEmployee(EmployeeDomain employeeDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO employee (employeeId,firstName,lastName,dob,gender,doorNumber,street,cityId,stateId,countryId,pincode,"
					+ "mobileNumber,alternativeNumber,aadharNumber,accountNumber,bankId,ifscCode,status,profileId,panNumber,emailId,creationDate,modificationDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { employeeDomain.getEmployeeId(), employeeDomain.getFirstName(),
							employeeDomain.getLastName(), employeeDomain.getDob(), employeeDomain.getGender(),
							employeeDomain.getDoorNumber(), employeeDomain.getStreet(), employeeDomain.getCityId(),
							employeeDomain.getStateId(), employeeDomain.getCountryId(), employeeDomain.getPincode(),
							employeeDomain.getMobileNumber(), employeeDomain.getAlternativeNumber(),
							employeeDomain.getAadharNumber(), employeeDomain.getAccountNumber(),
							employeeDomain.getBankId(), employeeDomain.getIfscCode(), employeeDomain.isStatus(),
							employeeDomain.getProfileId(), employeeDomain.getPanNumber(), employeeDomain.getEmailId(),
							simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()) });
			if (res == 1) {
				return "Employee saved successfully";
			} else
				throw new SAVE_FAILED("Save employee failed");

		} catch (Exception e) {
			logger.error("Exception saveEmployee in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<EmployeeDomain> getActiveInactiveEmployeeList(boolean status) throws Exception {
		try {
			String sql = "SELECT employeeId, firstName, lastName, mobileNumber, emailId, doorNumber, street,profileId,gender,dob,cityId,stateId,countryId,pincode,alternativeNumber,aadharNumber,accountNumber,bankId,ifscCode,creationDate FROM employee where status=?";
			List<EmployeeDomain> employeeDomain = jdbcTemplate.query(sql, new Object[] {status},
					new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
			return employeeDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getActiveInactiveEmployeeList in EmployeeDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Employee not found");
		} catch (Exception e) {
			logger.error("Exception getActiveInactiveEmployeeList in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	@Override
	public String activateEmployee(EmployeeDomain employeeDomain) throws Exception {
		try {
			String sql = "UPDATE employee SET status=1 WHERE employeeId=? ";
			int res = jdbcTemplate.update(sql, new Object[] { employeeDomain.getEmployeeId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Employee update failed");
		} catch (Exception e) {
			logger.error("Exception activateEmployee in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String inactivateEmployee(EmployeeDomain employeeDomain) throws Exception {
		try {
			String sql = "UPDATE employee SET status=0 WHERE employeeId=? ";
			int res = jdbcTemplate.update(sql, new Object[] { employeeDomain.getEmployeeId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Update Employee failed");
		} catch (Exception e) {
			logger.error("Exception inactivateEmployee in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Bean
	public DataValidation dataValidation() {
		return new DataValidation();
	}

	@Override
	public EmployeeDomain getEmployeeDetails(int profileId) throws Exception {
		try {
			String sql = "SELECT employeeId, firstName, lastName, mobileNumber, emailId, doorNumber, street,profileId,gender,dob,cityId,stateId,countryId,pincode,alternativeNumber,aadharNumber,accountNumber,bankId,ifscCode,panNumber from employee where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getEmployeeDetails in EmployeeDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Employee not found");
		} catch (Exception e) {
			logger.error("Exception getEmployeeDetails in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateFleetId(EmployeeDomain employeeDomain) throws Exception {
		try {
			String sql = "UPDATE employee SET fleetId=? WHERE employeeId=? ";
			int res = jdbcTemplate.update(sql,
					new Object[] { employeeDomain.getFleetId(), employeeDomain.getEmployeeId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Employee update failed");
		} catch (Exception e) {
			logger.error("Exception updateFleetId in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String updateFranchiseId(EmployeeDomain employeeDomain) throws Exception {
		try {
			String sql = "UPDATE employee SET franchiseId=? WHERE employeeId=? ";
			int res = jdbcTemplate.update(sql,
					new Object[] { employeeDomain.getFranchiseId(), employeeDomain.getEmployeeId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Employee update failed");
		} catch (Exception e) {
			logger.error("Exception updateFranchiseId in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String updateWarehouseId(EmployeeDomain employeeDomain) throws Exception {
		try {
			String sql = "UPDATE employee SET warehouseId=? WHERE employeeId=? ";
			int res = jdbcTemplate.update(sql,
					new Object[] { employeeDomain.getWarehouseId(), employeeDomain.getEmployeeId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Employee update failed");
		} catch (Exception e) {
			logger.error("Exception updateWarehouseId in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String updateOTStateId(EmployeeDomain employeeDomain) throws Exception {
		try {
			String sql = "UPDATE employee SET otStateId=? WHERE employeeId=? ";
			int res = jdbcTemplate.update(sql,
					new Object[] { employeeDomain.getOtStateId(), employeeDomain.getEmployeeId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Employee update failed");
		} catch (Exception e) {
			logger.error("Exception updateOTStateId in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EmployeeDomain getFleetId(int profileId) throws Exception {
		try {
			String sql = "SELECT employeeId, fleetId from employee where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getFleetId in EmployeeDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Employee not found");
		} catch (Exception e) {
			logger.error("Exception getFleetId in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EmployeeDomain getFranchiseId(int profileId) throws Exception {
		try {
			String sql = "SELECT employeeId, franchiseId from employee where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getFranchiseId in EmployeeDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Employee not found");
		} catch (Exception e) {
			logger.error("Exception getFranchiseId in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EmployeeDomain getWarehouseId(int profileId) throws Exception {
		try {
			String sql = "SELECT employeeId, warehouseId from employee where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getWarehouseId in EmployeeDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Employee not found");
		} catch (Exception e) {
			logger.error("Exception getWarehouseId in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EmployeeDomain getOTStateId(int profileId) throws Exception {
		try {
			String sql = "SELECT employeeId, otStateId from employee where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getOTStateId in EmployeeDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Employee not found");
		} catch (Exception e) {
			logger.error("Exception getOTStateId in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EmployeeDomain getEmployeeDetail(int employeeId) throws Exception {
		try {
			String sql = "SELECT employeeId, firstName, lastName, mobileNumber, emailId, doorNumber, street,profileId,gender,dob,cityId,stateId,countryId,pincode,alternativeNumber,aadharNumber,accountNumber,bankId,ifscCode,panNumber,creationDate from employee where employeeId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { employeeId },
					new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getEmployeeDetails in EmployeeDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Employee not found");
		} catch (Exception e) {
			logger.error("Exception getEmployeeDetails in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<EmployeeDomain> getEmployeeList(int city, boolean status) throws Exception {
		try {
			List<EmployeeDomain> employeeDomain = new ArrayList<EmployeeDomain>();
			if (status == true) {
				String sql = "SELECT employeeId, firstName, lastName, mobileNumber, emailId, doorNumber, street,profileId,gender,dob,cityId,stateId,countryId,pincode,alternativeNumber,aadharNumber,accountNumber,bankId,ifscCode,creationDate FROM employee where status=? and cityId=?";
				employeeDomain = jdbcTemplate.query(sql, new Object[] { status, city },
						new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
			} else {
				String sql = "SELECT employeeId, firstName, lastName, mobileNumber, emailId, doorNumber, street,profileId,gender,dob,cityId,stateId,countryId,pincode,alternativeNumber,aadharNumber,accountNumber,bankId,ifscCode,creationDate FROM employee where status=? and cityId=?";
				employeeDomain = jdbcTemplate.query(sql, new Object[] { status, city },
						new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
			}
			return employeeDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getActiveEmployeeList in EmployeeDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Employee not found");
		} catch (Exception e) {
			logger.error("Exception getActiveEmployeeList in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<EmployeeDomain> getEmployeeListByState(int city, boolean status) throws Exception {
		try {
			List<EmployeeDomain> employeeDomain = new ArrayList<EmployeeDomain>();
			if (status == true) {
				String sql = "SELECT employeeId, firstName, lastName, mobileNumber, emailId, doorNumber, street,profileId,gender,dob,cityId,stateId,countryId,pincode,alternativeNumber,aadharNumber,accountNumber,bankId,ifscCode,creationDate FROM employee where status=? and stateId=?";
				employeeDomain = jdbcTemplate.query(sql, new Object[] { status, city },
						new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
			} else {
				String sql = "SELECT employeeId, firstName, lastName, mobileNumber, emailId, doorNumber, street,profileId,gender,dob,cityId,stateId,countryId,pincode,alternativeNumber,aadharNumber,accountNumber,bankId,ifscCode,creationDate FROM employee where status=? and stateId=?";
				employeeDomain = jdbcTemplate.query(sql, new Object[] { status, city },
						new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
			}
			return employeeDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getEmployeeListByState in EmployeeDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Employee not found");
		} catch (Exception e) {
			logger.error("Exception getEmployeeListByState in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateEmployee(EmployeeDomain employeeDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE employee set firstName=?,lastName=?,doorNumber=?,street=?,cityId=?,stateId=?,countryId=?,pincode=?, emailId=?,mobileNumber=?, alternativeNumber =?,modificationDate=? WHERE profileId=? ";
			int res = jdbcTemplate.update(sql,
					new Object[] { employeeDomain.getFirstName(), employeeDomain.getLastName(),
							employeeDomain.getDoorNumber(), employeeDomain.getStreet(), employeeDomain.getCityId(),
							employeeDomain.getStateId(), employeeDomain.getCountryId(), employeeDomain.getPincode(),
							employeeDomain.getEmailId(), employeeDomain.getMobileNumber(),
							employeeDomain.getAlternativeNumber(), simpleDateFormat.format(new Date()),
							employeeDomain.getProfileId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Employee update failed");
		} catch (Exception e) {
			logger.error("Exception updateEmployee in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String updateBank(EmployeeDomain employeeDomain) throws Exception {	
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE employee SET bankId = ?,aadharNumber =?,accountNumber=?, ifscCode=?, panNumber=?,modificationDate=? WHERE profileId=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { employeeDomain.getBankId(), employeeDomain.getAadharNumber(),
							employeeDomain.getAccountNumber(), employeeDomain.getIfscCode(),
							employeeDomain.getPanNumber(), simpleDateFormat.format(new Date()),
							employeeDomain.getProfileId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Employee update failed");
		} catch (Exception e) {
			logger.error("Exception updateBank in EmployeeDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public EmployeeDomain validateAadharNumber(BigInteger aadharNumber) throws Exception {
		try {
			String sql = "select employeeId,profileId,aadharNumber from employee where aadharNumber=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { aadharNumber },
					new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception getEmployeeByaadharNumber in EmployeeDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EmployeeDomain validatePanNumber(String panNumber) throws Exception {
		try {
			String sql = "select employeeId,profileId,panNumber from employee where panNumber=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { panNumber },
					new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception getEmployeeBypanNumber in EmployeeDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EmployeeDomain validateAccountNumber(BigInteger accountNumber) throws Exception {
		try {
			String sql = "select employeeId,profileId,accountNumber from employee where accountNumber=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { accountNumber },
					new BeanPropertyRowMapper<EmployeeDomain>(EmployeeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception getEmployeeByaccountNumber in EmployeeDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteEmployee(int profileId) throws Exception {
		try {
			String sql = "delete from employee where profileId =?";
			int res = jdbcTemplate.update(sql, new Object[] { profileId });

			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Employee delete failed");

		} catch (Exception e) {
			logger.error("Exception deleteEmployee in EmployeeDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}

	}




}