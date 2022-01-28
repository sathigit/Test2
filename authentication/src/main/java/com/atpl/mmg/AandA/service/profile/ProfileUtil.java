package com.atpl.mmg.AandA.service.profile;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.constant.AddressType;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.constant.EnquiryStatus;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.bankaccount.BankAccountDAO;
import com.atpl.mmg.AandA.dao.enterprise.EnterpriseDAO;
import com.atpl.mmg.AandA.dao.fleetoperator.FleetOperatorDAO;
import com.atpl.mmg.AandA.dao.franchise.FranchiseDAO;
import com.atpl.mmg.AandA.dao.inactivereason.ReasonDao;
import com.atpl.mmg.AandA.dao.otp.OtpDAO;
import com.atpl.mmg.AandA.dao.profile.AddressDAO;
import com.atpl.mmg.AandA.dao.profile.BdmDAO;
import com.atpl.mmg.AandA.dao.profile.BdoDAO;
import com.atpl.mmg.AandA.dao.profile.ChannelPartnerDAO;
import com.atpl.mmg.AandA.dao.profile.CoordinatorDAO;
import com.atpl.mmg.AandA.dao.profile.CustomerDAO;
import com.atpl.mmg.AandA.dao.profile.FieldOfficerDAO;
import com.atpl.mmg.AandA.dao.profile.OperationalTeamDao;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.dao.profilerole.ProfileRoleDAO;
import com.atpl.mmg.AandA.dao.role.RoleDAO;
import com.atpl.mmg.AandA.dao.warehouse.WareHouseDAO;
import com.atpl.mmg.AandA.domain.bankaccount.BankAccountDomain;
import com.atpl.mmg.AandA.domain.inactivereason.ReasonDomain;
import com.atpl.mmg.AandA.domain.otp.Otp;
import com.atpl.mmg.AandA.domain.profile.AddressDomain;
import com.atpl.mmg.AandA.domain.profile.BDMDomain;
import com.atpl.mmg.AandA.domain.profile.BDODomain;
import com.atpl.mmg.AandA.domain.profile.ChannelPartnerDomain;
import com.atpl.mmg.AandA.domain.profile.CoordinatorDomain;
import com.atpl.mmg.AandA.domain.profile.CustomerDomain;
import com.atpl.mmg.AandA.domain.profile.EnterpriseDomain;
import com.atpl.mmg.AandA.domain.profile.FieldOfficerDomain;
import com.atpl.mmg.AandA.domain.profile.FleetOperatorDomain;
import com.atpl.mmg.AandA.domain.profile.FranchiseDomainV2;
import com.atpl.mmg.AandA.domain.profile.OperationalTeamDomain;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.domain.profile.WareHouseDomain;
import com.atpl.mmg.AandA.domain.profilerole.ProfileRoleDomain;
import com.atpl.mmg.AandA.domain.role.RoleDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.AADHAR_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.ACCOUNT_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.EMAILID_PATTERN_NOT_MATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.FARE_DISTRIBUTION_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.FARE_DISTRIBUTION_NOT_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.FIRSTNAME_PATTERN_NOT_MATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.GST_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.INVALID_PROFILE_SECURITY_KEY;
import com.atpl.mmg.AandA.exception.MmgRestException.LICENSE_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.OTP_VALIDATE_NOT_CHECKED;
import com.atpl.mmg.AandA.exception.MmgRestException.PAN_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.PASSWORD_MISMATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.PASSWORD_PATTERN_NOT_MATCHED;
import com.atpl.mmg.AandA.exception.MmgRestException.PHONENUMBER_PATTERN_NOT_MATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.PROFILE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.REGISTRATION_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.USER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.USER_NOT_EXISTS;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.mapper.profile.AddressMapper;
import com.atpl.mmg.AandA.model.auth.AuthModel;
import com.atpl.mmg.AandA.model.bankaccount.BankAccountModel;
import com.atpl.mmg.AandA.model.boardingRequest.BoardingRequestModel;
import com.atpl.mmg.AandA.model.boardingRequest.EnquiryReasonModel;
import com.atpl.mmg.AandA.model.faredist.FareDistributionDTo;
import com.atpl.mmg.AandA.model.profile.Address;
import com.atpl.mmg.AandA.model.profile.BDM;
import com.atpl.mmg.AandA.model.profile.BDO;
import com.atpl.mmg.AandA.model.profile.ChannelPartner;
import com.atpl.mmg.AandA.model.profile.City;
import com.atpl.mmg.AandA.model.profile.Coordinator;
import com.atpl.mmg.AandA.model.profile.Customer;
import com.atpl.mmg.AandA.model.profile.CustomerTypeModel;
import com.atpl.mmg.AandA.model.profile.Driver;
import com.atpl.mmg.AandA.model.profile.Enterprise;
import com.atpl.mmg.AandA.model.profile.FieldOfficer;
import com.atpl.mmg.AandA.model.profile.FleetOperator;
import com.atpl.mmg.AandA.model.profile.Franchise;
import com.atpl.mmg.AandA.model.profile.ListDto;
import com.atpl.mmg.AandA.model.profile.OperationalTeam;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.model.profile.ProfileEdit;
import com.atpl.mmg.AandA.model.profile.State;
import com.atpl.mmg.AandA.model.profile.TandCModel;
import com.atpl.mmg.AandA.model.profile.WareHouse;
import com.atpl.mmg.AandA.model.reason.ReasonModel;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.DataValidation;
import com.atpl.mmg.AandA.utils.DateUtility;
import com.atpl.mmg.AandA.utils.EmailValidator;
import com.atpl.mmg.AandA.utils.IDGeneration;

