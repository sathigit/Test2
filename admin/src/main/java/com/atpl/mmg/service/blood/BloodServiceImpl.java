package com.atpl.mmg.service.blood;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.blood.BloodDAO;
import com.atpl.mmg.domain.bank.BankDomain;
import com.atpl.mmg.domain.blood.BloodDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.blood.BloodMapper;
import com.atpl.mmg.model.blood.BloodModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("bloodService")
public class BloodServiceImpl implements BloodService, Constants {

	@Autowired
	BloodDAO bloodDAO;

	@Autowired
	BloodMapper bloodMapper;

	private static final Logger logger = LoggerFactory.getLogger(BloodServiceImpl.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getBloodGroup(Map<String, String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BLOOD.name(),
				SeverityTypes.DEBUG.ordinal(), "Exception getBloodGroupList in BloodServiceImpl "));
		List<BloodDomain> bloodDomain = bloodDAO.getBloodGroup(lowerBound, upperBound);
		List<BloodModel>  bloodModel = bloodMapper.entityList(bloodDomain);
		ListDto listDto = new ListDto();
		listDto.setList(bloodModel);
		listDto.setTotalSize(bloodDAO.getBloodGroupCount().getTotal());
		return listDto;
	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020
	 * 
	 */
	@Override
	public BloodModel getBloodGroupById(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BLOOD.name(),
				SeverityTypes.DEBUG.ordinal(), "Exception getBloodGroupById in BloodServiceImpl "
						+ JsonUtil.toJsonString(id)));
		BloodDomain BloodDomain = bloodDAO.getBloodGroupById(id);
		BloodModel BloodModel = new BloodModel();
		if (BloodDomain == null)
			throw new NOT_FOUND("Blood not found");
		BeanUtils.copyProperties(BloodDomain, BloodModel);
		return BloodModel;
	}

}
