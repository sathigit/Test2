package com.atpl.mmg.dao.quotation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.quotation.QuotationDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;

@Repository
@SuppressWarnings("rawtypes")
public class QuotationDAOImpl implements QuotationDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(QuotationDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String saveQuotation(QuotationDomain quotationDomain) throws Exception {
		try {
			String sql = "INSERT INTO quotation(quotationId,source,sourcelatitude,sourcelongitude,destination,destinationlatitude,destinationlongitude,goodsTypeId,kerbWeightId,totalAmount,customerId,emailId,status,pickUpDate,sStreet,sCity,sState,sCountry,sLandMark,dStreet,dCity,dState,dCountry,dLandMark,consignorName,consignorNumber,consigneeName,consigneeNumber,consignorGST,consigneeGST,consignorPAN,consigneePAN,bookedGoodsTypes,sPincode,dPincode) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { quotationDomain.getQuotationId(), quotationDomain.getSource(),
							quotationDomain.getSourcelatitude(), quotationDomain.getSourcelongitude(),
							quotationDomain.getDestination(), quotationDomain.getDestinationlatitude(),
							quotationDomain.getDestinationlongitude(), quotationDomain.getGoodsTypeId(),
							quotationDomain.getKerbWeightId(), quotationDomain.getTotalAmount(),
							quotationDomain.getCustomerId(), quotationDomain.getEmailId(), quotationDomain.getStatus(),
							quotationDomain.getPickUpDate(), quotationDomain.getsStreet(), quotationDomain.getsCity(),
							quotationDomain.getsState(), quotationDomain.getsCountry(), quotationDomain.getsLandMark(),
							quotationDomain.getdStreet(), quotationDomain.getdCity(), quotationDomain.getdState(),
							quotationDomain.getdCountry(), quotationDomain.getdLandMark(), quotationDomain.getConsignorName(), quotationDomain.getConsignorNumber(),
							quotationDomain.getConsigneeName(), quotationDomain.getConsigneeNumber(),
							quotationDomain.getConsignorGST(), quotationDomain.getConsigneeGST(), quotationDomain.getConsignorPAN(),
							quotationDomain.getConsigneePAN(),quotationDomain.getBookedGoodsTypes(),quotationDomain.getsPincode(),quotationDomain.getdPincode() });
			if (res == 1) {
				return "Saved succesfully";
			} else
				throw new SAVE_FAILED("Quotation save failed");
		} catch (Exception e) {
			logger.error("Exception saveQuotation in QuotationDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<QuotationDomain> getQuotation() throws Exception {
		try {
			String sql = "SELECT quotationId,source,sourcelatitude,sourcelongitude,destination,destinationlatitude,destinationlongitude,goodsTypeId,kerbWeightId,totalAmount,customerId,emailId,status,pickUpDate FROM quotation order by creationDate Desc";
			List<QuotationDomain> quotation = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<QuotationDomain>(QuotationDomain.class));
			return quotation;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getQuotation in QuotationDAOImpl", e.getMessage());
			throw new NOT_FOUND("Quotation not found");
		} catch (Exception e) {
			logger.error("Exception getQuotation in QuotationDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public QuotationDomain getQuotations(String id) throws Exception {
		try {
			String sql = "SELECT quotationId,source,sourcelatitude,sourcelongitude,destination,destinationlatitude,destinationlongitude,goodsTypeId,kerbWeightId,totalAmount,customerId,emailId,status,pickUpDate,consignorName,consignorNumber,consigneeName,consigneeNumber,consignorGST,consigneeGST,consignorPAN,consigneePAN,bookedGoodsTypes from quotation where quotationId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<QuotationDomain>(QuotationDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getQuotation in QuotationDAOImpl", e.getMessage());
			throw new NOT_FOUND("Quotation not found");
		} catch (Exception e) {
			logger.error("Exception getQuotation in QuotationDAOImpl", e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateQuotation(QuotationDomain quotationDomain) throws Exception {
		try {
			String sql = "UPDATE quotation SET source=?,destination=?,goodsTypeId=?,kerbWeightId=?,totalAmount=?,status=?,pickUpDate=? WHERE quotationId=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { quotationDomain.getSource(), quotationDomain.getDestination(),
							quotationDomain.getGoodsTypeId(), quotationDomain.getKerbWeightId(),
							quotationDomain.getTotalAmount(), quotationDomain.getStatus(),
							quotationDomain.getPickUpDate(), quotationDomain.getQuotationId() });
			if (res == 1) {
				return "Updates successfully";
			} else
				throw new UPDATE_FAILED("Quotation update failed");
		} catch (Exception e) {
			logger.error("Exception updateQuotation in QuotationDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<QuotationDomain> getQuotationByCustomer(String customerId) throws Exception {
		try {
			String sql = "SELECT quotationId,source,sourcelatitude,sourcelongitude,destination,destinationlatitude,destinationlongitude,goodsTypeId,kerbWeightId,totalAmount,customerId,emailId,status,pickUpDate,consignorName,consignorNumber,consigneeName,consigneeNumber,consignorGST,consigneeGST,consignorPAN,consigneePAN,bookedGoodsTypes FROM quotation where customerId=? order by creationDate Desc";
			List<QuotationDomain> quotation = jdbcTemplate.query(sql, new Object[] { customerId },
					new BeanPropertyRowMapper<QuotationDomain>(QuotationDomain.class));
			return quotation;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getQuotation in QuotationDAOImpl", e.getMessage());
			throw new NOT_FOUND("Quotation not found");
		} catch (Exception e) {
			logger.error("Exception getQuotationByCustomer in QuotationDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<QuotationDomain> getQuotation(String status, String city) throws Exception {
		try {
			String sql = "SELECT quotationId,source,sourcelatitude,sourcelongitude,destination,destinationlatitude,destinationlongitude,goodsTypeId,kerbWeightId,totalAmount,customerId,emailId,status,pickUpDate,consignorName,consignorNumber,consigneeName,consigneeNumber,consignorGST,consigneeGST,consignorPAN,consigneePAN,bookedGoodsTypes FROM quotation where status=? and sCity=? order by creationDate Desc";
			List<QuotationDomain> quotation = jdbcTemplate.query(sql, new Object[] { status, city },
					new BeanPropertyRowMapper<QuotationDomain>(QuotationDomain.class));
			return quotation;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getQuotation in QuotationDAOImpl", e.getMessage());
			throw new NOT_FOUND("Quotation not found");
		} catch (Exception e) {
			logger.error("Exception getQuotation in QuotationDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public QuotationDomain getDashboard(String customerId) throws Exception {
		try {
			String sql = "select count(*) as total from quotation where customerId =?";
			return (QuotationDomain) jdbcTemplate.queryForObject(sql, new Object[] {customerId},
					new BeanPropertyRowMapper<QuotationDomain>(QuotationDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException dashboard in QuotationDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception dashboard in QuotationDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
