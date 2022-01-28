package com.atpl.mmg.dao.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.domain.admin.CatalogTransactionFeeDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class CatalogTransactionFeeDaoImpl implements CatalogTransactionFeeDao{

	private static final Logger logger = LoggerFactory.getLogger(CatalogTransactionFeeDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<CatalogTransactionFeeDomain> getModificationFee(CatalogTransactionFeeDomain catalogTransactionFeeDomain) throws Exception {
		try {
//			select result.* from ((SELECT modifyPercent,goodsCategoryId FROM modifycatalogue where issueType="Destination" and paymentMode=2 and timeBound="12:24")  union (select modifyPercent,goodsCategoryId from modifycatalogue where FIND_IN_SET('2',goodsCategoryId) )) result;
			String sql = "SELECT modifyPercent,goodsCategoryId,timeBound,modifyFee FROM catalogtransactionfee where issueType=? and paymentMode=? and FIND_IN_SET(?,goodsCategoryId)";
			List<CatalogTransactionFeeDomain> catalogTransactionFee = jdbcTemplate.query(sql, new Object[] {catalogTransactionFeeDomain.getIssueType(),catalogTransactionFeeDomain.getPaymentMode(),
					catalogTransactionFeeDomain.getGoodsCategoryId()},
					new BeanPropertyRowMapper<CatalogTransactionFeeDomain>(CatalogTransactionFeeDomain.class));
			return catalogTransactionFee;
		} catch (Exception e) {
			logger.error("Exception getModificationFee in ModifyDaoImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String saveModificationFee(CatalogTransactionFeeDomain catalogTransactionFeeDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO catalogtransactionfee(uuid,issueType,paymentMode,goodsCategoryId,timeBound,modifyPercent,modifyFee,cancelPercent,cancelFee,refund,creationDate,modificationDate)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { catalogTransactionFeeDomain.getUuid(),catalogTransactionFeeDomain.getIssueType(),catalogTransactionFeeDomain.getPaymentMode(),
					catalogTransactionFeeDomain.getGoodsCategoryId(),catalogTransactionFeeDomain.getTimeBound(),catalogTransactionFeeDomain.getModifyPercent(),
					catalogTransactionFeeDomain.getModifyFee(),catalogTransactionFeeDomain.getCancelPercent(),catalogTransactionFeeDomain.getCancelFee(),
					catalogTransactionFeeDomain.isRefund(),simpleDateFormat.format(new Date()),simpleDateFormat.format(new Date())});
			if (res == 1) {
				return "Saved modification fee successfully";
			} else
				throw new SAVE_FAILED("Modification Fee save failed");
		} catch (Exception e) {
			logger.error("Exception saveModificationFee in ModifyDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
