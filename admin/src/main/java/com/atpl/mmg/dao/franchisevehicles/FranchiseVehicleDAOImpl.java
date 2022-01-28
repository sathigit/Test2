package com.atpl.mmg.dao.franchisevehicles;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.franchisevehicles.FranchiseVehicleDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;

@Repository
public class FranchiseVehicleDAOImpl implements FranchiseVehicleDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(FranchiseVehicleDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<FranchiseVehicleDomain> getAllVehicleImages(String goodsTypeId, String kerbWeightId,double numberOfTon)
			throws Exception {
		try {
			String sql = null;
			List<FranchiseVehicleDomain> franchisevehicleimages = new ArrayList<FranchiseVehicleDomain>();
			if (numberOfTon != 0) {
				sql = "select DISTINCT a.category, a.vehicleCategoryId, a.goodsTypeId,a.weightId ,avi.path, avi.size,avi.imageId, avi.name from vehiclecategories a, vehicleimages avi where a.vehicleCategoryId=avi.vehicleCategoryId AND FIND_IN_SET(?,a.goodsTypeId) AND a.weightId=? and weight>? and isActive=?";
				franchisevehicleimages = jdbcTemplate.query(sql, new Object[] { goodsTypeId, kerbWeightId,numberOfTon,true},
						new BeanPropertyRowMapper<FranchiseVehicleDomain>(FranchiseVehicleDomain.class));
			} else {
				sql = "select DISTINCT a.category, a.vehicleCategoryId, a.goodsTypeId,a.weightId ,avi.path, avi.size,avi.imageId, avi.name from vehiclecategories a, vehicleimages avi where a.vehicleCategoryId=avi.vehicleCategoryId AND FIND_IN_SET(?,a.goodsTypeId) AND a.weightId=? and isActive=?";
				franchisevehicleimages = jdbcTemplate.query(sql, new Object[] { goodsTypeId, kerbWeightId,true },
						new BeanPropertyRowMapper<FranchiseVehicleDomain>(FranchiseVehicleDomain.class));
			}
			return franchisevehicleimages;
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Vehicles not found");
		} catch (Exception e) {
			logger.error("Exception in getNearestFranchiseVehicleImages", e);
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<FranchiseVehicleDomain> getSingleVehicleImages(String goodsTypeId, String kerbWeightId,
			double numberOfTon) throws Exception {
		try {
			String sql = null;
			List<FranchiseVehicleDomain> franchisevehicleimages = new ArrayList<FranchiseVehicleDomain>();
			if (numberOfTon != 0) {
				sql = "select DISTINCT a.category, a.vehicleCategoryId, a.goodsTypeId,a.weightId,avi.path, avi.size,avi.imageId, avi.name from vehiclecategories a,  vehicleimages avi where a.vehicleCategoryId=avi.vehicleCategoryId AND FIND_IN_SET(?,a.goodsTypeId) AND a.weightId=? AND a.weight !=0 and weight>? and a.isActive=true ORDER BY a.category asc limit 1";
				franchisevehicleimages = jdbcTemplate.query(sql,
						new Object[] { goodsTypeId, kerbWeightId, numberOfTon },
						new BeanPropertyRowMapper<FranchiseVehicleDomain>(FranchiseVehicleDomain.class));

			} else {
				sql = "select DISTINCT a.category, a.vehicleCategoryId, a.goodsTypeId,a.weightId,avi.path, avi.size,avi.imageId, avi.name from vehiclecategories a,  vehicleimages avi where a.vehicleCategoryId=avi.vehicleCategoryId AND FIND_IN_SET(?,a.goodsTypeId) AND a.weightId=? AND weight !=0 and a.isActive=true ORDER BY a.category asc limit 1";
				franchisevehicleimages = jdbcTemplate.query(sql, new Object[] { goodsTypeId, kerbWeightId },
						new BeanPropertyRowMapper<FranchiseVehicleDomain>(FranchiseVehicleDomain.class));
			}
			return franchisevehicleimages;
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Vehicles not found");
		} catch (Exception e) {
			logger.error("Exception in getSingleVehicleImages", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
