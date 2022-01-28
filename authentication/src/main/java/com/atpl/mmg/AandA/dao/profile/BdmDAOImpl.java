package com.atpl.mmg.AandA.dao.profile;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.BDMDomain;
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
public class BdmDAOImpl implements BdmDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public BDMDomain save(BDMDomain bdmDomain) throws Exception {
		try {
			String sql = "INSERT INTO bdm (bdmId,profileId,createdDate,modifiedDate)"
					+ " VALUES(?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] {bdmDomain.getBdmId(),bdmDomain.getProfileId(),
					DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return bdmDomain;

			} else
				throw new SAVE_FAILED("BDM save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveBDM in BdmDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(BDMDomain bdmDomain) throws Exception {
		try {
			String sql = "UPDATE bdm SET modifiedDate=? WHERE bdmId=?";
			int res = 0;
	
			res = jdbcTemplate.update(sql, new Object[] { DateUtility.getDateFormat(new Date()), bdmDomain.getBdmId() });
				
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("BDM update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateBDM in BdmDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BDMDomain getBDMByProfileId(String profileId) throws Exception {
		try {
			String sql = "select * from bdm  where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<BDMDomain>(BDMDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("BDM not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getBDMByProfileId in BdmDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
