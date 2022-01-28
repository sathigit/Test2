package com.atpl.mmg.dao.termsandcondition;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.termsandcondition.TandCDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.T_C_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class TandCDAOImpl implements TandCDAO {
	private static final Logger logger = LoggerFactory.getLogger(TandCDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public TandCDomain saveTandC(TandCDomain tandCDomain) throws Exception {
		try {

			String sql = "INSERT INTO termsandcondition (uuid,roleId,version,name,termsAndConditions,isActive,comments,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { tandCDomain.getUuid(), tandCDomain.getRoleId(), tandCDomain.getVersion(),
							tandCDomain.getName(), tandCDomain.getTermsAndConditions(),
							tandCDomain.getIsActive(), tandCDomain.getComments(), DateUtility.setTimeZone(new Date()),
							DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return tandCDomain;
			} else
				throw new SAVE_FAILED("Terms and conditions saved successfully");
		} catch (Exception e) {
			logger.error("Exception saveTandC in TandCDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public TandCDomain getTandCByRole(int roleId, boolean status) throws Exception {
		try {
			String sql;
			TandCDomain tandCDomain = new TandCDomain();
			if (status) {
				sql = "select * from  termsandcondition where roleId=? order by createdDate desc limit 1";
				tandCDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId },
						new BeanPropertyRowMapper<TandCDomain>(TandCDomain.class));
			} else {
				sql = "select * from  termsandcondition where roleId=? and isActive=?";
				tandCDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId, true },
						new BeanPropertyRowMapper<TandCDomain>(TandCDomain.class));
			}
			return tandCDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getTandCByRole in TandCDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getTandCByRole in TandCDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<TandCDomain> getTandCListByRole(int roleId) throws Exception {
		try {
			String sql = "select * from  termsandcondition where roleId=? and isActive=?";
			List<TandCDomain> tandCDomain = jdbcTemplate.query(sql, new Object[] { roleId, true },
					new BeanPropertyRowMapper<TandCDomain>(TandCDomain.class));
			return tandCDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getTandCListByRole in TandCDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getTandCListByRole in TandCDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<TandCDomain> getAllTandCListByRole(int roleId,int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from  termsandcondition where roleId=?");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "select * from  termsandcondition where roleId=?";
			List<TandCDomain> tandCDomain = jdbcTemplate.query(sql.toString(), new Object[] { roleId },
					new BeanPropertyRowMapper<TandCDomain>(TandCDomain.class));
			return tandCDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getAllTandCListByRole in TandCDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getAllTandCListByRole in TandCDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public TandCDomain getTandCById(String uuid) throws Exception {
		try {
			String sql = "select * from  termsandcondition where uuid=?";
			TandCDomain tandCDomain = jdbcTemplate.queryForObject(sql, new Object[] { uuid },
					new BeanPropertyRowMapper<TandCDomain>(TandCDomain.class));
			return tandCDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getTandCById in TandCDAOImpl" + e.getMessage());
			throw new T_C_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getTandCById in TandCDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateTermsAndCondition(String uuid, boolean isActive) throws Exception {
		try {
			String sql = "UPDATE termsandcondition  SET isActive=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { isActive,DateUtility.setTimeZone(new Date()), uuid });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Terms and Condition Failed");
		} catch (Exception e) {
			logger.error("Exception UpdateTandC in TandCDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateTandC(String uuid, String termsAndConditions) throws Exception {
		try {
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
//			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
			String sql = "UPDATE termsandcondition  SET termsAndConditions=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { termsAndConditions,DateUtility.setTimeZone(new Date()),uuid });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Update Terms and Condition Failed");
		} catch (Exception e) {
			logger.error("Exception UpdateTandC in TandCDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<TandCDomain> getTandCListByRoleAndStatus(int roleId, boolean isActive) throws Exception {
		try {
			String sql = "select * from termsandcondition where roleId=? and isActive=?";
			List<TandCDomain> tandCDomain = jdbcTemplate.query(sql, new Object[] { roleId, isActive },
					new BeanPropertyRowMapper<TandCDomain>(TandCDomain.class));
			return tandCDomain;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception getTandCListByRoleAndStatus in TandCDAOImpl", e.getMessage());
			return null;
		}
	}

	@Override
	public List<TandCDomain> getTandCList(boolean isActive,int lowerBound,int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from  termsandcondition where isActive=?");
			if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "select * from termsandcondition where  isActive=?";
			List<TandCDomain> tandCDomain = jdbcTemplate.query(sql.toString(), new Object[] { isActive },
					new BeanPropertyRowMapper<TandCDomain>(TandCDomain.class));
			return tandCDomain;
		} catch (Exception e) {
			logger.error("Exception getTandCList in TandCDAOImpl", e.getMessage());
			return null;
		}
	}

	@Override
	public DashboardDomain getAllTandCListByRoleCount(int roleId) throws Exception {
		try {
			String sql = "select count(*) as total from  termsandcondition where roleId=?";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (Exception e) {
			logger.error("Exception getTandCById in TandCDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getTandCListByRoleAndStatusCount(int roleId, boolean isActive) throws Exception {
		try {
			String sql = "select count(*) as total from termsandcondition where roleId=? and isActive=?";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { roleId , isActive},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (Exception e) {
			logger.error("Exception getTandCListByRoleAndStatusCount in TandCDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getTandCListCount(boolean isActive) throws Exception {
		try {
			String sql = "select count(*) as total from termsandcondition where  isActive=?";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { isActive},
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (Exception e) {
			logger.error("Exception getTandCListCount in TandCDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
