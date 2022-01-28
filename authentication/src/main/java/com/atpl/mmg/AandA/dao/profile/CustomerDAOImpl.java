package com.atpl.mmg.AandA.dao.profile;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.CustomerDomain;
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
public class CustomerDAOImpl implements CustomerDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public CustomerDomain save(CustomerDomain customerDomain) throws Exception {
		try {
			String sql = "INSERT INTO customer (customerId,profileId,isTermsAndCondition,termsAndConditionsId,referenceId,customerTypeId,gstNo,createdDate,modifiedDate)"
					+ " VALUES(?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { customerDomain.getCustomerId(),customerDomain.getProfileId(),customerDomain.isIsTermsAndCondition(),
					customerDomain.getTermsAndConditionsId(),customerDomain.getReferenceId(),customerDomain.getCustomerTypeId(),customerDomain.getGstNo(),
					DateUtility.getDateFormat(new Date()), DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return customerDomain;

			} else
				throw new SAVE_FAILED("Customer save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveCustomer in CustomerDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(CustomerDomain customerDomain) throws Exception {
		try {
			String sql = "UPDATE customer SET referenceId=?,modifiedDate=? WHERE customerId=?";
			int res = 0;
	
			res = jdbcTemplate.update(sql, new Object[] { customerDomain.getReferenceId(),DateUtility.getDateFormat(new Date()), customerDomain.getCustomerId() });
				
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Customer update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateCustomer in CustomerDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CustomerDomain getCustomerByProfileId(String profileId) throws Exception {
		try {
			String sql = "select * from customer  where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<CustomerDomain>(CustomerDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Customer not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCustomerByProfileId in CustomerDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	@Override
	public CustomerDomain checkGstNumber(String gstNumber) throws Exception {
		try {
			String sql = "select * from customer where gstNo=?";
			return (CustomerDomain) jdbcTemplate.queryForObject(sql, new Object[] { gstNumber },
					new BeanPropertyRowMapper<CustomerDomain>(CustomerDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(), SeverityTypes.ALERT.ordinal(),
							"Exception checkGstNumber in CustomerDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FRANCHISE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception checkGstNumber in CustomerDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
