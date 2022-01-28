package com.atpl.mmg.dao.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.city.CityDomain;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
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
public class CityDAOImpl implements CityDAO, Constants {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String addCity(CityDomain cityDomain) throws Exception {
		try {
			String sql = "INSERT INTO cities (id,name,state_id,shortName) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] {cityDomain.getId(), cityDomain.getName(), cityDomain.getState_id(), cityDomain.getShortName() });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("City save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception addCity in CityDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<CityDomain> getCities(int countryId, int stateId, int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<CityDomain> cityDomain = null;
			if (countryId != 0 && stateId != 0) {
				sql.append(
						"SELECT c.id,c.name,c.state_id,c.pinCode,c.shortName,c.alias,c.isGovtPreferred from cities c,states s where c.state_id=s.id and s.country_id = ? and c.state_id=?");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				cityDomain = jdbcTemplate.query(sql.toString(), new Object[] { countryId, stateId },
						new BeanPropertyRowMapper<CityDomain>(CityDomain.class));
			} else if (countryId != 0) {
				sql.append(
						"SELECT c.id,c.name,c.state_id,c.pinCode,c.shortName,c.alias,c.isGovtPreferred from cities c,states s where c.state_id=s.id and s.country_id = ?");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				cityDomain = jdbcTemplate.query(sql.toString(), new Object[] { countryId },
						new BeanPropertyRowMapper<CityDomain>(CityDomain.class));
			} else {
				sql.append("SELECT id,name,state_id,pinCode,shortName,alias,isGovtPreferred from cities where state_id=?");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				cityDomain = jdbcTemplate.query(sql.toString(), new Object[] { stateId },
						new BeanPropertyRowMapper<CityDomain>(CityDomain.class));
			}
			return cityDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCities in CityDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateCity(CityDomain cityDomain) throws Exception {
		try {
			String sql = "UPDATE cities SET  name=?,state_id = ?,alias = ?,shortName = ?,pinCode=?,isGovtPreferred=? WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { cityDomain.getName(), cityDomain.getState_id(), cityDomain.getAlias(),
							cityDomain.getShortName(),cityDomain.getPinCode(), cityDomain.getIsGovtPreferred(), cityDomain.getId() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("City update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateCity in CityDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CityDomain getCity(int cityId) throws Exception {
		try {
			String sql = "SELECT * FROM cities where id=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { cityId },
					new BeanPropertyRowMapper<CityDomain>(CityDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("City not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCity in CityDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CityDomain getCityId(String name) throws Exception {
		try {
			String sql = "SELECT * FROM cities where name=? or FIND_IN_SET(?,alias)";
			return jdbcTemplate.queryForObject(sql, new Object[] { name, name },
					new BeanPropertyRowMapper<CityDomain>(CityDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("City not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCity in CityDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateAlias(CityDomain cityDomain) throws Exception {
		try {
			String sql = "update cities set alias=? where name=?";
			int res = jdbcTemplate.update(sql, new Object[] { cityDomain.getAlias(), cityDomain.getName() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("City update failed");
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("City not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateAlias in CityDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<CityDomain> getAllCities(boolean getCities, int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			if (getCities) {
				sql.append("SELECT * from cities where state_id  in (SELECT id FROM states where country_id ='"
						+ Constants.INDIA + "')");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
//				sql = "SELECT * from cities where state_id  in (SELECT id FROM states where country_id = 101)";
			} else {
				sql.append("SELECT * from cities");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
//				sql = "SELECT * from cities";
			}
			List<CityDomain> cityDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<CityDomain>(CityDomain.class));
			return cityDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getAllCities in CityDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getCitiesByStateCount(int stateId) throws Exception {
		try {
			String sql = null;
			sql = "SELECT count(*) as total from cities where state_id=?";
			DashboardDomain DashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { stateId },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return DashboardDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCitiesByStateCount in CityDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public DashboardDomain getAllCitiesCount(boolean getCities) throws Exception {
		String sql = null;
		try {
			if (getCities) {
				sql = "SELECT count(*) as total from cities where state_id  in (SELECT id FROM states where country_id ='"
						+ Constants.INDIA + "')";
			} else {
				sql = "SELECT count(*) as total from cities";
			}
			DashboardDomain DashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return DashboardDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getAllCities in CityDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
