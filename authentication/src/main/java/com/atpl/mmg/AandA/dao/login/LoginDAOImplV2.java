package com.atpl.mmg.AandA.dao.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.login.LoginDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.INVALID_PASSWORD;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.CommonUtils;

@Repository
public class LoginDAOImplV2 implements LoginDAOV2 {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public LoginDomain authenticate(LoginDomain loginDomain) throws Exception {
		try {
			loginDomain.setPassword(CommonUtils.encriptString(loginDomain.getPassword()));
			String sql = "SELECT p.id,pr.roleId,r.path from profile p ,profilerole pr,role r where  p.id = pr.profileId and \r\n" + 
					"pr.roleId = r.id and \r\n" + 
					"(p.mobileNumber= ? or p.emailId=?) and p.password=? \r\n" + 
					" and pr.isActive = 1 and pr.roleId = ?";
			return jdbcTemplate.queryForObject(sql,
					new Object[] { loginDomain.getUserName(), loginDomain.getUserName(), loginDomain.getPassword(), loginDomain.getRoleId() },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LOGIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception authenticate in LoginDAOImplV2 " + JsonUtil.toJsonString(e.getMessage())));
			
			throw new INVALID_PASSWORD();
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LOGIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception authenticate in LoginDAOImplV2 " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LoginDomain getTotalRole(String profileId) throws Exception {
		try {
			String sql = "select count(*) As total FROM profilerole where profileId=?";

			return (LoginDomain) jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LOGIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getTotalRole in LoginDAOImplV2 " + JsonUtil.toJsonString(e.getMessage())));
			
			throw new NOT_FOUND("Role count not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LOGIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getTotalRole in LoginDAOImplV2 " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<LoginDomain> getRoleNames(String profileId) throws Exception {
		try {
			String sql = "SELECT r.* from role r, profilerole pr, profile p where  p.id = pr.profileId and\r\n" + 
					"pr.roleId = r.id and pr.isActive = 1 and p.id = ?";
			List<LoginDomain> loginDomain = jdbcTemplate.query(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
			return loginDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LOGIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getRoleNames in LoginDAOImplV2 " + JsonUtil.toJsonString(e.getMessage())));
			throw new NOT_FOUND("Role name not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LOGIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getRoleNames in LoginDAOImplV2 " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	
	
}
