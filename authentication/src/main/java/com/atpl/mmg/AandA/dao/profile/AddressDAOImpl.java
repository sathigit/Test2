package com.atpl.mmg.AandA.dao.profile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.AddressDomain;
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
public class AddressDAOImpl implements AddressDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public AddressDomain save(AddressDomain addressDomain) throws Exception {
		try {
			String sql = "INSERT INTO address (uuid,profileId,roleId,address1,address2,landmark,countryId,stateId,cityId,pincode,type,createdDate,modifiedDate,isActive) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { addressDomain.getUuid(), addressDomain.getProfileId(), addressDomain.getRoleId(),
							addressDomain.getAddress1(), addressDomain.getAddress2(), addressDomain.getLandmark(),
							addressDomain.getCountryId(), addressDomain.getStateId(), addressDomain.getCityId(),
							addressDomain.getPincode(), addressDomain.getType(), DateUtility.getDateFormat(new Date()),
							DateUtility.getDateFormat(new Date()), addressDomain.isIsActive() });
			if (res == 1) {
				return addressDomain;

			} else
				throw new SAVE_FAILED("Address save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveAddress in AddressDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(AddressDomain addressDomain) throws Exception {
		try {
			String sql = "UPDATE address SET address1=?,address2=?,landmark=?,countryId=?,stateId=?,cityId=?,pincode=?,modifiedDate=? WHERE uuid=?";
			int res = 0;

			res = jdbcTemplate.update(sql,
					new Object[] { addressDomain.getAddress1(), addressDomain.getAddress2(),
							addressDomain.getLandmark(), addressDomain.getCountryId(), addressDomain.getStateId(),
							addressDomain.getCityId(), addressDomain.getPincode(),
							DateUtility.getDateFormat(new Date()), addressDomain.getUuid() });

			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Address update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateAddress in AddressDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

//	@Override
//	public List<AddressDomain> getAddressListsByProfileId(String profileId) throws Exception {
//		try {
//			String sql = "select * from address where profileId = ?";
//			List<AddressDomain> addressDomainList = jdbcTemplate.query(sql, new Object[] { profileId },
//					new BeanPropertyRowMapper<AddressDomain>(AddressDomain.class));
//			return addressDomainList;
//		} catch (EmptyResultDataAccessException e) {
//			Loggly.sendLogglyEvent(
//					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
//							"EmptyResultDataAccessException getAddressLitsByProfileId in AddressDAOImpl"
//									+ JsonUtil.toJsonString(e.getMessage())));
//			return null;
//		} catch (Exception e) {
//			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
//					SeverityTypes.CRITICAL.ordinal(),
//					"Exception getAddressLitsByProfileId in AddressDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
//
//			throw new BACKEND_SERVER_ERROR();
//		}
//	}

	@Override
	public List<AddressDomain> getAddressListsByProfileId(String profileId, int roleId, boolean isActive)
			throws Exception {
		try {
			String sql = null;
			List<AddressDomain> addressDomainList = new ArrayList<AddressDomain>();
			if (roleId > 0) {
				if (isActive) {
					sql = "select * from address where profileId = ? and roleId=? and isActive=?";
					addressDomainList = jdbcTemplate.query(sql, new Object[] { profileId, roleId, isActive },
							new BeanPropertyRowMapper<AddressDomain>(AddressDomain.class));
				} else {
					sql = "select * from address where profileId = ? and roleId=?";
					addressDomainList = jdbcTemplate.query(sql, new Object[] { profileId, roleId },
							new BeanPropertyRowMapper<AddressDomain>(AddressDomain.class));
				}
			} else {
				sql = "select * from address where profileId = ?";
				addressDomainList = jdbcTemplate.query(sql, new Object[] { profileId },
						new BeanPropertyRowMapper<AddressDomain>(AddressDomain.class));
			}
			return addressDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getAddressLitsByProfileId in AddressDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getAddressLitsByProfileId in AddressDAOImpl" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public AddressDomain getAddressByUuid(String uuid) throws Exception {
		try {
			String sql = "select * from address where uuid = ?";
			AddressDomain addressDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] { uuid },
					new BeanPropertyRowMapper<AddressDomain>(AddressDomain.class));
			return addressDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getAddressByUuid in AddressDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getAddressByUuid in AddressDAOImpl" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<AddressDomain> getAddressByTypeAndProfileId(String type, String profileId) throws Exception {
		try {
			String sql = "select * from address where profileId = ? and type = ?";
			List<AddressDomain> addressDomain = jdbcTemplate.query(sql.toString(), new Object[] { profileId, type },
					new BeanPropertyRowMapper<AddressDomain>(AddressDomain.class));
			return addressDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getAddressByUuid in AddressDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getAddressByUuid in AddressDAOImpl" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<AddressDomain> getAddressByTypeAndProfileIdWithRoleId(String type, String profileId, int roleId)
			throws Exception {
		try {
//			String sql = "select * from address a,profilerole pr where a.profileId=pr.profileId and a.profileId = ? and a.type = ? and pr.roleId=?;";
			String sql = "select * from address where  profileId = ? and type = ? and roleId=?";
			List<AddressDomain> addressDomain = jdbcTemplate.query(sql.toString(),
					new Object[] { profileId, type, roleId },
					new BeanPropertyRowMapper<AddressDomain>(AddressDomain.class));
			return addressDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getAddressByUuid in AddressDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getAddressByUuid in AddressDAOImpl" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public AddressDomain getAddressByvalidateAddress(AddressDomain addressDomain) throws Exception {
		try {
			AddressDomain address = new AddressDomain();
			String sql = null;
			if (!CommonUtils.isNullCheck(addressDomain.getAddress2())
					&& !CommonUtils.isNullCheck(addressDomain.getLandmark())) {
				sql = "select * from address where address1=? and address2=? and roleId=? and landmark=? and type =? "
						+ "and pincode =? and  profileId = ? and countryId =? and stateId =? and cityId =? ";
				address = jdbcTemplate.queryForObject(sql.toString(),
						new Object[] { addressDomain.getAddress1(), addressDomain.getAddress2(),
								addressDomain.getRoleId(), addressDomain.getLandmark(), addressDomain.getType(),
								addressDomain.getPincode(), addressDomain.getProfileId(), addressDomain.getCountryId(),
								addressDomain.getStateId(), addressDomain.getCityId() },
						new BeanPropertyRowMapper<AddressDomain>(AddressDomain.class));
			} else {
				sql = "select * from address where address1=? and roleId=? and type =? "
						+ "and pincode =? and  profileId = ? and countryId =? and stateId =? and cityId =? ";
				address = jdbcTemplate.queryForObject(sql.toString(),
						new Object[] { addressDomain.getAddress1(), addressDomain.getRoleId(), addressDomain.getType(),
								addressDomain.getPincode(), addressDomain.getProfileId(), addressDomain.getCountryId(),
								addressDomain.getStateId(), addressDomain.getCityId() },
						new BeanPropertyRowMapper<AddressDomain>(AddressDomain.class));
			}
			return address;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getAddressByvalidateAddress in AddressDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getAddressByvalidateAddress in AddressDAOImpl" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String activateDeactivateAddress(String uuid, boolean isActive) throws Exception {
		try {
			String sql = "UPDATE address SET isActive=?,modifiedDate=? WHERE uuid=?";
			int res = 0;
			res = jdbcTemplate.update(sql, new Object[] { isActive, DateUtility.getDateFormat(new Date()), uuid });
			if (res == 1)
				return "Updated successfully";
			else
				throw new UPDATE_FAILED("Address update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception activateDeactivateAddress in AddressDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public AddressDomain getActiveAddressByTypeAndProfileIdWithRoleId(String type, String profileId, int roleId)
			throws Exception {
		try {
			String sql = "select * from address where type = ? and profileId =? and roleId =? and isActive = 1";
			AddressDomain addressDomain = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { type, profileId, roleId },
					new BeanPropertyRowMapper<AddressDomain>(AddressDomain.class));
			return addressDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"EmptyResultDataAccessException getActiveAddressByTypeAndProfileIdWithRoleId in AddressDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getActiveAddressByTypeAndProfileIdWithRoleId in AddressDAOImpl"
									+ JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateAddressRoleId(String uuid, int roleId) throws Exception {
		try {
			String sql = "UPDATE address SET roleId=?,modifiedDate=? WHERE uuid=?";
			int res = 0;

			res = jdbcTemplate.update(sql, new Object[] { roleId,DateUtility.getDateFormat(new Date()), uuid });

			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Address update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateAddressRoleId in AddressDAOImpl" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
