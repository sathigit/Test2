package com.atpl.mmg.service.weight;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.dao.weight.WeightDAO;
import com.atpl.mmg.domain.lead.LeadDomain;
import com.atpl.mmg.domain.weight.WeightDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.weight.WeightMapper;
import com.atpl.mmg.model.lead.LeadModel;
import com.atpl.mmg.model.weight.WeightModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("WeightService")
public class WeightServiceImpl implements WeightService {

	@Autowired
	WeightDAO weightDAO;

	@Autowired
	WeightMapper weightMapper;

	private static final Logger logger = LoggerFactory.getLogger(WeightServiceImpl.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ListDto getWeight(Map<String,String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WEIGHT.name(), SeverityTypes.DEBUG.ordinal(),
				"getWeight in WeightServiceImpl "));
		List<WeightDomain> weightDomain = weightDAO.getWeight(lowerBound, upperBound);
		List<WeightModel> weightModel = weightMapper.entityList(weightDomain);
		ListDto listDto = new ListDto();
		listDto.setList(weightModel);
		totalSize = weightDAO.getWeightCount().getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;

	}

	@Override
	public WeightModel getWeight(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WEIGHT.name(), SeverityTypes.DEBUG.ordinal(),
				"getWeightById in WeightServiceImpl ")+ JsonUtil.toJsonString(id));
		WeightDomain organizationDomain = weightDAO.getWeight(id);
		WeightModel organizationModel = new WeightModel();
		if (organizationDomain == null)
			throw new NOT_FOUND("weight not found");
		BeanUtils.copyProperties(organizationDomain, organizationModel);
		return organizationModel;
	}

	@Override
	public WeightModel getWeight(String weight) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.WEIGHT.name(), SeverityTypes.DEBUG.ordinal(),
				"getWeightdetails in WeightServiceImpl ")+ JsonUtil.toJsonString(weight));
		WeightDomain organizationDomain = weightDAO.getWeight(weight);
		WeightModel organizationModel = new WeightModel();
		if (organizationDomain == null)
			throw new NOT_FOUND("weight not found");
		BeanUtils.copyProperties(organizationDomain, organizationModel);
		return organizationModel;
	}

}
