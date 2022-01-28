package com.atpl.mmg.AandA.dao.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.domain.dashboard.DashboardDomain;
import com.atpl.mmg.AandA.domain.dashboard.LeadDashboardDomain;
import com.atpl.mmg.AandA.domain.dashboard.ProfileDashboardDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.COUNT_NOT_FOUND;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;

@Repository
public class DashboardDAOImpl implements DashboardDAO, Constants {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<ProfileDashboardDomain> getProfileCount(boolean status) throws Exception {
		try {
			String sql = null;

			List<ProfileDashboardDomain> dashboardDomainList = null;
			if (!status) {
				sql = "select r.roleName,r.id as roleId,(select count(*) from profilerole where roleId = r.id) total\r\n"
						+ "from role r where r.status =1";
				dashboardDomainList = jdbcTemplate.query(sql, new Object[] {},
						new BeanPropertyRowMapper<ProfileDashboardDomain>(ProfileDashboardDomain.class));
			} else {
				sql = "select r.roleName,r.id as roleId,(select count(*) from profilerole where roleId = r.id and isActive =?) total from role r where r.status =1";
				dashboardDomainList = jdbcTemplate.query(sql, new Object[] { status },
						new BeanPropertyRowMapper<ProfileDashboardDomain>(ProfileDashboardDomain.class));
			}
			return dashboardDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileCount in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new COUNT_NOT_FOUND("Customer count not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getProfileCount in DashboardDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDashboardDomain> getProfileCountByCityId(boolean status, int cityId) throws Exception {
		try {
			String sql = null;
			List<ProfileDashboardDomain> dashboardDomainList = null;
			if (!status) {
				sql = "select r.roleName,r.id as roleId,(select count(*) from profilerole pr, profile p,address a where\r\n"
						+ "pr.roleId = r.id and pr.profileId = p.id and p.id=a.profileId and a.cityId=? and r.id = a.roleId  and a.isActive = true and \r\n"
						+ "a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)) total\r\n"
						+ "from role r where r.status =1";
				dashboardDomainList = jdbcTemplate.query(sql, new Object[] { cityId },
						new BeanPropertyRowMapper<ProfileDashboardDomain>(ProfileDashboardDomain.class));
			} else {
				sql = "select r.roleName,r.id as roleId,(select count(*) from profilerole pr, profile p,address a where\r\n"
						+ "pr.roleId = r.id and pr.profileId = p.id and p.id=a.profileId and a.cityId=? and r.id = a.roleId  and a.isActive = true and \r\n"
						+ "a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) and pr.isActive=?) total\r\n"
						+ "from role r where r.status =1";
				dashboardDomainList = jdbcTemplate.query(sql, new Object[] { cityId, status },
						new BeanPropertyRowMapper<ProfileDashboardDomain>(ProfileDashboardDomain.class));
			}

			return dashboardDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileCountByCityId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new COUNT_NOT_FOUND("Customer count by city id not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileCountByCityId in DashboardDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDashboardDomain> getProfileCountByStateId(boolean status, int stateId) throws Exception {
		try {
			String sql = null;
			List<ProfileDashboardDomain> dashboardDomainList = null;
			if (!status) {
				sql = "select r.roleName,r.id as roleId,(select count(*) from profilerole pr, profile p,address a where\r\n"
						+ "pr.roleId = r.id and pr.profileId = p.id and p.id=a.profileId and a.stateId=?  and r.id = a.roleId and \r\n"
						+ "a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)) total\r\n"
						+ "from role r where r.status =1";
				dashboardDomainList = jdbcTemplate.query(sql, new Object[] { stateId },
						new BeanPropertyRowMapper<ProfileDashboardDomain>(ProfileDashboardDomain.class));
			} else {
				sql = "SELECT  r.roleName,r.id AS roleId,(SELECT COUNT(*) FROM profilerole pr,profile p,address a WHERE "
						+ "pr.roleId = r.id AND pr.profileId = p.id  AND a.profileId = p.id AND pr.profileId = a.profileId "
						+ "AND a.stateId = ? AND r.id = a.roleId AND a.isActive = true AND "
						+ "a.type = (CASE WHEN pr.roleId IN (1,2,13) THEN 'HOME' ELSE 'OFFICE'  END)"
						+ "AND pr.isActive = ?) total FROM  role r	WHERE  r.status = 1";

//				sql = "select r.roleName,r.id as roleId,(select count(*) from profilerole pr, profile p,address a where\r\n"
//						+ "pr.roleId = r.id and pr.profileId = p.id and p.id=a.profileId and pr.profileId = a.profileId and a.stateId=?  and r.id = a.roleId  and \r\n"
//						+ "a.type=(case when pr.roleId in (1,2,12,13) then 'HOME' else 'OFFICE' end) and pr.isActive=?) total\r\n"
//						+ "from role r where r.status =1";
				dashboardDomainList = jdbcTemplate.query(sql, new Object[] { stateId, status },
						new BeanPropertyRowMapper<ProfileDashboardDomain>(ProfileDashboardDomain.class));
			}

			return dashboardDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileCountByStateId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new COUNT_NOT_FOUND("Customer count by state id not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileCountByStateId in DashboardDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getProfileCountByRoleAndSts(int roleId, boolean status, boolean isStatus, int customerTypeId)
			throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = null;
			if (isStatus) {
				if (roleId == Integer.parseInt(Role.CUSTOMER.getCode()) && customerTypeId > 0) {
					sql = "select count(*) as total from profilerole pr,profile p ,customer c where pr.profileId = p.id and  c.profileId = p.id and pr.roleId= ? and pr.isActive=? and c.customerTypeId =?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, customerTypeId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole  where roleId= ? and isActive=?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}

			} else {
				if (roleId == Integer.parseInt(Role.CUSTOMER.getCode()) && customerTypeId > 0) {
					sql = "select count(*) as total from profilerole pr,profile p ,customer c where pr.profileId = p.id and  c.profileId = p.id and pr.roleId= ? and c.customerTypeId =?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, customerTypeId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole where roleId=?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileCountByRoleAndSts in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new COUNT_NOT_FOUND("Customer count not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileCountByRoleAndSts in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getProfileCountByRoleAndSts(int roleId, boolean status, int cityId, boolean isStatus,
			Boolean isTag, int customerTypeId) throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = null;
			if (isStatus) {
				if (roleId == Integer.parseInt(Role.FRANCHISE.getCode()) && null != isTag) {
					sql = "select count(*) as total from profilerole pr, profile p, franchise f,address a where pr.profileId = p.id and p.id=a.profileId and f.profileId = p.id and pr.roleId=? and a.cityId=? and a.roleId=? and "
							+ "a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) and pr.isActive=? and f.isTag = ? ";
					dashboardDomain = jdbcTemplate.queryForObject(sql,
							new Object[] { roleId, cityId, roleId, status, isTag },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else if (roleId == Integer.parseInt(Role.CUSTOMER.getCode()) && customerTypeId > 0) {
					sql = "select count(*) as total from profilerole pr, profile p, customer c,address a where pr.profileId = p.id and p.id=a.profileId and c.profileId = p.id and pr.roleId=? and a.cityId=? and a.roleId=? and "
							+ "a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) and pr.isActive=? and c.customerTypeId = ? ";
					dashboardDomain = jdbcTemplate.queryForObject(sql,
							new Object[] { roleId, cityId, roleId, status, customerTypeId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr, profile p,address a where pr.profileId = p.id and p.id=a.profileId and pr.roleId=? and a.cityId=? and a.roleId=? and "
							+ "a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) and pr.isActive=?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cityId, roleId, status },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else {

				if (roleId == Integer.parseInt(Role.FRANCHISE.getCode()) && null != isTag) {
					sql = "select count(*) as total from profilerole pr, profile p, franchise f,address a where pr.profileId = p.id and p.id=a.profileId and f.profileId = p.id and pr.roleId=? and a.cityId=? and a.roleId=? and "
							+ "a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) and f.isTag = ? ";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cityId, roleId, isTag },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else if (roleId == Integer.parseInt(Role.CUSTOMER.getCode()) && customerTypeId > 0) {
					sql = "select count(*) as total from profilerole pr, profile p, customer c,address a where pr.profileId = p.id and p.id=a.profileId and c.profileId = p.id and pr.roleId=? and a.cityId=? and a.roleId=? and "
							+ "a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) and c.customerTypeId = ? ";
					dashboardDomain = jdbcTemplate.queryForObject(sql,
							new Object[] { roleId, cityId, roleId, customerTypeId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr, profile p,address a where pr.profileId = p.id and p.id=a.profileId and pr.roleId=? and a.cityId=? and a.roleId=? and "
							+ "a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cityId, roleId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileCountByRoleAndSts in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new COUNT_NOT_FOUND("Customer count not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileCountByRoleAndSts in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getProfileCountByRoleAndStsAndStateId(int roleId, boolean status, int stateId,
			boolean isStatus, Boolean isTag, int customerTypeId) throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = null;
			if (isStatus) {
				if (roleId == Integer.parseInt(Role.FRANCHISE.getCode()) && null != isTag) {
					sql = "select count(*) as total from profilerole pr, profile p,address a,franchise f where pr.profileId = p.id and p.id=a.profileId and f.profileId = p.id and pr.roleId=? and a.stateId=? and a.roleId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) and pr.isActive=? and f.isTag = ? ";
					dashboardDomain = jdbcTemplate.queryForObject(sql,
							new Object[] { roleId, stateId, roleId, status, isTag },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else if (roleId == Integer.parseInt(Role.CUSTOMER.getCode()) && customerTypeId > 0) {
					sql = "select count(*) as total from profilerole pr, profile p,address a,customer c where pr.profileId = p.id and p.id=a.profileId and c.profileId = p.id  and pr.roleId=? and a.stateId=? and a.roleId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) and pr.isActive=? and c.customerTypeId = ? ";
					dashboardDomain = jdbcTemplate.queryForObject(sql,
							new Object[] { roleId, stateId, roleId, status, customerTypeId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr, profile p,address a where pr.profileId = p.id and p.id=a.profileId and pr.roleId=? and a.stateId=? and a.roleId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) and pr.isActive=?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, stateId, roleId, status },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else {
				if (roleId == Integer.parseInt(Role.FRANCHISE.getCode()) && null != isTag) {
					sql = "select count(*) as total from profilerole pr, profile p,address a,franchise f where pr.profileId = p.id and p.id=a.profileId and f.profileId = p.id and pr.roleId=? and a.stateId=? and a.roleId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)  and f.isTag = ? ";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, stateId, roleId, isTag },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else if (roleId == Integer.parseInt(Role.CUSTOMER.getCode()) && customerTypeId > 0) {
					sql = "select count(*) as total from profilerole pr, profile p,address a,customer c where pr.profileId = p.id and p.id=a.profileId and c.profileId = p.id and pr.roleId=? and a.stateId=? and a.roleId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)  and c.customerTypeId = ? ";
					dashboardDomain = jdbcTemplate.queryForObject(sql,
							new Object[] { roleId, stateId, roleId, customerTypeId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr, profile p,address a where pr.profileId = p.id and p.id=a.profileId and pr.roleId=? and a.stateId=? and a.roleId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, stateId, roleId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileCountByRoleAndStsAndStateId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new COUNT_NOT_FOUND("Customer count not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getProfileCountByRoleAndStsAndStateId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getProfileCountByFranchiseId(String franchiseId) throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = null;
			sql = "select (select count(*) from fieldofficer where franchiseId=?) fieldofficer, \r\n"
					+ "(select count(*) from coordinator where franchiseId=?) coordinator;";
			dashboardDomain = (DashboardDomain) jdbcTemplate.queryForObject(sql,
					new Object[] { franchiseId, franchiseId },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileCountByFranchiseId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new COUNT_NOT_FOUND("Customer count not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileCountByFranchiseId in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getDashboardByBdmId(String roleId, String bdmId, boolean status, boolean isStatus,
			String searchText) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			DashboardDomain dashboardDomain = new DashboardDomain();
			if (roleId.equals(Role.CHANNEL_PARTNER.getCode())) {
				if (isStatus) {
					sql.append(
							"select count(*) as total from  channelpartner c, profilerole r,profile p where c.profileId = r.profileId and r.profileId = p.id and r.roleId = ? and r.isActive = ? and c.bdmId=? ");
					if (searchText != null)
						sql.append(" (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
								+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
								+ "' or c.channelPartnerId = '" + searchText + "' )");
					dashboardDomain = (DashboardDomain) jdbcTemplate.queryForObject(sql.toString(),
							new Object[] { roleId, status, bdmId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql.append(
							"select count(*) as total from  channelpartner c, profilerole r,profile p where c.profileId = r.profileId and r.profileId = p.id and r.roleId = ? and c.bdmId=? ");
					if (searchText != null)
						sql.append(" (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
								+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
								+ "' or c.channelPartnerId = '" + searchText + "' )");
					dashboardDomain = (DashboardDomain) jdbcTemplate.queryForObject(sql.toString(),
							new Object[] { roleId, bdmId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId.equals(Role.BDO.getCode())) {
				if (isStatus) {
					sql.append(
							"select count(*) as total from  bdo b, profilerole r,profile p where b.profileId = r.profileId and r.profileId = p.id and r.roleId = ? and r.isActive = ? and b.bdmId=? ");
					if (searchText != null)
						sql.append(" (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
								+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
								+ "' or b.bdoId = '" + searchText + "' )");
					dashboardDomain = (DashboardDomain) jdbcTemplate.queryForObject(sql.toString(),
							new Object[] { roleId, status, bdmId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql.append(
							"select count(*) as total from  bdo b, profilerole r,profile p where b.profileId = r.profileId and r.profileId = p.id and r.roleId = ? and b.bdmId=? ");
					if (searchText != null)
						sql.append(" (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
								+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
								+ "' or b.bdoId = '" + searchText + "' )");
					dashboardDomain = (DashboardDomain) jdbcTemplate.queryForObject(sql.toString(),
							new Object[] { roleId, bdmId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getDashboardByBdmId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getDashboardByBdmId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LeadDashboardDomain getCustomerLead() throws Exception {
		try {
			String sql = "SELECT count(uuid) as total FROM customerlead";
			LeadDashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<LeadDashboardDomain>(LeadDashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLead in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCustomerLead in DashboardDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LeadDashboardDomain getCustomerLead(String assignedId) throws Exception {
		try {
			String sql = "SELECT count(assignedId) as total FROM customerlead where assignedId=?";
			LeadDashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { assignedId },
					new BeanPropertyRowMapper<LeadDashboardDomain>(LeadDashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLead in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCustomerLead in DashboardDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LeadDashboardDomain getCustomerLeadByStsId(String leadStatusId, String assignedId) throws Exception {
		try {
			String sql = null;
			LeadDashboardDomain dashboardDomain = new LeadDashboardDomain();
			if (null != assignedId) {
				sql = "SELECT count(assignedId) as leadStatus FROM customerlead where leadStatusId = ? and assignedId=?";
				dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { leadStatusId, assignedId },
						new BeanPropertyRowMapper<LeadDashboardDomain>(LeadDashboardDomain.class));
			} else {
				sql = "SELECT count(leadStatusId) as leadStatus FROM customerlead where leadStatusId = ?";
				dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { leadStatusId },
						new BeanPropertyRowMapper<LeadDashboardDomain>(LeadDashboardDomain.class));
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLeadByStsId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getCustomerLeadByStsId in DashboardDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LeadDashboardDomain getCustomerLeadByRmksId(String leadRemarksId, String assignedId) throws Exception {
		try {
			String sql = null;
			LeadDashboardDomain dashboardDomain = new LeadDashboardDomain();
			if (null != assignedId) {
				sql = "SELECT count(assignedId) as leadRemarks FROM customerlead where leadRemarksId = ? and assignedId=?";
				dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { leadRemarksId, assignedId },
						new BeanPropertyRowMapper<LeadDashboardDomain>(LeadDashboardDomain.class));
			} else {
				sql = "SELECT count(leadRemarksId) as leadRemarks FROM customerlead where leadRemarksId = ?";
				dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { leadRemarksId },
						new BeanPropertyRowMapper<LeadDashboardDomain>(LeadDashboardDomain.class));
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLeadByRmksId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getCustomerLeadByRmksId in DashboardDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LeadDashboardDomain getCustomerLeadByUploadedById(String uploadedById) throws Exception {
		try {
			String sql = "select IFNULL(sum(case when  status = 'COMPLETED' then 1 else 0  end),0) completed,\r\n"
					+ " IFNULL(sum(case when status = 'CANCELLED' then 1 else 0 end),0) cancelled,\r\n"
					+ "  IFNULL(sum(case when status = 'PENDING' then 1 else 0 end),0) pending,\r\n"
					+ "  IFNULL(sum(case when status = 'INPROGRESS' then 1 else 0 end),0) inProgress,\r\n"
					+ " count(*) as total from customerlead where uploadedById = ?";
			LeadDashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { uploadedById },
					new BeanPropertyRowMapper<LeadDashboardDomain>(LeadDashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLeadByUploadedById in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getCustomerLeadByUploadedById in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LeadDashboardDomain getCustomerLeadByAssignedId(String assignedId) throws Exception {
		try {
			String sql = "select IFNULL(sum(case when status = 'COMPLETED' then 1 else 0  end),0) completed,\r\n"
					+ " IFNULL(sum(case when status = 'CANCELLED' then 1 else 0 end),0) cancelled,\r\n"
					+ " IFNULL(sum(case when status = 'PENDING' then 1 else 0 end),0) pending,\r\n"
					+ " IFNULL(sum(case when status = 'INPROGRESS' then 1 else 0 end),0) inProgress,\r\n"
					+ "	count(*) as total from customerlead where assignedId = ?";
			LeadDashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { assignedId },
					new BeanPropertyRowMapper<LeadDashboardDomain>(LeadDashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLeadByAssignedId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getCustomerLeadByAssignedId in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getProfilCounteByRoleAndFranchiseId(int roleId, boolean status, String franchiseId,
			String searchText) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			DashboardDomain dashboardDomain = null;
			if (roleId == Integer.parseInt(Role.FIELDOFFICER.getCode())) {
				sql.append(
						"select count(p.id) as total from profile p,profilerole r, fieldofficer f where p.id=r.profileId AND p.id = f.profileId and r.roleId=? AND r.isActive =? AND f.franchiseId=? ");
				if (searchText != null)
					sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.fieldOfficerId like '" + searchText + "%' ) ");
			} else if (roleId == Integer.parseInt(Role.COORDINATOR.getCode())) {
				sql.append(
						"select count(p.id) as total from profile p,profilerole r, coordinator c where p.id=r.profileId AND p.id = c.profileId and r.roleId=? AND r.isActive =? AND c.franchiseId=? ");
				if (searchText != null)
					sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or c.coordinatorId like '" + searchText + "%') ");
			} else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
				sql.append("select count(p.id) as total from profile p,profilerole r, fleetoperator f "
						+ "where p.id = r.profileId and f.profileId = p.id and r.roleId =? and r.isActive=? and f.franchiseId = ? ");
				if (searchText != null)
					sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText
							+ "%' ) ");
			} else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
				sql.append("select count(p.id) as total from profile p,profilerole r, warehouse w  "
						+ "where p.id = r.profileId and w.profileId = p.id and r.roleId =? and r.isActive=? and w.franchiseId = ? ");
				if (searchText != null)
					sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or w.companyName like '" + searchText + "%' or w.wareHouseId like '" + searchText
							+ "%') ");
			} else if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) {
				sql.append(
						"select count(p.id) as total from profile p,profilerole r, enterprise e where p.id = r.profileId and e.profileId = p.id and r.roleId =? and r.isActive=? and e.franchiseId =? ");
				if (searchText != null)
					sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or e.companyName like '" + searchText + "%' or e.enterpriseId like '" + searchText
							+ "%' ) ");
			} else if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
				sql.append(
						"select count(p.id) as total from profile p,profilerole r where p.id = r.profileId and r.isActive=? and r.roleId =? ");
				if (searchText != null)
					sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' ) ");
			}
			if (roleId == Integer.parseInt(Role.DRIVER.getCode()))
				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] { status, roleId },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			else
				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(),
						new Object[] { roleId, status, franchiseId },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfilCounteByRoleAndFranchiseId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getProfilCounteByRoleAndFranchiseId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getProfileByChannelPartnerId(int roleId, boolean status, String cpId, boolean isStatus)
			throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = null;
			if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profile p,profilerole r,franchise f where p.id=r.profileId AND p.id = f.profileId and "
							+ "r.roleId=? and f.channelPartnerId = ? and r.isActive =?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cpId, status },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profile p,profilerole r,franchise f where p.id=r.profileId AND p.id = f.profileId and "
							+ "r.roleId=? and f.channelPartnerId = ?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cpId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileByChannelPartnerId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileByChannelPartnerId in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getProfilesCountByOtState(int otStateId, boolean status) throws Exception {
		try {
			String sql = "select count(p.id) as total from profile p,profilerole r,operational_team ot where p.id = r.profileId and  p.id = ot.profileId and"
					+ " ot.assignedStateId=? and r.isActive=? and r.roleId =" + Role.OPERATIONAL_TEAM.getCode();
			return (DashboardDomain) jdbcTemplate.queryForObject(sql, new Object[] { otStateId, status },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfilesCountByOtState in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfilesCountByOtState in DashboardDAOImpl" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getCustomerLeadCountByStsAndAssignedId(String status, String assignedId) throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = new DashboardDomain();
			if (status.equalsIgnoreCase("all")) {
				sql = "SELECT count(*) as total FROM customerlead where assignedId=?";
				dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { assignedId },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			} else {
				sql = "SELECT count(*) as total  FROM customerlead where status=? and assignedId=?";
				dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { status, assignedId },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCustomerLeadByStsAndAssignedId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getCustomerLeadByStsAndAssignedId in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getCustomerLeadsByStsAndAssignedIdAndUploadedId(String status, String assignedId,
			String uploadedById) throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = new DashboardDomain();
			if (status != null && assignedId != null && uploadedById != null) {
				if (status.equalsIgnoreCase("all")) {
					sql = "SELECT count(*) as total FROM customerlead where assignedId=? and uploadedById=?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { assignedId, uploadedById },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "SELECT count(*) as total FROM customerlead where status=? and assignedId=? and uploadedById=?";
					dashboardDomain = jdbcTemplate.queryForObject(sql,
							new Object[] { status, assignedId, uploadedById },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else {
				if (status != null && uploadedById != null)
					sql = "SELECT count(*) as total FROM customerlead where status=? and uploadedById=?";
				dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { status, uploadedById },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getCustomerLeadsByStsAndAssignedIdAndUploadedId in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getCustomerLeadsByStsAndAssignedIdAndUploadedId in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getReasonsCountByRoleByProfile(int roleId, String profileId, boolean checkRequestedBy,
			boolean isFlag) throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = new DashboardDomain();
			if (isFlag) {
				if (checkRequestedBy)
					sql = "select count(*) as total from reason where roleId = ? and profileId=? and changedStatus in ('ACTIVE','INACTIVE')";
				else
					sql = "select count(*) as total  from reason where roleId = ? and profileId=? and changedStatus in ('PENDING','INPROCESS','ONHOLD')";
				dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, profileId },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			} else {
				if (profileId != null && roleId != 0) {
					sql = "select count(*) total from reason where roleId = ? and profileId=?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, profileId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) total from reason where roleId = ?";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getReasonsCountByRoleByProfile in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getReasonsCountByRoleByProfile in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getProfileSearchByRoleAndSts(int roleId, boolean status, boolean isStatus, String searchText,
			int customerTypeId) throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = null;
			if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,franchise f where pr.profileId = p.id and pr.profileId = f.profileId AND pr.roleId= ? and pr.isActive=?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.franchiseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,franchise f where pr.profileId = p.id and pr.profileId = f.profileId AND pr.roleId= ?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.franchiseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,fleetoperator f where pr.profileId = p.id and pr.profileId = f.profileId AND pr.roleId= ? and pr.isActive=?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText + "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,fleetoperator f where pr.profileId = p.id and pr.profileId = f.profileId AND pr.roleId= ?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText + "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,warehouse f where pr.profileId = p.id and pr.profileId = f.profileId AND pr.roleId= ? and pr.isActive=?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.wareHouseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,warehouse f where pr.profileId = p.id and pr.profileId = f.profileId AND pr.roleId= ?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.wareHouseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,enterprise f where pr.profileId = p.id and pr.profileId = f.profileId AND pr.roleId= ? and pr.isActive=?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,enterprise f where pr.profileId = p.id and pr.profileId = f.profileId AND pr.roleId= ?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.CUSTOMER.getCode())) {
				if (isStatus) {

					if (customerTypeId > 0) {
						sql = "select count(*) as total from profilerole pr,profile p,customer c where pr.profileId = p.id and pr.profileId = c.profileId AND pr.roleId= ? and pr.isActive=?"
								+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
								+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
								+ "%') and c.customerTypeId = ? ";
						dashboardDomain = jdbcTemplate.queryForObject(sql,
								new Object[] { roleId, status, customerTypeId },
								new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
					} else {

						sql = "select count(*) as total from profilerole pr,profile p,customer c where pr.profileId = p.id and pr.profileId = c.profileId AND pr.roleId= ? and pr.isActive=?"
								+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
								+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
								+ "%')";
						dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status },
								new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
					}
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,customer c where pr.profileId = p.id and pr.profileId = c.profileId AND pr.roleId= ?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p where pr.profileId = p.id and pr.roleId= ? and pr.isActive=?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p where pr.profileId = p.id and pr.roleId= ?"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileSearchByRoleAndSts in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileSearchByRoleAndSts in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getProfileSearchCountByRoleAndSts(int roleId, boolean status, int cityId, boolean isStatus,
			String searchText, int customerTypeId) throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = null;
			if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,address a,franchise f where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and pr.isActive=? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) "
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.franchiseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,franchise f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and a.cityId=? and "
							+ " a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) AND (p.firstName like '"
							+ searchText + "%' or p.lastName like '" + searchText + "%' or p.mobileNumber like '"
							+ searchText + "%' or p.emailId like '" + searchText + "%' or f.companyName like '"
							+ searchText + "%' or f.franchiseId like '" + searchText + "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,fleetoperator f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND  pr.roleId= ? and pr.isActive=? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText + "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,fleetoperator f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) "
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText + "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,warehouse f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and pr.isActive=? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.wareHouseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,warehouse f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId  AND pr.roleId= ? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or  f.companyName like '" + searchText + "%' or f.wareHouseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,enterprise f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and pr.isActive=? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) "
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,enterprise f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.CUSTOMER.getCode())) {
				if (isStatus) {
					if (customerTypeId > 0) {
						sql = "select count(*) as total from profilerole pr,profile p,customer c,address a where pr.profileId = p.id and pr.profileId = c.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and pr.isActive=? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) "
								+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
								+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
								+ "%') and c.customerTypeId = ?";
						dashboardDomain = jdbcTemplate.queryForObject(sql,
								new Object[] { roleId, status, cityId, customerTypeId },
								new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
					} else {
						sql = "select count(*) as total from profilerole pr,profile p,customer c,address a where pr.profileId = p.id and pr.profileId = c.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and pr.isActive=? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) "
								+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
								+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
								+ "%')";
						dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, cityId },
								new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
					}
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,customer c,address a where pr.profileId = p.id and pr.profileId = c.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,address a where pr.profileId = p.id AND pr.profileId=a.profileId and pr.roleId= ? and pr.isActive=? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,address a where pr.profileId = p.id AND pr.profileId=a.profileId and pr.roleId= ? and a.cityId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, cityId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileSearchCountByRoleAndSts in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileSearchCountByRoleAndSts in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getProfileSearchCountByRoleAndStsAndStateId(int roleId, boolean status, int stateId,
			boolean isStatus, String searchText, int customerTypeId) throws Exception {
		try {
			String sql = null;
			DashboardDomain dashboardDomain = null;
			if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,address a,franchise f where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and pr.isActive=? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and  (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.franchiseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,franchise f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and a.stateId=? and "
							+ "a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end) AND"
							+ "  (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.franchiseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,fleetoperator f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND  pr.roleId= ? and pr.isActive=? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText + "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,fleetoperator f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText + "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,warehouse f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and pr.isActive=? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.wareHouseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,warehouse f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId  AND pr.roleId= ? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.wareHouseId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,enterprise f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and pr.isActive=? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,enterprise f,address a where pr.profileId = p.id and pr.profileId = f.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else if (roleId == Integer.parseInt(Role.CUSTOMER.getCode())) {
				if (isStatus) {

					if (customerTypeId > 0) {
						sql = "select count(*) as total from profilerole pr,profile p,customer c,address a where pr.profileId = p.id and pr.profileId = c.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and pr.isActive=? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
								+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
								+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
								+ "%') and c.customerTypeId =?";
						dashboardDomain = jdbcTemplate.queryForObject(sql,
								new Object[] { roleId, status, stateId, customerTypeId },
								new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
					} else {
						sql = "select count(*) as total from profilerole pr,profile p,customer c,address a where pr.profileId = p.id and pr.profileId = c.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and pr.isActive=? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
								+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
								+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
								+ "%')";
						dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, stateId },
								new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
					}
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,customer c,address a where pr.profileId = p.id and pr.profileId = c.profileId AND pr.profileId=a.profileId AND pr.roleId= ? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			} else {
				if (isStatus) {
					sql = "select count(*) as total from profilerole pr,profile p,address a where pr.profileId = p.id AND pr.profileId=a.profileId and pr.roleId= ? and pr.isActive=? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, status, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				} else {
					sql = "select count(*) as total from profilerole pr,profile p,address a where pr.profileId = p.id AND pr.profileId=a.profileId and pr.roleId= ? and a.stateId=? and a.type=(case when pr.roleId in (1,2,13) then 'HOME' else 'OFFICE' end)"
							+ " and (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%')";
					dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, stateId },
							new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
				}
			}
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileSearchCountByRoleAndSts in DashboardDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileSearchCountByRoleAndSts in DashboardDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
