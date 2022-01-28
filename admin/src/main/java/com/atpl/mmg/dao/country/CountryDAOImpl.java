package com.atpl.mmg.dao.country;

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
import com.atpl.mmg.domain.country.CountryDomain;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;

@Repository
public class CountryDAOImpl implements CountryDAO, Constants {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public String addCountry(CountryDomain countryDomain) throws Exception {
		try {
			String sql = "INSERT INTO country (shortname,name,code) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { countryDomain.getShortname(), countryDomain.getName(), countryDomain.getCode() });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Country save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.COUNTRY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception addCountry in CountryDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public List<CountryDomain> getCountry(int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT name,code,id,shortname FROM country");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT name,code,id,shortname FROM country";
			List<CountryDomain> countryDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<CountryDomain>(CountryDomain.class));
			return countryDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.COUNTRY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCountry in CountryDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CountryDomain getCountry(int countryId) throws Exception {
		try {
			String sql = "SELECT * FROM country where id=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { countryId },
					new BeanPropertyRowMapper<CountryDomain>(CountryDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.COUNTRY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCountry in CountryDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new NOT_FOUND("Country not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.COUNTRY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCountry in CountryDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String UpdateCountry(CountryDomain countryDomain) throws Exception {
		try {
			String sql = "UPDATE country SET  shortname=?,name=?,code=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { countryDomain.getShortname(), countryDomain.getName(),
					countryDomain.getCode(), countryDomain.getId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Country update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.COUNTRY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception UpdateCountry in CountryDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String DeleteCountry(int countryId) throws Exception {
		try {
			String sql = "DELETE FROM item WHERE id=?";
			int res = jdbcTemplate.update(sql);
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Country delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.COUNTRY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception delete in CountryDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CountryDomain getCountry(String countryname) throws Exception {
		try {
			String sql = "SELECT name,shortname,code,id FROM country where name like ? ";
			return jdbcTemplate.queryForObject(sql, new Object[] { countryname },
					new BeanPropertyRowMapper<CountryDomain>(CountryDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Country not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.COUNTRY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCountry in CountryDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public DashboardDomain getCountryCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM country";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.COUNTRY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCountry in CountryDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
