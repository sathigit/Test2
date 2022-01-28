package com.atpl.mmg.dao.route;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.packagetype.PackageTypeDomain;
import com.atpl.mmg.domain.route.RouteDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.PACKAGE_TYPE_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class RouteDaoImpl implements RouteDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public RouteDomain getRouteById(String uuid) throws Exception {
		try {
			String sql = "SELECT * FROM route where uuid= ? ";
			return (RouteDomain) jdbcTemplate.queryForObject(sql, new Object[] { uuid },
					new BeanPropertyRowMapper<RouteDomain>(RouteDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "EmptyResultDataAccessException getRouteById in RouteDaoImpl"));
			throw new NOT_FOUND("Route not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getRouteById in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<RouteDomain> getAllRoute(Boolean status, int lowerBound, int upperBound) throws Exception {
		try {
			List<RouteDomain> routeDomain = new ArrayList<RouteDomain>();
			StringBuffer sql = new StringBuffer();
			if (null == status) {
				sql.append("select * from route ");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				routeDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
						new BeanPropertyRowMapper<RouteDomain>(RouteDomain.class));
			} else {
				sql.append("select * from route where isActive = true and status = ? ");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				routeDomain = jdbcTemplate.query(sql.toString(), new Object[] { status },
						new BeanPropertyRowMapper<RouteDomain>(RouteDomain.class));
			}
			return routeDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getAllRoute in RouteDaoImpl "));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getRouteCount(Boolean status) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(uuid) as total from route where  isActive = true");
			if (null != status)
				sql.append(" and  status =" + status + " ");
			return jdbcTemplate.queryForObject(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getAllRouteCount in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateRoute(RouteDomain routeDomain) throws Exception {
		try {
			String sql = "update route set source=?,destination=?,createdBy=?,roleId=?,modifiedDate=? where uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { routeDomain.getSource(), routeDomain.getDestination(), routeDomain.getCreatedBy(),
							routeDomain.getRoleId(), DateUtility.setTimeZone(new Date()), routeDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new SAVE_FAILED("Route update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception updateRoute in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteRoute(String routeId) throws Exception {
		try {
			String sql = "update  route set isActive=? where uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { false, routeId });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new SAVE_FAILED("Route delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception deleteRoute in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateRouteStatus(String uuid, boolean status) throws Exception {
		try {
			String sql = "UPDATE route  SET status=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { status, DateUtility.setTimeZone(new Date()), uuid });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Route Update Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception updateRouteStatus in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<RouteDomain> getRouteBySourceAndDestination(Boolean status, String source, String destination,
			int lowerBound, int upperBound) throws Exception {
		List<RouteDomain> routeDomain = new ArrayList<RouteDomain>();
		try {
			StringBuilder sql = new StringBuilder();
			if (!CommonUtils.isNullCheck(destination)) {
				sql.append(
						"SELECT * FROM route WHERE isActive=true and ? IN(json_extract(source,'$.city')) = true and ? IN(json_extract(destination,'$.city')) = true");
				if (null != status)
					sql.append(" and status = " + status + "  ");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				return jdbcTemplate.query(sql.toString(), new Object[] { source, destination },
						new BeanPropertyRowMapper<RouteDomain>(RouteDomain.class));
			} else {
				sql.append("SELECT * FROM route WHERE isActive=true and  ? IN(json_extract(source,'$.city')) = true ");
				if (null != status)
					sql.append(" and status = " + status + " ");

				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				routeDomain = jdbcTemplate.query(sql.toString(), new Object[] { source },
						new BeanPropertyRowMapper<RouteDomain>(RouteDomain.class));
			}
			return routeDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
							"EmptyResultDataAccessException getRouteBySourceAndDestination in RouteDaoImpl"));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getRouteBySourceAndDestination in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String addRoute(RouteDomain routeDomain) throws Exception {
		try {
			String sql = "insert into route(uuid,source,destination,createdBy,roleId,createdDate,modifiedDate) "
					+ "values(?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { routeDomain.getUuid(), routeDomain.getSource(), routeDomain.getDestination(),
							routeDomain.getCreatedBy(), routeDomain.getRoleId(), DateUtility.setTimeZone(new Date()),
							DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("Route save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception saveRoute in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getCountRouteBySourceAndDestination(Boolean status, String source, String destination)
			throws Exception {
		DashboardDomain dashboardDomain = new DashboardDomain();
		try {
			StringBuilder sql = new StringBuilder();
			if (!CommonUtils.isNullCheck(destination)) {
				sql.append(
						"SELECT count(*) as total FROM route WHERE isActive=true and ? IN(json_extract(source,'$.city')) = true and ? IN(json_extract(destination,'$.city')) = true");
				if (null != status)
					sql.append(" and status = " + status + "  ");
				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] { source, destination },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			} else {
				sql.append("SELECT count(*) as total FROM route WHERE isActive=true and ? IN(json_extract(source,'$.city')) = true ");
				if (null != status)
					sql.append(" and status = " + status + "  ");

				dashboardDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] { source },
						new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			}
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getCountRouteBySourceAndDestination in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
		return dashboardDomain;
	}

	@Override
	public RouteDomain validateRouteDetails(RouteDomain routeDomain) throws Exception {
		RouteDomain route = new RouteDomain();
		try {
			String sql = null;
			sql = "select * from route where source = ?  and destination =? and isActive=true ";
			route = jdbcTemplate.queryForObject(sql,
					new Object[] { routeDomain.getSource(), routeDomain.getDestination() },
					new BeanPropertyRowMapper<RouteDomain>(RouteDomain.class));

		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
							"EmptyResultDataAccessException validateRouteDetails in RouteDaoImpl"));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception validateRouteDetails in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
		return route;
	}

	@Override
	public RouteDomain validateRouteCityDetails(String source, String destination) throws Exception {
		RouteDomain route = new RouteDomain();
		try {
			String sql = null;
			sql = "select * from route where isActive=true and ? IN(json_extract(source,'$.city')) = true and ? IN(json_extract(destination,'$.city')) = true";
			route = jdbcTemplate.queryForObject(sql, new Object[] { source, destination },
					new BeanPropertyRowMapper<RouteDomain>(RouteDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
							"EmptyResultDataAccessException validateRouteCityDetails in RouteDaoImpl"));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception validateRouteCityDetails in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
		return route;
	}

	@Override
	public RouteDomain validateRouteDetailsOnLatLong(String sourcelat, String sourcelong, String dstlat, String dstlong)
			throws Exception {
		RouteDomain route = new RouteDomain();
		try {
			String sql = null;
			sql = "SELECT * FROM route WHERE isActive=true and ? IN(json_extract(source,'$.latitude')) = true and ? IN(json_extract(source,'$.longitude')) = true and ? IN(json_extract(destination,'$.latitude')) = true and ? IN(json_extract(destination,'$.longitude')) = true  ";
			route = jdbcTemplate.queryForObject(sql, new Object[] { sourcelat, sourcelong, dstlat, dstlong },
					new BeanPropertyRowMapper<RouteDomain>(RouteDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
							"EmptyResultDataAccessException validateRouteDetails in RouteDaoImpl"));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception validateRouteDetails in RouteDaoImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
		return route;
	}
}
