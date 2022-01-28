package com.atpl.mmg.dao.employee;

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
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.employee.BdoDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.COUNT_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.DateUtility;

/* Author:Sindhu
 * creation Date:17-11-2019
 * modified Date:25-02-2020
 * Description:Bdo Mapping to Bdm
 * */

@SuppressWarnings("unused")
@Repository
public class BdoDaoImpl implements BdoDao {

	private static final Logger logger = LoggerFactory.getLogger(BdoDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String saveBdo(BdoDomain BdoDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO bdo (id,bdoId,bdmId,franchiseId,beId,creationDate,modificationDate) VALUES(?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { BdoDomain.getId(), BdoDomain.getBdoId(), BdoDomain.getBdmId(),
							BdoDomain.getFranchiseId(), BdoDomain.getBeId(),simpleDateFormat.format(new Date()),
							simpleDateFormat.format(new Date()) });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Save Bdo failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BDO.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception  in saveBdo in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<BdoDomain> getbdoList(int bdmId,int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * from bdo where beId!=0 and bdmId=?");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT * from bdo where beId!=0 and bdmId=?";
			return jdbcTemplate.query(sql.toString(), new Object[] { bdmId },
					new BeanPropertyRowMapper<BdoDomain>(BdoDomain.class));
		}  catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BDO.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getbdoList in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	@Override
	public BdoDomain getbdoCount(int bdmId) throws Exception {

		try {
			String sql = "SELECT count(*) as bdoCount from bdo where beId!=0 and bdmId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { bdmId },
					new BeanPropertyRowMapper<BdoDomain>(BdoDomain.class));
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BDO.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getBdoCount in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BdoDomain getbdoFranchise(int bdoId) throws Exception {
		try {
			String sql = "SELECT bdmId,franchiseId from bdo where bdoId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { bdoId },
					new BeanPropertyRowMapper<BdoDomain>(BdoDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getbdoFranchise in BdoDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Bdo details not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BDO.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getbdoFranchise in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BdoDomain getbdo(String franchiseId) throws Exception {
		BdoDomain bdoDomain = new BdoDomain();
		try {
			String sql = "SELECT bdoId from bdo where franchiseId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { franchiseId },
					new BeanPropertyRowMapper<BdoDomain>(BdoDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BDO.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getCrmList in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			return bdoDomain; //return null as we are considering this as success in franchise update
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BDO.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getbdo in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BdoDomain getBusinessExecutiveCountByBdmId(int bdmId) throws Exception {
		try {
			String sql = "SELECT count(beId) as businessExecutive from bdo where  beId !=0 and bdmId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { bdmId },
					new BeanPropertyRowMapper<BdoDomain>(BdoDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BDO.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getBusinessExecutiveCountByBdmId in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new COUNT_NOT_FOUND("Bdo's count not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.BDO.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getBusinessExecutiveCountByBdmId in BdoDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
