package com.atpl.mmg.AandA.dao.otp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.otp.Otp;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.OTP_NOT_SAVED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class OtpDAOImpl implements OtpDAO {
	private static final Logger logger = LoggerFactory.getLogger(OtpDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Otp save(Otp otp) {
		try {
			String sql = "INSERT INTO otp (mobileNumber,emailId,otp,createdDate,modifiedDate) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { otp.getMobileNumber(), otp.getEmailId(), otp.getOtp(),
					DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1)
				return otp;
			else
				throw new OTP_NOT_SAVED();
		} catch (Exception e) {
			logger.error("Exception in save otp", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public Otp update(Otp otp) {
		try {
			String sql = "UPDATE otp SET otp=?,modifiedDate=? WHERE mobileNumber=?";
			int res = jdbcTemplate.update(sql, otp.getOtp(), DateUtility.getDateFormat(new Date()),
					otp.getMobileNumber());
			if (res == 1)
				return otp;
			else
				return null;
		} catch (Exception e) {
			logger.error("Exception in update otp", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Otp getOtp(String mobileNumber, String email) {
		Otp otp = new Otp();
		try {

			String sql = null;
			if (!CommonUtils.isNullCheck(mobileNumber)) {
				sql = "SELECT * FROM otp where mobileNumber=? order by createdDate desc limit 1";
				otp = (Otp) jdbcTemplate.queryForObject(sql, new Object[] { mobileNumber },
						new BeanPropertyRowMapper(Otp.class));
			}
			if (!CommonUtils.isNullCheck(email)) {
				sql = "SELECT * FROM otp where emailId=? order by createdDate desc limit 1";
				otp = (Otp) jdbcTemplate.queryForObject(sql, new Object[] { email },
						new BeanPropertyRowMapper(Otp.class));
			}
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in getOtp", e);
			throw new BACKEND_SERVER_ERROR();
		}
		return otp;
	}

	@Override
	public String updateIsChecked(int id) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
			String sql = "UPDATE otp SET isChecked=?,modifiedDate=? WHERE id=?";
			int res = jdbcTemplate.update(sql, true, date, id);
			if (res == 1)
				return "Updated successfully";
			else
				return null;
		} catch (Exception e) {
			logger.error("Exception in updateIsChecked otp", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteOtp(int id) throws Exception {
		try {
			String sql = "DELETE FROM otp WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Otp delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception deleteOtp in OtpDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}