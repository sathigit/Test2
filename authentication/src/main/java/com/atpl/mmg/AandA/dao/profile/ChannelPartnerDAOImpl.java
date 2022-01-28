package com.atpl.mmg.AandA.dao.profile;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.ChannelPartnerDomain;
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
public class ChannelPartnerDAOImpl implements ChannelPartnerDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public ChannelPartnerDomain save(ChannelPartnerDomain channelPartnerDomain) throws Exception {
		try {
			String sql = "INSERT INTO channelpartner (channelPartnerId,profileId,bdmId,createdDate,modifiedDate)"
					+ " VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] {channelPartnerDomain.getChannelPartnerId(),channelPartnerDomain.getProfileId(),channelPartnerDomain.getBdmId(),
					DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return channelPartnerDomain;

			} else
				throw new SAVE_FAILED("Channel Partner save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveChannelPartner in ChannelPartnerDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(ChannelPartnerDomain channelPartnerDomain) throws Exception {
		try {
			String sql = "UPDATE channelpartner SET bdmId=?,modifiedDate=? WHERE channelPartnerId=?";
			int res = 0;
	
			res = jdbcTemplate.update(sql, new Object[] { channelPartnerDomain.getBdmId(),DateUtility.getDateFormat(new Date()), channelPartnerDomain.getChannelPartnerId() });
				
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Channel Partner update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateChannelPartner in ChannelPartnerDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ChannelPartnerDomain getChannelPartnerByProfileId(String profileId) throws Exception {
		try {
			String sql = "select * from channelpartner  where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<ChannelPartnerDomain>(ChannelPartnerDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Channel Partner not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getChannelPartnerByProfileId in ChannelPartnerDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
