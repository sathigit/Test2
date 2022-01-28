package com.atpl.mmg.dao.vehicle;

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
import com.atpl.mmg.domain.vehicle.VehicleDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;

@Repository
@SuppressWarnings("rawtypes")
public class VehicleDAOImpl implements VehicleDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(VehicleDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<VehicleDomain> getVehicle(int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT category,vehicleCategoryId,kerbWeightId,goodsTypeId FROM vehicles");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);

//			String sql = "SELECT category,vehicleCategoryId,kerbWeightId,goodsTypeId FROM vehicles";
			List<VehicleDomain> vehicles = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<VehicleDomain>(VehicleDomain.class));
			return vehicles;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getVehicle in VehicleDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Vehicle not found");
		} catch (Exception e) {
			logger.error("Exception getVehicle in VehicleDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<VehicleDomain> getVehicle(String goodsTypeId, String kerbWeightId,int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT category,vehicleCategoryId,kerbWeightId,goodsTypeId FROM vehicles where goodsTypeId=? and kerbWeightId=? ");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT category,vehicleCategoryId,kerbWeightId,goodsTypeId FROM vehicles where goodsTypeId=? and kerbWeightId=? ";
			List<VehicleDomain> newVehicleDomain = jdbcTemplate.query(sql.toString(), new Object[] { goodsTypeId, kerbWeightId },
					new BeanPropertyRowMapper<VehicleDomain>(VehicleDomain.class));
			return newVehicleDomain;
		} catch (Exception e) {
			logger.error("Exception getVehicle in VehicleDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<VehicleDomain> getVehicleByKerbWeightId(String kerbWeightId,int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT distinct(vehicleCategoryId) FROM  vehicles where kerbWeightId = ? ");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);

//			String sql = "SELECT distinct(vehicleCategoryId) FROM  vehicles where kerbWeightId = ? ";
			List<VehicleDomain> newVehicleDomain = jdbcTemplate.query(sql.toString(), new Object[] { kerbWeightId },
					new BeanPropertyRowMapper<VehicleDomain>(VehicleDomain.class));
			return newVehicleDomain;
		} catch (Exception e) {
			logger.error("Exception getVehicleByKerbWeightId in VehicleDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteVehicle(int id) throws Exception {
		try {
			String sql = "DELETE FROM vehicle WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("Vehicle delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteVehicle in VehicleDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<VehicleDomain> getVehicle(String vehicleCategoryId) throws Exception {
		try {
			String sql = "SELECT * from vehicles where vehicleCategoryId=? ";
			List<VehicleDomain> newVehicleDomain = jdbcTemplate.query(sql, new Object[] { vehicleCategoryId },
					new BeanPropertyRowMapper<VehicleDomain>(VehicleDomain.class));
			return newVehicleDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getVehicle in VehicleDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Vehicle not found");
		} catch (Exception e) {
			logger.error("Exception getVehicle in VehicleDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String saveVehicle(VehicleDomain VehicleDomain) throws Exception {
		try {
			String sql = "INSERT INTO vehicles(vehicleCategoryId, category, goodsTypeId, kerbWeightId)"
					+ " VALUES(?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { VehicleDomain.getVehicleCategoryId(),
					VehicleDomain.getCategory(), VehicleDomain.getGoodsTypeId(), VehicleDomain.getKerbWeightId() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("Vehicle save failed");
		} catch (Exception e) {
			logger.error("Exception saveVehicle in VehicleDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();

		}

	}

	@Override
	public DashboardDomain getVehicleCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM vehicles";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (Exception e) {
			logger.error("Exception getVehicleCount in VehicleDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getVehicleByTypeAndKerbweightCount(String goodsTypeId, String kerbWeightId) throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM vehicles where goodsTypeId=? and kerbWeightId=? ";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { kerbWeightId },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (Exception e) {
			logger.error("Exception getVehicleByTypeAndKerbweightCount in VehicleDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getVehicleByKerbWeightIdCount(String kerbWeightId) throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM vehicles where  kerbWeightId=? ";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { kerbWeightId },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (Exception e) {
			logger.error("Exception getVehicleByKerbWeightIdCount in VehicleDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}	}

}
