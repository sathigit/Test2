package com.atpl.mmg.AandA.service.profile;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.atpl.mmg.AandA.common.ApiUtility;
import com.atpl.mmg.AandA.common.GenericHttpClient;
import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.common.MmgEnum;
import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.forgotPassword.ForgotPasswordDAO;
import com.atpl.mmg.AandA.dao.otp.OtpDAO;
import com.atpl.mmg.AandA.dao.profile.ProfileDAO;
import com.atpl.mmg.AandA.domain.forgotPassword.ForgotPasswordDomain;
import com.atpl.mmg.AandA.domain.otp.Otp;
import com.atpl.mmg.AandA.domain.profile.ProfileDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.AADHAR_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.ACCOUNT_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.CUSTOMER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.EMAILID_PATTERN_NOT_MATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.FIRSTNAME_PATTERN_NOT_MATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.INVALID_OTP;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.OLD_PASSWORD_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.OLD_PASSWORD__MISMATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.PAN_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.PASSWORD_MISMATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.PASSWORD_PATTERN_NOT_MATCHED;
import com.atpl.mmg.AandA.exception.MmgRestException.PROFILE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.PROMOCODE_ALREADY_GENERATED;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.USER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.USER_NOT_EXISTS;
import com.atpl.mmg.AandA.mapper.forgotPassword.ForgotPasswordMapper;
import com.atpl.mmg.AandA.mapper.profile.ProfileMapper;
import com.atpl.mmg.AandA.model.profile.ProfileModel;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.DataValidation;
import com.atpl.mmg.AandA.utils.EmailValidator;
//import com.atpl.mmg.AandA.utils.NotificationMessagingTemplate;
import com.atpl.mmg.AandA.utils.IDGeneration;

@SuppressWarnings("unused")
@Service("ProfileService")
public class ProfileServiceImpl implements ProfileService, Constants {

	private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

	@Autowired
	ProfileDAO profileDAO;

	@Autowired
	ForgotPasswordDAO forgotPasswordDAO;

	@Autowired
	ForgotPasswordMapper forgotPasswordMapper;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	ProfileMapper profileMapper;

	@Autowired
	EmailValidator emailValidator;

	@Autowired
	IDGeneration idGeneration;

	@Autowired
	DataValidation dataValidation;

	@Autowired
	OtpDAO otpDAO;

	static String otp, otp1;
	boolean otpIsActive = false;
	BigInteger ValidatePhoneNumber;

	public ProfileServiceImpl() {
		// Constuctor
	}

	/**
	 * Author:Vidya S K Modified Date: 2/14/2020,2/15/2020 Description: 1. To Check
	 * the password Pattern 2. Check the Role and Save The Customer And Profile Data
	 * 
	 */
	public ProfileModel saveProfile(ProfileModel profileModel) throws Exception {
		/**
		 * Validation Pattern
		 */
		validateProfile(profileModel);
		profileModel = encryptPwd(profileModel);
		Role role = Role.getRole(profileModel.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profileModel.getRoleId() + "");
		switch (role) {
		case CUSTOMER:
			validateCustomer(profileModel);
			profileModel = saveCustomerData(profileModel);
			break;
		case DRIVER:
		case FRANCHISE:
		case FLEET_OPERATOR:
		case WAREHOUSE:
		case BDO:
		case BDM:
		case OPERATIONAL_TEAM:
		case COORDINATOR:
		case FIELDOFFICER:
		case EMPLOYEE:
		case HELPCENTER:
		case HELPCENTERLEVEL2:
		case HELPCENTERLEVEL3:
		case CHANNEL_PARTNER:
			isMobileNumberExists(profileModel);
			isEmailIdExists(profileModel);
			profileModel = saveProfileData(profileModel);
			break;
		default:
			break;
		}
		return profileModel;
	}

	private boolean validateProfile(ProfileModel profileModel) {
		/* MobileNumber Validation */
		if (null == profileModel.getMobileNumber())
			throw new NOT_FOUND("Please enter mobileNumber");
		/* FirstName Validation */
		if (null == profileModel.getFirstName())
			throw new NOT_FOUND("Please enter firstname");
		/* FirstName Validation */
		if (!dataValidation.isValidate(profileModel.getFirstName(), DataValidation.FIRSTNAME_PATTERN))
			throw new FIRSTNAME_PATTERN_NOT_MATCH();
		/* EmailID Validation */
		if (null == profileModel.getEmailId())
			throw new NOT_FOUND("Please enter emailId");
		if (!dataValidation.isValidate(profileModel.getEmailId(), DataValidation.EMAIL_PATTERN))
			throw new EMAILID_PATTERN_NOT_MATCH();
		/* Password Validation */
		if (!dataValidation.isValidate(profileModel.getPassword(), DataValidation.PASSWORD_PATTERN))
			throw new PASSWORD_PATTERN_NOT_MATCHED();
		/* Password and ConfirmPasswor Match */
		if (!profileModel.getPassword().equals(profileModel.getConfirmPassword()))
			throw new PASSWORD_MISMATCH();
		return true;

	}

	private ProfileModel saveProfileData(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		profileDomain = profileDAO.saveProfile(profileDomain);
		BeanUtils.copyProperties(profileDomain, profileModel);
		return profileModel;

	}

