package com.atpl.mmg.dao.route;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.domain.route.RouteTagDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class RouteTagDaoImpl implements RouteTagDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String addRouteTag(RouteTagDomain routeTagDomain) throws Exception {
		try {
			String sql = "insert into routetag(uuid,routeId,vehicleId,vendorId,roleId,tagLocation,tagBy,tagByRoleId,createdDate,modifiedDate) "
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { routeTagDomain.getUuid(), routeTagDomain.getRouteId(), routeTagDomain.getVehicleId(),
							routeTagDomain.getVendorId(), routeTagDomain.getRoleId(), routeTagDomain.getTagLocation(),
							routeTagDomain.getTagBy(), routeTagDomain.getTagByRoleId(),
							DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Route Tag save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception addRouteTag in RouteTagDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<RouteTagDomain> getAllRouteTag(String routeId, String vendorId, String tagLocation, String vehicleId)
			throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			if (!CommonUtils.isNullCheck(routeId) && !CommonUtils.isNullCheck(vendorId)) {
				sql.append("SELECT * FROM routetag WHERE routeId = ? and vendorId = ? and isActive =true");
				return jdbcTemplate.query(sql.toString(), new Object[] { routeId, vendorId },
						new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));
			}
			if (!CommonUtils.isNullCheck(routeId) && !CommonUtils.isNullCheck(tagLocation)) {
				sql.append("SELECT * FROM routetag WHERE routeId = ? and tagLocation = ? and isActive =true");
				return jdbcTemplate.query(sql.toString(), new Object[] { routeId, tagLocation },
						new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));
			}
			if (!CommonUtils.isNullCheck(routeId) && !CommonUtils.isNullCheck(vehicleId)) {
				sql.append("SELECT * FROM routetag WHERE routeId = ? and vehicleId = ? and isActive =true");
				return jdbcTemplate.query(sql.toString(), new Object[] { routeId, vehicleId },
						new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));
			}
			if (!CommonUtils.isNullCheck(routeId)) {
				sql.append("SELECT * FROM routetag WHERE routeId = ? and isActive =true");
				return jdbcTemplate.query(sql.toString(), new Object[] { routeId },
						new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));
			}
			if (!CommonUtils.isNullCheck(vendorId)) {
				sql.append("SELECT * FROM routetag WHERE vendorId = ? and isActive =true");
				return jdbcTemplate.query(sql.toString(), new Object[] { vendorId },
						new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));
			}
			if (!CommonUtils.isNullCheck(vehicleId)) {
				sql.append("SELECT * FROM routetag WHERE vehicleId = ? and isActive =true");
				return jdbcTemplate.query(sql.toString(), new Object[] { vehicleId },
						new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));
			}

		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "EmptyResultDataAccessException getAllRouteTag in RouteTagDaoImpl"));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getAllRouteTag in RouteTagDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
		return null;
	}

	public List<RouteTagDomain> getAllRouteTag() throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM routetag where isActive = true ");
			return jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));

		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "EmptyResultDataAccessException getAllRouteTag in RouteTagDaoImpl"));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getAllRouteTag in RouteTagDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public RouteTagDomain getRouteTagById(String uuid) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM routetag where uuid=? and isActive = true");
			return jdbcTemplate.queryForObject(sql.toString(), new Object[] { uuid },
					new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));

		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
							"EmptyResultDataAccessException getRouteTagById in RouteTagDaoImpl"));
			throw new NOT_FOUND("Route Tag details not found!!");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getRouteTagById in RouteTagDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateRouteTag(RouteTagDomain routeTagDomain) throws Exception {
		try {
			String sql = "update routetag set routeId=? ,vendorId =?,roleId=?,modifiedDate=? where uuid=?";
			int res = jdbcTemplate.update(sql.toString(),
					new Object[] { routeTagDomain.getRouteId(), routeTagDomain.getVendorId(),
							routeTagDomain.getRoleId(), DateUtility.setTimeZone(new Date()),
							routeTagDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("RouteTag Update Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception updateRouteTag in RouteTagDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<RouteTagDomain> getAllRouteTagVehiclesVendors(String routeId, boolean isVehicle, int roleId)
			throws Exception {
		List<RouteTagDomain> routeList = new ArrayList<RouteTagDomain>();
		try {
			StringBuilder sql = new StringBuilder();
			if (isVehicle) {
				sql.append("SELECT vehicleId FROM routetag WHERE routeId = ? and vehicleId is not null");
				routeList = jdbcTemplate.query(sql.toString(), new Object[] { routeId },
						new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));
			} else {
				sql.append("SELECT vendorId FROM routetag WHERE routeId = ? and roleId = ? and vendorId is not null");
				routeList = jdbcTemplate.query(sql.toString(), new Object[] { routeId, roleId },
						new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));
			}
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
							"EmptyResultDataAccessException getAllRouteTagVehiclesVendors in RouteTagDaoImpl"));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getAllRouteTagVehiclesVendors in RouteTagDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
		return routeList;
	}

	@Override
	public String updateRouteTagStatus(String uuid, boolean status) throws Exception {
		try {
			String sql = "update routetag set status=?, modifiedDate=? where uuid=?";
			int res = jdbcTemplate.update(sql.toString(),
					new Object[] { status, DateUtility.setTimeZone(new Date()), uuid });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("RouteTag Update Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception updateRouteTagStatus in RouteTagDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteRouteTag(String uuid) throws Exception {
		try {
			String sql = "update routetag set isActive=?, modifiedDate=? where uuid=?";
			int res = jdbcTemplate.update(sql.toString(),
					new Object[] { false, DateUtility.setTimeZone(new Date()), uuid });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("RouteTag delete Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception deleteRouteTag in RouteTagDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public RouteTagDomain validateRouteTag(String routeId,String vendorId,int roleId,String vehicleId)
			throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM routetag where routeId= ? ");
			if (!CommonUtils.isNullCheck(vendorId) && roleId > 0) {
				sql.append(" and vendorId='" + vendorId + "' and roleId='" + roleId + "'  ");
			}
			if (!CommonUtils.isNullCheck(vehicleId)) {
				sql.append(" and vehicleId='" + vehicleId + "' ");
			}
			return jdbcTemplate.queryForObject(sql.toString(), new Object[] { routeId },
					new BeanPropertyRowMapper<RouteTagDomain>(RouteTagDomain.class));

		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
							"EmptyResultDataAccessException getRouteTagById in RouteTagDaoImpl"));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getRouteTagById in RouteTagDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
