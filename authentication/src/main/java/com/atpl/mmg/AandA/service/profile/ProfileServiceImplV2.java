package com.atpl.mmg.AandA.service.profile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.constant.AddressType;
import com.atpl.mmg.AandA.constant.AuditValues;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.constant.EnquiryRole;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.DBUpdateDAO;
import com.atpl.mmg.AandA.dao.audit.AuditDAO;
import com.atpl.mmg.AandA.dao.dashboard.DashboardDAO;
import com.atpl.mmg.AandA.dao.forgotPassword.ForgotPasswordDAO;
import com.atpl.mmg.AandA.dao.franchise.FranchiseDAO;
import com.atpl.mmg.AandA.dao.otp.OtpDAO;
import com.atpl.mmg.AandA.dao.profile.AddressDAO;
import com.atpl.mmg.AandA.dao.profile.BdmDAO;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.dao.profilerole.ProfileRoleDAO;
import com.atpl.mmg.AandA.dao.role.RoleDAO;
import com.atpl.mmg.AandA.dao.session.SessionDAO;
import com.atpl.mmg.AandA.domain.Audit;
import com.atpl.mmg.AandA.domain.DBUpdate;
import com.atpl.mmg.AandA.domain.bankaccount.BankAccountDomain;
import com.atpl.mmg.AandA.domain.dashboard.DashboardDomain;
import com.atpl.mmg.AandA.domain.otp.Otp;
import com.atpl.mmg.AandA.domain.profile.AddressDomain;
import com.atpl.mmg.AandA.domain.profile.BDMDomain;
import com.atpl.mmg.AandA.domain.profile.DriverDomain;
import com.atpl.mmg.AandA.domain.profile.FranchiseDomainV2;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.domain.profilerole.ProfileRoleDomain;
import com.atpl.mmg.AandA.domain.role.RoleDomain;
import com.atpl.mmg.AandA.domain.session.SessionDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.EMAILID_PATTERN_NOT_MATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.INVALID_OTP;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.OLD_PASSWORD_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.OLD_PASSWORD__MISMATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.OTP_EXPIRED;
import com.atpl.mmg.AandA.exception.MmgRestException.PASSWORD_MISMATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.PASSWORD_PATTERN_NOT_MATCHED;
import com.atpl.mmg.AandA.exception.MmgRestException.PHONENUMBER_PATTERN_NOT_MATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.PROFILE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.USER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.USER_NOT_EXISTS;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.mapper.bankaccount.BankAccountMapper;
import com.atpl.mmg.AandA.mapper.profile.AddressMapper;
import com.atpl.mmg.AandA.mapper.profile.ProfileMapperV2;
import com.atpl.mmg.AandA.model.auth.AuthModel;
import com.atpl.mmg.AandA.model.bankaccount.BankAccountModel;
import com.atpl.mmg.AandA.model.profile.Address;
import com.atpl.mmg.AandA.model.profile.BDO;
import com.atpl.mmg.AandA.model.profile.CoordinatorDetail;
import com.atpl.mmg.AandA.model.profile.Driver;
import com.atpl.mmg.AandA.model.profile.Franchise;
import com.atpl.mmg.AandA.model.profile.ListDto;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.model.profile.ProfileEdit;
import com.atpl.mmg.AandA.model.profile.RouteTag;
import com.atpl.mmg.AandA.model.profilerole.ProfileRole;
import com.atpl.mmg.AandA.model.reason.ReasonModel;
import com.atpl.mmg.AandA.service.DBUpdateService;
import com.atpl.mmg.AandA.service.dashboard.DashboardService;
import com.atpl.mmg.AandA.service.reason.ReasonService;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.DataValidation;
import com.atpl.mmg.AandA.utils.DateUtility;
import com.atpl.mmg.AandA.utils.EmailValidator;

@Service("ProfileServiceV2")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProfileServiceImplV2 implements ProfileServiceV2, Constants {

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Autowired
	EmailValidator emailValidator;

	@Autowired
	OtpDAO otpDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	ForgotPasswordDAO forgotPasswordDAO;

	@Autowired
	ProfileRoleDAO profileRoleDAO;

	@Autowired
	ProfileUtil profileUtil;

	@Autowired
	ProfileMapperV2 profileMapperV2;

	@Autowired
	DBUpdateService dbUpdateService;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	DataValidation dataValidation;

	@Autowired
	ProfileCommonService profileCommonService;

	@Autowired
	ProfileCommonService companyProfileService;

	@Autowired
	ProfileCommonService driverService;

	@Autowired
	AuthFranchiseCommonService franchiseCommonService;

	@Autowired
	ReasonService reasonService;

	@Autowired
	DBUpdateDAO dbUpdateDAO;

	@Autowired
	AddressMapper addressMapper;

	@Autowired
	BankAccountMapper bankAccountMapper;

	@Autowired
	BdmDAO bdmDAO;

	@Autowired
	FranchiseDAO franchiseDAO;

	@Autowired
	DashboardService dashboardService;

	@Autowired
	DashboardDAO dashboardDAO;

	@Autowired
	AddressDAO addressDAO;

	@Autowired
	SessionDAO sessionDAO;

	@Autowired
	AuditDAO auditDAO;

	@Override
	public Profile save(Profile profile, String profileSecurityHeader) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Profile Request: " + JsonUtil.toJsonString(profile)));
		ProfileDomainV2 existingProfileDomain = null;
		boolean isCommonProfile = true;
		if (!profile.isIsMigration()) {
			if (!profile.isIsRegisterByMmg())
				existingProfileDomain = profileUtil.validateUserExistsByMobileOrEmail(profile);
		}
		if (null == existingProfileDomain) {
			existingProfileDomain = new ProfileDomainV2();
			isCommonProfile = false;
		}
		profileUtil.validateCommonProfile(profile, profileSecurityHeader, isCommonProfile);
		Role role = Role.getRole(profile.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");
		switch (role) {
		case CUSTOMER:
		case OWNER:
		case TIEUPS:
		case BDM:
		case BDO:
		case CHANNEL_PARTNER:
		case COORDINATOR:
		case FIELDOFFICER:
		case OPERATIONAL_TEAM:
			profile = profileCommonService.save(profile, existingProfileDomain);
			break;
		case FRANCHISE:
		case FLEET_OPERATOR:
		case ENTERPRISE:
		case WAREHOUSE:
			profile = companyProfileService.save(profile, existingProfileDomain);
			break;
		case DRIVER:
			profile = driverService.save(profile, existingProfileDomain);
			break;
		default:
			break;
		}

		return profile;
	}

	@Override
	public String sendOtp(Profile profile, Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Otp Request: " + JsonUtil.toJsonString(profile)));
		boolean isCheck = false;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("isCheck"))
				isCheck = Boolean.valueOf(reqParam.get("isCheck"));
		}
		if (CommonUtils.isNullCheck(profile.getMobileNumber()))
			throw new NOT_FOUND("Please enter mobileNumber");

		Role role = Role.getRole(profile.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");
		switch (role) {
		case CUSTOMER:
		case DRIVER:
		case FRANCHISE:
		case FLEET_OPERATOR:
		case WAREHOUSE:
			// case OWNER:
			// case TIEUPS:
		case BDM:
		case BDO:
		case ENTERPRISE:
		case CHANNEL_PARTNER:
			if (!isCheck) {
				profileUtil.validateUserExistsByMobileOrEmail(profile);
			}
			return sendOtpOrEmailToCustomer(profile);
		default:
			break;
		}
		return null;
	}

	private String sendOtpOrEmailToCustomer(Profile profile) throws Exception {
		String otp = emailValidator.generateOTP();
		if (null != profile) {

			Otp otpObj = new Otp();
			if (!CommonUtils.isNullCheck(profile.getMobileNumber())) {
				otpObj.setMobileNumber(profile.getMobileNumber());
			}
			if (!CommonUtils.isNullCheck(profile.getEmailId())) {
				otpObj.setEmailId(profile.getEmailId());
			}
			otpObj.setOtp(otp);
			otpDAO.save(otpObj);
			if (!CommonUtils.isNullCheck(profile.getMobileNumber())) {
				/**
				 * Send sms
				 */
				otp = emailValidator.sendSMSMessage(profile.getMobileNumber().toString(), otp,
						Constants.CUSTOMER_OTP_MESSAGE, true);
			}
			if (null != profile.getEmailId() && profile.getEmailId().trim().length() > 0) {
				/**
				 * Send Email
				 */
				Map<String, Object> variables = new HashMap<>();
				variables.put("customerOtp", otp);
				String body = emailValidator.generateMailHtml("customer", variables);
				emailValidator.sendEmail("Your Otp", profile.getEmailId(), body);
			}
		}

//		Otp otpObj = new Otp();
//		if (!CommonUtils.isNullCheck(profile.getMobileNumber())) {
//			otpObj.setMobileNumber(profile.getMobileNumber());
//		}
//		if (!CommonUtils.isNullCheck(profile.getEmailId())) {
//			otpObj.setEmailId(profile.getEmailId());
//		}
//		otpObj.setOtp(otp);
//		otpDAO.save(otpObj);
		if (mmgProperties.getOtpVisible().equalsIgnoreCase("Yes"))
			return otp;
		return "Otp sent successfully";
	}

	@Override
