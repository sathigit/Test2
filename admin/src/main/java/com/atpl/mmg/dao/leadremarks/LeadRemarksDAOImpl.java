package com.atpl.mmg.dao.leadremarks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.lead.LeadDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class LeadRemarksDAOImpl implements LeadRemarksDAO {

	private static final Logger logger = LoggerFactory.getLogger(LeadRemarksDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String save(LeadDomain leadDomain) throws Exception {
		try {
			String sql = "INSERT INTO leadremarks(uuid,name,createdDate,modifiedDate,status) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { leadDomain.getUuid(), leadDomain.getName(),
					DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()), leadDomain.isStatus() });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Lead Remarks save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveLeadRemarks in LeadRemarksDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(LeadDomain leadDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE leadremarks SET name=?,modifiedDate=?,status=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { leadDomain.getName(),DateUtility.setTimeZone(new Date()),
					leadDomain.isStatus(), leadDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Lead Remarks update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateLeadRemarks in LeadRemarksDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<LeadDomain> getLeadRemarks(int lowerBound,int upperBound) throws Exception {
		try {
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM leadremarks where status=? ORDER BY name asc");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT * FROM leadremarks where status=? ORDER BY name asc";
			List<LeadDomain> leadDomain = jdbcTemplate.query(sql.toString(), new Object[] { true },
					new BeanPropertyRowMapper<LeadDomain>(LeadDomain.class));
			return leadDomain;
		}  catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getLeadRemarks in LeadRemarksDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
  		throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String delete(String uuid) throws Exception {
		try {
			String sql = "DELETE FROM leadremarks WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { uuid });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Lead Remarks delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception deleteLeadRemarks in LeadRemarksDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}	}

	@Override
	public LeadDomain getLeadRemarks(String uuid) throws Exception {
		try {
			String sql = "SELECT * FROM leadremarks where uuid=? ";
			return jdbcTemplate.queryForObject(sql, new Object[] { uuid },
					new BeanPropertyRowMapper<LeadDomain>(LeadDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getLeadRemarksByUUid in LeadRemarksDAOImpl" + JsonUtil.toJsonString(e.getMessage())));

			throw new NOT_FOUND("Lead Remarks not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getLeadRemarksByUUid in LeadRemarksDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getLeadRemarksCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM leadremarks where status=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { true },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getLeadRemarksCount in LeadRemarksDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
