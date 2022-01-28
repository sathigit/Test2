package com.atpl.mmg.dao.fdtrans;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Role;
import com.atpl.mmg.domain.fdtrans.FareDistributionTransactionDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class FareDistributionTransactionDAOImpl implements FareDistributionTransactionDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public FareDistributionTransactionDomain save(FareDistributionTransactionDomain fareDistributionTransactionDomain)
			throws Exception {
		try {
			String sql = "INSERT INTO fdtransaction(uuid,bookingId,driverId,franchiseId,bookingAmount,labourAmount,insuranceAmount,gstAmount,totalCost,bookingDate,status,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { fareDistributionTransactionDomain.getUuid(),
					fareDistributionTransactionDomain.getBookingId(), fareDistributionTransactionDomain.getDriverId(),
					fareDistributionTransactionDomain.getFranchiseId(),
					fareDistributionTransactionDomain.getBookingAmount(),
					fareDistributionTransactionDomain.getLabourAmount(),
					fareDistributionTransactionDomain.getInsuranceAmount(),
					fareDistributionTransactionDomain.getGstAmount(), fareDistributionTransactionDomain.getTotalCost(),
					fareDistributionTransactionDomain.getBookingDate(), true, DateUtility.setTimeZone(new Date()),
					DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return fareDistributionTransactionDomain;
			} else
				throw new SAVE_FAILED("Fare distribution transaction save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception saveFareDistributionTransaction in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(FareDistributionTransactionDomain fareDistributionTransactionDomain) throws Exception {
		try {
			String sql = "UPDATE fdtransaction SET driverId=?,bookingAmount=?,labourAmount=?,insuranceAmount=?,gstAmount=?,totalCost=?,bookingDate=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { fareDistributionTransactionDomain.getDriverId(),
					fareDistributionTransactionDomain.getBookingAmount(),
					fareDistributionTransactionDomain.getLabourAmount(),
					fareDistributionTransactionDomain.getInsuranceAmount(),
					fareDistributionTransactionDomain.getGstAmount(), fareDistributionTransactionDomain.getTotalCost(),
					fareDistributionTransactionDomain.getBookingDate(), DateUtility.setTimeZone(new Date()),
					fareDistributionTransactionDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Fare Distribution Transaction Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateFareDistributionTransaction in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<FareDistributionTransactionDomain> getFareDistributioneTrans() throws Exception {
		try {
			String sql = "select * from fdtransaction";
			List<FareDistributionTransactionDomain> fareDistributionDomainList = jdbcTemplate.query(sql,
					new Object[] {}, new BeanPropertyRowMapper<FareDistributionTransactionDomain>(
							FareDistributionTransactionDomain.class));
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributioneTrans in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributioneTrans in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public FareDistributionTransactionDomain getFareDistributionTrans(String uuid) throws Exception {
		try {
			String sql = "select * from fdtransaction where uuid=?";
			FareDistributionTransactionDomain fareDistributionDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { uuid }, new BeanPropertyRowMapper<FareDistributionTransactionDomain>(
							FareDistributionTransactionDomain.class));
			return fareDistributionDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributionTrans in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributionTrans in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public FareDistributionTransactionDomain getFareDistributionTrans(BigInteger bookingId) throws Exception {
		try {
			String sql = "select * from fdtransaction where bookingId=?";
			FareDistributionTransactionDomain fareDistributionDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { bookingId }, new BeanPropertyRowMapper<FareDistributionTransactionDomain>(
							FareDistributionTransactionDomain.class));
			return fareDistributionDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributionTrans in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributionTrans in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<FareDistributionTransactionDomain> getFDTransDetails(String companyProfileId, Role role,
			String startDate, String endDate) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<FareDistributionTransactionDomain> fareDistributionDomainList = null;
			if (role.equals(Role.DRIVER)) {
				sql.append(
						"select fdt.bookingId,fdt.bookingDate,fdt.driverId,fdt.franchiseId,fdt.totalCost,fdtd.amount,fdtd.amount as earning,sum(fdtd.amount) as totalEarnings,fd.fareDistributionTypeId  from faredistribution fd,fdtransactiondetail fdtd,fdtransaction fdt "
								+ "where fd.uuid=fdtd.fareDistributionId AND fdtd.fdTransactionId=fdt.uuid AND fdt.driverId=? AND fd.roleId = ? AND fdt.bookingDate>=? and fdt.bookingDate<=? group by fdtd.fdTransactionId,fdtd.fareDistributionId  having count(fdtd.fdTransactionId)>=1 order by fdt.bookingDate desc");
				fareDistributionDomainList = jdbcTemplate.query(sql.toString(),
						new Object[] { companyProfileId, role.getCode(), startDate, endDate },
						new BeanPropertyRowMapper<FareDistributionTransactionDomain>(
								FareDistributionTransactionDomain.class));
			} else if (role.equals(Role.FRANCHISE)) {
				sql.append(
						"select fdt.bookingId,fdt.bookingDate,fdt.driverId,fdt.franchiseId,fdt.totalCost,fdtd.amount,fdtd.amount as earning,sum(fdtd.amount) as totalEarnings,fd.fareDistributionTypeId  from fdtransaction fdt, fdtransactiondetail fdtd, faredistribution fd where fdt.uuid = fdtd.fdTransactionId AND fd.uuid = fdtd.fareDistributionId AND fdt.franchiseId =? AND fd.roleId =? AND fdt.bookingDate>=? and fdt.bookingDate<=? group by fdtd.fdTransactionId,fdtd.fareDistributionId  having count(fdtd.fdTransactionId)>=1 order by fdt.bookingDate desc");
				fareDistributionDomainList = jdbcTemplate.query(sql.toString(),
						new Object[] { companyProfileId, role.getCode(), startDate, endDate },
						new BeanPropertyRowMapper<FareDistributionTransactionDomain>(
								FareDistributionTransactionDomain.class));
			} else {
				sql.append(
						"select fdt.bookingId,fdt.bookingDate,fdt.driverId,fdt.franchiseId,fdt.totalCost,fdtd.amount,fdtd.amount as earning,sum(fdtd.amount) as totalEarnings,fd.fareDistributionTypeId  from fdtransaction fdt, fdtransactiondetail fdtd, faredistribution fd where fdt.uuid = fdtd.fdTransactionId AND fd.uuid = fdtd.fareDistributionId AND fd.roleId = ? AND fdt.bookingDate>=? and fdt.bookingDate<=? group by fdtd.fdTransactionId,fdtd.fareDistributionId  having count(fdtd.fdTransactionId)>=1 order by fdt.bookingDate desc");
				fareDistributionDomainList = jdbcTemplate.query(sql.toString(),
						new Object[] { role.getCode(), startDate, endDate },
						new BeanPropertyRowMapper<FareDistributionTransactionDomain>(
								FareDistributionTransactionDomain.class));
			}
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributioneTrans in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributioneTrans in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public List<FareDistributionTransactionDomain> getFDTransDetails(String companyProfileId, Role role, int limit)
			throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<FareDistributionTransactionDomain> fareDistributionDomainList = null;
			if (role.equals(Role.DRIVER)) {
				sql.append(
						"select fdt.bookingId,fdt.bookingDate,fdt.driverId,fdt.franchiseId,fdt.totalCost,fdtd.amount,fdtd.amount as earning,sum(fdtd.amount) as totalEarnings,fd.fareDistributionTypeId  from faredistribution fd,fdtransactiondetail fdtd,fdtransaction fdt "
								+ "where fd.uuid=fdtd.fareDistributionId AND fdtd.fdTransactionId=fdt.uuid AND fdt.driverId=? AND fd.roleId = ? group by fdtd.fdTransactionId,fdtd.fareDistributionId  having count(fdtd.fdTransactionId)>=1 order by fdt.bookingDate desc");
				if (limit != 0)
					sql.append(" LIMIT  " + limit);
				fareDistributionDomainList = jdbcTemplate.query(sql.toString(),
						new Object[] { companyProfileId, role.getCode() },
						new BeanPropertyRowMapper<FareDistributionTransactionDomain>(
								FareDistributionTransactionDomain.class));
			} else if (role.equals(Role.FRANCHISE)) {
				sql.append(
						"select fdt.bookingId,fdt.bookingDate,fdt.driverId,fdt.franchiseId,fdt.totalCost,fdtd.amount,fdtd.amount as earning,sum(fdtd.amount) as totalEarnings,fd.fareDistributionTypeId  from fdtransaction fdt, fdtransactiondetail fdtd, faredistribution fd where fdt.uuid = fdtd.fdTransactionId AND fd.uuid = fdtd.fareDistributionId AND fdt.franchiseId =? AND fd.roleId =? group by fdtd.fdTransactionId,fdtd.fareDistributionId  having count(fdtd.fdTransactionId)>=1 order by fdt.bookingDate desc");
				if (limit != 0)
					sql.append(" LIMIT  " + limit);
				fareDistributionDomainList = jdbcTemplate.query(sql.toString(),
						new Object[] { companyProfileId, role.getCode() },
						new BeanPropertyRowMapper<FareDistributionTransactionDomain>(
								FareDistributionTransactionDomain.class));
			} else {
				sql.append(
						"select fdt.bookingId,fdt.bookingDate,fdt.driverId,fdt.franchiseId,fdt.totalCost,fdtd.amount,fdtd.amount as earning,sum(fdtd.amount) as totalEarnings,fd.fareDistributionTypeId  from fdtransaction fdt, fdtransactiondetail fdtd, faredistribution fd where fdt.uuid = fdtd.fdTransactionId AND fd.uuid = fdtd.fareDistributionId AND fd.roleId = ? group by fdtd.fdTransactionId,fdtd.fareDistributionId  having count(fdtd.fdTransactionId)>=1 order by fdt.bookingDate desc");
				if (limit != 0)
					sql.append(" LIMIT  " + limit);
				fareDistributionDomainList = jdbcTemplate.query(sql.toString(), new Object[] { role.getCode() },
						new BeanPropertyRowMapper<FareDistributionTransactionDomain>(
								FareDistributionTransactionDomain.class));
			}
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributioneTrans in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributioneTrans in FareDistributionTransactionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

}
