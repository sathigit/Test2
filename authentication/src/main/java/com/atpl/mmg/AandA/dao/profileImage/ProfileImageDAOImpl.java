package com.atpl.mmg.AandA.dao.profileImage;

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

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profileImage.ProfileImageDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class ProfileImageDAOImpl implements ProfileImageDAO {

	private static final Logger logger = LoggerFactory.getLogger(ProfileImageDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String saveImage(ProfileImageDomain imageDomain) throws Exception {
		try {
			String sql = "INSERT INTO profileimage (uuid,roleId,name,type,size,path,category,profileId,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { imageDomain.getUuid(), imageDomain.getRoleId(), imageDomain.getName(),
							imageDomain.getType(), imageDomain.getSize(), imageDomain.getPath(),
							imageDomain.getCategory(), imageDomain.getProfileId(), imageDomain.isIsActive(),
							DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()) });
			if (res == 1)
				return "Image saved successfully";
			else
				throw new SAVE_FAILED("Image save failed");

		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveImage in ProfileImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileImageDomain> getImage(String profileId, boolean isActive) throws Exception {
		try {
			List<ProfileImageDomain> profileImageDomain = new ArrayList<ProfileImageDomain>();
			String sql = "SELECT * FROM profileimage where profileId=?  and isActive = ?";
			profileImageDomain = jdbcTemplate.query(sql, new Object[] { profileId, isActive },
					new BeanPropertyRowMapper<ProfileImageDomain>(ProfileImageDomain.class));
			return profileImageDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "EmptyResultDataAccessException getImage in ProfileImageDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getImage in ProfileImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileImageDomain> getAllImages(String profileId) throws Exception {
		try {
			List<ProfileImageDomain> profileImageDomain = new ArrayList<ProfileImageDomain>();
			String sql = null;
			if (null == profileId || profileId.isEmpty()) {
				sql = "SELECT * FROM profileimage ";
				profileImageDomain = jdbcTemplate.query(sql, new Object[] {},
						new BeanPropertyRowMapper<ProfileImageDomain>(ProfileImageDomain.class));
			} else {
				sql = "SELECT * FROM profileimage where profileId=?";

				profileImageDomain = jdbcTemplate.query(sql, new Object[] { profileId },
						new BeanPropertyRowMapper<ProfileImageDomain>(ProfileImageDomain.class));
			}
			return profileImageDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "EmptyResultDataAccessException getImage in ProfileImageDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getImage in ProfileImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileImageDomain> getAllImagesOnRole(int roleId) throws Exception {
		try {
			List<ProfileImageDomain> profileImageDomain = new ArrayList<ProfileImageDomain>();
			String sql = "SELECT * FROM profileimage where roleId=?";
			profileImageDomain = jdbcTemplate.query(sql, new Object[] { roleId },
					new BeanPropertyRowMapper<ProfileImageDomain>(ProfileImageDomain.class));
			return profileImageDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getAllImagesOnRole in ProfileImageDAOImpl "
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getAllImagesOnRole in ProfileImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateImage(ProfileImageDomain imagedomain) throws Exception {
		try {
			String sql = "UPDATE profileimage SET  profileId=?,name=?,type=?,size=?,path=?,category=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { imagedomain.getProfileId(), imagedomain.getName(), imagedomain.getType(),
							imagedomain.getSize(), imagedomain.getPath(), imagedomain.getCategory(),
							DateUtility.setTimeZone(new Date()), imagedomain.getUuid() });
			if (res == 1)
				return "Image updated successfully";
			else
				throw new UPDATE_FAILED("Image update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateImage in ProfileImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileImageDomain getImage(String profileId, int roleId, String category, boolean isActive)
			throws Exception {
		try {
			ProfileImageDomain profileImageDomain = new ProfileImageDomain();
			String sql = "SELECT * FROM profileimage where profileId=? and roleId=? and category=? and isActive=?";
			profileImageDomain = jdbcTemplate.queryForObject(sql,
					new Object[] { profileId, roleId, category, isActive },
					new BeanPropertyRowMapper<ProfileImageDomain>(ProfileImageDomain.class));
			return profileImageDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "EmptyResultDataAccessException getImage in ProfileImageDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getImage in ProfileImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateImageIsActive(String uuid, boolean isActive) throws Exception {
		try {
			String sql = "UPDATE profileimage SET isActive=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { isActive, DateUtility.setTimeZone(new Date()), uuid });
			if (res == 1)
				return "Image updated successfully";
			else
				throw new UPDATE_FAILED("Image update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateImage in ProfileImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<ProfileImageDomain> getActiveImageByCategoryAndRole(String profileId, String category, int roleId)
			throws Exception {
		try {
			List<ProfileImageDomain> profileImageDomain = new ArrayList<ProfileImageDomain>();
			String sql = null;
			if (0 != roleId) {
				sql = "SELECT * FROM profileimage where profileId =? and roleId=? and isActive= true";
				profileImageDomain = jdbcTemplate.query(sql, new Object[] { profileId, roleId },
						new BeanPropertyRowMapper<ProfileImageDomain>(ProfileImageDomain.class));
			}else {
				sql = "SELECT * FROM profileimage where profileId =? and isActive= true";
				profileImageDomain = jdbcTemplate.query(sql, new Object[] { profileId },
						new BeanPropertyRowMapper<ProfileImageDomain>(ProfileImageDomain.class));
			}
			if (!CommonUtils.isNullCheck(category)) {
				sql = "SELECT * FROM profileimage where profileId =? and category=? and isActive= true";
				profileImageDomain = jdbcTemplate.query(sql, new Object[] { profileId, category },
						new BeanPropertyRowMapper<ProfileImageDomain>(ProfileImageDomain.class));
			} 
			return profileImageDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getImageByCategory in ProfileImageDAOImpl "
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getImageByCategory in ProfileImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileImageDomain> getImageByCategoryOnIsactive(String profileId, String category, boolean isActive,
			int roleId) throws Exception {
		try {
			List<ProfileImageDomain> profileImageDomain = new ArrayList<ProfileImageDomain>();
			String sql = null;
			if (0 != roleId) {
				sql = "SELECT * FROM profileimage where profileId =? and category=? and isActive=? and roleId=?";
				profileImageDomain = jdbcTemplate.query(sql, new Object[] { profileId, category, isActive, roleId },
						new BeanPropertyRowMapper<ProfileImageDomain>(ProfileImageDomain.class));
			} else {
				sql = "SELECT * FROM profileimage where profileId =? and category=? and isActive=?";
				profileImageDomain = jdbcTemplate.query(sql, new Object[] { profileId, category, isActive },
						new BeanPropertyRowMapper<ProfileImageDomain>(ProfileImageDomain.class));

			}
			return profileImageDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getImageByCategory in ProfileImageDAOImpl "
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.IMAGE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getImageByCategory in ProfileImageDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