@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProfileUtil {

	@Autowired
	DataValidation dataValidation;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Autowired
	ProfileRoleDAO profileRoleDAO;

	@Autowired
	AuthFranchiseCommonService franchiseCommonService;

	@Autowired
	BankAccountDAO bankAccountDAO;

	@Autowired
	EmailValidator emailValidator;

	@Autowired
	OtpDAO otpDAO;

	@Autowired
	AdminCommonService adminCommonService;

	@Autowired
	IDGeneration idGeneration;

	@Autowired
	FranchiseDAO franchiseDAO;

	@Autowired
	FleetOperatorDAO fleetOperatorDAO;

	@Autowired
	EnterpriseDAO enterpriseDAO;

	@Autowired
	WareHouseDAO wareHouseDAO;

	@Autowired
	AddressDAO addressDAO;

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	BdmDAO bdmDAO;

	@Autowired
	BdoDAO bdoDAO;

	@Autowired
	ChannelPartnerDAO channelPartnerDAO;

	@Autowired
	FieldOfficerDAO fieldOfficerDAO;

	@Autowired
	OperationalTeamDao operationalTeamDAO;

	@Autowired
	CoordinatorDAO coordinatorDAO;

	@Autowired
	AddressMapper addressMapper;

	@Autowired
	ReasonDao reasonDao;

	@Autowired
	RoleDAO roleDAO;

	public boolean validateCommonProfile(Profile profile, String profileSecurityHeader, boolean isCommonProfile)
			throws Exception {
		if (CommonUtils.isNullCheck(profile.getProfileSource()))
			throw new NOT_FOUND("Please mention profile source");
		Role role = Role.getRole(profile.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");
		validateCommonRoleProfile(profile, profileSecurityHeader);
		boolean isUpdate = false;
		if (!CommonUtils.isNullCheck(profile.getId())) {
			isUpdate = true;
		}
		if (!profile.isIsMigration()) {
			if (!profile.isIsRegisterByMmg())
				if (CommonUtils.isNullCheck(profile.getCreatedBy()))
					validatePassword(profile);
				else {
					if (!profile.getCreatedBy().equalsIgnoreCase(Role.FRANCHISE.name()))
						validatePassword(profile);
				}
		}

		switch (role) {
		case CUSTOMER:
			validateCustomerDetails(profile, isUpdate);
			break;
		// case OWNER:
		// case TIEUPS:
		case BDM:
			validateBDO(profile, isUpdate, isCommonProfile);
			break;
		// case BDO:
		case CHANNEL_PARTNER:
			validateChannelPartner(profile, isUpdate, isCommonProfile);
			break;

		case COORDINATOR:
			validateCoordinator(profile, isUpdate, isCommonProfile);
			break;
		case FIELDOFFICER:
			validateFieldOfficer(profile, isUpdate, isCommonProfile);
			break;
		case OPERATIONAL_TEAM:
			validateOperationalTeam(profile, isUpdate, isCommonProfile);
			break;
		case FRANCHISE:
			validateFranchiseDetails(profile, isUpdate, isCommonProfile, true);
			break;
		case FLEET_OPERATOR:
			validateFleetDetails(profile, isUpdate, isCommonProfile, true);
			break;
		case ENTERPRISE:
			validateEnterpriseDetails(profile, isUpdate, isCommonProfile);
			break;
		case WAREHOUSE:
			validateWarehouseDetails(profile, isUpdate, isCommonProfile, true);
			break;
		case DRIVER:
			validateDriverDetails(profile, isUpdate, isCommonProfile);
			break;
		default:
			break;

		}
		return true;
	}

	public boolean validateUpdateCommonProfile(Profile profile, String profileSecurityHeader, boolean isPersonal)
			throws Exception {
		if (CommonUtils.isNullCheck(profile.getProfileSource()))
			throw new NOT_FOUND("Please mention profile source");
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"validateCommonProfile in ProfileUtil" + JsonUtil.toJsonString(profile)));

		Role role = Role.getRole(profile.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");
		if (profile.getRoleId() == Integer.parseInt(Role.CUSTOMER.getCode()) || isPersonal) {
			validateCommonRoleProfile(profile, profileSecurityHeader);
			validatePassword(profile);
		}
		if (profile.getRoleId() != Integer.parseInt(Role.CUSTOMER.getCode()))
			validateModifiedByAndReason(profile.getModifiedBy(), profile.getReason(), profile.getModifiedByRoleId());
		boolean isUpdate = false;
		if (!CommonUtils.isNullCheck(profile.getId())) {
			isUpdate = true;
		}

		switch (role) {
		case CUSTOMER:
			validateCustomerDetails(profile, isUpdate);
			break;
		// case OWNER:
		// case TIEUPS:
		case BDM:
			if (!isPersonal)
				validateBDO(profile, isUpdate, false);
			break;
		// case BDO:
		case CHANNEL_PARTNER:
			if (!isPersonal)
				validateChannelPartner(profile, isUpdate, false);
			break;
		case COORDINATOR:
			if (!isPersonal)
				validateCoordinator(profile, isUpdate, false);
			break;
		case FIELDOFFICER:
			if (!isPersonal)
				validateFieldOfficer(profile, isUpdate, false);
			break;
		case OPERATIONAL_TEAM:
			if (!isPersonal)
				validateOperationalTeam(profile, isUpdate, false);
			break;
		case FRANCHISE:
			if (!isPersonal)
				validateFranchiseDetails(profile, isUpdate, false, isPersonal);
			break;
		case FLEET_OPERATOR:
			if (!isPersonal)
				validateFleetDetails(profile, isUpdate, false, isPersonal);
			break;
		case ENTERPRISE:
			if (!isPersonal)
				validateEnterpriseDetails(profile, isUpdate, false);
			break;
		case WAREHOUSE:
			if (!isPersonal)
				validateWarehouseDetails(profile, isUpdate, false, isPersonal);
			break;
		case DRIVER:
			if (!isPersonal)
				validateDriverDetails(profile, isUpdate, false);
			break;
		default:
			break;

		}
		return true;
	}

	private void validateCommonRoleProfile(Profile profile, String profileSecurityHeader) throws Exception {
		boolean isValid = true;
		if (!CommonUtils.isNullCheck(profile.getId()))
			isValid = true;
		else if ((profile.getProfileSource().equalsIgnoreCase(Constants.PROFILE_SOURCE)
				|| profile.getProfileSource().equalsIgnoreCase(Role.FRANCHISE.name())
				|| profile.getProfileSource().equalsIgnoreCase(Role.OPERATIONAL_TEAM.name()))
				&& Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.CUSTOMER.getCode())) {
			isValid = false;
		}

		if (!isValid) {
			if (!profile.isIsMigration())
				validateMobile(profile.getMobileNumber());
		} else {
			validateMobileEmailAndFirstName(profile);

			if (Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.OWNER.getCode())
					|| Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.TIEUPS.getCode())) {
				if (CommonUtils.isNullCheck(profileSecurityHeader))
					throw new NOT_FOUND("Profile security header key cannot be empty for role admin/tieups");
				else if (!profileSecurityHeader.equalsIgnoreCase(mmgProperties.getProfileSecHeaderKey()))
					throw new INVALID_PROFILE_SECURITY_KEY();
			}

			if (Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.CUSTOMER.getCode())
					|| Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.DRIVER.getCode())) {
				if (!profile.getProfileSource().equalsIgnoreCase(Constants.PROFILE_SOURCE)) {
					if (!Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.CUSTOMER.getCode()))
						validateDobAndGender(profile.getDob(), profile.getGenderId());
				}

			}
			if (!Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.CUSTOMER.getCode()))
				validateAddress(profile);
		}
		if (!profile.isIsMigration()) {
			if (!profile.isIsRegisterByMmg()) {
				if (CommonUtils.isNullCheck(profile.getId()))
					if (CommonUtils.isNullCheck(profile.getCreatedBy()))
						validateOtpChecked(profile, true);
			}
		}

	}

	public boolean validateMobileEmailAndFirstName(Profile profile) throws Exception {
		validateFirstName(profile.getFirstName());
		validateMobile(profile.getMobileNumber());
		if (!Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.DRIVER.getCode())) {
			if (Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.CUSTOMER.getCode())) {
				if (null != profile.getCustomer()) {
					if (1 != profile.getCustomer().getCustomerTypeId())
						if (CommonUtils.isNullCheck(profile.getEmailId()))
							throw new NOT_FOUND("Please enter emailId");
				}
			} else if (CommonUtils.isNullCheck(profile.getEmailId()))
				throw new NOT_FOUND("Please enter emailId");
		} else {
			if (CommonUtils.isNullCheck(profile.getEmailId()))
				profile.setEmailId(null);
		}

		if (!CommonUtils.isNullCheck(profile.getEmailId())) {
			if (!dataValidation.isValidate(profile.getEmailId(), DataValidation.EMAIL_PATTERN))
				throw new EMAILID_PATTERN_NOT_MATCH();
		}
		return true;
	}

	public boolean validateFirstName(String firstName) {
		if (CommonUtils.isNullCheck(firstName))
			throw new NOT_FOUND("Please enter firstname");
		if (!dataValidation.isValidate(firstName, DataValidation.FIRSTNAME_PATTERN))
			throw new FIRSTNAME_PATTERN_NOT_MATCH();
		return true;
	}

	public boolean validateMobile(String mobileNumber) {
		if (CommonUtils.isNullCheck(mobileNumber))
			throw new NOT_FOUND("Please enter mobileNumber");
		if (!dataValidation.isValidate(String.valueOf(mobileNumber), DataValidation.PHONENUMBER_PATTERN))
			throw new PHONENUMBER_PATTERN_NOT_MATCH();
		return true;
	}

	public boolean validateDobAndGender(Date dob, int genderId) throws ParseException {
		if (null == dob)
			throw new NOT_FOUND("Please enter date of birth");

		if (!DateUtility.dobValidation(dob))
			throw new NOT_FOUND("Age should be greater than 18 years " + dob);

		if (0 == genderId)
			throw new NOT_FOUND("Please enter gender");

		// Gender validation through enum - pending
		return true;
	}

	public void validateOtpChecked(Profile profile, boolean isSaveProfile) throws Exception {
		boolean isOtpChecked = false;
		if (isSaveProfile) {
			if (Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.CUSTOMER.getCode())
					|| Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.DRIVER.getCode()))
				isOtpChecked = true;
		} else
			isOtpChecked = true;

		if (isOtpChecked) {
			Otp otpObj = otpDAO.getOtp(String.valueOf(profile.getMobileNumber()), null);
			if (null == otpObj || !otpObj.isIsChecked())
				throw new OTP_VALIDATE_NOT_CHECKED();
		}

	}

	public void validatePassword(Profile profile) {

		if (CommonUtils.isNullCheck(profile.getId())) {

			if (CommonUtils.isNullCheck(profile.getPassword()))
				throw new NOT_FOUND("Please enter the password");
			if (!dataValidation.isValidate(profile.getPassword(), DataValidation.PASSWORD_PATTERN))
				throw new PASSWORD_PATTERN_NOT_MATCHED();
			if (!Integer.toString(profile.getRoleId()).equalsIgnoreCase(Role.CUSTOMER.getCode())) {
				if (!profile.getPassword().equals(profile.getConfirmPassword()))
					throw new PASSWORD_MISMATCH();
				if (CommonUtils.isNullCheck(profile.getConfirmPassword()))
					throw new NOT_FOUND("Please enter the confirm password");
			} else
				profile.setConfirmPassword(profile.getPassword());

			encryptPwd(profile);
		}
	}

	private boolean encryptPwd(Profile profile) {
		profile.setPassword(CommonUtils.encriptString(profile.getPassword()));
		profile.setConfirmPassword(CommonUtils.encriptString(profile.getConfirmPassword()));
		return true;
	}

	private void validateAddress(Profile profile) {
		List<Address> addressList = profile.getAddress();
		String[] addressTypes = new String[2];
		int count = 0;
		if (null == addressList)
			throw new NOT_FOUND("Please enter address details");
		else {
			for (Address address : addressList) {
				if (CommonUtils.isNullCheck(address.getAddress1()))
					throw new NOT_FOUND("Please enter address");
				if (address.getCountryId() == 0)
					throw new NOT_FOUND("Please enter country");
				if (address.getStateId() == 0)
					throw new NOT_FOUND("Please enter state");
				if (address.getCityId() == 0)
					throw new NOT_FOUND("Please enter city");
				if (address.getPincode() == null)
					throw new NOT_FOUND("Please enter pin code");
				if (CommonUtils.isNullCheck(address.getType()))
					throw new NOT_FOUND("Please enter address type (HOME/OFFICE)");
				else {
//					boolean isFound = Arrays.stream(addressTypes).anyMatch(address.getType().toUpperCase()::equals);
//					if (isFound)
//						throw new NOT_FOUND("Address type " + address.getType() + " already added");
//					else {
					if (!EnumUtils.isValidEnum(AddressType.class, address.getType().toUpperCase()))
						throw new NOT_FOUND("Address type should be HOME/OFFICE");

					addressTypes[count] = address.getType().toUpperCase();
					count++;
//					}
				}
			}

			if (profile.getRoleId() == Integer.parseInt(Role.CUSTOMER.getCode())
					|| profile.getRoleId() == Integer.parseInt(Role.DRIVER.getCode())
					|| profile.getRoleId() == Integer.parseInt(Role.FIELDOFFICER.getCode())
//					|| profile.getRoleId() == Integer.parseInt(Role.COORDINATOR.getCode())
			) {
				if (!Arrays.stream(addressTypes).anyMatch(AddressType.HOME.name()::equals))
					throw new NOT_FOUND(Role.getRole(String.valueOf(profile.getRoleId())).name()
							+ " role should contain home address");
			} else {
				if (!Arrays.stream(addressTypes).anyMatch(AddressType.OFFICE.name()::equals))
					throw new NOT_FOUND(Role.getRole(String.valueOf(profile.getRoleId())).name()
							+ " role should contain Office address");
			}

		}
	}

	private void validateCustomerDetails(Profile profile, boolean isUpdate) throws Exception {
		Customer customer = profile.getCustomer();
		if (!CommonUtils.isNullCheck(profile.getCreatedBy())
				&& profile.getCreatedBy().equalsIgnoreCase(Role.FRANCHISE.name())) {
			if (CommonUtils.isNullCheck(profile.getCustomer().getReferenceId()))
				throw new NOT_FOUND("Please enter the franchise referenceId in customer details");
			else
				franchiseDAO.getFranchiseByFranchiseId(profile.getCustomer().getReferenceId());
		}
		if (CommonUtils.isNullCheck(profile.getId())) {
			if (null == customer)
				throw new NOT_FOUND("Please enter customer details");
			else {
				if (0 >= customer.getCustomerTypeId())
					throw new NOT_FOUND("Please mention the customerType");
				else
					adminCommonService.customerTypeById(customer.getCustomerTypeId());
				Role role = Role.getRole(profile.getRoleId() + "");
				if (!profile.isIsMigration())
					validateTermsAndCondition(profile, role);
				if (!CommonUtils.isNullCheck(customer.getGstNo()))
					validateGstNo(customer.getGstNo(), profile.getId(), isUpdate, Role.CUSTOMER.getCode());
				else
					customer.setGstNo(null);
			}
		}

	}

	private boolean validateTermsAndCondition(Profile profile, Role role) throws Exception {

		if (role.getCode().equalsIgnoreCase(Role.CUSTOMER.getCode())) {
			if (null != profile.getCustomer()) {
				if (!profile.getCustomer().isIsTermsAndCondition())
					throw new NOT_FOUND("Please accept the terms and conditions");
				else
					profile.getCustomer().setIsTermsAndCondition(true);

				if (CommonUtils.isNullCheck(profile.getCustomer().getTermsAndConditionsId()))
					throw new NOT_FOUND("Please mention the  terms and conditions Id");
				else {
					List<TandCModel> tandCModel = adminCommonService.getActiveTermsAndConditions(profile.getRoleId());
					for (TandCModel tAndC : tandCModel) {
						if (!profile.getCustomer().getTermsAndConditionsId().equals(tAndC.getUuid()))
							throw new NOT_FOUND("Please mention the proper terms and conditions Id");
					}
				}
			}
		}
//		boolean skipTerms = false;
//		if (!CommonUtils.isNullCheck(profile.getCreatedBy())
//				&& profile.getCreatedBy().equalsIgnoreCase(Role.FRANCHISE.name())) {
//			skipTerms = true;
//		}
//		if (role.getCode().equalsIgnoreCase(Role.CUSTOMER.getCode())) {
//			if (null != profile.getCustomer()) {
//				if (!profile.getCustomer().isIsTermsAndCondition()) {
//					if (!skipTerms)
//						throw new NOT_FOUND("Please accept the terms and conditions");
//					else
//						profile.getCustomer().setIsTermsAndCondition(true);
//				} else
//					profile.getCustomer().setIsTermsAndCondition(true);
//
//				if (CommonUtils.isNullCheck(profile.getCustomer().getTermsAndConditionsId())) {
//					if (!skipTerms)
//						throw new NOT_FOUND("Please mention the  terms and conditions Id");
//					else {
//						verifyTermsAndConditions(profile, skipTerms);
//					}
//				} else {
//					verifyTermsAndConditions(profile, skipTerms);
//				}
//			}
//		}
		return true;
	}

	private void verifyTermsAndConditions(Profile profile, boolean skipTerms) throws Exception {
		List<TandCModel> tandCModel = adminCommonService.getActiveTermsAndConditions(profile.getRoleId());
		for (TandCModel tAndC : tandCModel) {
			if (!skipTerms) {
				if (!profile.getCustomer().getTermsAndConditionsId().equals(tAndC.getUuid()))
					throw new NOT_FOUND("Please mention the proper terms and conditions Id");
			} else
				profile.getCustomer().setTermsAndConditionsId(tAndC.getUuid());
		}
	}

	private void validateBDO(Profile profile, boolean isUpdate, boolean isCommonProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "validateBDO in ProfileUtil" + JsonUtil.toJsonString(profile)));
		if (null == profile.getAadharNumber())
			throw new NOT_FOUND("Please enter aadhar number");
		else
			validateAadharNumber(profile.getAadharNumber(), profile.getId(), isUpdate, isCommonProfile);

		validatePanNumber(profile, profile.getId(), isUpdate, isCommonProfile);
		validateBank(profile, profile.getId(), isUpdate, isCommonProfile);
	}

	private void validateChannelPartner(Profile profile, boolean isUpdate, boolean isCommonProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"validateChannelPartner in ProfileUtil" + JsonUtil.toJsonString(profile)));
		if (null == profile.getAadharNumber())
			throw new NOT_FOUND("Please enter aadhar number");
		else
			validateAadharNumber(profile.getAadharNumber(), profile.getId(), isUpdate, isCommonProfile);

		validatePanNumber(profile, profile.getId(), isUpdate, isCommonProfile);
		validateBank(profile, profile.getId(), isUpdate, isCommonProfile);
	}

	private void validateCoordinator(Profile profile, boolean isUpdate, boolean isCommonProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"validateCoordinator in ProfileUtil" + JsonUtil.toJsonString(profile)));
		if (null == profile.getAadharNumber())
			throw new NOT_FOUND("Please enter aadhar number");
		else
			validateAadharNumber(profile.getAadharNumber(), profile.getId(), isUpdate, isCommonProfile);

		validatePanNumber(profile, profile.getId(), isUpdate, isCommonProfile);
		validateBank(profile, profile.getId(), isUpdate, isCommonProfile);
	}

	private void validateFieldOfficer(Profile profile, boolean isUpdate, boolean isCommonProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"validateFieldOfficer in ProfileUtil" + JsonUtil.toJsonString(profile)));
		if (null == profile.getAadharNumber())
			throw new NOT_FOUND("Please enter aadhar number");
		else
			validateAadharNumber(profile.getAadharNumber(), profile.getId(), isUpdate, isCommonProfile);

		validatePanNumber(profile, profile.getId(), isUpdate, isCommonProfile);
		validateBank(profile, profile.getId(), isUpdate, isCommonProfile);
	}

	private void validateOperationalTeam(Profile profile, boolean isUpdate, boolean isCommonProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"validateOperationalTeam in ProfileUtil" + JsonUtil.toJsonString(profile)));
		if (null == profile.getAadharNumber())
			throw new NOT_FOUND("Please enter aadhar number");
		else
			validateAadharNumber(profile.getAadharNumber(), profile.getId(), isUpdate, isCommonProfile);

		validatePanNumber(profile, profile.getId(), isUpdate, isCommonProfile);
		validateBank(profile, profile.getId(), isUpdate, isCommonProfile);
	}

	public void validateWarehouseDetails(Profile profile, boolean isUpdate, boolean isCommonProfile,
			boolean isProfessional) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"validateWarehouseDetails in ProfileUtil" + JsonUtil.toJsonString(profile)));
		WareHouse wareHouse = profile.getWarehouse();
		if (null == wareHouse)
			throw new NOT_FOUND("Please mention the warehouse details");

		validateCompanyName(wareHouse.getCompanyName());
		validateYoc(wareHouse.getYearOfContract(), profile.getRoleId());
		validateStartAndEndDate(wareHouse.getStartDate(), wareHouse.getEndDate());
		if (!CommonUtils.isNullCheck(wareHouse.getMdName()))
			validateProprietorName(wareHouse.getMdName(), "md");
		validateCompanyAddress(wareHouse.getLatitude(), wareHouse.getLongitude());
		validatePanNumber(profile, profile.getId(), isUpdate, isCommonProfile);
		validateBank(profile, profile.getId(), isUpdate, isCommonProfile);
		validateWarehouseRegistrationNo(wareHouse.getRegistrationNumber(), profile.getId(), isUpdate);
		if (isProfessional) {
			if (!CommonUtils.isNullCheck(wareHouse.getBoardingEnquiryId()))
				validateEnquiry(wareHouse.getModifiedBy(), wareHouse.getReason());
		}
	}

	public void validateCompanyName(String name) throws Exception {
		if (CommonUtils.isNullCheck(name))
			throw new NOT_FOUND("Please mention the CompanyName!!");
		if (!dataValidation.isValidate(name, DataValidation.FIRSTNAME_PATTERN))
			throw new NOT_FOUND("Please mention the proper company Name");
	}

	private void validateYoc(int yoc, int roleId) throws Exception {
		if (0 >= yoc)
			throw new NOT_FOUND("Please mention the year of contract");

		if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())) {
			if (yoc > mmgProperties.getFranchiseYearOfContract())
				throw new NOT_FOUND("Year of contract should be within " + mmgProperties.getFranchiseYearOfContract()
						+ " years!!!");
		}
	}

	private void validateStartAndEndDate(Date startDate, Date endDate) {
		if (null == startDate)
			throw new NOT_FOUND("Please enter Start date");

		if (null == endDate)
			throw new NOT_FOUND("Please enter End date");

		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, -1);

		if (startDate.before(now.getTime()))
			throw new NOT_FOUND("Start date should be after or equal to today's date");

		if (startDate.after(endDate))
			throw new NOT_FOUND("Start date should be before end date");
	}

	public void validateProprietorName(String name, String positionName) throws Exception {
		if (CommonUtils.isNullCheck(name))
			throw new NOT_FOUND("Please mention the " + positionName + " Name!!");
		if (!dataValidation.isValidate(name, DataValidation.FIRSTNAME_PATTERN))
			throw new NOT_FOUND("Please mention the proper " + positionName + " Name");
	}

	private void validateCompanyAddress(double lat, double lng) throws Exception {
		if (lat <= 0)
			throw new NOT_FOUND("Please enter latitude");

		if (lng <= 0)
			throw new NOT_FOUND("Please enter longitude");
	}

	public void validatePanNumber(Profile profile, String profileId, boolean updateStatus, boolean isCommonProfile)
			throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "validatePanNumber in ProfileUtil")
				+ JsonUtil.toJsonString(profile));
		if (CommonUtils.isNullCheck(profile.getPanNumber()))
			throw new NOT_FOUND("Please enter the pan number");

		if (!dataValidation.isValidate(profile.getPanNumber(), DataValidation.PANNUMBER_PATTERN))
			throw new NOT_FOUND("Please mention the proper  pan Number !!");

		ProfileDomainV2 profileDomainV2 = profileDAOV2.getProfileDetByPanOrAadharNo(profile.getPanNumber(), null);
		if (null != profileDomainV2) {
			if (isCommonProfile) {
				if (profile.getPanNumber().equalsIgnoreCase(profile.getPanNumber()))
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(), "validatePanNumber in ProfileUtil while update")
							+ JsonUtil.toJsonString(profile));
			} else {
				if (updateStatus) {
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(), "validatePanNumber in ProfileUtil while update")
							+ JsonUtil.toJsonString(updateStatus));

					if (!profileId.equalsIgnoreCase(profileDomainV2.getId()))
						throw new PAN_NUMBER_ALREADY_EXIST();
				} else
					throw new PAN_NUMBER_ALREADY_EXIST();
			}
		}
	}

	public void validateBank(Profile profile, String profileId, boolean updateStatus, boolean isCommonProfile)
			throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "validateBank in ProfileUtil") + JsonUtil.toJsonString(profile));
		List<BankAccountModel> bankAccountList = profile.getBankAccount();
		if (null == bankAccountList)
			throw new NOT_FOUND("Please enter bank details");
		for (BankAccountModel bank : bankAccountList) {
			if (bank.getAccountNumber() != null) {
				if (bank.getAccountNumber().toString().length() < 9 || bank.getAccountNumber().toString().length() > 21)
					throw new NOT_FOUND("AccountNumber length should be within 9-21 digits");

				BankAccountDomain bankAccount = bankAccountDAO.checkAccountNumber(bank.getAccountNumber());
				if (null != bankAccount) {
					if (isCommonProfile) {
						Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
								SeverityTypes.INFORMATIONAL.ordinal(), "validateBank in ProfileUtil while update")
								+ JsonUtil.toJsonString(isCommonProfile));
					} else {
						if (updateStatus) {
							Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
									SeverityTypes.INFORMATIONAL.ordinal(), "validateBank in ProfileUtil while update")
									+ JsonUtil.toJsonString(updateStatus));
							if (!profileId.equalsIgnoreCase(bankAccount.getProfileId()))
								throw new ACCOUNT_NUMBER_ALREADY_EXIST();
						} else
							throw new ACCOUNT_NUMBER_ALREADY_EXIST();
					}
				}

			} else
				throw new NOT_FOUND("Please enter the account number");

			if (CommonUtils.isNullCheck(bank.getBranchName()))
				throw new NOT_FOUND("Please mention the branch Name");
			if (CommonUtils.isNullCheck(bank.getIfscCode()))
				throw new NOT_FOUND("Please mention the ifsccode");
			if (0 >= bank.getBankId())
				throw new NOT_FOUND("Please mention the bank Id");
		}
	}

	public void validateWarehouseRegistrationNo(String registrationNumber, String profileId, boolean updateStatus)
			throws Exception {
		if (CommonUtils.isNullCheck(registrationNumber))
			throw new NOT_FOUND("Please mention the registration number!!!");
		WareHouseDomain warehouseDomain = wareHouseDAO.checkWarehouseRegisterNumber(registrationNumber);
		if (null != warehouseDomain) {
			if (updateStatus) {
				if (profileId.equalsIgnoreCase(warehouseDomain.getProfileId()))
					throw new REGISTRATION_NUMBER_ALREADY_EXIST();
			} else
				throw new REGISTRATION_NUMBER_ALREADY_EXIST();
		}
	}

	public void validateFranchiseDetails(Profile profile, boolean isUpdate, boolean isCommonProfile,
			boolean isProfessional) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "validateFranchiseDetails in ProfileUtil while update")
				+ JsonUtil.toJsonString(profile));
		Franchise franchise = profile.getFranchise();
		if (null == franchise)
			throw new NOT_FOUND("Please mention the franchise details");

		validateCompanyName(franchise.getCompanyName());
		validatePanNumber(profile, profile.getId(), isUpdate, isCommonProfile);
		validateBank(profile, profile.getId(), isUpdate, isCommonProfile);
		validateYoc(franchise.getYearOfContract(), Integer.parseInt(Role.FRANCHISE.getCode()));
		validateStartAndEndDate(franchise.getStartDate(), franchise.getEndDate());

		if (!CommonUtils.isNullCheck(franchise.getGstNo()))
			validateGstNo(franchise.getGstNo(), profile.getId(), isUpdate, Role.FRANCHISE.getCode());
		else
			franchise.setGstNo(null);
		if (!CommonUtils.isNullCheck(franchise.getLicenseNo()))
			validateLicenseNumber(franchise.getLicenseNo(), profile.getId(), isUpdate, Role.FRANCHISE.getCode());
		else
			franchise.setLicenseNo(null);
		if (!CommonUtils.isNullCheck(franchise.getProprietorName())) {
			if (CommonUtils.isNullCheck(franchise.getProprietorName()))
				throw new NOT_FOUND("Please mention the  proprietor  Name!!");
			if (!dataValidation.isValidate(franchise.getProprietorName(), DataValidation.FIRSTNAME_PATTERN))
				throw new NOT_FOUND("Please mention the proper proprietor Name");
		}
		validateOrganisation(franchise.getOrganisationType());
		if (null != franchise.getLatitude() && null != franchise.getLongitude()) {
			validateCompanyAddress(franchise.getLatitude(), franchise.getLongitude());
		} else
			throw new NOT_FOUND("Please enter latitude and longitude");
		if (isProfessional) {
			if (CommonUtils.isNullCheck(franchise.getChannelPartnerId())) {
				if (!CommonUtils.isNullCheck(franchise.getBoardingEnquiryId()))
					validateEnquiry(franchise.getModifiedBy(), franchise.getReason());
			}
		}
	}

	private void validateEnquiry(String userId, String reason) throws Exception {
		if (CommonUtils.isNullCheck(userId))
			throw new NOT_FOUND("Please mention the userId!!");
		else {
			ProfileDomainV2 profileDomainV2 = profileDAOV2.getProfileDetails(userId);
			if (null == profileDomainV2)
				throw new NOT_FOUND("UserId not exists!!");
		}
		if (CommonUtils.isNullCheck(reason))
			throw new NOT_FOUND("Please mention the reason!!");
	}

	public boolean validateGstNo(String gstNumber, String profileId, boolean status, String role) throws Exception {
		if (!dataValidation.isValidate(gstNumber, DataValidation.GSTNUMBER_PATTERN))
			throw new NOT_FOUND("Please mention the proper  Gst Number !!");

		if (role.equals(Role.FRANCHISE.getCode())) {
			FranchiseDomainV2 franchiseDomain = franchiseDAO.checkGstNumber(gstNumber);
			if (franchiseDomain != null && status) {
				if (!profileId.equalsIgnoreCase(franchiseDomain.getProfileId()))
					throw new GST_NUMBER_ALREADY_EXIST();
			} else if (franchiseDomain != null)
				throw new GST_NUMBER_ALREADY_EXIST();
		}
		if (role.equals(Role.FLEET_OPERATOR.getCode())) {
			FleetOperatorDomain fleetOperatorDomain = fleetOperatorDAO.checkGstNumber(gstNumber);
			if (fleetOperatorDomain != null && status) {
				if (!profileId.equalsIgnoreCase(fleetOperatorDomain.getProfileId()))
					throw new GST_NUMBER_ALREADY_EXIST();
			} else if (fleetOperatorDomain != null)
				throw new GST_NUMBER_ALREADY_EXIST();
		}

		if (role.equals(Role.ENTERPRISE.getCode())) {
			EnterpriseDomain enterpriseDomain = enterpriseDAO.checkGstNumber(gstNumber);
			if (enterpriseDomain != null && status) {
				if (!profileId.equalsIgnoreCase(enterpriseDomain.getProfileId()))
					throw new GST_NUMBER_ALREADY_EXIST();
			} else if (enterpriseDomain != null)
				throw new GST_NUMBER_ALREADY_EXIST();
		}

		if (role.equals(Role.CUSTOMER.getCode())) {
			CustomerDomain customerDomain = customerDAO.checkGstNumber(gstNumber);
			if (customerDomain != null && status) {
				if (!profileId.equalsIgnoreCase(customerDomain.getProfileId()))
					throw new GST_NUMBER_ALREADY_EXIST();
			} else if (customerDomain != null)
				throw new GST_NUMBER_ALREADY_EXIST();
		}
		return true;
	}

	public boolean validateLicenseNumber(String licenseNumber, String profileId, boolean updateStatus, String role)
			throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"validateLicenseNumber in ProfileUtil with licenseNumber" + JsonUtil.toJsonString(licenseNumber)
						+ ",role" + JsonUtil.toJsonString(role) + ",status" + JsonUtil.toJsonString(updateStatus)));
		if (role.equals(Role.FRANCHISE.getCode())) {
			FranchiseDomainV2 franchiseDomain = franchiseDAO.checkLicenseNumber(licenseNumber);
			if (franchiseDomain != null && updateStatus) {
				if (!profileId.equalsIgnoreCase(franchiseDomain.getProfileId()))
					throw new LICENSE_NUMBER_ALREADY_EXIST();
			} else if (franchiseDomain != null)
				throw new LICENSE_NUMBER_ALREADY_EXIST();
		}
		if (role.equals(Role.ENTERPRISE.getCode())) {
			EnterpriseDomain enterpriseDomain = enterpriseDAO.checkLicenseNumber(licenseNumber);
			if (enterpriseDomain != null && updateStatus) {
				if (!profileId.equalsIgnoreCase(enterpriseDomain.getProfileId()))
					throw new LICENSE_NUMBER_ALREADY_EXIST();
			} else if (enterpriseDomain != null)
				throw new LICENSE_NUMBER_ALREADY_EXIST();
		}
		return true;
	}

	private void validateOrganisation(int type) throws Exception {
		if (0 >= type)
			throw new NOT_FOUND("Please mention the organisation Type");
	}

	public void validateFleetDetails(Profile profile, boolean isUpdate, boolean isCommonProfile, boolean isProfessional)
			throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "validateFleetDetails in ProfileUtil with isUpdate"
						+ JsonUtil.toJsonString(isUpdate) + ",profile" + JsonUtil.toJsonString(profile)));
		FleetOperator fleetOperator = profile.getFleet();
		if (null == fleetOperator)
			throw new NOT_FOUND("Please mention the fleet details");

		validateCompanyName(fleetOperator.getCompanyName());
		validateYoc(fleetOperator.getYearOfContract(), profile.getRoleId());
		validateStartAndEndDate(fleetOperator.getStartDate(), fleetOperator.getEndDate());
		if (null != fleetOperator.getLatitude() && null != fleetOperator.getLongitude()) {
			validateCompanyAddress(fleetOperator.getLatitude(), fleetOperator.getLongitude());
		} else
			throw new NOT_FOUND("Please enter latitude and longitude");
		if (CommonUtils.isNullCheck(fleetOperator.getFranchiseId()))
			throw new NOT_FOUND("Please enter franchise id");

		validatePanNumber(profile, profile.getId(), isUpdate, isCommonProfile);
		validateBank(profile, profile.getId(), isUpdate, isCommonProfile);
		if (!CommonUtils.isNullCheck(fleetOperator.getGstNo())) {
			validateGstNo(fleetOperator.getGstNo(), profile.getId(), isUpdate, Role.FLEET_OPERATOR.getCode());
		} else
			throw new NOT_FOUND("Please mention the gst number!!!");
		if (!isProfessional) {
			if (!CommonUtils.isNullCheck(fleetOperator.getBoardingEnquiryId()))
				validateEnquiry(fleetOperator.getModifiedBy(), fleetOperator.getReason());
		}
	}

	public void validateEnterpriseDetails(Profile profile, boolean isUpdate, boolean isCommonProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "validateEnterpriseDetails in ProfileUtil with isUpdate"
						+ JsonUtil.toJsonString(isUpdate) + ",profile" + JsonUtil.toJsonString(profile)));
		Enterprise enterprise = profile.getEnterprise();
		if (null == enterprise)
			throw new NOT_FOUND("Please mention the enterprise details");

		validateCompanyName(enterprise.getCompanyName());

		if (!CommonUtils.isNullCheck(enterprise.getGstNo())) {
			validateGstNo(enterprise.getGstNo(), profile.getId(), isUpdate, Role.ENTERPRISE.getCode());
		} else
			throw new NOT_FOUND("Please mention the gst number!!!");

		if (!CommonUtils.isNullCheck(enterprise.getLicenseNo()))
			validateLicenseNumber(enterprise.getLicenseNo(), profile.getId(), isUpdate, Role.ENTERPRISE.getCode());
		else
			throw new NOT_FOUND("Please mention the license number!!!");
		if (!CommonUtils.isNullCheck(enterprise.getEntrepreneurName()))
			validateProprietorName(enterprise.getEntrepreneurName(), "Entrepreneur");
		validateOrganisation(enterprise.getOrganisationType());
		validateYoc(enterprise.getYearOfContract(), profile.getRoleId());
		validateStartAndEndDate(enterprise.getStartDate(), enterprise.getEndDate());
		if (null != enterprise.getLatitude() && null != enterprise.getLongitude()) {
			validateCompanyAddress(enterprise.getLatitude(), enterprise.getLongitude());
		} else
			throw new NOT_FOUND("Please enter latitude and longitude");

		validatePanNumber(profile, profile.getId(), isUpdate, isCommonProfile);
		validateBank(profile, profile.getId(), isUpdate, isCommonProfile);
	}

	private void validateDriverDetails(Profile profile, boolean isUpdate, boolean isCommonProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "validateDriverDetails in ProfileUtil with isUpdate"
						+ JsonUtil.toJsonString(isUpdate) + ",profile" + JsonUtil.toJsonString(profile)));
		Driver driver = profile.getDriver();
		if (null == driver)
			throw new NOT_FOUND("Please enter the driverdetails");
		if (0 >= driver.getBloodId())
			throw new NOT_FOUND("Please enter the bloodgroup");
		if (0 >= driver.getDriverTypeId())
			throw new NOT_FOUND("Please enter the driverType");

		if (!CommonUtils.isNullCheck(driver.getCompanyName())) {
			if (!dataValidation.isValidate(driver.getCompanyName(), DataValidation.FIRSTNAME_PATTERN))
				throw new NOT_FOUND("Please mention the proper company Name");
		}

		if (CommonUtils.isNullCheck(driver.getDlNumber()))
			throw new NOT_FOUND("Please enter the dl number!!");
		else
			franchiseCommonService.validateDl(driver, profile.getId(), isUpdate);

		if (null == driver.getDlIssueDate())
			throw new NOT_FOUND("Please enter the dlDate");

		if (null == driver.getDlExpiryDate())
			throw new NOT_FOUND("Please enter the dl expiry Date");

		if (!CommonUtils.isNullCheck(driver.getBadgeNumber())) {
			franchiseCommonService.validateBadge(driver, profile.getId(), isUpdate);
			if (null == driver.getBadgeExpiryDate())
				throw new NOT_FOUND("Please enter the badge expiry Date");
		}

		if (0 >= driver.getLicenceCategory())
			throw new NOT_FOUND("Please enter the license category");

		if (CommonUtils.isNullCheck(driver.getRefferenceCode()))
			driver.setRefferenceCode(null);
		if (CommonUtils.isNullCheck(driver.getIdentificationMark()))
			driver.setIdentificationMark(null);
		if (CommonUtils.isNullCheck(driver.getCompanyName()))
			driver.setCompanyName(null);

		validatePanNumber(profile, profile.getId(), isUpdate, isCommonProfile);
		validateBank(profile, profile.getId(), isUpdate, isCommonProfile);
	}

	public boolean validateAadharNumber(BigInteger aadharNumber, String profileId, boolean updateStatus,
			boolean isCommonProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "validateAadharNumber in ProfileUtil with aadharNumber"
						+ JsonUtil.toJsonString(aadharNumber) + ",updateStatus" + JsonUtil.toJsonString(updateStatus)));
		ProfileDomainV2 profileDomainV2 = profileDAOV2.getProfileDetByPanOrAadharNo(null, aadharNumber);
		if (null != profileDomainV2) {
			if (isCommonProfile) {
				if (profileDomainV2.getAadharNumber() == aadharNumber)
					return true;
			} else {
				if (updateStatus) {
					if (!profileId.equalsIgnoreCase(profileDomainV2.getId()))
						throw new AADHAR_NUMBER_ALREADY_EXIST();
				} else
					throw new AADHAR_NUMBER_ALREADY_EXIST();
			}
		}
		return true;
	}

	public ProfileDomainV2 validateUserExistsByMobileOrEmail(Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"validateUserExistsByMobileOrEmail in ProfileUtil" + JsonUtil.toJsonString(profile)));
		ProfileDomainV2 profileDomainV2 = null;

		if (null != profile.getMobileNumber()) {
			if (null != profile.getEmailId()) {
				profileDomainV2 = profileDAOV2.getProfileByMobileNumberOrEmailId(null, profile.getEmailId(),
						profile.getRoleId());
				if (null != profileDomainV2)
					throw new USER_ALREADY_EXIST(profile.getEmailId());
			}

			profileDomainV2 = profileDAOV2.getProfileByMobileNumberOrEmailId(profile.getMobileNumber(),
					profile.getEmailId(), profile.getRoleId());
			if (null != profileDomainV2) {
				if (!profileDomainV2.getIsActive())
					throw new USER_ALREADY_EXIST(profile.getMobileNumber().toString(), true);
				else
					throw new USER_ALREADY_EXIST(profile.getMobileNumber().toString());
			} else {
				profileDomainV2 = profileDAOV2.getProfileByMobileNumberOrEmailId(profile.getMobileNumber(),
						profile.getEmailId(), 0);
			}

			/*
			 * else { List<ProfileDomain> profileDomainList = profileDAOV2
			 * .getProfileByMobileNumberOrEmailId(profileModel.getMobileNumber(),
			 * profileModel.getEmailId()); if (profileDomainList.size() > 0) { throw new
			 * USER_EXIST_DIFF_ROLE(profileModel.getMobileNumber().toString()); } }
			 */

		}
		return profileDomainV2;
	}

	@Transactional(rollbackFor = Exception.class)
	public Profile saveProfileRole(Profile profile, ProfileDomainV2 existingProfileDomain) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"saveProfileRole in ProfileUtil with profile" + JsonUtil.toJsonString(profile)
						+ " and existingProfileDomain" + JsonUtil.toJsonString(existingProfileDomain)));
		ProfileRoleDomain profileRoleDomain = new ProfileRoleDomain();
		ProfileDomainV2 profileDomainV2 = new ProfileDomainV2();
		List<ProfileDomainV2> profileDomainList = profileDAOV2
				.getProfileByMobileNumberOrEmailId(profile.getMobileNumber(), profile.getEmailId());
		if (profile.getRoleId() != Integer.parseInt(Role.CUSTOMER.getCode())
				&& (profile.getRoleId() != Integer.parseInt(Role.OWNER.getCode())
						&& profile.getRoleId() != Integer.parseInt(Role.TIEUPS.getCode())))
			profile.setIsActive(false);
		else
			profile.setIsActive(true);
		if ((null == existingProfileDomain.getId() || existingProfileDomain.getId().isEmpty())
				&& profileDomainList.isEmpty()) {
			profile.setId(CommonUtils.generateRandomId());
			profile.setDefaultRoleId(profile.getRoleId());
			BeanUtils.copyProperties(profile, profileDomainV2);
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(), "Savind data into profile in saveProfileRole on ProfileUtil"
							+ JsonUtil.toJsonString(profileDomainV2)));
			profileDomainV2 = profileDAOV2.save(profileDomainV2);

			if (null != profile.getAddress()) {
				for (Address address : profile.getAddress()) {
					AddressDomain addressDomain = new AddressDomain();
					BeanUtils.copyProperties(address, addressDomain);
					addressDomain.setUuid(CommonUtils.generateRandomId());
					addressDomain.setRoleId(profile.getRoleId());
					addressDomain.setProfileId(profile.getId());
					addressDomain.setIsActive(true);
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(),
							"getAddressByvalidateAddress in saveProfileRole on ProfileUtil"
									+ JsonUtil.toJsonString(addressDomain)));

					AddressDomain addr = addressDAO.getAddressByvalidateAddress(addressDomain);
					if (null == addr) {
						Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
								SeverityTypes.INFORMATIONAL.ordinal(), "Save address in saveProfileRole on ProfileUtil"
										+ JsonUtil.toJsonString(addressDomain)));
						addressDAO.save(addressDomain);
					}
				}
			}

			// BeanUtils.copyProperties(profileDomainV2, profileRoleDomain);
		} else {
			if (!profileDomainList.isEmpty()) {
				if (profile.getRoleId() != Integer.parseInt(Role.CUSTOMER.getCode())
						&& (profile.getRoleId() != Integer.parseInt(Role.OWNER.getCode())
								&& profile.getRoleId() != Integer.parseInt(Role.TIEUPS.getCode()))) {
					profile.setId(profileDomainList.get(0).getId());
					BeanUtils.copyProperties(profile, profileDomainV2);
					profileDAOV2.updateCommonProfile(profileDomainV2);
				}
				BeanUtils.copyProperties(profileDomainList.get(0), profileDomainV2);
				if (null != profile.getAddress()) {
					for (Address address : profile.getAddress()) {
						AddressDomain addressDomain = new AddressDomain();
						BeanUtils.copyProperties(address, addressDomain);
						addressDomain.setUuid(CommonUtils.generateRandomId());
						addressDomain.setRoleId(profile.getRoleId());
						addressDomain.setProfileId(profileDomainV2.getId());
						addressDomain.setIsActive(true);
						Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
								SeverityTypes.INFORMATIONAL.ordinal(),
								"getAddressByvalidateAddress in saveProfileRole on ProfileUtil"
										+ JsonUtil.toJsonString(addressDomain)));

//						AddressDomain existingAddress = addressDAO.getAddressByTypeAndProfileIdWithRoleId(
//								address.getType(), profileDomainV2.getId(), profile.getRoleId());
						// check Address based on role for multiple role so passing role
						List<AddressDomain> existingAddress = addressDAO.getAddressByTypeAndProfileIdWithRoleId(
								address.getType(), profileDomainV2.getId(), profile.getRoleId());
						if (existingAddress.isEmpty()) {
							AddressDomain addr = addressDAO.getAddressByvalidateAddress(addressDomain);
							if (null == addr) {
								Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(
										ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.INFORMATIONAL.ordinal(),
										"Save address in saveProfileRole on ProfileUtil"
												+ JsonUtil.toJsonString(addressDomain)));
//								List<AddressDomain> addrDomainList = addressDAO
//										.getAddressListsByProfileId(profileDomainV2.getId());
								// we need to check with role
//								List<AddressDomain> addrDomainList = addressDAO
//										.getAddressListsByProfileId(profileDomainV2.getId(),profile.getRoleId());
//								if (!addrDomainList.isEmpty()) {
//									existingAddress = new AddressDomain();
//									for (AddressDomain addresses : addrDomainList) {
//										if (profile.getRoleId() == Integer.parseInt(Role.CUSTOMER.getCode())
//												|| profile.getRoleId() == Integer.parseInt(Role.DRIVER.getCode())
//												|| profile.getRoleId() == Integer.parseInt(Role.FIELDOFFICER.getCode())
//												|| profile.getRoleId() == Integer
//														.parseInt(Role.COORDINATOR.getCode())) {
//											if (addresses.getType().equalsIgnoreCase(AddressType.HOME.name())) 
//												BeanUtils.copyProperties(addresses, existingAddress);
//										} else {
//											if (addresses.getType().equalsIgnoreCase(AddressType.OFFICE.name())) 
//												BeanUtils.copyProperties(addresses, existingAddress);
//										}
//									}
//									if (existingAddress.getType() == null)
//										addressDAO.save(addressDomain);
//								}
								addressDAO.save(addressDomain);
							}
						}
					}
				}
			} else
				BeanUtils.copyProperties(profileDomainV2, existingProfileDomain);
			profileDomainV2.setIsActive(profile.getIsActive());
		}

		profileRoleDomain.setUuid(CommonUtils.generateRandomId());
		profileRoleDomain.setProfileId(profileDomainV2.getId());
		profileRoleDomain.setRoleId(profile.getRoleId());
		profileRoleDomain.setIsActive(profileDomainV2.getIsActive());
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"save into profilerole in saveProfileRole on ProfileUtil" + JsonUtil.toJsonString(profileRoleDomain)));
		profileRoleDAO.save(profileRoleDomain);

		Profile profileId = new Profile();
		profileId.setId(profileRoleDomain.getProfileId());
		/*
		 * if (!profileDomainList.isEmpty())
		 * profileId.setWarningMessage("User already Registered in this role : " +
		 * Role.getRole(profileDomainList.get(0).getRoleId() + "") + ", New role:" +
		 * Role.getRole(profileModel.getRoleId() + "") + " also assigned"); else
		 * profileId.setWarningMessage(null);
		 */
		return profileId;
	}

	public boolean saveBank(Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "saveBank in  ProfileUtil" + JsonUtil.toJsonString(profile)));
		BankAccountDomain bankAccountDomain = new BankAccountDomain();
		List<BankAccountModel> bankAccountModelList = profile.getBankAccount();
		if (null != bankAccountModelList) {
			for (BankAccountModel bankAccountmodel : bankAccountModelList) {
				BeanUtils.copyProperties(bankAccountmodel, bankAccountDomain);
				bankAccountDomain.setProfileId(profile.getId());
				BankAccountDomain bankAccount = bankAccountDAO.checkAccountNumber(bankAccountmodel.getAccountNumber());
				if (null != bankAccount)
					throw new ACCOUNT_NUMBER_ALREADY_EXIST();
				BankAccountDomain bankAccountsDomain = bankAccountDAO
						.getBankAccountValidateByBankAccount(bankAccountDomain);
				if (null == bankAccountsDomain) {
					bankAccountDomain.setUuid(CommonUtils.generateRandomId());
					List<BankAccountDomain> bankDetails = bankAccountDAO.getAllBankAccountsByProfileId(profile.getId(),
							null);
					if (!bankDetails.isEmpty()) {
						bankAccountDomain.setStatus(false);
					} else
						bankAccountDomain.setStatus(true);
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(),
							"saveBank data into table in  ProfileUtil" + JsonUtil.toJsonString(bankAccountDomain)));
					bankAccountDAO.save(bankAccountDomain);
				}
			}
		}

		return true;
	}

	public void validateProfile(Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"validateProfile in  ProfileUtil" + JsonUtil.toJsonString(profile)));
		ProfileDomainV2 mobileObj = profileDAOV2.getProfileByMobileNumberOrEmailId(profile.getMobileNumber(),
				profile.getEmailId(), profile.getRoleId());
		if (null == mobileObj)
			throw new USER_NOT_EXISTS();
		if (!mobileObj.getId().equalsIgnoreCase(profile.getId()))
			throw new PROFILE_NOT_FOUND();
	}

	public Profile validateProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"validateProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		if (CommonUtils.isNullCheck(profileId))
			throw new NOT_FOUND("ProfileId cannot be empty");

		ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetails(profileId);
		if (null == profileDomain)
			throw new PROFILE_NOT_FOUND();

		Profile profile = new Profile();
		BeanUtils.copyProperties(profileDomain, profile);

		return profile;
	}

	public void updateAddress(Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"updateAddress in  ProfileUtil" + JsonUtil.toJsonString(profile)));
		List<Address> addressList = profile.getAddress();
		if (null != addressList) {
			for (Address address : addressList) {
				AddressDomain addressDomain = new AddressDomain();
				BeanUtils.copyProperties(address, addressDomain);
				if (!CommonUtils.isNullCheck(addressDomain.getUuid()))
					addressDAO.update(addressDomain);
				else {
					addressDomain.setUuid(CommonUtils.generateRandomId());
					addressDomain.setProfileId(profile.getId());
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(),
							"save address in updateAddress in  ProfileUtil" + JsonUtil.toJsonString(addressDomain)));
					addressDAO.save(addressDomain);
				}
			}
		}
	}

	public List<BankAccountDomain> getBankAccountDetails(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getBankAccountDetails in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		List<BankAccountDomain> bankAccountDomainList = bankAccountDAO.getBankAccounts(profileId, true);
		for (BankAccountDomain bankAccountDomain : bankAccountDomainList) {
			bankAccountDomain.setBankName(adminCommonService.bankNameById(bankAccountDomain.getBankId()));
		}
		return bankAccountDomainList;
	}

	public Customer getCustomerByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getCustomerByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		CustomerDomain customerDomain = customerDAO.getCustomerByProfileId(profileId);
		Customer customer = null;
		if (null != customerDomain) {
			customer = new Customer();
			BeanUtils.copyProperties(customerDomain, customer);
			String typeName;
			try {
				typeName = adminCommonService.customerTypeById(customer.getCustomerTypeId());
			} catch (Exception e) {
				typeName = null;
			}
			customer.setCustomerTypeName(typeName);
		}
		return customer;
	}

	public BDM getBDMByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getBDMByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		BDMDomain bdmDomain = bdmDAO.getBDMByProfileId(profileId);
		BDM bdm = null;
		if (null != bdmDomain) {
			bdm = new BDM();
			BeanUtils.copyProperties(bdmDomain, bdm);
		}
		return bdm;
	}

	public BDO getBDOByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getBDOByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		BDODomain bdoDomain = bdoDAO.getBDOByProfileId(profileId);
		BDO bdo = null;
		if (null != bdoDomain) {
			bdo = new BDO();
			BeanUtils.copyProperties(bdoDomain, bdo);
		}
		return bdo;
	}

	public ChannelPartner getChannelePartnerByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getChannelePartnerByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		ChannelPartnerDomain channelPartnerDomain = channelPartnerDAO.getChannelPartnerByProfileId(profileId);
		ChannelPartner channelPartner = null;
		if (null != channelPartnerDomain) {
			channelPartner = new ChannelPartner();
			BeanUtils.copyProperties(channelPartnerDomain, channelPartner);
		}
		return channelPartner;
	}

	public Coordinator getCoordinatorByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getCoordinatorByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		CoordinatorDomain coordinatorDomain = coordinatorDAO.getCoordinatorByProfileId(profileId);
		Coordinator coordinator = null;
		if (null != coordinatorDomain) {
			coordinator = new Coordinator();
			BeanUtils.copyProperties(coordinatorDomain, coordinator);
		}
		return coordinator;
	}

	public FieldOfficer getFieldOfficerByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getFieldOfficerByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		FieldOfficerDomain fieldOffcierDomain = fieldOfficerDAO.getFieldOffcierByProfileId(profileId);
		FieldOfficer fieldOffcier = null;
		if (null != fieldOffcierDomain) {
			fieldOffcier = new FieldOfficer();
			BeanUtils.copyProperties(fieldOffcierDomain, fieldOffcier);
		}
		return fieldOffcier;
	}

	public OperationalTeam getOperationalTeamByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getOperationalTeamByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		OperationalTeamDomain operationalTeamDomain = operationalTeamDAO.getOperationalTeamByProfileId(profileId);
		OperationalTeam operationalTeam = null;
		if (null != operationalTeamDomain) {
			operationalTeam = new OperationalTeam();
			BeanUtils.copyProperties(operationalTeamDomain, operationalTeam);
		}
		return operationalTeam;
	}

	public FleetOperator getFleetOperatorByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getFleetOperatorByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		FleetOperatorDomain fleetOperatorDomain = fleetOperatorDAO.getFleetByProfileId(profileId);
		FleetOperator fleetOperatorModel = null;
		if (null != fleetOperatorDomain) {
			fleetOperatorModel = new FleetOperator();
			BeanUtils.copyProperties(fleetOperatorDomain, fleetOperatorModel);
		}
		return fleetOperatorModel;
	}

	public WareHouse getWareHouseByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getWareHouseByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		WareHouseDomain wareHouseDomain = wareHouseDAO.getWarehouseByProfileId(profileId);
		WareHouse wareHouseModelV2 = null;
		if (null != wareHouseDomain) {
			wareHouseModelV2 = new WareHouse();
			BeanUtils.copyProperties(wareHouseDomain, wareHouseModelV2);
		}
		return wareHouseModelV2;
	}

	public Franchise getFranchiseByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getFranchiseByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		FranchiseDomainV2 franchiseDomain = franchiseDAO.getFranchiseByProfileId(profileId);
		Franchise franchiseModel = null;
		if (null != franchiseDomain) {
			franchiseDomain.setOrganizationName(
					adminCommonService.organizationNameById(franchiseDomain.getOrganisationType()));
			franchiseModel = new Franchise();
			BeanUtils.copyProperties(franchiseDomain, franchiseModel);
		}
		return franchiseModel;
	}

	public Enterprise getEnterpriseByProfileId(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getEnterpriseByProfileId in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		EnterpriseDomain enterpriseDomain = enterpriseDAO.getEnterpriseByProfileId(profileId);
		Enterprise enterpriseModel = null;
		if (null != enterpriseDomain) {
			enterpriseDomain.setOrganizationName(
					adminCommonService.organizationNameById(enterpriseDomain.getOrganisationType()));
			enterpriseModel = new Enterprise();
			BeanUtils.copyProperties(enterpriseDomain, enterpriseModel);
		}
		return enterpriseModel;
	}

	public List<AddressDomain> getAddressDetails(String profileId, int roleId, boolean isActive) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getAddressDetails in  ProfileUtil" + JsonUtil.toJsonString(profileId)));
		List<AddressDomain> addressDomainList = addressDAO.getAddressListsByProfileId(profileId, roleId, isActive);
		if (!addressDomainList.isEmpty())
			for (AddressDomain address : addressDomainList) {
				address.setCityName(adminCommonService.cityNameById(address.getCityId()));
				address.setStateName(adminCommonService.stateNameById(address.getStateId()));
				address.setCountryName(adminCommonService.countryNameById(address.getCountryId()));
			}
		return addressDomainList;
	}

	public List<Profile> getAddressList(List<Profile> profileList) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getAddressList in  ProfileUtil" + JsonUtil.toJsonString(profileList)));
		Map<Integer, City> cityMap = adminCommonService.getAllCities();
		for (Profile profile : profileList) {
			List<AddressDomain> addressDomainList = addressDAO.getAddressListsByProfileId(profile.getId(),
					profile.getRoleId(), true);
			if (!addressDomainList.isEmpty())
				for (AddressDomain address : addressDomainList) {
					address.setCityName(cityMap.get(address.getCityId()).getName());
					// address.setCityName(adminCommonService.cityNameById(address.getCityId()));
				}
			List<Address> addressList = new ArrayList<>();
			addressList = addressMapper.entityList(addressDomainList);
			profile.setAddress(addressList);
		}

		return profileList;
	}

	public List<Profile> getCustomerListWithType(List<Profile> profileList) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getCustomerListWithType in  ProfileUtil" + JsonUtil.toJsonString(profileList)));
		Map<Integer, CustomerTypeModel> customerTypeMap = adminCommonService.getAllCustomerType();
		for (Profile profile : profileList) {
			int customerType = profile.getCustomer().getCustomerTypeId();
			Customer customer = profile.getCustomer();
			customer.setCustomerTypeName(customerTypeMap.get(customerType).getTypeName());
			profile.setCustomer(customer);
		}
		return profileList;
	}

	public List<Address> getAddress(String addressType, String profileId, int roleId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getAddress in  ProfileUtil based on addressType "
						+ JsonUtil.toJsonString(addressType) + "and profileId" + JsonUtil.toJsonString(profileId)));
		Map<String, Address> addressMap = new HashMap<String, Address>();
		Map<Integer, City> cityMap = adminCommonService.getAllCities();
		Map<Integer, State> stateMap = adminCommonService.getAllStates(Constants.INDIA);
		Address addr = new Address();
		List<Address> address = null;
		AddressDomain addressDomain = addressDAO.getActiveAddressByTypeAndProfileIdWithRoleId(addressType, profileId,
				roleId);
		if (addressDomain != null) {
			address = new ArrayList<Address>();
			addressDomain.setCityName(cityMap.get(addressDomain.getCityId()).getName());
			addressDomain.setStateName(stateMap.get(addressDomain.getStateId()).getName());
			BeanUtils.copyProperties(addressDomain, addr);
			addressMap.put(profileId, addr);
			address = addressMap.values().stream().collect(Collectors.toList());
		}
		return address;
	}

	public List<Profile> getDriverList(List<Profile> profileList, String requestedBy) throws Exception {
		if (profileList.size() > 0) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					"getDriverList in Profileutil" + JsonUtil.toJsonString(profileList) + "and requestedBy"
							+ JsonUtil.toJsonString(requestedBy)));

			Map<String, Driver> driverProfileMap = franchiseCommonService.getAllDriverList(requestedBy);
			Driver driverModelV2 = null;
			Iterator<Profile> profileIterator = profileList.iterator();
			while (profileIterator.hasNext()) {
				Profile profile = (Profile) profileIterator.next();
				if (driverProfileMap.containsKey(profile.getId())) {
					driverModelV2 = (Driver) driverProfileMap.get(profile.getId());
					profile.setDriver(driverModelV2);
				} else {
					profileIterator.remove();
				}
			}
		}
		return profileList;
	}

	public String getAddressType(Role role) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getAddressType in Profileutil" + JsonUtil.toJsonString(role)));
		String addressType = null;
		if (role.getCode() == Role.DRIVER.getCode() || role.getCode() == Role.CUSTOMER.getCode()
				|| role.getCode() == Role.FIELDOFFICER.getCode()
//				|| role.getCode() == Role.COORDINATOR.getCode()
		)
			addressType = AddressType.HOME.name();
		else
			addressType = AddressType.OFFICE.name();

		return addressType;
	}

	public List<Profile> getDriverListByFranchiseId(List<Profile> profileList, String franchiseId,
			Map<String, String> reqParam, String status, Map<String, Driver> driverProfileMap) throws Exception {
		if (profileList.size() > 0) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					"getDriverList in Profileutil" + JsonUtil.toJsonString(profileList) + "and franchiseId"
							+ JsonUtil.toJsonString(franchiseId)));
			if (null == driverProfileMap)
				driverProfileMap = getDriverMapsByFranchiseId(franchiseId, reqParam, status);
			Driver driverModelV2 = null;
			Iterator<Profile> profileIterator = profileList.iterator();
			while (profileIterator.hasNext()) {
				Profile profile = (Profile) profileIterator.next();
				if (null != driverProfileMap) {
					if (driverProfileMap.containsKey(profile.getId())) {
						driverModelV2 = (Driver) driverProfileMap.get(profile.getId());
						driverModelV2
								.setFranchiseName(franchiseDAO.getFranchiseByFranchiseId(franchiseId).getCompanyName());
						profile.setDriver(driverModelV2);
					} else {
						profileIterator.remove();
					}
				}
			}
		}
		return profileList;
	}

	public Map<String, Driver> getDriverMapsByFranchiseId(String franchiseId, Map<String, String> reqParam,
			String status) throws Exception {
		Map<String, Driver> driverProfileMap = franchiseCommonService.getDriversByFranchiseId(franchiseId, reqParam,
				status);
		return driverProfileMap;
	}

	public void getProfileRoleDetails(int roleId, Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getProfileRoleDetails in Profileutil"
						+ JsonUtil.toJsonString(roleId) + "and profile" + JsonUtil.toJsonString(profile)));
		if (roleId != 0) {
			Role role = Role.getRole(roleId + "");
			if (null == role)
				throw new ROLE_NOT_FOUND(roleId + "");

			switch (role) {
			case CUSTOMER:
				profile.setCustomer(getCustomerByProfileId(profile.getId()));
				break;
			// case OWNER:
			// case TIEUPS:
			case BDM:
				profile.setBdm(getBDMByProfileId(profile.getId()));
				break;
			case BDO:
				profile.setBdo(getBDOByProfileId(profile.getId()));
				break;
			case CHANNEL_PARTNER:
				profile.setChannelPartner(getChannelePartnerByProfileId(profile.getId()));
				break;
			case COORDINATOR:
				profile.setCoordinator(getCoordinatorByProfileId(profile.getId()));
				break;
			case FIELDOFFICER:
				profile.setFieldOfficer(getFieldOfficerByProfileId(profile.getId()));
				break;
			case OPERATIONAL_TEAM:
				profile.setOperationalTeam(getOperationalTeamByProfileId(profile.getId()));
				break;
			case FLEET_OPERATOR:
				profile.setFleet(getFleetOperatorByProfileId(profile.getId()));
				break;
			case WAREHOUSE:
				profile.setWarehouse(getWareHouseByProfileId(profile.getId()));
				break;
			case DRIVER:
				profile.setDriver(franchiseCommonService.getDriverDetails(profile.getId()));
				break;
			case FRANCHISE:
				profile.setFranchise(getFranchiseByProfileId(profile.getId()));
				break;
			case ENTERPRISE:
				profile.setEnterprise(getEnterpriseByProfileId(profile.getId()));
				break;
			default:
				break;
			}
		}
	}

	public void copyProfileForUpdate(Profile profile, Profile existingProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"copyProfileForUpdate in Profileutil with profile" + JsonUtil.toJsonString(profile)
						+ "and existingProfile" + JsonUtil.toJsonString(existingProfile)));
		if (profile.getRoleId() > 0)
			existingProfile.setRoleId(profile.getRoleId());
		if (!CommonUtils.isNullCheck(profile.getFirstName()))
			existingProfile.setFirstName(profile.getFirstName());
		if (!CommonUtils.isNullCheck(profile.getLastName()))
			existingProfile.setLastName(profile.getLastName());
		if (null != profile.getDob())
			existingProfile.setDob(profile.getDob());
		if (profile.getGenderId() > 0)
			existingProfile.setGenderId(profile.getGenderId());
		if (!CommonUtils.isNullCheck(profile.getAlternativeNumber()))
			existingProfile.setAlternativeNumber(profile.getAlternativeNumber());
		if (profile.getAadharNumber() != null)
			existingProfile.setAadharNumber(profile.getAadharNumber());
		if (!CommonUtils.isNullCheck(profile.getPanNumber()))
			existingProfile.setPanNumber(profile.getPanNumber());
		if (!CommonUtils.isNullCheck(profile.getModifiedBy()))
			existingProfile.setModifiedBy(profile.getModifiedBy());
		if (!CommonUtils.isNullCheck(profile.getReason()))
			existingProfile.setReason(profile.getReason());
		if (profile.getModifiedByRoleId() > 0)
			existingProfile.setModifiedByRoleId(profile.getModifiedByRoleId());

		copyAddressForUpdate(profile, existingProfile);
		copyProfileRoleDataForUpdate(profile, existingProfile);
	}

	private void copyAddressForUpdate(Profile profile, Profile existingProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"copyAddressForUpdate in Profileutil with profile" + JsonUtil.toJsonString(profile)
						+ "and existingProfile" + JsonUtil.toJsonString(existingProfile)));
		List<Address> addressList = profile.getAddress();
		List<Address> existingAddressList = existingProfile.getAddress();

		if (null != addressList && null != existingAddressList) {
			for (Address address : addressList) {
				if (!CommonUtils.isNullCheck(address.getUuid())) {
					if (existingAddressList.contains(address)) {
						int index = existingAddressList.indexOf(address);
						if (index != -1) {
							if (!CommonUtils.isNullCheck(address.getAddress1()))
								existingAddressList.get(index).setAddress1(address.getAddress1());
							if (address.getCountryId() > 0)
								existingAddressList.get(index).setCountryId(address.getCountryId());
							if (address.getCityId() > 0)
								existingAddressList.get(index).setCityId(address.getCityId());
							if (address.getStateId() > 0)
								existingAddressList.get(index).setStateId(address.getStateId());
							if (null != address.getPincode())
								existingAddressList.get(index).setPincode(address.getPincode());
						}
					}
				} else
					existingAddressList.add(address);
			}
		}

		if (null == existingAddressList) {
			existingAddressList = addressList;
		}

		existingProfile.setAddress(existingAddressList);
	}

	private void copyProfileRoleDataForUpdate(Profile profile, Profile existingProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"copyProfileRoleDataForUpdate in Profileutil with profile" + JsonUtil.toJsonString(profile)
						+ "and existingProfile" + JsonUtil.toJsonString(existingProfile)));
		Role role = Role.getRole(profile.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");

		switch (role) {
		case CUSTOMER:
			Customer customer = profile.getCustomer();
			if (null != customer) {
				if (!CommonUtils.isNullCheck(customer.getReferenceId()))
					existingProfile.getCustomer().setReferenceId(customer.getReferenceId());
			}
			break;
		// case OWNER:
		// case TIEUPS:
		// case BDM:
		case BDO:
			BDO bdo = profile.getBdo();
			if (null != bdo) {
				if (!CommonUtils.isNullCheck(bdo.getBdmId()))
					existingProfile.getBdo().setBdmId(bdo.getBdmId());
			}
		case CHANNEL_PARTNER:
			ChannelPartner channelPartner = profile.getChannelPartner();
			if (null != channelPartner) {
				if (!CommonUtils.isNullCheck(channelPartner.getBdmId()))
					existingProfile.getChannelPartner().setBdmId(channelPartner.getBdmId());
			}
			break;
		case COORDINATOR:
			Coordinator coordinator = profile.getCoordinator();
			if (null != coordinator) {
				if (!CommonUtils.isNullCheck(coordinator.getFranchiseId()))
					existingProfile.getCoordinator().setFranchiseId(coordinator.getFranchiseId());
			}
			break;
		case FIELDOFFICER:
			FieldOfficer fieldOfficer = profile.getFieldOfficer();
			if (null != fieldOfficer) {
				if (!CommonUtils.isNullCheck(fieldOfficer.getFranchiseId()))
					existingProfile.getFieldOfficer().setFranchiseId(fieldOfficer.getFranchiseId());
			}
			break;
		case OPERATIONAL_TEAM:
			OperationalTeam operationalTeam = new OperationalTeam();
			if (null != operationalTeam) {
				if (operationalTeam.getAssignedStateId() > 0)
					existingProfile.getOperationalTeam().setAssignedStateId(operationalTeam.getAssignedStateId());
			}
			break;

		case FRANCHISE:
			Franchise franchise = profile.getFranchise();
			if (null != franchise) {
				Franchise existingFranchise = existingProfile.getFranchise();
				if (!CommonUtils.isNullCheck(franchise.getCompanyName()))
					existingFranchise.setCompanyName(franchise.getCompanyName());
				if (franchise.getYearOfContract() > 0)
					existingFranchise.setYearOfContract(franchise.getYearOfContract());
				if (null != franchise.getStartDate())
					existingFranchise.setStartDate(franchise.getStartDate());
				if (null != franchise.getEndDate())
					existingFranchise.setEndDate(franchise.getEndDate());
				if (!CommonUtils.isNullCheck(franchise.getProprietorName()))
					existingFranchise.setProprietorName(franchise.getProprietorName());
				if (!CommonUtils.isNullCheck(franchise.getLicenseNo()))
					existingFranchise.setLicenseNo(franchise.getLicenseNo());
				if (!CommonUtils.isNullCheck(franchise.getGstNo()))
					existingFranchise.setGstNo(franchise.getGstNo());
				if (franchise.getOrganisationType() > 0)
					existingFranchise.setOrganisationType(franchise.getOrganisationType());
				if (franchise.getLatitude() > 0)
					existingFranchise.setLatitude(franchise.getLatitude());
				if (franchise.getLongitude() > 0)
					existingFranchise.setLongitude(franchise.getLongitude());
				if (!CommonUtils.isNullCheck(franchise.getModifiedBy()))
					existingFranchise.setModifiedBy(franchise.getModifiedBy());
				if (!CommonUtils.isNullCheck(franchise.getReason()))
					existingFranchise.setReason(franchise.getReason());
			}
			break;
		case FLEET_OPERATOR:
			FleetOperator fleetOperator = profile.getFleet();
			if (null != fleetOperator) {
				FleetOperator existingFleetOperator = existingProfile.getFleet();
				if (!CommonUtils.isNullCheck(fleetOperator.getCompanyName()))
					existingFleetOperator.setCompanyName(fleetOperator.getCompanyName());
				if (fleetOperator.getYearOfContract() > 0)
					existingFleetOperator.setYearOfContract(fleetOperator.getYearOfContract());
				if (null != fleetOperator.getStartDate())
					existingFleetOperator.setStartDate(fleetOperator.getStartDate());
				if (null != fleetOperator.getEndDate())
					existingFleetOperator.setEndDate(fleetOperator.getEndDate());
				if (fleetOperator.getLatitude() > 0)
					existingFleetOperator.setLatitude(fleetOperator.getLatitude());
				if (fleetOperator.getLongitude() > 0)
					existingFleetOperator.setLongitude(fleetOperator.getLongitude());
				if (!CommonUtils.isNullCheck(fleetOperator.getModifiedBy()))
					existingFleetOperator.setModifiedBy(fleetOperator.getModifiedBy());
				if (!CommonUtils.isNullCheck(fleetOperator.getReason()))
					existingFleetOperator.setReason(fleetOperator.getReason());
			}
			break;

		case ENTERPRISE:
			Enterprise enterprise = profile.getEnterprise();
			if (null != enterprise) {
				Enterprise existingEnterprise = existingProfile.getEnterprise();
				if (!CommonUtils.isNullCheck(enterprise.getCompanyName()))
					existingEnterprise.setCompanyName(enterprise.getCompanyName());
				if (enterprise.getYearOfContract() > 0)
					existingEnterprise.setYearOfContract(enterprise.getYearOfContract());
				if (null != enterprise.getStartDate())
					existingEnterprise.setStartDate(enterprise.getStartDate());
				if (null != enterprise.getEndDate())
					existingEnterprise.setEndDate(enterprise.getEndDate());
				if (enterprise.getLatitude() > 0)
					existingEnterprise.setLatitude(enterprise.getLatitude());
				if (enterprise.getLongitude() > 0)
					existingEnterprise.setLongitude(enterprise.getLongitude());
			}
			break;
		case WAREHOUSE:
			WareHouse warehouse = profile.getWarehouse();
			if (null != warehouse) {
				WareHouse existingWarehouse = existingProfile.getWarehouse();
				if (!CommonUtils.isNullCheck(warehouse.getCompanyName()))
					existingWarehouse.setCompanyName(warehouse.getCompanyName());
				if (warehouse.getYearOfContract() > 0)
					existingWarehouse.setYearOfContract(warehouse.getYearOfContract());
				if (null != warehouse.getStartDate())
					existingWarehouse.setStartDate(warehouse.getStartDate());
				if (null != warehouse.getEndDate())
					existingWarehouse.setEndDate(warehouse.getEndDate());
				if (warehouse.getLatitude() > 0)
					existingWarehouse.setLatitude(warehouse.getLatitude());
				if (warehouse.getLongitude() > 0)
					existingWarehouse.setLongitude(warehouse.getLongitude());
				if (warehouse.getMaxCapacity() > 0)
					existingWarehouse.setMaxCapacity(warehouse.getMaxCapacity());
				if (!CommonUtils.isNullCheck(warehouse.getRegistrationNumber()))
					existingWarehouse.setRegistrationNumber(warehouse.getRegistrationNumber());
				if (!CommonUtils.isNullCheck(warehouse.getMdName()))
					existingWarehouse.setMdName(warehouse.getMdName());
				if (!CommonUtils.isNullCheck(warehouse.getModifiedBy()))
					existingWarehouse.setModifiedBy(warehouse.getModifiedBy());
				if (!CommonUtils.isNullCheck(warehouse.getReason()))
					existingWarehouse.setReason(warehouse.getReason());
			}
		case DRIVER:
			Driver driver = profile.getDriver();
			if (null != driver) {
				Driver existingDriver = existingProfile.getDriver();
				if (driver.getBloodId() > 0)
					existingDriver.setBloodId(driver.getBloodId());
				if (driver.getDriverTypeId() > 0)
					existingDriver.setDriverTypeId(driver.getDriverTypeId());
				if (driver.getWeight() > 0)
					existingDriver.setWeight(driver.getWeight());
				if (!CommonUtils.isNullCheck(driver.getIdentificationMark()))
					existingDriver.setIdentificationMark(driver.getIdentificationMark());
				if (driver.getHeight() > 0)
					existingDriver.setHeight(driver.getHeight());
				if (!CommonUtils.isNullCheck(driver.getCompanyName()))
					existingDriver.setCompanyName(driver.getCompanyName());
				if (!CommonUtils.isNullCheck(driver.getDlNumber()))
					existingDriver.setDlNumber(driver.getDlNumber());
				if (null != driver.getDlIssueDate())
					existingDriver.setDlIssueDate(driver.getDlIssueDate());
				if (null != driver.getDlExpiryDate())
					existingDriver.setDlExpiryDate(driver.getDlExpiryDate());
				if (!CommonUtils.isNullCheck(driver.getBadgeNumber()))
					existingDriver.setBadgeNumber(driver.getBadgeNumber());
				if (null != driver.getBadgeExpiryDate())
					existingDriver.setBadgeExpiryDate(driver.getBadgeExpiryDate());
				if (driver.getIssueRto() > 0)
					existingDriver.setIssueRto(driver.getIssueRto());
				if (driver.getLicenceCategory() > 0)
					existingDriver.setLicenceCategory(driver.getLicenceCategory());
			}
		default:
			break;
		}
	}

	public void checkFareDistributionCreatedOrNot(String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"checkFareDistributionCreatedOrNot in Profileutil " + JsonUtil.toJsonString(profileId)));
		List<FareDistributionDTo> fareDistributionModels = new ArrayList<FareDistributionDTo>();
		Franchise franchiseModel = new Franchise();
		franchiseModel = getFranchiseByProfileId(profileId);
		fareDistributionModels = adminCommonService.getFDListByFranchiseId(franchiseModel.getFranchiseId(), false);
		if (fareDistributionModels == null || fareDistributionModels.isEmpty())
			throw new FARE_DISTRIBUTION_NOT_EXIST();
		fareDistributionModels = adminCommonService.getFDListByFranchiseId(franchiseModel.getFranchiseId(), true);
		if (fareDistributionModels == null || fareDistributionModels.isEmpty()) {
			throw new FARE_DISTRIBUTION_EXIST();
		}
	}

	public Map<String, Integer> getPagination(Map<String, String> reqParam) {
		Map<String, Integer> paginationCount = new HashMap<>();
		int lowerBound = 0;
		int upperBound = 0;
		int pageNo = 0, pageSize = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("pageNo")) {
				pageNo = Integer.parseInt(reqParam.get("pageNo"));
			}

			if (reqParam.containsKey("pageSize")) {
				pageSize = Integer.parseInt(reqParam.get("pageSize"));
			}

			if (reqParam.containsKey("pageNo") && reqParam.containsKey("pageSize")) {
				if (pageNo <= 0 || pageSize <= 0)
					throw new NOT_FOUND("PageNo or page size should be grater than zero");
			}
		}

		lowerBound = pageNo - 1;

		if (pageNo > 1) {
			lowerBound = (pageNo - 1) * pageSize;
		}
		upperBound = pageSize;

		paginationCount.put("lowerBound", lowerBound);
		paginationCount.put("upperBound", upperBound);

		return paginationCount;
	}

	public List<Profile> downloadProfileList(List<Profile> profileModelList) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"downloadProfileList in Profileutil " + JsonUtil.toJsonString(profileModelList)));
		for (Profile profile : profileModelList) {
			List<Address> addressList = new ArrayList<Address>();
//			List<AddressDomain> addressDomainList = getAddressDetails(profile.getId());
			List<AddressDomain> addressDomainList = getAddressDetails(profile.getId(), profile.getDefaultRoleId(),
					true);
			if (addressDomainList.size() > 0)
				for (AddressDomain addressDomain : addressDomainList) {
					Address address = new Address();
					if (addressDomain.getType().equalsIgnoreCase(AddressType.HOME.name()))
						BeanUtils.copyProperties(addressDomain, address);
					else
						BeanUtils.copyProperties(addressDomain, address);
					addressList.add(address);
				}
			profile.setAddress(addressList);

		}
		return profileModelList;

	}

	public ListDto getListWithPaginationCount(List<Profile> profileList, int totalSize) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getListWithPaginationCount in Profileutil " + JsonUtil.toJsonString(profileList)));
		ListDto listDto = new ListDto();
		listDto.setList(profileList);
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	public ListDto getBoardingRequestWithPaginationCount(List<BoardingRequestModel> boardingRequestList, int totalSize)
			throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getBoardingRequestWithPaginationCount in Profileutil " + JsonUtil.toJsonString(boardingRequestList)));
		ListDto listDto = new ListDto();
		listDto.setList(boardingRequestList);
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	public ListDto getEnquiryReasonWithPaginationCount(List<EnquiryReasonModel> reasonList, int totalSize)
			throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getEnquiryReasonWithPaginationCount in Profileutil " + JsonUtil.toJsonString(reasonList)));
		ListDto listDto = new ListDto();
		listDto.setList(reasonList);
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	public boolean checkPersonalData(Profile profile) {
		if (profile.getMobileNumber() != null && profile.getEmailId() != null && profile.getFirstName() != null
				&& profile.getId() != null)
			return true;
		return false;

	}

	public boolean validateModifiedByAndReason(String modifiedBy, String reason, int modifiedByRoleId) {
		if (CommonUtils.isNullCheck(modifiedBy))
			throw new NOT_FOUND("Please mention the userId!!");
		if (CommonUtils.isNullCheck(reason))
			throw new NOT_FOUND("Please mention the reason!!");
		if (modifiedByRoleId == 0)
			throw new NOT_FOUND("Please mention the userRoleId!!");
		return true;

	}

	public void saveReason(String modifiedBy, String reason, int modifiedByRoleId, String profileId, int roleId)
			throws Exception {
		ReasonDomain reasonDomain = new ReasonDomain();
		List<ReasonDomain> reasonDomainList = reasonDao.getReasonsByRoleByProfile(roleId, profileId, 0, 0);
		if (!reasonDomainList.isEmpty()) {
			reasonDomain.setChangedStatus(reasonDomainList.get(0).getChangedStatus());
			reasonDomain.setPreviousStatus(reasonDomainList.get(0).getPreviousStatus());
			reasonDomain.setUserId(modifiedBy);
			reasonDomain.setUserRoleId(modifiedByRoleId);
			reasonDomain.setProfileId(profileId);
			reasonDomain.setRoleId(roleId);
			reasonDomain.setReason(reason);
			reasonDomain.setUuid(CommonUtils.generateRandomId());
			reasonDao.save(reasonDomain);
		}

	}

