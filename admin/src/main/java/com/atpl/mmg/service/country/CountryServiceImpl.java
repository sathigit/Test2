package com.atpl.mmg.service.country;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.country.CountryDAO;
import com.atpl.mmg.domain.country.CountryDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.country.CountryMapper;
import com.atpl.mmg.model.country.CountryModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("countryService")
@SuppressWarnings("rawtypes")
public class CountryServiceImpl implements CountryService, Constants {

	@Autowired
	CountryDAO countryDAO;

	@Autowired
	CountryMapper countryMapper;

	private static final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

	public CountryServiceImpl() {
		// constructor
	}

	/**
	 * Author:Vidya S K Modified Date: 19/2/2020 Description: Save Country
	 * 
	 */
	@Override
	public String addCountry(CountryModel countryModel) throws Exception {
		CountryDomain countryDomain = new CountryDomain();
		BeanUtils.copyProperties(countryModel, countryDomain);
	 return countryDAO.addCountry(countryDomain);
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public ListDto getCountry(Map<String, String> reqParam) throws Exception {
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
				"Exception getCountry in CountryServiceImpl "));
		List<CountryDomain> countryDomain = countryDAO.getCountry(lowerBound,upperBound);

		List<CountryModel> countryModel = countryMapper.entityList(countryDomain);
		ListDto listDto = new ListDto();
		listDto.setList(countryModel);
		totalSize = countryDAO.getCountryCount().getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020
	 * 
	 */

	@Override
	public CountryModel getCountry(int countryId) throws Exception {
		CountryDomain countryDomain = countryDAO.getCountry(countryId);
		CountryModel countryModel = new CountryModel();
		if (countryDomain == null)
			throw new NOT_FOUND("Country not found");
		BeanUtils.copyProperties(countryDomain, countryModel);
		return countryModel;
	}

	/**
	 * Author:Vidya S K Modified Date: 19/2/2020 Description: update Country
	 * 
	 */
	@Override
	public String UpdateCountry(CountryModel countryModel) throws Exception {
		CountryDomain countryDomain = new CountryDomain();
		BeanUtils.copyProperties(countryModel, countryDomain);
		return countryDAO.UpdateCountry(countryDomain);
	}

	/**
	 * Author:Vidya S K Modified Date: 19/2/2020 Description: Delete Country
	 * 
	 */
	@Override
	public String DeleteCountry(int countryId) throws Exception {
		CountryDomain countryDomain = new CountryDomain();
		CountryModel countryModel = new CountryModel();
		BeanUtils.copyProperties(countryModel, countryDomain);
		return countryDAO.DeleteCountry(countryId);
	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020
	 * 
	 */
	@Override
	public CountryModel getCountry(String countryname) throws Exception {
		CountryDomain countryDomain = countryDAO.getCountry(countryname);
		CountryModel countryModel = new CountryModel();
		if (countryDomain == null)
			throw new NOT_FOUND("Country not found");
		BeanUtils.copyProperties(countryDomain, countryModel);
		return countryModel;
	}

}
