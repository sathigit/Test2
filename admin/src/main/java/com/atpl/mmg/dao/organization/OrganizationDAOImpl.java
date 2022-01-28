package com.atpl.mmg.dao.organization;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.organization.OrganizationDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;

@Repository
@SuppressWarnings("rawtypes")
public class OrganizationDAOImpl implements OrganizationDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public String saveOrganization(OrganizationDomain organizationDomain) throws Exception {
		try {
			String sql = "INSERT INTO organization( name ) VALUES(?)";
			int res = jdbcTemplate.update(sql, new Object[] { organizationDomain.getName() });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Organization save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveOrganization in OrganizationDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public List<OrganizationDomain> getOrganization(int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT org.name,org.id FROM organization org ORDER BY name asc");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT org.name,org.id FROM organization org ORDER BY name asc";
			List<OrganizationDomain> franchise = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<OrganizationDomain>(OrganizationDomain.class));
			return franchise;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getOrganization in OrganizationDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String updateOrganization(OrganizationDomain id) throws Exception {
		try {
			String sql = "UPDATE organization SET name=?  WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id.getName(), id.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Organization update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateOrganization in OrganizationDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public OrganizationDomain getOrganization(int id) throws Exception {
		try {
			String sql = "SELECT * FROM organization where id=?";
			return (OrganizationDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<OrganizationDomain>(OrganizationDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Organization not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getOrganization in OrganizationDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteOrganization(int id) throws Exception {
		try {
			String sql = "DELETE FROM organization WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Organization delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception deleteOrganization in OrganizationDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getOrganizationCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM organization";
			return (DashboardDomain) jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getOrganizationCount in OrganizationDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