//	public Profile validateOtp(String mobileNumber, String otp) throws Exception {
	public Profile validateOtp(Otp otp, boolean profileEdit) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"Otp Request: MobileNumber" + JsonUtil.toJsonString(otp) + " Otp " + JsonUtil.toJsonString(otp)));
		if (!profileEdit) {
			if (CommonUtils.isNullCheck(otp.getMobileNumber()))
				throw new NOT_FOUND("Please enter mobile number");
		}
		if (CommonUtils.isNullCheck(otp.getOtp()))
			throw new NOT_FOUND("Please enter OTP");

		Profile profile = new Profile();
		Otp otpObj = new Otp();
		if (!CommonUtils.isNullCheck(otp.getMobileNumber()))
			otpObj = otpDAO.getOtp(otp.getMobileNumber(), null);
		if (!CommonUtils.isNullCheck(otp.getEmailId()))
			otpObj = otpDAO.getOtp(null, otp.getEmailId());
		if (null != otpObj) {
			if (otpObj.getOtp().equalsIgnoreCase(otp.getOtp())) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(otpObj.getCreatedDate());
				cal.add(Calendar.MINUTE, mmgProperties.getOtpExpiryInMinutes());
				Date expiryTime = cal.getTime();
				Date date = DateUtility.setTimeZone(new Date());
				if (expiryTime.compareTo(date) < 0)
					throw new OTP_EXPIRED();
				profile.setOtpIsActive(true);
				otpDAO.updateIsChecked(otpObj.getId());
			} else
				throw new INVALID_OTP();
		} else
			throw new INVALID_OTP();
		return profile;
	}

	@Override
	public String update(Profile profile, String profileSecurityHeader) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Profile Request: " + JsonUtil.toJsonString(profile)));
		ProfileDomainV2 profileDomainV2 = new ProfileDomainV2();
		Role role = Role.getRole(profile.getRoleId() + "");
		boolean isPersonal = false;
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");
		if (profile.getRoleId() != Integer.parseInt(Role.CUSTOMER.getCode()))
			isPersonal = profileUtil.checkPersonalData(profile);
		profileUtil.validateProfileId(profile.getId());
		Map<String, String> reqParam = new HashMap<>();
		reqParam.put("roleId", String.valueOf(profile.getRoleId()));
		Profile profileObject = getProfileDetails(profile.getId(), reqParam);
		profileUtil.copyProfileForUpdate(profile, profileObject);

		profileUtil.validateUpdateCommonProfile(profileObject, profileSecurityHeader, isPersonal);
		profileUtil.validateProfile(profileObject);
		BeanUtils.copyProperties(profileObject, profileDomainV2);

		if (profile.getRoleId() == Integer.parseInt(Role.CUSTOMER.getCode())) {
			profileDAOV2.updateProfile(profileDomainV2, profile.getRoleId());
			profileUtil.updateAddress(profile);
		}
		if (isPersonal)
			profileDAOV2.updateProfile(profileDomainV2, profile.getRoleId());
		switch (role) {
		case CUSTOMER:
		case OWNER:
		case TIEUPS:
		case BDM:
		case BDO:
		case CHANNEL_PARTNER:
		case COORDINATOR:
		case FIELDOFFICER:
		case OPERATIONAL_TEAM:
			profileCommonService.update(profileObject);
			break;
		case FRANCHISE:
		case FLEET_OPERATOR:
		case ENTERPRISE:
		case WAREHOUSE:
			companyProfileService.update(profileObject);
			break;
		case DRIVER:
			driverService.update(profileObject);
			break;
		default:
			break;
		}
		if (profile.getRoleId() != Integer.parseInt(Role.CUSTOMER.getCode()))
			profileUtil.saveReason(profile.getModifiedBy(), profile.getReason(), profile.getModifiedByRoleId(),
					profile.getId(), profile.getRoleId());
		return "Updated Successfully";
	}

	@Override
	public Profile getProfileDetails(String id, Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getProfileDetails Request: " + JsonUtil.toJsonString(id)));
		int roleId = 0;
		boolean isAddressAndBankDetail = true;
		ProfileDomainV2 profileDomainv2 = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("roleId"))
				roleId = Integer.parseInt(reqParam.get("roleId"));
			if (reqParam.containsKey("isAddressAndBankDetail"))
				isAddressAndBankDetail = Boolean.valueOf("isAddressAndBankDetail");
		}
		Profile profile = new Profile();
		if (roleId != 0)
			profileDomainv2 = profileDAOV2.getProfileByIdAndRole(id, roleId);
		else
			profileDomainv2 = profileDAOV2.getProfileDetails(id);
		if (null == profileDomainv2)
			throw new PROFILE_NOT_FOUND();
		BeanUtils.copyProperties(profileDomainv2, profile);
		if (isAddressAndBankDetail) {
			List<BankAccountDomain> bankAccountDomainList = profileUtil.getBankAccountDetails(id);
			if (bankAccountDomainList.size() > 0) {
				List<BankAccountModel> bankAccountModelList = bankAccountMapper.entityList(bankAccountDomainList);
				profile.setBankAccount(bankAccountModelList);
			}
//		List<AddressDomain> addressDomainList = profileUtil.getAddressDetails(id);
			List<AddressDomain> addressDomainList = profileUtil.getAddressDetails(id, roleId, false);
			if (addressDomainList.size() > 0) {
				List<Address> addressList = new ArrayList<>();
				addressList = addressMapper.entityList(addressDomainList);
				profile.setAddress(addressList);
			}
			profileUtil.getProfileRoleDetails(roleId, profile);
		}
		return profile;
	}

	@Override
	public Profile getProfileDetByMobileNo(Map<String, String> reqParam, boolean isRequired) throws Exception {
		String mobileOrEmail = null;
		int roleId = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("mobileNumber")) {
				mobileOrEmail = reqParam.get("mobileNumber");
				if (!isRequired) {
					if (!dataValidation.isValidate(String.valueOf(mobileOrEmail), DataValidation.PHONENUMBER_PATTERN))
						throw new PHONENUMBER_PATTERN_NOT_MATCH();
				}
			} else if (reqParam.containsKey("email")) {
				mobileOrEmail = reqParam.get("email");
				if (!dataValidation.isValidate(mobileOrEmail, DataValidation.EMAIL_PATTERN))
					throw new EMAILID_PATTERN_NOT_MATCH();
			}
			if (reqParam.containsKey("roleId")) {
				String role = reqParam.get("roleId");
				roleId = Integer.parseInt(role);
			}

		} else
			throw new NOT_FOUND("Please enter mobilenumber or email id");

		if (CommonUtils.isNullCheck(mobileOrEmail))
			throw new NOT_FOUND("Please enter mobilenumber or email id");

		Profile profile = null;
		ProfileDomainV2 profileDomainv2 = profileDAOV2.getProfileDetByMobileOrEmail(mobileOrEmail, roleId);
		if (profileDomainv2 == null) {
			throw new PROFILE_NOT_FOUND();
		} else {
			List<BankAccountDomain> bankAccountDomainList = profileUtil.getBankAccountDetails(profileDomainv2.getId());
			if (bankAccountDomainList.size() > 0)
				profileDomainv2.setBankAccount(bankAccountDomainList);
//			List<AddressDomain> addressDomainList = profileUtil.getAddressDetails(profileDomainv2.getId());
			List<AddressDomain> addressDomainList = profileUtil.getAddressDetails(profileDomainv2.getId(), roleId,
					false);
			if (addressDomainList.size() > 0)
				profileDomainv2.setAddress(addressDomainList);
			profile = new Profile();
			BeanUtils.copyProperties(profileDomainv2, profile);
		}
		return profile;
	}

	@Override
	public String updateToken(ProfileRole profileRole) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Update Request: " + JsonUtil.toJsonString(profileRole)));
		if (null == mmgProperties.getxApiSource())
			throw new NOT_FOUND("Please mention X-API-SOURCE header");

		if (null == profileRole.getProfileId())
			throw new NOT_FOUND("ProfileId cannot be empty");

		Role role = Role.getRole(profileRole.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profileRole.getRoleId() + "");

		ProfileRoleDomain profileRoleDomain = profileRoleDAO.getRoleDet(profileRole.getProfileId(),
				profileRole.getRoleId());
		if (null == profileRoleDomain)
			throw new NOT_FOUND("Profile Role details not found");

		if (mmgProperties.getxApiSource().equalsIgnoreCase(Constants.PROFILE_SOURCE)) {
			if (null == profileRole.getAppTokenId())
				throw new NOT_FOUND("Please mention appTokenId");
			profileRoleDomain.setAppTokenId(profileRole.getAppTokenId());
		} else {
			if (null == profileRole.getWebTokenId())
				throw new NOT_FOUND("Please mention webTokenId");
			profileRoleDomain.setWebTokenId(profileRole.getWebTokenId());
		}

		return profileDAOV2.updateToken(profileRoleDomain, mmgProperties.getxApiSource());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String profileActivateOrDeactivate(ReasonModel reasonModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"profileActivateOrDeactivate Request: " + JsonUtil.toJsonString(reasonModel)));
		Role role = Role.getRole(reasonModel.getRoleId() + "");
		boolean changedStatus = false;
		if (null == role)
			throw new ROLE_NOT_FOUND(reasonModel.getRoleId() + "");
		if (reasonModel.getStatus().equals(EnquiryRole.ACTIVE.name()))
			changedStatus = true;
		if (reasonModel.getStatus().equalsIgnoreCase(EnquiryRole.ACTIVE.name())
				|| reasonModel.getStatus().equalsIgnoreCase(EnquiryRole.INACTIVE.name())) {
			SessionDomain sessionDomainV2 = sessionDAO.getSessionByProfileIdAndRoleId(reasonModel.getProfileId(), true,
					reasonModel.getRoleId());

			if (reasonModel.getStatus().equalsIgnoreCase(EnquiryRole.INACTIVE.name())) {
				if (null != sessionDomainV2)
					if (sessionDomainV2.isIsActive()) {
						throw new NOT_FOUND("Please logout the profileId :" + reasonModel.getProfileId());
					}
			}
			ProfileDomainV2 profileDomainV2 = profileDAOV2.getProfileByIdAndRole(reasonModel.getProfileId(),
					reasonModel.getRoleId());
			if (null != profileDomainV2)
				if (!profileDomainV2.getIsActive().equals(changedStatus)) {
					if (profileDomainV2.getIsActive())
						reasonModel.setPreviousStatus(EnquiryRole.ACTIVE.name());
					else
						reasonModel.setPreviousStatus(EnquiryRole.INACTIVE.name());
					updateStatus(reasonModel, changedStatus);
				}
		} else
			updateStatus(reasonModel, changedStatus);
		return "Updated successfully";
	}

	private String updateStatus(ReasonModel reasonModel, boolean changedStatus) throws Exception {
		if (reasonModel.getRoleId() == Integer.parseInt(Role.DRIVER.getCode())) {
			if (changedStatus) {
				/**
				 * Date: 06/14/2021 Disable driver profile image mandatory
				 * franchiseCommonService.getDriverImagesByDriverId(reasonModel.getProfileId());
				 */
			}
			Driver driver = franchiseCommonService.getDriverDetails(reasonModel.getProfileId());
			if (null != driver) {
				reasonModel.setPreviousStatus(driver.getStatus());
			}
			// Update method
			DBUpdate dbUpdate = new DBUpdate();
			dbUpdate.setTableName("drivers");

			Map<String, Object> expression = new HashMap<String, Object>();
			expression.put("status", reasonModel.getStatus());
			dbUpdate.setExpression(expression);

			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("driverId", driver.getDriverId());
			dbUpdate.setConditions(conditions);
			franchiseCommonService.dbUpdate(dbUpdate);
		}
		if (reasonModel.getRoleId() == Integer.parseInt(Role.FRANCHISE.getCode())) {
			if (reasonModel.getStatus().equals(EnquiryRole.ACTIVE.name()))
				profileUtil.checkFareDistributionCreatedOrNot(reasonModel.getProfileId());
		}
		profileDAOV2.profileActivateOrDeactivate(changedStatus, reasonModel.getProfileId(), reasonModel.getRoleId());
		return reasonService.save(reasonModel, true);
	}

	@Override
	public ListDto getActiveOrInactiveProfiles(Integer roleId, boolean status, Map<String, String> reqParam)
			throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getActiveOrInactiveProfiles Request: " + JsonUtil.toJsonString(roleId)));
		String isCallingFrmFranchise = null, requestedBy = null;
		int cityId = 0, stateId = 0, lowerBound = 0, upperBound = 0, customerTypeId = 0, totalSize = 0;

		if (reqParam.size() > 0) {
			if (reqParam.containsKey("requestedBy"))
				requestedBy = reqParam.get("requestedBy");
			if (reqParam.containsKey("isCallingFrmFranchise"))
				isCallingFrmFranchise = reqParam.get("isCallingFrmFranchise");
			if (reqParam.containsKey("cityId"))
				cityId = Integer.parseInt(reqParam.get("cityId"));
			if (reqParam.containsKey("stateId"))
				stateId = Integer.parseInt(reqParam.get("stateId"));
			if (reqParam.containsKey("customerTypeId"))
				customerTypeId = Integer.parseInt(reqParam.get("customerTypeId"));

			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}

		List<Profile> profileList = new ArrayList<Profile>();
		profileList = getActiveOrInactiveProfilesDetails(roleId, status, reqParam);
		if (cityId > 0)
			totalSize = dashboardService.getProfileCountByRoleAndSts(roleId, status, cityId, 0, customerTypeId);
		else if (stateId > 0)
			totalSize = dashboardService.getProfileCountByRoleAndSts(roleId, status, 0, stateId, customerTypeId);
		else
			totalSize = dashboardService.getProfileCountByRoleAndSts(roleId, status, 0, 0, customerTypeId);
		ListDto listDto = profileUtil.getListWithPaginationCount(profileList, totalSize);
		return listDto;
	}

	@Override
	public List<Profile> usersSessionActiveOrInActiveListByCityId(boolean isActive, boolean status, Integer roleId,
			Map<String, String> reqParam) throws Exception {
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(roleId + "");
		int cityId = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("cityId")) {
				cityId = Integer.parseInt(reqParam.get("cityId"));
			}
		}

		String addressType = profileUtil.getAddressType(role);

		List<ProfileDomainV2> profileDomain = new ArrayList<ProfileDomainV2>();
		profileDomain = profileDAOV2.usersSessionActiveOrInActiveListByCityId(isActive, cityId, status, roleId,
				addressType);

		return profileMapperV2.entityList(profileDomain);
	}

	@Override
	public ListDto getProfileDetailsByRoleId(Integer roleId, Map<String, String> reqParam) throws Exception {
		String requestedBy = null; // This is applicable only for normal drivers and onboard drivers
		String isCallingFrmFranchise = null, searchText = null;
		int cityId = 0, stateId = 0, lowerBound = 0, upperBound = 0, totalSize = 0;
		Boolean isTag = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("searchText")) {
				searchText = reqParam.get("searchText");
			}
			if (reqParam.containsKey("requestedBy"))
				requestedBy = reqParam.get("requestedBy");
			if (reqParam.containsKey("isCallingFrmFranchise"))
				isCallingFrmFranchise = reqParam.get("isCallingFrmFranchise");
			if (reqParam.containsKey("cityId"))
				cityId = Integer.parseInt(reqParam.get("cityId"));
			if (reqParam.containsKey("stateId"))
				stateId = Integer.parseInt(reqParam.get("stateId"));
			if (reqParam.containsKey("isTag")) {
				isTag = Boolean.valueOf(reqParam.get("isTag"));
			}
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}

		List<Profile> profileList = new ArrayList<Profile>();
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(roleId + "");

		List<ProfileDomainV2> profileDomainList = null;
		String addressType = profileUtil.getAddressType(role);

		if (cityId > 0)
			profileDomainList = profileDAOV2.getProfilesByCity(cityId, roleId, addressType, isTag, searchText,
					lowerBound, upperBound);
		else if (stateId > 0)
			profileDomainList = profileDAOV2.getProfilesByState(stateId, roleId, addressType, isTag, searchText,
					lowerBound, upperBound);
		else
			profileDomainList = profileDAOV2.getProfileDetailsByRoleId(roleId, isTag, searchText, lowerBound,
					upperBound);

		profileList = profileMapperV2.entityList(profileDomainList);
		for (Profile profile : profileList) {
			profile.setAddress(profileUtil.getAddress(addressType, profile.getId(), roleId));
		}
		if (null == isCallingFrmFranchise) {
			if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
				if (profileList.size() > 0) {
					profileUtil.getDriverList(profileList, requestedBy);
				}
			}
		}
		if (cityId > 0)
			totalSize = dashboardService.getProfileCountByRole(roleId, cityId, 0, isTag);
		else if (stateId > 0)
			totalSize = dashboardService.getProfileCountByRole(roleId, 0, stateId, isTag);
		else
			totalSize = dashboardService.getProfileCountByRole(roleId, 0, 0, isTag);
		ListDto listDto = profileUtil.getListWithPaginationCount(profileList, totalSize);
		return listDto;
	}

	@Override
	public String forgotPassword(String mobileNo) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "forgotPassword Request: " + JsonUtil.toJsonString(mobileNo)));
		ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetByMobileOrEmail(mobileNo, 0);
		if (profileDomain == null) {
			throw new USER_NOT_EXISTS();
		}
		Profile profile = new Profile();
		BeanUtils.copyProperties(profileDomain, profile);
		return sendOtpOrEmailToCustomer(profile);
	}

	@Override
	public String updatePassword(Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "updatePassword Request: " + JsonUtil.toJsonString(profile)));
		/** Password Validation */
		if (!dataValidation.isValidate(profile.getPassword(), DataValidation.PASSWORD_PATTERN))
			throw new PASSWORD_PATTERN_NOT_MATCHED();
		/** Password and ConfirmPassword Match */
		if (!profile.getPassword().equals(profile.getConfirmPassword()))
			throw new PASSWORD_MISMATCH();
		/** Password and ConfirmPassword Encryption */
		profile = encryptPwd(profile);

		if (profile.isIsChangePassword()) {
			profile = encryptOldPwd(profile);
			validateOldPassword(profile);
		} else {
			ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetByMobileOrEmail(profile.getMobileNumber(), 0);
			if (profileDomain == null) {
				throw new USER_NOT_EXISTS();
			}
			profileUtil.validateOtpChecked(profile, false);
		}
		ProfileDomainV2 passwordDomain = new ProfileDomainV2();
		BeanUtils.copyProperties(profile, passwordDomain);
		return profileDAOV2.updatePasswordById(passwordDomain);
	}

	private Profile encryptPwd(Profile profile) {
		profile.setPassword(CommonUtils.encriptString(profile.getPassword()));
		profile.setConfirmPassword(CommonUtils.encriptString(profile.getConfirmPassword()));
		return profile;
	}

	@Override
	public String sendConfirmPasswordEmail(Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"sendConfirmPasswordEmail Request: " + JsonUtil.toJsonString(profile)));
		if (null == profile.getEmailId() || profile.getEmailId().isEmpty())
			throw new NOT_FOUND("Please mention the emailId");
		if (null == profile.getPassword() || profile.getPassword().isEmpty())
			throw new NOT_FOUND("Please mention the password");
		/**
		 * Send Email
		 */
		RoleDomain role = roleDAO.getRoleName(profile.getRoleId());
		String roleName = role.getRoleName();
		Map<String, Object> variables = new HashMap<>();
		variables.put("role", roleName);
		variables.put("phno", profile.getMobileNumber());
		variables.put("email", profile.getEmailId());
		variables.put("password", profile.getPassword());
		String body = emailValidator.generateMailHtml("password", variables);
		emailValidator.sendEmail("Move My Goods", profile.getEmailId(), body);
		return "Mail sent";
	}

	@Override
	public ListDto getProfileByBdmId(int roleId, String bdmId, Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getProfileByBdmId Request: " + JsonUtil.toJsonString(roleId))
				+ " bdmId " + JsonUtil.toJsonString(bdmId));
		String status = null;
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("status"))
				status = reqParam.get("status");
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		BDMDomain bdmDomain = bdmDAO.getBDMByProfileId(bdmId);
		List<ProfileDomainV2> profileDomain = null;
		if (CommonUtils.isNullCheck(status))
			profileDomain = profileDAOV2.getProfileByBdmId(roleId, bdmDomain.getBdmId(), null, lowerBound, upperBound);
		else
			profileDomain = profileDAOV2.getProfileByRoleIdAndStsAndBdmId(Boolean.parseBoolean(status), roleId,
					bdmDomain.getBdmId(), null, lowerBound, upperBound);

		List<Profile> profile = profileMapperV2.entityList(profileDomain);

		if (roleId == Integer.parseInt(Role.BDO.getCode())) {

			for (Profile profileModel : profile) {
				if (null != profileModel.getBdo()) {
					BDO bdo = profileModel.getBdo();
					if (!CommonUtils.isNullCheck(bdo.getFranchiseId())) {
						FranchiseDomainV2 franchise = franchiseDAO.getFranchiseByFranchiseId(bdo.getFranchiseId());
						bdo.setFranchiseName(franchise.getCompanyName());
						profileModel.setBdo(bdo);
					}
				}
			}
		}
		ListDto listDto = new ListDto();
		DashboardDomain dashboardDomain = new DashboardDomain();
		if (CommonUtils.isNullCheck(status)) {
			dashboardDomain = dashboardDAO.getDashboardByBdmId(String.valueOf(roleId), bdmDomain.getBdmId(), false,
					false, null);
			totalSize = dashboardDomain.getTotal();
		} else {
			boolean isActive = Boolean.parseBoolean(status);
			dashboardDomain = dashboardDAO.getDashboardByBdmId(String.valueOf(roleId), bdmDomain.getBdmId(), isActive,
					true, null);
			totalSize = dashboardDomain.getTotal();
		}
		listDto = profileUtil.getListWithPaginationCount(profile, totalSize);
		return listDto;
	}

	@Override
	public ListDto getProfileByChannelPartnerId(int roleId, String cpId, Map<String, String> reqParam)
			throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getProfileByChannelPartnerId Request: "
						+ JsonUtil.toJsonString(roleId) + " channel partnerId " + JsonUtil.toJsonString(cpId)));
		List<Profile> profileList = new ArrayList<Profile>();
		String status = null;
		int lowerBound = 0;
		int upperBound = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("status"))
				status = reqParam.get("status");
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		List<ProfileDomainV2> profileDomain = profileDAOV2.getProfileByCpId(roleId, cpId, status, lowerBound,
				upperBound);
		profileList = profileMapperV2.entityList(profileDomain);
		ListDto listDto = new ListDto();
		boolean isStatus = Boolean.parseBoolean(status);
		if (status != null)
			listDto = profileUtil.getListWithPaginationCount(profileList,
					dashboardService.getProfileCountByChannelPartnerId(roleId, isStatus, cpId, true));
		else
			listDto = profileUtil.getListWithPaginationCount(profileList,
					dashboardService.getProfileCountByChannelPartnerId(roleId, isStatus, cpId, false));
		return listDto;
	}

	@Override
	public ListDto getProfileByRoleAndFranchiseId(int roleId, boolean status, String franchiseId, boolean isFlag,
			Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getProfileByRoleAndFranchiseId: "
						+ JsonUtil.toJsonString(roleId) + " franchiseId " + JsonUtil.toJsonString(franchiseId)));
		List<Profile> profileList = new ArrayList<Profile>();
		List<ProfileDomainV2> profileDomain = null, driverprofileDomainList = null;
		ListDto listDto = null;
		int totalSize = 0, lowerBound = 0, upperBound = 0;

		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		if (isFlag) {
			if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
				driverprofileDomainList = profileDAOV2.getProfileByRoleAndFranchiseId(roleId, status, franchiseId, null,
						0, 0);
				profileDomain = driverprofileDomainList;
			} else
				profileDomain = profileDAOV2.getProfileByRoleAndFranchiseId(roleId, status, franchiseId, null,
						lowerBound, upperBound);
		} else
			profileDomain = profileDAOV2.getProfileByRoleAndFranchiseId(roleId, franchiseId, lowerBound, upperBound);
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(roleId + "");
		String addressType = profileUtil.getAddressType(role);

		profileList = profileMapperV2.entityList(profileDomain);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getProfileByRoleAndFranchiseId:  getAddress for addressType" + JsonUtil.toJsonString(addressType)));

		for (Profile profile : profileList) {
			profile.setAddress(profileUtil.getAddress(addressType, profile.getId(), roleId));
		}
		if (driverprofileDomainList == null)
			totalSize = dashboardService.getProfilCounteByRoleAndFranchiseId(roleId, status, franchiseId, null);
		if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
			if (profileList.size() > 0) {
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"getProfileByRoleAndFranchiseId:  getDriverListByFranchiseId for profileList"
								+ JsonUtil.toJsonString(profileList) + "and franchiseId:"
								+ JsonUtil.toJsonString(franchiseId)));
				String statusFlag = null;
				if (status)
					statusFlag = "ACTIVE";
				else
					statusFlag = "INACTIVE";
				if (upperBound > 0 || upperBound == 0) {
					Map<String, String> reqParams = new HashMap<String, String>();
					Map<String, Driver> driverProfileMapWithPagination = profileUtil
							.getDriverMapsByFranchiseId(franchiseId, reqParam, statusFlag);
					if (driverprofileDomainList != null) {
						List<Profile> profileModelLists = new ArrayList<Profile>();
						Map<String, Driver> allDriverProfileMaps = profileUtil.getDriverMapsByFranchiseId(franchiseId,
								reqParams, statusFlag);
						profileModelLists = profileMapperV2.entityList(driverprofileDomainList);
						profileUtil.getDriverListByFranchiseId(profileModelLists, franchiseId, reqParams, statusFlag,
								allDriverProfileMaps);
						totalSize = profileModelLists.size();
					}
					profileUtil.getDriverListByFranchiseId(profileList, franchiseId, reqParams, statusFlag,
							driverProfileMapWithPagination);
					if (driverprofileDomainList == null)
						totalSize = profileList.size();
				}
			}
		}
		if (isFlag)
			listDto = profileUtil.getListWithPaginationCount(profileList, totalSize);
		else
			listDto = profileUtil.getListWithPaginationCount(profileList, 0);
		return listDto;
	}

	@Override
	public Profile getProfileDetailsByCompanyProfileId(@RequestParam Map<String, String> reqParam) throws Exception {
		String companyProfileId = null;
		int roleId = 0;
		DriverDomain driver = null;
		if (reqParam.size() == 0)
			throw new NOT_FOUND("Please enter company profile id");
		String key = null;
		for (Map.Entry<String, String> entry : reqParam.entrySet())
			key = entry.getKey();

		companyProfileId = reqParam.get(key);
		roleId = getRoleId(key);

		if (reqParam.containsKey("driverId")) {
			roleId = Integer.parseInt(Role.DRIVER.getCode());
			driver = franchiseCommonService.getDriverDetailsByDriverId(reqParam.get("driverId"));
			if (null != driver)
				companyProfileId = driver.getProfileId();
		}

		ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetailsByCompanyProfileId(roleId, companyProfileId);

		if (profileDomain == null)
			throw new PROFILE_NOT_FOUND();

		List<BankAccountDomain> bankAccountDomainList = profileUtil.getBankAccountDetails(profileDomain.getId());
		if (bankAccountDomainList.size() > 0)
			profileDomain.setBankAccount(bankAccountDomainList);
//		List<AddressDomain> addressDomainList = profileUtil.getAddressDetails(profileDomain.getId());
		List<AddressDomain> addressDomainList = profileUtil.getAddressDetails(profileDomain.getId(), roleId, true);
		if (addressDomainList.size() > 0)
			profileDomain.setAddress(addressDomainList);
		Profile profile = new Profile();
		BeanUtils.copyProperties(profileDomain, profile);

		profileUtil.getProfileRoleDetails(roleId, profile);
		return profile;
	}

	private int getRoleId(String key) {
		int roleId = 0;
		switch (key) {
		case "customerId":
			roleId = Integer.parseInt(Role.CUSTOMER.getCode());
			break;
		case "operationalTeamId":
			roleId = Integer.parseInt(Role.OPERATIONAL_TEAM.getCode());
			break;
		case "channelPartnerId":
			roleId = Integer.parseInt(Role.CHANNEL_PARTNER.getCode());
			break;
		case "bdmId":
			roleId = Integer.parseInt(Role.BDM.getCode());
			break;
		case "coordinatorId":
			roleId = Integer.parseInt(Role.COORDINATOR.getCode());
			break;
		case "fieldOfficerId":
			roleId = Integer.parseInt(Role.FIELDOFFICER.getCode());
			break;
		case "bdoId":
			roleId = Integer.parseInt(Role.BDO.getCode());
			break;
		case "wareHouseId":
			roleId = Integer.parseInt(Role.WAREHOUSE.getCode());
			break;
		case "franchiseId":
			roleId = Integer.parseInt(Role.FRANCHISE.getCode());
			break;
		case "fleetId":
			roleId = Integer.parseInt(Role.FLEET_OPERATOR.getCode());
			break;
		case "enterpriseId":
			roleId = Integer.parseInt(Role.ENTERPRISE.getCode());
			break;
		case "driverId":
			roleId = Integer.parseInt(Role.DRIVER.getCode());
			break;
		}
		return roleId;
	}

	@Override
	public ListDto getOTDetailsByOtStateId(int otAssignedStateId, @RequestParam Map<String, String> reqParam)
			throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		List<ProfileDomainV2> operationalTeamDomain = profileDAOV2.getProfilesByOtState(otAssignedStateId, true,
				lowerBound, upperBound);
		List<Profile> profileList = profileMapperV2.entityList(operationalTeamDomain);
		DashboardDomain dashboardDomain = new DashboardDomain();
		dashboardDomain = dashboardDAO.getProfilesCountByOtState(otAssignedStateId, true);
		ListDto listDto = profileUtil.getListWithPaginationCount(profileList, dashboardDomain.getTotal());
		return listDto;
	}
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	@Override
	public String saveRole(ProfileRole profileRole) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "saveRole Request: " + JsonUtil.toJsonString(profileRole)));
		if (profileRole.getRoleId() == 0)
			throw new NOT_FOUND("Please enter role id");
		if (null == profileRole.getProfileId())
			throw new NOT_FOUND("Please enter profile id");

		ProfileRoleDomain profileRoleDomain = profileRoleDAO.getRoleDet(profileRole.getProfileId(),
				profileRole.getRoleId());
		if (null != profileRoleDomain)
			throw new NOT_FOUND("Role is already assigned");

		profileRoleDomain = new ProfileRoleDomain();
		profileRoleDomain.setUuid(CommonUtils.generateRandomId());
		profileRoleDomain.setProfileId(profileRole.getProfileId());
		profileRoleDomain.setRoleId(profileRole.getRoleId());
		profileRoleDomain.setIsActive(true);
		profileRoleDAO.save(profileRoleDomain);

		return "Profile Role assigned successfully";
	}

	private Profile encryptOldPwd(Profile profile) {
		if (profile.getOldPassword() != null || !profile.getOldPassword().isEmpty())
			profile.setOldPassword(CommonUtils.encriptString(profile.getOldPassword()));
		else
			throw new OLD_PASSWORD_NOT_FOUND();
		return profile;
	}

	private void validateOldPassword(Profile profile) throws Exception {
		ProfileDomainV2 passwordDomain = profileDAOV2.getProfileDetails(profile.getId());
		if (!passwordDomain.getPassword().equals(profile.getOldPassword()))
			throw new OLD_PASSWORD__MISMATCH();
	}

	public byte[] downloadProfileList(boolean status, int roleId) throws Exception {
		List<ProfileDomainV2> profileDomainList = new ArrayList<ProfileDomainV2>();
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(roleId + "");
		switch (role) {
		case CUSTOMER:
		case FRANCHISE:
		case FLEET_OPERATOR:
		case WAREHOUSE:
		case DRIVER:
			profileDomainList = profileDAOV2.userActiveOrInActiveList(roleId, status, 0, 0, 0, 0, null, 0, null);
		default:
			break;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String[] columnshead = { "First Name", "lastName", "Mobile Number", "Email ID", "YOC", "Pan Number",
				"AadharNumber" };
		String sheetName = "Profile";
		XSSFWorkbook workbook = CommonUtils.createExcelWorkbook(columnshead, sheetName);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		Row row = null;
		Cell rowCell = null;
		int count = 1;

		for (ProfileDomainV2 profileDomain : profileDomainList) {
			row = sheet.createRow(count);
			for (int j = 0; j < columnshead.length; j++) {
				rowCell = row.createCell(j);
				switch (j + 1) {
				case 1:
					rowCell.setCellValue(profileDomain.getFirstName());
					break;
				case 2:
					rowCell.setCellValue(profileDomain.getLastName());
					break;
				case 3:
					rowCell.setCellValue(String.valueOf(profileDomain.getMobileNumber()));
					break;
				case 4:
					rowCell.setCellValue(profileDomain.getEmailId());
					break;
				case 5:
					rowCell.setCellValue(Double.parseDouble(profileDomain.getAadharNumber().toString()));
					break;
				case 6:
					rowCell.setCellValue(profileDomain.getPanNumber());
					break;
				case 7:
					rowCell.setCellValue(profileDomain.getPanNumber());
					break;
				}
			}
			count++;
		}
		workbook.write(out);
		workbook.close();
		return out.toByteArray();
	}

	@Override
	public ListDto profilesSearch(Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		String searchText = null, franchiseId = null, bdmId = null;
		int roleId = 0, stateId = 0, cityId = 0, totalSize = 0, customerTypeId = 0;
		boolean status = false;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
			if (reqParam.containsKey("searchText")) {
				searchText = reqParam.get("searchText");
			}
			if (reqParam.containsKey("roleId")) {
				String role = reqParam.get("roleId");
				roleId = Integer.parseInt(role);
			}
			if (reqParam.containsKey("status")) {
				String isStatus = reqParam.get("status");
				status = Boolean.parseBoolean(isStatus);
			}
			if (reqParam.containsKey("cityId")) {
				String city = reqParam.get("cityId");
				cityId = Integer.parseInt(city);
			}
			if (reqParam.containsKey("stateId")) {
				String state = reqParam.get("stateId");
				stateId = Integer.parseInt(state);
			}
			if (reqParam.containsKey("franchiseId")) {
				franchiseId = reqParam.get("franchiseId");
			}
			if (reqParam.containsKey("bdmId")) {
				bdmId = reqParam.get("bdmId");
			}
			if (reqParam.containsKey("customerTypeId")) {
				customerTypeId = Integer.valueOf(reqParam.get("customerTypeId"));
			}
		}
		List<Profile> profileList = new ArrayList<Profile>();
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(roleId + "");
		List<ProfileDomainV2> profileDomainList = new ArrayList<ProfileDomainV2>();
		List<ProfileDomainV2> profileDomainsList = null;
		String addressType = profileUtil.getAddressType(role);
		if (franchiseId != null) {
			if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
				if (upperBound > 0)
					profileDomainsList = profileDAOV2.getProfileByRoleAndFranchiseId(roleId, status, franchiseId,
							searchText, 0, 0);
				profileDomainList = profileDAOV2.getProfileByRoleAndFranchiseId(roleId, status, franchiseId, searchText,
						lowerBound, upperBound);
			} else
				profileDomainList = profileDAOV2.getProfileByRoleAndFranchiseId(roleId, status, franchiseId, searchText,
						lowerBound, upperBound);

			profileList = profileMapperV2.entityList(profileDomainList);
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(), "getProfileByRoleAndFranchiseId:  getAddress for addressType"
							+ JsonUtil.toJsonString(addressType)));

			for (Profile profile : profileList) {
				profile.setAddress(profileUtil.getAddress(addressType, profile.getId(), roleId));
			}
			if (profileDomainsList == null)
				totalSize = dashboardService.getProfilCounteByRoleAndFranchiseId(roleId, status, franchiseId,
						searchText);
			if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
				if (profileList.size() > 0) {
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(),
							"getProfileByRoleAndFranchiseId:  getDriverListByFranchiseId for profileList"
									+ JsonUtil.toJsonString(profileList) + "and franchiseId:"
									+ JsonUtil.toJsonString(franchiseId)));
					String statusFlag = null;
					if (status)
						statusFlag = "ACTIVE";
					else
						statusFlag = "INACTIVE";
					if (upperBound > 0 || upperBound == 0) {
						Map<String, String> reqParams = new HashMap<String, String>();
						List<Profile> profileLists = new ArrayList<Profile>();
						Map<String, Driver> driverProfileMap = profileUtil.getDriverMapsByFranchiseId(franchiseId,
								reqParams, statusFlag);
						if (profileDomainsList != null) {
							List<Profile> profileModelLists = new ArrayList<Profile>();
							profileModelLists = profileMapperV2.entityList(profileDomainsList);
							profileUtil.getDriverListByFranchiseId(profileModelLists, franchiseId, reqParams,
									statusFlag, driverProfileMap);
							totalSize = profileModelLists.size();
						}
						profileLists = profileMapperV2.entityList(profileDomainList);
						profileUtil.getDriverListByFranchiseId(profileLists, franchiseId, reqParams, statusFlag,
								driverProfileMap);
						if (profileDomainsList == null)
							totalSize = profileLists.size();
					}
				}
			}
			profileUtil.getAddressList(profileList);
		} else if (bdmId != null) {
			BDMDomain bdmDomain = bdmDAO.getBDMByProfileId(bdmId);
			if (status)
				profileDomainList = profileDAOV2.getProfileByRoleIdAndStsAndBdmId(status, roleId, bdmDomain.getBdmId(),
						null, lowerBound, upperBound);
			else
				profileDomainList = profileDAOV2.getProfileByBdmId(roleId, bdmDomain.getBdmId(), null, lowerBound,
						upperBound);
			profileList = profileMapperV2.entityList(profileDomainList);
			if (roleId == Integer.parseInt(Role.BDO.getCode())) {
				for (Profile profileModel : profileList) {
					if (null != profileModel.getBdo()) {
						BDO bdo = profileModel.getBdo();
						if (!CommonUtils.isNullCheck(bdo.getFranchiseId())) {
							FranchiseDomainV2 franchise = franchiseDAO.getFranchiseByFranchiseId(bdo.getFranchiseId());
							bdo.setFranchiseName(franchise.getCompanyName());
							profileModel.setBdo(bdo);
						}
					}
				}
			}
			DashboardDomain dashboardDomain = new DashboardDomain();
			if (status)
				dashboardDomain = dashboardDAO.getDashboardByBdmId(String.valueOf(roleId), bdmDomain.getBdmId(), status,
						true, null);
			else
				dashboardDomain = dashboardDAO.getDashboardByBdmId(String.valueOf(roleId), bdmDomain.getBdmId(), false,
						false, null);
			totalSize = dashboardDomain.getTotal();

		} else {
			if (roleId != 0)
				profileDomainList = profileDAOV2.userActiveOrInActiveList(roleId, status, lowerBound, upperBound,
						cityId, stateId, addressType, customerTypeId, searchText);
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					"userActiveOrInActiveList: " + JsonUtil.toJsonString(roleId)));

			profileList = profileMapperV2.entityList(profileDomainList);
			if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
				if (profileList.size() > 0) {
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(),
							"userActiveOrInActiveList: " + JsonUtil.toJsonString(roleId)));
					profileUtil.getDriverList(profileList, null);
				}
			}
			totalSize = dashboardService.getProfileSearchCountByRole(roleId, status, cityId, stateId, searchText,
					customerTypeId);
			profileUtil.getAddressList(profileList);
			if (roleId == Integer.parseInt(Role.CUSTOMER.getCode())) {
				profileUtil.getCustomerListWithType(profileList);
			}
		}
		ListDto listDto = profileUtil.getListWithPaginationCount(profileList, totalSize);
		return listDto;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String updateAddress() throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getAllprofileRoles: "));

		List<ProfileRoleDomain> profilelist = profileRoleDAO.getAllprofileRoles();
		for (ProfileRoleDomain profileDomainV2 : profilelist) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					"getprofileRolesOnprofileId: " + JsonUtil.toJsonString(profileDomainV2.getProfileId())));
			List<ProfileRoleDomain> profileroleList = profileRoleDAO
					.getProfileRoleByProfileId(profileDomainV2.getProfileId());
			for (ProfileRoleDomain profilerole : profileroleList) {
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"getAddressListsByProfileId: " + JsonUtil.toJsonString(profilerole.getProfileId())));

				List<AddressDomain> addressList = addressDAO.getAddressListsByProfileId(profilerole.getProfileId(), 0,
						false);
				for (AddressDomain addressDomain : addressList) {
					if (addressDomain.getType().equalsIgnoreCase(AddressType.HOME.name())) {
						if (profilerole.getRoleId() == Integer.parseInt(Role.CUSTOMER.getCode())
								|| profilerole.getRoleId() == Integer.parseInt(Role.DRIVER.getCode())
								|| profilerole.getRoleId() == Integer.parseInt(Role.FIELDOFFICER.getCode())
								|| profilerole.getRoleId() == Integer.parseInt(Role.COORDINATOR.getCode())) {
							Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
									SeverityTypes.INFORMATIONAL.ordinal(),
									"updateAddressRoleId for HOME TYPE: for uuid"
											+ JsonUtil.toJsonString(addressDomain.getUuid()) + "and roleId"
											+ JsonUtil.toJsonString(profilerole.getRoleId())));
							addressDAO.updateAddressRoleId(addressDomain.getUuid(), profilerole.getRoleId());
						} else {
							Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
									SeverityTypes.INFORMATIONAL.ordinal(),
									"updateAddressRoleId for OFFICE TYPE: for uuid"
											+ JsonUtil.toJsonString(addressDomain.getUuid()) + "and roleId"
											+ JsonUtil.toJsonString(profilerole.getRoleId())));
							addressDAO.updateAddressRoleId(addressDomain.getUuid(), profilerole.getRoleId());
						}
					}
				}
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(), "getAddressByTypeAndProfileIdWithRoleId: for HOME TYPE"
								+ JsonUtil.toJsonString(profilerole.getProfileId())));

				List<AddressDomain> updateHomeAddressList = addressDAO.getAddressByTypeAndProfileIdWithRoleId(
						AddressType.HOME.name(), profilerole.getProfileId(), profilerole.getRoleId());
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"activateDeactivateAddress" + JsonUtil.toJsonString(profilerole.getProfileId())));
				if (!updateHomeAddressList.isEmpty())
					addressDAO.activateDeactivateAddress(updateHomeAddressList.get(0).getUuid(), true);

				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(), "getAddressByTypeAndProfileIdWithRoleId: for OFFICE TYPE"
								+ JsonUtil.toJsonString(profilerole.getProfileId())));

				List<AddressDomain> updateOfficeAddressList = addressDAO.getAddressByTypeAndProfileIdWithRoleId(
						AddressType.HOME.name(), profilerole.getProfileId(), profilerole.getRoleId());
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"activateDeactivateAddress" + JsonUtil.toJsonString(profilerole.getProfileId())));
				if (!updateOfficeAddressList.isEmpty())
					addressDAO.activateDeactivateAddress(updateOfficeAddressList.get(0).getUuid(), true);
			}
		}
		return "Updated Successfully";
	}

	@Override
	public AuthModel getAuthDetails(AuthModel authModel) throws Exception {
		List<Profile> coordinatorList = new ArrayList<Profile>();
		List<Profile> franchiseList = new ArrayList<Profile>();
		if (!authModel.getCoordinators().isEmpty()) {
			for (CoordinatorDetail coordinator : authModel.getCoordinators()) {
				Profile profile = new Profile();
				Map<String, String> reqParam = new HashMap<String, String>();
				reqParam.put("coordinatorId", coordinator.getCoordinatorId());
				profile = getProfileDetailsByCompanyProfileId(reqParam);
				coordinatorList.add(profile);
			}
		}
		if (!authModel.getFranchise().isEmpty()) {
			for (Franchise franchise : authModel.getFranchise()) {
				Profile profile = new Profile();
				Map<String, String> reqParam = new HashMap<String, String>();
				reqParam.put("franchiseId", franchise.getFranchiseId());
				profile = getProfileDetailsByCompanyProfileId(reqParam);
				franchiseList.add(profile);
			}
		}
		authModel.setCoordinators(null);
		authModel.setFranchise(null);
		authModel.setCoordinatorList(coordinatorList);
		authModel.setFranchiseList(franchiseList);
		return authModel;
	}

	public List<Profile> getActiveOrInactiveProfilesDetails(Integer roleId, boolean status,
			Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getActiveOrInactiveProfiles Request: " + JsonUtil.toJsonString(roleId)));
		String isCallingFrmFranchise = null, requestedBy = null;
		int cityId = 0, stateId = 0, lowerBound = 0, upperBound = 0, customerTypeId = 0;

		if (reqParam.size() > 0) {
			if (reqParam.containsKey("requestedBy"))
				requestedBy = reqParam.get("requestedBy");
			if (reqParam.containsKey("isCallingFrmFranchise"))
				isCallingFrmFranchise = reqParam.get("isCallingFrmFranchise");
			if (reqParam.containsKey("cityId"))
				cityId = Integer.parseInt(reqParam.get("cityId"));
			if (reqParam.containsKey("stateId"))
				stateId = Integer.parseInt(reqParam.get("stateId"));
			if (reqParam.containsKey("customerTypeId"))
				customerTypeId = Integer.parseInt(reqParam.get("customerTypeId"));

			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}

		List<Profile> profileList = new ArrayList<Profile>();
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(roleId + "");

		List<ProfileDomainV2> profileDomainList = null;
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getAddressType: " + JsonUtil.toJsonString(role)));

		String addressType = profileUtil.getAddressType(role);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "userActiveOrInActiveList: " + JsonUtil.toJsonString(roleId)));
		profileDomainList = profileDAOV2.userActiveOrInActiveList(roleId, status, lowerBound, upperBound, cityId,
				stateId, addressType, customerTypeId, null);
		profileList = profileMapperV2.entityList(profileDomainList);
		if (null == isCallingFrmFranchise) {
			if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
				if (profileList.size() > 0) {
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(),
							"userActiveOrInActiveList: " + JsonUtil.toJsonString(roleId)));
					profileUtil.getDriverList(profileList, requestedBy);
				}
			}
		}
		profileUtil.getAddressList(profileList);
		if (roleId == Integer.parseInt(Role.CUSTOMER.getCode())) {
			profileUtil.getCustomerListWithType(profileList);
		}
		return profileList;
	}

	@Override
	public AuthModel getProfileDetailsOnRole(String roles, Map<String, String> reqParam) throws Exception {
		AuthModel authModel = new AuthModel();
		String[] cities = null;
		String city = null;
		boolean status = false;

		String[] roleList = roles.split(",");
		if (reqParam.size() > 0) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(), "getProfileDetailsOnRole in profileServiceImplv2"
							+ JsonUtil.toJsonString(reqParam) + "for roles " + JsonUtil.toJsonString(roles)));
			if (reqParam.containsKey("status"))
				status = Boolean.valueOf(reqParam.get("status"));
			if (reqParam.containsKey("cityId")) {
				city = reqParam.get("cityId");
				cities = city.split(",");
			}
		}
		List<Profile> profile = null;
		List<Profile> profileList = null;
		for (String roleString : roleList) {
			profileList = new ArrayList<Profile>();
			profile = new ArrayList<Profile>();
			if (null != cities && cities.length > 0) {
				for (String cityId : cities) {
					reqParam.put("cityId", cityId);
					profile = new ArrayList<Profile>();
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(),
							"getProfileDetailsOnRole in profileServiceImplv2" + JsonUtil.toJsonString(reqParam)));
					profile = getActiveOrInactiveProfilesDetails(Integer.valueOf(roleString), status, reqParam);
					profileList.addAll(profile);
				}
			} else {
				profile = getActiveOrInactiveProfilesDetails(Integer.valueOf(roleString), status, reqParam);
				profileList.addAll(profile);
			}
			authModel = profileUtil.getProfileListDetails(authModel, profileList, roleString);

		}
		return authModel;
	}

	@Override
	public String tagVendor(RouteTag routeTag) throws Exception {
		if (CommonUtils.isNullCheck(routeTag.getVendorId()))
			throw new NOT_FOUND("Please mention the vendorId !!");
		if (0 >= routeTag.getVendorRoleId())
			throw new NOT_FOUND("Please mention the vendorRoleId !!");
		Role role = Role.getRole(String.valueOf(routeTag.getVendorRoleId()));
		DBUpdate dbUpdate = new DBUpdate();
		Map<String, Object> expression = new HashMap<String, Object>();
		Map<String, Object> conditions = new HashMap<String, Object>();
		switch (role) {
		case FRANCHISE:
			dbUpdate.setTableName("franchise");
			expression.put("isTag", true);
			conditions.put("franchiseId", routeTag.getVendorId());
			break;
		default:
			break;
		}
		dbUpdate.setExpression(expression);
		dbUpdate.setConditions(conditions);
		dbUpdateService.dbUpdate(dbUpdate);
		return "Tagged successfully";
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String profileMobileEmailSendOtp(ProfileEdit profileEdit, Map<String, String> reqParam) throws Exception {
		boolean isExist = false;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("isExist")) {
				isExist = Boolean.valueOf(reqParam.get("isExist"));
			}
		}
		ProfileDomainV2 profileDomainV2 = new ProfileDomainV2();
		if (profileEdit.isIsMobile()) {
			if (CommonUtils.isNullCheck(profileEdit.getMobileNumber()))
				throw new NOT_FOUND("Please mention the mobile Number!!");
		} else {
			if (CommonUtils.isNullCheck(profileEdit.getEmailId()))
				throw new NOT_FOUND("Please mention the email Id!!");
		}
		ProfileDomainV2 profileValidate = null;

		if (!CommonUtils.isNullCheck(profileEdit.getProfileId())) {
			profileDomainV2 = profileDAOV2.getProfileDetails(profileEdit.getProfileId());
			if (null == profileDomainV2)
				throw new NOT_FOUND("Please mention the proper profileId !!");
			else {
				if (profileEdit.isIsMobile()) {
					if (isExist) {
						if (!profileEdit.getMobileNumber().equals(profileDomainV2.getMobileNumber()))
							throw new NOT_FOUND("Please mention the proper mobile number!!");
					} else {
						profileValidate = profileDAOV2.getProfileByMobileNumberOrEmailId(profileEdit.getMobileNumber(), null,
								0);
						if (null != profileValidate)
							throw new USER_ALREADY_EXIST(profileEdit.getMobileNumber());
					}
				} else {
					if (isExist) {
						if (!CommonUtils.isNullCheck(profileDomainV2.getEmailId()))
							if (!profileEdit.getEmailId().equals(profileDomainV2.getEmailId()))
								throw new NOT_FOUND("Please mention the proper email !!");
					} else {
						profileValidate = profileDAOV2.getProfileByMobileNumberOrEmailId(null, profileEdit.getEmailId(), 0);
						if (null != profileValidate)
							throw new USER_ALREADY_EXIST(profileEdit.getEmailId());
					}
				}
			}
		} else
			throw new NOT_FOUND("Please mention the profileId !!");
		Profile profile = new Profile();
		BeanUtils.copyProperties(profileEdit, profile);
		sendOtpOrEmailToCustomer(profile);
		return "Otp sent successfully";
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String profileMobileEmailEditValidateOtp(ProfileEdit profileEdit, Map<String, String> reqParam)
			throws Exception {
		ProfileDomainV2 profileDomain = new ProfileDomainV2();
		if (!CommonUtils.isNullCheck(profileEdit.getProfileId())) {
			profileDomain = profileDAOV2.getProfileDetails(profileEdit.getProfileId());
			if (null == profileDomain)
				throw new NOT_FOUND("Please mention the proper profileId !!");
		} else
			throw new NOT_FOUND("Please mention the profileId !!");
		boolean isExist = false;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("isExist")) {
				isExist = Boolean.valueOf(reqParam.get("isExist"));
			}
		}
		if (isExist) {
			Otp otp = new Otp();
			otp.setOtp(profileEdit.getOtp());
			if (profileEdit.isIsMobile()) {
				otp.setMobileNumber(profileDomain.getMobileNumber());
			} else
				otp.setEmailId(profileDomain.getEmailId());
			validateOtp(otp, true);
			return "Validated Successfully";
		}
		profileUtil.validateProfileAndRole(profileEdit);
		ProfileDomainV2 profileDomainV2 = new ProfileDomainV2();
		Otp otp = new Otp();
		otp.setOtp(profileEdit.getOtp());
		if (profileEdit.isIsMobile()) {
			otp.setMobileNumber(profileEdit.getMobileNumber());
		} else
			otp.setEmailId(profileEdit.getEmailId());
		Profile profile = validateOtp(otp, true);
		if (profile.isOtpIsActive()) {
			ProfileDomainV2 profiledetails = profileDAOV2.getProfileDetails(profileEdit.getProfileId());
			Audit audit = new Audit();
			String fieldName = null;
			audit.setTableName("profile");
			if (null != profiledetails) {
				if (profileEdit.isIsMobile()) {
					profileDomainV2 = profileDAOV2.getProfileByMobileNumberOrEmailId(profileEdit.getMobileNumber(),
							null, 0);
					fieldName = "mobileNumber";
					audit = setValues(audit, fieldName, AuditValues.MOBILE_NO_EDIT.getCode(),
							profiledetails.getMobileNumber(), profileEdit.getMobileNumber());
				} else {
					profileDomainV2 = profileDAOV2.getProfileByMobileNumberOrEmailId(null, profileEdit.getEmailId(), 0);
					fieldName = "emailId";
					audit = setValues(audit, fieldName, AuditValues.EMAIL_EDIT.getCode(), profiledetails.getEmailId(),
							profileEdit.getEmailId());
				}
				if (null == profileDomainV2) {
					profileDAOV2.updateEmailAndMobileNo(profileEdit.getEmailId(), profileEdit.getMobileNumber(),
							profileEdit.getProfileId());
					audit.setAuditId(CommonUtils.generateRandomId());
					audit.setUserId(profileEdit.getUserId());
					audit.setRoleId(profileEdit.getRoleId());
					auditDAO.save(audit);

				} else {
					throw new USER_ALREADY_EXIST();
				}

			}
		}
		return "Updated Successfully";
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String profileMobileEmailEdit(ProfileEdit profileEdit, Map<String, String> reqParam) throws Exception {
		ProfileDomainV2 profileDomainV2 = new ProfileDomainV2();
		ProfileDomainV2 validateProfile = new ProfileDomainV2();
		if (0 >= profileEdit.getRoleId())
			throw new NOT_FOUND("Please mention the roleId");
		else {
			Role role = Role.getRole(String.valueOf(profileEdit.getRoleId()));
			if (null == role)
				throw new ROLE_NOT_FOUND(" " + profileEdit.getRoleId());
		}
		if (!CommonUtils.isNullCheck(profileEdit.getUserId())) {
			validateProfile = profileDAOV2.getProfileByIdAndRole(profileEdit.getUserId(), profileEdit.getRoleId());
			if (null == validateProfile)
				throw new PROFILE_NOT_FOUND();
		} else
			throw new NOT_FOUND("Please mention the userId");

		ProfileDomainV2 profiledetails = profileDAOV2.getProfileDetails(profileEdit.getProfileId());
		Otp otp = new Otp();
		boolean isValidate = false;
		Audit audit = new Audit();
		String fieldName = null;
		audit.setTableName("profile");
		if (null != profiledetails) {
			if (profileEdit.isIsMobile()) {
				profileDomainV2 = profileDAOV2.getProfileByMobileNumberOrEmailId(profileEdit.getMobileNumber(), null,
						0);
//				otp = otpDAO.getOtp(profileEdit.getMobileNumber(), null);
//				if (null != otp) {
//					isValidate = otp.isIsChecked();
//				}
//				audit.setFieldName("mobileNumber");
//				audit.setActivity(AuditValues.MOBILE_NO_EDIT.getCode());
//				audit.setOldValue(profiledetails.getMobileNumber());
//				audit.setNewValue(profileEdit.getMobileNumber());
				fieldName = "mobileNumber";
				audit = setValues(audit, fieldName, AuditValues.MOBILE_NO_EDIT.getCode(),
						profiledetails.getMobileNumber(), profileEdit.getMobileNumber());
			} else {
				profileDomainV2 = profileDAOV2.getProfileByMobileNumberOrEmailId(null, profileEdit.getEmailId(), 0);
//				otp = otpDAO.getOtp(null, profileEdit.getEmailId());
//				if (null != otp) {
//					isValidate = otp.isIsChecked();
//				}
				fieldName = "emailId";
//				audit.setActivity(AuditValues.EMAIL_EDIT.getCode());
//				audit.setOldValue(profiledetails.getEmailId());
//				audit.setNewValue(profileEdit.getEmailId());
				audit = setValues(audit, fieldName, AuditValues.EMAIL_EDIT.getCode(), profiledetails.getEmailId(),
						profileEdit.getEmailId());
			}
			if (null == profileDomainV2) {
				if (isValidate) {
					profileDAOV2.updateEmailAndMobileNo(profileEdit.getEmailId(), profileEdit.getMobileNumber(),
							profileEdit.getProfileId());
					audit.setAuditId(CommonUtils.generateRandomId());
					audit.setUserId(profileEdit.getUserId());
					audit.setRoleId(profileEdit.getRoleId());
					auditDAO.save(audit);
				}
			} else
				throw new USER_ALREADY_EXIST();
		}
		return "Updated Successfully";
	}

	private Audit setValues(Audit audit, String fieldName, String activity, String oldValue, String newValue) {
		audit.setFieldName(fieldName);
		audit.setActivity(activity);
		audit.setOldValue(oldValue);
		audit.setNewValue(newValue);
		return audit;
	}

}
