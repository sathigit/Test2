package com.atpl.mmg.AandA.dao.franchise;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.FranchiseDomainV2;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.FRANCHISE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class FranchiseDAOImpl implements FranchiseDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public FranchiseDomainV2 save(FranchiseDomainV2 franchiseDomainV2) throws Exception {
		try {
			String sql = "INSERT INTO franchise (franchiseId,companyName,organisationType,yearOfContract,startDate,endDate,"
					+ "gstNo,licenseNo,proprietorName,profileId,latitude,longitude,boardingEnquiryId,channelPartnerId,createdDate,modifiedDate) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { franchiseDomainV2.getFranchiseId(), franchiseDomainV2.getCompanyName(),
							franchiseDomainV2.getOrganisationType(), franchiseDomainV2.getYearOfContract(),
							franchiseDomainV2.getStartDate(), franchiseDomainV2.getEndDate(),
							franchiseDomainV2.getGstNo(), franchiseDomainV2.getLicenseNo(),
							franchiseDomainV2.getProprietorName(), franchiseDomainV2.getProfileId(),
							franchiseDomainV2.getLatitude(), franchiseDomainV2.getLongitude(),
							franchiseDomainV2.getBoardingEnquiryId(), franchiseDomainV2.getChannelPartnerId(),
							DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return franchiseDomainV2;
			} else
				throw new SAVE_FAILED("Save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception saveFranchise in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public FranchiseDomainV2 checkGstNumber(String gstNumber) throws Exception {
		try {
			String sql = "select * from franchise where gstNo=?";
			return (FranchiseDomainV2) jdbcTemplate.queryForObject(sql, new Object[] { gstNumber },
					new BeanPropertyRowMapper<FranchiseDomainV2>(FranchiseDomainV2.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(), SeverityTypes.ALERT.ordinal(),
							"Exception checkGstNumber in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception checkGstNumber in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public FranchiseDomainV2 checkLicenseNumber(String licenseNumber) throws Exception {
		try {
			String sql = "select * from franchise where licenseNo=?";
			return (FranchiseDomainV2) jdbcTemplate.queryForObject(sql, new Object[] { licenseNumber },
					new BeanPropertyRowMapper<FranchiseDomainV2>(FranchiseDomainV2.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.ALERT.ordinal(),
					"Exception checkLicenseNumber in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception checkLicenseNumber in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(FranchiseDomainV2 franchiseDomain, boolean status) throws Exception {
		try {
			String sql = null;
			int res;
			if (status) {
				sql = "UPDATE franchise SET companyName=?,yearOfContract=?,startDate=?,endDate=?,"
						+ "proprietorName=?,gstNo=?,licenseNo=?,organisationType=?,latitude =?,longitude=?,modifiedDate=? "
						+ "WHERE franchiseId=?";
				res = jdbcTemplate.update(sql,
						new Object[] { franchiseDomain.getCompanyName(), franchiseDomain.getYearOfContract(),
								franchiseDomain.getStartDate(), franchiseDomain.getEndDate(),
								franchiseDomain.getProprietorName(), franchiseDomain.getGstNo(),
								franchiseDomain.getLicenseNo(), franchiseDomain.getOrganisationType(),
								franchiseDomain.getLatitude(), franchiseDomain.getLongitude(),
								DateUtility.getDateFormat(new Date()), franchiseDomain.getFranchiseId() });
			} else {
				sql = "UPDATE franchise SET companyName=?, proprietorName=?,modifiedDate=? WHERE franchiseId=?";
				res = jdbcTemplate.update(sql,
						new Object[] { franchiseDomain.getCompanyName(), franchiseDomain.getProprietorName(),
								DateUtility.getDateFormat(new Date()), franchiseDomain.getFranchiseId() });
			}
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Update Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateFranchiseDetails in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public FranchiseDomainV2 getFranchiseByProfileId(String profileId) throws Exception {
		try {
			String sql = "select * from franchise  where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<FranchiseDomainV2>(FranchiseDomainV2.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFranchiseId in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new FRANCHISE_NOT_FOUND();
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFranchiseId in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<FranchiseDomainV2> getFranchises() throws Exception {
		try {
			List<FranchiseDomainV2> franchiseDomainList = new ArrayList<FranchiseDomainV2>();
			String sql = "SELECT * FROM franchise";
			franchiseDomainList = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<FranchiseDomainV2>(FranchiseDomainV2.class));

			return franchiseDomainList;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFranchises in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public FranchiseDomainV2 getFranchiseByFranchiseId(String franchiseId) throws Exception {
		try {
			String sql = "select * from franchise  where franchiseId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { franchiseId },
					new BeanPropertyRowMapper<FranchiseDomainV2>(FranchiseDomainV2.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFranchiseByFranchiseId in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new FRANCHISE_NOT_FOUND();
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFranchiseByFranchiseId in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateTag(boolean isTag) throws Exception {
		try {
			String sql = null;
			int res;
			sql = "UPDATE franchise SET isTag=? WHERE franchiseId=?";
			res = jdbcTemplate.update(sql, new Object[] { isTag });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Update Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateTag in FranchiseDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
