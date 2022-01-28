package com.atpl.mmg.dao.goodstype;

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

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.goodstype.GoodstypeDomain;
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
@SuppressWarnings({"rawtypes","unchecked"})
public class GoodstypeDAOImpl implements GoodstypeDAO, Constants {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public String saveGoodsType(GoodstypeDomain organizationDomain) {
		try {
			String sql = "INSERT INTO goodstype( name,isProfitable,creationDate,modificationDate,status ) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { organizationDomain.getName(),organizationDomain.isIsProfitable(), DateUtility.setTimeZone(),
							DateUtility.setTimeZone(), organizationDomain.isStatus() });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("GoodsType save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveGoodsType in GoodstypeDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public List<GoodstypeDomain> getGoodsType(int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM goodstype where status = ? ORDER BY name asc");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT * FROM goodstype where id NOT IN('32','28','21','12','31','26','29','27','23','25') ORDER BY name asc";
			List<GoodstypeDomain> franchise = jdbcTemplate.query(sql.toString(), new Object[] {true},
					new BeanPropertyRowMapper<GoodstypeDomain>(GoodstypeDomain.class));
			return franchise;
		}catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getGoodsType in GoodstypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateGoodsType(GoodstypeDomain goodstypeDomain) throws Exception {
		try {
			String sql = "UPDATE goodstype SET name=?,creationDate=?,modificationDate=?,status=?  WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { goodstypeDomain.getName(), DateUtility.setTimeZone(),
							DateUtility.setTimeZone(), goodstypeDomain.isStatus(), goodstypeDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("GoodsType update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateGoodsType in GoodstypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public GoodstypeDomain getGoodsType(int id) throws Exception {
		try {
			String sql = "SELECT * FROM goodstype where id=?";
			return (GoodstypeDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper(GoodstypeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getGoodsTypeById in GoodstypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new NOT_FOUND("GoodsType not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getGoodsTypeById in GoodstypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteGoodsType(int id) throws Exception {
		try {
			String sql = "DELETE FROM goodstype WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("GoodsType delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception deleteGoodsType in GoodstypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public GoodstypeDomain getGoodsTypes(String name) throws Exception {
		try {
			String sql = "SELECT * FROM goodstype where name=?";
			return (GoodstypeDomain) jdbcTemplate.queryForObject(sql, new Object[] { name },
					new BeanPropertyRowMapper<GoodstypeDomain>(GoodstypeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getGoodsTypes in GoodstypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new NOT_FOUND("GoodsType not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getGoodsTypes in GoodstypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getGoodsTypeCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM goodstype where status = ?";
			return (DashboardDomain) jdbcTemplate.queryForObject(sql, new Object[] {true},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getGoodsTypeCount in GoodstypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new NOT_FOUND("GoodsType not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getGoodsTypeCount in GoodstypeDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
