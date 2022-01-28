package com.atpl.mmg.dao.location;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.location.LocationDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;


@Repository
public class LocationDAOImpl implements LocationDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(LocationDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<LocationDomain> getLocation() throws Exception {
		try {
			String sql = "SELECT * FROM location";
			List<LocationDomain> franchise= jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<LocationDomain>(LocationDomain.class));
			return franchise;
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("location not found");
		} catch (Exception e) {
			logger.error("Exception getLocation in LocationDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public LocationDomain getLocation(int id) throws Exception {
			try {
				String sql = "SELECT * FROM location where id=?";
				return (LocationDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
						new BeanPropertyRowMapper(LocationDomain.class));
			} catch (EmptyResultDataAccessException e) {
				throw new NOT_FOUND("location not found");
			} catch (Exception e) {
				logger.error("Exception getLocationbyid in LocationDAOImpl", e);
				throw new BACKEND_SERVER_ERROR();
			}
		}
	
}