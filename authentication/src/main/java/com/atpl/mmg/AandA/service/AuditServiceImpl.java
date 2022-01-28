package com.atpl.mmg.AandA.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.audit.AuditDAO;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.domain.Audit;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.PROFILE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.CommonUtils;

@Service("auditService")
public class AuditServiceImpl implements AuditService {

	@Autowired
	AuditDAO auditDAO;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Override
	public String save(Audit audit) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.AUDIT_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Audit Request: " + JsonUtil.toJsonString(audit)));
		validateAudit(audit);
		audit.setAuditId(CommonUtils.generateRandomId());
		auditDAO.save(audit);
		return "Saved successfully";
	}

	@Override
	public Audit getAuditByUuid(String auditId) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.AUDIT_SERVICE.name(), SeverityTypes.INFORMATIONAL.ordinal(),
						"getAddressesByUuid Request: " + JsonUtil.toJsonString(auditId)));
		Audit audit = auditDAO.getAuditByUuid(auditId);
		if (null == audit)
			throw new NOT_FOUND("Audit not found");
		return audit;
	}

	@Override
	public List<Audit> getAudit(String userId, int roleId, @RequestParam Map<String, String> reqParam)
			throws Exception {
		String activity = null;
		List<Audit> auditList = new ArrayList<Audit>();
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("activity"))
				activity = reqParam.get("activity");
		}
		Audit audit = new Audit();
		audit.setUserId(userId);
		audit.setRoleId(roleId);
		validateAuditProfileDetails(audit);
		auditList = auditDAO.getAudit(userId, roleId, activity);
		return auditList;
	}

	private void validateAudit(Audit audit) throws Exception {
		validateAuditProfileDetails(audit);
		if (CommonUtils.isNullCheck(audit.getActivity()))
			throw new NOT_FOUND("Please mention the activity");
		if (CommonUtils.isNullCheck(audit.getTableName()))
			throw new NOT_FOUND("Please mention the table name");
		if (CommonUtils.isNullCheck(audit.getFieldName()))
			throw new NOT_FOUND("Please mention the field name");
		if (CommonUtils.isNullCheck(audit.getOldValue()))
			throw new NOT_FOUND("Please mention the old value");
		if (CommonUtils.isNullCheck(audit.getNewValue()))
			throw new NOT_FOUND("Please mention the new value");
	}

	private void validateAuditProfileDetails(Audit audit) throws Exception {
		if (0 >= audit.getRoleId())
			throw new NOT_FOUND("Please mention the roleId");
		else {
			Role role = Role.getRole(" " + audit.getRoleId());
			if (null == role)
				throw new ROLE_NOT_FOUND(" " + audit.getRoleId());
		}
		if (CommonUtils.isNullCheck(audit.getUserId()))
			throw new NOT_FOUND("Please mention the userId");
		else {
			ProfileDomainV2 validateProfile = profileDAOV2.getProfileByIdAndRole(audit.getUserId(), audit.getRoleId());
			if (null == validateProfile)
				throw new PROFILE_NOT_FOUND();
		}
	}

}
