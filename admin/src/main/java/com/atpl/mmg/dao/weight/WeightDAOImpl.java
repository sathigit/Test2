package com.atpl.mmg.dao.weight;

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
import com.atpl.mmg.domain.weight.WeightDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;

@Repository
public class WeightDAOImpl implements WeightDAO {

	private static final Logger logger = LoggerFactory.getLogger(WeightDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<WeightDomain> getWeight(int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM weight ");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);

//			String sql = "SELECT * FROM weight";
			List<WeightDomain> weightDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<WeightDomain>(WeightDomain.class));
			return weightDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WEIGHT.name(),
					SeverityTypes.CRITICAL.ordinal(), "EmptyResultDataAccessException getWeight in WeightDAOImpl")
					+ JsonUtil.toJsonString(e.getMessage()));
			throw new NOT_FOUND("weight not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.WEIGHT.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getWeight in WeightDAOImpl") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public WeightDomain getWeight(int goodsId) throws Exception {
		try {
			String sql = "SELECT * FROM weight where goodsId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { goodsId },
					new BeanPropertyRowMapper<WeightDomain>(WeightDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getWeight in WeightDAOImpl" + e.getMessage());
			throw new NOT_FOUND("weight not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.WEIGHT.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getWeight in WeightDAOImpl") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public WeightDomain getWeight(String weight) throws Exception {
		try {
			String sql = "SELECT * FROM weight where weight=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { weight },
					new BeanPropertyRowMapper<WeightDomain>(WeightDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WEIGHT.name(),
					SeverityTypes.CRITICAL.ordinal(), "EmptyResultDataAccessException getWeight in WeightDAOImpl")
					+ JsonUtil.toJsonString(e.getMessage()));
			throw new NOT_FOUND("weight not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.WEIGHT.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getWeight in WeightDAOImpl ") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getWeightCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM weight";
			return jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.WEIGHT.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getWeightCount in WeightDAOImpl ") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
