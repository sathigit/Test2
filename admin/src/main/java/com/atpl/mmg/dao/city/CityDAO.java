package com.atpl.mmg.dao.city;

import java.util.List;

import com.atpl.mmg.domain.city.CityDomain;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
@SuppressWarnings("rawtypes")
public interface CityDAO {
	
	public String addCity(CityDomain cityDomain) throws Exception; 
	
	public List<CityDomain> getCities(int countryId,int stateId ,int lowerBound, int upperBound) throws Exception ;
	
	public DashboardDomain getCitiesByStateCount(int stateId) throws Exception ;

	public String updateCity(CityDomain cityDomain)throws Exception;
	
	public CityDomain getCity(int cityId) throws Exception;
	
	public CityDomain getCityId(String name) throws Exception;
	
	public String updateAlias(CityDomain cityDomain) throws Exception;
	
	public List<CityDomain> getAllCities(boolean getCities,int lowerBound,int upperBound) throws Exception;
		
	public DashboardDomain getAllCitiesCount(boolean getCities) throws Exception;
}