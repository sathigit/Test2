package com.atpl.mmg.AandA.dao.profile;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.domain.profile.BDMDomain;
import com.atpl.mmg.AandA.domain.profile.BDODomain;
import com.atpl.mmg.AandA.domain.profile.ChannelPartnerDomain;
import com.atpl.mmg.AandA.domain.profile.CoordinatorDomain;
import com.atpl.mmg.AandA.domain.profile.CustomerDomain;
import com.atpl.mmg.AandA.domain.profile.EnterpriseDomain;
import com.atpl.mmg.AandA.domain.profile.FieldOfficerDomain;
import com.atpl.mmg.AandA.domain.profile.FleetOperatorDomain;
import com.atpl.mmg.AandA.domain.profile.FranchiseDomainV2;
import com.atpl.mmg.AandA.domain.profile.OperationalTeamDomain;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.domain.profile.WareHouseDomain;
import com.atpl.mmg.AandA.domain.profilerole.ProfileRoleDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.PASSWORD_NOT_UPDATED;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.model.profile.CoordinatorDetail;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class ProfileDAOImplV2 implements ProfileDAOV2 {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public ProfileDomainV2 getProfileDetByPanOrAadharNo(String panNumber, BigInteger aadharNumber) throws Exception {
		try {
			String sql = "SELECT * from profile where panNumber=? or aadharNumber=?";
			ProfileDomainV2 profileDomainV2 = jdbcTemplate.queryForObject(sql, new Object[] { panNumber, aadharNumber },
					new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			return profileDomainV2;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileDetByPanOrAadharNo in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileDetByPanOrAadharNo in ProfileDAOImplV2"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomainV2 getProfileByMobileNumberOrEmailId(String mobileNumber, String emailId, int roleId)
			throws Exception {
		try {
			if (roleId != 0) {
				String sql = "select p.*,pr.isActive as isActive from profile p,profilerole pr where p.id=pr.profileId AND (p.mobileNumber=? or p.emailId=?) and pr.roleId =?";
				return (ProfileDomainV2) jdbcTemplate.queryForObject(sql,
						new Object[] { mobileNumber, emailId, roleId },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			} else {
				String sql = "select * from profile where mobileNumber=? or emailId=?";
				return (ProfileDomainV2) jdbcTemplate.queryForObject(sql, new Object[] { mobileNumber, emailId },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			}
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileByMobileNumberOrEmailId in ProfileDAOImplV2"
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileByMobileNumberOrEmailId in ProfileDAOImplV2"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomainV2> getProfileByMobileNumberOrEmailId(String mobileNumber, String emailId)
			throws Exception {
		try {
			String sql = "select * from profile p,profilerole pr where p.id=pr.profileId AND (p.mobileNumber=? or p.emailId=?)";
			List<ProfileDomainV2> profileDomainV2 = jdbcTemplate.query(sql, new Object[] { mobileNumber, emailId },
					new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			return profileDomainV2;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileByMobileNumberOrEmailId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileByMobileNumberOrEmailId in ProfileDAOImplV2"
							+ JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateCommonProfile(ProfileDomainV2 profileDomainV2) throws Exception {
		try {
			String sql = "UPDATE profile SET aadharNumber=?, panNumber=?, modifiedDate=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomainV2.getAadharNumber(),
					profileDomainV2.getPanNumber(), DateUtility.getDateFormat(new Date()), profileDomainV2.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Common Profile update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateCommonProfile in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomainV2 save(ProfileDomainV2 profileDomainV2) throws Exception {
		try {
			String sql = "INSERT INTO profile (id,firstName,lastName,mobileNumber,alternativeNumber,emailId,defaultroleId,genderId,dob,password,confirmPassword,"
					+ "aadharNumber,panNumber,profileSource,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { profileDomainV2.getId(), profileDomainV2.getFirstName(),
							profileDomainV2.getLastName(), profileDomainV2.getMobileNumber(),
							profileDomainV2.getAlternativeNumber(), profileDomainV2.getEmailId(),
							profileDomainV2.getDefaultRoleId(), profileDomainV2.getGenderId(), profileDomainV2.getDob(),
							profileDomainV2.getPassword(), profileDomainV2.getConfirmPassword(),
							profileDomainV2.getAadharNumber(), profileDomainV2.getPanNumber(),
							profileDomainV2.getProfileSource(), DateUtility.getDateFormat(new Date()),
							DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return profileDomainV2;

			} else
				throw new SAVE_FAILED("Profile save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception saveProfile in ProfileDAOImplV2 " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomainV2 getProfileDetails(String id) throws Exception {
		try {
			String sql = "SELECT * from profile where id=?";
			ProfileDomainV2 profileDomainV2 = jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			return profileDomainV2;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getprofileDetails in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getprofileDetails in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateProfile(ProfileDomainV2 profileDomainV2, int roleId) throws Exception {
		try {
			String sql;
			int res = 0;

			if (roleId == Integer.parseInt(Role.DRIVER.getCode())
					|| roleId == Integer.parseInt(Role.CUSTOMER.getCode())) {
				sql = "UPDATE profile SET firstName=?,lastName=?,dob=?,genderId = ?,alternativeNumber=?,aadharNumber = ?,panNumber=?,modifiedDate=? WHERE id=?";
				res = jdbcTemplate.update(sql,
						new Object[] { profileDomainV2.getFirstName(), profileDomainV2.getLastName(),
								profileDomainV2.getDob(), profileDomainV2.getGenderId(),
								profileDomainV2.getAlternativeNumber(), profileDomainV2.getAadharNumber(),
								profileDomainV2.getPanNumber(), DateUtility.getDateFormat(new Date()),
								profileDomainV2.getId() });
			} else {
				sql = "UPDATE profile SET firstName=?,lastName=?,alternativeNumber=?,aadharNumber = ?,panNumber=?,modifiedDate=? WHERE id=?";
				res = jdbcTemplate.update(sql,
						new Object[] { profileDomainV2.getFirstName(), profileDomainV2.getLastName(),
								profileDomainV2.getAlternativeNumber(), profileDomainV2.getAadharNumber(),
								profileDomainV2.getPanNumber(), DateUtility.getDateFormat(new Date()),
								profileDomainV2.getId() });
			}

			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Profile update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception updateProfile in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomainV2 getProfileByIdAndRole(String id, int roleId) throws Exception {
		try {
			String sql = "select p.*,r.appTokenId as appTokenId,r.webTokenId as webTokenId,r.isActive as isActive from profile p, profilerole r where p.id = r.profileId and  r.profileId=? and r.roleId =? ";
			ProfileDomainV2 profiledomain = jdbcTemplate.queryForObject(sql, new Object[] { id, roleId },
					new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			return profiledomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getprofileDetails in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getprofileDetails in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomainV2 getProfileDetByMobileOrEmail(String mobileOrEmail, int roleId) throws Exception {
		try {
			String sql = null;
			ProfileDomainV2 profileDomainV2 = new ProfileDomainV2();
			if (roleId != 0) {
				sql = "SELECT p.* from profile p,profilerole pr where p.id=pr.profileId and (p.mobileNumber=? or p.emailId= ?) and pr.roleId=?";
				profileDomainV2 = jdbcTemplate.queryForObject(sql,
						new Object[] { mobileOrEmail, mobileOrEmail, roleId },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			} else {
				sql = "SELECT * from profile where  (mobileNumber=? or emailId= ?)";
				profileDomainV2 = jdbcTemplate.queryForObject(sql, new Object[] { mobileOrEmail, mobileOrEmail },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			}
			return profileDomainV2;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException mobileNumberExistsOrNot in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception mobileNumberExistsOrNot in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateToken(ProfileRoleDomain profileRoleDomain, String profileSource) throws Exception {
		try {
			if (profileSource.equalsIgnoreCase(Constants.PROFILE_SOURCE)) {
				String sql = "UPDATE profilerole SET appTokenId=?, modifiedDate = ? WHERE uuid =?";
				int res = jdbcTemplate.update(sql, new Object[] { profileRoleDomain.getAppTokenId(),
						DateUtility.getDateFormat(new Date()), profileRoleDomain.getUuid() });
				if (res == 1) {
					return "Updated successfully";
				} else
					throw new UPDATE_FAILED("Profile token update failed");
			} else {
				String sql = "UPDATE profilerole SET webTokenId=?, modifiedDate = ? WHERE uuid =?";
				int res = jdbcTemplate.update(sql, new Object[] { profileRoleDomain.getWebTokenId(),
						DateUtility.getDateFormat(new Date()), profileRoleDomain.getUuid() });
				if (res == 1) {
					return "Updated successfully";
				} else
					throw new UPDATE_FAILED("Profile token update failed");
			}
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "EmptyResultDataAccessException updateToken in ProfileDAOImplV2"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String profileActivateOrDeactivate(boolean status, String profileId, Integer roleId) throws Exception {
		try {
			String sql = "UPDATE profilerole SET isActive=? WHERE profileId=? and roleId=?";
			int res = jdbcTemplate.update(sql, new Object[] { status, profileId, roleId });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Profile update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException profileActivateOrDeactivate in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	private List<ProfileDomainV2> customerList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, int customerTypeId, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append("select p.*,r.isActive,r.roleId, c.* from profile p,profilerole r, customer c, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and c.profileId = p.id and a.cityId=? and a.roleId =? and a.type = ? and r.isActive=? and r.roleId =? ");
			if (customerTypeId > 0) {
				sql.append(" and c.customerTypeId = '" + customerTypeId + "'");
			}
//			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or c.customerId = '" + searchText + "') ");
			if (searchText != null)
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or c.customerId like '" + searchText + "%') ");
			sql.append(" order by c.createdDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append("select p.*,r.isActive,r.roleId, c.* from profile p,profilerole r, customer c, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and c.profileId = p.id and a.stateId=? and a.roleId =? and a.type = ? and r.isActive=? and r.roleId =? ");
			if (customerTypeId > 0) {
				sql.append(" and c.customerTypeId = '" + customerTypeId + "'");
			}
//			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + "' or c.customerId = '"
//						+ searchText + "') ");
			if (searchText != null)
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or c.customerId like '" + searchText + "%') ");
			sql.append(" order by c.createdDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {
						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							CustomerDomain customer = (new BeanPropertyRowMapper<>(CustomerDomain.class)).mapRow(rs,
									rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setCustomer(customer);
							return profileDomain;
						}

					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, c.* from profile p,profilerole r, customer c\r\n"
							+ "where p.id = r.profileId and c.profileId = p.id and r.roleId =? AND r.isActive=? ");
			if (customerTypeId > 0) {
				sql.append(" and c.customerTypeId = '" + customerTypeId + "'");
			}
//			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or c.customerId = '" + searchText + "') ");
			if (searchText != null)
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or c.customerId like '" + searchText + "%') ");

			sql.append(" order by c.createdDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							CustomerDomain customer = (new BeanPropertyRowMapper<>(CustomerDomain.class)).mapRow(rs,
									rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setCustomer(customer);
							return profileDomain;
						}

					});
		}
	}

	private List<ProfileDomainV2> franchiseList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append("select p.*,r.isActive,r.roleId, f.* from profile p,profilerole r, franchise f, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.cityId=? and a.roleId =? and a.type = ? and r.isActive=? and r.roleId =? ");
//			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or f.companyName = '" + searchText + "' or f.franchiseId = '" + searchText + "') ");
			if (searchText != null)
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or f.companyName like '" + searchText + "%' or f.franchiseId like '" + searchText
						+ "%') ");

			sql.append(" order by f.modifiedDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append("select p.*,r.isActive,r.roleId, f.* from profile p,profilerole r, franchise f, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.stateId=? and a.roleId =? and a.type = ? and r.isActive=? and r.roleId =? ");
//			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or f.companyName = '" + searchText + "' or f.franchiseId = '" + searchText + "') ");
			if (searchText != null)
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or f.companyName like '" + searchText + "%' or f.franchiseId like '" + searchText
						+ "%') ");

			sql.append(" order by f.modifiedDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {
						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							FranchiseDomainV2 franchise = (new BeanPropertyRowMapper<>(FranchiseDomainV2.class))
									.mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setFranchise(franchise);
							return profileDomain;
						}

					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, f.* from profile p,profilerole r, franchise f\r\n"
							+ "where p.id = r.profileId and f.profileId = p.id and r.roleId =? AND r.isActive=? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or f.companyName = '" + searchText + "' or f.franchiseId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or f.companyName like '" + searchText + "%' or f.franchiseId like '" + searchText
						+ "%') ");

			sql.append(" order by f.modifiedDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							FranchiseDomainV2 franchise = (new BeanPropertyRowMapper<>(FranchiseDomainV2.class))
									.mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setFranchise(franchise);
							return profileDomain;
						}

					});
		}
	}

	private List<ProfileDomainV2> fleetOperatorList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append(
					"select p.*,r.isActive,r.roleId, f.* from profile p,profilerole r, fleetoperator f, address a \r\n"
							+ "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.cityId=? and a.roleId =? and a.type = ? and r.isActive=? and r.roleId =?  ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or f.companyName = '" + searchText + "' or f.fleetId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText + "%') ");

			sql.append(" order by f.modifiedDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append(
					"select p.*,r.isActive,r.roleId, f.* from profile p,profilerole r, fleetoperator f, address a \r\n"
							+ "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.stateId=?  and a.roleId =? and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or f.companyName = '" + searchText + "' or f.fleetId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText + "%') ");

			sql.append(" order by f.modifiedDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);

			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {
						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							FleetOperatorDomain fleet = (new BeanPropertyRowMapper<>(FleetOperatorDomain.class))
									.mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setFleet(fleet);
							return profileDomain;
						}
					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, f.* from profile p,profilerole r, fleetoperator f\r\n"
							+ "where p.id = r.profileId and f.profileId = p.id and r.roleId =? AND r.isActive=? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or f.companyName = '" + searchText + "' or f.fleetId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText + "%') ");

			sql.append(" order by f.modifiedDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							FleetOperatorDomain fleet = (new BeanPropertyRowMapper<>(FleetOperatorDomain.class))
									.mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setFleet(fleet);
							return profileDomain;
						}

					});
		}
	}

	private List<ProfileDomainV2> wareHouseList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append("select p.*,r.isActive,r.roleId, w.* from profile p,profilerole r, warehouse w, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and w.profileId = p.id and a.cityId=? and a.roleId =? and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or w.companyName = '" + searchText + "' or w.wareHouseId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or w.companyName like '" + searchText + "%' or w.wareHouseId like '" + searchText + "%') ");

			sql.append(" order by w.modifiedDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append("select p.*,r.isActive,r.roleId, w.* from profile p,profilerole r, warehouse w, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and w.profileId = p.id and a.stateId=? and a.roleId =? and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or w.companyName = '" + searchText + "' or w.wareHouseId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or w.companyName like '" + searchText + "%' or w.wareHouseId like '" + searchText + "%') ");

			sql.append(" order by w.modifiedDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							WareHouseDomain warehouse = (new BeanPropertyRowMapper<>(WareHouseDomain.class)).mapRow(rs,
									rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setWarehouse(warehouse);
							return profileDomain;
						}

					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, w.* from profile p,profilerole r, warehouse w\r\n"
							+ "where p.id = r.profileId and w.profileId = p.id and r.roleId =? AND r.isActive=? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or w.companyName = '" + searchText + "' or w.wareHouseId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or w.companyName like '" + searchText + "%' or w.wareHouseId like '" + searchText + "%') ");

			sql.append(" order by w.modifiedDate desc ");
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							WareHouseDomain warehouse = (new BeanPropertyRowMapper<>(WareHouseDomain.class)).mapRow(rs,
									rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setWarehouse(warehouse);
							return profileDomain;
						}

					});
		}
	}

	private List<ProfileDomainV2> enterpriseList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append("select p.*,r.isActive,r.roleId, e.* from profile p,profilerole r, enterprise e, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and e.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =?  ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or e.companyName = '" + searchText + "' or e.enterpriseId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or e.companyName like '" + searchText + "%' or e.enterpriseId like '" + searchText + "%') ");
			sql.append(" order by e.modifiedDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append("select p.*,r.isActive,r.roleId, e.* from profile p,profilerole r, enterprise e, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and e.profileId = p.id and a.stateId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or e.companyName = '" + searchText + "' or e.enterpriseId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or e.companyName like '" + searchText + "%' or e.enterpriseId like '" + searchText + "%') ");
			sql.append(" order by e.modifiedDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							EnterpriseDomain enterprise = (new BeanPropertyRowMapper<>(EnterpriseDomain.class))
									.mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setEnterprise(enterprise);
							return profileDomain;
						}

					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, e.* from profile p,profilerole r, enterprise e\r\n"
							+ "where p.id = r.profileId and e.profileId = p.id and r.roleId =? AND r.isActive=? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or e.companyName = '" + searchText + "' or e.enterpriseId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or e.companyName like '" + searchText + "%' or e.enterpriseId like '" + searchText + "%') ");

			sql.append(" order by e.modifiedDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							EnterpriseDomain enterprise = (new BeanPropertyRowMapper<>(EnterpriseDomain.class))
									.mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setEnterprise(enterprise);
							return profileDomain;
						}

					});
		}

	}

	private List<ProfileDomainV2> operationalTeamList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append(
					"select p.*,r.isActive,r.roleId, o.* from profile p,profilerole r, operational_team o, address a \r\n"
							+ "where p.id = r.profileId and p.id = a.profileId and o.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or o.operationalTeamId = '" + searchText + "') ");
			sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
					+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
					+ "%' or o.operationalTeamId like '" + searchText + "%') ");
			sql.append(" order by o.createdDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append(
					"select p.*,r.isActive,r.roleId, o.* from profile p,profilerole r, operational_team o, address a \r\n"
							+ "where p.id = r.profileId and p.id = a.profileId and o.profileId = p.id and a.stateId=?   and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or o.operationalTeamId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or o.operationalTeamId like '" + searchText + "%') ");
			sql.append(" order by o.createdDate desc ");
		}
		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {
						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							OperationalTeamDomain operationalTeamDomain = (new BeanPropertyRowMapper<>(
									OperationalTeamDomain.class)).mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setOperationalTeam(operationalTeamDomain);
							return profileDomain;
						}

					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, o.* from profile p,profilerole r, operational_team o\r\n"
							+ "where p.id = r.profileId and o.profileId = p.id and r.roleId =? AND r.isActive=? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or o.operationalTeamId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or o.operationalTeamId like '" + searchText + "%') ");
			sql.append(" order by o.createdDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							OperationalTeamDomain operationalTeamDomain = (new BeanPropertyRowMapper<>(
									OperationalTeamDomain.class)).mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setOperationalTeam(operationalTeamDomain);
							return profileDomain;
						}

					});
		}
	}

	private List<ProfileDomainV2> bdmList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append("select p.*,r.isActive,r.roleId, b.* from profile p,profilerole r, bdm b, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and b.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or b.bdmId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or b.bdmId like '" + searchText + "%') ");
			sql.append(" order by b.createdDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append("select p.*,r.isActive,r.roleId, b.* from profile p,profilerole r, bdm b, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and b.profileId = p.id and a.stateId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or b.bdmId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or b.bdmId like '" + searchText + "%') ");
			sql.append(" order by b.createdDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {
						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							BDMDomain bdmDomain = (new BeanPropertyRowMapper<>(BDMDomain.class)).mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setBdm(bdmDomain);
							return profileDomain;
						}

					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, b.* from profile p,profilerole r, bdm b \r\n"
							+ "where p.id = r.profileId and b.profileId = p.id and r.roleId =? AND r.isActive=? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or b.bdmId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or b.bdmId like '" + searchText + "%') ");
			sql.append(" order by b.createdDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							BDMDomain bdmDomain = (new BeanPropertyRowMapper<>(BDMDomain.class)).mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setBdm(bdmDomain);
							return profileDomain;
						}

					});
		}

	}

	private List<ProfileDomainV2> bdoList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append("select p.*,r.isActive,r.roleId, b.* from profile p,profilerole r, bdo b, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and b.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or b.bdoId = '" + searchText + "') ");
			sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
					+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
					+ "%' or b.bdoId like '" + searchText + "%') ");

			sql.append(" order by b.createdDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append("select p.*,r.isActive,r.roleId, b.* from profile p,profilerole r, bdo b, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and b.profileId = p.id and a.stateId=?  and a.roleId =? and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or b.bdoId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or b.bdoId like '" + searchText + "%') ");
			sql.append(" order by b.createdDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {
						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							BDODomain bdoDomain = (new BeanPropertyRowMapper<>(BDODomain.class)).mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setBdo(bdoDomain);
							return profileDomain;
						}
					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, b.* from profile p,profilerole r, bdo b \r\n"
							+ "where p.id = r.profileId and b.profileId = p.id and r.roleId =? AND r.isActive=? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or b.bdoId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or b.bdoId like '" + searchText + "%') ");
			sql.append(" order by b.createdDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							BDODomain bdoDomain = (new BeanPropertyRowMapper<>(BDODomain.class)).mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setBdo(bdoDomain);
							return profileDomain;
						}

					});
		}
	}

	private List<ProfileDomainV2> channelPartnerList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append(
					"select p.*,r.isActive,r.roleId, c.* from profile p,profilerole r, channelpartner c, address a \r\n"
							+ "where p.id = r.profileId and p.id = a.profileId and c.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or c.channelPartnerId = '" + searchText + "') ");
			sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
					+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
					+ "%' or c.channelPartnerId like '" + searchText + "%') ");

			sql.append(" order by c.createdDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append(
					"select p.*,r.isActive,r.roleId, c.* from profile p,profilerole r, channelpartner c, address a \r\n"
							+ "where p.id = r.profileId and p.id = a.profileId and c.profileId = p.id and a.stateId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or c.channelPartnerId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or c.channelPartnerId like '" + searchText + "%') ");
			sql.append(" order by c.createdDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							ChannelPartnerDomain channelPartnerDomain = (new BeanPropertyRowMapper<>(
									ChannelPartnerDomain.class)).mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setChannelPartner(channelPartnerDomain);
							return profileDomain;
						}

					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, c.* from profile p,profilerole r, channelpartner c \r\n"
							+ "where p.id = r.profileId and c.profileId = p.id and r.roleId =? AND r.isActive=? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or c.channelPartnerId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or c.channelPartnerId like '" + searchText + "%') ");
			sql.append(" order by c.createdDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							ChannelPartnerDomain channelPartnerDomain = (new BeanPropertyRowMapper<>(
									ChannelPartnerDomain.class)).mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setChannelPartner(channelPartnerDomain);
							return profileDomain;
						}

					});
		}
	}

	private List<ProfileDomainV2> coordinatorList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append("select p.*,r.isActive,r.roleId, c.* from profile p,profilerole r, coordinator c, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and c.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or c.coordinatorId = '" + searchText + "') ");
			sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
					+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
					+ "%' or c.coordinatorId like '" + searchText + "%') ");
			sql.append(" order by c.createdDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append("select p.*,r.isActive,r.roleId, c.* from profile p,profilerole r, coordinator c, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and c.profileId = p.id and a.stateId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or c.coordinatorId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or c.coordinatorId like '" + searchText + "%') ");
			sql.append(" order by c.createdDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							CoordinatorDomain coordinatorDomain = (new BeanPropertyRowMapper<>(CoordinatorDomain.class))
									.mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setCoordinator(coordinatorDomain);
							return profileDomain;
						}
					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, c.* from profile p,profilerole r, coordinator c \r\n"
							+ "where p.id = r.profileId and c.profileId = p.id and r.roleId =? AND r.isActive=? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + "' or c.coordinatorId = '"
