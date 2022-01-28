package com.atpl.mmg.dao.faredisttype;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.faredisttype.FareDistributionTypeDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.FARE_DISTRIBUTION_TYPE_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class FareDistributionTypeDAOImpl implements FareDistributionTypeDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String save(FareDistributionTypeDomain fareDistributionTypeDomain) throws Exception {
		try {
			String sql = "INSERT INTO faredistributiontype(uuid,name,description,role,isActive,type,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { fareDistributionTypeDomain.getUuid(), fareDistributionTypeDomain.getName(),
							fareDistributionTypeDomain.getDescription(), fareDistributionTypeDomain.getRole(),
							fareDistributionTypeDomain.isIsActive(), fareDistributionTypeDomain.getType(),
							DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return " Fare distribution type saved successfully";
			} else
				throw new SAVE_FAILED("Fare distribution type save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception saveFareDistributionType in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(FareDistributionTypeDomain fareDistributionTypeDomain) throws Exception {
		try {
			String sql = "UPDATE faredistributiontype  SET name=?,description=?,role =?,type=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { fareDistributionTypeDomain.getName(), fareDistributionTypeDomain.getDescription(),
							fareDistributionTypeDomain.getRole(), fareDistributionTypeDomain.getType(),
							DateUtility.setTimeZone(new Date()), fareDistributionTypeDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Fare Distribution Type Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateFareDistributionType in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<FareDistributionTypeDomain> getFareDistributionTypes(boolean isActive,int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from  faredistributiontype where  isActive=?");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "select * from  faredistributiontype where  isActive=?";
			List<FareDistributionTypeDomain> fareDistributionTypeDomainList = jdbcTemplate.query(sql.toString(),
					new Object[] { isActive },
					new BeanPropertyRowMapper<FareDistributionTypeDomain>(FareDistributionTypeDomain.class));
			return fareDistributionTypeDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributionTypes in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributionTypes in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public List<FareDistributionTypeDomain> getFareDistributionTypes(int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from faredistributiontype");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "select * from faredistributiontype";
			List<FareDistributionTypeDomain> fareDistributionTypeDomainList = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<FareDistributionTypeDomain>(FareDistributionTypeDomain.class));
			return fareDistributionTypeDomainList;
		}  catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributionTypes in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public FareDistributionTypeDomain getFareDistributionType(String uuid) throws Exception {
		try {
			String sql = "select * from  faredistributiontype where uuid=?";
			FareDistributionTypeDomain fareDistributionTypeDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { uuid },
					new BeanPropertyRowMapper<FareDistributionTypeDomain>(FareDistributionTypeDomain.class));
			return fareDistributionTypeDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributionType in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new FARE_DISTRIBUTION_TYPE_NOT_FOUND();
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributionType in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public FareDistributionTypeDomain getFareDistributionTypeByType(String type) throws Exception {
		try {
			String sql = "select * from  faredistributiontype where type=?";
			FareDistributionTypeDomain fareDistributionTypeDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { type },
					new BeanPropertyRowMapper<FareDistributionTypeDomain>(FareDistributionTypeDomain.class));
			return fareDistributionTypeDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributionTypeByType in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributionTypeByType in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	@Override
	public String delete(String uuid) throws Exception {
		try {
			String sql = "DELETE FROM faredistributiontype WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { uuid });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Fare Distribution Type delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception deleteFareDistributionType in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateIsActive(boolean isActive, String uuid) throws Exception {
		try {
			String sql = "UPDATE faredistributiontype  SET isActive=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { isActive, DateUtility.setTimeZone(new Date()), uuid });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Fare Distribution Type Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception updateIsActive in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getFareDistributionTypesCount(boolean isActive) throws Exception {
		try {
			String	sql="select count(*) as total from  faredistributiontype where  isActive=?";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { isActive },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributionTypes in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getFareDistributionTypesCount() throws Exception {
		try {
			String	sql="select count(*) as total from  faredistributiontype";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql,
					new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributionTypesCount in FareDistributionTypeDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
