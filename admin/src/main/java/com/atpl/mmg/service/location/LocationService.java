package com.atpl.mmg.service.location;

import java.util.List;

import com.atpl.mmg.model.location.LocationModel;

public interface LocationService {
	
	public List<LocationModel> getLocation() throws Exception;
	
	public LocationModel getLocation(int  id) throws Exception;
	
}