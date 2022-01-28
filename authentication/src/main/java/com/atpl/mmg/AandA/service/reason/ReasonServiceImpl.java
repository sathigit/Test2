package com.atpl.mmg.AandA.service.reason;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.EnquiryRole;
import com.atpl.mmg.AandA.constant.EnquiryStatus;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.dashboard.DashboardDAO;
import com.atpl.mmg.AandA.dao.inactivereason.ReasonDao;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.dao.profilerole.ProfileRoleDAO;
import com.atpl.mmg.AandA.dao.role.RoleDAO;
import com.atpl.mmg.AandA.domain.dashboard.DashboardDomain;
import com.atpl.mmg.AandA.domain.inactivereason.ReasonDomain;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.domain.profilerole.ProfileRoleDomain;
import com.atpl.mmg.AandA.domain.role.RoleDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.mapper.inactivereason.ReasonMapper;
import com.atpl.mmg.AandA.model.profile.Driver;
import com.atpl.mmg.AandA.model.profile.ListDto;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.model.reason.ReasonModel;
import com.atpl.mmg.AandA.service.profile.AuthFranchiseCommonService;
import com.atpl.mmg.AandA.service.profile.ProfileUtil;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.EmailValidator;

@Service("reasonService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReasonServiceImpl implements ReasonService {

	@Autowired
	ReasonDao reasonDao;

	@Autowired
	ReasonMapper reasonMapper;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	EmailValidator emailValidator;

	@Autowired
	ProfileRoleDAO profileRoleDAO;

	@Autowired
	DashboardDAO dashboardDAO;

	@Autowired
	AuthFranchiseCommonService franchiseCommonService;

	@Autowired
	ProfileUtil profileUtil;

	@Override
	public Profile validateReason(ReasonModel reasonModel) throws Exception {
		Profile profile = profileUtil.validateProfileId(reasonModel.getProfileId());
		if (CommonUtils.isNullCheck(reasonModel.getReason()))
			throw new NOT_FOUND("Please mention the reason!!");
		if (CommonUtils.isNullCheck(reasonModel.getUserId()))
			throw new NOT_FOUND("Please mention the userId!!");
		else {
			ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetails(reasonModel.getUserId());
			if (null == profileDomain)
				throw new NOT_FOUND("Please mentin the proper userId!!");
		}
		if (0 >= reasonModel.getRoleId())
			throw new NOT_FOUND("Please mention the roleId!!");
		else
			roleDAO.getRoleName(reasonModel.getRoleId());
		if (CommonUtils.isNullCheck(reasonModel.getStatus()))
			throw new NOT_FOUND("Please mention the status!!");
		return profile;
	}

	@Override
	public String save(ReasonModel reasonModel,boolean isFromProfile) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Reason Request: " + JsonUtil.toJsonString(reasonModel)));
		ReasonDomain reasonDomain = new ReasonDomain();
		Profile profile = validateReason(reasonModel);
		profileUtil.saveReason(reasonModel,false,isFromProfile);
		/*
		 * String previousStatus = EnquiryStatus.INACTIVE.name(); ProfileRoleDomain
		 * profileRoleDomain = profileRoleDAO.getRoleDet(reasonModel.getProfileId(),
		 * reasonModel.getRoleId()); RoleDomain role =
		 * roleDAO.getRoleName(reasonModel.getRoleId()); if (null != profileRoleDomain)
		 * { if (profileRoleDomain.isIsActive()) previousStatus =
		 * EnquiryStatus.ACTIVE.name(); } else throw new
		 * NOT_FOUND("Profile not found for the role " + role.getRoleName() + "."); if
		 * (reasonModel.getRoleId() == Integer.parseInt(Role.DRIVER.getCode())) { Driver
		 * driver = franchiseCommonService.getDriverDetails(reasonModel.getProfileId());
		 * previousStatus = driver.getStatus(); }
		 * reasonModel.setUuid(CommonUtils.generateRandomId());
		 * reasonModel.setChangedStatus(reasonModel.getStatus());
		 * reasonModel.setPreviousStatus(previousStatus);
		 * BeanUtils.copyProperties(reasonModel, reasonDomain);
		 * reasonDao.save(reasonDomain);
		 */
		if (reasonModel.getStatus().equalsIgnoreCase(EnquiryRole.ACTIVE.name())
				|| reasonModel.getStatus().equalsIgnoreCase(EnquiryRole.INACTIVE.name()))
			sendEmail(reasonModel, profile);
		return "Updated successfully";
	}

	@Override
	public String sendEmail(ReasonModel reasonModel, Profile profile) throws Exception {
		ReasonDomain requestDomain = new ReasonDomain();
		BeanUtils.copyProperties(reasonModel, requestDomain);
		String body, msg, text, message = null;
		Map<String, Object> variables = new HashMap<>();
		RoleDomain role = roleDAO.getRoleName(reasonModel.getRoleId());
		if (reasonModel.getStatus().equalsIgnoreCase(EnquiryStatus.ACTIVE.name())) {
			message = "Your  " + role.getRoleName() + " account  is  activated.";
			msg = "Welcome to MOVE MY GOODS.";
			text = "Your " + role.getRoleName() + " account is  activated.";
			variables.put("firstName", profile.getFirstName());
			variables.put("message", msg);
			variables.put("text", text);
		}
		if (reasonModel.getStatus().equalsIgnoreCase(EnquiryStatus.INACTIVE.name())) {
			message = "Your " + role.getRoleName()
					+ " Account is deactivated.Please contact Move My Goods Help Center for assistance.Contact No: 901 902 903 6.";
			msg = " ";
			text = "Your " + role.getRoleName() + "  Account is deactivated due to the reason:"
					+ reasonModel.getReason() + ".";
			variables.put("firstName", profile.getFirstName());
			variables.put("message", msg);
			variables.put("text", text);
		}
		emailValidator.sendSMSMessage(profile.getMobileNumber(),null, message, false);
		body = emailValidator.generateMailHtml("statusemail", variables);
		emailValidator.sendEmail("Move My Goods", profile.getEmailId(), body);
		return "Mail sent";

	}

	@Override
	public ListDto getReasonsByRoleId(int roleId, Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getReasonsByRoleId Request: " + JsonUtil.toJsonString(roleId)));
		if (0 >= roleId)
			throw new NOT_FOUND("Please mention the roleId!!");
		else
			roleDAO.getRoleName(roleId);
		List<ReasonDomain> reasonDomain = new ArrayList<ReasonDomain>();

		String profileId = null;
		int lowerBound = 0;
		int upperBound = 0;
		DashboardDomain dashboardDomain = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("profileId")) {
				profileId = reqParam.get("profileId");
				reasonDomain = reasonDao.getReasonsByRoleByProfile(roleId, profileId, lowerBound, upperBound);
				dashboardDomain = dashboardDAO.getReasonsCountByRoleByProfile(roleId, profileId, false, false);
			}
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
			if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
				if (reqParam.containsKey("profileId") && reqParam.containsKey("requestedBy")) {
					if (reqParam.get("requestedBy").equals("FRANCHISE")) {
						reasonDomain = reasonDao.getReasonsByRoleByProfileAndStatus(roleId, profileId, true, lowerBound,
								upperBound);
						dashboardDomain = dashboardDAO.getReasonsCountByRoleByProfile(roleId, profileId, true, true);
					}
					if (reqParam.get("requestedBy").equals("ONBOARD")) {
						reasonDomain = reasonDao.getReasonsByRoleByProfileAndStatus(roleId, profileId, false,
								lowerBound, upperBound);
						dashboardDomain = dashboardDAO.getReasonsCountByRoleByProfile(roleId, profileId, false, true);
					}
				}
			}
		} else {
			reasonDomain = reasonDao.getReasonsByRoleId(roleId);
			dashboardDomain = dashboardDAO.getReasonsCountByRoleByProfile(roleId, null, false, false);
		}
		for (ReasonDomain reason : reasonDomain) {
			RoleDomain roleDomain = roleDAO.getRoleName(roleId);
			reason.setRole(roleDomain.getRoleName());
			ProfileDomainV2 profile = profileDAOV2.getProfileDetails(reason.getUserId());
			reason.setUserName(profile.getFirstName());
			roleDomain = roleDAO.getRoleName(reason.getUserRoleId());
			reason.setUserRole(roleDomain.getRoleName());
		}
		ListDto listDto = new ListDto(dashboardDomain.getTotal(), reasonMapper.entityList(reasonDomain));
		return listDto;
	}

	@Override
	public ListDto getReasonsByRoleAndProfileId(int roleId, String profileId, Map<String, String> reqParam)
			throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getReasonsByRoleAndProfileId Request: "
						+ JsonUtil.toJsonString(roleId) + " profileId " + JsonUtil.toJsonString(profileId)));
		if (0 >= roleId)
			throw new NOT_FOUND("Please mention the roleId!!");
		if (null == profileId)
			throw new NOT_FOUND("Please mention the profileId!!");
		int lowerBound = 0;
		int upperBound = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		List<ReasonDomain> reasonDomainList = reasonDao.getReasonsByRoleByProfile(roleId, profileId, lowerBound,
				upperBound);
		for (ReasonDomain reason : reasonDomainList) {
			RoleDomain roleDomain = roleDAO.getRoleName(roleId);
			reason.setRole(roleDomain.getRoleName());
			ProfileDomainV2 profile = profileDAOV2.getProfileDetails(reason.getUserId());
			reason.setUserName(profile.getFirstName());
		}
		DashboardDomain dashboardDomain = dashboardDAO.getReasonsCountByRoleByProfile(roleId, profileId, false, false);
		ListDto listDto = new ListDto(dashboardDomain.getTotal(), reasonMapper.entityList(reasonDomainList));
		return listDto;
	}

	@Override
	public ReasonModel getReasonByUuid(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getReasonByUuid Request: " + JsonUtil.toJsonString(uuid)));
		ReasonModel reasonModel = new ReasonModel();
		ReasonDomain reasonDomain = reasonDao.getReasonByUuid(uuid);
		RoleDomain roleDomain = roleDAO.getRoleName(reasonDomain.getRoleId());
		reasonDomain.setRole(roleDomain.getRoleName());
		ProfileDomainV2 profile = profileDAOV2.getProfileDetails(reasonDomain.getUserId());
		reasonDomain.setUserName(profile.getFirstName());
		roleDomain = roleDAO.getRoleName(reasonDomain.getUserRoleId());
		reasonDomain.setUserRole(roleDomain.getRoleName());
		BeanUtils.copyProperties(reasonDomain, reasonModel);
		return reasonModel;
	}

}
