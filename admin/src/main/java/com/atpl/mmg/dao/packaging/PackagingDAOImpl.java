package com.atpl.mmg.dao.packaging;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.packaging.PackagingDomain;
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
public class PackagingDAOImpl implements PackagingDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(PackagingDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String savePackaging(PackagingDomain packagingDomain) throws Exception {
		try {
			String sql = "Insert into packaging(packagingName) values(?)";
			int res = jdbcTemplate.update(sql, new Object[] { packagingDomain.getPackagingName() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("Packaging save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception savePackaging in PackagingDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<PackagingDomain> getPackagingList(int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
				sql.append("select packagingId,packagingName from packaging");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "select packagingId,packagingName from packaging";
			List<PackagingDomain> packagingDomain = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<PackagingDomain>(PackagingDomain.class));
			return packagingDomain;
		}  catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getPackagingList in PackagingDaoImpl "));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public PackagingDomain getPackageDetails(BigInteger packagingId) throws Exception {
		try {
			String sql = "select packagingId,packagingName from packaging where packagingId=? ";
			return jdbcTemplate.queryForObject(sql, new Object[] { packagingId },
					new BeanPropertyRowMapper<PackagingDomain>(PackagingDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
					"EmptyResultDataAccessException getPackageDetails in PackagingDAOImpl"));
			throw new NOT_FOUND("packaging not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getPackageDetails in PackagingDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updatePackagingStatus(PackagingDomain packagingDomain) throws Exception {
		try {
			String sql = "UPDATE packaging SET status=? where packagingId=?";
			int res = jdbcTemplate.update(sql, new Object[] {packagingDomain.isStatus(),packagingDomain.getPackagingId() });
			if (res == 1) {
				return "Updates successfully";
			} else
				throw new UPDATE_FAILED("Packaging Status update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception updatePackagingStatus in PackagingDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deletePackaging(BigInteger packagingId) throws Exception {
		try {
			String sql = "DELETE FROM packaging WHERE packagingId=?";
			int res = jdbcTemplate.update(sql, new Object[] { packagingId });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Packaging delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception deletePackaging in PackagingDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updatePackagingName(PackagingDomain packagingDomain) throws Exception {
		try {
			String sql = "UPDATE packaging SET packagingName=? where packagingId=?";
			int res = jdbcTemplate.update(sql, new Object[] {packagingDomain.getPackagingName(),packagingDomain.getPackagingId() });
			if (res == 1) {
				return "Updates successfully";
			} else
				throw new UPDATE_FAILED("Packaging Name update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception updatePackagingName in PackagingDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getPackageDetailsCount() throws Exception {
		try {
			String sql = "select count(packagingId) as total from packaging ";
			return jdbcTemplate.queryForObject(sql, new Object[] { },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
		}  catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getPackageDetailsCount in PackagingDAOImpl"));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
