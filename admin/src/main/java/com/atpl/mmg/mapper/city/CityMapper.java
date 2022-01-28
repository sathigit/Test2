package com.atpl.mmg.mapper.city;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.city.CityDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.city.CityModel;

@Component
public class CityMapper extends AbstractModelMapper<CityModel, CityDomain>  {

	@Override
	public Class<CityModel> entityType() {
		return CityModel.class;
	}

	@Override
	public Class<CityDomain> modelType() {
		return CityDomain.class;
	}

}
