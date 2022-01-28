package com.atpl.mmg.AandA.dao.profile;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.BDODomain;
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
public class BdoDAOImpl implements BdoDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public BDODomain save(BDODomain bdoDomain) throws Exception {
		try {
			String sql = "INSERT INTO bdo (bdoId,profileId,bdmId,createdDate,modifiedDate)" + " VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { bdoDomain.getBdoId(), bdoDomain.getProfileId(), bdoDomain.getBdmId(),
							DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return bdoDomain;

			} else
				throw new SAVE_FAILED("BDO save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveBdo in BdoDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(BDODomain bdoDomain) throws Exception {
		try {
			String sql = "UPDATE bdo SET bdmId=?, modifiedDate=? WHERE bdoId=?";
			int res = 0;

			res = jdbcTemplate.update(sql,
					new Object[] { DateUtility.getDateFormat(new Date()), bdoDomain.getBdoId() });

			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("BDO update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateBdo in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BDODomain getBDOByProfileId(String profileId) throws Exception {
		try {
			String sql = "select * from bdo  where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<BDODomain>(BDODomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("BDO not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getBDOByProfileId in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateFranchiseId(BDODomain bdoDomain) throws Exception {
		try {
			String sql = "UPDATE bdo SET franchiseId=?, modifiedDate=? WHERE bdoId=?";
			int res = 0;
			res = jdbcTemplate.update(sql, new Object[] { bdoDomain.getFranchiseId(),
					DateUtility.getDateFormat(new Date()), bdoDomain.getBdoId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("BDO update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateFranchiseId in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
