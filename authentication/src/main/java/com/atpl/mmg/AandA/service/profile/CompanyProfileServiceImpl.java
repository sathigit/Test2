package com.atpl.mmg.AandA.service.profile;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.constant.EnquiryRole;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.bankaccount.BankAccountDAO;
import com.atpl.mmg.AandA.dao.boardingRequest.BoardingRequestDAO;
import com.atpl.mmg.AandA.dao.enterprise.EnterpriseDAO;
import com.atpl.mmg.AandA.dao.fleetoperator.FleetOperatorDAO;
import com.atpl.mmg.AandA.dao.franchise.FranchiseDAO;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.dao.warehouse.WareHouseDAO;
import com.atpl.mmg.AandA.domain.boardingRequest.BoardingRequestDomain;
import com.atpl.mmg.AandA.domain.boardingRequest.EnquiryReasonDomain;
import com.atpl.mmg.AandA.domain.profile.EnterpriseDomain;
import com.atpl.mmg.AandA.domain.profile.FleetOperatorDomain;
import com.atpl.mmg.AandA.domain.profile.FranchiseDomainV2;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.domain.profile.WareHouseDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.VALIDATE_BOARDING_ENQUIRY;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.IDGeneration;

@Service("companyProfileService")
@Qualifier("companyProfileService")
public class CompanyProfileServiceImpl implements ProfileCommonService {

	@Autowired
	ProfileUtil profileUtil;

	ProfileCommonService profileCommonService;

