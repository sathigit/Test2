package com.atpl.mmg.dao.location;

import java.util.List;

import com.atpl.mmg.domain.location.LocationDomain;

public interface LocationDAO {
	
	public List<LocationDomain> getLocation() throws Exception;
	
	public LocationDomain getLocation(int id) throws Exception;
}
