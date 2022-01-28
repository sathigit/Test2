package com.atpl.mmg.AandA.dao.inactivereason;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.inactivereason.ReasonDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class ReasonDaoImpl implements ReasonDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String save(ReasonDomain reasonDomain) throws Exception {
		try {
			String sql = "INSERT INTO reason (uuid,roleId,profileId,reason,userId,userRoleId,previousStatus,changedStatus,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { reasonDomain.getUuid(), reasonDomain.getRoleId(), reasonDomain.getProfileId(),
							reasonDomain.getReason(), reasonDomain.getUserId(), reasonDomain.getUserRoleId(),
							reasonDomain.getPreviousStatus(), reasonDomain.getChangedStatus(),
							DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveReason in ReasonDaoImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ReasonDomain> getReasonsByRoleId(int roleId) throws Exception {
		try {
			String sql = "select * from reason where roleId = ?";
			List<ReasonDomain> reasonList = jdbcTemplate.query(sql.toString(), new Object[] { roleId },
					new BeanPropertyRowMapper<ReasonDomain>(ReasonDomain.class));
			return reasonList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(), SeverityTypes.ALERT.ordinal(),
							"Exception getReasonsByRoleId in ReasonDaoImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getReasonsByRoleId in ReasonDaoImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ReasonDomain> getReasonsByRoleByProfile(int roleId, String profileId, int lowerBound, int upperBound)
			throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from reason where roleId = ? and profileId=? order by createdDate desc");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			List<ReasonDomain> profileRoleList = jdbcTemplate.query(sql.toString(), new Object[] { roleId, profileId },
					new BeanPropertyRowMapper<ReasonDomain>(ReasonDomain.class));
			return profileRoleList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(),
					SeverityTypes.ALERT.ordinal(),
					"Exception getReasonsByRoleByProfile in ReasonDaoImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getReasonsByRoleByProfile in ReasonDaoImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ReasonDomain getReasonByUuid(String uuid) throws Exception {
		try {
			String sql = "select * from reason where uuid = ?";
			ReasonDomain reasonDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] { uuid },
					new BeanPropertyRowMapper<ReasonDomain>(ReasonDomain.class));
			return reasonDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(), SeverityTypes.ALERT.ordinal(),
							"Exception getReasonByUuid in ReasonDaoImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getReasonByUuid in ReasonDaoImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ReasonDomain> getReasonsByRoleByProfileAndStatus(int roleId, String profileId, boolean checkRequestedBy,
			int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			if (checkRequestedBy)
				sql.append(
						"select * from reason where roleId = ? and profileId=? and changedStatus in ('ACTIVE','INACTIVE')");
			else
				sql.append(
						"select * from reason where roleId = ? and profileId=? and changedStatus in ('PENDING','INPROCESS','ONHOLD')");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			List<ReasonDomain> profileRoleList = jdbcTemplate.query(sql.toString(), new Object[] { roleId, profileId },
					new BeanPropertyRowMapper<ReasonDomain>(ReasonDomain.class));
			return profileRoleList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(),
					SeverityTypes.ALERT.ordinal(),
					"Exception getReasonsByRoleByProfile in ReasonDaoImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.REASON_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getReasonsByRoleByProfile in ReasonDaoImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
