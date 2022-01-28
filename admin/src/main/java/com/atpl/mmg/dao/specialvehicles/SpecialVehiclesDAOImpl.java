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
import com.atpl.mmg.domain.specialvehicles.SpecialVehiclesDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;

@Repository
@SuppressWarnings("rawtypes")
public class SpecialVehiclesDAOImpl implements SpecialVehiclesDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(SpecialCategoryDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public SpecialVehiclesDomain addSpecialVehicle(SpecialVehiclesDomain specialVehiclesDomain) throws Exception {
		try {
			String sql = "INSERT INTO specialvehicle( specialCategoryId,model,perHour ) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { specialVehiclesDomain.getSpecialCategoryId(),
					specialVehiclesDomain.getModel(), specialVehiclesDomain.getPerHour() });
			if (res == 1) {
				return specialVehiclesDomain;
			} else
				throw new SAVE_FAILED("Special Vehcile save failed");
		} catch (Exception e) {
			logger.error("Exception addSpecialVehicle in SpecialVehiclesDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String UpdateSpecialVehicle(SpecialVehiclesDomain specialVehiclesDomain) throws Exception {
		try {
			String sql = "UPDATE specialvehicle SET specialCategoryId=?,model=?,perHour=? WHERE vehicleCategoryId=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { specialVehiclesDomain.getSpecialCategoryId(), specialVehiclesDomain.getModel(),
							specialVehiclesDomain.getPerHour(), specialVehiclesDomain.getVehicleCategoryId() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("Special Vehcile update failed");
		} catch (Exception e) {
			logger.error("Exception UpdateSpecialVehicle in SpecialVehiclesDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<SpecialVehiclesDomain> getSpecialVehicle() throws Exception {
		try {
			String sql = "SELECT * FROM specialvehicle where status=1";
			List<SpecialVehiclesDomain> specialVehiclesDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<SpecialVehiclesDomain>(SpecialVehiclesDomain.class));
			return specialVehiclesDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getBank in ProfileDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Special Vehcile not found");
		} catch (Exception e) {
			logger.error("Exception getSpecialVehicle in SpecialVehiclesDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteSpecialVehicle(int vehicleCategoryId) throws Exception {
		try {
			String sql = "DELETE FROM specialvehicle WHERE vehicleCategoryId=?";
			int res = jdbcTemplate.update(sql, new Object[] { vehicleCategoryId });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("Special Vehcile  delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteSpecialVehicle in SpecialVehiclesDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();

		}
	}

}
