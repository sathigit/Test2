package com.atpl.mmg.dao.leadprofession;


import java.util.Date;
import java.util.List;

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
public class LeadProfessionDAOImpl implements LeadProfessionDAO {

	private static final Logger logger = LoggerFactory.getLogger(LeadProfessionDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String save(LeadDomain leadDomain) throws Exception {
		try {
			String sql = "INSERT INTO leadprofession(uuid,name,createdDate,modifiedDate,status) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { leadDomain.getUuid(), leadDomain.getName(),
					DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()), leadDomain.isStatus() });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Lead Profession save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_PROFESSION.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveLeadProfession in LeadProfessionDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
		throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(LeadDomain leadDomain) throws Exception {
		try {
			String sql = "UPDATE leadprofession SET name=?,modifiedDate=?,status=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { leadDomain.getName(), DateUtility.setTimeZone(new Date()),
					leadDomain.isStatus(), leadDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Lead Profession update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_PROFESSION.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateLeadProfession in LeadProfessionDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<LeadDomain> getLeadProfession(int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM leadprofession where status=? ORDER BY name asc");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT * FROM leadprofession where status=? ORDER BY name asc";
			List<LeadDomain> leadDomain = jdbcTemplate.query(sql.toString(), new Object[] { true },
					new BeanPropertyRowMapper<LeadDomain>(LeadDomain.class));
			return leadDomain;
		}  catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_PROFESSION.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getLeadProfession in LeadProfessionDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String delete(String uuid) throws Exception {
		try {
			String sql = "DELETE FROM leadprofession WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { uuid });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Lead Profession delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_PROFESSION.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception deleteLeadProfession in LeadProfessionDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LeadDomain getLeadProfession(String uuid) throws Exception {
		try {
			String sql = "SELECT * FROM leadprofession where uuid=? ";
			return jdbcTemplate.queryForObject(sql, new Object[] { uuid },
					new BeanPropertyRowMapper<LeadDomain>(LeadDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Lead Profession not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_PROFESSION.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getLeadProfessionByUuid in LeadProfessionDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getLeadProfessionCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM leadprofession where status=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { true },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		}  catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_PROFESSION.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getLeadProfessionCount in LeadProfessionDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
