package com.atpl.mmg.AandA.dao.login;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.domain.login.LoginDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.INVALID_PASSWORD;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.utils.CommonUtils;

@Repository
public class LoginDAOImpl implements LoginDAO {
	private static final Logger logger = LoggerFactory.getLogger(LoginDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public LoginDomain authenticate(LoginDomain loginDomain) throws Exception {
		try {
			loginDomain.setPassword(CommonUtils.encriptString(loginDomain.getPassword()));
			String sql = "SELECT p.id,p.roleId,r.path from profile p ,role r where  p.roleId = r.id and (p.mobileNumber= ? or p.emailId=?) and p.password=? and p.status = 1";
			return jdbcTemplate.queryForObject(sql,
					new Object[] { loginDomain.getUserName(), loginDomain.getUserName(), loginDomain.getPassword() },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException auteneticate in LoginDAOImpl" + e.getMessage());
			throw new INVALID_PASSWORD();
		} catch (Exception e) {
			logger.error("Exception auteneticate in LoginDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public LoginDomain customerAuthenticate(LoginDomain loginDomain) throws Exception {
		try {
			loginDomain.setPassword(CommonUtils.encriptString(loginDomain.getPassword()));
			String sql = "SELECT p.id,p.roleId,r.path,p.frequentCustomer from customer p ,role r where  p.roleId = r.id and (p.mobileNumber= ? or p.emailId=?) and p.password=? and p.status = 1";
			return jdbcTemplate.queryForObject(sql,
					new Object[] { loginDomain.getUserName(), loginDomain.getUserName(), loginDomain.getPassword() },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException auteneticate in LoginDAOImpl" + e.getMessage());
			throw new INVALID_PASSWORD();
		} catch (Exception e) {
			logger.error("Exception customerAuthenticate in LoginDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<LoginDomain> getPermission(int roleId) throws Exception {
		try {
			String sql = "SELECT permissionId,permissionName from permission prm join prole prl on prm.id=prl.id join role rl on prl.roleId=? limit 6";
			List<LoginDomain> loginDomain = jdbcTemplate.query(sql, new Object[] { roleId },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
			return loginDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getCustomerPermission in LoginDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Owner permission not found");
		} catch (Exception e) {
			logger.error("Exception getCustomerPermission in LoginDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public LoginDomain getMobileNumberProfile(BigInteger mobileNumber) throws Exception {
		try {
			String sql = "SELECT * from profile p where p.contactNumber=?";
			LoginDomain logindomain = jdbcTemplate.queryForObject(sql, new Object[] { mobileNumber },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
			return logindomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getMobileNumberProfile in LoginDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getMobileNumberProfile in LoginDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	public LoginDomain mobileauthenticate(LoginDomain logindomain) throws Exception {
		try {
			String sql = "  SELECT p.id,p.mobileNumber,p.roleId,r.path, s.sessionId FROM auth.profile p , auth.session s , auth.role r where \r\n"
					+ "					 (p.mobileNumber= ?)  and p.roleId = r.id and p.mobileNumber = s.emailId and p.status = 1 and s.isActive =1;";
			return jdbcTemplate.queryForObject(sql, new Object[] { logindomain.getMobileNumber() },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException mobileauthenticate in LoginDAOImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception mobileauthenticate in LoginDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String savePermission(LoginDomain loginDomain) throws Exception {
		try {
			String sql = "INSERT INTO prole (id, roleId,status) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { loginDomain.getId(), loginDomain.getRoleId(), loginDomain.isStatus() });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("Prole save failed");
		} catch (Exception e) {
			logger.error("Exception savePermission in LoginDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LoginDomain roleCount(int id) throws Exception {
		try {
			String sql = "select count(*) As total FROM auth.prole where id=?";

			return (LoginDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getting all roleCount in LoginDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Role count not found");
		} catch (Exception e) {
			logger.error("Exception getting all roleCount in LoginDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<LoginDomain> getRoleName(int id) throws Exception {
		try {
			String sql = "select r.id,r.roleName,r.path from role r left join(select roleId,id,status from profile union all select roleId,id,status from prole)as a on a.roleId=r.id where a.status=1 and a.id=?";
			List<LoginDomain> loginDomain = jdbcTemplate.query(sql, new Object[] { id },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
			return loginDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getRoleName in LoginDAOImpl" + e.getMessage());
			throw new NOT_FOUND("ProleName not found");
		} catch (Exception e) {
			logger.error("Exception getRoleName in LoginDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public LoginDomain rolePath(int id) throws Exception {
		try {
			String sql = "select * FROM auth.role where id=?";
			return (LoginDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException rolePath in LoginDAOImpl" + e.getMessage());
			throw new NOT_FOUND("rolePath not found");
		} catch (Exception e) {
			logger.error("Exception rolePath all roleCount in LoginDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deleteProle(int proleId) throws Exception {
		try {
			String sql = "DELETE FROM prole WHERE proleId=?";
			int res = jdbcTemplate.update(sql, new Object[] { proleId });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("Prole delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteBank in BankDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public LoginDomain getProle(int id, int roleId) throws Exception {
		try {
			String sql = "select proleId FROM auth.prole where id=? and roleId=?";

			return (LoginDomain) jdbcTemplate.queryForObject(sql, new Object[] { id, roleId },
					new BeanPropertyRowMapper<LoginDomain>(LoginDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getProle in LoginDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Prole not found");
		} catch (Exception e) {
			logger.error("Exception getProle all roleCount in LoginDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