	private ProfileModel saveCustomerData(ProfileModel profileModel) throws Exception {
		if (!profileModel.isFrequentCustomer()) {
			if (!profileModel.isIsTermsAndCondition())
				throw new NOT_FOUND("Please accept the terms and conditions");
			if(null == profileModel.getTermsAndConditionsId())
				throw new NOT_FOUND("Please mention the  terms and conditions Id");
		}
		ProfileDomain profileDomain = new ProfileDomain();
		profileModel.setIsActive(true);
		BeanUtils.copyProperties(profileModel, profileDomain);
		profileDomain = profileDAO.saveProfileCustomer(profileDomain);
		/**
		 * Send sms
		 */
		String message = "Dear "+ profileDomain.getFirstName() +",\r\nThank you for registering with MoveMyGoods.Click https://bit.ly/39X7wWd " + 
								"to book your trip.Call 901 902 903 6 for support";
						emailValidator.sendSMSMessage(profileModel.getMobileNumber().toString(),null, message, false);

		/**
		 * Send Email
		 */
		Map<String, Object> variables = new HashMap<>();
		variables.put("customerName", profileModel.getFirstName());
		String body = emailValidator.generateMailHtml("registration", variables);
		emailValidator.sendEmail("Move My Goods", profileModel.getEmailId(), body);
		/**
		 * Create Object for PromocodeCode
		 */
		ProfileModel promoObj = new ProfileModel(profileDomain.getId());
		String reqBody = JsonUtil.toJsonString(promoObj);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpPost(
				mmgProperties.getFareUrl() + "v1" + "/savePromocode/",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), reqBody, MmgEnum.FARE.name());

		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new PROMOCODE_ALREADY_GENERATED();
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		BeanUtils.copyProperties(profileDomain, profileModel);
		return profileModel;
	}

	@Override
	public ProfileModel getProfile(String id) throws Exception {
		ProfileDomain profileDomain = profileDAO.getProfile(id);
		BigInteger mydefault = BigInteger.ZERO;
		if (profileDomain.getAlternativeNumber() == null)
			profileDomain.setAlternativeNumber(mydefault);
		if (profileDomain.getCityId() == 0)
			profileDomain.setCityId(0);
		if (profileDomain.getStateId() == 0)
			profileDomain.setStateId(0);
		if (profileDomain.getCountryId() == 0)
			profileDomain.setCountryId(0);
		return profileMapper.entity(profileDomain);
	}

	@Override
	public ProfileModel getProfileId(String mobileNumber, boolean status) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		if (!status)
			profileDomain = profileDAO.getProfileId(mobileNumber);
		else
			profileDomain = profileDAO.getCustomerProfileId(mobileNumber);
		ProfileModel profileModel = new ProfileModel();
		BeanUtils.copyProperties(profileDomain, profileModel);
		return profileModel;
	}

	private boolean validProfile(ProfileModel profileModel) {
		/* MobileNumber Validation */
		if (null == profileModel.getMobileNumber())
			throw new NOT_FOUND("Please enter mobileNumber");
		/* FirstName Validation */
		if (null == profileModel.getFirstName())
			throw new NOT_FOUND("Please enter firstname");
		/* FirstName Validation */
		if (!dataValidation.isValidate(profileModel.getFirstName(), DataValidation.FIRSTNAME_PATTERN))
			throw new FIRSTNAME_PATTERN_NOT_MATCH();
		/* EmailID Validation */
		if (null == profileModel.getEmailId())
			throw new NOT_FOUND("Please enter emailId");
		if (!dataValidation.isValidate(profileModel.getEmailId(), DataValidation.EMAIL_PATTERN))
			throw new EMAILID_PATTERN_NOT_MATCH();
		return true;

	}

	/**
	 * Author:Vidya S K Modified Date: 2/15/2020,2/17/2020 Description: 1. To Check
	 * the Role 2. Update the Profile
	 * 
	 */
	@Override
	public String updateProfileAndCustomer(ProfileModel profileModel) throws Exception {
		/**
		 * Pattern Validation for firstName,EmalId,Password and ConfirmPassword
		 */

		Role role = Role.getRole(profileModel.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profileModel.getRoleId() + "");
		switch (role) {
		case CUSTOMER:
			validProfile(profileModel);
			validateCustomerId(profileModel);
			return updateCustomer(profileModel);
		case FRANCHISE:
			validProfile(profileModel);
			validateProfileId(profileModel);
			return updateCompany(profileModel);
		case FLEET_OPERATOR:
		case WAREHOUSE:
			validProfile(profileModel);
			validateProfileId(profileModel);
			return updateProfile(profileModel);
		case DRIVER:
			validateUpdateDriver(profileModel);
			return updateDriverProfile(profileModel);
		case EMPLOYEE:
		case OWNER:
		case TIEUPS:
		case OPERATIONAL_TEAM:
		case ENTERPRISE:
		case BDM:
		case BDO:
		case CHANNEL_PARTNER:
			validProfile(profileModel);
			validateProfileId(profileModel);
			return updateCompany(profileModel);
		default:
			break;
		}
		return null;
	}

	private ProfileModel validateEmail(ProfileModel profileModel) throws Exception {
		if (profileModel.getEmailId() == null || profileModel.getEmailId().isEmpty())
			profileModel.setEmailId(null);
		else {
			if (!dataValidation.isValidate(profileModel.getEmailId(), DataValidation.EMAIL_PATTERN))
				throw new EMAILID_PATTERN_NOT_MATCH();
			ForgotPasswordDomain emailObj = forgotPasswordDAO.getprofileByEmail(profileModel.getEmailId());
			if (null == emailObj) {
				profileModel.setEmailId(profileModel.getEmailId());
				return profileModel;
			}
			if (emailObj.getId() != profileModel.getId())
				throw new USER_ALREADY_EXIST();
		}
		return profileModel;
	}

	private ProfileModel validateMobile(ProfileModel profileModel) throws Exception {
		ForgotPasswordDomain mobileObj = forgotPasswordDAO.getProfile(profileModel.getMobileNumber());
		if (null == mobileObj) {
			profileModel.setMobileNumber(profileModel.getMobileNumber());
			return profileModel;
		}
		if (!mobileObj.getId().equalsIgnoreCase(profileModel.getId()))
			throw new USER_ALREADY_EXIST();
		return profileModel;
	}

	private void validateUpdateDriver(ProfileModel profileModel) throws Exception {
		validateMobile(profileModel);
		validateEmail(profileModel);
		if (null != profileModel.getPanNumber()) {
			if (profileModel.getPanNumber().trim().isEmpty()
					|| profileModel.getPanNumber().equalsIgnoreCase("PAN_NUMBER"))
				profileModel.setPanNumber(null);
			else {
				panNumber(profileModel.getPanNumber(), profileModel.getId(), true);
			}
		}
		if (null != profileModel.getAadharNumber()) {
			if (profileModel.getAadharNumber().intValue() != 0)
				aadharNumber(profileModel.getAadharNumber(), profileModel.getId(), true);
		}
		if (null != profileModel.getAccountNumber()) {
			if (profileModel.getAccountNumber().intValue() != 0)
				accountNumber(profileModel.getAccountNumber(), profileModel.getId(), true);
		}

	}

	private void validateCustomerId(ProfileModel profileModel) throws Exception {
		ForgotPasswordDomain mobileObj = forgotPasswordDAO.getCustomer(profileModel.getMobileNumber());
		if (null == mobileObj)
			throw new USER_NOT_EXISTS();
		if (mobileObj.getId().equalsIgnoreCase(profileModel.getId()))
			throw new PROFILE_NOT_FOUND();
		ForgotPasswordDomain emailObj = forgotPasswordDAO.getCustomerEmailId(profileModel.getEmailId());
		if (null == emailObj)
			throw new USER_NOT_EXISTS();
		if (emailObj.getId().equalsIgnoreCase(profileModel.getId()))
			throw new PROFILE_NOT_FOUND();
	}

	private void validateProfileId(ProfileModel profileModel) throws Exception {
		ForgotPasswordDomain mobileObj = forgotPasswordDAO.getProfile(profileModel.getMobileNumber());
		if (null == mobileObj)
			throw new USER_NOT_EXISTS();
		if (mobileObj.getId().equalsIgnoreCase(profileModel.getId()))
			throw new PROFILE_NOT_FOUND();
		ForgotPasswordDomain emailObj = forgotPasswordDAO.getprofileByEmail(profileModel.getEmailId());
		if (null == emailObj)
			throw new USER_NOT_EXISTS();
		if (emailObj.getId().equalsIgnoreCase(profileModel.getId()))
			throw new PROFILE_NOT_FOUND();
	}

	private String updateCustomer(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updateCustomerProfile(profileDomain);
	}

	private String updateProfile(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updateProfile(profileDomain);
	}

	private String updateDriverProfile(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updateDriverProfile(profileDomain);
	}

	private String updateCompany(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updateCompany(profileDomain);
	}

	private ProfileModel encryptPwd(ProfileModel profileModel) {
		profileModel.setPassword(CommonUtils.encriptString(profileModel.getPassword()));
		profileModel.setConfirmPassword(CommonUtils.encriptString(profileModel.getConfirmPassword()));
		return profileModel;
	}

	/**
	 * Author:Sindhu S Modified Date: 2/16/2020 Description: 1. update password
	 * 
	 */
	@Override
	public String updatePassword(ProfileModel profileModel) throws Exception {
		Role role = Role.getRole(profileModel.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role) {
			getRoleId(profileModel);
			role = Role.getRole(profileModel.getRoleId() + "");
		}
		/** Password Validation */
		if (!dataValidation.isValidate(profileModel.getPassword(), DataValidation.PASSWORD_PATTERN))
			throw new PASSWORD_PATTERN_NOT_MATCHED();
		/** Password and ConfirmPassword Match */
		if (!profileModel.getPassword().equals(profileModel.getConfirmPassword()))
			throw new PASSWORD_MISMATCH();
		profileModel = encryptPwd(profileModel);
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		switch (role) {
		case CUSTOMER:
			return profileDAO.updatePasswordCustomer(profileDomain);
		case FRANCHISE:
		case FLEET_OPERATOR:
		case WAREHOUSE:
		case DRIVER:
		case EMPLOYEE:
		case OWNER:
		case TIEUPS:
		case OPERATIONAL_TEAM:
		case ENTERPRISE:
		case BDM:
		case BDO:
		case CHANNEL_PARTNER:
			return profileDAO.updatePassword(profileDomain);
		}
		return "";
	}

	@Override
	public String awsSmsOTP(ProfileModel profileModel) throws Exception {
		Role role = Role.getRole(profileModel.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profileModel.getRoleId() + "");
		switch (role) {
		case CUSTOMER:
			validateCustomer(profileModel);
			return sendOtpOrEmailToCustomer(profileModel);
		case DRIVER:
		case FRANCHISE:
		case FLEET_OPERATOR:
		case WAREHOUSE:
		case EMPLOYEE:
		case OWNER:
		case TIEUPS:
		case BDM:
		case BDO:
		case ENTERPRISE:
		case CHANNEL_PARTNER:
			validateProfileOtp(profileModel);
			return sendOtpOrEmailToCustomer(profileModel);
		}
		return null;
	}

	private void validateCustomer(ProfileModel profileModel) throws Exception {
		if (!profileModel.getEmailId().isEmpty()) {
			ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO.getCustomerEmailId(profileModel.getEmailId());
			if (null != forgotPasswordDomain) {
				throw new CUSTOMER_ALREADY_EXIST(profileModel.getEmailId());
			}
		}
		ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO
				.getCustomerByMobileNumberOrEmailId(profileModel.getMobileNumber(), profileModel.getEmailId());
		if (null != forgotPasswordDomain) {
			throw new CUSTOMER_ALREADY_EXIST(profileModel.getMobileNumber().toString());
		}
	}

	private void validateProfileOtp(ProfileModel profileModel) throws Exception {
		if (!profileModel.getEmailId().isEmpty()) {
			ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO.getprofileByEmail(profileModel.getEmailId());
			if (null != forgotPasswordDomain) {
				throw new USER_ALREADY_EXIST(profileModel.getEmailId());
			}
		}
		ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO.getProfileByMobileNumberOrEmailId(
				profileModel.getMobileNumber(), profileModel.getEmailId(), profileModel.getRoleId());
		if (null != forgotPasswordDomain) {
			throw new USER_ALREADY_EXIST(profileModel.getMobileNumber().toString());
		}
	}

	private String sendOtpOrEmailToCustomer(ProfileModel profileModel) throws Exception {
		String otp = emailValidator.generateOTP();
		if (null != profileModel) {

			/**
			 * Send otp and email
			 */
			if (null != profileModel.getMobileNumber() && null != profileModel.getEmailId()
					&& profileModel.getEmailId().trim().length() > 0) {
				/**
				 * Send sms
				 */
				otp = emailValidator.sendSMSMessage(profileModel.getMobileNumber().toString(),otp,
						Constants.CUSTOMER_OTP_MESSAGE, true);
				/**
				 * Send Email
				 */
				Map<String, Object> variables = new HashMap<>();
				variables.put("customerOtp", otp);
				String body = emailValidator.generateMailHtml("customer", variables);
				emailValidator.sendEmail("Your Otp", profileModel.getEmailId(), body);
			} else if (null != profileModel.getMobileNumber()) {
				/**
				 * Send sms
				 */
				otp = emailValidator.sendSMSMessage(profileModel.getMobileNumber().toString(),otp,
						Constants.CUSTOMER_OTP_MESSAGE, true);
			} else if (null != profileModel.getEmailId() && profileModel.getEmailId().trim().length() > 0) {
				/**
				 * Send Email
				 */
				Map<String, Object> variables = new HashMap<>();
				variables.put("customerOtp", otp);
				String body = emailValidator.generateMailHtml("customer", variables);
				emailValidator.sendEmail("Your Otp", profileModel.getEmailId(), body);
			}
		}
		/**
		 *
		 * Save otp into table
		 */
		Otp otpObj = new Otp();
		otpObj.setMobileNumber(profileModel.getMobileNumber().toString());
		otpObj.setOtp(otp);
		otpDAO.save(otpObj);
		return "Otp sent successfully";
	}

	@Override
	public ProfileModel getSmsOtp(String otp, String mobileNumber) throws Exception {
		ProfileModel profileModel = new ProfileModel();
		Otp otpObj = otpDAO.getOtp(mobileNumber.toString(),null);
		if (null != otpObj) {
			profileModel.setOtpIsActive(true);
		} else
			throw new INVALID_OTP();
		return profileModel;
	}

	@Override
	public String Activation(ProfileModel profileModel) throws Exception {
		validateProfileIdByToken(profileModel.getId());
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.Activation(profileDomain);
	}

	@Override
	public String disableFranchise(ProfileModel profileModel) throws Exception {
		validateProfileIdByToken(profileModel.getId());
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.disableFranchise(profileDomain);
	}

	@Override
	public List<ProfileModel> getProfilebyRole(int roleId) throws Exception {
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(roleId + "");
		List<ProfileDomain> profileDomain = profileDAO.getProfilebyRole(roleId);
		return profileMapper.entityList(profileDomain);
	}

	@Override
	public String saveProfileforAndriod(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.saveProfileforAndriod(profileDomain);
	}

	@Override
	public List<ProfileModel> getActiveCustomer() throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.customerActiveOrInActive(true);
		return profileMapper.entityList(profileDomain);
	}

	@Override
	public List<ProfileModel> getDisableCustomer() throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.customerActiveOrInActive(false);
		return profileMapper.entityList(profileDomain);
	}

	/**
	 * Author:Vidya S K Created Date: 06/09/2019 Modified Date: 06/09/2019 -
	 * 06/09/2019 Description: get customer Acive and Inactive List based on city
	 * and Status.
	 * 
	 */

	@Override
	public List<ProfileModel> getCustomerbyCity(int cityId, boolean status) throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.getCustomerbyCity(cityId, status);
		return profileMapper.entityList(profileDomain);
	}

	/**
	 * Author:Vidya S K Created Date: 06/09/2019 Modified Date: 06/09/2019 -
	 * 06/09/2019 Description: get customer Acive and Inactive List based on state
	 * and Status.
	 * 
	 */

	@Override
	public List<ProfileModel> getCustomerByState(int stateId, boolean status) throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.getCustomerByState(stateId, status);
		return profileMapper.entityList(profileDomain);
	}

	@Override
	public String customerActivation(ProfileModel profileModel) throws Exception {
		validateCustomerIdByToken(profileModel.getId());
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.customerActivation(profileDomain);
	}

	@Override
	public String disableCustomer(ProfileModel profileModel) throws Exception {
		validateCustomerIdByToken(profileModel.getId());
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.disableCustomer(profileDomain);
	}

	@Override
	public List<ProfileModel> getprofileIdbyCityid(int cityId) throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.profileDetailsByCity(Role.DRIVER.getCode(), cityId);
		return profileMapper.entityList(profileDomain);
	}

	@Override
	public String deleteCustomer(int id) throws Exception {
		return profileDAO.deleteCustomer(id);
	}

	@Override
	public String deleteProfile(int id) throws Exception {
		return profileDAO.deleteProfile(id);
	}

	@Override
	public ProfileModel getProfileById(String id) throws Exception {
		validateProfileIdByToken(id);
		ProfileDomain profileDomain = profileDAO.getProfileById(id);
		logger.info("service====>" + profileDomain);
		ProfileDomain profileDomain1 = profileDAO.getProfileByCityId(profileDomain.getCityId(), id);
		return profileMapper.entity(profileDomain1);
	}

	/**
	 * Author:Vidya S K Modified Date: 2/17/2020 Description: 1. Update Token
	 * 
	 */
	@Override
	public String updateTokenId(ProfileModel profileModel) throws Exception {
		Role role = Role.getRole(profileModel.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profileModel.getRoleId() + "");
		switch (role) {
		case CUSTOMER:
			validateCustomerIdByToken(profileModel.getId());
			return updateCustomerToken(profileModel);
		case FRANCHISE:
		case FLEET_OPERATOR:
		case WAREHOUSE:
		case DRIVER:
		case EMPLOYEE:
		case OWNER:
		case TIEUPS:
		case OPERATIONAL_TEAM:
		case ENTERPRISE:
		case BDM:
		case BDO:
		case CHANNEL_PARTNER:
			validateProfileIdByToken(profileModel.getId());
			return updateProfileToken(profileModel);
		}
		return "Token";
	}

	private void validateCustomerIdByToken(String id) throws Exception {
		ProfileDomain profileDomain = profileDAO.getProfileCustomer(id);
		if (null == profileDomain)
			throw new PROFILE_NOT_FOUND();
	}

	private void validateProfileIdByToken(String id) throws Exception {
		ProfileDomain profileDomain = profileDAO.getProfile(id);
		if (null == profileDomain)
			throw new PROFILE_NOT_FOUND();
	}

	private String updateCustomerToken(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updateTokenIdCustomer(profileDomain);
	}

	private String updateProfileToken(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updateProfileTokenId(profileDomain);

	}

	/**
	 * Author:Sindhu S Modified Date: 2/17/2020 Description: 1. forgotpassword
	 * verify otp
	 * 
	 */
	@Override
	public String forgotPasswordCustomerSmsOTP(ProfileModel profileModel) throws Exception {
		Role role = Role.getRole(profileModel.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role) {
			getRoleId(profileModel);
			role = Role.getRole(profileModel.getRoleId() + "");
		}
		switch (role) {
		case CUSTOMER:
			getCustomerDetails(profileModel.getMobileNumber());
			return sendOtpOrEmailToCustomer(profileModel);
		case DRIVER:
		case FRANCHISE:
		case FLEET_OPERATOR:
		case WAREHOUSE:
		case EMPLOYEE:
		case OWNER:
		case TIEUPS:
		case BDM:
		case BDO:
		case CHANNEL_PARTNER:
			getProfileDetails(profileModel.getMobileNumber());
			return sendOtpOrEmailToCustomer(profileModel);
		}
		return null;
	}

	private ProfileModel getRoleId(ProfileModel profileModel) throws Exception {
		ForgotPasswordDomain forgotProfilePasswordDomain = forgotPasswordDAO
				.getProfileByMobileNumberOrEmailId(profileModel.getMobileNumber(), null, 0);
		if (null == forgotProfilePasswordDomain) {
			throw new USER_NOT_EXISTS();
		}
		profileModel.setRoleId(forgotProfilePasswordDomain.getRoleId());
		return profileModel;
	}

	private void getCustomerDetails(String mobileNumber) throws Exception {
		ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO.getCustomer(mobileNumber);
		if (null == forgotPasswordDomain) {
			throw new USER_NOT_EXISTS();
		}
	}

	private void getProfileDetails(String mobileNumber) throws Exception {
		ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO.getProfile(mobileNumber);
		if (null == forgotPasswordDomain) {
			throw new USER_NOT_EXISTS();
		}
	}

	/**
	 * Author:Sindhu S Modified Date: 2/3/2020 Description: 1. Save profile with
	 * validations.
	 */
	@Override
	public ProfileModel saveProfileDetails(ProfileModel profileModel) throws Exception {
		Role role = Role.getRole(profileModel.getRoleId() + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(profileModel.getRoleId() + "");
		if (null == profileModel.getMobileNumber())
			throw new NOT_FOUND("Please enter the mobileNumber");
		/* Password Validation */
		if (!dataValidation.isValidate(profileModel.getPassword(), DataValidation.PASSWORD_PATTERN))
			throw new PASSWORD_PATTERN_NOT_MATCHED();
		/* Password and ConfirmPasswor Match */
		if (!profileModel.getPassword().equals(profileModel.getConfirmPassword()))
			throw new PASSWORD_MISMATCH();
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		isMobileNumberExists(profileModel);
		switch (role) {
		case FRANCHISE:
		case FLEET_OPERATOR:
			validateFleetOperator(profileDomain);
			isEmailIdExists(profileModel);
			profileDomain = profile(profileDomain);
			break;
		case WAREHOUSE:
			validateEmailAcctPan(profileDomain);
			isEmailIdExists(profileModel);
			panNumber(profileDomain.getPanNumber(), null, false);
			accountNumber(profileDomain.getAccountNumber(), null, false);
			profileDomain = profile(profileDomain);
			break;
		case DRIVER:
			validateDriver(profileDomain);
			accountNumber(profileDomain.getAccountNumber(), null, false);
			profileDomain = profile(profileDomain);
			break;
		case BDO:
		case BDM:
		case COORDINATOR:
		case FIELDOFFICER:
		case HELPCENTER:
		case OPERATIONAL_TEAM:
		case HELPCENTERLEVEL2:
		case HELPCENTERLEVEL3:
		case CHANNEL_PARTNER:
			validateFieldOfficer(profileDomain);
			isEmailIdExists(profileModel);
			profileDomain = profile(profileDomain);
			break;
		case ENTERPRISE:
			isEmailIdExists(profileModel);
			gstNumber(profileModel.getGstNo());
			profileDomain = profile(profileDomain);
			break;
		default:
			break;
		}
		BeanUtils.copyProperties(profileDomain, profileModel);
		return profileModel;
	}
	private void validateEmailAcctPan(ProfileDomain profileDomain) throws Exception {
		if (null == profileDomain.getEmailId())
			throw new NOT_FOUND("Please enter the emailId");
		if (!dataValidation.isValidate(profileDomain.getEmailId(), DataValidation.EMAIL_PATTERN))
			throw new EMAILID_PATTERN_NOT_MATCH();
		if (null == profileDomain.getAccountNumber())
			throw new NOT_FOUND("Please enter the accountNumber");
		if (null == profileDomain.getPanNumber())
			throw new NOT_FOUND("Please enter the panNumber");
	}

	private void validateDriver(ProfileDomain profileDomain) throws Exception {
		ProfileModel profileModel = new ProfileModel();
		if (null != profileDomain.getEmailId()) {
			if (profileDomain.getEmailId().trim().isEmpty())
				profileDomain.setEmailId(null);
			else {
				if (!dataValidation.isValidate(profileDomain.getEmailId(), DataValidation.EMAIL_PATTERN))
					throw new EMAILID_PATTERN_NOT_MATCH();
				BeanUtils.copyProperties(profileDomain, profileModel);
				isEmailIdExists(profileModel);
			}
		}
		if (profileDomain.getAadharNumber().intValue() != 0)
			aadharNumber(profileDomain.getAadharNumber(), null, false);

		if (null != profileDomain.getPanNumber())
			if (profileDomain.getPanNumber().trim().isEmpty())
				profileDomain.setPanNumber(null);
			else
				panNumber(profileDomain.getPanNumber(), null, false);

	}

	private void validateFieldOfficer(ProfileDomain profileDomain) throws Exception {
		validateEmailAcctPan(profileDomain);
		if (null == profileDomain.getAadharNumber())
			throw new NOT_FOUND("Please enter the aadharNumber");
		if (profileDomain.getPanNumber() != null && profileDomain.getPanNumber().trim().length() > 0)
			panNumber(profileDomain.getPanNumber(), null, false);
		if (null != profileDomain.getAadharNumber())
			aadharNumber(profileDomain.getAadharNumber(), null, false);
		if (null != profileDomain.getAccountNumber())
			accountNumber(profileDomain.getAccountNumber(), null, false);
	}

	private void validateFleetOperator(ProfileDomain profileDomain) throws Exception {
		if (null == profileDomain.getEmailId())
			throw new NOT_FOUND("Please enter the emailId");
		if (!dataValidation.isValidate(profileDomain.getEmailId(), DataValidation.EMAIL_PATTERN))
			throw new EMAILID_PATTERN_NOT_MATCH();
		if (profileDomain.getGstNo() != null && profileDomain.getGstNo().trim().length() > 0)
			gstNumber(profileDomain.getGstNo());
		if (profileDomain.getPanNumber() != null && profileDomain.getPanNumber().trim().length() > 0)
			panNumber(profileDomain.getPanNumber(), null, false);
		if (profileDomain.getAccountNumber() != null)
			accountNumber(profileDomain.getAccountNumber(), null, false);
	}

	private String panNumber(String panNumber, String id, boolean updateStatus) throws Exception {
		JSONObject data = null;
		String profileId = null;
		Map<String, Object> adminhttpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1" + "/validatePanNumber/" + panNumber + "/" + updateStatus,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.OWNER.name());
		System.out.println(adminhttpResponse);
		int adminstatusCode = (int) adminhttpResponse.get("statusCode");
		JSONObject adminjsonResponse = (JSONObject) adminhttpResponse.get("response");
		if (updateStatus) {
			if (adminjsonResponse.has("data"))
				if (((data = (JSONObject) adminjsonResponse.get("data")) != null)
						|| ((data = (JSONObject) adminjsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) adminjsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new PAN_NUMBER_ALREADY_EXIST();
				}
		} else if (adminstatusCode != HttpStatus.OK.value()) {
			throw new PAN_NUMBER_ALREADY_EXIST();
		}

		/*Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getTieupseUrl() + "v1" + "/IsAlltieupsPanNumberExists/" + panNumber + "/" + updateStatus,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.TIEUPS.name());
		System.out.println(httpResponse);
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (updateStatus) {
			if (jsonResponse.has("data"))
				if (((data = (JSONObject) jsonResponse.get("data")) != null)
						|| ((data = (JSONObject) jsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) jsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new PAN_NUMBER_ALREADY_EXIST();
				}
		} else if (statusCode != HttpStatus.OK.value()) {
			throw new PAN_NUMBER_ALREADY_EXIST();
		} */

		Map<String, Object> franchisehttpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFranchiseUrl() + "v1" + "/IsAllfranchisePanNumberExists/" + panNumber + "/"
						+ updateStatus,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.TIEUPS.name());
		int franchisestatusCode = (int) franchisehttpResponse.get("statusCode");
		JSONObject franchisejsonResponse = (JSONObject) franchisehttpResponse.get("response");
		if (updateStatus) {
			if (franchisejsonResponse.has("data"))
				if (((data = (JSONObject) franchisejsonResponse.get("data")) != null)
						|| ((data = (JSONObject) franchisejsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) franchisejsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new PAN_NUMBER_ALREADY_EXIST();
				}
		} else if (franchisestatusCode != HttpStatus.OK.value()) {
			throw new PAN_NUMBER_ALREADY_EXIST();
		}

//		Map<String, Object> fleethttpResponse = GenericHttpClient.doHttpGet(
//				mmgProperties.getFleetUrl()+"v1" + "/IsAllFleetPanNumberExists/" + panNumber,
//				ApiUtility.getHeaders(mmgProperties.getFleetHeaderApiKey(), null),
//				MmgEnum.TIEUPS.name());
//		int fleetstatusCode = (int) fleethttpResponse.get("statusCode");
//		JSONObject fleetjsonResponse = (JSONObject) fleethttpResponse.get("response");
//		if (fleetjsonResponse != null) {
//			if (fleetstatusCode != HttpStatus.OK.value()) {
//				throw new PAN_NUMBER_ALREADY_EXIST();
//			}
//		} else {
//			throw new BACKEND_SERVER_ERROR();
//		}
		return null;
	}

	private String accountNumber(BigInteger accountNumber, String id, boolean updateStatus) throws Exception {
		JSONObject data = null;
		String profileId = null;
		Map<String, Object> adminhttpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1" + "/validateAccountNumber/" + accountNumber + "/" + updateStatus,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.OWNER.name());
		System.out.println(adminhttpResponse);
		int adminstatusCode = (int) adminhttpResponse.get("statusCode");
		JSONObject adminjsonResponse = (JSONObject) adminhttpResponse.get("response");
		if (updateStatus) {
			if (adminjsonResponse.has("data"))
				if (((data = (JSONObject) adminjsonResponse.get("data")) != null)
						|| ((data = (JSONObject) adminjsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) adminjsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new ACCOUNT_NUMBER_ALREADY_EXIST();
				}
		} else if (adminstatusCode != HttpStatus.OK.value()) {
			throw new ACCOUNT_NUMBER_ALREADY_EXIST();
		}

		/*Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getTieupseUrl() + "v1" + "/IsAlltieupsAccountNumberExists/" + accountNumber + "/"
						+ updateStatus,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.TIEUPS.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (updateStatus) {
			if (jsonResponse.has("data"))
				if (((data = (JSONObject) jsonResponse.get("data")) != null)
						|| ((data = (JSONObject) jsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) jsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new ACCOUNT_NUMBER_ALREADY_EXIST();
				}
		} else if (statusCode != HttpStatus.OK.value()) {
			throw new ACCOUNT_NUMBER_ALREADY_EXIST();
		} */

		Map<String, Object> franchisehttpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFranchiseUrl() + "v1" + "/IsAllfranchiseAccountNumberExists/" + accountNumber + "/"
						+ updateStatus,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.TIEUPS.name());
		int franchisestatusCode = (int) franchisehttpResponse.get("statusCode");
		JSONObject franchisejsonResponse = (JSONObject) franchisehttpResponse.get("response");
		if (updateStatus) {
			if (franchisejsonResponse.has("data"))
				if (((data = (JSONObject) franchisejsonResponse.get("data")) != null)
						|| ((data = (JSONObject) franchisejsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) franchisejsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new ACCOUNT_NUMBER_ALREADY_EXIST();
				}
		} else if (franchisestatusCode != HttpStatus.OK.value()) {
			throw new ACCOUNT_NUMBER_ALREADY_EXIST();
		}
//		Map<String, Object> fleethttpResponse = GenericHttpClient.doHttpGet(
//				mmgProperties.getFleetUrl()+"v1" + "/IsAllFleetAccountNumberExists/" + accountNumber,
//				ApiUtility.getHeaders(mmgProperties.getFleetHeaderApiKey(), null),
//				MmgEnum.TIEUPS.name());
//		int fleetstatusCode = (int) fleethttpResponse.get("statusCode");
//		JSONObject fleetjsonResponse = (JSONObject) fleethttpResponse.get("response");
//		if (fleetjsonResponse != null) {
//			if (fleetstatusCode != HttpStatus.OK.value()) {
//				throw new ACCOUNT_NUMBER_ALREADY_EXIST();
//			}
//		} else {
//			throw new BACKEND_SERVER_ERROR();
//		}
		return null;
	}

	private String gstNumber(String gstNumber) throws Exception {
		/*Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getTieupseUrl() + "v1" + "/IsAlltieupsGstNumberExists/" + gstNumber,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.TIEUPS.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new GST_NUMBER_ALREADY_EXIST();
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		} */
		return null;
	}

	private String aadharNumber(BigInteger aadharNumber, String id, boolean updateStatus) throws Exception {
		JSONObject data = null;
		String profileId = null;
		Map<String, Object> adminhttpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAdminUrl() + "v1" + "/validateAadharNumber/" + aadharNumber + "/" + updateStatus,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.OWNER.name());
		System.out.println(adminhttpResponse);
		int adminstatusCode = (int) adminhttpResponse.get("statusCode");
		JSONObject adminjsonResponse = (JSONObject) adminhttpResponse.get("response");
		if (updateStatus) {
			if (adminjsonResponse.has("data"))
				if (((data = (JSONObject) adminjsonResponse.get("data")) != null)
						|| ((data = (JSONObject) adminjsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) adminjsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new AADHAR_NUMBER_ALREADY_EXIST();
				}
		} else if (adminstatusCode != HttpStatus.OK.value()) {
			throw new AADHAR_NUMBER_ALREADY_EXIST();
		}

		/*Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getTieupseUrl() + "v1" + "/validateTieupsAadharNumber/" + aadharNumber + "/"
						+ updateStatus,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.TIEUPS.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (updateStatus) {
			if (jsonResponse.has("data"))
				if (((data = (JSONObject) jsonResponse.get("data")) != null)
						|| ((data = (JSONObject) jsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) jsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new AADHAR_NUMBER_ALREADY_EXIST();
				}
		} else if (statusCode != HttpStatus.OK.value()) {
			throw new AADHAR_NUMBER_ALREADY_EXIST();
		} */

		Map<String, Object> franchisehttpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFranchiseUrl() + "v1" + "/IsAllfranchiseAadharNumberExists/" + aadharNumber + "/"
						+ updateStatus,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.TIEUPS.name());
		int franchisestatusCode = (int) franchisehttpResponse.get("statusCode");
		JSONObject franchisejsonResponse = (JSONObject) franchisehttpResponse.get("response");
		if (updateStatus) {
			if (franchisejsonResponse.has("data"))
				if (((data = (JSONObject) franchisejsonResponse.get("data")) != null)
						|| ((data = (JSONObject) franchisejsonResponse.get("data")).length() != 0)
						|| !((data = (JSONObject) franchisejsonResponse.get("data")).toString().contentEquals("{}"))) {
					profileId = data.getString("profileId");
					if (!id.equalsIgnoreCase(profileId))
						throw new AADHAR_NUMBER_ALREADY_EXIST();
				}
		} else if (franchisestatusCode != HttpStatus.OK.value()) {
			throw new AADHAR_NUMBER_ALREADY_EXIST();
		}

//		Map<String, Object> fleethttpResponse = GenericHttpClient.doHttpGet(
//				mmgProperties.getFleetUrl()+"v1" + "/IsAllFleetAadharNumberExists/" + aadharNumber,
//				ApiUtility.getHeaders(mmgProperties.getFleetHeaderApiKey(), null),
//				MmgEnum.TIEUPS.name());
//		int fleetstatusCode = (int) fleethttpResponse.get("statusCode");
//		JSONObject fleetjsonResponse = (JSONObject) fleethttpResponse.get("response");
//		if (fleetjsonResponse != null) {
//			if (fleetstatusCode != HttpStatus.OK.value()) {
//				throw new AADHAR_NUMBER_ALREADY_EXIST();
//			}
//		} else {
//			throw new BACKEND_SERVER_ERROR();
//		}
		return null;
	}

	private ProfileDomain profile(ProfileDomain profileDomain) throws Exception {
		profileDomain.setPassword(CommonUtils.encriptString(profileDomain.getPassword()));
		profileDomain.setConfirmPassword(CommonUtils.encriptString(profileDomain.getConfirmPassword()));
		profileDomain.setIsActive(false);
		BigInteger pin = BigInteger.ZERO;
		if (profileDomain.getPincode() == null)
			profileDomain.setPincode(pin);
		return profileDAO.saveProfile(profileDomain);
	}

	/**
	 * Author:Vidya S K Modified Date: 2/17/2020 Description: 1. Update Password By
	 * Id
	 * 
	 */
	@Override
	public String updatePasswordById(ProfileModel profileModel) throws Exception {
		Role role = Role.getRole(profileModel.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profileModel.getRoleId() + "");
		/** Password Validation */
		if (!dataValidation.isValidate(profileModel.getPassword(), DataValidation.PASSWORD_PATTERN))
			throw new PASSWORD_PATTERN_NOT_MATCHED();
		/** Password and ConfirmPassword Match */
		if (!profileModel.getPassword().equals(profileModel.getConfirmPassword()))
			throw new PASSWORD_MISMATCH();
		/** Password and ConfirmPassword Encryption */
		profileModel = encryptPwd(profileModel);
		profileModel = encryptOldPwd(profileModel);
		switch (role) {
		case CUSTOMER:
			validateCustomerIdByToken(profileModel.getId());
			validateOldCustomerPassword(profileModel);
			return updatePasswordByIdCustomer(profileModel);
		case DRIVER:
		case FRANCHISE:
		case FLEET_OPERATOR:
		case WAREHOUSE:
		case OWNER:
		case BDM:
		case BDO:
		case TIEUPS:
		case OPERATIONAL_TEAM:
		case COORDINATOR:
		case FIELDOFFICER:
		case EMPLOYEE:
		case CHANNEL_PARTNER:
			validateProfileIdByToken(profileModel.getId());
			validateOldProfilePassword(profileModel);
			return updatePasswordByIdProfile(profileModel);
		}
		return "Password";
	}

	private ProfileModel encryptOldPwd(ProfileModel profileModel) {
		if (profileModel.getOldPassword() != null || !profileModel.getOldPassword().isEmpty())
			profileModel.setOldPassword(CommonUtils.encriptString(profileModel.getOldPassword()));
		else
			throw new OLD_PASSWORD_NOT_FOUND();
		return profileModel;
	}

	private void validateOldCustomerPassword(ProfileModel profileModel) throws Exception {
		ProfileDomain passwordDomain = profileDAO.getPasswordByIdCustomer(profileModel.getId());
		if (!passwordDomain.getPassword().equals(profileModel.getOldPassword()))
			throw new OLD_PASSWORD__MISMATCH();
	}

	private void validateOldProfilePassword(ProfileModel profileModel) throws Exception {
		ProfileDomain passwordDomain = profileDAO.getPasswordById(profileModel.getId());
		if (!passwordDomain.getPassword().equals(profileModel.getOldPassword()))
			throw new OLD_PASSWORD__MISMATCH();
	}

	private String updatePasswordByIdCustomer(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updatePasswordByIdCustomer(profileDomain);
	}

	private String updatePasswordByIdProfile(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updatePasswordById(profileDomain);
	}

	public boolean isEmailIdExists(ProfileModel profileModel) throws Exception {
		ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO.getprofileByEmail(profileModel.getEmailId());
		if (null == forgotPasswordDomain)
			return true;
		else
			throw new USER_ALREADY_EXIST();
	}

	@Override
	public boolean isMobileNumberExists(ProfileModel profileModel) throws Exception {
		ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO.getProfile(profileModel.getMobileNumber());
		if (null == forgotPasswordDomain)
			return true;
		else
			throw new USER_ALREADY_EXIST();
	}

	@Override
	public String updateWebToken(ProfileModel profileModel) throws Exception {
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updateWebToken(profileDomain);
	}

	@Override
	public ProfileModel getProfileRefferCode(String id, String refferCode) throws Exception {
		ProfileDomain profileDomain = profileDAO.getProfileRefferCode(id, refferCode);
		if (null == profileDomain)
			throw new PROFILE_NOT_FOUND();
		return profileMapper.entity(profileDomain);
	}

	@Override
	public ProfileModel getProfileCustomer(String id) throws Exception {
		ProfileDomain profileDomain = profileDAO.getProfileCustomer(id);
		if (profileDomain == null)
			throw new PROFILE_NOT_FOUND();
		logger.info("service====>" + profileDomain);
		BigInteger mydefault = BigInteger.ZERO;
		if (profileDomain.getAlternativeNumber() == null)
			profileDomain.setAlternativeNumber(mydefault);
		if (profileDomain.getCityId() == 0)
			profileDomain.setCityId(0);
		if (profileDomain.getStateId() == 0)
			profileDomain.setStateId(0);
		if (profileDomain.getCountryId() == 0)
			profileDomain.setCountryId(101);
		if (profileDomain.getGenderId() == 0)
			profileDomain.setGenderId(1);
		if (profileDomain.getPincode() == null)
			profileDomain.setPincode(mydefault);
		return profileMapper.entity(profileDomain);
	}

	@Override
	public String updateCustomerCreditAmount(ProfileModel profileModel) throws Exception {
		validateCustomerIdByToken(profileModel.getId());
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updateCustomerCreditAmount(profileDomain);
	}

	@Override
	public boolean mobileNumberAlrdyExitOrNot(ProfileModel profileModel) throws Exception {
		ForgotPasswordDomain forgotPasswordDomainMobileNumber = forgotPasswordDAO
				.getProfile(profileModel.getMobileNumber());
		if (forgotPasswordDomainMobileNumber == null) {
			return true;
		} else {
			throw new USER_ALREADY_EXIST();
		}
	}

	@Override
	public String deleteDriver(String mobileNumber) throws Exception {
		ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO.getProfile(mobileNumber);
		ProfileDomain profile = new ProfileDomain();
		profile.setId(forgotPasswordDomain.getId());

		/*Map<String, Object> deletehttpResponse = GenericHttpClient.doHttpDelete(
				mmgProperties.getTieupseUrl() + "v1" + "/delete/drivers",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.TIEUPS.name());
		int deletestatusCode = (int) deletehttpResponse.get("statusCode");
		JSONObject deletejsonResponse = (JSONObject) deletehttpResponse.get("response");
		if (deletejsonResponse != null) {
			if (deletestatusCode != HttpStatus.OK.value()) {
				throw new DELETE_FAILED("Delete Failed");
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		} */
		return profileDAO.deleteDriver(mobileNumber);
	}

	@Override
	public List<ProfileModel> getAllProfileCustomer() throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.getAllProfileCustomer();
		return profileMapper.entityList(profileDomain);
	}

	@Override
	public ProfileModel getProfileFreqeuntCustomer(String id) throws Exception {
		ProfileDomain profileDomain = profileDAO.getProfileFreqeuntCustomer(id);
		if (profileDomain == null)
			throw new PROFILE_NOT_FOUND();
		logger.info("service====>" + profileDomain);
		BigInteger mydefault = BigInteger.ZERO;
		if (profileDomain.getAlternativeNumber() == null)
			profileDomain.setAlternativeNumber(mydefault);
		if (profileDomain.getCityId() == 0)
			profileDomain.setCityId(0);
		if (profileDomain.getStateId() == 0)
			profileDomain.setStateId(0);
		if (profileDomain.getCountryId() == 0)
			profileDomain.setCountryId(0);
		return profileMapper.entity(profileDomain);

	}

	/*
	 * Author:Sindhu Creationdate: 16-11-2019 Description: bdmlist based on city
	 */
	@Override
	public List<ProfileModel> getbdmProfileListonCity(int cityId) throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.profileDetailsByCity(Role.BDM.getCode(), cityId);
		return profileMapper.entityList(profileDomain);
	}

	/*
	 * vidya Creationdate: 26-11-2019 Description: bdolist based on city
	 */
	@Override
	public List<ProfileModel> getbdoProfileListonCity(int cityId) throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.profileDetailsByCity(Role.BDO.getCode(), cityId);
		return profileMapper.entityList(profileDomain);
	}

	/*
	 * Author:Sindhu Creationdate: 29-11-2019 Description: To get active bdm
	 */
	@Override
	public List<ProfileModel> getActiveBdms() throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.activeBDMsOrBDOs(Role.BDM.getCode());
		return profileMapper.entityList(profileDomain);
	}

	/*
	 * Author:Sindhu Creationdate: 29-11-2019 Description: To get active enrolledbdm
	 */
	@Override
	public List<ProfileModel> getEnrolledActiveBdms() throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.enrolledActiveBdmsOrBDOs(Role.BDM.getCode());
		return profileMapper.entityList(profileDomain);
	}

	/*
	 * Author:Sindhu Creationdate: 29-11-2019 Description: To get active bdo
	 */
	@Override
	public List<ProfileModel> getActiveBdos() throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.activeBDMsOrBDOs(Role.BDO.getCode());
		return profileMapper.entityList(profileDomain);
	}

	/*
	 * Author:Sindhu Creationdate: 29-11-2019 Description: To get active enrolledbdo
	 */
	@Override
	public List<ProfileModel> getEnrolledActiveBdos() throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.enrolledActiveBdmsOrBDOs(Role.BDO.getCode());
		return profileMapper.entityList(profileDomain);
	}

	@Override
	public String updateCrmEnrolled(ProfileModel profileModel) throws Exception {
		validateProfileIdByToken(profileModel.getId());
		ProfileDomain profileDomain = new ProfileDomain();
		BeanUtils.copyProperties(profileModel, profileDomain);
		return profileDAO.updateCrmEnrolled(profileDomain);
	}

	/*
	 * Author:Vidya Creationdate: 2-12-2019 Description: To update Operational Team
	 * profile Lis based on city
	 */
	@Override
	public List<ProfileModel> getOPProfileListonCity(int cityId) throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.profileDetailsByCity(Role.OPERATIONAL_TEAM.getCode(), cityId);
		return profileMapper.entityList(profileDomain);
	}

	private ProfileDomain setDefaultvalues(ProfileDomain profileDomain) throws Exception {
		if (profileDomain.getFirstName() == null || profileDomain.getFirstName().isEmpty())
			profileDomain.setFirstName(" ");
		if (profileDomain.getLastName() == null || profileDomain.getLastName().isEmpty()) {
			profileDomain.setLastName(" ");
		}
		if (profileDomain.getDoorNumber() == null || profileDomain.getDoorNumber().isEmpty()) {
			profileDomain.setDoorNumber(" ");
		}
		if (profileDomain.getMobileNumber() == null || profileDomain.getMobileNumber().equals(0)) {
			profileDomain.setMobileNumber("1234567890");
		}
		if (profileDomain.getStreet() == null || profileDomain.getStreet().isEmpty()) {
			profileDomain.setStreet(" ");
		}
		if (profileDomain.getEmailId() == null || profileDomain.getEmailId().isEmpty()) {
			profileDomain.setEmailId(" ");
		}
		if (profileDomain.getPincode() == null || profileDomain.getPincode().equals(0)) {
			BigInteger pincode = BigInteger.valueOf(123456);
			profileDomain.setPincode(pincode);
		}
		if (profileDomain.getIsActive() == true)
			profileDomain.setAction("Active");
		else
			profileDomain.setAction("Inactive");
		if (profileDomain.getStateId() == 0)
			profileDomain.setStateName(" ");
		else {
			Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getAdminUrl() + "v1" + "/state/" + profileDomain.getStateId(),
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.OWNER.name());
			int statusCode = (int) httpResponse.get("statusCode");
			JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
			if (jsonResponse != null) {
				if (statusCode != HttpStatus.OK.value()) {
					throw new NOT_FOUND(jsonResponse.getString("message"));
				} else {
					JSONObject data = (JSONObject) jsonResponse.get("data");
					String stateName = data.getString("name");
					profileDomain.setStateName(stateName);
				}
			} else {
				throw new BACKEND_SERVER_ERROR();
			}
		}
		if (profileDomain.getCityId() == 0)
			profileDomain.setCityName(" ");
		else {
			Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getAdminUrl() + "v1" + "/city/id/" + profileDomain.getCityId(),
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.OWNER.name());
			int statusCode = (int) httpResponse.get("statusCode");
			JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
			if (jsonResponse != null) {
				if (statusCode != HttpStatus.OK.value()) {
					throw new NOT_FOUND(jsonResponse.getString("message"));
				} else {
					JSONObject data = (JSONObject) jsonResponse.get("data");
					String cityName = data.getString("name");
					profileDomain.setCityName(cityName);
				}
			} else {
				throw new BACKEND_SERVER_ERROR();
			}
		}
		return profileDomain;
	}

	/*
	 * Author:Sindhu Creationdate: 24-2-2020 Description: CustomerCSVDetails
	 */
	@Override
	public List<ProfileModel> getCustomerCSVDetails() throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.customerActiveOrInActive(true);
		for (ProfileDomain profile : profileDomain) {
			setDefaultvalues(profile);
		}
		return profileMapper.entityList(profileDomain);
	}

	/*
	 * Author:Jayaram Creationdate: 01/01/2020 Description: Driver mobile number
	 * based on city
	 */

	@Override
	public List<ProfileModel> getDriverNumber(int cityId) throws Exception {
		List<ProfileDomain> profileDomain = profileDAO.profileDetailsByCity(Role.DRIVER.getCode(), cityId);
		return profileMapper.entityList(profileDomain);
	}

}
