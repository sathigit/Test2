package com.atpl.mmg.dao.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.route.RouteDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.CommonUtils;

@Repository
public class DashboardDAOImpl implements DashboardDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public DashboardDomain getRouteCount(Boolean status) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) as total from route ");
			if (null != status) {
				sql.append(" where status = " + status + " ");
			}
			return (DashboardDomain) jdbcTemplate.queryForObject(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getting all BookingsCount in DashboardDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getRouteCountOnStateCity(Boolean status, String source, String destination, int cityId,
			int stateId) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) as total from route ");
			if (null != status) {
				sql.append(" where status = " + status + " ");
			}
			if (cityId > 0) {
				if (!CommonUtils.isNullCheck(destination)) {
					sql.append(" and '" + cityId + "'  IN(json_extract(destination,'$.city')) = true");
				}
				if (!CommonUtils.isNullCheck(source)) {
					sql.append(" and '" + cityId + "'  IN(json_extract(source,'$.city')) = true ");
				}
			}
			if (stateId > 0) {
				if (!CommonUtils.isNullCheck(destination)) {
					sql.append(" and '" + stateId + "'  IN(json_extract(destination,'$.state')) = true");
				}
				if (!CommonUtils.isNullCheck(source)) {
					sql.append(" and '" + stateId + "'  IN(json_extract(source,'$.state')) = true ");
				}
			}
			return (DashboardDomain) jdbcTemplate.queryForObject(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getRouteCountOnStateCity in DashboardDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
