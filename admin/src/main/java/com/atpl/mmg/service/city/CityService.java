package com.atpl.mmg.service.city;

import java.util.Map;

import com.atpl.mmg.model.city.CityModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface CityService {
	
	public String addCity(CityModel cityModel) throws Exception;
	
	public ListDto getCities(int stateId,Map<String,String> reqParam) throws Exception;
	
	public String updateCity(CityModel  cityModel)throws Exception;
	
	public CityModel getCity(int  cityId) throws Exception;
	
	public CityModel getCityId(String name) throws Exception;
	
	public String updateAlias(CityModel CityModel) throws Exception;
	
	public ListDto getAllCities(boolean getCities,Map<String,String> reqParam) throws Exception;

}