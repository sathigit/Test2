package com.atpl.mmg.service.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.city.CityDAO;
import com.atpl.mmg.domain.city.CityDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.city.CityMapper;
import com.atpl.mmg.model.city.CityModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("cityService")
@SuppressWarnings("unused")
public class CityServiceImpl implements CityService, Constants {

	@Autowired
	CityDAO cityDAO;

	@Autowired
	CityMapper cityMapper;

	private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

	public CityServiceImpl() {
		// constructor
	}

	/**
	 * Author:Vidya S K Modified Date: 19/2/2020 Description: Update city
	 * 
	 */
	@Override
	public String addCity(CityModel cityModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.DEBUG.ordinal(),
				" addCity in CityServiceImpl ") + JsonUtil.toJsonString(cityModel));

		CityDomain cityDomain = new CityDomain();
		BeanUtils.copyProperties(cityModel, cityDomain);
		return cityDAO.addCity(cityDomain);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getCities(int stateId, Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.DEBUG.ordinal(),
				"getCitiesByStateId in CityServiceImpl ") + JsonUtil.toJsonString(stateId));
		List<CityDomain> cities = cityDAO.getCities(0,stateId, lowerBound, upperBound);
		List<CityModel> cityModel = cityMapper.entityList(cities);
		ListDto listDto = new ListDto();
		listDto.setList(cityModel);
		listDto.setTotalSize(cityDAO.getCitiesByStateCount(stateId).getTotal());
		return listDto;
	}

	/**
	 * Author:Vidya S K Modified Date: 19/2/2020 Description: update city
	 * 
	 */
	@Override
	public String updateCity(CityModel cityModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.DEBUG.ordinal(),
				" updateCity in CityServiceImpl ") + JsonUtil.toJsonString(cityModel));

		CityDomain cityDomain = new CityDomain();
		BeanUtils.copyProperties(cityModel, cityDomain);
		return cityDAO.updateCity(cityDomain);
	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020 Description: city
	 * 
	 */
	@Override
	public CityModel getCity(int cityId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.DEBUG.ordinal(),
				" getCity in CityServiceImpl ") + JsonUtil.toJsonString(cityId));

		CityDomain cityDomain = cityDAO.getCity(cityId);
		CityModel cityModel = new CityModel();
		if (cityDomain == null)
			throw new NOT_FOUND("City not found");
		BeanUtils.copyProperties(cityDomain, cityModel);
		return cityModel;
	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020 Description: city
	 * 
	 */
	@Override
	public CityModel getCityId(String name) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.DEBUG.ordinal(),
				" getCityIdByName in CityServiceImpl ") + JsonUtil.toJsonString(name));
		CityDomain cityDomain = cityDAO.getCityId(name);
		CityModel cityModel = new CityModel();
		if (cityDomain == null)
			throw new NOT_FOUND("City not found");
		BeanUtils.copyProperties(cityDomain, cityModel);
		return cityModel;

	}

	@Override
	public String updateAlias(CityModel cityModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.DEBUG.ordinal(),
				" updateAlias in CityServiceImpl ") + JsonUtil.toJsonString(cityModel));
		CityDomain cityDomain = new CityDomain();
		BeanUtils.copyProperties(cityModel, cityDomain);
		if (null == cityModel.getName() || cityModel.getName().isEmpty())
			throw new NOT_FOUND("Please mention the cityName");
		if (null == cityModel.getAlias() || cityModel.getAlias().isEmpty())
			throw new NOT_FOUND("Please mention the city Alias Name");
		cityDomain = cityDAO.getCityId(cityDomain.getName());
		List<String> list = new ArrayList<>(cityModel.getAlias());
		String string = String.join(", ", list);
		if (null != cityDomain.getAlias()) {
			string = cityDomain.getAlias() + "," + String.join(", ", list);
			cityDomain.setAlias(string);
		} else
			cityDomain.setAlias(string);
		return cityDAO.updateAlias(cityDomain);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getAllCities(boolean getIndiaCities, Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.CITY.name(), SeverityTypes.DEBUG.ordinal(),
				" getAllCitiesBygetIndiaCities in CityServiceImpl ") + JsonUtil.toJsonString(getIndiaCities));
		List<CityDomain> cities = new ArrayList<CityDomain>();
		if (getIndiaCities) {
			cities = cityDAO.getAllCities(true, lowerBound, upperBound);
		} else
			cities = cityDAO.getAllCities(false, lowerBound, upperBound);
		List<CityModel> cityModel = cityMapper.entityList(cities);
		ListDto listDto = new ListDto();
		listDto.setList(cityModel);
		listDto.setTotalSize(cityDAO.getAllCitiesCount(getIndiaCities).getTotal());
		return listDto;
	}

}