	@Autowired
	BankAccountDAO bankAccountDAO;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Autowired
	MMGProperties mmgProperties;

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
	BoardingRequestDAO boardingRequestDAO;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Profile save(Profile profile, ProfileDomainV2 existingProfileDomain) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"save Profile in CompanyProfileServiceImpl: " + JsonUtil.toJsonString(profile)
						+ " existingProfileDomain: " + JsonUtil.toJsonString(existingProfileDomain)));
		Profile profileObject = new Profile();
		Role role = Role.getRole(profile.getRoleId() + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");

		profileObject = profileUtil.saveProfileRole(profile, existingProfileDomain);
		profileUtil.saveBank(profile);

		switch (role) {
		case FRANCHISE:
			saveFranchise(profile, profileObject.getId());
			break;
		case FLEET_OPERATOR:
			saveFleetOperator(profile, profileObject.getId());
			break;
		case ENTERPRISE:
			saveEnterprise(profile, profileObject.getId());
			break;
		case WAREHOUSE:
			saveWareHouse(profile, profileObject.getId());
			break;
		default:
			break;
		}
		Profile profileId = new Profile();
		profileId.setId(profileObject.getId());
		return profileId;
	}

	public void saveFranchise(Profile profile, String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"saveFranchise in CompanyProfileServiceImpl: " + JsonUtil.toJsonString(profile)));
		FranchiseDomainV2 franchiseDomain = new FranchiseDomainV2();
		if (null != profile.getFranchise())
			BeanUtils.copyProperties(profile.getFranchise(), franchiseDomain);
		if (franchiseDomain.getFranchiseId() == null || franchiseDomain.getFranchiseId().isEmpty())
			franchiseDomain.setFranchiseId(String.valueOf(idGeneration.generateRandomNumber()));
		franchiseDomain.setProfileId(profileId);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"saveFranchise into franchise Table: " + JsonUtil.toJsonString(franchiseDomain)));

		franchiseDAO.save(franchiseDomain);
		if (CommonUtils.isNullCheck(profile.getFranchise().getChannelPartnerId()))
			updateEnquiry(franchiseDomain.getBoardingEnquiryId(), franchiseDomain.getModifiedBy(),
					franchiseDomain.getReason());
	}

	public void saveFleetOperator(Profile profile, String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"saveFleetOperator in CompanyProfileServiceImpl: " + JsonUtil.toJsonString(profile)));
		FleetOperatorDomain fleetOperatorDomain = new FleetOperatorDomain();
		if (null != profile.getFleet())
			BeanUtils.copyProperties(profile.getFleet(), fleetOperatorDomain);
		fleetOperatorDomain.setFleetId(String.valueOf(idGeneration.generateRandomNumber()));
		fleetOperatorDomain.setProfileId(profileId);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "savefleetOperatorDomain into fleetOperatorDomain Table: "
						+ JsonUtil.toJsonString(fleetOperatorDomain)));
		fleetOperatorDomain = fleetOperatorDAO.save(fleetOperatorDomain);
		updateEnquiry(fleetOperatorDomain.getBoardingEnquiryId(), fleetOperatorDomain.getModifiedBy(),
				fleetOperatorDomain.getReason());
	}

	public void saveEnterprise(Profile profile, String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"saveEnterprise in CompanyProfileServiceImpl: " + JsonUtil.toJsonString(profile)));

		EnterpriseDomain enterpriseDomain = new EnterpriseDomain();
		if (null != profile.getEnterprise())
			BeanUtils.copyProperties(profile.getEnterprise(), enterpriseDomain);
		enterpriseDomain.setEnterpriseId(String.valueOf(idGeneration.generateRandomNumber()));
		enterpriseDomain.setProfileId(profileId);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"saveEnterprise in enterprise Table: " + JsonUtil.toJsonString(profile)));

		enterpriseDAO.save(enterpriseDomain);
	}

	public void saveWareHouse(Profile profile, String profileId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"saveWareHouse in CompanyProfileServiceImpl: " + JsonUtil.toJsonString(profile)));

		WareHouseDomain wareHouseDomain = new WareHouseDomain();
		if (null != profile.getWarehouse())
			BeanUtils.copyProperties(profile.getWarehouse(), wareHouseDomain);
		wareHouseDomain.setWareHouseId(String.valueOf(idGeneration.generateRandomNumber()));
		wareHouseDomain.setProfileId(profileId);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"saveWareHouse in Warehouse: " + JsonUtil.toJsonString(wareHouseDomain)));
		wareHouseDAO.save(wareHouseDomain);
		updateEnquiry(wareHouseDomain.getBoardingEnquiryId(), wareHouseDomain.getModifiedBy(),
				wareHouseDomain.getReason());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String update(Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"updateProfile in CompanyProfileServiceImpl: " + JsonUtil.toJsonString(profile)));

		Role role = Role.getRole(profile.getRoleId() + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");
		switch (role) {
		case FRANCHISE:
			updateFranchise(profile, true);
			break;
		case FLEET_OPERATOR:
			updateFleet(profile);
			break;
		case WAREHOUSE:
			updateWareHouse(profile);
			break;
		case ENTERPRISE:
			updateEnterprise(profile);
			break;
		default:
			break;
		}
		return "Updated Successfully";
	}

	public void updateFranchise(Profile profile, boolean status) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"updateFranchise in CompanyProfileServiceImpl: " + JsonUtil.toJsonString(profile)));
		FranchiseDomainV2 franchiseDomain = null;
		if (null != profile.getFranchise()) {
			franchiseDomain = new FranchiseDomainV2();
			BeanUtils.copyProperties(profile.getFranchise(), franchiseDomain);
			if (!CommonUtils.isNullCheck(franchiseDomain.getFranchiseId()))
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"updateFranchise in FranchiseTable: " + JsonUtil.toJsonString(franchiseDomain)));
			franchiseDAO.update(franchiseDomain, status);
		}
	}

	public void updateFleet(Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"updateFleet in CompanyProfileServiceImpl: " + JsonUtil.toJsonString(profile)));
		FleetOperatorDomain fleetOperatorDomain = null;
		if (null != profile.getFleet()) {
			fleetOperatorDomain = new FleetOperatorDomain();
			BeanUtils.copyProperties(profile.getFleet(), fleetOperatorDomain);
			if (!CommonUtils.isNullCheck(fleetOperatorDomain.getFleetId()))
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"updateFleet in FleetTable: " + JsonUtil.toJsonString(fleetOperatorDomain)));
			fleetOperatorDAO.update(fleetOperatorDomain);
		}

	}

	public void updateWareHouse(Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"updateWareHouse in CompanyProfileServiceImpl: " + JsonUtil.toJsonString(profile)));
		WareHouseDomain wareHouseDomain = null;
		if (null != profile.getWarehouse()) {
			wareHouseDomain = new WareHouseDomain();
			BeanUtils.copyProperties(profile.getWarehouse(), wareHouseDomain);
			if (!CommonUtils.isNullCheck(wareHouseDomain.getWareHouseId()))
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"updateWareHouse in Warehouse table: " + JsonUtil.toJsonString(wareHouseDomain)));
				wareHouseDAO.update(wareHouseDomain);
		}
	}

	public void updateEnterprise(Profile profile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"updateEnterprise in CompanyProfileServiceImpl: " + JsonUtil.toJsonString(profile)));
		EnterpriseDomain enterpriseDomain = null;
		if (null != profile.getEnterprise()) {
			enterpriseDomain = new EnterpriseDomain();
			BeanUtils.copyProperties(profile.getEnterprise(), enterpriseDomain);
			if (!CommonUtils.isNullCheck(enterpriseDomain.getEnterpriseId()))
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"updateEnterprise in Enterprise table: " + JsonUtil.toJsonString(enterpriseDomain)));
				enterpriseDAO.update(enterpriseDomain);
		}
	}

	private void updateEnquiry(String enquiryId, String userId, String reason) throws Exception {
		BoardingRequestDomain boardingRequestDomain = boardingRequestDAO.getEnquiryRequest(enquiryId);
		if (!boardingRequestDomain.getStatus().equals(EnquiryRole.INPROCESS.name()))
			throw new VALIDATE_BOARDING_ENQUIRY("Please process the boarding enquiry");
		EnquiryReasonDomain enquiryReasonDomain = new EnquiryReasonDomain();
		boardingRequestDAO.updateEnquiryStatus(enquiryId, EnquiryRole.COMPLETED.name());
		enquiryReasonDomain.setUuid(CommonUtils.generateRandomId());
		enquiryReasonDomain.setPreviousStatus(boardingRequestDomain.getStatus());
		enquiryReasonDomain.setChangedStatus(EnquiryRole.COMPLETED.name());
		enquiryReasonDomain.setEnquiryId(enquiryId);
		enquiryReasonDomain.setUserId(userId);
		enquiryReasonDomain.setReason(reason);
		enquiryReasonDomain = boardingRequestDAO.saveEnquiryReason(enquiryReasonDomain);
	}
}