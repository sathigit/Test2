package com.atpl.mmg.AandA.dao.warehouse;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.profile.WareHouseDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import  com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class WareHouseDAOImpl implements WareHouseDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public WareHouseDomain save(WareHouseDomain wareHouseDomain) throws Exception {
		try {
			String sql = "INSERT INTO warehouse(warehouseId,profileId,companyName,yearOfContract,startDate,endDate,franchiseId,registrationNumber,maxCapacity,mdName,"
					+ "latitude,longitude,boardingEnquiryId,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { wareHouseDomain.getWareHouseId(), wareHouseDomain.getProfileId(),
							wareHouseDomain.getCompanyName(),
							wareHouseDomain.getYearOfContract(),wareHouseDomain.getStartDate(),wareHouseDomain.getEndDate(),
							wareHouseDomain.getFranchiseId(),wareHouseDomain.getRegistrationNumber(),
							wareHouseDomain.getMaxCapacity(), wareHouseDomain.getMdName(),
							wareHouseDomain.getLatitude(), wareHouseDomain.getLongitude(),wareHouseDomain.getBoardingEnquiryId(),
							DateUtility.getDateFormat(new Date()),DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return wareHouseDomain;
			} else
				throw new SAVE_FAILED(" Warehouse Details save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WAREHOUSE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception saveWarehouse in WareHouseDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public WareHouseDomain checkWarehouseRegisterNumber(String registrationNumber) throws Exception {
		try {
			String sql = "select * from warehouse  where  registrationNumber=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { registrationNumber },
					new BeanPropertyRowMapper<WareHouseDomain>(WareHouseDomain.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WAREHOUSE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception checkWarehouseRegisterNumber in WareHouseDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public WareHouseDomain getWarehouseByProfileId(String profileId) throws Exception {
		try {
			String sql = "select * from warehouse  where profileId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { profileId },
					new BeanPropertyRowMapper<WareHouseDomain>(WareHouseDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("Wearhouse Details Not found");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WAREHOUSE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getWarehouseByProfileId in WareHouseDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String update(WareHouseDomain wareHouseDomain) throws Exception {
		try {
			String sql = "UPDATE warehouse SET companyName=?, yearOfContract=?,startDate=?,endDate=?,maxCapacity=?,registrationNumber=?,mdName=?,"
					+ "latitude=?,longitude=? WHERE warehouseId=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { wareHouseDomain.getCompanyName(),wareHouseDomain.getYearOfContract(),wareHouseDomain.getStartDate(),wareHouseDomain.getEndDate(),
							wareHouseDomain.getMaxCapacity(),wareHouseDomain.getRegistrationNumber(),wareHouseDomain.getMdName(),wareHouseDomain.getLatitude(),
							wareHouseDomain.getLongitude(),wareHouseDomain.getWareHouseId()});
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("Updated Failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WAREHOUSE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception updateWareHouse in WareHouseDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<WareHouseDomain> getWareHouses() throws Exception {
		try {
			String sql = "SELECT * FROM warehouse  order by companyName asc";
			List<WareHouseDomain> WarehouseList = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<WareHouseDomain>(WareHouseDomain.class));
			return WarehouseList;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WAREHOUSE_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(), "Exception getWareHouses in WareHouseDAOImpl"
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	
	
}