//						+ searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or c.coordinatorId like '" + searchText + "%') ");
			sql.append(" order by c.createdDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							CoordinatorDomain coordinatorDomain = (new BeanPropertyRowMapper<>(CoordinatorDomain.class))
									.mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setCoordinator(coordinatorDomain);
							return profileDomain;
						}

					});
		}
	}

	private List<ProfileDomainV2> fieldOfficerList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append(
					"select p.*,r.isActive,r.roleId, f.* from profile p,profilerole r, fieldofficer f, address a \r\n"
							+ "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or f.fieldOfficerId = '" + searchText + "') ");
			sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
					+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
					+ "%' or f.fieldOfficerId like '" + searchText + "%') ");

			sql.append(" order by f.createdDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append(
					"select p.*,r.isActive,r.roleId, f.* from profile p,profilerole r, fieldofficer f, address a \r\n"
							+ "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.stateId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or f.fieldOfficerId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or f.fieldOfficerId like '" + searchText + "%') ");
			sql.append(" order by f.createdDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new RowMapper<ProfileDomainV2>() {
						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							FieldOfficerDomain fieldOfficerDomain = (new BeanPropertyRowMapper<>(
									FieldOfficerDomain.class)).mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setFieldOfficer(fieldOfficerDomain);
							return profileDomain;
						}

					});
		} else {
			sql.append(
					"select p.*,r.appTokenId,r.webTokenId,r.isActive,r.roleId, f.* from profile p,profilerole r, fieldofficer f \r\n"
							+ "where p.id = r.profileId and f.profileId = p.id and r.roleId =? AND r.isActive=?  ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//						+ "' or f.fieldOfficerId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or f.fieldOfficerId like '" + searchText + "%') ");
			sql.append(" order by f.createdDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new RowMapper<ProfileDomainV2>() {

						@Override
						public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
							FieldOfficerDomain fieldOfficerDomain = (new BeanPropertyRowMapper<>(
									FieldOfficerDomain.class)).mapRow(rs, rowNum);
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							profileDomain.setFieldOfficer(fieldOfficerDomain);
							return profileDomain;
						}

					});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.atpl.mmg.AandA.dao.profile.ProfileDAOV2#
	 * getProfileDetailsByCompanyProfileId(int, java.lang.String)
	 * customer,driver,owner and tieups role
	 */
	private List<ProfileDomainV2> otherList(StringBuffer sql, boolean status, Integer roleId, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, String searchText) {
		int cityOrStateId = 0;
		if (cityId > 0) {
			cityOrStateId = cityId;
			sql.append("select p.*,r.isActive,r.roleId from profile p,profilerole r, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and a.cityId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText + "%') ");

			sql.append(" order by r.createdDate desc ");
		} else if (stateId > 0) {
			cityOrStateId = stateId;
			sql.append("select p.*,r.roleId,r.isActive from profile p,profilerole r, address a \r\n"
					+ "where p.id = r.profileId and p.id = a.profileId and a.stateId=?  and a.roleId =?  and a.type = ? and r.isActive=? and r.roleId =? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText + "%') ");

			sql.append(" order by r.createdDate desc ");
		}

		if (cityOrStateId > 0) {
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(),
					new Object[] { cityOrStateId, roleId, addressType, status, roleId },
					new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
		} else {
			sql.append(
					"select p.*,r.roleId,r.appTokenId,r.webTokenId,r.isActive from profile p,profilerole r where p.id = r.profileId and r.roleId =? AND r.isActive=? ");
			if (searchText != null)
//				sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//						+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText + "') ");
				sql.append("AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText + "%') ");
			sql.append(" order by r.createdDate desc ");
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			return jdbcTemplate.query(sql.toString(), new Object[] { roleId, status },
					new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
		}
	}

	@Override
	public List<ProfileDomainV2> userActiveOrInActiveList(Integer roleId, boolean status, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType, int customerTypeId, String searchText)
			throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<ProfileDomainV2> profileDomainList = null;
			Role role = Role.getRole(roleId + "");
			switch (role) {
			case CUSTOMER:
				profileDomainList = customerList(sql, status, roleId, lowerBound, upperBound, cityId, stateId,
						addressType, customerTypeId, searchText);
				break;
			case OWNER:
			case TIEUPS:
			case DRIVER:
				profileDomainList = otherList(sql, status, roleId, lowerBound, upperBound, cityId, stateId, addressType,
						searchText);
				break;
			case BDM:
				profileDomainList = bdmList(sql, status, roleId, lowerBound, upperBound, cityId, stateId, addressType,
						searchText);
				break;
			case BDO:
				profileDomainList = bdoList(sql, status, roleId, lowerBound, upperBound, cityId, stateId, addressType,
						searchText);
				break;
			case CHANNEL_PARTNER:
				profileDomainList = channelPartnerList(sql, status, roleId, lowerBound, upperBound, cityId, stateId,
						addressType, searchText);
				break;
			case COORDINATOR:
				profileDomainList = coordinatorList(sql, status, roleId, lowerBound, upperBound, cityId, stateId,
						addressType, searchText);
				break;
			case FIELDOFFICER:
				profileDomainList = fieldOfficerList(sql, status, roleId, lowerBound, upperBound, cityId, stateId,
						addressType, searchText);
				break;
			case OPERATIONAL_TEAM:
				profileDomainList = operationalTeamList(sql, status, roleId, lowerBound, upperBound, cityId, stateId,
						addressType, searchText);
				break;
			case FRANCHISE:
				profileDomainList = franchiseList(sql, status, roleId, lowerBound, upperBound, cityId, stateId,
						addressType, searchText);
				break;
			case FLEET_OPERATOR:
				profileDomainList = fleetOperatorList(sql, status, roleId, lowerBound, upperBound, cityId, stateId,
						addressType, searchText);
				break;
			case ENTERPRISE:
				profileDomainList = enterpriseList(sql, status, roleId, lowerBound, upperBound, cityId, stateId,
						addressType, searchText);
				break;
			case WAREHOUSE:
				profileDomainList = wareHouseList(sql, status, roleId, lowerBound, upperBound, cityId, stateId,
						addressType, searchText);
				break;
			default:
				break;
			}
			return profileDomainList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException userActiveOrInActiveList in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception userActiveOrInActiveList in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	/*
	 * @Override public List<ProfileDomainV2> getProfilesByCityAndSts(Integer
	 * cityId, boolean status, Integer roleId, String type, int lowerBound, int
	 * upperBound) throws Exception { try { StringBuffer sql = new StringBuffer();
	 * List<ProfileDomainV2> profileDomainList = null; if (roleId ==
	 * Integer.parseInt(Role.FRANCHISE.getCode())) { sql.
	 * append("select p.*,r.isActive, f.* from profile p,profilerole r, franchise f, address a \r\n"
	 * +
	 * "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.cityId=? and a.type = ? and r.isActive=? and r.roleId =?  \r\n"
	 * + " order by f.companyName asc "); if (upperBound > 0) sql.append("limit " +
	 * lowerBound + "," + upperBound);
	 * 
	 * profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { cityId,
	 * type, status, roleId }, new RowMapper<ProfileDomainV2>() {
	 * 
	 * @Override public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { FranchiseDomainV2 franchise = (new
	 * BeanPropertyRowMapper<>(FranchiseDomainV2.class)) .mapRow(rs, rowNum);
	 * ProfileDomainV2 profileDomain = (new
	 * BeanPropertyRowMapper<>(ProfileDomainV2.class)) .mapRow(rs, rowNum);
	 * profileDomain.setFranchise(franchise); return profileDomain; }
	 * 
	 * }); } else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
	 * sql.append(
	 * "select p.*,r.appTokenId,r.webTokenId,r.isActive, f.* from profile p,profilerole r, fleetoperator f, address a \r\n"
	 * +
	 * "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.cityId=? and a.type =? and r.isActive=? and r.roleId =? \r\n"
	 * + " order by f.companyName asc "); if (upperBound > 0) sql.append("limit " +
	 * lowerBound + "," + upperBound); profileDomainList =
	 * jdbcTemplate.query(sql.toString(), new Object[] { cityId, type, status,
	 * roleId }, new RowMapper<ProfileDomainV2>() {
	 * 
	 * @Override public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { FleetOperatorDomain fleet = (new
	 * BeanPropertyRowMapper<>(FleetOperatorDomain.class)) .mapRow(rs, rowNum);
	 * ProfileDomainV2 profileDomain = (new
	 * BeanPropertyRowMapper<>(ProfileDomainV2.class)) .mapRow(rs, rowNum);
	 * profileDomain.setFleet(fleet); return profileDomain; }
	 * 
	 * }); } else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
	 * sql.append(
	 * "select p.*,r.appTokenId,r.webTokenId,r.isActive, w.* from profile p,profilerole r, warehouse w, address a \r\n"
	 * +
	 * "where p.id = r.profileId and p.id = a.profileId and w.profileId = p.id and a.cityId=? and a.type = ? and r.isActive=? and r.roleId =? \r\n"
	 * + " order by w.companyName asc "); if (upperBound > 0) sql.append("limit " +
	 * lowerBound + "," + upperBound); profileDomainList =
	 * jdbcTemplate.query(sql.toString(), new Object[] { cityId, type, status,
	 * roleId }, new RowMapper<ProfileDomainV2>() {
	 * 
	 * @Override public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { WareHouseDomain warehouse = (new
	 * BeanPropertyRowMapper<>(WareHouseDomain.class)) .mapRow(rs, rowNum);
	 * ProfileDomainV2 profileDomain = (new
	 * BeanPropertyRowMapper<>(ProfileDomainV2.class)) .mapRow(rs, rowNum);
	 * profileDomain.setWarehouse(warehouse); return profileDomain; }
	 * 
	 * }); } else
	 * 
	 * if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) { sql.append(
	 * "select p.*,r.appTokenId,r.webTokenId,r.isActive, e.* from profile p,profilerole r, enterprise e, address a \r\n"
	 * +
	 * "where p.id = r.profileId and p.id and a.profileId and e.profileId = p.id and a.cityId=? and a.type =? and r.isActive=? and r.roleId =? \r\n"
	 * + " order by e.companyName asc "); if (upperBound > 0) sql.append("limit " +
	 * lowerBound + "," + upperBound); profileDomainList =
	 * jdbcTemplate.query(sql.toString(), new Object[] { cityId, type, status,
	 * roleId }, new RowMapper<ProfileDomainV2>() {
	 * 
	 * @Override public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { EnterpriseDomain enterprise = (new
	 * BeanPropertyRowMapper<>(EnterpriseDomain.class)) .mapRow(rs, rowNum);
	 * ProfileDomainV2 profileDomain = (new
	 * BeanPropertyRowMapper<>(ProfileDomainV2.class)) .mapRow(rs, rowNum);
	 * profileDomain.setEnterprise(enterprise); return profileDomain; }
	 * 
	 * }); } else { sql.append(
	 * "select p.* from profile p,profilerole r, address a where p.id = r.profileId and p.id = a.profileId and a.cityId=? and a.type =? and r.isActive=? and r.roleId =? order by r.createdDate desc "
	 * ); if (upperBound > 0) sql.append("limit " + lowerBound + "," + upperBound);
	 * profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { cityId,
	 * type, status, roleId }, new
	 * BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class)); }
	 * 
	 * return profileDomainList; } catch (EmptyResultDataAccessException e) {
	 * Loggly.sendLogglyEvent(
	 * EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
	 * SeverityTypes.CRITICAL.ordinal(),
	 * "EmptyResultDataAccessException usersListByCityId in ProfileDAOImplV2" +
	 * JsonUtil.toJsonString(e.getMessage()))); return null; } catch (Exception e) {
	 * Loggly.sendLogglyEvent(
	 * EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
	 * SeverityTypes.CRITICAL.ordinal(),
	 * "Exception usersListByCityId in ProfileDAOImplV2" +
	 * JsonUtil.toJsonString(e.getMessage())));
	 * 
	 * throw new BACKEND_SERVER_ERROR(); } }
	 */

	/*
	 * @Override public List<ProfileDomainV2> getProfilesByStateAnsSts(Integer
	 * stateId, boolean status, Integer roleId, String type, int lowerBound, int
	 * upperBound) throws Exception { try { StringBuffer sql = new StringBuffer();
	 * List<ProfileDomainV2> profileDomainList = null;
	 * 
	 * if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())) { sql.
	 * append("select p.*,r.isActive, f.* from profile p,profilerole r, franchise f, address a \r\n"
	 * +
	 * "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.stateId=? and a.type =? and r.isActive=? and r.roleId =?  \r\n"
	 * + " order by f.companyName asc "); if (upperBound > 0) sql.append("limit " +
	 * lowerBound + "," + upperBound); profileDomainList =
	 * jdbcTemplate.query(sql.toString(), new Object[] { stateId, type, status,
	 * roleId }, new RowMapper<ProfileDomainV2>() {
	 * 
	 * @Override public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { FranchiseDomainV2 franchise = (new
	 * BeanPropertyRowMapper<>(FranchiseDomainV2.class)) .mapRow(rs, rowNum);
	 * ProfileDomainV2 profileDomain = (new
	 * BeanPropertyRowMapper<>(ProfileDomainV2.class)) .mapRow(rs, rowNum);
	 * profileDomain.setFranchise(franchise); return profileDomain; }
	 * 
	 * }); } else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
	 * sql.append(
	 * "select p.*,r.appTokenId,r.webTokenId,r.isActive, f.* from profile p,profilerole r, fleetoperator f, address a \r\n"
	 * +
	 * "where p.id = r.profileId and p.id= a.profileId and f.profileId = p.id and a.stateId=? and a.type =? and r.isActive=? and r.roleId =? \r\n"
	 * + " order by f.companyName asc "); if (upperBound > 0) sql.append("limit " +
	 * lowerBound + "," + upperBound); profileDomainList =
	 * jdbcTemplate.query(sql.toString(), new Object[] { stateId, type, status,
	 * roleId }, new RowMapper<ProfileDomainV2>() {
	 * 
	 * @Override public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { FleetOperatorDomain fleet = (new
	 * BeanPropertyRowMapper<>(FleetOperatorDomain.class)) .mapRow(rs, rowNum);
	 * ProfileDomainV2 profileDomain = (new
	 * BeanPropertyRowMapper<>(ProfileDomainV2.class)) .mapRow(rs, rowNum);
	 * profileDomain.setFleet(fleet); return profileDomain; }
	 * 
	 * }); } else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
	 * sql.append(
	 * "select p.*,r.appTokenId,r.webTokenId,r.isActive, w.* from profile p,profilerole r, warehouse w, address a \r\n"
	 * +
	 * "where p.id = r.profileId and p.id = a.profileId and w.profileId = p.id and a.stateId=? and a.type = ? and r.isActive=? and r.roleId =? \r\n"
	 * + " order by w.companyName asc "); if (upperBound > 0) sql.append("limit " +
	 * lowerBound + "," + upperBound); profileDomainList =
	 * jdbcTemplate.query(sql.toString(), new Object[] { stateId, status, roleId },
	 * new RowMapper<ProfileDomainV2>() {
	 * 
	 * @Override public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { WareHouseDomain warehouse = (new
	 * BeanPropertyRowMapper<>(WareHouseDomain.class)) .mapRow(rs, rowNum);
	 * ProfileDomainV2 profileDomain = (new
	 * BeanPropertyRowMapper<>(ProfileDomainV2.class)) .mapRow(rs, rowNum);
	 * profileDomain.setWarehouse(warehouse); return profileDomain; }
	 * 
	 * }); } else
	 * 
	 * if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) { sql.append(
	 * "select p.*,r.appTokenId,r.webTokenId,r.isActive, e.* from profile p,profilerole r, enterprise e, address a \r\n"
	 * +
	 * "where p.id = r.profileId and p.id = a.profileId and e.profileId = p.id and a.stateId=? and a.type=? and r.isActive=? and r.roleId =? \r\n"
	 * + " order by e.companyName asc "); if (upperBound > 0) sql.append("limit " +
	 * lowerBound + "," + upperBound); profileDomainList =
	 * jdbcTemplate.query(sql.toString(), new Object[] { stateId, type, status,
	 * roleId }, new RowMapper<ProfileDomainV2>() {
	 * 
	 * @Override public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws
	 * SQLException { EnterpriseDomain enterprise = (new
	 * BeanPropertyRowMapper<>(EnterpriseDomain.class)) .mapRow(rs, rowNum);
	 * ProfileDomainV2 profileDomain = (new
	 * BeanPropertyRowMapper<>(ProfileDomainV2.class)) .mapRow(rs, rowNum);
	 * profileDomain.setEnterprise(enterprise); return profileDomain; }
	 * 
	 * }); } else { sql.append(
	 * "select p.* from profile p,profilerole r, address a where p.id = r.profileId and p.id = a.profileId and a.stateId=? and a.type = ? and r.isActive=? and r.roleId =? order by r.createdDate desc"
	 * ); if (upperBound > 0) sql.append("limit " + lowerBound + "," + upperBound);
	 * profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] {
	 * stateId, type, status, roleId }, new
	 * BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class)); }
	 * 
	 * return profileDomainList; } catch (EmptyResultDataAccessException e) {
	 * Loggly.sendLogglyEvent(
	 * EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
	 * SeverityTypes.CRITICAL.ordinal(),
	 * "EmptyResultDataAccessException usersListByStateId in ProfileDAOImplV2" +
	 * JsonUtil.toJsonString(e.getMessage()))); return null; } catch (Exception e) {
	 * Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.
	 * PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
	 * "Exception usersListByStateId in ProfileDAOImplV2" +
	 * JsonUtil.toJsonString(e.getMessage())));
	 * 
	 * throw new BACKEND_SERVER_ERROR(); } }
	 */

	@Override
	public List<ProfileDomainV2> getProfilesByCity(Integer cityId, Integer roleId, String type, Boolean isTag,
			String searchText, int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<ProfileDomainV2> profileDomainList = null;
			if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.isActive, f.* from profile p,profilerole r, franchise f, address a \r\n"
								+ " where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type = ? and r.roleId =?  ");
				if (null != isTag) {
					sql.append(" and f.isTag = " + isTag + " ");
				}
				if (searchText != null)
					sql.append(" AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or f.companyName = '" + searchText + "' or f.franchiseId = '" + searchText + "') ");
				sql.append(" order by f.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { cityId, roleId, type, roleId },
						new RowMapper<ProfileDomainV2>() {
							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								FranchiseDomainV2 franchise = (new BeanPropertyRowMapper<>(FranchiseDomainV2.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setFranchise(franchise);
								return profileDomain;
							}
						});
			} else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.appTokenId,r.webTokenId,r.isActive, f.* from profile p,profilerole r, fleetoperator f, address a \r\n"
								+ "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type =? and r.roleId =?  ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or f.companyName = '" + searchText + "' or f.fleetId = '" + searchText + "') ");
				sql.append(" order by f.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { cityId, roleId, type, roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								FleetOperatorDomain fleet = (new BeanPropertyRowMapper<>(FleetOperatorDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setFleet(fleet);
								return profileDomain;
							}

						});
			} else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.appTokenId,r.webTokenId,r.isActive, w.* from profile p,profilerole r, warehouse w, address a \r\n"
								+ "where p.id = r.profileId and p.id = a.profileId and w.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type = ? and r.roleId =? ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or w.companyName = '" + searchText + "' or w.wareHouseId = '" + searchText + "') ");
				sql.append(" order by w.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { cityId, roleId, type, roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								WareHouseDomain warehouse = (new BeanPropertyRowMapper<>(WareHouseDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setWarehouse(warehouse);
								return profileDomain;
							}

						});
			} else

			if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.appTokenId,r.webTokenId,r.isActive, e.* from profile p,profilerole r, enterprise e, address a \r\n"
								+ "where p.id = r.profileId and p.id and a.profileId and e.profileId = p.id and a.cityId=?  and a.roleId =?  and a.type =? and r.roleId =? ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or e.companyName = '" + searchText + "' or e.enterpriseId = '" + searchText + "') ");
				sql.append(" order by e.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { cityId, roleId, type, roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								EnterpriseDomain enterprise = (new BeanPropertyRowMapper<>(EnterpriseDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setEnterprise(enterprise);
								return profileDomain;
							}

						});
			} else {
				sql.append(
						"select p.* from profile p,profilerole r, address a where p.id = r.profileId and p.id = a.profileId and a.cityId=?  and a.roleId =?  and a.type =? and r.roleId =? ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText + "') ");
				sql.append(" order by r.createdDate desc ");
				if (upperBound > 0)
					sql.append(" limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { cityId, roleId, type, roleId },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			}
			return profileDomainList;
		} catch (

		EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException usersListByCityId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception usersListByCityId in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomainV2> getProfilesByState(Integer stateId, Integer roleId, String type, Boolean isTag,
			String searchText, int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<ProfileDomainV2> profileDomainList = null;

			if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.isActive, f.* from profile p,profilerole r, franchise f, address a \r\n"
								+ "where p.id = r.profileId and p.id = a.profileId and f.profileId = p.id and a.stateId=?  and a.roleId =?  and a.type =? and r.roleId =? ");
				if (null != isTag) {
					sql.append(" f.isTag = '" + isTag + "' ");
				}
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or f.companyName = '" + searchText + "' or f.franchiseId = '" + searchText + "') ");
				sql.append(" order by f.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { stateId, roleId, type, roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								FranchiseDomainV2 franchise = (new BeanPropertyRowMapper<>(FranchiseDomainV2.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setFranchise(franchise);
								return profileDomain;
							}

						});
			} else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.appTokenId,r.webTokenId,r.isActive, f.* from profile p,profilerole r, fleetoperator f, address a \r\n"
								+ "where p.id = r.profileId and p.id= a.profileId and f.profileId = p.id and a.stateId=?  and a.roleId =? and a.type =? and r.roleId =? ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or f.companyName = '" + searchText + "' or f.fleetId = '" + searchText + "') ");
				sql.append(" order by f.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { stateId, roleId, type, roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								FleetOperatorDomain fleet = (new BeanPropertyRowMapper<>(FleetOperatorDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setFleet(fleet);
								return profileDomain;
							}

						});
			} else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.appTokenId,r.webTokenId,r.isActive, w.* from profile p,profilerole r, warehouse w, address a \r\n"
								+ "where p.id = r.profileId and p.id = a.profileId and w.profileId = p.id and a.stateId=?  and a.roleId =? and a.type = ? and r.roleId =? ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or w.companyName = '" + searchText + "' or w.wareHouseId = '" + searchText + "') ");
				sql.append(" order by w.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { stateId, roleId, type, roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								WareHouseDomain warehouse = (new BeanPropertyRowMapper<>(WareHouseDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setWarehouse(warehouse);
								return profileDomain;
							}

						});
			} else

			if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.appTokenId,r.webTokenId,r.isActive, e.* from profile p,profilerole r, enterprise e, address a \r\n"
								+ "where p.id = r.profileId and p.id = a.profileId and e.profileId = p.id and a.stateId=?  and a.roleId =?  and a.type=? and r.roleId =? ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or e.companyName = '" + searchText + "' or e.enterpriseId = '" + searchText + "') ");
				sql.append(" order by e.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { stateId, roleId, type, roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								EnterpriseDomain enterprise = (new BeanPropertyRowMapper<>(EnterpriseDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setEnterprise(enterprise);
								return profileDomain;
							}

						});
			} else {
				sql.append(
						"select p.* from profile p,profilerole r, address a where p.id = r.profileId and p.id = a.profileId and a.stateId=?  and a.roleId =?  and a.type = ? and r.roleId =? ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText + "') ");
				sql.append(" order by r.createdDate desc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { stateId, roleId, type, roleId },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			}

			return profileDomainList;
		} catch (

		EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException usersListByStateId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception usersListByStateId in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomainV2> usersSessionActiveOrInActiveListByCityId(boolean isActive, Integer cityId,
			boolean status, Integer roleId, String addressType) throws Exception {
		try {
			String sql = null;
			List<ProfileDomainV2> profileDomain = new ArrayList<ProfileDomainV2>();
			if (cityId != 0) {
				sql = "select p.*,s.* from profile p,profilerole r,session s, address a where p.id = r.profileId and p.id = a.profileId and "
						+ "r.profileId = s.userId and a.cityId=?  and a.roleId =?  and a.type=? and r.isActive=? and r.roleId =? and s.isActive = ?";
				profileDomain = jdbcTemplate.query(sql,
						new Object[] { cityId, roleId, addressType, status, roleId, isActive },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			} else {
				sql = "select p.*,s.* from profile p,profilerole r,session s where p.id = r.profileId and r.profileId = s.userId and r.isActive=? and r.roleId =? and s.isActive = ?";
				profileDomain = jdbcTemplate.query(sql, new Object[] { status, roleId, isActive },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			}
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException usersListByCityId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception usersListByCityId in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomainV2> getProfileDetailsByRoleId(Integer roleId, Boolean isTag, String searchText,
			int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<ProfileDomainV2> profileDomainList = null;

			if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())) {
				sql.append(
						"select p.*,r.appTokenId,r.webTokenId,r.isActive, f.* from profile p,profilerole r, franchise f\r\n"
								+ "where p.id = r.profileId and f.profileId = p.id and r.roleId =? ");
				if (null != isTag) {
					sql.append(" f.isTag = '" + isTag + "' ");
				}
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or f.companyName = '" + searchText + "' or f.franchiseId = '" + searchText + "') ");
				sql.append(" order by f.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								FranchiseDomainV2 franchise = (new BeanPropertyRowMapper<>(FranchiseDomainV2.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setFranchise(franchise);
								return profileDomain;
							}

						});
			} else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
				sql.append(
						"select p.*,r.appTokenId,r.webTokenId,r.isActive, f.* from profile p,profilerole r, fleetoperator f\r\n"
								+ "where p.id = r.profileId and f.profileId = p.id and r.roleId =? ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or f.companyName = '" + searchText + "' or f.fleetId = '" + searchText + "') ");
				sql.append(" order by f.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								FleetOperatorDomain fleet = (new BeanPropertyRowMapper<>(FleetOperatorDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setFleet(fleet);
								return profileDomain;
							}

						});
			} else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
				sql.append(
						"select p.*,r.appTokenId,r.webTokenId,r.isActive, w.* from profile p,profilerole r, warehouse w\r\n"
								+ "where p.id = r.profileId and w.profileId = p.id and r.roleId =? ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or w.companyName = '" + searchText + "' or w.wareHouseId = '" + searchText + "') ");
				sql.append(" order by w.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								WareHouseDomain warehouse = (new BeanPropertyRowMapper<>(WareHouseDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setWarehouse(warehouse);
								return profileDomain;
							}

						});
			} else

			if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) {
				sql.append(
						"select p.*,r.appTokenId,r.webTokenId,r.isActive, e.* from profile p,profilerole r, enterprise e\r\n"
								+ "where p.id = r.profileId and e.profileId = p.id and r.roleId =? \r\n"
								+ " order by e.companyName asc ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
							+ "' or e.companyName = '" + searchText + "' or e.enterpriseId = '" + searchText + "') ");
				sql.append(" order by e.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { roleId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								EnterpriseDomain enterprise = (new BeanPropertyRowMapper<>(EnterpriseDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setEnterprise(enterprise);
								return profileDomain;
							}

						});
			} else {
				sql.append(
						"select p.*,r.appTokenId,r.webTokenId, r.isActive from profile p, profilerole r where p.id = r.profileId and r.roleId = ? ");
				if (searchText != null)
					sql.append("AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText + "') ");
				sql.append(" order by r.createdDate desc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { roleId },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			}

			return profileDomainList;
		} catch (

		EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileDetailsByRoleId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getProfileDetailsByRoleId in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updatePasswordById(ProfileDomainV2 profileDomain) throws Exception {
		try {
			String sql = null;
			int res;
			if (null != profileDomain.getId()) {
				sql = "UPDATE profile SET password=?, confirmPassword=? WHERE id=?";
				res = jdbcTemplate.update(sql, new Object[] { profileDomain.getPassword(),
						profileDomain.getConfirmPassword(), profileDomain.getId() });
			} else {
				sql = "UPDATE profile SET password=?, confirmPassword=? WHERE mobileNumber=?";
				res = jdbcTemplate.update(sql, new Object[] { profileDomain.getPassword(),
						profileDomain.getConfirmPassword(), profileDomain.getMobileNumber() });
			}
			if (res == 1) {
				return "Password changed successfully";
			} else
				throw new PASSWORD_NOT_UPDATED();
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updatePasswordById in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomainV2> getProfileByBdmId(int roleId, String bdmId, String searchText, int lowerBound,
			int upperBound) throws Exception {
		try {
			List<ProfileDomainV2> profileDomain = new ArrayList<ProfileDomainV2>();
			StringBuffer sql = new StringBuffer();
			if (roleId == Integer.parseInt(Role.CHANNEL_PARTNER.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.isActive from profile p,profilerole r,channelpartner cp where p.id=r.profileId AND p.id = cp.profileId and "
								+ "r.roleId=? and cp.bdmId = ? ");
				if (searchText != null)
					sql.append(" (p.firstName = " + searchText + " or p.lastName = " + searchText
							+ " or p.mobileNumber = " + searchText + " or p.emailId = " + searchText + ")");
				sql.append(" order by r.createdDate desc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				return jdbcTemplate.query(sql.toString(), new Object[] { roleId, bdmId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								ChannelPartnerDomain channelPartner = (new BeanPropertyRowMapper<>(
										ChannelPartnerDomain.class)).mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setChannelPartner(channelPartner);
								return profileDomain;
							}

						});
			} else if (roleId == Integer.parseInt(Role.BDO.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.isActive,b.* from profile p,profilerole r,bdo b where p.id=r.profileId AND p.id = b.profileId and "
								+ "r.roleId=? and b.bdmId = ? ");
				if (searchText != null)
					sql.append(" (p.firstName = " + searchText + " or p.lastName = " + searchText
							+ " or p.mobileNumber = " + searchText + " or p.emailId = " + searchText + ")");
				sql.append(" order by r.createdDate desc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				return jdbcTemplate.query(sql.toString(), new Object[] { roleId, bdmId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								BDODomain bdoDomain = (new BeanPropertyRowMapper<>(BDODomain.class)).mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setBdo(bdoDomain);
								return profileDomain;
							}

						});
			}
			return profileDomain;
		} catch (

		EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileByBdmId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getProfileByBdmId in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomainV2> getProfileByRoleIdAndStsAndBdmId(boolean status, int roleId, String bdmId,
			String searchText, int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<ProfileDomainV2> profileDomain = new ArrayList<ProfileDomainV2>();
			if (roleId == Integer.parseInt(Role.CHANNEL_PARTNER.getCode())) {
				sql.append(
						"select p.*,r.roleId,cp.* from profile p,profilerole r,channelpartner cp where p.id=r.profileId AND p.id = cp.profileId "
								+ "and r.isActive=? AND r.roleId=? and cp.bdmId = ? ");
				if (searchText != null)
					sql.append(" (p.firstName = " + searchText + " or p.lastName = " + searchText
							+ " or p.mobileNumber = " + searchText + " or p.emailId = " + searchText + ")");
				sql.append(" order by r.createdDate desc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				return jdbcTemplate.query(sql.toString(), new Object[] { status, roleId, bdmId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								ChannelPartnerDomain channelPartner = (new BeanPropertyRowMapper<>(
										ChannelPartnerDomain.class)).mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setChannelPartner(channelPartner);
								return profileDomain;
							}

						});
			} else if (roleId == Integer.parseInt(Role.BDO.getCode())) {
				sql.append(
						"select p.*,r.roleId,b.* from profile p,profilerole r,bdo b where p.id=r.profileId AND p.id = b.profileId "
								+ "and r.isActive=? AND r.roleId=? and b.bdmId = ? ");
				if (searchText != null)
					sql.append(" (p.firstName = " + searchText + " or p.lastName = " + searchText
							+ " or p.mobileNumber = " + searchText + " or p.emailId = " + searchText + ")");
				sql.append(" order by r.createdDate desc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				return jdbcTemplate.query(sql.toString(), new Object[] { status, roleId, bdmId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								BDODomain bdmDomain = (new BeanPropertyRowMapper<>(BDODomain.class)).mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setBdo(bdmDomain);
								return profileDomain;
							}

						});
			}
			return profileDomain;
		} catch (

		EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileByRoleIdAndStsAndBdmId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileByRoleIdAndStsAndBdmId in ProfileDAOImplV2"
							+ JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomainV2> getProfileByCpId(int roleId, String cpId, String status, int lowerBound,
			int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<ProfileDomainV2> profileDomainList = null;
			if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())) {
				if (CommonUtils.isNullCheck(status)) {
					sql.append(
							"select p.*,r.roleId,r.isActive,f.* from profile p,profilerole r,franchise f where p.id=r.profileId AND p.id = f.profileId and "
									+ "r.roleId=? and f.channelPartnerId = ? order by r.createdDate desc ");
					if (upperBound > 0)
						sql.append("desc limit " + lowerBound + "," + upperBound);
					profileDomainList = jdbcTemplate.query(sql.toString(), new Object[] { roleId, cpId },
//							new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
							new RowMapper<ProfileDomainV2>() {
								@Override
								public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
									FranchiseDomainV2 franchise = (new BeanPropertyRowMapper<>(FranchiseDomainV2.class))
											.mapRow(rs, rowNum);
									ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
											.mapRow(rs, rowNum);
									profileDomain.setFranchise(franchise);
									return profileDomain;
								}

							});
				} else {
					sql.append(
							"select p.*,r.roleId,r.isActive,f.* from profile p,profilerole r,franchise f where p.id=r.profileId AND p.id = f.profileId and "
									+ "r.roleId=? and f.channelPartnerId = ? and r.isActive =? order by createdDate desc ");
					if (upperBound > 0)
						sql.append("desc limit " + lowerBound + "," + upperBound);
					profileDomainList = jdbcTemplate.query(sql.toString(),
							new Object[] { roleId, cpId, Boolean.parseBoolean(status) },
//							new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
							new RowMapper<ProfileDomainV2>() {

								@Override
								public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
									FranchiseDomainV2 franchise = (new BeanPropertyRowMapper<>(FranchiseDomainV2.class))
											.mapRow(rs, rowNum);
									ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
											.mapRow(rs, rowNum);
									profileDomain.setFranchise(franchise);
									return profileDomain;
								}

							});
				}
			}
			return profileDomainList;
		} catch (

		EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileByCpId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception getProfileByCpId in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomainV2> getProfileByRoleAndFranchiseId(int roleId, boolean status, String franchiseId,
			String searchText, int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<ProfileDomainV2> profileDomain = null;
			if (roleId == Integer.parseInt(Role.FIELDOFFICER.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.isActive,f.* from profile p,profilerole r, fieldofficer f where p.id=r.profileId AND p.id = f.profileId and "
								+ "r.roleId=? AND r.isActive =? AND f.franchiseId=? ");
				if (searchText != null)
//					sql.append(" AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//							+ "' or f.fieldOfficerId = '" + searchText + "' ) ");
					sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.fieldOfficerId like '" + searchText + "%' ) ");

				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId, status, franchiseId },
						new RowMapper<ProfileDomainV2>() {
							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								FieldOfficerDomain fieldOfficer = (new BeanPropertyRowMapper<>(
										FieldOfficerDomain.class)).mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setFieldOfficer(fieldOfficer);
								return profileDomain;
							}

						});

			} else if (roleId == Integer.parseInt(Role.COORDINATOR.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.isActive,c.* from profile p,profilerole r, coordinator c where p.id=r.profileId AND p.id = c.profileId and "
								+ "r.roleId=? AND r.isActive =? AND c.franchiseId=? ");
				if (searchText != null)
//					sql.append(" AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//							+ "' or c.coordinatorId = '" + searchText + "') ");
				sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
						+ "%' or c.coordinatorId like '" + searchText + "%') ");

				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId, status, franchiseId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								CoordinatorDomain coordinator = (new BeanPropertyRowMapper<>(CoordinatorDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setCoordinator(coordinator);
								return profileDomain;
							}
						});

			} else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
				sql.append("select p.*,r.roleId,r.isActive,f.* from profile p,profilerole r, fleetoperator f \r\n"
						+ "where p.id = r.profileId and f.profileId = p.id and r.roleId =? and r.isActive=? and f.franchiseId = ? \r\n"
						+ " order by f.companyName asc ");
				if (searchText != null)
