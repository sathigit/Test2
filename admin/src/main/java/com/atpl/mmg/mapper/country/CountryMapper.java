package com.atpl.mmg.mapper.country;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.country.CountryDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.country.CountryModel;

@Component
public class CountryMapper extends AbstractModelMapper<CountryModel, CountryDomain> {
	
	@Override
	public Class<CountryModel> entityType(){
		return CountryModel.class;
	}
	
	@Override
	public Class<CountryDomain> modelType(){
		return CountryDomain.class;
	}

}
