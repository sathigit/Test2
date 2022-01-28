package com.atpl.mmg.AandA.dao.session;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.domain.session.SessionDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;

@Repository
public class SessionDAOImpls implements SessionDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(SessionDAOImpls.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	public SessionDomain saveSession(SessionDomain session) {
		try {
			String sql = "INSERT INTO session (sessionId,userId,lastAccessTime,accessToken,emailId,isActive,roleId) VALUES(?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { session.getSessionId(), session.getUserId(), new Date(),session.getAccessToken(),
					session.getEmailId(), session.isIsActive(),session.getRoleId() });
			if (res == 1) {
				return session;
			} else
				throw new SAVE_FAILED("Profile save failed");
		} catch (Exception e) {
			logger.error("Exception saveSession in SessionDAOImpls" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	public String updateSession(SessionDomain session) {
		try {
			String sql = "UPDATE session SET isActive=? WHERE sessionId=? ";
			int res = jdbcTemplate.update(sql, session.isIsActive(), session.getSessionId());
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Session update failed");
		} catch (Exception e) {
			logger.error("Exception updateSession in SessionDAOImpls" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();

		}

	}

	public SessionDomain getSession(String sessionId) {
		try {
			String sql = "SELECT * FROM session where sessionId=?";
			return (SessionDomain) jdbcTemplate.queryForObject(sql, new Object[] { sessionId },
					new BeanPropertyRowMapper<SessionDomain>(SessionDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getSession in SessionDAOImpls" + e.getMessage());
			throw new NOT_FOUND("Session not found");
		} catch (Exception e) {
			logger.error("Exception getSession in SessionDAOImpls" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public SessionDomain getSessionId(String userId) {
		try {
			String sql = "SELECT * FROM session where userId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { userId },
					new BeanPropertyRowMapper<SessionDomain>(SessionDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getSession in SessionDAOImpls" + e.getMessage());
			throw new NOT_FOUND("Session not found");
		} catch (Exception e) {
			logger.error("Exception getSession in SessionDAOImpls" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateRecentSession(int userId) throws Exception {
		try {
			String sql = "UPDATE session SET isActive=0 WHERE userId=? ";
			int res = jdbcTemplate.update(sql, userId);
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Session update failed");
		} catch (Exception e) {
			logger.error("Exception updateRecentSession in SessionDAOImpls" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public SessionDomain getLastAccessTime(String id) throws Exception {
		try {
			String sql = "select s.userId,s.lastAccessTime,c.mobileNumber,c.cityId,c.firstName from customer c,session s where c.id = s.userId and c.id = ? order by s.lastAccessTime desc limit 1";
			return (SessionDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<SessionDomain>(SessionDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getLastAccessTime in SessionDAOImpls" + e.getMessage());
			throw new NOT_FOUND("Session not found");
		} catch (Exception e) {
			logger.error("Exception getLastAccessTime in SessionDAOImpls" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<SessionDomain> getLastAccessTime() throws Exception {
		try {
			String sql = "select distinct c.id as userId from customer c,session s where c.id = s.userId and s.isActive=0 order by s.lastAccessTime;";
			List<SessionDomain> loginDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<SessionDomain>(SessionDomain.class));
			return loginDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getLastAccessTime in SessionDAOImpls" + e.getMessage());
			throw new NOT_FOUND("Session not found");
		} catch (Exception e) {
			logger.error("Exception getLastAccessTime in SessionDAOImpls" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public SessionDomain getSessionByProfileId(String userId,int roleId) throws Exception {
		try {
			String sql = "SELECT * FROM session where userId = ? and isActive = ? and roleId = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] { userId, true,roleId },
					new BeanPropertyRowMapper<SessionDomain>(SessionDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getSession in SessionDAOImpls" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getSession in SessionDAOImpls" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	@Override
	public SessionDomain getSessionByProfileIdAndRoleId(String profileId, boolean isActive, Integer roleId) throws Exception {
		try {
			String sql = "select s.* from profile p,profilerole pr,session s where p.id = pr.profileId and pr.profileId = s.userId and pr.isActive=? and pr.roleId =? and s.userId=?  and s.isActive = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] { isActive, roleId,profileId, isActive },
					new BeanPropertyRowMapper<SessionDomain>(SessionDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getSessionByProfileIdAndRoleId in SessionDAOImpls" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getSessionByProfileIdAndRoleId in SessionDAOImpls" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
}