//					sql.append(" AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//							+ "' or f.companyName = '" + searchText + "' or f.fleetId = '" + searchText + "' ) ");

					sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or f.companyName like '" + searchText + "%' or f.fleetId like '" + searchText + "%' ) ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId, status, franchiseId },
						new RowMapper<ProfileDomainV2>() {
							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								FleetOperatorDomain fleet = (new BeanPropertyRowMapper<>(FleetOperatorDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setFleet(fleet);
								return profileDomain;
							}
						});

			} else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
				sql.append("select p.*,r.roleId,r.isActive,w.* from profile p,profilerole r, warehouse w\r\n"
						+ "where p.id = r.profileId and w.profileId = p.id and r.roleId =? and r.isActive=? and w.franchiseId = ? ");
				if (searchText != null)
//					sql.append(" AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//							+ "' or w.companyName = '" + searchText + "' or w.wareHouseId = '" + searchText + "') ");
					sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or w.companyName like '" + searchText + "%' or w.wareHouseId like '" + searchText + "%') ");

				sql.append(" order by w.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId, status, franchiseId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								WareHouseDomain warehouse = (new BeanPropertyRowMapper<>(WareHouseDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setWarehouse(warehouse);
								return profileDomain;
							}
						});

			} else if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) {
				sql.append("select p.*,r.roleId,r.isActive from profile p,profilerole r, enterprise e\r\n"
						+ "where p.id = r.profileId and e.profileId = p.id and r.roleId =? and r.isActive=? and e.franchiseId =? ");
				if (searchText != null)
//					sql.append(" AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText
//							+ "' or e.companyName = '" + searchText + "' or e.enterpriseId = '" + searchText + "' ) ");
					sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
							+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText
							+ "%' or e.companyName like '" + searchText + "%' or e.enterpriseId like '" + searchText + "%' ) ");

				sql.append(" order by e.companyName asc ");
				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId, status, franchiseId },
						new RowMapper<ProfileDomainV2>() {
							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								EnterpriseDomain enterprise = (new BeanPropertyRowMapper<>(EnterpriseDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setEnterprise(enterprise);
								return profileDomain;
							}
						});
			} else if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
				sql.append(
						"select p.*,r.isActive from profile p,profilerole r where p.id = r.profileId and r.isActive=? and r.roleId =? ");
				if (searchText != null)
