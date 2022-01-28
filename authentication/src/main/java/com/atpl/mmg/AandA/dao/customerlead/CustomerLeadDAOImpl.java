package com.atpl.mmg.AandA.dao.customerlead;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.customerlead.CustomerLeadDomain;
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
public class CustomerLeadDAOImpl implements CustomerLeadDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String save(List<CustomerLeadDomain> customerLeadDomainList) throws Exception {
		try {
			for (CustomerLeadDomain customerLeadDomain : customerLeadDomainList) {
				String sql = "INSERT INTO customerlead (uuid,platform,firstName,lastName,doorNumber,street,cityId,stateId,countryId,pincode,mobileNumber,emailId,leadStatusId,leadRemarksId,comment,leadProfessionId,uploadedById,assignedId,assignedDate,callDate,status,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				int res = jdbcTemplate.update(sql,
						new Object[] { customerLeadDomain.getUuid(), customerLeadDomain.getPlatform(),
								customerLeadDomain.getFirstName(), customerLeadDomain.getLastName(),
								customerLeadDomain.getDoorNumber(), customerLeadDomain.getStreet(),
								customerLeadDomain.getCityId(), customerLeadDomain.getStateId(),
								customerLeadDomain.getCountryId(), customerLeadDomain.getPincode(),
								customerLeadDomain.getMobileNumber(), customerLeadDomain.getEmailId(),
								customerLeadDomain.getLeadStatusId(), customerLeadDomain.getLeadRemarksId(),
								customerLeadDomain.getComment(), customerLeadDomain.getLeadProfessionId(),
								customerLeadDomain.getUploadedById(), customerLeadDomain.getAssignedId(),
								customerLeadDomain.getAssignedDate(), customerLeadDomain.getCallDate(),
								customerLeadDomain.getStatus(), DateUtility.setTimeZone(new Date()),
								DateUtility.setTimeZone(new Date()), });
				if (res != 1)
					throw new SAVE_FAILED("Customer Leads data save failed");
			}
			return "Saved successfully";
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception saveCustomerLead in CustomerLeadDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CustomerLeadDomain getCustomerLeadsByUuId(String uuid) throws Exception {
		try {
			String sql = "SELECT * FROM customerlead where uuid=?";
			CustomerLeadDomain customerLeadDomain = jdbcTemplate.queryForObject(sql, new Object[] { uuid },
					new BeanPropertyRowMapper<CustomerLeadDomain>(CustomerLeadDomain.class));
			return customerLeadDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLeadsByUuId in CustomerLeadDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new NOT_FOUND("Customer Leads data not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getCustomerLeadsByUuId in CustomerLeadDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(CustomerLeadDomain customerLeadDomain) throws Exception {
		try {

			String sql = "UPDATE customerlead SET platform = ?,firstName=?,lastName=?,doorNumber=?,street=?,cityId=?,stateId=?,countryId=?,pincode=?,emailId=?,leadStatusId=?,leadRemarksId=?,comment=?,leadProfessionId=?,callDate=?,status=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { customerLeadDomain.getPlatform(), customerLeadDomain.getFirstName(),
							customerLeadDomain.getLastName(), customerLeadDomain.getDoorNumber(),
							customerLeadDomain.getStreet(), customerLeadDomain.getCityId(),
							customerLeadDomain.getStateId(), customerLeadDomain.getCountryId(),
							customerLeadDomain.getPincode(), customerLeadDomain.getEmailId(),
							customerLeadDomain.getLeadStatusId(), customerLeadDomain.getLeadRemarksId(),
							customerLeadDomain.getComment(), customerLeadDomain.getLeadProfessionId(),
							customerLeadDomain.getCallDate(), customerLeadDomain.getStatus(),
							DateUtility.setTimeZone(new Date()), customerLeadDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Customer Leads failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception Update Customer Leads in CustomerLeadDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<CustomerLeadDomain> getCustomerLeadsByStsAndAssignedId(String status, String assignedId, int lowerBound,
			int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<CustomerLeadDomain> leadDomain = new ArrayList<CustomerLeadDomain>();
			if (status.equalsIgnoreCase("all")) {
				sql.append("SELECT * FROM customerlead where assignedId=? order by createdDate desc");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				leadDomain = jdbcTemplate.query(sql.toString(), new Object[] { assignedId },
						new BeanPropertyRowMapper<CustomerLeadDomain>(CustomerLeadDomain.class));
			} else {
				sql.append("SELECT * FROM customerlead where status=? and assignedId=? order by createdDate desc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				leadDomain = jdbcTemplate.query(sql.toString(), new Object[] { status, assignedId },
						new BeanPropertyRowMapper<CustomerLeadDomain>(CustomerLeadDomain.class));
			}
			return leadDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLeadsByStsAndAssignedId in CustomerLeadDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCustomerLeadsByStsAndAssignedId in CustomerLeadDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<CustomerLeadDomain> getCustomerLeadsByStsAndUploadedById(String status, String uploadedById,
			int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<CustomerLeadDomain> leadDomain = new ArrayList<CustomerLeadDomain>();
			if (null != uploadedById) {
				if (!status.equalsIgnoreCase("all")) {
					sql.append(
							"SELECT * FROM customerlead where status=? and uploadedById=? order by createdDate desc ");
					if (upperBound > 0)
						sql.append("limit " + lowerBound + "," + upperBound);
					leadDomain = jdbcTemplate.query(sql.toString(), new Object[] { status, uploadedById },
							new BeanPropertyRowMapper<CustomerLeadDomain>(CustomerLeadDomain.class));
				} else {
					sql.append("SELECT * FROM customerlead where uploadedById=? order by createdDate desc ");
					if (upperBound > 0)
						sql.append("limit " + lowerBound + "," + upperBound);
					leadDomain = jdbcTemplate.query(sql.toString(), new Object[] { uploadedById },
							new BeanPropertyRowMapper<CustomerLeadDomain>(CustomerLeadDomain.class));
				}
			} else {
				sql.append("SELECT * FROM customerlead where status=? order by createdDate desc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				leadDomain = jdbcTemplate.query(sql.toString(), new Object[] { status },
						new BeanPropertyRowMapper<CustomerLeadDomain>(CustomerLeadDomain.class));
			}
			return leadDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLeadsByStsAndUploadedById in CustomerLeadDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCustomerLeadsByStsAndUploadedById in CustomerLeadDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<CustomerLeadDomain> getCustomerLeads(int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM customerlead order by createdDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			List<CustomerLeadDomain> leadDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<CustomerLeadDomain>(CustomerLeadDomain.class));
			return leadDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLeadsByStsAndUploadedById in CustomerLeadDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getCustomerLeads in CustomerLeadDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateAssignDetailsByUuid(CustomerLeadDomain customerLeadDomain) throws Exception {
		try {
			String sql = "UPDATE customerlead SET assignedId=?,assignedDate=?,status=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { customerLeadDomain.getAssignedId(), customerLeadDomain.getAssignedDate(),
							customerLeadDomain.getStatus(), DateUtility.setTimeZone(new Date()),
							customerLeadDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Assigne Details failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception Update Assigne Details in CustomerLeadDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<CustomerLeadDomain> getCustomerLeadsByStsAndAssignedIdAndUploadedId(String status, String assignedId,
			String uploadedById, int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<CustomerLeadDomain> leadDomain = new ArrayList<CustomerLeadDomain>();
			if (status.equalsIgnoreCase("all")) {
				sql.append(
						"SELECT * FROM customerlead where assignedId=? and uploadedById=? order by createdDate desc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				leadDomain = jdbcTemplate.query(sql.toString(), new Object[] { assignedId, uploadedById },
						new BeanPropertyRowMapper<CustomerLeadDomain>(CustomerLeadDomain.class));
			} else {
				sql.append(
						"SELECT * FROM customerlead where status=? and assignedId=? and uploadedById=? order by createdDate desc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				leadDomain = jdbcTemplate.query(sql.toString(), new Object[] { status, assignedId, uploadedById },
						new BeanPropertyRowMapper<CustomerLeadDomain>(CustomerLeadDomain.class));
			}
			return leadDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getCustomerLeadsByStsAndAssignedIdAndUploadedId in CustomerLeadDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.CUSTOMER_LEAD.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCustomerLeadsByStsAndAssignedIdAndUploadedId in CustomerLeadDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
