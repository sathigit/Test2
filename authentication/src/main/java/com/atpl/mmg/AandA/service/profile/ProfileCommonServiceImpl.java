package com.atpl.mmg.AandA.service.profile;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.bankaccount.BankAccountDAO;
import com.atpl.mmg.AandA.dao.profile.BdmDAO;
import com.atpl.mmg.AandA.dao.profile.BdoDAO;
import com.atpl.mmg.AandA.dao.profile.ChannelPartnerDAO;
import com.atpl.mmg.AandA.dao.profile.CoordinatorDAO;
import com.atpl.mmg.AandA.dao.profile.CustomerDAO;
import com.atpl.mmg.AandA.dao.profile.FieldOfficerDAO;
import com.atpl.mmg.AandA.dao.profile.OperationalTeamDao;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.domain.profile.BDMDomain;
import com.atpl.mmg.AandA.domain.profile.BDODomain;
import com.atpl.mmg.AandA.domain.profile.ChannelPartnerDomain;
import com.atpl.mmg.AandA.domain.profile.CoordinatorDomain;
import com.atpl.mmg.AandA.domain.profile.CustomerDomain;
import com.atpl.mmg.AandA.domain.profile.FieldOfficerDomain;
import com.atpl.mmg.AandA.domain.profile.OperationalTeamDomain;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.EmailValidator;
import com.atpl.mmg.AandA.utils.IDGeneration;

@Service("profileCommonService")
@Qualifier("profileCommonService")
public class ProfileCommonServiceImpl implements ProfileCommonService {

	@Autowired
	ProfileUtil profileUtil;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Autowired
	BankAccountDAO bankAccountDAO;

	@Autowired
	AuthFranchiseCommonService franchiseCommonService;

	@Autowired
	EmailValidator emailValidator;

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	IDGeneration idGeneration;

	@Autowired
	OperationalTeamDao operationalDao;

	@Autowired
	CoordinatorDAO coordinatorDao;

	@Autowired
	BdmDAO bdmDAO;

	@Autowired
	BdoDAO bdoDAO;

	@Autowired
	ChannelPartnerDAO channelPartnerDAO;