//					sql.append(" AND (p.firstName = '" + searchText + "' or p.lastName = '" + searchText
//							+ "' or p.mobileNumber = '" + searchText + "' or p.emailId = '" + searchText + "' ) ");
				sql.append(" AND (p.firstName like '" + searchText + "%' or p.lastName like '" + searchText
						+ "%' or p.mobileNumber like '" + searchText + "%' or p.emailId like '" + searchText + "%' ) ");

				if (upperBound > 0)
					sql.append("limit " + lowerBound + "," + upperBound);
			}

			if (roleId == Integer.parseInt(Role.DRIVER.getCode()))
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { status, roleId },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
//			else
//				profileDomain = jdbcTemplate.query(sql, new Object[] { roleId, status, franchiseId },
//						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			return profileDomain;
		} catch (

		EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileByRoleAndFranchiseId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileByRoleAndFranchiseId in ProfileDAOImplV2"
							+ JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomainV2> getProfileByRoleAndFranchiseId(int roleId, String franchiseId, int lowerBound,
			int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			List<ProfileDomainV2> profileDomain = null;
			if (roleId == Integer.parseInt(Role.FIELDOFFICER.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.isActive,f.* from profile p,profilerole r, fieldofficer f where p.id=r.profileId AND p.id = f.profileId and "
								+ "r.roleId=? AND f.franchiseId=? ");
				if (upperBound > 0)
					sql.append("desc limit " + lowerBound + "," + upperBound);
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId, franchiseId },
						new RowMapper<ProfileDomainV2>() {
							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								FieldOfficerDomain fieldOfficer = (new BeanPropertyRowMapper<>(
										FieldOfficerDomain.class)).mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setFieldOfficer(fieldOfficer);
								return profileDomain;
							}

						});

			} else if (roleId == Integer.parseInt(Role.COORDINATOR.getCode())) {
				sql.append(
						"select p.*,r.roleId,r.isActive,c.* from profile p,profilerole r, coordinator c where p.id=r.profileId AND p.id = c.profileId and "
								+ "r.roleId=? AND c.franchiseId=? ");
				if (upperBound > 0)
					sql.append("desc limit " + lowerBound + "," + upperBound);
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId, franchiseId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								CoordinatorDomain coordinator = (new BeanPropertyRowMapper<>(CoordinatorDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setCoordinator(coordinator);
								return profileDomain;
							}
						});

			} else if (roleId == Integer.parseInt(Role.FLEET_OPERATOR.getCode())) {
				sql.append("select p.*,r.roleId,r.isActive,f.* from profile p,profilerole r, fleetoperator f \r\n"
						+ "where p.id = r.profileId and f.profileId = p.id and r.roleId =? and f.franchiseId = ? \r\n"
						+ " order by f.companyName asc ");
				if (upperBound > 0)
					sql.append("desc limit " + lowerBound + "," + upperBound);
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId, franchiseId },
						new RowMapper<ProfileDomainV2>() {
							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								FleetOperatorDomain fleet = (new BeanPropertyRowMapper<>(FleetOperatorDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setFleet(fleet);
								return profileDomain;
							}
						});

			} else if (roleId == Integer.parseInt(Role.WAREHOUSE.getCode())) {
				sql.append("select p.*,r.roleId,r.isActive,w.* from profile p,profilerole r, warehouse w\r\n"
						+ "where p.id = r.profileId and w.profileId = p.id and r.roleId =? and w.franchiseId = ? \r\n"
						+ " order by w.companyName asc ");
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId, franchiseId },
						new RowMapper<ProfileDomainV2>() {

							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								WareHouseDomain warehouse = (new BeanPropertyRowMapper<>(WareHouseDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setWarehouse(warehouse);
								return profileDomain;
							}
						});

			} else if (roleId == Integer.parseInt(Role.ENTERPRISE.getCode())) {
				sql.append("select p.*,r.roleId,r.isActive from profile p,profilerole r, enterprise e\r\n"
						+ "where p.id = r.profileId and e.profileId = p.id and r.roleId =? and e.franchiseId =? \r\n"
						+ " order by e.companyName asc ");
				if (upperBound > 0)
					sql.append("desc limit " + lowerBound + "," + upperBound);
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId, franchiseId },
						new RowMapper<ProfileDomainV2>() {
							@Override
							public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws SQLException {
								EnterpriseDomain enterprise = (new BeanPropertyRowMapper<>(EnterpriseDomain.class))
										.mapRow(rs, rowNum);
								ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
										.mapRow(rs, rowNum);
								profileDomain.setEnterprise(enterprise);
								return profileDomain;
							}
						});
			} else if (roleId == Integer.parseInt(Role.DRIVER.getCode())) {
				sql.append(
						"select p.*,r.isActive from profile p,profilerole r where p.id = r.profileId and r.roleId =? ");
				if (upperBound > 0)
					sql.append("desc limit " + lowerBound + "," + upperBound);
			}

			if (roleId == Integer.parseInt(Role.DRIVER.getCode()))
				profileDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
