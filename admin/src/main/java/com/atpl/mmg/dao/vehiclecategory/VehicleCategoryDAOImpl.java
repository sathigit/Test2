package com.atpl.mmg.dao.vehiclecategory;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.vehiclecategory.VehicleCategoryDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.exception.MmgRestException.VEHICLECATEGORY_AlREADY_EXISTS;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class VehicleCategoryDAOImpl implements VehicleCategoryDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(VehicleCategoryDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public String saveVehicleCategory(VehicleCategoryDomain vehicleCategoryDomain) throws Exception {
		try {
			String sql = "INSERT INTO vehiclecategories(category,goodsTypeId,weightId, pricePerKm, pricePerMin, baseFare, commission,"
					+ " vehicleEngineCapacity,vehicleSpeed,fuelTank,grossVehicleWeight,weight,overallLength,loadBodyLength,"
					+ "waitingTimeCharge,nightFee,minimumBookingKm,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { vehicleCategoryDomain.getCategory(), vehicleCategoryDomain.getGoodsTypeId(),
							vehicleCategoryDomain.getWeightId(), vehicleCategoryDomain.getPricePerKm(),
							vehicleCategoryDomain.getPricePerMin(), vehicleCategoryDomain.getBaseFare(),
							vehicleCategoryDomain.getCommission(), vehicleCategoryDomain.getVehicleEngineCapacity(),
							vehicleCategoryDomain.getVehicleSpeed(), vehicleCategoryDomain.getFuelTank(),
							vehicleCategoryDomain.getGrossVehicleWeight(), vehicleCategoryDomain.getWeight(),
							vehicleCategoryDomain.getOverallLength(), vehicleCategoryDomain.getLoadBodyLength(),
							vehicleCategoryDomain.getWaitingTimeCharge(), vehicleCategoryDomain.getNightFee(),
							vehicleCategoryDomain.getMinimumBookingKm(), DateUtility.setTimeZone(new Date()),
							DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("VehicleCategory save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception saveVehicleCategory in VehicleCategoryDAOImpl ")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public VehicleCategoryDomain getVehicleCategoryByNameAndWeight(String category, int weightId) throws Exception {
		try {
			String sql = "SELECT vehicleCategoryId FROM vehiclecategories where category =? and weightId=?";
			VehicleCategoryDomain vehicleCategories = jdbcTemplate.queryForObject(sql,
					new Object[] { category, weightId },
					new BeanPropertyRowMapper<VehicleCategoryDomain>(VehicleCategoryDomain.class));
			return vehicleCategories;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"EmptyResultDataAccessException getVehicleCategoryByNameAndWeight in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehicleCategoryByNameAndWeight in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public List<VehicleCategoryDomain> getVehicleCategory(int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM vehiclecategories where weight !=0 and isActive =1 order by category asc");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT * FROM vehiclecategories where weight !=0 and isActive =1 order by category asc";
			List<VehicleCategoryDomain> vehicleCategories = jdbcTemplate.query(sql.toString(), new Object[] {},
					new BeanPropertyRowMapper<VehicleCategoryDomain>(VehicleCategoryDomain.class));
			return vehicleCategories;
		}  catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehicleCategory in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String updateVehicleCategory(VehicleCategoryDomain vehicleCategoryId) throws Exception {
		try {
			String sql = "UPDATE vehiclecategories SET category=?,goodsTypeId=?,weightId=?,pricePerKm=?,pricePerMin=?,baseFare=?,vehicleEngineCapacity=?,vehicleSpeed=?,"
					+ "fuelTank=?,grossVehicleWeight=?, weight=?, overallLength=?,loadBodyLength=?,"
					+ "waitingTimeCharge=?,minimumBookingKm=?,nightFee=? WHERE vehicleCategoryId=?";

			int res = jdbcTemplate.update(sql,
					new Object[] { vehicleCategoryId.getCategory(), vehicleCategoryId.getGoodsTypeId(),
							vehicleCategoryId.getWeightId(), vehicleCategoryId.getPricePerKm(),
							vehicleCategoryId.getPricePerMin(), vehicleCategoryId.getBaseFare(),
							vehicleCategoryId.getVehicleEngineCapacity(), vehicleCategoryId.getVehicleSpeed(),
							vehicleCategoryId.getFuelTank(), vehicleCategoryId.getGrossVehicleWeight(),
							vehicleCategoryId.getWeight(), vehicleCategoryId.getOverallLength(),
							vehicleCategoryId.getLoadBodyLength(), vehicleCategoryId.getWaitingTimeCharge(),
							vehicleCategoryId.getMinimumBookingKm(), vehicleCategoryId.getNightFee(),
							vehicleCategoryId.getVehicleCategoryId() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("VehicleCategory update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception updateVehicleCategory in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String enableDisableVehicleCategory(int vehicleCategoryId, boolean status) throws Exception {
		try {
			String sql = "update vehiclecategories set isActive=? where vehicleCategoryId=?";
			int res = jdbcTemplate.update(sql, new Object[] { status, vehicleCategoryId });
			if (res == 1) {
				return "Enabled successfully";
			} else
				throw new DELETE_FAILED("VehicleCategory delete failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception enableDisableVehicleCategory in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();

		}

	}

	@Override
	public VehicleCategoryDomain getVehicleCategorybyId(Integer vehicleCategoryId) throws Exception {
		try {
			String sql = "SELECT * from vehiclecategories where vehicleCategoryId=? ";
			return (VehicleCategoryDomain) jdbcTemplate.queryForObject(sql, new Object[] { vehicleCategoryId },
					new BeanPropertyRowMapper<VehicleCategoryDomain>(VehicleCategoryDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"EmptyResultDataAccessException getVehicleCategorybyId in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new NOT_FOUND("VehicleCategory not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehicleCategorybyId in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();

		}

	}

	public List<VehicleCategoryDomain> getVehicleCategorybyWeight(String kerbWeight,int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM vehiclecategories where isActive=? and weightId=?");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT * FROM vehiclecategories where isActive=? and weightId=?";
			List<VehicleCategoryDomain> vehicleCategories = jdbcTemplate.query(sql.toString(), new Object[] { true, kerbWeight },
					new BeanPropertyRowMapper<VehicleCategoryDomain>(VehicleCategoryDomain.class));
			return vehicleCategories;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"EmptyResultDataAccessException getVehicleCategorybyWeight in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehicleCategorybyWeight in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public VehicleCategoryDomain getVehicleCategorybyId() throws Exception {
		try {
			String sql = "SELECT vehicleCategoryId from vehiclecategories order by vehicleCategoryId DESC limit 1";
			return jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper<VehicleCategoryDomain>(VehicleCategoryDomain.class));
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"EmptyResultDataAccessException getVehicleCategorybyId in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new NOT_FOUND("VehicleCategory not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehicleCategorybyId in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public List<VehicleCategoryDomain> getVehicle(String goodsTypeId, int weightId,int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM vehiclecategories vc,vehicleimages vi where  vc.vehicleCategoryId=vi.vehicleCategoryId and isActive=1 and FIND_IN_SET(?,vc.goodsTypeId) and vc.weightId=?");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT * FROM vehiclecategories vc,vehicleimages vi where  vc.vehicleCategoryId=vi.vehicleCategoryId and isActive=1 and FIND_IN_SET(?,vc.goodsTypeId) and vc.weightId=?";
			List<VehicleCategoryDomain> newVehicleDomain = jdbcTemplate.query(sql.toString(),
					new Object[] { goodsTypeId, weightId },
					new BeanPropertyRowMapper<VehicleCategoryDomain>(VehicleCategoryDomain.class));
			return newVehicleDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehicle in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<VehicleCategoryDomain> getVehiclesByDirectBooking(int kerbweight) throws Exception {
		try {
			String sql = " select vehiclecategoryId from vehiclecategories where isActive=1 and weight<=?";
			List<VehicleCategoryDomain> newVehicleDomain = jdbcTemplate.query(sql, new Object[] { kerbweight },
					new BeanPropertyRowMapper<VehicleCategoryDomain>(VehicleCategoryDomain.class));
			return newVehicleDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehiclesByDirectBooking in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<VehicleCategoryDomain> getVehiclesByKerbweightId(int weightId) throws Exception {
		try {
			List<VehicleCategoryDomain> newVehicleDomain = null;
			String sql = "SELECT distinct vc.vehicleCategoryId,vc.category,vc.pricePerKm,vc.baseFare,vc.vehicleSpeed,vc.grossVehicleWeight,vc.weight,vc.weightId,vi.name,vi.path,vc.waitingTimeCharge,vc.minimumBookingKm FROM vehiclecategories vc,vehicleimages vi where vc.vehicleCategoryId=vi.vehicleCategoryId and isActive=1 and vc.weightId=?";
			newVehicleDomain = jdbcTemplate.query(sql.toString(), new Object[] { weightId },
					new BeanPropertyRowMapper<VehicleCategoryDomain>(VehicleCategoryDomain.class));
			return newVehicleDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehiclesByKerbweightId in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();

		}
	}

	@Override
	public DashboardDomain getVehicleCategorybyWeightCount(String kerbWeight) throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM vehiclecategories where isActive=? and weightId=?";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { true, kerbWeight },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"EmptyResultDataAccessException getVehicleCategoryByNameAndWeight in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehicleCategoryByNameAndWeight in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getVehicleCategoryCount() throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM vehiclecategories where weight !=0 and isActive =1";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql,
					new Object[] {},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"EmptyResultDataAccessException getVehicleCategoryCount in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehicleCategoryCount in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getVehicleCount(String goodsTypeId, int weightId) throws Exception {
		try {
			String sql = "SELECT count(*) as total FROM vehiclecategories vc,vehicleimages vi where  vc.vehicleCategoryId=vi.vehicleCategoryId and isActive=1 and FIND_IN_SET(?,vc.goodsTypeId) and vc.weightId=?";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql,
					new Object[] {goodsTypeId,weightId},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"EmptyResultDataAccessException getVehicleCount in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
					"Exception getVehicleCount in VehicleCategoryDAOImpl")+ JsonUtil.toJsonString(e.getMessage()));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
