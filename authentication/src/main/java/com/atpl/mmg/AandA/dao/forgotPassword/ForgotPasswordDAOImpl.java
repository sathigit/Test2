package com.atpl.mmg.AandA.dao.forgotPassword;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.forgotPassword.ForgotPasswordDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;

@Repository
public class ForgotPasswordDAOImpl implements ForgotPasswordDAO {
	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public ForgotPasswordDomain getCustomer(String mobileNumber) throws Exception {
		try {
			String sql = "select id,roleId,mobileNumber,emailId,firstName from customer where mobileNumber=?";
			return (ForgotPasswordDomain) jdbcTemplate.queryForObject(sql, new Object[] { mobileNumber },
					new BeanPropertyRowMapper<ForgotPasswordDomain>(ForgotPasswordDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getMobileNumber in ForgotPasswordDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getMobileNumber in ForgotPasswordDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ForgotPasswordDomain getProfile(String mobileNumber) throws Exception {
		try {
			String sql = "select id,mobileNumber,emailId,firstName from profile where mobileNumber=?";
			return (ForgotPasswordDomain) jdbcTemplate.queryForObject(sql, new Object[] { mobileNumber },
					new BeanPropertyRowMapper<ForgotPasswordDomain>(ForgotPasswordDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getMobileNumber in ForgotPasswordDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getMobileNumber in ForgotPasswordDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ForgotPasswordDomain getprofileByEmail(String emailId) throws Exception {
		try {
			String sql = "select id,roleId,mobileNumber,emailId from profile where emailId=?";
			return (ForgotPasswordDomain) jdbcTemplate.queryForObject(sql, new Object[] { emailId },
					new BeanPropertyRowMapper<ForgotPasswordDomain>(ForgotPasswordDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfileByEmail in ForgotPasswordDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getProfileByEmail in ForgotPasswordDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}



	@Override
	public ForgotPasswordDomain getCustomerEmailId(String emailId) throws Exception {
		try {
			String sql = "select id,emailId from customer where emailId=?";
			return (ForgotPasswordDomain) jdbcTemplate.queryForObject(sql, new Object[] { emailId },
					new BeanPropertyRowMapper<ForgotPasswordDomain>(ForgotPasswordDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getEmailId in ForgotPasswordDAOImpl" + e.getMessage());
		return null;
		} catch (Exception e) {
			logger.error("Exception getEmailId in ForgotPasswordDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ForgotPasswordDomain getCustomerByMobileNumberOrEmailId(String mobileNumber, String emailId)
			throws Exception {
		try {
			String sql = "select * from customer where mobileNumber=? or emailId=?";
			return (ForgotPasswordDomain) jdbcTemplate.queryForObject(sql, new Object[] { mobileNumber, emailId },
					new BeanPropertyRowMapper<ForgotPasswordDomain>(ForgotPasswordDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error(
					"EmptyResultDataAccessException getCustomerMobileNumber in ForgotPasswordDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getMobileNumber in getCustomerMobileNumberOrEmail" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ForgotPasswordDomain getProfileByMobileNumberOrEmailId(String mobileNumber, String emailId,int roleId)
			throws Exception {
		try {
			if(roleId !=0) {
			String sql = "select * from profile p,profilerole pr where p.id=pr.profileId AND pr.isActive=? AND (p.mobileNumber=? or p.emailId=?) and pr.roleId =?";
			return (ForgotPasswordDomain) jdbcTemplate.queryForObject(sql, new Object[] {true, mobileNumber, emailId ,roleId},
					new BeanPropertyRowMapper<ForgotPasswordDomain>(ForgotPasswordDomain.class));
			}
			else
			{
				String sql = "select * from profile where mobileNumber=? or emailId=?";
				return (ForgotPasswordDomain) jdbcTemplate.queryForObject(sql, new Object[] { mobileNumber, emailId },
						new BeanPropertyRowMapper<ForgotPasswordDomain>(ForgotPasswordDomain.class));
			}
				
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfileByMobileNumberOrEmailId in ForgotPasswordDAOImpl"
					+ e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getMobileNumber in getProfileByMobileNumberOrEmailId" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}
	@Override
	public List<ForgotPasswordDomain> getProfileByMobileNumberOrEmailId(BigInteger mobileNumber, String emailId) throws Exception {
		try {
			String sql = "select * from profile p,profilerole pr where p.id=pr.profileId AND pr.isActive=? AND (p.mobileNumber=? or p.emailId=?)";
			List<ForgotPasswordDomain> forgotPasswordDomain = jdbcTemplate.query(sql, new Object[] { true,mobileNumber, emailId },
					new BeanPropertyRowMapper<ForgotPasswordDomain>(ForgotPasswordDomain.class));
			return forgotPasswordDomain;
				
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FORGOT_PASSWORD_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileByMobileNumberOrEmailId in ForgotPasswordDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FORGOT_PASSWORD_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileByMobileNumberOrEmailId in ForgotPasswordDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	@Override
	public ForgotPasswordDomain getProfileByEmailId(String emailId, int roleId) throws Exception {
		try {
			String sql = "select id,roleId,mobileNumber,emailId from profile p,profilerole pr where p.id = pr.profileId AND pr.isActive=? AND p.emailId=? AND pr.roleId=?";
			return (ForgotPasswordDomain) jdbcTemplate.queryForObject(sql, new Object[] {true, emailId,roleId },
					new BeanPropertyRowMapper<ForgotPasswordDomain>(ForgotPasswordDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FORGOT_PASSWORD_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileByEmailId in ForgotPasswordDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FORGOT_PASSWORD_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileByEmailId in ForgotPasswordDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ForgotPasswordDomain getProfileByMobileNumber(BigInteger mobileNumber, int roleId) throws Exception {
		try {
			String sql = "select id,roleId,mobileNumber,emailId from profile p,profilerole pr where p.id = pr.profileId AND pr.isActive=? AND p.mobileNumber=? AND pr.roleId=?";
			return (ForgotPasswordDomain) jdbcTemplate.queryForObject(sql, new Object[] {true, mobileNumber,roleId },
					new BeanPropertyRowMapper<ForgotPasswordDomain>(ForgotPasswordDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FORGOT_PASSWORD_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileByMobileNumber in ForgotPasswordDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FORGOT_PASSWORD_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileByMobileNumber in ForgotPasswordDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
