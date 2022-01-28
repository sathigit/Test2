package com.atpl.mmg.AandA.dao.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.domain.Audit;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class AuditDAOImpl implements AuditDAO, Constants {

	protected static Logger logger = LoggerFactory.getLogger(AuditDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Audit save(Audit audit) throws Exception {
		try {
			String sql = "INSERT INTO audit (auditId,userId,roleId,tableName,fieldName,oldValue,newValue,activity,createdDate) VALUES(?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { audit.getAuditId(), audit.getUserId(), audit.getRoleId(), audit.getTableName(),
							audit.getFieldName(), audit.getOldValue(), audit.getNewValue(), audit.getActivity(),
							DateUtility.setTimeZone(new Date()) });
			if (res == 1)
				return audit;
			else
				throw new SAVE_FAILED("Audit save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.AUDIT_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception while saving audit " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public Audit getAuditByUuid(String auditId) throws Exception {
		try {
			String sql = "select * from audit where auditId = ?";
			Audit audit = jdbcTemplate.queryForObject(sql.toString(), new Object[] { auditId },
					new BeanPropertyRowMapper<Audit>(Audit.class));
			return audit;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.AUDIT_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getAuditByUuid in AuditDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getAuditByUuid in AuditDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<Audit> getAudit(String userId, int roleId, String activity) throws Exception {
		List<Audit> audit = new ArrayList<Audit>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from audit ");
			if (!CommonUtils.isNullCheck(userId) && roleId > 0) {
				if (!CommonUtils.isNullCheck(activity)) {
					sql.append(" where userId ='" + userId + "' and roleId ='" + roleId + "' and activity ='" + activity
							+ "' ");
				}
				sql.append(" where userId ='" + userId + "' and roleId ='" + roleId + "' ");
			}
			audit = jdbcTemplate.query(sql.toString(), new Object[] {}, new BeanPropertyRowMapper<Audit>(Audit.class));
			return audit;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.AUDIT_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getAudit in AuditDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getAudit in AuditDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
