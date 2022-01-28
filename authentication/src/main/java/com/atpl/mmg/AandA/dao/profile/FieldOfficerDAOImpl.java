package com.atpl.mmg.AandA.dao.profile;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.FieldOfficerDomain;
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
public class FieldOfficerDAOImpl implements FieldOfficerDAO{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public FieldOfficerDomain save(FieldOfficerDomain fieldOfficerDomain) throws Exception {
		try {
			String sql = "INSERT INTO fieldofficer (fieldOfficerId,profileId,franchiseId,createdDate,modifiedDate)"
					+ " VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { fieldOfficerDomain.getFieldOfficerId(),fieldOfficerDomain.getProfileId(),fieldOfficerDomain.getFranchiseId(),
					DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return fieldOfficerDomain;

			} else
				throw new SAVE_FAILED("Field Officer save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveFieldOfficer in FieldOfficerDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(FieldOfficerDomain fieldOfficerDomain) throws Exception {
		try {
			String sql = "UPDATE fieldofficer SET franchiseId=?,modifiedDate=? WHERE fieldOfficerId=?";
			int res = 0;
	
			res = jdbcTemplate.update(sql, new Object[] { fieldOfficerDomain.getFranchiseId(),DateUtility.getDateFormat(new Date()), fieldOfficerDomain.getFieldOfficerId() });
				
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Field Officer update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateFieldOfficer in FieldOfficerDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public FieldOfficerDomain getFieldOffcierByProfileId(String profileId) throws Exception {
		try {
			String sql = "select * from fieldofficer  where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<FieldOfficerDomain>(FieldOfficerDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Field Offcier not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getFieldOffcierByProfileId in FieldOfficerDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
}
