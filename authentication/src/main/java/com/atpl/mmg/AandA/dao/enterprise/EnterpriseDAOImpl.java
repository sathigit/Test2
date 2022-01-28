package com.atpl.mmg.AandA.dao.enterprise;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.EnterpriseDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class EnterpriseDAOImpl implements EnterpriseDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public EnterpriseDomain save(EnterpriseDomain enterpriseDomain) throws Exception {
		try {
			String sql = "INSERT INTO enterprise( enterpriseId,companyName,gstNo,licenseNo,entrepreneurName,franchiseId,"
					+ "organisationType,yearOfContract,startDate,endDate,"
					+ "profileId,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { enterpriseDomain.getEnterpriseId(), enterpriseDomain.getCompanyName(),
							enterpriseDomain.getGstNo(), enterpriseDomain.getLicenseNo(),enterpriseDomain.getEntrepreneurName(),
							enterpriseDomain.getFranchiseId(),enterpriseDomain.getOrganisationType(),
							enterpriseDomain.getYearOfContract(), enterpriseDomain.getStartDate(),enterpriseDomain.getEndDate(),enterpriseDomain.getProfileId(),
							DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1)
				return enterpriseDomain;
			else
				throw new SAVE_FAILED("Enterprenuer save failed");

		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ENTERPRISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception saveEntrepreneur in EnterpriseDaoImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	

	@Override
	public String update(EnterpriseDomain enterpriseDomain) throws Exception {
		try {
			String sql = "UPDATE enterprise SET companyName=?, yearOfContract=?,startDate=?,endDate=?,gstNo=?,licenseNo=?,entrepreneurName=?,"
					+ "latitude=?,longitude=?,organisationType=? WHERE warehouseId=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { enterpriseDomain.getCompanyName(),enterpriseDomain.getYearOfContract(),enterpriseDomain.getStartDate(),enterpriseDomain.getEndDate(),
							enterpriseDomain.getGstNo(),enterpriseDomain.getLicenseNo(),enterpriseDomain.getEntrepreneurName(),enterpriseDomain.getLatitude(),
							enterpriseDomain.getLongitude(),enterpriseDomain.getOrganisationType(),enterpriseDomain.getEnterpriseId()});
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Updated Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WAREHOUSE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception updateWareHouse in WareHouseDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EnterpriseDomain checkLicenseNumber(String licenseNumber) throws Exception {
		try {
			String sql = "select * from enterprise where licenseNo=?";
			return (EnterpriseDomain) jdbcTemplate.queryForObject(sql, new Object[] { licenseNumber },
					new BeanPropertyRowMapper<EnterpriseDomain>(EnterpriseDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ENTERPRISE_SERVICE.name(),
					SeverityTypes.ALERT.ordinal(), "Exception checkLicenseNumber in EnterpriseDaoImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ENTERPRISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception checkLicenseNumber in EnterpriseDaoImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EnterpriseDomain checkGstNumber(String gstNumber) throws Exception {
		try {
			String sql = "select * from enterprise where gstNo=?";
			return (EnterpriseDomain) jdbcTemplate.queryForObject(sql, new Object[] { gstNumber },
					new BeanPropertyRowMapper<EnterpriseDomain>(EnterpriseDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ENTERPRISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "EmptyResultDataAccessException checkGstNumber in EnterpriseDaoImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ENTERPRISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception checkLicenseNumber in EnterpriseDaoImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EnterpriseDomain getEnterpriseByProfileId(String profileId) throws Exception {
		try {
			String sql = "select * from enterprise where profileId=?";
			return (EnterpriseDomain) jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<EnterpriseDomain>(EnterpriseDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ENTERPRISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getEnterpriseByProfileId in EnterpriseDaoImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ENTERPRISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getEnterpriseByProfileId in EnterpriseDaoImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<EnterpriseDomain> getEnterprises() throws Exception {
		try {
			String sql = "SELECT * FROM enterprise";
			List<EnterpriseDomain> entrepreneurList = jdbcTemplate.query(sql, new Object[] {  },
					new BeanPropertyRowMapper<EnterpriseDomain>(EnterpriseDomain.class));
			return entrepreneurList;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ENTERPRISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getEnterprises in EnterpriseDaoImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
