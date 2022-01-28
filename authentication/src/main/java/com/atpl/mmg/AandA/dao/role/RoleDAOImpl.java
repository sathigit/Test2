package com.atpl.mmg.AandA.dao.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.domain.role.RoleDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;

@Repository
public class RoleDAOImpl implements RoleDAO, Constants {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<RoleDomain> getRole(int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * from role");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			List<RoleDomain> roleDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<RoleDomain>(RoleDomain.class));
			return roleDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getRole in RoleDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new NOT_FOUND("Role not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getRole in RoleDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public RoleDomain getRoleNameBasedProfile(int id) throws Exception {

		try {
			String sql = "select r.roleName from profile p join role r on p.roleId = r.id where p.id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<RoleDomain>(RoleDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getRoleNameBasedProfile in RoleDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getRoleNameBasedProfile in RoleDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	@Override
	public RoleDomain getRoleCount() throws Exception {

		try {
			String sql = "select count(*) as id from role";
			return jdbcTemplate.queryForObject(sql, new Object[] { },
					new BeanPropertyRowMapper<RoleDomain>(RoleDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getRoleNameBasedProfile in RoleDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getRoleNameBasedProfile in RoleDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public String saveRole(RoleDomain roleDomain) throws Exception {
		try {
			String sql = "INSERT INTO role( id,roleName ) VALUES(?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { roleDomain.getId(), roleDomain.getRoleName() });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Role save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveRole in RoleDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateRole(RoleDomain roleDomain) throws Exception {
		try {
			String sql = "UPDATE role SET roleName=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { roleDomain.getRoleName(), roleDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Role save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateRole in RoleDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteRole(int id) throws Exception {
		try {
			String sql = "DELETE FROM role WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Deleted successfully";
			} else {
				throw new DELETE_FAILED("Delete failed");
			}
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception deleteRole in RoleDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public RoleDomain getRoleName(int id) throws Exception {
		try {
			String sql = "select * from role  where id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<RoleDomain>(RoleDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(),
					SeverityTypes.CRITICAL.ordinal(), "EmptyResultDataAccessException getRoleName in RoleDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new NOT_FOUND("Role not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROLE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getRoleName in RoleDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
