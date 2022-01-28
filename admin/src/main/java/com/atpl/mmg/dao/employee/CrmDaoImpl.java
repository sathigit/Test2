package com.atpl.mmg.dao.employee;

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

import com.atpl.mmg.domain.employee.CrmDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.utils.DateUtility;

@SuppressWarnings("unused")
@Repository
public class CrmDaoImpl implements CrmDao {

	private static final Logger logger = LoggerFactory.getLogger(CrmDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String savePerformance(CrmDomain crmDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO admin.performance (id,name,emailId,mobileNumber,link,profileId,creationDate,modificationDate) VALUES(?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { crmDomain.getId(), crmDomain.getName(), crmDomain.getEmailId(),
							crmDomain.getMobileNumber(), crmDomain.getLink(), crmDomain.getProfileId(),
							simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()) });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Save performance failed");
		} catch (Exception e) {
			logger.error("Exception savePerformance in CrmDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CrmDomain getPerformance(int profileId) throws Exception {
		try {
			String sql = "SELECT *  from performance where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<CrmDomain>(CrmDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getPerformance in CrmDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Performance not found");
		} catch (Exception e) {
			logger.error("Exception getPerformance in CrmDAOImpl" + e.getMessage());
			return null;
		}
	}

	@Override
	public String updatePerformance(CrmDomain crmDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE performance SET link =?,modificationDate=?  WHERE profileId=?";
			int res = jdbcTemplate.update(sql, new Object[] { crmDomain.getLink(), simpleDateFormat.format(new Date()),
					crmDomain.getProfileId() });
			if (res == 1) {
				return "Updated successfully";
				
			} else 
				throw new UPDATE_FAILED("Performance update failed");
		} catch (Exception e) {
			logger.error("Exception updatePerformance in crmDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
