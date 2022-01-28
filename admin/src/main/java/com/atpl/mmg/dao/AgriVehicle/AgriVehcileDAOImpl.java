package com.atpl.mmg.dao.AgriVehicle;

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

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.agriVehicle.AgriVehicleDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.utils.DateUtility;

@Repository
@SuppressWarnings("rawtypes")
public class AgriVehcileDAOImpl implements AgriVehcileDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(AgriVehcileDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String saveAgriVehcile(AgriVehicleDomain agriVehicleDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO agriearthvehicle( company,category,bookingTypeId,creationDate,modificationDate,status,perHour ) VALUES(?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { agriVehicleDomain.getCompany(), agriVehicleDomain.getCategory(),
							agriVehicleDomain.getBookingTypeId(), simpleDateFormat.format(new Date()),
							simpleDateFormat.format(new Date()), agriVehicleDomain.isStatus(),
							agriVehicleDomain.getPerHour() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("AgriVehicle save failed");
		} catch (Exception e) {
			logger.error("Exception saveAgriVehcile in AgriVehcileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<AgriVehicleDomain> getAgriVehcile() throws Exception {
		try {
			String sql = "SELECT * FROM agriearthvehicle";
			List<AgriVehicleDomain> agriVehicleDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<AgriVehicleDomain>(AgriVehicleDomain.class));
			return agriVehicleDomain;
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("AgriVehicle not found");
		} catch (Exception e) {
			logger.error("Exception getAgriVehcile in AgriVehcileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public AgriVehicleDomain agriVehcile(int vehicleCategoryId) throws Exception {
		try {
			String sql = "SELECT * FROM agriearthvehicle where vehicleCategoryId=?";
			return (AgriVehicleDomain) jdbcTemplate.queryForObject(sql, new Object[] { vehicleCategoryId },
					new BeanPropertyRowMapper(AgriVehicleDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("AgriVehicle not found");
		} catch (Exception e) {
			logger.error("Exception agriVehcile in AgriVehcileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateAgriVehicle(AgriVehicleDomain agriVehicleDomain) throws Exception {
		try {

			String sql = "UPDATE agriearthvehicle SET company=?,category=?,perHour=? WHERE vehicleCategoryId=?";

			int res = jdbcTemplate.update(sql,
					new Object[] {agriVehicleDomain.getCompany(),agriVehicleDomain.getCategory(),agriVehicleDomain.getPerHour(),agriVehicleDomain.getVehicleCategoryId()});
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("AgriVehicle update failed");

		}catch (Exception e) {
			logger.error("Exception updateAgriVehicle in AgriVehcileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String deleteAgriVehicleCategory(int vehicleCategoryId) throws Exception {
		try {
			String sql = "DELETE FROM agriearthvehicle WHERE vehicleCategoryId=?";
			int res = jdbcTemplate.update(sql, new Object[] { vehicleCategoryId });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("AgriVehicle delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteVehicleCategory in AgriVehcileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();

		}

	}

}
