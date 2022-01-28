package com.atpl.mmg.dao.driverType;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.dao.city.CityDAOImpl;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.driverType.DriverTypeDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;

@Repository
public class DriverTypeDAOImpl implements DriverTypeDAO {

	private static final Logger logger = LoggerFactory.getLogger(CityDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<DriverTypeDomain> getDriverType(int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * from drivertype");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT * from drivertype";
			List<DriverTypeDomain> driverDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<DriverTypeDomain>(DriverTypeDomain.class));
			return driverDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.DRIVER_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getDriverType in DriverDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DriverTypeDomain getDriverTypeById(int id) throws Exception {
		try {
			String sql = "SELECT * FROM drivertype  where id=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<DriverTypeDomain>(DriverTypeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.DRIVER_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"DriverType not found" + JsonUtil.toJsonString(e.getMessage())));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.DRIVER_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getDriverTypeById in DriverTypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
		return null;
	}

	@Override
	public List<DriverTypeDomain> getLicenceCategory(int lowerBound,int upperBound) throws Exception {
		try 
		{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * from licencecategory");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT * from licencecategory";
			List<DriverTypeDomain> driverDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<DriverTypeDomain>(DriverTypeDomain.class));
			return driverDomain;
		}  catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LICENSE_CATEGORY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getLicenceCategory in DriverDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DriverTypeDomain getLicenceCategoryById(int id) throws Exception {
		try {
			String sql = "SELECT * FROM licencecategory  where id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<DriverTypeDomain>(DriverTypeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LICENSE_CATEGORY.name(), SeverityTypes.CRITICAL.ordinal(),
							"LicenceCategory not found in DriverDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LICENSE_CATEGORY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getLicenceCategoryById in DriverTypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
		return null;
	}

	@Override
	public DashboardDomain getDriverTypeCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM drivertype";
			return jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.DRIVER_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getDriverTypeCount in DriverTypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getLicenceCategoryCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM licencecategory";
			return jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LICENSE_CATEGORY.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getLicenceCategoryCount in DriverTypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}

}
