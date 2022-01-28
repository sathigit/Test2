package com.atpl.mmg.AandA.service.role;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.dao.role.RoleDAO;
import com.atpl.mmg.AandA.domain.role.RoleDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.mapper.role.RoleMapper;
import com.atpl.mmg.AandA.model.profile.ListDto;
import com.atpl.mmg.AandA.model.role.RoleModel;
import com.atpl.mmg.AandA.service.profile.ProfileUtil;

@Service("roleService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RoleServiceImpl implements RoleService, Constants {

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	ProfileUtil profileUtil;

	@Override
	public ListDto getRole(Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		List<RoleDomain> roleDomainList = roleDAO.getRole(lowerBound, upperBound);
		RoleDomain roleDomain = roleDAO.getRoleCount();
		ListDto listDto = new ListDto(roleDomain.getId(), roleMapper.entityList(roleDomainList));
		return listDto;
	}

	@Override
	public RoleModel getRoleNameBasedProfile(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getRoleNameBasedProfile Request: " + JsonUtil.toJsonString(id)));
		RoleDomain insurancePlanDomain = roleDAO.getRoleNameBasedProfile(id);
		RoleModel insurancePlanModel = new RoleModel();
		if (insurancePlanDomain == null)
			throw new NOT_FOUND("Role not found");
		BeanUtils.copyProperties(insurancePlanDomain, insurancePlanModel);
		return insurancePlanModel;
	}

	@Override
	public String saveRole(RoleModel roleModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Role Request: " + JsonUtil.toJsonString(roleModel)));
		if (roleModel.getId() == 0)
			throw new NOT_FOUND("Id cannot be empty");
		if (null == roleModel.getRoleName())
			throw new NOT_FOUND("Role Name cannot be empty");

		RoleDomain RoleDomain = new RoleDomain();
		BeanUtils.copyProperties(roleModel, RoleDomain);
		return roleDAO.saveRole(RoleDomain);
	}

	@Override
	public String updateRole(RoleModel roleModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Role Request: " + JsonUtil.toJsonString(roleModel)));
		if (roleModel.getId() == 0)
			throw new NOT_FOUND("Id cannot be empty");

		if (null == roleModel.getRoleName())
			throw new NOT_FOUND("Role Name cannot be empty");

		RoleDomain roleDomain = new RoleDomain();
		BeanUtils.copyProperties(roleModel, roleDomain);
		return roleDAO.updateRole(roleDomain);
	}

	@Override
	public String deleteRole(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "deleteRole Request: " + JsonUtil.toJsonString(id)));
		RoleDomain roleDomain = new RoleDomain();
		RoleModel roleModel = new RoleModel();
		BeanUtils.copyProperties(roleModel, roleDomain);
		return roleDAO.deleteRole(id);
	}

	@Override
	public RoleModel getRoleName(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getRoleName Request: " + JsonUtil.toJsonString(id)));
		RoleDomain roleDomain = roleDAO.getRoleName(id);
		if (null == roleDomain)
			throw new NOT_FOUND("Role not found");
		return roleMapper.entity(roleDomain);
	}
}
