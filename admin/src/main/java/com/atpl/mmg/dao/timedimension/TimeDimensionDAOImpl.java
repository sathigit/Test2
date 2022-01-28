package com.atpl.mmg.dao.timedimension;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.timedimension.TimeDimensionDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;

@Repository
public class TimeDimensionDAOImpl implements TimeDimensionDAO,Constants {

	private static final Logger logger = LoggerFactory.getLogger(TimeDimensionDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<TimeDimensionDomain> getDate(String contractStartDate,String contractEndDate,String day1) throws Exception {
		try {
			String sql = "SELECT db_date,day,day_name from time_dimension where db_date>=? and db_date<=? and day_name=?";
			List<TimeDimensionDomain> stateDomain = jdbcTemplate.query(sql, new Object[] { contractStartDate,contractEndDate,day1 },
					new BeanPropertyRowMapper<TimeDimensionDomain>(TimeDimensionDomain.class));
			return stateDomain;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getDate in TimeDimensionDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Date not found");
		} catch (Exception e) {
			logger.error("Exception getDate in TimeDimensionDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public TimeDimensionDomain getWeekDays(String date) throws Exception {
		try {
			String sql = "SELECT day_name,day FROM time_dimension where db_date=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { date },
					new BeanPropertyRowMapper<TimeDimensionDomain>(TimeDimensionDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getWeekDays in StateDAOImpl" + e.getMessage());
			throw new NOT_FOUND("Week days not found");
		} catch (Exception e) {
			logger.error("Exception getWeekDays in StateDAOImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
