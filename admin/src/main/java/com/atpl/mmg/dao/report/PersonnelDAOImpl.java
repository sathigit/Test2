package com.atpl.mmg.dao.report;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.report.PersonnelDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;

@Repository
public class PersonnelDAOImpl implements PersonnelDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(PersonnelDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String addPersonnel(PersonnelDomain personnelDomain) throws Exception {
		try {
			String sql = "INSERT INTO personnel (personnelSource,personnelYear,personnelAmount) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { personnelDomain.getPersonnelSource(),
					personnelDomain.getPersonnelYear(), personnelDomain.getPersonnelAmount() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("Personnel save failed");
		} catch (Exception e) {
			logger.error("Exception addPersonnel in PersonnelDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String addPersonnelSource(PersonnelDomain personnelDomain) throws Exception {
		try {
			String sql = "INSERT INTO admin.personnelsource (source) VALUES(?)";
			int res = jdbcTemplate.update(sql, new Object[] { personnelDomain.getSource() });
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("PersonnelSource save failed");
		} catch (Exception e) {
			logger.error("Exception addPersonnelSource in PersonnelDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public PersonnelDomain getPersonnelSource(int id) throws Exception {

		try {
			String sql = "SELECT * FROM personnelsource where id=?";
			return (PersonnelDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<PersonnelDomain>(PersonnelDomain.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception getPersonnelSourcebyid in PersonnelDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updatePersonnelSource(PersonnelDomain PersonnelDomain) throws Exception {
		try {
			String sql = " UPDATE admin.personnelsource SET  source=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { PersonnelDomain.getSource(), PersonnelDomain.getId() });
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("PersonnelSource update failed");
		} catch (Exception e) {
			logger.error("Exception updatePersonnelSource in PersonnelDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String deletePersonnelSource(int id) throws Exception {
		try {
			String sql = "DELETE FROM personnelsource WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("PersonnelSource delete failed");
		} catch (Exception e) {
			logger.error("Exception deletePersonnelSource in PersonnelDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<PersonnelDomain> getPersonnelSource() throws Exception {
		try {
			String sql = "SELECT id,source FROM admin.personnelsource";
			List<PersonnelDomain> personnelDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<PersonnelDomain>(PersonnelDomain.class));
			return personnelDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("Exception getDirectCostSourcebyid in DirectCostDAOImpl", e);
			throw new NOT_FOUND("PersonnelSource not found");
		} catch (Exception e) {
			logger.error("Exception getPersonnelSource in PersonnelDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}
}