//			else
//				profileDomain = jdbcTemplate.query(sql, new Object[] { roleId, status, franchiseId },
//						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			return profileDomain;
		} catch (

		EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException getProfileByRoleAndFranchiseId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getProfileByRoleAndFranchiseId in ProfileDAOImplV2"
							+ JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomainV2 getProfileDetailsByCompanyProfileId(int roleId, String companyProfileId) throws Exception {
		try {
			String sql = null;
			ProfileDomainV2 profileDomain = null;

			if (roleId != 0) {
				Role role = Role.getRole(roleId + "");
				switch (role) {
				case CUSTOMER:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,c.* from profile p, profilerole r, customer c \r\n"
							+ "where p.id = r.profileId and c.profileId = p.id  and c.customerId = ? and r.roleId = "
							+ Role.CUSTOMER.getCode();
					break;
				// case OWNER:
				// case TIEUPS:
				case BDM:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,b.* from profile p, profilerole r, bdm b \r\n"
							+ "where p.id = r.profileId and b.profileId = p.id  and b.bdmId = and r.roleId = "
							+ Role.BDM.getCode();
					break;
				case BDO:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,b.* from profile p, profilerole r, bdo b \r\n"
							+ "where p.id = r.profileId and b.profileId = p.id  and b.bdoId = and r.roleId = "
							+ Role.BDO.getCode();
					break;
				case CHANNEL_PARTNER:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,c.* from profile p, profilerole r, channelpartner c \r\n"
							+ "where p.id = r.profileId and c.profileId = p.id  and c.channelPartnerId = and r.roleId = "
							+ Role.CHANNEL_PARTNER.getCode();
					break;
				case COORDINATOR:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,c.* from profile p, profilerole r, coordinator c \r\n"
							+ "where p.id = r.profileId and c.profileId = p.id  and c.coordinatorId = ? and r.roleId = "
							+ Role.COORDINATOR.getCode();
					break;
				case FIELDOFFICER:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,f.* from profile p, profilerole r, fieldOfficer f \r\n"
							+ "where p.id = r.profileId and f.profileId = p.id  and f.fieldOfficerId = ? and r.roleId = "
							+ Role.FIELDOFFICER.getCode();
					break;
				case OPERATIONAL_TEAM:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,o.* from profile p, profilerole r, operational_team o \r\n"
							+ "where p.id = r.profileId and o.profileId = p.id  and o.operationalTeamId = ? and r.roleId = "
							+ Role.OPERATIONAL_TEAM.getCode();
					break;
				case FLEET_OPERATOR:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,f.* from profile p, profilerole r, fleetoperator f\r\n"
							+ "where p.id = r.profileId and f.profileId = p.id and f.fleetId = ? and r.roleId = "
							+ Role.FLEET_OPERATOR.getCode();
					break;
				case WAREHOUSE:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,w.* from profile p, profilerole r, warehouse w\r\n"
							+ "where p.id = r.profileId and w.profileId = p.id and w.wareHouseId = ? and r.roleId = "
							+ Role.WAREHOUSE.getCode();
					break;
				case DRIVER:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId from profile p, profilerole r "
							+ "where p.id = r.profileId  and p.id = ? and r.roleId = " + Role.DRIVER.getCode();
					break;
				case FRANCHISE:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,f.* from profile p, profilerole r, franchise f\r\n"
							+ "where p.id = r.profileId and f.profileId = p.id  and f.franchiseId = ? and r.roleId = "
							+ Role.FRANCHISE.getCode();
					break;
				case ENTERPRISE:
					sql = "select p.*,r.isActive,r.appTokenId,r.webTokenId,e.* from profile p, profilerole r, enterprise e\r\n"
							+ "where p.id = r.profileId and e.profileId = p.id and e.enterpriseId = ? and r.roleId = "
							+ Role.ENTERPRISE.getCode();
					break;
				default:
					break;
				}

				profileDomain = jdbcTemplate.queryForObject(sql, new Object[] { companyProfileId },
						new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			}

			/*
			 * if (roleId == Integer.parseInt(Role.FRANCHISE.getCode())){ sql =
			 * "select p.*,r.isActive,r.appTokenId,r.webTokenId,f.* from profile p, profilerole r, franchise f\r\n"
			 * + "where p.id = r.profileId and f.profileId = p.id  and f.franchiseId = ?";
			 * profileDomain = jdbcTemplate.queryForObject(sql, new Object[] {
			 * companyProfileId }, new RowMapper<ProfileDomainV2>() {
			 * 
			 * @Override public ProfileDomainV2 mapRow(ResultSet rs, int rowNum) throws
			 * SQLException { FranchiseDomainV2 franchise = (new
			 * BeanPropertyRowMapper<>(FranchiseDomainV2.class)).mapRow(rs,rowNum);
			 * ProfileDomainV2 profileDomain = (new
			 * BeanPropertyRowMapper<>(ProfileDomainV2.class)).mapRow(rs,rowNum);
			 * profileDomain.setFranchise(franchise); return profileDomain; }
			 * 
			 * }); }
			 */

			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException validatePanNumber in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception validation in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomainV2> getProfilesByOtState(int assignedStateId, boolean status, int lowerBound,
			int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(
					"select p.* from profile p,profilerole r,operational_team ot where p.id = r.profileId and  p.id = ot.profileId and"
							+ " ot.assignedStateId=? and r.isActive=? and r.roleId ="
							+ Role.OPERATIONAL_TEAM.getCode());
			if (upperBound > 0)
				sql.append("limit " + lowerBound + "," + upperBound);
			List<ProfileDomainV2> profileDomain = jdbcTemplate.query(sql.toString(),
					new Object[] { assignedStateId, status },
					new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException usersListByStateId in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception usersListByStateId in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));

			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CoordinatorDetail getCoordinatorByCoordinatorId(String coordinatorId) throws Exception {
		try {
			String sql = null;
			CoordinatorDetail coordinatorDetail = null;
			sql = "select p.*,c.* from profile p,  coordinator c where  c.profileId = p.id  and c.coordinatorId = ? ";
//			profileDomain = jdbcTemplate.queryForObject(sql, new Object[] { coordinatorId },
//					new BeanPropertyRowMapper<ProfileDomainV2>(ProfileDomainV2.class));
			coordinatorDetail = jdbcTemplate.queryForObject(sql, new Object[] { coordinatorId },
					new RowMapper<CoordinatorDetail>() {
						@Override
						public CoordinatorDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
							CoordinatorDetail coordinator = (new BeanPropertyRowMapper<>(CoordinatorDetail.class))
									.mapRow(rs, rowNum);
							Profile profile = new Profile();
							ProfileDomainV2 profileDomain = (new BeanPropertyRowMapper<>(ProfileDomainV2.class))
									.mapRow(rs, rowNum);
							BeanUtils.copyProperties(profileDomain, profile);
							coordinator.setProfile(profile);
							return coordinator;
						}
					});
			return coordinatorDetail;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"EmptyResultDataAccessException validatePanNumber in ProfileDAOImplV2"
									+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(
					EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(), SeverityTypes.CRITICAL.ordinal(),
							"Exception validation in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateEmailAndMobileNo(String email, String mobileNumber, String profileId) throws Exception {
		try {
			String sql = null;
			int res = 0;
			if (!CommonUtils.isNullCheck(email)) {
				sql = "UPDATE profile SET emailId=?, modifiedDate=? WHERE id=?";
				res = jdbcTemplate.update(sql,
						new Object[] { email, DateUtility.getDateFormat(new Date()), profileId });
			}
			if (!CommonUtils.isNullCheck(mobileNumber)) {
				sql = "UPDATE profile SET mobileNumber=?, modifiedDate=? WHERE id=?";
				res = jdbcTemplate.update(sql,
						new Object[] { mobileNumber, DateUtility.getDateFormat(new Date()), profileId });
			}
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("updateEmailAndMobileNo failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateEmailAndMobileNo in ProfileDAOImplV2" + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	/************************************************************************************************************************************************************************/

	/*
	 * @Override public ProfileDomain getPasswordById(String id) throws Exception {
	 * try { String sql = "SELECT password from profile where id=?"; ProfileDomain
	 * profiledomain = jdbcTemplate.queryForObject(sql, new Object[] { id }, new
	 * BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class)); return
	 * profiledomain; } catch (EmptyResultDataAccessException e) {
	 * Loggly.sendLogglyEvent(
	 * EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
	 * SeverityTypes.CRITICAL.ordinal(),
	 * "EmptyResultDataAccessException getPasswordById in ProfileDAOImplV2" +
	 * JsonUtil.toJsonString(e.getMessage()))); return null; } catch (Exception e) {
	 * Loggly.sendLogglyEvent(
	 * EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
	 * SeverityTypes.CRITICAL.ordinal(),
	 * "Exception getPasswordById in ProfileDAOImplV2" +
	 * JsonUtil.toJsonString(e.getMessage())));
	 * 
	 * throw new BACKEND_SERVER_ERROR(); } }
	 */

}
