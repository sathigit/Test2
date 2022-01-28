package com.atpl.mmg.service.driverType;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.dao.driverType.DriverTypeDAO;
import com.atpl.mmg.domain.driverType.DriverTypeDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.driverType.DriverTypeMapper;
import com.atpl.mmg.model.driverType.DriverTypeModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;


@Service("driverTypeService")
public class DriverTypeServiceImpl implements DriverTypeService {

	@Autowired
	DriverTypeDAO driverTypeDAO;
	
	@Autowired
	DriverTypeMapper driverTypeMapper;
	
	
	private static final Logger logger = LoggerFactory.getLogger(DriverTypeServiceImpl.class);

	public DriverTypeServiceImpl() {
		// constructor
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getDriverType(Map<String, String> reqParam) throws Exception {
		
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.DRIVER_TYPE.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception getDriverType in DriverTypeServiceImpl "));
		List<DriverTypeDomain> driverDomain = driverTypeDAO.getDriverType(lowerBound,upperBound);
		List<DriverTypeModel> driverTypeModel = driverTypeMapper.entityList(driverDomain);
		ListDto listDto = new ListDto();
		listDto.setList(driverTypeModel);
		totalSize = driverTypeDAO.getDriverTypeCount().getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	@Override
	public DriverTypeModel getDriverTypeById(int id) throws Exception {
		DriverTypeDomain driverDomain = driverTypeDAO.getDriverTypeById(id);
		DriverTypeModel driverModel = new DriverTypeModel();
		if (driverDomain == null)
			throw new NOT_FOUND("DriverType not found");
		BeanUtils.copyProperties(driverDomain, driverModel);
		return driverModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getLicenceCategory(Map<String, String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.DRIVER_TYPE.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception getDriverType in DriverTypeServiceImpl "));
		List<DriverTypeDomain> driverDomain = driverTypeDAO.getLicenceCategory(lowerBound,upperBound);
		List<DriverTypeModel> driverTypeModel = driverTypeMapper.entityList(driverDomain);
		ListDto listDto = new ListDto();
		listDto.setList(driverTypeModel);
		totalSize = driverTypeDAO.getLicenceCategoryCount().getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	@Override
	public DriverTypeModel getLicenceCategoryById(int id) throws Exception {
		DriverTypeDomain driverDomain = driverTypeDAO.getLicenceCategoryById(id);
		DriverTypeModel driverModel = new DriverTypeModel();
		if (driverDomain == null)
			throw new NOT_FOUND("LicenceCategory not found");
		BeanUtils.copyProperties(driverDomain, driverModel);
		return driverModel;
	}

}
