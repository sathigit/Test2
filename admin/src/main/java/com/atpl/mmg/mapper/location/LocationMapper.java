package com.atpl.mmg.mapper.location;
import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.location.LocationDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.location.LocationModel;

@Component
public class LocationMapper extends AbstractModelMapper<LocationModel, LocationDomain> {

	@Override
	public Class<LocationModel> entityType() {
		
		return LocationModel.class;
	}

	@Override
	public Class<LocationDomain> modelType() {
		
		return LocationDomain.class;
	}

}

