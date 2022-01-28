package com.atpl.mmg.dao.report;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.report.RevenueDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;

@Repository
public class RevenueDAOImpl implements RevenueDAO, Constants {
	
	private static final Logger logger = LoggerFactory.getLogger(RevenueDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String addRevenue(RevenueDomain revenueDomain) throws Exception {
		try {
			String sql = "INSERT INTO revenuesm (revenueSourceId,revenueAmount,revenueYear) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] {revenueDomain.getRevenueSourceId(),revenueDomain.getRevenueAmount(),revenueDomain.getRevenueYear()});
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("Revenue save failed");
		} catch (Exception e) {
			logger.error("Exception addRevenue in RevenueDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String addRevenueSource(RevenueDomain revenueDomain) throws Exception {
		try {
			String sql = "INSERT INTO revenuesource (source) VALUES(?)";
			int res = jdbcTemplate.update(sql, new Object[] {revenueDomain.getSource()});

			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("RevenueSource save failed");
		} catch (Exception e) {
			logger.error("Exception addRevenueSource in RevenueDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public RevenueDomain getRevenueSource(int id) throws Exception {
		try {
			String sql = "SELECT * FROM revenuesource where id=?";
			return (RevenueDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<RevenueDomain>(RevenueDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("RevenueSource not found");
		} catch (Exception e) {
			logger.error("Exception getRevenueSourcebyid in RevenueDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}
	@Override
	public String updateRevenueSource(RevenueDomain revenueDomain) throws Exception {
		try {
			String sql =" UPDATE revenuesource SET  source=? WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] {revenueDomain.getSource(),revenueDomain.getId()});
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("RevenueSource update failed");
		} catch (Exception e) {
			logger.error("Exception updateRevenueSource in RevenueDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}
	@Override
	public String deleteRevenueSource(int id)throws Exception {
		try {
			String sql = "DELETE FROM revenuesource WHERE id=?";
			int res = jdbcTemplate.update(sql,new Object[] { id });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("RevenueSource delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteRevenueSource in RevenueDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}
	@Override
	public List<RevenueDomain> getRevenueSource() throws Exception {
		try {
			String sql = "SELECT id,source FROM revenuesource";
			List<RevenueDomain> revenueDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<RevenueDomain>(RevenueDomain.class));
			return revenueDomain;
		} catch (Exception e) {
			logger.error("Exception getRevenueSource in RevenueDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
