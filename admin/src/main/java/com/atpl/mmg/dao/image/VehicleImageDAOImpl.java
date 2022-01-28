package com.atpl.mmg.dao.image;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.image.VehicleImageDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;

@Repository
public class VehicleImageDAOImpl implements VehicleImageDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(VehicleImageDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String saveVehicleImage(VehicleImageDomain vehicleImageDomain) throws Exception {
		try {
			String sql = "INSERT INTO  vehicleimages (imageId,vehicleCategoryId,name,type,size,path) VALUES(?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { vehicleImageDomain.getImageId(), vehicleImageDomain.getVehicleCategoryId(),
							vehicleImageDomain.getName(), vehicleImageDomain.getType(), vehicleImageDomain.getSize(),
							vehicleImageDomain.getPath() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("VehicleImage save failed");
		} catch (Exception e) {
			logger.error("Exception saveVehicleImage in VehicleImageDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<VehicleImageDomain> getVehicleImagesbyCategory() throws Exception {
		try {
			String sql = "SELECT * FROM vehicleimages vm join vehiclecategories vc on vm.vehicleCategoryId = vc.vehicleCategoryId";
			return jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<VehicleImageDomain>(VehicleImageDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getVehicleImagesbyCategory in VehicleImageDAOImpl"
					+ e.getMessage());
			throw new NOT_FOUND("VehicleImages not found");
		} catch (Exception e) {
			logger.error("Exception getVehicleImagesbyCategory in VehicleImageDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public VehicleImageDomain getVehicleImagesbyCategoryId(int vehicleCategoryId) throws Exception {
		try {
			String sql = "SELECT * FROM vehicleimages where vehicleCategoryId=?";
			return (VehicleImageDomain) jdbcTemplate.queryForObject(sql, new Object[] { vehicleCategoryId },
					new BeanPropertyRowMapper<VehicleImageDomain>(VehicleImageDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getVehicleImagesbyCategoryId in VehicleImageDAOImpl"
					+ e.getMessage());
			throw new NOT_FOUND("VehicleImages not found");
		} catch (Exception e) {
			logger.error("Exception getVehicleImagesbyCategoryId in VehicleImageDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<VehicleImageDomain> getVehicleImagesbyType() throws Exception {
		try {
			String sql = "SELECT * FROM vehicletypeimages";
			return jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<VehicleImageDomain>(VehicleImageDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error(
					"EmptyResultDataAccessException getVehicleImagesbyType in VehicleImageDAOImpl" + e.getMessage());
			throw new NOT_FOUND("VehicleImages not found");
		} catch (Exception e) {
			logger.error("Exception getVehicleImagesbyType in VehicleImageDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<VehicleImageDomain> getVehicleImagesbyTypeId(int vehicleTypeId) throws Exception {
		try {
			String sql = "SELECT * FROM vehicletypeimages where vehicleTypeId=?";
			return jdbcTemplate.query(sql, new Object[] { vehicleTypeId },
					new BeanPropertyRowMapper<VehicleImageDomain>(VehicleImageDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error(
					"EmptyResultDataAccessException getVehicleImagesbyTypeId in VehicleImageDAOImpl" + e.getMessage());
			throw new NOT_FOUND("VehicleImages not found");
		} catch (Exception e) {
			logger.error("Exception getVehicleImagesbyTypeId in VehicleImageDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<VehicleImageDomain> getVehicleImagesFranchise(String goodsTypeId, String kerbWeightId, String origin)
			throws Exception {
		try {
			String sql = "select a.category, a.vehicleCategoryId, a.goodsTypeId,a.kerbWeightId, avi.path, avi.size, avi.imageId, avi.name,ac.id as id from  vehicles a,  vehicleimages avi, cities ac where  a.goodsTypeId=? AND a.kerbWeightId>=? AND a.vehicleCategoryId=avi.vehicleCategoryId and ac.name = ?";
			return jdbcTemplate.query(sql, new Object[] { goodsTypeId, kerbWeightId, origin },
					new BeanPropertyRowMapper<VehicleImageDomain>(VehicleImageDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error(
					"EmptyResultDataAccessException getVehicleImagesFranchise in VehicleImageDAOImpl" + e.getMessage());
			throw new NOT_FOUND("FranchiseVehicleImages not found");
		} catch (Exception e) {
			logger.error("Exception getVehicleImagesFranchise in VehicleImageDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

}
