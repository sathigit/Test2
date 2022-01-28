package com.atpl.mmg.dao.bank;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.bank.BankDomain;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.DateUtility;

@Repository
@SuppressWarnings("rawtypes")
public class BankDAOImpl implements BankDAO, Constants {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public String saveBank(BankDomain bankDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO bank(name,createdDate,modifiedDate) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { bankDomain.getName(), DateUtility.setTimeZone(new Date()),
					DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Bank save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveBank in BankDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	public List<BankDomain> getBank(int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
	      sql.append("SELECT * FROM bank ORDER BY name asc");
	  	if (upperBound > 0)
			sql.append(" limit " + lowerBound + "," + upperBound);
			List<BankDomain> Bank = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<BankDomain>(BankDomain.class));
			return Bank;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getBank in BankDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new NOT_FOUND("Bank not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getBank in BankDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String updateBank(BankDomain bankDomain) throws Exception {
		try {
			String sql = "UPDATE bank SET name=?,modifiedDate=?,status=?  WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { bankDomain.getName(), DateUtility.setTimeZone(new Date()),
					bankDomain.isStatus(), bankDomain.getId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Bank update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateBank in BankDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BankDomain getBank(int id) throws Exception {
		try {
			String sql = "SELECT * FROM bank where id=?";
			return (BankDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<BankDomain>(BankDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Bank not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getBank in BankDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteBank(int id) throws Exception {

		try {
			String sql = "DELETE FROM bank WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Bank delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception delete bank in BankDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getBankCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM bank ";
			return (DashboardDomain) jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getBankCount in BankDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
