package com.atpl.mmg.dao.blood;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.blood.BloodDomain;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;

@Repository
public class BloodDAOImpl implements BloodDAO, Constants {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<BloodDomain> getBloodGroup(int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
		      sql.append("SELECT * FROM blood");
		  	if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
			List<BloodDomain> bloodDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<BloodDomain>(BloodDomain.class));
			return bloodDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BLOOD.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getBloodGroup in BloodDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BloodDomain getBloodGroupById(int id) throws Exception {
		try {
			String sql = "SELECT * FROM blood  where bloodId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<BloodDomain>(BloodDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Blood not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BLOOD.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getBloodGroupById in BloodDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getBloodGroupCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM blood ";
			return jdbcTemplate.queryForObject(sql, new Object[] { },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Blood not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BLOOD.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getBloodGroupCount in BloodDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
