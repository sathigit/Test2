package com.atpl.mmg.service.employee;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.ApiUtility;
import com.atpl.mmg.common.GenericHttpClient;
import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.common.MmgEnum;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.dao.employee.EmployeeDAO;
import com.atpl.mmg.domain.employee.EmployeeDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.AADHAR_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.exception.MmgRestException.ACCOUNT_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.EMAILID_PATTERN_NOT_MATCH;
import com.atpl.mmg.exception.MmgRestException.FIRSTNAME_PATTERN_NOT_MATCH;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.PAN_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.exception.MmgRestException.PAN_PATTERN_NOT_MATCH;
import com.atpl.mmg.exception.MmgRestException.PROFILE_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.mapper.employee.EmployeeMapper;
import com.atpl.mmg.model.ValidateModel.ValidateModel;
import com.atpl.mmg.model.employee.EmployeeModel;
import com.atpl.mmg.utils.DataValidation;
import com.atpl.mmg.utils.IDGeneration;

/*
 * Author:Sindhu modified Date: 27-2-2020 
 */

@SuppressWarnings({ "rawtypes", "unused" })
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeDAO employeeDAO;

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	IDGeneration idGeneration;

	@Autowired
	DataValidation dataValidation;

	private boolean validateProfile(EmployeeModel employeeModel) {
		/** FirstName Validation */
		if (!dataValidation.isValidate(employeeModel.getFirstName(), DataValidation.FIRSTNAME_PATTERN))
			throw new FIRSTNAME_PATTERN_NOT_MATCH();
		/** EmailID Validation */
		if (!dataValidation.isValidate(employeeModel.getEmailId(), DataValidation.EMAIL_PATTERN))
			throw new EMAILID_PATTERN_NOT_MATCH();
		if (null == employeeModel.getFirstName() || employeeModel.getFirstName().isEmpty())
			throw new NOT_FOUND("Please enter the first Name");
		if (!dataValidation.isValidate(employeeModel.getFirstName(), DataValidation.FIRSTNAME_PATTERN))
			throw new FIRSTNAME_PATTERN_NOT_MATCH();
		if (0 == employeeModel.getGender())
			throw new NOT_FOUND("Please enter the gender");
		if (null == employeeModel.getDob() || employeeModel.getDob().isEmpty())
			throw new NOT_FOUND("Please enter the dateofbirth");
		if (null == employeeModel.getDoorNumber() || employeeModel.getDoorNumber().isEmpty())
			throw new NOT_FOUND("Please enter the door number");
		if (null == employeeModel.getPincode())
			throw new NOT_FOUND("Please enter the pincode");
		if (null == employeeModel.getStreet() || employeeModel.getStreet().isEmpty())
			throw new NOT_FOUND("Please enter the street");
		if (null == employeeModel.getCountryId())
			throw new NOT_FOUND("Please mention the country");
		if (null == employeeModel.getStateId())
			throw new NOT_FOUND("Please mention the state");
		if (null == employeeModel.getCityId())
			throw new NOT_FOUND("Please mention the city");
		if (null == employeeModel.getMobileNumber())
			throw new NOT_FOUND("Please mention the mobile number");
		if (null == employeeModel.getEmailId() || employeeModel.getEmailId().isEmpty())
			throw new NOT_FOUND("Please mention the email");
		if (null == employeeModel.getPanNumber() || employeeModel.getPanNumber().isEmpty())
			throw new NOT_FOUND("Please enter the pan number");
		if (!dataValidation.isValidate(employeeModel.getPanNumber(), DataValidation.PANNUMBER_PATTERN))
			throw new PAN_PATTERN_NOT_MATCH();
		if (null == employeeModel.getAccountNumber())
			throw new NOT_FOUND("Please enter the accountNumber");
		else if (employeeModel.getAccountNumber().toString().length() < 9
				|| employeeModel.getAccountNumber().toString().length() > 21)
			throw new NOT_FOUND("AccountNumber length should be within 9-21 digits");
		if (null == employeeModel.getAadharNumber())
			throw new NOT_FOUND("Please enter the aadhar Number");
		else if (null != employeeModel.getAadharNumber()) {
			if (employeeModel.getAadharNumber().toString().length() != 12)
				throw new NOT_FOUND("AadharNumber length should be 12 digits!!");
		}
		if (null == employeeModel.getBankId())
			throw new NOT_FOUND("Please enter the bank");
		if (null == employeeModel.getIfscCode() || employeeModel.getIfscCode().isEmpty())
			throw new NOT_FOUND("Please enter the ifsc code");
		return true;
	}

	private EmployeeDomain getProfile(int profileId) throws Exception {
		EmployeeDomain employeeDomain = new EmployeeDomain();
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v2" + "/getprofile/" + profileId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		JSONObject data = (JSONObject) jsonResponse.get("data");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new PROFILE_NOT_FOUND();
			} else {
				int profileId1 = data.getInt("id");
				employeeDomain.setProfileId(profileId1);
				String emailId = data.getString("emailId");
				employeeDomain.setEmailId(emailId);
				int roleId = data.getInt("roleId");
				employeeDomain.setRoleId(roleId);
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return employeeDomain;
	}

	@Override
	public EmployeeModel saveEmployee(EmployeeModel employeeModel) throws Exception {
		JSONObject data = null;
		int profileId;
		EmployeeDomain employeeDomain = new EmployeeDomain();
		validateProfile(employeeModel);
		EmployeeModel profileDomain = new EmployeeModel(employeeModel.getFirstName(), employeeModel.getLastName(),
				employeeModel.getDob(), employeeModel.getGender(), employeeModel.getDoorNumber(),
				employeeModel.getStreet(), employeeModel.getCityId(), employeeModel.getStateId(),
				employeeModel.getCountryId(), employeeModel.getPincode(), employeeModel.getMobileNumber(),
				employeeModel.getAlternativeNumber(), employeeModel.getAadharNumber(), employeeModel.getAccountNumber(),
				employeeModel.getPanNumber(), employeeModel.getProfileId(), employeeModel.getRoleId(),
				employeeModel.getEmailId(), employeeModel.getPassword(), employeeModel.getConfirmPassword());

		String reqBody = JsonUtil.toJsonString(profileDomain);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpPost(
				mmgProperties.getAuthUrl() + "v1" + "/companyProfile2",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()), reqBody,
				MmgEnum.AUTH.name());

		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value()) {
				throw new SAVE_FAILED(jsonResponse.getString("message"));
			} else
				data = (JSONObject) jsonResponse.get("data");
		profileId = data.getInt("id");
		try {
			BeanUtils.copyProperties(employeeModel, employeeDomain);
			String employeeId = idGeneration.RandomNumberEmployee();
			employeeDomain.setEmployeeId(employeeId);
			employeeDomain.setProfileId(profileId);
			employeeDAO.saveEmployee(employeeDomain);
		} catch (Exception e) {
			Map<String, Object> deletehttpResponse = GenericHttpClient.doHttpDelete(
					mmgProperties.getAuthUrl() + "v1" + "/deleteAuthProfile/" + profileId,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.AUTH.name());
			int deletestatusCode = (int) deletehttpResponse.get("statusCode");
			JSONObject deletejsonResponse = (JSONObject) deletehttpResponse.get("response");
			if (deletejsonResponse != null)
				if (deletestatusCode != HttpStatus.OK.value()) {
					throw new DELETE_FAILED("Delete Failed");
				} else
					throw new BACKEND_SERVER_ERROR();
		}
		employeeModel = new EmployeeModel(employeeDomain.getEmployeeId());
		return employeeModel;
	}

	@Override
	public String activateEmployee(EmployeeModel employeeModel) throws Exception {
		EmployeeDomain employeeDomain = new EmployeeDomain();
		BeanUtils.copyProperties(employeeModel, employeeDomain);
		return employeeDAO.activateEmployee(employeeDomain);
	}

	@Override
	public String inactivateEmployee(EmployeeModel employeeModel) throws Exception {
		EmployeeDomain employeeDomain = new EmployeeDomain();
		BeanUtils.copyProperties(employeeModel, employeeDomain);
		return employeeDAO.inactivateEmployee(employeeDomain);
	}

	@Override
	public EmployeeModel getEmployeeDetails(int profileId) throws Exception {

		EmployeeDomain employeeDomain = employeeDAO.getEmployeeDetails(profileId);
		EmployeeModel employeeModel = new EmployeeModel();
		if (employeeDomain == null)
			return null;
		BeanUtils.copyProperties(employeeDomain, employeeModel);
		return employeeModel;

	}

	@Override
	public String updateFleetId(EmployeeModel employeeModel) throws Exception {
		EmployeeDomain employeeDomain = new EmployeeDomain();
		BeanUtils.copyProperties(employeeModel, employeeDomain);
		return employeeDAO.updateFleetId(employeeDomain);
	}

	@Override
	public String updateFranchiseId(EmployeeModel employeeModel) throws Exception {
		EmployeeDomain employeeDomain = new EmployeeDomain();
		BeanUtils.copyProperties(employeeModel, employeeDomain);
		return employeeDAO.updateFranchiseId(employeeDomain);
	}

	@Override
	public String updateWarehouseId(EmployeeModel employeeModel) throws Exception {
		EmployeeDomain employeeDomain = new EmployeeDomain();
		BeanUtils.copyProperties(employeeModel, employeeDomain);
		return employeeDAO.updateWarehouseId(employeeDomain);
	}

	@Override
	public String updateOTStateId(EmployeeModel employeeModel) throws Exception {
		EmployeeDomain employeeDomain = new EmployeeDomain();
		BeanUtils.copyProperties(employeeModel, employeeDomain);
		return employeeDAO.updateOTStateId(employeeDomain);
	}

	@Override
	public EmployeeModel getFleetId(int profileId) throws Exception {
		EmployeeDomain employeeDomain = employeeDAO.getFleetId(profileId);
		EmployeeModel employeeModel = new EmployeeModel();
		if (employeeDomain == null)
			throw new NOT_FOUND("Employee not found");
		BeanUtils.copyProperties(employeeDomain, employeeModel);
		return employeeModel;
	}

	@Override
	public EmployeeModel getFranchiseId(int profileId) throws Exception {
		EmployeeDomain employeeDomain = employeeDAO.getFranchiseId(profileId);
		EmployeeModel employeeModel = new EmployeeModel();
		if (employeeDomain == null)
			throw new NOT_FOUND("Employee not found");
		BeanUtils.copyProperties(employeeDomain, employeeModel);
		return employeeModel;
	}

	@Override
	public EmployeeModel getWarehouseId(int profileId) throws Exception {
		EmployeeDomain employeeDomain = employeeDAO.getWarehouseId(profileId);
		EmployeeModel employeeModel = new EmployeeModel();
		if (employeeDomain == null)
			throw new NOT_FOUND("Employee not found");
		BeanUtils.copyProperties(employeeDomain, employeeModel);
		return employeeModel;
	}

	@Override
	public EmployeeModel getOTStateId(int profileId) throws Exception {

		EmployeeDomain employeeDomain = employeeDAO.getOTStateId(profileId);
		EmployeeModel employeeModel = new EmployeeModel();
		if (employeeDomain == null)
			throw new NOT_FOUND("Employee not found");
		BeanUtils.copyProperties(employeeDomain, employeeModel);
		return employeeModel;
	}

	@Override
	public EmployeeModel getEmployeeDetail(int employeeId) throws Exception {

		EmployeeDomain employeeDomain = employeeDAO.getEmployeeDetail(employeeId);
		EmployeeModel employeeModel = new EmployeeModel();
		if (employeeDomain == null)
			throw new NOT_FOUND("Employee details not found");
		BeanUtils.copyProperties(employeeDomain, employeeModel);
		return employeeModel;

	}

	@Override
	public List<EmployeeModel> getEmployeeList(int city, boolean status) throws Exception {
		List<EmployeeDomain> employeeDomain = employeeDAO.getEmployeeList(city, status);
		EmployeeDomain employee = new EmployeeDomain();
		List<EmployeeDomain> list = new ArrayList<EmployeeDomain>();
		for (EmployeeDomain employeelist : employeeDomain) {
			employee = getProfile(employeelist.getProfileId());
			employeelist.setRoleId(employee.getRoleId());
			employeelist.setEmailId(employee.getEmailId());
			employeelist.setMobileNumber(employeelist.getMobileNumber());
			list.add(employeelist);
		}
		return employeeMapper.entityList(list);
	}

	@Override
	public List<EmployeeModel> getEmployeeListByState(int state, boolean status) throws Exception {
		List<EmployeeDomain> employeeDomain = employeeDAO.getEmployeeListByState(state, status);
		EmployeeDomain employee = new EmployeeDomain();
		List<EmployeeDomain> list = new ArrayList<EmployeeDomain>();
		for (EmployeeDomain employeelist : employeeDomain) {
			employee = getProfile(employeelist.getProfileId());
			employeelist.setRoleId(employee.getRoleId());
			employeelist.setEmailId(employee.getEmailId());
			employeelist.setMobileNumber(employeelist.getMobileNumber());
			list.add(employeelist);
		}
		return employeeMapper.entityList(list);
	}

	@Override
	public String updateBank(EmployeeModel employeeModel) throws Exception {
		GenericRes response = new GenericRes();
		if (employeeModel.getIfscCode().isEmpty() || employeeModel.getIfscCode() == null)
			throw new NOT_FOUND("Please mention the ifscCode!!");
		if (employeeModel.getBankId() == null)
			throw new NOT_FOUND("Please select the Bank!!");
		if (employeeModel.getAccountNumber() == null)
			throw new NOT_FOUND("Please mention the Account Number!!");
		if (employeeModel.getAadharNumber() == null)
			throw new NOT_FOUND("Please mention the Aadhar Number!!");
		if (employeeModel.getPanNumber().isEmpty() || employeeModel.getPanNumber() == null)
			throw new NOT_FOUND("Please mention the Pan Number!!");
		EmployeeDomain employeeDomain = new EmployeeDomain();
		BeanUtils.copyProperties(employeeModel, employeeDomain);
		return employeeDAO.updateBank(employeeDomain);
	}

	@Override
	public String updateEmployee(EmployeeModel employeeModel) throws Exception {
		EmployeeDomain employeeDomain = new EmployeeDomain();

		BeanUtils.copyProperties(employeeModel, employeeDomain);

		if (employeeDomain.getFirstName().isEmpty() || employeeDomain.getFirstName() == null)
			throw new NOT_FOUND("Please mention the FirstName!!");
		if (employeeDomain.getLastName().isEmpty() || employeeDomain.getLastName() == null)
			throw new NOT_FOUND("Please mention the LastName!!");
		if (employeeDomain.getStateId() == null)
			throw new NOT_FOUND("Please select the State!!");
		if (employeeDomain.getCityId() == null)
			throw new NOT_FOUND("Please select the City!!");
		if (employeeDomain.getPincode() == null)
			throw new NOT_FOUND("Please mention the pincode!!");
		if (employeeDomain.getEmailId().isEmpty() || employeeDomain.getEmailId() == null)
			throw new NOT_FOUND("Please mention the EmailId!!");

		if (employeeDomain.getDoorNumber() == null || employeeDomain.getDoorNumber().isEmpty())
			employeeDomain.setDoorNumber("NA");
		if (employeeDomain.getStreet() == null || employeeDomain.getStreet().isEmpty())
			employeeDomain.setStreet("NA");

		int cityId = employeeDomain.getCityId().intValueExact();
		employeeDomain.setCity(cityId);
		int stateId = employeeDomain.getStateId().intValueExact();
		employeeDomain.setState(stateId);
		int countryId = employeeDomain.getCountryId().intValueExact();
		employeeDomain.setCountry(countryId);
		employeeDomain.setId(employeeDomain.getProfileId());
		employeeDomain.setRoleId(employeeDomain.getRoleId());
		System.out.println(employeeDomain.getRoleId());

		EmployeeDomain profileDomain = new EmployeeDomain(employeeDomain.getFirstName(), employeeDomain.getCityId(),
				employeeDomain.getStateId(), employeeDomain.getCountryId(), employeeDomain.getAlternativeNumber(),
				employeeDomain.getEmailId(), employeeDomain.getMobileNumber(), employeeDomain.getId(),
				employeeDomain.getRoleId(), employeeDomain.getPassword(), employeeDomain.getConfirmPassword());
		String reqBody = JsonUtil.toJsonString(profileDomain);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpPut(
				mmgProperties.getAuthUrl() + "v1" + "/updateProfile",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()), reqBody,
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new UPDATE_FAILED("Update Failed");
			} else {
				return employeeDAO.updateEmployee(employeeDomain);
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteEmployee(int profileId) throws Exception {
		return employeeDAO.deleteEmployee(profileId);
	}

	@Override
	public List<EmployeeModel> getActiveInactiveEmployeeList(boolean status) throws Exception {
		// TODO Auto-generated method stub
		List<EmployeeDomain> employeeDomain = employeeDAO.getActiveInactiveEmployeeList(status);
		EmployeeDomain employee = new EmployeeDomain();
		List<EmployeeDomain> list = new ArrayList<EmployeeDomain>();
		for (EmployeeDomain employeelist : employeeDomain) {
			employee = getProfile(employeelist.getProfileId());
			employeelist.setRoleId(employee.getRoleId());
			employeelist.setEmailId(employee.getEmailId());
			employeelist.setMobileNumber(employeelist.getMobileNumber());
			list.add(employeelist);
		}
		return employeeMapper.entityList(list);
	}

	@Override
	public ValidateModel validateAadharNumber(BigInteger aadharNumber, boolean updateStatus) throws Exception {
		ValidateModel validate = null;
		EmployeeDomain domain = employeeDAO.validateAadharNumber(aadharNumber);
		if (domain != null && updateStatus) {
			validate = new ValidateModel(domain.getEmployeeId(), domain.getProfileId());
			BeanUtils.copyProperties(domain, validate);
			return validate;
		} else if (domain != null)
			throw new AADHAR_NUMBER_ALREADY_EXIST();
		return validate;
	}

	@Override
	public ValidateModel validatePanNumber(String panNumber, boolean updateStatus) throws Exception {
		ValidateModel validate = null;
		EmployeeDomain domain = employeeDAO.validatePanNumber(panNumber);
		if (domain != null && updateStatus) {
			validate = new ValidateModel(domain.getEmployeeId(), domain.getProfileId());
			BeanUtils.copyProperties(domain, validate);
			return validate;
		} else if (domain != null)
			throw new PAN_NUMBER_ALREADY_EXIST();
		return validate;
	}

	@Override
	public ValidateModel validateAccountNumber(BigInteger accountNumber, boolean updateStatus) throws Exception {
		ValidateModel validate = null;
		EmployeeDomain domain = employeeDAO.validateAccountNumber(accountNumber);
		if (domain != null && updateStatus) {
			validate = new ValidateModel(domain.getEmployeeId(), domain.getProfileId());
			BeanUtils.copyProperties(domain, validate);
			return validate;
		} else if (domain != null)
			throw new ACCOUNT_NUMBER_ALREADY_EXIST();
		return validate;
	}

}