package com.atpl.mmg.service.location;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.location.LocationDAO;
import com.atpl.mmg.domain.location.LocationDomain;
import com.atpl.mmg.mapper.location.LocationMapper;
import com.atpl.mmg.model.location.LocationModel;

@Service("locationService")
public class LocationServiceImpl implements LocationService, Constants {

	@Autowired
	LocationDAO locationDAO;

	@Autowired
	LocationMapper locationMapper;

	private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

	public LocationServiceImpl() {
		// constructor
	}

	@Override
	public List<LocationModel> getLocation() throws Exception {
		List<LocationDomain> Location = locationDAO.getLocation();
		return locationMapper.entityList(Location);
	}

	@Override
	public LocationModel getLocation(int id) throws Exception {
		LocationDomain locationDomain = locationDAO.getLocation(id);
		LocationModel locationModel = new LocationModel();
		if (locationDomain == null)
			return null;
		BeanUtils.copyProperties(locationDomain, locationModel);
		return locationModel;
	}

}