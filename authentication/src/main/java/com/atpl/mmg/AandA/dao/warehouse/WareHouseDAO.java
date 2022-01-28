package com.atpl.mmg.AandA.dao.warehouse;

import java.util.List;

import com.atpl.mmg.AandA.domain.profile.WareHouseDomain;

public interface WareHouseDAO {
	public WareHouseDomain save(WareHouseDomain wareHouseDomain) throws Exception;
	
	public WareHouseDomain checkWarehouseRegisterNumber(String registrationNumber) throws Exception;
	
	public WareHouseDomain getWarehouseByProfileId(String profileId) throws Exception;
	
	public String update(WareHouseDomain wareHouseDomain) throws Exception;
	
	public List<WareHouseDomain>  getWareHouses() throws Exception;
}
