package com.atpl.mmg.dao.state;

import java.util.ArrayList;
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
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.state.StateDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;

@Repository
@SuppressWarnings("rawtypes")
public class StateDAOImpl implements StateDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(StateDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String addState(StateDomain stateDomain) throws Exception {
		try {
			String sql = "INSERT INTO states (name,country_id,shortName) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { stateDomain.getName(), stateDomain.getCountry_id(), stateDomain.getShortName() });

			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("State save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
							"Exception addState in StateDAOImpl ") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<StateDomain> getStates(int countryId, int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<StateDomain> stateDomain = new ArrayList<StateDomain>();
			if (countryId != 0) {
				sql.append("SELECT id,name,shortName,country_id from states where country_id=?");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				stateDomain = jdbcTemplate.query(sql.toString(), new Object[] { countryId },
						new BeanPropertyRowMapper<StateDomain>(StateDomain.class));
			} else {
				sql.append("SELECT id,name,shortName,country_id from states ");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				stateDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
						new BeanPropertyRowMapper<StateDomain>(StateDomain.class));
			}
			return stateDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(),
					SeverityTypes.DEBUG.ordinal(), "EmptyResultDataAccessException getStates in StateDAOImpl")
					+ JsonUtil.toJsonString(e.getMessage()));
			throw new NOT_FOUND("State not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
							"Exception getStates in StateDAOImpl") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String UpdateState(StateDomain stateDomain) throws Exception {
		try {
			String sql = "UPDATE states SET  name=?,country_id=?,shortName=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { stateDomain.getName(),stateDomain.getCountry_id(),stateDomain.getShortName(), stateDomain.getId() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("State update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
							"Exception UpdateState in StateDAOImpl") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public StateDomain getState(int stateId) throws Exception {
		try {
			String sql = "SELECT * FROM states where id=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { stateId },
					new BeanPropertyRowMapper<StateDomain>(StateDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(),
					SeverityTypes.DEBUG.ordinal(), "EmptyResultDataAccessException getState in StateDAOImpl")
					+ JsonUtil.toJsonString(e.getMessage()));
			throw new NOT_FOUND("State not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
							"Exception getState in StateDAOImpl") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<StateDomain> getStates(int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT id,name,shortName,country_id from states");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);

//			String sql = "SELECT id,name,shortName,country_id from states ";
			List<StateDomain> stateDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<StateDomain>(StateDomain.class));
			return stateDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
							"Exception getStates in StateDAOImpl") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public StateDomain getState(String stateName) throws Exception {
		try {
			String sql = "SELECT * FROM states where name=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { stateName },
					new BeanPropertyRowMapper<StateDomain>(StateDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getState in StateDAOImpl" + e.getMessage());
			throw new NOT_FOUND("State not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
							"Exception getState in StateDAOImpl ") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getStatesCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM states";
			return jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
							"Exception getStatesCount in StateDAOImpl ") + JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getStatesCountonCountryId(int countryId) throws Exception {
		try {
			String sql = "SELECT count(*) as total from states where country_id=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { countryId },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getStatesCountonCountryId in StateDAOImpl ")
					+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
