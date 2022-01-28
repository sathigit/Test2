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
import com.atpl.mmg.domain.report.DirectCostDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;

@Repository
@SuppressWarnings("rawtypes")
public class DirectCostDAOImpl implements DirectCostDAO, Constants {
	
	private static final Logger logger = LoggerFactory.getLogger(DirectCostDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String addDirectCost(DirectCostDomain directCostDomain) throws Exception {
		try {
			String sql = "INSERT INTO directcost (directCostSourceId,directCostAmount,directCostYear) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] {directCostDomain.getDirectCostSourceId(),directCostDomain.getDirectCostAmount(),directCostDomain.getDirectCostYear()});
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("DirectCost save failed");
		} catch (Exception e) {
			logger.error("Exception addDirectCost in DirectCostDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String addDirectCostSource(DirectCostDomain directCostDomain) throws Exception {
		try {
			String sql = "INSERT INTO directcostsource (source) VALUES(?)";
			int res = jdbcTemplate.update(sql, new Object[] {directCostDomain.getSource()});
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("DirectCostSource save failed");
		} catch (Exception e) {
			logger.error("Exception addDirectCostSource in DirectCostDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}
	@Override
	public String updateDirectCost(DirectCostDomain directCostDomain) throws Exception {
		try {
			String sql =" UPDATE admin.directcostsource SET  source=? WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] {directCostDomain.getSource(),directCostDomain.getId()});
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("DirectCost update failed");
		} catch (Exception e) {
			logger.error("Exception updateDirectCost in DirectCostDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	@Override
	public String deleteDirectCost(int id)throws Exception {
		try {
			String sql = "DELETE FROM directcostsource WHERE id=?";
			int res = jdbcTemplate.update(sql,new Object[] { id });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("DirectCost delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteDirectCost in DirectCostDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}


	@Override
	public DirectCostDomain getDirectCostSource(int id) throws Exception {
	
	try {
		String sql = "SELECT id,source FROM directcostsource where id =?";
		return (DirectCostDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
				new BeanPropertyRowMapper<DirectCostDomain>(DirectCostDomain.class));
	} catch (EmptyResultDataAccessException e) {
		logger.error("Exception getDirectCostSourcebyid in DirectCostDAOImpl", e);
		throw new NOT_FOUND("DirectCost not found");
	} catch (Exception e) {
		logger.error("Exception getDirectCostSourcebyid in DirectCostDAOImpl", e);
		throw new BACKEND_SERVER_ERROR();
	}
	}
	
	@Override
	public List<DirectCostDomain> getDirectCostSource() throws Exception {
		try {
			String sql = "SELECT id,source FROM directcostsource";
			List<DirectCostDomain> directCostDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<DirectCostDomain>(DirectCostDomain.class));
			return directCostDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("Exception getDirectCostSourcebyid in DirectCostDAOImpl", e);
			throw new NOT_FOUND("DirectCost not found");
		}catch (Exception e) {
			logger.error("Exception getDirectCostSource in DirectCostDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
}
