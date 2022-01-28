package com.atpl.mmg.dao.country;

import java.util.List;

import com.atpl.mmg.domain.country.CountryDomain;
import com.atpl.mmg.domain.dashboard.DashboardDomain;

@SuppressWarnings("rawtypes")
public interface CountryDAO {

	public String addCountry(CountryDomain adminDomain) throws Exception;

	public List<CountryDomain> getCountry(int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getCountryCount() throws Exception;

	public CountryDomain getCountry(int CountryId) throws Exception;

	public String UpdateCountry(CountryDomain countryDomain) throws Exception;

	public String DeleteCountry(int countryId) throws Exception;

	public CountryDomain getCountry(String countryname) throws Exception;

}
