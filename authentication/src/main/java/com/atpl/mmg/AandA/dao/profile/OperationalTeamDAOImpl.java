package com.atpl.mmg.AandA.dao.profile;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.OperationalTeamDomain;
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
public class OperationalTeamDAOImpl implements OperationalTeamDao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public OperationalTeamDomain save(OperationalTeamDomain operationalTeamDomain) throws Exception {
		try {
			String sql = "INSERT INTO operational_team (operationalTeamId,profileId,assignedStateId,createdDate,modifiedDate)"
					+ " VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] {operationalTeamDomain.getOperationalTeamId(),operationalTeamDomain.getProfileId(),operationalTeamDomain.getAssignedStateId(),
					DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return operationalTeamDomain;

			} else
				throw new SAVE_FAILED("Operational team save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveOperationalTeam in OperationalTeamDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(OperationalTeamDomain operationalTeamDomain) throws Exception {
		try {
			String sql = "UPDATE operational_team SET assignedStateId=?,modifiedDate=? WHERE operationalTeamId=?";
			int res = 0;
	
			res = jdbcTemplate.update(sql, new Object[] { operationalTeamDomain.getAssignedStateId(),DateUtility.getDateFormat(new Date()), 
					operationalTeamDomain.getOperationalTeamId() });
				
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Operational team update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateOperationalTeam in OperationalTeamDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public OperationalTeamDomain getOperationalTeamByProfileId(String profileId) throws Exception {
		try {
			String sql = "select * from operational_team  where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<OperationalTeamDomain>(OperationalTeamDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Operational team not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getOperationalTeamByProfileId in OperationalTeamDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	
	
}
