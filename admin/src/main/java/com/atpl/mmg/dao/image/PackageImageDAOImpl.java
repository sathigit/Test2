package com.atpl.mmg.dao.image;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.domain.image.PackageImageDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class PackageImageDAOImpl implements PackageImageDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String save(PackageImageDomain packageImageDomain) throws Exception {
		try {
			String sql = "INSERT INTO  packageimage (imageId,name,type,size,path,category,packageId,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { packageImageDomain.getImageId(), packageImageDomain.getName(),
							packageImageDomain.getType(), packageImageDomain.getSize(), packageImageDomain.getPath(),
							packageImageDomain.getCategory(), packageImageDomain.getPackageId(),
							DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("Package image save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"save in PackageImageDAOImpl ") + JsonUtil.toJsonString(packageImageDomain));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<PackageImageDomain> getImage(String packageId, boolean status) throws Exception {
		try {
			List<PackageImageDomain> profileImageDomain = new ArrayList<PackageImageDomain>();
			String sql = null;

				sql = "SELECT * FROM packageimage where packageId =? and isActive=?";
				profileImageDomain = jdbcTemplate.query(sql, new Object[] { packageId, status },
						new BeanPropertyRowMapper<PackageImageDomain>(PackageImageDomain.class));

			
			return profileImageDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getImage in PackageImageDAOImpl "
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getImage in PackageImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateImageIsActive(String uuid, boolean isActive) throws Exception {
		try {
			String sql = "UPDATE packageimage SET isActive=?,modifiedDate=? WHERE imageId=?";
			int res = jdbcTemplate.update(sql, new Object[] { isActive, DateUtility.setTimeZone(new Date()), uuid });
			if (res == 1)
				return "Image updated successfully";
			else
				throw new UPDATE_FAILED("Image update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateImageIsActive in PackageImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}
	
}
