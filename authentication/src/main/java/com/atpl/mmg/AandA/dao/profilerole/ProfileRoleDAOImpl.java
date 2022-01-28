package com.atpl.mmg.AandA.dao.profilerole;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profilerole.ProfileRoleDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class ProfileRoleDAOImpl implements ProfileRoleDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public ProfileRoleDomain save(ProfileRoleDomain profileRole) throws Exception {
		try {
			String sql = "INSERT INTO profilerole (uuid,profileId,roleId,appTokenId,webTokenId,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { profileRole.getUuid(),profileRole.getProfileId(),profileRole.getRoleId(),
					profileRole.getAppTokenId(),profileRole.getWebTokenId(),profileRole.isIsActive(), DateUtility.getDateFormat(new Date()),
					DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return profileRole;
			} else
				throw new SAVE_FAILED("Profile save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveProfileRole in ProfileDAOImplV2 " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileRoleDomain> getProfileRole(int roleId) throws Exception {
		try {
			String sql = "select * from profilerole where isActive = ? and roleId = ?";
			List<ProfileRoleDomain> profileRoleList = jdbcTemplate.query(sql.toString(), new Object[] { true,roleId },
					new BeanPropertyRowMapper<ProfileRoleDomain>(ProfileRoleDomain.class));
			return profileRoleList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileRole in ProfileRoleDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileRole in ProfileRoleDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileRoleDomain getRoleDet(String profileId, int roleId) throws Exception {
		try {
			String sql = "select * from profilerole where profileId = ? and roleId = ?";
			ProfileRoleDomain profileRoleDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] { profileId,roleId },
					new BeanPropertyRowMapper<ProfileRoleDomain>(ProfileRoleDomain.class));
			return profileRoleDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getRoleDet in ProfileRoleDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getRoleDet in ProfileRoleDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileRoleDomain> getRoleIdDetails(String profileId,boolean isActive) throws Exception{
		try {
			String sql = "SELECT pr.*, r.roleName FROM profilerole pr, role r where pr.roleId = r.id and pr.isActive = ? and pr.profileId = ?";
			List<ProfileRoleDomain> profileRoleList = jdbcTemplate.query(sql.toString(), new Object[] {isActive, profileId },
					new BeanPropertyRowMapper<ProfileRoleDomain>(ProfileRoleDomain.class));
			return profileRoleList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getRoleIdDetails in ProfileRoleDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getRoleIdDetails in ProfileRoleDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileRoleDomain> getProfileRoleByProfileId(String profileId) throws Exception {
		try {
			String sql = "SELECT * FROM profilerole where profileId = ?";
			List<ProfileRoleDomain> profileRoleList = jdbcTemplate.query(sql.toString(), new Object[] {profileId },
					new BeanPropertyRowMapper<ProfileRoleDomain>(ProfileRoleDomain.class));
			return profileRoleList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileRoleByProfileId in ProfileRoleDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileRoleByProfileId in ProfileRoleDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	@Override
	public List<ProfileRoleDomain> getAllprofileRoles() throws Exception {
		try {
			String sql = "select * from profilerole";
			List<ProfileRoleDomain> profileDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<ProfileRoleDomain>(ProfileRoleDomain.class));
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getAllprofileRoles in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getAllprofileRoles in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	
}
