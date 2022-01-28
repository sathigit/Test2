package com.atpl.mmg.dao.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.admin.AdminDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class AdminDAOImpl implements AdminDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(AdminDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<AdminDomain> getSeverity() throws Exception {
		try {
			String sql = "SELECT * FROM severity";
			List<AdminDomain> adminDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
			return adminDomain;
		} catch (Exception e) {
			logger.error("Exception getSeverity in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	public List<AdminDomain> getService() throws Exception {
		try {
			String sql = "SELECT * FROM service";
			List<AdminDomain> adminDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
			return adminDomain;
		} catch (Exception e) {
			logger.error("Exception getService in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	public List<AdminDomain> getCustomerIssue() throws Exception {
		try {
			String sql = "SELECT * FROM customerissue";
			List<AdminDomain> adminDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
			return adminDomain;
		} catch (Exception e) {
			logger.error("Exception getCustomerIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	public List<AdminDomain> getFranchiseIssue() throws Exception {
		try {
			String sql = "SELECT * FROM franchiseissue";
			List<AdminDomain> adminDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
			return adminDomain;
		} catch (Exception e) {
			logger.error("Exception getFranchiseIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<AdminDomain> getFleetIssue() throws Exception {
		try {
			String sql = "SELECT * FROM fleetissue";
			List<AdminDomain> adminDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
			return adminDomain;
		} catch (Exception e) {
			logger.error("Exception getFleetIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String addDriverIssue(AdminDomain adminDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO service( id, name,creationDate,modificationDate,status) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { adminDomain.getId(), adminDomain.getName(),
					simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), adminDomain.isStatus() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("DriverIssue save failed");
		} catch (Exception e) {
			logger.error("Exception addDriverIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String addCustomerIssue(AdminDomain adminDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO customerissue( id, issue,creationDate,modificationDate,status) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { adminDomain.getId(), adminDomain.getIssue(),
					simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), adminDomain.isStatus() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("CustomerIssue save failed");
		} catch (Exception e) {
			logger.error("Exception addCustomerIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String addFranchiseIssue(AdminDomain adminDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO franchiseissue( id, name,creationDate,modificationDate,status) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { adminDomain.getId(), adminDomain.getName(),
					simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), adminDomain.isStatus() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("FranchiseIssue save failed");
		} catch (Exception e) {
			logger.error("Exception addFranchiseIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String addFleetIssue(AdminDomain adminDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO fleetissue( id, issue,creationDate,modificationDate,status) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { adminDomain.getId(), adminDomain.getIssue(),
					simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), adminDomain.isStatus() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("FleetIssue save failed");
		} catch (Exception e) {
			logger.error("Exception addFleetIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateCustomerIssue(AdminDomain adminDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE customerissue SET issue=?,creationDate=?,modificationDate=?,status=?  WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { adminDomain.getIssue(), adminDomain.getId(),
					simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), adminDomain.isStatus() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("CustomerIssue update failed");
		} catch (Exception e) {
			logger.error("Exception updateCustomerIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateDriverIssue(AdminDomain adminDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE service SET name=?,creationDate=?,modificationDate=?,status=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { adminDomain.getName(), adminDomain.getId(),
					simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), adminDomain.isStatus() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("DriverIssue update failed");
		} catch (Exception e) {
			logger.error("Exception updateDriverIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateFranchiseIssue(AdminDomain adminDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE franchiseisuue SET name=?,creationDate=?,modificationDate=?,status=?  WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { adminDomain.getName(), adminDomain.getId(),
					simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), adminDomain.isStatus() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("FranchiseIssue update failed");
		} catch (Exception e) {
			logger.error("Exception updateFranchiseIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateFleetIssue(AdminDomain adminDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE fleetissue SET issue=?,creationDate=?,modificationDate=?,status=?  WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { adminDomain.getIssue(), simpleDateFormat.format(new Date()),
							simpleDateFormat.format(new Date()), adminDomain.isStatus(), adminDomain.getId() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("FleetIssue update failed");
		} catch (Exception e) {
			logger.error("Exception updateFleetIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteCustomerIssue(int id) throws Exception {
		try {
			String sql = "DELETE FROM customerissue WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("CustomerIssue delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteCustomerIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteDriverIssue(int id) throws Exception {
		try {
			String sql = "DELETE FROM service WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("DriverIssue delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteDriverIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteFranchiseIssue(int id) throws Exception {
		try {
			String sql = "DELETE FROM franchiseissue WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("FranchiseIssue delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteFranchiseIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteFleetIssue(int id) throws Exception {
		try {
			String sql = "DELETE FROM fleetissue WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("FleetIssue delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteFleetIssue in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String addSeverity(AdminDomain adminDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO severity( id, name,creationDate,modificationDate,status) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { adminDomain.getId(), adminDomain.getName(),
					simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), adminDomain.isStatus() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("Severity save failed");
		} catch (Exception e) {
			logger.error("Exception addSeverity in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateSeverity(AdminDomain adminDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE severity SET name=?,creationDate=?,modificationDate=?,status=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { adminDomain.getName(), adminDomain.getId(),
					simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), adminDomain.isStatus() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("Severity update failed");
		} catch (Exception e) {
			logger.error("Exception updateSeverity in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteSeverity(int id) throws Exception {
		try {
			String sql = "DELETE FROM severity WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("Severity delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteSeverity in AdminDAOImpl", e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public AdminDomain getCustomerIssue(int id) throws Exception {
		try {
			String sql = "select * from customerissue where id =?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getCustomerIssuebyId in AdminDAOImpl" + e.getMessage());
			throw new NOT_FOUND("customerIssue not found");
		} catch (Exception e) {
			logger.error("Exception getCustomerIssuebyId in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public AdminDomain getFranchiseIssue(int id) throws Exception {
		try {
			String sql = "select * from franchiseissue where id =?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getFranchiseIssuebyId in AdminDAOImpl" + e.getMessage());
			throw new NOT_FOUND("FranchiseIssue not found");
		} catch (Exception e) {
			logger.error("Exception getFranchiseIssuebyId in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public AdminDomain getDriverIssue(int id) throws Exception {
		try {
			String sql = "select * from service where id =?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getDriverIssuebyId in AdminDAOImpl" + e.getMessage());
			throw new NOT_FOUND("DriverIssue not found");
		} catch (Exception e) {
			logger.error("Exception getDriverIssuebyId in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public AdminDomain getFleetIssue(int id) throws Exception {
		try {
			String sql = "select * from fleetissue where id =?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getFleetIssuebyId in AdminDAOImpl" + e.getMessage());
			throw new NOT_FOUND("FleetIssue not found");
		} catch (Exception e) {
			logger.error("Exception getFleetIssuebyId in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public AdminDomain getSeverity(int id) throws Exception {
		try {
			String sql = "select * from severity where id =?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getSeveritybyId in AdminDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Severity not found");
		} catch (Exception e) {
			logger.error("Exception getSeveritybyId in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<AdminDomain> getEmployeeList() throws Exception {
		try {
			String sql = "SELECT * FROM employeelist";
			List<AdminDomain> adminDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<AdminDomain>(AdminDomain.class));
			return adminDomain;
		} catch (Exception e) {
			logger.error("Exception getEmployeeList in AdminDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
