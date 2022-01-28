package com.atpl.mmg.AandA.dao.profile;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.domain.profile.ProfileDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.PASSWORD_NOT_UPDATED;
import com.atpl.mmg.AandA.exception.MmgRestException.PROFILES_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.PROFILE_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.utils.DataValidation;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class ProfileDAOImpl implements ProfileDAO {

	private static final Logger logger = LoggerFactory.getLogger(ProfileDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataValidation dataValidation;

	public ProfileDomain saveProfile(ProfileDomain profileDomain) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO profile (firstName,middleName,lastName,genderId,roleId,dob,doorNumber,pincode,street,countryId,stateId,cityId,mobileNumber,alternativeNumber,emailId,password,confirmPassword,status,creationDate,modificationDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { profileDomain.getFirstName(), profileDomain.getMiddleName(),
							profileDomain.getLastName(), profileDomain.getGenderId(), profileDomain.getRoleId(),
							profileDomain.getDob(), profileDomain.getDoorNumber(), profileDomain.getPincode(),
							profileDomain.getStreet(), profileDomain.getCountryId(), profileDomain.getStateId(),
							profileDomain.getCityId(), profileDomain.getMobileNumber(),
							profileDomain.getAlternativeNumber(), profileDomain.getEmailId(),
							profileDomain.getPassword(), profileDomain.getConfirmPassword(),
							simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()) });
			if (res == 1) {
				return getProfileId(profileDomain.getMobileNumber());

			} else
				throw new SAVE_FAILED("Profile save failed");
		} catch (Exception e) {
			logger.error("Exception saveProfile in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getProfile(String id) throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,tokenId,webToken from profile where id=?";
			ProfileDomain profiledomain = jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profiledomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfile in ProfileDAOImpl" + e.getMessage());
			throw new PROFILE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getProfile in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomain> getAllProfileCustomer() throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,tokenId,webToken,frequentCustomer from customer where frequentCustomer=1";
			List<ProfileDomain> profileDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfile in ProfileDAOImpl" + e.getMessage());
			throw new PROFILES_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getProfile in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getProfileId(String mobileNumber) throws Exception {
		try {
			String sql = "SELECT profile.id from profile where mobileNumber=? order by creationDate DESC limit 1";
			return (ProfileDomain) jdbcTemplate.queryForObject(sql, new Object[] { mobileNumber },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfileId in ProfileDAOImpl" + e.getMessage());
			throw new PROFILE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getProfileId in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getProfileByEmailId(String emailId) throws Exception {
		try {
			String sql = "SELECT emailId from profile p where p.emailId=?";
			return (ProfileDomain) jdbcTemplate.queryForObject(sql, new Object[] { emailId },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfileByEmailId in ProfileDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getProfileByEmailId in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateProfile(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE profile SET firstName=?,lastName=?,doorNumber=?,pincode=?,street=?,countryId=?,stateId=?,cityId=?,alternativeNumber=? WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { profileDomain.getFirstName(), profileDomain.getLastName(),
							profileDomain.getDoorNumber(), profileDomain.getPincode(), profileDomain.getStreet(),
							profileDomain.getCountryId(), profileDomain.getStateId(), profileDomain.getCityId(),
							profileDomain.getAlternativeNumber(), profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Profile update failed");
		} catch (Exception e) {
			logger.error("Exception updateProfile in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateDriverProfile(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE profile SET firstName=?,lastName=?,dob=?,emailId =?,mobileNumber=?,doorNumber=?,pincode=?,street=?,countryId=?,stateId=?,cityId=? WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { profileDomain.getFirstName(), profileDomain.getLastName(), profileDomain.getDob(),
							profileDomain.getEmailId(), profileDomain.getMobileNumber(), profileDomain.getDoorNumber(),
							profileDomain.getPincode(), profileDomain.getStreet(), profileDomain.getCountryId(),
							profileDomain.getStateId(), profileDomain.getCityId(), profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Profile update failed");
		} catch (Exception e) {
			logger.error("Exception updateProfile in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updatePassword(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE profile SET password=?, confirmPassword=? WHERE mobileNumber=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getPassword(),
					profileDomain.getConfirmPassword(), profileDomain.getMobileNumber() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new PASSWORD_NOT_UPDATED();
		} catch (Exception e) {
			logger.error("Exception updatePassword in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String Activation(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE profile SET status=1  WHERE id=? ";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update failed");
		} catch (Exception e) {
			logger.error("Exception Activation in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String disableFranchise(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE profile SET status=0 WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Update failed");
		} catch (Exception e) {
			logger.error("Exception disableFranchise in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomain> getProfilebyRole(int roleId) throws Exception {
		try {
			String sql = "SELECT id,firstName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,status FROM profile where roleId=?";
			List<ProfileDomain> profileDomain = jdbcTemplate.query(sql, new Object[] { roleId },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfilebyRole in BookingDAOImpl" + e.getMessage());
			throw new PROFILES_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getProfilebyRole in BookingDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String saveProfileforAndriod(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "INSERT INTO customer (mobileNumber, roleId, status) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getMobileNumber(),
					profileDomain.getRoleId()});
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Profile save failed");
		} catch (Exception e) {
			logger.error("Exception saveProfileforAndriod in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomain> customerActiveOrInActive(boolean status) throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,status,creationDate FROM customer where status=?";
			List<ProfileDomain> profileDomain = jdbcTemplate.query(sql, new Object[] { status },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getActiveCustomer in ProfileDAOImpl" + e.getMessage());
			throw new PROFILES_NOT_FOUND("Active Customer not found");
		} catch (Exception e) {
			logger.error("Exception getActiveCustomer in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public List<ProfileDomain> getCustomerbyCity(int cityId, boolean status) throws Exception {
		try {
			List<ProfileDomain> profileDomain = new ArrayList<ProfileDomain>();
			if (status == true) {
				String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,creationDate FROM customer where status=? and cityId=?";
				profileDomain = jdbcTemplate.query(sql, new Object[] { status, cityId },
						new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			} else {
				String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,creationDate FROM customer where status=? and cityId=?";
				profileDomain = jdbcTemplate.query(sql, new Object[] { status, cityId },
						new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			}
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getActiveCustomer in ProfileDAOImpl" + e.getMessage());
			throw new PROFILES_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getActiveCustomer in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	public List<ProfileDomain> getCustomerByState(int stateId, boolean status) throws Exception {
		try {
			List<ProfileDomain> profileDomain = new ArrayList<ProfileDomain>();
			if (status == true) {
				String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,creationDate FROM auth.customer where status=? and stateId=?";
				profileDomain = jdbcTemplate.query(sql, new Object[] { status, stateId },
						new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			} else {
				String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,creationDate FROM auth.customer where status=? and stateId=?";
				profileDomain = jdbcTemplate.query(sql, new Object[] { status, stateId },
						new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			}
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getCustomerByState in ProfileDAOImpl" + e.getMessage());
			throw new PROFILES_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getCustomerByState in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String customerActivation(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE customer SET status=1 WHERE roleId=1 and id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update failed");
		} catch (Exception e) {
			logger.error("Exception customerActivation in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String disableCustomer(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE customer SET status=0 WHERE roleId=1 and id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update failed");
		} catch (Exception e) {
			logger.error("Exception disableCustomer in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteProfile(int id) throws Exception {
		try {
			String sql = "delete from profile where id = ?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteProfile in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getProfileById(String id) throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,creationDate from profile p where p.id=?";
			ProfileDomain profiledomain = jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profiledomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfile in ProfileDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getProfile in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getProfileByCityId(int cityId, String id) throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,creationDate from auth.profile p where p.id=? and p.cityId=?";
			ProfileDomain profiledomain = jdbcTemplate.queryForObject(sql, new Object[] { id, cityId },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profiledomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfile in ProfileDAOImpl" + e.getMessage());
			throw new PROFILE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getProfile in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateCompany(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE profile SET firstName=?,alternativeNumber=?,countryId=?,stateId=?,cityId=? WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { profileDomain.getFirstName(), profileDomain.getAlternativeNumber(),
							profileDomain.getCountryId(), profileDomain.getStateId(), profileDomain.getCityId(),
							profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update profile failed");
		} catch (Exception e) {
			logger.error("Exception updateCompany in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateProfileTokenId(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE profile SET tokenId=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getTokenId(), profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				return null;
		} catch (Exception e) {
			logger.error("Exception updateProfileTokenId in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getPasswordById(String id) throws Exception {
		try {
			String sql = "SELECT password from profile p where p.id=?";
			return (ProfileDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfileByEmailId in ProfileDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getProfileByEmailId in ProfileDAOImpl" + e.getMessage());
			return null;
		}
	}

	@Override
	public String updatePasswordById(ProfileDomain profileDomain) throws Exception {

		try {
			String sql = "UPDATE profile SET password=?, confirmPassword=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getPassword(),
					profileDomain.getConfirmPassword(), profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new PASSWORD_NOT_UPDATED();
		} catch (Exception e) {
			logger.error("Exception updatePasswordById in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateWebToken(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE customer SET webToken=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getWebToken(), profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Token update failed");
		} catch (Exception e) {
			logger.error("Exception updateWebToken in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getProfileDetails(BigInteger mobileNumber) throws Exception {
		try {
			String sql = "SELECT * from profile where mobileNumber=? ";
			return (ProfileDomain) jdbcTemplate.queryForObject(sql, new Object[] { mobileNumber },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfile in ProfileDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getProfilein ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getProfileRefferCode(String id, String refferCode) throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,tokenId,webToken,refferCode,creationDate from customer p where p.id=? AND p.refferCode=?";
			return (ProfileDomain) jdbcTemplate.queryForObject(sql, new Object[] { id, refferCode },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfileRefferCode in ProfileDAOImpl" + e.getMessage());
			throw new PROFILE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getProfileRefferCode in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain saveProfileCustomer(ProfileDomain profileDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "INSERT INTO customer (firstName,middleName,lastName,genderId,roleId,dob,doorNumber,pincode,street,countryId,stateId,cityId,mobileNumber,alternativeNumber,emailId,password,confirmPassword,status,refferCode,frequentCustomer,isTermsAndCondition,termsAndConditionsId,creationDate,modificationDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { profileDomain.getFirstName(), profileDomain.getMiddleName(),
							profileDomain.getLastName(), profileDomain.getGenderId(), profileDomain.getRoleId(),
							profileDomain.getDob(), profileDomain.getDoorNumber(), profileDomain.getPincode(),
							profileDomain.getStreet(), profileDomain.getCountryId(), profileDomain.getStateId(),
							profileDomain.getCityId(), profileDomain.getMobileNumber(),
							profileDomain.getAlternativeNumber(), profileDomain.getEmailId(),
							profileDomain.getPassword(), profileDomain.getConfirmPassword(),
							profileDomain.getRefferCode(), profileDomain.isFrequentCustomer(),
							simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()) });
			if (res == 1) {
				return getCustomerProfileId(profileDomain.getMobileNumber());
			} else
				throw new SAVE_FAILED("Profile save failed");
		} catch (Exception e) {
			logger.error("Exception in saveProfileCustomer in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public ProfileDomain getCustomerProfileId(String mobileNumber) throws Exception {
		try {
			ProfileDomain profileDomain = new ProfileDomain();
			String sql = "SELECT customer.id from customer where mobileNumber=? order by creationDate DESC limit 1";
			profileDomain = jdbcTemplate.queryForObject(sql, new Object[] { mobileNumber },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfileId in ProfileDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getCustomerProfileId in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateTokenIdCustomer(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE customer SET tokenId=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getTokenId(), profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Token update failed");
		} catch (Exception e) {
			logger.error("Exception in updateProfile in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updatePasswordCustomer(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE customer SET password=?, confirmPassword=? WHERE mobileNumber=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getPassword(),
					profileDomain.getConfirmPassword(), profileDomain.getMobileNumber() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new PASSWORD_NOT_UPDATED();
		} catch (Exception e) {
			logger.error("Exception in updatePasswordCustomer", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getPasswordByIdCustomer(String id) throws Exception {
		try {
			String sql = "SELECT password from customer where id=?";
			return (ProfileDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfileByEmailId in ProfileDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getProfileByEmailId in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updatePasswordByIdCustomer(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE customer SET password=?, confirmPassword=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getPassword(),
					profileDomain.getConfirmPassword(), profileDomain.getId() });
			if (res == 1) {
				return "Password updated successfully";
			} else
				throw new PASSWORD_NOT_UPDATED();
		} catch (Exception e) {
			logger.error("Exception updatePasswordByIdCustomer in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateCustomerProfile(ProfileDomain profileDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE customer SET firstName=?,lastName=?,doorNumber=?,pincode=?,street=?,countryId=?,stateId=?,cityId=?,alternativeNumber=?,modificationDate=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getFirstName(), profileDomain.getLastName(),
					profileDomain.getDoorNumber(), profileDomain.getPincode(), profileDomain.getStreet(),
					profileDomain.getCountryId(), profileDomain.getStateId(), profileDomain.getCityId(),
					profileDomain.getAlternativeNumber(), simpleDateFormat.format(new Date()), profileDomain.getId() });
			if (res == 1) {
				return "Update sucessfully";
			} else
				throw new UPDATE_FAILED("Profile update failed");
		} catch (Exception e) {
			logger.error("Exception updateCustomerProfile in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getProfileCustomer(String id) throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,tokenId,webToken,frequentCustomer,creationDate from customer where id=?";
			ProfileDomain profiledomain = jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profiledomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfile in ProfileDAOImpl" + e.getMessage());
			throw new PROFILE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getProfile in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateCustomerCreditAmount(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE customer SET credit=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.getCredit(), profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Credit amount update failed");
		} catch (Exception e) {
			logger.error("Exception updateCustomerCreditAmount in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteDriver(String mobileNumber) throws Exception {
		try {
			String sql = "delete from profile where mobileNumber = ?";
			int res = jdbcTemplate.update(sql, new Object[] { mobileNumber });
			if (res == 1) {
				return "Deleted successfully";
			} else {
				throw new DELETE_FAILED("Delete failed");
			}
		} catch (Exception e) {
			logger.error("Exception deleteDriver in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ProfileDomain getProfileFreqeuntCustomer(String id) throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,tokenId,webToken,frequentCustomer,creationDate from customer p where p.frequentCustomer=1 and p.id=?";
			ProfileDomain profiledomain = jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profiledomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProfile in ProfileDAOImpl" + e.getMessage());
			throw new PROFILE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getProfile in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	/*
	 * Author:Sindhu Creationdate: 16-11-2019,4-3-2020 Description: bdm and bdo,
	 * driver list based on city
	 */
	public List<ProfileDomain> profileDetailsByCity(String roleId, int cityId) throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,creationDate FROM profile where status=1 and roleId=? and cityId=?";
			List<ProfileDomain> profileDomain = jdbcTemplate.query(sql, new Object[] { roleId, cityId },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getbdmProfileListonCity in ProfileDAOImpl" + e.getMessage());
			return null; // returning null as we are considering this as success while franchise update
		} catch (Exception e) {
			logger.error("Exception getbdmProfileListonCity in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	/*
	 * Author:Sindhu Creationdate: 29-11-2019,4-3-2020 Description: To get active
	 * bdm
	 */
	@Override
	public List<ProfileDomain> activeBDMsOrBDOs(String roleId) throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,tokenId,webToken,status from profile where status=1 and roleId=?";
			List<ProfileDomain> profileDomain = jdbcTemplate.query(sql, new Object[] { roleId },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getActiveBdms in ProfileDAOImpl" + e.getMessage());
			throw new PROFILE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getActiveBdms in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateCrmEnrolled(ProfileDomain profileDomain) throws Exception {
		try {
			String sql = "UPDATE profile SET enrolledCrm=? WHERE  id=?";
			int res = jdbcTemplate.update(sql, new Object[] { profileDomain.isEnrolledCrm(), profileDomain.getId() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Failed");
		} catch (Exception e) {
			logger.error("Exception updateCrmEnrolled in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	/*
	 * Author:Sindhu Creationdate: 29-11-2019,4-3-2020 Description: To get active
	 * enrolled BDM or BDO
	 */
	@Override
	public List<ProfileDomain> enrolledActiveBdmsOrBDOs(String roleId) throws Exception {
		try {
			String sql = "SELECT id,firstName,middleName,lastName,roleId,stateId,cityId,countryId,doorNumber,street,mobileNumber,alternativeNumber,emailId,pincode,dob,genderId,tokenId,webToken,status from profile where status=1 and enrolledCrm=0 and roleId=?";
			List<ProfileDomain> profileDomain = jdbcTemplate.query(sql, new Object[] { roleId },
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));
			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getEnrolledActiveBdms in ProfileDAOImpl" + e.getMessage());
			throw new PROFILE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getEnrolledActiveBdms in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ProfileDomain> getCustomerCSVDetails() throws Exception {
		try {
			String sql = "SELECT id,firstName,lastName,doorNumber,street,mobileNumber,emailId,pincode,stateId,cityId,status FROM customer where  status=1";
			List<ProfileDomain> profileDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<ProfileDomain>(ProfileDomain.class));

			return profileDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getCustomerCSVDetails in ProfileDAOImpl" + e.getMessage());
			throw new PROFILE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getCustomerCSVDetails in ProfileDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteCustomer(int id) throws Exception {
		try {
			String sql = "delete from customer where id = ?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteCustomer in ProfileDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