	@Autowired
	FieldOfficerDAO fieldOfficerDAO;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Profile save(Profile profile, ProfileDomainV2 existingProfileDomain) throws Exception {
		Profile profileObject = new Profile();
		Role role = Role.getRole(profile.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");

		profileObject = profileUtil.saveProfileRole(profile, existingProfileDomain);
		profileUtil.saveBank(profile);

		switch (role) {
		case CUSTOMER:
			saveCustomer(profile, profileObject.getId());
			break;
		// case OWNER:
		// case TIEUPS:
		case COORDINATOR:
			saveCoordinator(profile, profileObject.getId());
			break;
		case BDM:
			saveBDM(profile, profileObject.getId());
			break;
		case BDO:
			saveBDO(profile, profileObject.getId());
			break;
		case OPERATIONAL_TEAM:
			saveOperationalTeam(profile, profileObject.getId());
			break;
		case CHANNEL_PARTNER:
			saveChannelPartner(profile, profileObject.getId());
			break;
		case FIELDOFFICER:
			saveFieldOfficer(profile, profileObject.getId());
			break;
		}

		sendRegistrationMail(role, profile);
		return profileObject;
	}

	private void saveCustomer(Profile profile, String profileId) throws Exception {
		CustomerDomain customerDomain = new CustomerDomain();
		if (null != profile.getCustomer())
			BeanUtils.copyProperties(profile.getCustomer(), customerDomain);
		customerDomain.setCustomerId(String.valueOf(idGeneration.generateRandomNumber()));
		customerDomain.setProfileId(profileId);
		customerDAO.save(customerDomain);
	}

	private void saveOperationalTeam(Profile profile, String profileId) throws Exception {
		OperationalTeamDomain operationalTeamDomain = new OperationalTeamDomain();
		if (null != profile.getOperationalTeam())
			BeanUtils.copyProperties(profile.getOperationalTeam(), operationalTeamDomain);
		operationalTeamDomain.setOperationalTeamId(String.valueOf(idGeneration.generateRandomNumber()));
		operationalTeamDomain.setProfileId(profileId);
		operationalDao.save(operationalTeamDomain);
	}

	private void saveChannelPartner(Profile profile, String profileId) throws Exception {
		ChannelPartnerDomain channelPartnerDomain = new ChannelPartnerDomain();
		if (null != profile.getChannelPartner())
			BeanUtils.copyProperties(profile.getChannelPartner(), channelPartnerDomain);
		channelPartnerDomain.setChannelPartnerId(String.valueOf(idGeneration.generateRandomNumber()));
		channelPartnerDomain.setProfileId(profileId);
		channelPartnerDAO.save(channelPartnerDomain);
	}

	private void saveBDM(Profile profile, String profileId) throws Exception {
		BDMDomain bdmDomain = new BDMDomain();
		if (null != profile.getBdm())
			BeanUtils.copyProperties(profile.getBdm(), bdmDomain);
		bdmDomain.setBdmId(String.valueOf(idGeneration.generateRandomNumber()));
		bdmDomain.setProfileId(profileId);
		bdmDAO.save(bdmDomain);
	}

	private void saveCoordinator(Profile profile, String profileId) throws Exception {
		CoordinatorDomain coordinatorDomain = new CoordinatorDomain();
		if (null != profile.getCoordinator())
			BeanUtils.copyProperties(profile.getCoordinator(), coordinatorDomain);
		coordinatorDomain.setCoordinatorId(String.valueOf(idGeneration.generateRandomNumber()));
		coordinatorDomain.setProfileId(profileId);
		coordinatorDao.save(coordinatorDomain);
	}

	private void saveBDO(Profile profile, String profileId) throws Exception {
		BDODomain bdoDomain = new BDODomain();
		if (null != profile.getBdo())
			BeanUtils.copyProperties(profile.getBdo(), bdoDomain);
		bdoDomain.setBdoId(String.valueOf(idGeneration.generateRandomNumber()));
		bdoDomain.setProfileId(profileId);
		bdoDomain.setBdmId(bdoDomain.getBdmId());
		bdoDAO.save(bdoDomain);
	}

	private void saveFieldOfficer(Profile profile, String profileId) throws Exception {
		FieldOfficerDomain fieldOfficerDomain = new FieldOfficerDomain();
		if (null != profile.getFieldOfficer())
			BeanUtils.copyProperties(profile.getFieldOfficer(), fieldOfficerDomain);
		fieldOfficerDomain.setFieldOfficerId(String.valueOf(idGeneration.generateRandomNumber()));
		fieldOfficerDomain.setProfileId(profileId);
		fieldOfficerDAO.save(fieldOfficerDomain);
	}

	private void sendRegistrationMail(Role role, Profile profile) {
		if (role.getCode() == Role.CUSTOMER.getCode()) {
			/**
			 * Send sms
			 */
			String message = null;
			message = "Dear " + profile.getFirstName()
					+ ",\r\nThank you for registering with MoveMyGoods.Click https://bit.ly/39X7wWd "
					+ "to book your trip.Call 901 902 903 6 for support";
			if (profile.isIsRegisterByMmg())
				message = "Dear " + profile.getFirstName()
						+ ",\r\nThank you for registering with MoveMyGoods.Click to http://35.154.73.99/validate reset your password. "
						+ "Call 901 902 903 6 for support";
			emailValidator.sendSMSMessage(profile.getMobileNumber().toString(),null, message, false);
			/**
			 * Send Email
			 */
			Map<String, Object> variables = new HashMap<>();
			variables.put("customerName", profile.getFirstName());
			String body = emailValidator.generateMailHtml("registration", variables);
			emailValidator.sendEmail("Move My Goods", profile.getEmailId(), body);
		}
	}

	public String update(Profile profile) throws Exception {
		Role role = Role.getRole(profile.getRoleId() + "");
		/**
		 * App or website must pass the role id in the request
		 */
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");
		switch (role) {
		case CUSTOMER:
			updateCustomer(profile);
			break;
		// case OWNER:
		// case TIEUPS:
		case COORDINATOR:
			updateCoordinator(profile);
			break;
		case BDM:
			updateBDM(profile);
			break;
		case BDO:
			updateBDO(profile);
			break;
		case OPERATIONAL_TEAM:
			updateOperationalTeam(profile);
			break;
		case CHANNEL_PARTNER:
			updateChannelPartner(profile);
			break;
		case FIELDOFFICER:
			updateFieldOfficer(profile);
			break;
		}
		return "Updated Successfully";
	}

	private void updateCustomer(Profile profile) throws Exception {
		CustomerDomain customerDomain = null;
		if (null != profile.getCustomer()) {
			customerDomain = new CustomerDomain();
			BeanUtils.copyProperties(profile.getCustomer(), customerDomain);
			if (!CommonUtils.isNullCheck(customerDomain.getCustomerId())) {
				customerDAO.update(customerDomain);
			}
		}
	}

	private void updateCoordinator(Profile profile) throws Exception {
		CoordinatorDomain coordinatorDomain = null;
		if (null != profile.getCoordinator()) {
			coordinatorDomain = new CoordinatorDomain();
			BeanUtils.copyProperties(profile.getCoordinator(), coordinatorDomain);
			if (!CommonUtils.isNullCheck(coordinatorDomain.getCoordinatorId())) {
				coordinatorDao.update(coordinatorDomain);
			}
		}
	}

	private void updateBDM(Profile profile) throws Exception {
		BDMDomain bdmDomain = null;
		if (null != profile.getBdm()) {
			bdmDomain = new BDMDomain();
			BeanUtils.copyProperties(profile.getBdm(), bdmDomain);
			if (!CommonUtils.isNullCheck(bdmDomain.getBdmId())) {
				bdmDAO.update(bdmDomain);
			}
		}
	}

	private void updateBDO(Profile profile) throws Exception {
		BDODomain bdoDomain = null;
		if (null != profile.getBdo()) {
			bdoDomain = new BDODomain();
			BeanUtils.copyProperties(profile.getBdo(), bdoDomain);
			if (!CommonUtils.isNullCheck(bdoDomain.getBdoId())) {
				bdoDAO.update(bdoDomain);
			}
		}
	}

	private void updateOperationalTeam(Profile profile) throws Exception {
		OperationalTeamDomain operationalTeamrDomain = null;
		if (null != profile.getOperationalTeam()) {
			operationalTeamrDomain = new OperationalTeamDomain();
			BeanUtils.copyProperties(profile.getOperationalTeam(), operationalTeamrDomain);
			if (!CommonUtils.isNullCheck(operationalTeamrDomain.getOperationalTeamId())) {
				operationalDao.update(operationalTeamrDomain);
			}
		}
	}

	private void updateChannelPartner(Profile profile) throws Exception {
		ChannelPartnerDomain channelPartnerDomain = null;
		if (null != profile.getChannelPartner()) {
			channelPartnerDomain = new ChannelPartnerDomain();
			BeanUtils.copyProperties(profile.getChannelPartner(), channelPartnerDomain);
			if (!CommonUtils.isNullCheck(channelPartnerDomain.getChannelPartnerId())) {
				channelPartnerDAO.update(channelPartnerDomain);
			}
		}
	}

	private void updateFieldOfficer(Profile profile) throws Exception {
		FieldOfficerDomain fieldOfficerDomain = null;
		if (null != profile.getFieldOfficer()) {
			fieldOfficerDomain = new FieldOfficerDomain();
			BeanUtils.copyProperties(profile.getFieldOfficer(), fieldOfficerDomain);
			if (!CommonUtils.isNullCheck(fieldOfficerDomain.getFieldOfficerId())) {
				fieldOfficerDAO.update(fieldOfficerDomain);
			}
		}
	}
}
