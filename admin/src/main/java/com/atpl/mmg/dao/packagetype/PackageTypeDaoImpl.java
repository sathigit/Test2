package com.atpl.mmg.dao.packagetype;

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

import com.atpl.mmg.domain.packagetype.PackageTypeDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.PACKAGE_TYPE_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class PackageTypeDaoImpl implements PackageTypeDao {

	private static final Logger logger = LoggerFactory.getLogger(PackageTypeDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public String save(PackageTypeDomain packageTypeDomain) {
		try {
			String sql = "INSERT INTO packagetype(uuid,name,isDefault,createdDate, modifiedDate) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { packageTypeDomain.getUuid(), packageTypeDomain.getName(),
					true, DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("packageType Save Failed");
		} catch (Exception e) {
			logger.error("Exception save in PackageTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public List<PackageTypeDomain> getPackageType(Boolean status) throws Exception {
		try {
			List<PackageTypeDomain> packageType = new ArrayList<PackageTypeDomain>();
			String sql = null;
			if (null != status) {
				sql = "SELECT * FROM packagetype where status = ?  and  isDefault= true";
				packageType = jdbcTemplate.query(sql, new Object[] { status },
						new BeanPropertyRowMapper<PackageTypeDomain>(PackageTypeDomain.class));
			} else {
				sql = "SELECT * FROM packagetype where isDefault= true";
				packageType = jdbcTemplate.query(sql, new Object[] {},
						new BeanPropertyRowMapper<PackageTypeDomain>(PackageTypeDomain.class));
			}
			return packageType;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getPackageType in PackageTypeDaoImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getPackageType in PackageTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String update(PackageTypeDomain packageTypeDomain) throws Exception {
		try {

			String sql = "UPDATE packagetype SET name=?, modifiedDate=?  WHERE uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { packageTypeDomain.getName(), new Date(), packageTypeDomain.getUuid() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("packageType Update Failed");
		} catch (Exception e) {
			logger.error("Exception update in PackageTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public PackageTypeDomain getPackageTypeById(String uuId) throws Exception {
		try {
			String sql = "SELECT * FROM packagetype where uuid=?";
			return (PackageTypeDomain) jdbcTemplate.queryForObject(sql, new Object[] { uuId },
					new BeanPropertyRowMapper<PackageTypeDomain>(PackageTypeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new PACKAGE_TYPE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getPackageTypeById in PackageTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String delete(String uuId) throws Exception {

		try {
			String sql = "UPDATE packagetype SET isDefault=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { false, uuId });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("packageType Delete Failed");
		} catch (Exception e) {
			logger.error("Exception delete in PackageTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public PackageTypeDomain getPackageTypeByName(String name) throws Exception {
		try {
			String sql = "SELECT * FROM packagetype where name=?";
			return (PackageTypeDomain) jdbcTemplate.queryForObject(sql, new Object[] { name },
					new BeanPropertyRowMapper<PackageTypeDomain>(PackageTypeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception getPackageTypeByName in PackageTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateStatus(String uuid, boolean status) throws Exception {
		try {
			String sql = "UPDATE packagetype SET status=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { status, uuid });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("update  Failed");
		} catch (Exception e) {
			logger.error("Exception updateStatus in PackageTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
