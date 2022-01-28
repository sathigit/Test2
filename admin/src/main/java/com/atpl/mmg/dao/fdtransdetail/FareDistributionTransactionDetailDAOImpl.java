package com.atpl.mmg.dao.fdtransdetail;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.domain.fdtrans.FareDistributionTransactionDomain;
import com.atpl.mmg.domain.fdtransdetail.FareDistributionTransactionDetailDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class FareDistributionTransactionDetailDAOImpl implements FareDistributionTransactionDetailDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String save(FareDistributionTransactionDetailDomain fareDistrbutionTransactionDetailDomain)
			throws Exception {
		try {
			String sql = "INSERT INTO fdtransactiondetail(uuid,fdTransactionId,fareDistributionId,amount,status,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { fareDistrbutionTransactionDetailDomain.getUuid(),
							fareDistrbutionTransactionDetailDomain.getFdTransactionId(),
							fareDistrbutionTransactionDetailDomain.getFareDistributionId(),
							fareDistrbutionTransactionDetailDomain.getAmount(),
							fareDistrbutionTransactionDetailDomain.isStatus(), DateUtility.setTimeZone(new Date()),DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return " Fare distribution transaction saved successfully";
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
	public String update(FareDistributionTransactionDetailDomain fareDistrbutionTransactionDetailDomain)
			throws Exception {
		try {
			String sql = "UPDATE fdtransactiondetail SET amount=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { fareDistrbutionTransactionDetailDomain.getAmount(),DateUtility.setTimeZone(new Date()),
					fareDistrbutionTransactionDetailDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Fare Distribution Transaction Detail Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateFareDistributionTrasactionDetail in FareDistributionDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<FareDistributionTransactionDetailDomain> getFareDistributioneTransDetail() throws Exception {
		try {
			String sql = "select * from fdtransactiondetail";
			List<FareDistributionTransactionDetailDomain> fareDistributionDomainList = jdbcTemplate.query(sql,
					new Object[] {}, new BeanPropertyRowMapper<FareDistributionTransactionDetailDomain>(
							FareDistributionTransactionDetailDomain.class));
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributioneTransDetail in FareDistrbutionTransactionDetailDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributioneTransDetail in FareDistrbutionTransactionDetailDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public FareDistributionTransactionDetailDomain getFareDistributionTransDetail(String uuid) throws Exception {
		try {
			String sql = "select * from fdtransactiondetail where uuid=?";
			FareDistributionTransactionDetailDomain fareDistributionDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { uuid }, new BeanPropertyRowMapper<FareDistributionTransactionDetailDomain>(
							FareDistributionTransactionDetailDomain.class));
			return fareDistributionDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributionTransDetail in FareDistrbutionTransactionDetailDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributionTransDetail in FareDistrbutionTransactionDetailDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<FareDistributionTransactionDetailDomain> getFareDistributioneTransDetail(String fdType,
			String franchiseId,BigInteger bookingId) throws Exception {
		try {
			String sql = "select fdtd.uuid, fd.percentage from fdtransactiondetail fdtd,faredistribution fd,faredistributiontype fdtp,fdtransaction fdt where fdtd.fareDistributionId = fd.uuid and fd.fareDistributionTypeId=fdtp.uuid and fdtd.fdTransactionId= fdt.uuid and fdtp.type=? and  fd.franchiseId=? and fdt.bookingId=?";
			List<FareDistributionTransactionDetailDomain> fareDistributionDomainList = jdbcTemplate.query(sql,
					new Object[] { fdType, franchiseId,bookingId },
					new BeanPropertyRowMapper<FareDistributionTransactionDetailDomain>(
							FareDistributionTransactionDetailDomain.class));
			return fareDistributionDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getFareDistributioneTransDetail in FareDistrbutionTransactionDetailDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getFareDistributioneTransDetail in FareDistrbutionTransactionDetailDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();

		}
	}

}
