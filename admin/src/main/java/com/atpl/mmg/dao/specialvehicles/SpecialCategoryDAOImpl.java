package com.atpl.mmg.dao.specialvehicles;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.specialvehicles.SpecialCategoryDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;

@Repository
public class SpecialCategoryDAOImpl implements SpecialCategoryDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(SpecialCategoryDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public SpecialCategoryDomain addSpecialCategory(SpecialCategoryDomain specialCategoryDomain) throws Exception {
		try {
			String sql = "INSERT INTO specialvehicleCategory( specialCategory ) VALUES(?)";
			int res = jdbcTemplate.update(sql, new Object[] { specialCategoryDomain.getSpecialCategory() });
			if (res == 1) {
				return specialCategoryDomain;
			} else
				throw new SAVE_FAILED("Special vehcile save failed");
		} catch (Exception e) {
			logger.error("Exception addSpecialCategory in SpecialCategoryDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String UpdateSpecialCategory(SpecialCategoryDomain specialCategoryDomain) throws Exception {
		try {
			String sql = "UPDATE specialvehicleCategory SET specialCategory=? WHERE specialCategoryId=?";
			int res = jdbcTemplate.update(sql, new Object[] { specialCategoryDomain.getSpecialCategory(),
					specialCategoryDomain.getSpecialCategoryId() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("Special vehcile update failed");
		} catch (Exception e) {
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public List<SpecialCategoryDomain> getSpecialCategory() throws Exception {
		try {
			String sql = "SELECT * FROM specialvehicleCategory where status=1";
			List<SpecialCategoryDomain> specialCategoryDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<SpecialCategoryDomain>(SpecialCategoryDomain.class));
			return specialCategoryDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfileRefferCode in ProfileDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Special vehcile not found");
		} 
		catch (Exception e) {
			logger.error("Exception getSpecialCategory in SpecialCategoryDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteSpecialCategory(int specialCategoryId) throws Exception {
		try {
			String sql = "DELETE FROM specialvehicleCategory WHERE specialCategoryId=?";
			int res = jdbcTemplate.update(sql, new Object[] { specialCategoryId });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("Special vehcile delete failed");

		} catch (Exception e) {
			logger.error("Exception deleteSpecialCategory in SpecialCategoryDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