//	public List<Franchise> getFranchiseDetails(List<Franchise> franchises) throws Exception {
//		List<Franchise> franchiseList = new ArrayList<Franchise>();
//		for (Franchise franchise : franchises) {
//			FranchiseDomainV2 franchiseDomainV2 = franchiseDAO.getFranchiseByFranchiseId(franchise.getFranchiseId());
//			BeanUtils.copyProperties(franchiseDomainV2, franchise);
//			franchiseList.add(franchise);
//		}
//		return franchiseList;
//	}
//
//
//	public List<Profile> getCoordinatorDetails(List<CoordinatorDetail> coordinators) throws Exception {
//		List<Profile> coordinatorList = new ArrayList<Profile>();
//		for (CoordinatorDetail coordinator : coordinators) {
//			getProfileDetailsByCompanyProfileId(@RequestParam Map<String, String> reqParam)
//			CoordinatorDetail coordinatorDetail = new CoordinatorDetail();
//			CoordinatorDetail coordinatorDetailDomain = profileDAOV2
//					.getCoordinatorByCoordinatorId(coordinator.getCoordinatorId());
//			BeanUtils.copyProperties(coordinatorDetailDomain, coordinatorDetail);
//			coordinatorList.add(coordinatorDetail);
//		}
//		return coordinatorList;
//	}

	public AuthModel getProfileListDetails(AuthModel authModel, List<Profile> profile, String role) throws Exception {
		Role roleModel = Role.getRole(role);

		if (null == roleModel)
			throw new ROLE_NOT_FOUND(roleModel + "");

		switch (roleModel) {
		case FRANCHISE:
			authModel.setFranchiseList(profile);
			break;
		case COORDINATOR:
			authModel.setCoordinatorList(profile);
			break;
		case CUSTOMER:
			authModel.setCustomerList(profile);
			break;
		case BDM:
			authModel.setBdmList(profile);
			break;
		case BDO:
			authModel.setBdoList(profile);
			break;
		case CHANNEL_PARTNER:
			authModel.setChannelPartnerList(profile);
			break;
		case FIELDOFFICER:
			authModel.setFieldOfficerList(profile);
			break;
		case OPERATIONAL_TEAM:
			authModel.setOperationTeamList(profile);
			break;
		case FLEET_OPERATOR:
			authModel.setFleetList(profile);
			break;
		case ENTERPRISE:
			authModel.setEnterpriseList(profile);
			break;
		case WAREHOUSE:
			authModel.setWarehouseList(profile);
			break;
		case DRIVER:
			authModel.setDriverList(profile);
			break;
		default:
			break;

		}
		return authModel;
	}

	public void saveReason(ReasonModel reasonModel, boolean isEdit, boolean isFromProfile) throws Exception {
		ReasonDomain reasonDomain = new ReasonDomain();
		String previousStatus = EnquiryStatus.INACTIVE.name();
		ProfileRoleDomain profileRoleDomain = profileRoleDAO.getRoleDet(reasonModel.getProfileId(),
				reasonModel.getRoleId());
		RoleDomain role = roleDAO.getRoleName(reasonModel.getRoleId());
		if (isFromProfile) {
			if (null == profileRoleDomain)
				throw new NOT_FOUND("Profile not found for the role " + role.getRoleName() + ".");
		} else {
			if (null != profileRoleDomain) {
				if (profileRoleDomain.isIsActive()) {
					previousStatus = EnquiryStatus.ACTIVE.name();
					reasonModel.setPreviousStatus(previousStatus);
				}
			} else
				throw new NOT_FOUND("Profile not found for the role " + role.getRoleName() + ".");
			if (reasonModel.getRoleId() == Integer.parseInt(Role.DRIVER.getCode())) {
				Driver driver = franchiseCommonService.getDriverDetails(reasonModel.getProfileId());
				previousStatus = driver.getStatus();
				reasonModel.setPreviousStatus(previousStatus);
			}
		}
		reasonModel.setUuid(CommonUtils.generateRandomId());
		if (isEdit)
			reasonModel.setChangedStatus(previousStatus);
		else
			reasonModel.setChangedStatus(reasonModel.getStatus());
		BeanUtils.copyProperties(reasonModel, reasonDomain);
		reasonDao.save(reasonDomain);
	}

	public void validateProfileAndRole(ProfileEdit profileEdit) throws Exception {
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
	}

}
