package com.atpl.mmg.AandA.dao.fleetoperator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.FleetOperatorDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class FleetOperatorDAOImpl implements FleetOperatorDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public FleetOperatorDomain save(FleetOperatorDomain fleetOperatorDomain) throws Exception {
		try {
			String sql = "INSERT INTO fleetoperator (fleetId,companyName,yearOfContract,startDate,endDate,gstNo,franchiseId,"
					+ "profileId,latitude,longitude,boardingEnquiryId,createdDate,modifiedDate) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { fleetOperatorDomain.getFleetId(), fleetOperatorDomain.getCompanyName(),
							 fleetOperatorDomain.getYearOfContract(),fleetOperatorDomain.getStartDate(),fleetOperatorDomain.getEndDate(),
							 fleetOperatorDomain.getGstNo(),fleetOperatorDomain.getFranchiseId(),
							fleetOperatorDomain.getProfileId(), fleetOperatorDomain.getLatitude(),
							fleetOperatorDomain.getLongitude(),fleetOperatorDomain.getBoardingEnquiryId(),
							DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return fleetOperatorDomain;
			} else
				throw new SAVE_FAILED("FleetOperator save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FLEET_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception fleetoperator in FleetOperatorDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public FleetOperatorDomain checkGstNumber(String gstNumber) throws Exception {
		try {
			String sql = "select * from fleetoperator where gstNo=?";
			return (FleetOperatorDomain) jdbcTemplate.queryForObject(sql, new Object[] { gstNumber },
					new BeanPropertyRowMapper<FleetOperatorDomain>(FleetOperatorDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FLEET_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception checkGstNumber in FleetOperatorDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FLEET_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception checkGstNumber in FleetOperatorDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	@Override
	public String update(FleetOperatorDomain fleetOperatorDomain) throws Exception {
		try {
			String sql = "UPDATE fleetoperator SET companyName=?,yearOfContract=?,startDate=?,endDate=?,latitude=?,longitude=?,modifiedDate=?  WHERE fleetId=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { fleetOperatorDomain.getCompanyName(),fleetOperatorDomain.getYearOfContract(),fleetOperatorDomain.getStartDate(),fleetOperatorDomain.getEndDate(),
							fleetOperatorDomain.getLatitude(),fleetOperatorDomain.getLongitude(),DateUtility.getDateFormat(new Date()),
							fleetOperatorDomain.getFleetId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Updated Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FLEET_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception updateFleet in FleetOperatorDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public FleetOperatorDomain getFleetByProfileId(String profileId) throws Exception {
		try {
			String sql = "SELECT * FROM fleetoperator where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<FleetOperatorDomain>(FleetOperatorDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("FleetOperator not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FLEET_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getFleetByProfile in FleetOperatorDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<FleetOperatorDomain> getFleetOperators() throws Exception {
		List<FleetOperatorDomain> fleetOperatorDomainList = new ArrayList<FleetOperatorDomain>();
		try {
			String sql = "SELECT * FROM fleetoperator";
			fleetOperatorDomainList = jdbcTemplate.query(sql, new Object[] { },
					new BeanPropertyRowMapper<FleetOperatorDomain>(FleetOperatorDomain.class));
			return fleetOperatorDomainList;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FLEET_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getFleetOperators in FleetOperatorDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	
}
