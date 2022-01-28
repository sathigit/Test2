package com.atpl.mmg.AandA.dao.profile;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.CoordinatorDomain;
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
public class CoordinatorDAOImpl implements CoordinatorDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public CoordinatorDomain save(CoordinatorDomain coordinatorDomain) throws Exception {
		try {
			String sql = "INSERT INTO coordinator (coordinatorId,profileId,franchiseId,latitude,longitude,isAgency,createdDate,modifiedDate)"
					+ " VALUES(?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { coordinatorDomain.getCoordinatorId(), coordinatorDomain.getProfileId(),
							coordinatorDomain.getFranchiseId(), coordinatorDomain.getLatitude(),
							coordinatorDomain.getLongitude(), coordinatorDomain.getIsAgency(),
							DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return coordinatorDomain;

			} else
				throw new SAVE_FAILED("Co ordinator save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception saveCoordinator in CoordinatorDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(CoordinatorDomain coordinatorDomain) throws Exception {
		try {
			String sql = "UPDATE coordinator SET franchiseId=?,latitude=?,longitude=?,isAgency=?,modifiedDate=? WHERE coordinatorId=?";
			int res = 0;
			res = jdbcTemplate.update(sql,
					new Object[] { coordinatorDomain.getFranchiseId(), coordinatorDomain.getLatitude(),
							coordinatorDomain.getLongitude(), coordinatorDomain.getIsAgency(),
							DateUtility.getDateFormat(new Date()), coordinatorDomain.getCoordinatorId() });

			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Coordinator update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateCoordinator in CoordinatorDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CoordinatorDomain getCoordinatorByProfileId(String profileId) throws Exception {
		try {
			String sql = "select * from coordinator  where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<CoordinatorDomain>(CoordinatorDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Coordinator not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getCoordinatorByProfileId in CoordinatorDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
