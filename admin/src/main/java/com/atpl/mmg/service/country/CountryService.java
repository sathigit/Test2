package com.atpl.mmg.service.country;

import java.util.Map;

import com.atpl.mmg.model.country.CountryModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface CountryService {
	
public String addCountry(CountryModel countryModel) throws Exception;
	
	public ListDto getCountry(Map<String, String> reqParam) throws Exception;
	
	public CountryModel getCountry(int  countryId) throws Exception;
	
	public CountryModel getCountry(String countryname) throws Exception;
	
	public String UpdateCountry(CountryModel  countryModel)throws Exception;
	
	public String DeleteCountry(int countryId)throws Exception;

}
