package com.atpl.mmg.dao.faredist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.domain.faredist.FareDistributionDomain;
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
public class FareDistributionDAOImpl implements FareDistributionDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String save(FareDistributionDomain fareDistributionDomain) throws Exception {
		try {
			String sql = "INSERT INTO faredistribution(uuid,franchiseId,roleId,fareDistributionTypeId,percentage,fixedCost,isPercentage,isActive,status,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { fareDistributionDomain.getUuid(), fareDistributionDomain.getFranchiseId(),
							fareDistributionDomain.getRoleId(), fareDistributionDomain.getFareDistributionTypeId(),
							fareDistributionDomain.getPercentage(), fareDistributionDomain.getFixedCost(),
							fareDistributionDomain.isIsPercentage(), fareDistributionDomain.isIsActive(),true,
							DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return " Fare distribution saved successfully";
			} else
				throw new SAVE_FAILED("Fare distribution save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception saveFareDistribution in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(FareDistributionDomain fareDistributionDomain) throws Exception {
		try {
			String sql = "UPDATE faredistribution SET roleId=?,percentage=?,fixedCost=?,fareDistributionTypeId=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { fareDistributionDomain.getRoleId(), fareDistributionDomain.getPercentage(),
							fareDistributionDomain.getFixedCost(), fareDistributionDomain.getFareDistributionTypeId(),
							DateUtility.setTimeZone(new Date()), fareDistributionDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Fare Distribution Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception updateFareDistribution in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<FareDistributionDomain> getFareDistributiones(boolean isActive, String franchiseId) throws Exception {
		try {
			String sql = "select franchiseId,fareDistributionTypeId,isPercentage,group_concat(roleId) as role,group_concat(percentage) as percentages,group_concat(uuid) as uuids,group_concat(fixedCost) as fixedCosts,group_concat(isActive) as isActives from faredistribution where isActive=? and franchiseId=? and status=? group by fareDistributionTypeId having count(isPercentage)>1";
			List<FareDistributionDomain> fareDistributionDomainList = jdbcTemplate.query(sql,
					new Object[] { isActive, franchiseId,true },
					new BeanPropertyRowMapper<FareDistributionDomain>(FareDistributionDomain.class));
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public List<FareDistributionDomain> getFDTypeAndFareDistributiones(String dfType, boolean isActive,
			String franchiseId) throws Exception {
		try {
			String sql = "select * from faredistributiontype fdt, faredistribution fd where fdt.uuid = fd.fareDistributionTypeId AND type=? AND fdt.isActive=? AND fd.isActive = ? AND fd.franchiseId =? and status=?";
			List<FareDistributionDomain> fareDistributionDomainList = jdbcTemplate.query(sql,
					new Object[] { dfType, isActive, isActive, franchiseId,true },
					new BeanPropertyRowMapper<FareDistributionDomain>(FareDistributionDomain.class));
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFDTypeAndFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFDTypeAndFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public List<FareDistributionDomain> getFareDistributiones() throws Exception {
		try {
			String sql = "select franchiseId,fareDistributionTypeId,isPercentage,group_concat(roleId) as role,group_concat(percentage) as percentages,group_concat(uuid) as uuids,group_concat(fixedCost) as fixedCosts,group_concat(isActive) as isActives from faredistribution where status=? group by fareDistributionTypeId having count(isPercentage)>1";
			List<FareDistributionDomain> fareDistributionDomainList = jdbcTemplate.query(sql, new Object[] { true},
					new BeanPropertyRowMapper<FareDistributionDomain>(FareDistributionDomain.class));
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistribution in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getFareDistribution in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public FareDistributionDomain getFareDistribution(String uuid) throws Exception {
		try {
			String sql = "select * from faredistribution where uuid=? ";
			FareDistributionDomain fareDistributionDomain = jdbcTemplate.queryForObject(sql, new Object[] { uuid },
					new BeanPropertyRowMapper<FareDistributionDomain>(FareDistributionDomain.class));
			return fareDistributionDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistribution in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getFareDistribution in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateIsActiveByFDTypeId(boolean isActive, String uuid) throws Exception {
		try {
			String sql = "UPDATE faredistribution SET isActive=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { isActive, DateUtility.setTimeZone(new Date()), uuid });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Fare Distribution Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception updateIsActiveByFDTypeId in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<FareDistributionDomain> getFareDistributiones(String fareDistributionTypeId, String franchiseId)
			throws Exception {
		try {
			String sql = null;
			List<FareDistributionDomain> fareDistributionDomainList = new ArrayList<FareDistributionDomain>();
			if (null != franchiseId) {
				sql = "select franchiseId,fareDistributionTypeId,isPercentage,group_concat(roleId) as role,group_concat(percentage) as percentages,group_concat(uuid) as uuids,group_concat(fixedCost) as fixedCosts,group_concat(isActive) as isActives from faredistribution where fareDistributionTypeId=? and franchiseId=? and status=? group by fareDistributionTypeId";
				fareDistributionDomainList = jdbcTemplate.query(sql,
						new Object[] { fareDistributionTypeId, franchiseId,true },
						new BeanPropertyRowMapper<FareDistributionDomain>(FareDistributionDomain.class));
			} else {
				sql = "select franchiseId,fareDistributionTypeId,isPercentage,group_concat(roleId) as role,group_concat(percentage) as percentages,group_concat(uuid) as uuids,group_concat(fixedCost) as fixedCosts,group_concat(isActive) as isActives from faredistribution where fareDistributionTypeId=? and status=? group by fareDistributionTypeId";
				fareDistributionDomainList = jdbcTemplate.query(sql, new Object[] { fareDistributionTypeId,true},
						new BeanPropertyRowMapper<FareDistributionDomain>(FareDistributionDomain.class));
			}
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public String delete(String uuid) throws Exception {
		try {
			String sql = "update faredistribution set status=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { false,uuid });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Fare Distribution delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception deleteFareDistribution in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<FareDistributionDomain> getFareDistributiones(String franchiseId) throws Exception {
		try {
			String sql = "select franchiseId,fareDistributionTypeId,isPercentage,group_concat(roleId) as role,group_concat(percentage) as percentages,group_concat(uuid) as uuids,group_concat(fixedCost) as fixedCosts,group_concat(isActive) as isActives from faredistribution where franchiseId=? and status=? group by fareDistributionTypeId";
			List<FareDistributionDomain> fareDistributionDomainList = jdbcTemplate.query(sql,
					new Object[] { franchiseId,true },
					new BeanPropertyRowMapper<FareDistributionDomain>(FareDistributionDomain.class));
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public List<FareDistributionDomain> getFareDistributioneByFDTypeId(String fareDistributionTypeId) throws Exception {
		try {
			String sql = "select * from faredistribution where fareDistributionTypeId=?";
			List<FareDistributionDomain> fareDistributionDomainList = jdbcTemplate.query(sql,
					new Object[] {fareDistributionTypeId },
					new BeanPropertyRowMapper<FareDistributionDomain>(FareDistributionDomain.class));
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}
	@Override
	public List<FareDistributionDomain> getFareDistributioneByTypeAndFranchiseId(String fareDistributionTypeId,
			String franchiseId) throws Exception {
		try {
			String sql = null;
			List<FareDistributionDomain> fareDistributionDomainList = new ArrayList<FareDistributionDomain>();
			if (null != franchiseId) {
				sql = "select * from faredistribution where fareDistributionTypeId=? and franchiseId=? and status=?";
				fareDistributionDomainList = jdbcTemplate.query(sql,
						new Object[] { fareDistributionTypeId, franchiseId,true },
						new BeanPropertyRowMapper<FareDistributionDomain>(FareDistributionDomain.class));
			} else {
				sql = "select * from faredistribution where fareDistributionTypeId=? and status=?";
				fareDistributionDomainList = jdbcTemplate.query(sql, new Object[] { fareDistributionTypeId,true},
						new BeanPropertyRowMapper<FareDistributionDomain>(FareDistributionDomain.class));
			}
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getFareDistributiones in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

}
