package com.atpl.mmg.dao.packagedimension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.domain.packageDimension.PackageDimensionDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.PACKAGE_DIMENSION_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class PackageDimensionDaoImpl implements PackageDimensionDao {

	private static final Logger logger = LoggerFactory.getLogger(PackageDimensionDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public String save(PackageDimensionDomain packageDimensionDomain) throws Exception {
		try {
			String sql = "INSERT INTO packagedimension(uuid,description,length, width, height,measuringUnit,imagePath,packageTypeId,createdDate,modifiedDate,isDefault) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { packageDimensionDomain.getUuid(), packageDimensionDomain.getDescription(),
							packageDimensionDomain.getLength(), packageDimensionDomain.getWidth(),
							packageDimensionDomain.getHeight(), packageDimensionDomain.getMeasuringUnit(),
							packageDimensionDomain.getImagePath(), packageDimensionDomain.getPackageTypeId(),
							DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()), true });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("package Dimension Save Failed");
		} catch (Exception e) {
			logger.error("Exception save in packageDimensionDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public List<PackageDimensionDomain> getPackageDimension(Boolean status, String packageTypeId) throws Exception {
		try {
			List<PackageDimensionDomain> data = new ArrayList<PackageDimensionDomain>();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM packagedimension where  isDefault = true ");
			if (null != status) {
				sql.append(" and status = " + status + " ");
			}
			if (!CommonUtils.isNullCheck(packageTypeId)) {
				sql.append(" and packageTypeId = '" + packageTypeId + "' ");
			}
			data = jdbcTemplate.query(sql.toString(), new Object[] { },
					new BeanPropertyRowMapper<PackageDimensionDomain>(PackageDimensionDomain.class));

			return data;
		} catch (EmptyResultDataAccessException e) {
			logger.error(
					"EmptyResultDataAccessException getPackageDimension in packageDimensionDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getPackageDimension in packageDimensionDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public PackageDimensionDomain getPackageDimensionByuuId(String uuId) throws Exception {
		try {
			String sql = "SELECT * FROM packagedimension where uuid=?";
			return (PackageDimensionDomain) jdbcTemplate.queryForObject(sql, new Object[] { uuId },
					new BeanPropertyRowMapper<PackageDimensionDomain>(PackageDimensionDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new PACKAGE_DIMENSION_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getPackageDimensionByuuId in PackageDimensionDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(PackageDimensionDomain packageDimensionDomain) throws Exception {
		try {
			String sql = "UPDATE packagedimension SET length=?, width=?, height=?, measuringUnit=?,description =?,packageTypeId=?, modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { packageDimensionDomain.getLength(), packageDimensionDomain.getWidth(),
							packageDimensionDomain.getHeight(), packageDimensionDomain.getMeasuringUnit(),
							packageDimensionDomain.getDescription(), packageDimensionDomain.getPackageTypeId(),
							DateUtility.setTimeZone(new Date()), packageDimensionDomain.getUuid() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("package Dimension Update Failed");
		} catch (Exception e) {
			logger.error("Exception update in packageDimensionDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String delete(String uuId) throws Exception {
		try {
			String sql = "UPDATE packagedimension SET isDefault=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { false, uuId });
			if (res == 1) {
				return "Deleted Successfully";
			} else
				throw new UPDATE_FAILED("package Dimension Failed");
		} catch (Exception e) {
			logger.error("Exception updatePackageDimension in PackageDimensionDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public PackageDimensionDomain getPackageDimensionByDetails(double length, double width, double height,
			String measuringUnit, String packageTypeId) throws Exception {
		try {
			String sql = "SELECT * FROM packagedimension where length=? and width=? and height=? and measuringUnit=? and packageTypeId=?";
			return (PackageDimensionDomain) jdbcTemplate.queryForObject(sql,
					new Object[] { length, width, height, measuringUnit, packageTypeId },
					new BeanPropertyRowMapper<PackageDimensionDomain>(PackageDimensionDomain.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception getPackageDimensionByDetails in PackageDimensionDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateStatus(String uuId, boolean status) throws Exception {
		try {
			String sql = "UPDATE packagedimension SET status=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { status, uuId });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Update Failed");
		} catch (Exception e) {
			logger.error("Exception updateStatus in PackageDimensionDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateImagePath(String uuId, String imagePath) throws Exception {
		try {
			String sql = "UPDATE packagedimension SET imagePath=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { imagePath, uuId });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Update Failed");
		} catch (Exception e) {
			logger.error("Exception updateImagePath in PackageDimensionDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
