package com.atpl.mmg.dao.leadstatus;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
public class LeadStatusDAOImpl implements LeadStatusDAO {
	private static final Logger logger = LoggerFactory.getLogger(LeadStatusDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String save(LeadDomain leadDomain) throws Exception {
		try {
			String sql = "INSERT INTO leadstatus(uuid,name,createdDate,modifiedDate,status) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { leadDomain.getUuid(), leadDomain.getName(),
					DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()), leadDomain.isStatus() });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Lead Status save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_STATUS.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception saveLeadStatus in LeadStatusDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(LeadDomain leadDomain) throws Exception {
		try {
			String sql = "UPDATE leadstatus SET name=?,modifiedDate=?,status=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { leadDomain.getName(), DateUtility.setTimeZone(new Date()),
					leadDomain.isStatus(), leadDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Lead Status update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_STATUS.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception updateLeadStatus in LeadStatusDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<LeadDomain> getLeadStatus(int lowerBound,int upperBound) throws Exception {
		try {
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM leadstatus where status=? ORDER BY name asc");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT * FROM leadstatus where status=? ORDER BY name asc";
			List<LeadDomain> leadDomain = jdbcTemplate.query(sql.toString(), new Object[] { true },
					new BeanPropertyRowMapper<LeadDomain>(LeadDomain.class));
			return leadDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_STATUS.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getLeadStatus in LeadStatusDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String delete(String uuid) throws Exception {
		try {
			String sql = "DELETE FROM leadstatus WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { uuid });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Lead Status delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_STATUS.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception deleteLeadStatus in LeadStatusDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LeadDomain getLeadStatus(String uuid) throws Exception {
		try {
			String sql = "SELECT * FROM leadstatus where uuid=? ";
			return jdbcTemplate.queryForObject(sql, new Object[] { uuid },
					new BeanPropertyRowMapper<LeadDomain>(LeadDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Lead Status not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_STATUS.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getLeadStatus in LeadStatusDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getLeadStatusCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM leadstatus where status=? ";
			return jdbcTemplate.queryForObject(sql, new Object[] { true },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Lead Status not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_STATUS.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getLeadStatusCount in LeadStatusDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
