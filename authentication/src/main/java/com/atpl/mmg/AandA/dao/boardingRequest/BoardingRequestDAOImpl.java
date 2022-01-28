package com.atpl.mmg.AandA.dao.boardingRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.boardingRequest.BoardingRequestDomain;
import com.atpl.mmg.AandA.domain.boardingRequest.EnquiryReasonDomain;
import com.atpl.mmg.AandA.domain.dashboard.DashboardDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.ENQUIRY_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class BoardingRequestDAOImpl implements BoardingRequestDAO {

	private static final Logger logger = LoggerFactory.getLogger(BoardingRequestDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String saveRequest(BoardingRequestDomain boardingRequestDomain) throws Exception {
		try {
			String sql = "INSERT INTO boardingrequest (uuid,mobileNumber,firstName,lastName,email,roleId,message,countryId,stateId,cityId,requestNumber,validateEnquiry,createdDate,modifiedDate,refferenceCode) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { boardingRequestDomain.getUuid(), boardingRequestDomain.getMobileNumber(),
							boardingRequestDomain.getFirstName(), boardingRequestDomain.getLastName(),
							boardingRequestDomain.getEmail(), boardingRequestDomain.getRoleId(),
							boardingRequestDomain.getMessage(), boardingRequestDomain.getCountryId(),
							boardingRequestDomain.getStateId(), boardingRequestDomain.getCityId(),
							boardingRequestDomain.getRequestNumber(), boardingRequestDomain.getValidateEnquiry(),
							DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()),
							boardingRequestDomain.getRefferenceCode() });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Enquiry Request failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception saveRequest in BoardingRequestDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateEnquiryStatus(String uuid, String status) throws Exception {
		try {
			String sql = "UPDATE boardingrequest SET status=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { status, DateUtility.setTimeZone(new Date()), uuid });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update enquiry failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception Update Enquiry Request in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<BoardingRequestDomain> getEnquires(int roleId, String status, int lowerBound, int upperBound)
			throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			if (null == status || status.isEmpty())
				sql.append("SELECT *, NULL AS validateEnquiry FROM boardingrequest where  roleId=?");
			else
				sql.append("SELECT *, NULL AS validateEnquiry FROM boardingrequest where status='" + status
						+ "' and roleId=? order by modifiedDate desc");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
			List<BoardingRequestDomain> boardingRequestDomain = jdbcTemplate.query(sql.toString(),
					new Object[] { roleId },
					new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			return boardingRequestDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getEnquiresList in BoardingRequestDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getEnquires in BoardingRequestDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<BoardingRequestDomain> getEnquiresByCityAndStatus(String status, int roleId, int cityId, int lowerBound,
			int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			if (null == status || status.isEmpty()) {
				sql.append(
						"SELECT *, NULL AS validateEnquiry FROM boardingrequest where  roleId=? and cityId = ? order by createdDate desc");
			} else
				sql.append("SELECT *, NULL AS validateEnquiry FROM boardingrequest where status='" + status
						+ "' and roleId=? and cityId = ? order by createdDate desc");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
			List<BoardingRequestDomain> boardingRequestDomain = jdbcTemplate.query(sql.toString(),
					new Object[] { roleId, cityId },
					new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			return boardingRequestDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getEnquiresByCityAndStatus in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getEnquiresByCityAndStatus in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<BoardingRequestDomain> getRequestListByStateAndStatus(String status, int roleId, int stateId,
			int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			if (null == status || status.isEmpty())
				sql.append(
						"SELECT *, NULL AS validateEnquiry FROM boardingrequest  where roleId=? and stateId = ? order by createdDate desc");
			else
				sql.append("SELECT *, NULL AS validateEnquiry FROM boardingrequest  where status='" + status
						+ "' and roleId=? and stateId = ? order by createdDate desc");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
			List<BoardingRequestDomain> boardingRequestDomain = jdbcTemplate.query(sql.toString(),
					new Object[] { roleId, stateId },
					new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			return boardingRequestDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getRequestListByStateAndStatus in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getRequestListByStateAndStatus in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateOnHoldReason(BoardingRequestDomain boardingRequestDomain) throws Exception {
		try {
			String sql = "UPDATE boardingrequest SET status = ?,modifiedDate=?,onHoldReason=? WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { boardingRequestDomain.getStatus(), DateUtility.setTimeZone(new Date()),
							boardingRequestDomain.getOnHoldReason(), boardingRequestDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update enquiry failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateOnHoldReason in BoardingRequestDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BoardingRequestDomain getEnquiryRequest(String uuid) throws Exception {
		try {
			String sql = "SELECT *, NULL AS validateEnquiry FROM boardingrequest where uuid=?";
			BoardingRequestDomain boardingRequestDomain = jdbcTemplate.queryForObject(sql, new Object[] { uuid },
					new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			return boardingRequestDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getEnquiryRequest in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new ENQUIRY_NOT_FOUND();
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getEnquiryRequest in BoardingRequestDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BoardingRequestDomain getRequestByValidateEnquiry(String validateEnquiry) throws Exception {
		try {
			String sql = "SELECT * FROM boardingrequest where validateEnquiry=?";
			BoardingRequestDomain boardingRequestDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { validateEnquiry },
					new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			return boardingRequestDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getRequest in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getRequestByValidateEnquiry in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BoardingRequestDomain getRequest(String requestNumber) throws Exception {
		try {
			String sql = "SELECT *, NULL AS validateEnquiry FROM boardingrequest where requestNumber =?";
			BoardingRequestDomain boardingRequestDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { requestNumber },
					new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			return boardingRequestDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getRequest in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new ENQUIRY_NOT_FOUND();
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getRequest in BoardingRequestDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<BoardingRequestDomain> getRequestListOnStateAndCity(String status, int roleId, int stateId, int cityId,
			int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			if (null == status || status.isEmpty())
				sql.append(
						"SELECT *, NULL AS validateEnquiry FROM boardingrequest where  roleId=?  and stateId =? and cityId = ? ORDER BY createdDate DESC");
			else
				sql.append("SELECT *, NULL AS validateEnquiry FROM boardingrequest where status='" + status
						+ "' and roleId=?  and stateId =? and cityId = ? ORDER BY createdDate DESC");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
			List<BoardingRequestDomain> boardingRequestDomain = jdbcTemplate.query(sql.toString(),
					new Object[] { roleId, stateId, cityId },
					new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			return boardingRequestDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getRequestListOnStateAndCity in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getRequestListOnStateAndCity in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateEnquiryRequest(BoardingRequestDomain boardingRequestDomain) throws Exception {
		try {
			String sql = "UPDATE boardingrequest SET firstName =?,lastName =?,message=?,countryId=?,stateId=?,cityId=?,mobileNumber=?,email=?,refferenceCode=?,status=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { boardingRequestDomain.getFirstName(), boardingRequestDomain.getLastName(),
							boardingRequestDomain.getMessage(), boardingRequestDomain.getCountryId(),
							boardingRequestDomain.getStateId(), boardingRequestDomain.getCityId(),
							boardingRequestDomain.getMobileNumber(), boardingRequestDomain.getEmail(),
							boardingRequestDomain.getRefferenceCode(), boardingRequestDomain.getStatus(),
							DateUtility.setTimeZone(new Date()), boardingRequestDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update enquiry failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception updateEnquiryRequest in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public EnquiryReasonDomain saveEnquiryReason(EnquiryReasonDomain enquiryReasonDomain) throws Exception {
		try {
			String sql = "INSERT INTO enquiryreason (uuid,userId,reason,enquiryId,previousStatus,changedStatus,createdDate,modifiedDate) VALUES (?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { enquiryReasonDomain.getUuid(), enquiryReasonDomain.getUserId(),
							enquiryReasonDomain.getReason(), enquiryReasonDomain.getEnquiryId(),
							enquiryReasonDomain.getPreviousStatus(), enquiryReasonDomain.getChangedStatus(),
							DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return enquiryReasonDomain;
			} else
				throw new SAVE_FAILED("Enquiry reason save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ENQUIRY_REASON_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception saveEnquiryReason in BoardingRequestDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<EnquiryReasonDomain> getEnquiryReasonList(String enquiryId, int lowerBound, int upperBound)
			throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM enquiryreason where  enquiryId=?");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
			List<EnquiryReasonDomain> enquiryReasonDomain = jdbcTemplate.query(sql.toString(),
					new Object[] { enquiryId },
					new BeanPropertyRowMapper<EnquiryReasonDomain>(EnquiryReasonDomain.class));
			return enquiryReasonDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getEnquiryReasonList in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getEnquiresByRole in BoardingRequestDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BoardingRequestDomain getRequest(int cityId, String mobileNumber, String emailId, int roleId)
			throws Exception {
		try {
			String sql = "SELECT * FROM boardingrequest where cityId=? and mobileNumber=? and email=? and roleId=? limit 1;";
			BoardingRequestDomain boardingRequestDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { cityId, mobileNumber, emailId, roleId },
					new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			return boardingRequestDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getRequest in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getRequestByValidateEnquiry in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getEnquiresCountOnRoleStatus(int roleId, String status) throws Exception {
		try {
			DashboardDomain dashboardDomain = new DashboardDomain();
			StringBuffer sql = new StringBuffer();
			if (null == status || status.isEmpty()) {
				sql.append("SELECT count(*) as total  FROM boardingrequest where  roleId=?");
				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] { roleId },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			} else {
				sql.append("SELECT count(*) as total FROM boardingrequest where status='" + status
						+ "' and roleId=? order by modifiedDate desc");
				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] { roleId },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getEnquiresList in BoardingRequestDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getEnquires in BoardingRequestDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getCountEnquiresByCityAndStatus(String status, int roleId, int cityId) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			if (null == status || status.isEmpty())
				sql.append(
						"SELECT count(*) as total FROM boardingrequest where  roleId=? and cityId = ? order by createdDate desc");
			else
				sql.append("SELECT count(*) as total FROM boardingrequest where status='" + status
						+ "' and roleId=? and cityId = ? order by createdDate desc");

			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { roleId, cityId }, new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getCountEnquiresByCityAndStatus in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getCountEnquiresByCityAndStatus in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getCountRequestListByStateAndStatus(String status, int roleId, int stateId)
			throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			if (null == status || status.isEmpty())
				sql.append(
						"SELECT count(*) as total FROM boardingrequest  where roleId=? and stateId = ? order by createdDate desc");
			else
				sql.append("SELECT count(*) as total FROM boardingrequest  where status='" + status
						+ "' and roleId=? and stateId = ? order by createdDate desc");
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { roleId, stateId },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getCountRequestListByStateAndStatus in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getCountRequestListByStateAndStatus in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getCountRequestListOnStateAndCity(String status, int roleId, int stateId, int cityId)
			throws Exception {
		try {
			DashboardDomain dashboardDomain = new DashboardDomain();
			StringBuffer sql = new StringBuffer();
			if (null == status || status.isEmpty())
				sql.append(
						"SELECT count(*) as total FROM boardingrequest where  roleId=?  and stateId =? and cityId = ? ORDER BY createdDate DESC");

			else
				sql.append("SELECT count(*) as total FROM boardingrequest where status='" + status
						+ "' and roleId=?  and stateId =? and cityId = ? ORDER BY createdDate DESC");
			dashboardDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] { roleId, stateId, cityId },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));

			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getCountRequestListOnStateAndCity in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getCountRequestListOnStateAndCity in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getEnquiryReasonListCount(String enquiryId) throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM enquiryreason where  enquiryId=?";
			DashboardDomain enquiryReasonDomain = jdbcTemplate.queryForObject(sql, new Object[] { enquiryId },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return enquiryReasonDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getEnquiryReasonList in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getEnquiresByRole in BoardingRequestDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<BoardingRequestDomain> boardingRequestSearchList(int roleId, String status, int cityId, int stateId,
			String searchText, int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<BoardingRequestDomain> boardingRequestDomain = new ArrayList<BoardingRequestDomain>();
			if (roleId != 0 && status != null && cityId != 0 && searchText != null) {
				sql.append(
						"SELECT *, NULL AS validateEnquiry FROM boardingrequest where roleId=? and status =? and cityId = ? and (firstName = ? or lastName=? or mobileNumber = ? or email = ? or message = ? or requestNumber = ? or refferenceCode = ?) ORDER BY createdDate DESC");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				boardingRequestDomain = jdbcTemplate.query(sql.toString(),
						new Object[] { roleId, status, cityId, searchText, searchText, searchText, searchText,
								searchText, searchText, searchText },
						new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			} else if (roleId != 0 && status != null && stateId != 0 && searchText != null) {
				sql.append(
						"SELECT *, NULL AS validateEnquiry FROM boardingrequest where roleId=? and status =? and stateId = ? and (firstName = ? or lastName=? or mobileNumber = ? or email = ? or message = ? or requestNumber = ? or refferenceCode = ?) ORDER BY createdDate DESC");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				boardingRequestDomain = jdbcTemplate.query(sql.toString(),
						new Object[] { roleId, status, stateId, searchText, searchText, searchText, searchText,
								searchText, searchText, searchText },
						new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			} else if (roleId != 0 && status != null && searchText != null) {
				sql.append(
						"SELECT *, NULL AS validateEnquiry FROM boardingrequest where roleId=? and status =? and (firstName = ? or lastName=? or mobileNumber = ? or email = ? or message = ? or requestNumber = ? or refferenceCode = ?) ORDER BY createdDate DESC");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				boardingRequestDomain = jdbcTemplate.query(sql.toString(),
						new Object[] { roleId, status, searchText, searchText, searchText, searchText, searchText,
								searchText, searchText },
						new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			} else if (roleId != 0 && searchText != null) {
				sql.append(
						"SELECT *, NULL AS validateEnquiry FROM boardingrequest where roleId=? and (firstName = ? or lastName=? or mobileNumber = ? or email = ? or message = ? or requestNumber = ? or refferenceCode = ? or status = ?) ORDER BY createdDate DESC");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				boardingRequestDomain = jdbcTemplate.query(sql.toString(),
						new Object[] { roleId, searchText, searchText, searchText, searchText, searchText, searchText,
								searchText, searchText },
						new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			} else {
				sql.append(
						"SELECT *, NULL AS validateEnquiry FROM boardingrequest where (firstName = ? or lastName=? or mobileNumber = ? or email = ? or message = ? or requestNumber = ? or refferenceCode = ? or status = ?) ORDER BY createdDate DESC");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				boardingRequestDomain = jdbcTemplate.query(sql.toString(),
						new Object[] { searchText, searchText, searchText, searchText, searchText, searchText,
								searchText, searchText },
						new BeanPropertyRowMapper<BoardingRequestDomain>(BoardingRequestDomain.class));
			}
			return boardingRequestDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException boardingRequestSearchList in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception boardingRequestSearchList in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public int getboardingRequestSearchCount(int roleId, String status, int cityId, int stateId,
			String searchText) throws Exception {
		try {
			DashboardDomain dashboardDomain = new DashboardDomain();
			StringBuffer sql = new StringBuffer();
			if (roleId != 0 && status != null && cityId != 0 && searchText != null) {
				sql.append(
						"SELECT count(*) as total FROM boardingrequest where roleId=? and status =? and cityId = ? and (firstName = ? or lastName=? or mobileNumber = ? or email = ? or message = ? or requestNumber = ? or refferenceCode = ?) ORDER BY createdDate DESC");
				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(),
						new Object[] { roleId, status, cityId, searchText, searchText, searchText, searchText,
								searchText, searchText, searchText },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			} else if (roleId != 0 && status != null && stateId != 0 && searchText != null) {
				sql.append(
						"SELECT count(*) as total FROM boardingrequest where roleId=? and status =? and stateId = ? and (firstName = ? or lastName=? or mobileNumber = ? or email = ? or message = ? or requestNumber = ? or refferenceCode = ?) ORDER BY createdDate DESC");
				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(),
						new Object[] { roleId, status, stateId, searchText, searchText, searchText, searchText,
								searchText, searchText, searchText },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			} else if (roleId != 0 && status != null && searchText != null) {
				sql.append(
						"SELECT count(*) as total FROM boardingrequest where roleId=? and status =? and (firstName = ? or lastName=? or mobileNumber = ? or email = ? or message = ? or requestNumber = ? or refferenceCode = ?) ORDER BY createdDate DESC");
				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(),
						new Object[] { roleId, status, searchText, searchText, searchText, searchText, searchText,
								searchText, searchText },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			} else if (roleId != 0 && searchText != null) {
				sql.append(
						"SELECT count(*) as total FROM boardingrequest where roleId=? and (firstName = ? or lastName=? or mobileNumber = ? or email = ? or message = ? or requestNumber = ? or refferenceCode = ? or status = ?) ORDER BY createdDate DESC");
				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(),
						new Object[] { roleId, searchText, searchText, searchText, searchText, searchText, searchText,
								searchText, searchText },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			} else {
				sql.append(
						"SELECT count(*) as total FROM boardingrequest where (firstName = ? or lastName=? or mobileNumber = ? or email = ? or message = ? or requestNumber = ? or refferenceCode = ? or status = ?) ORDER BY createdDate DESC");
				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(),
						new Object[] { searchText, searchText, searchText, searchText, searchText, searchText,
								searchText, searchText },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			}
			return dashboardDomain.getTotal();
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getCountRequestListOnStateAndCity in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return 0;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getCountRequestListOnStateAndCity in BoardingRequestDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
