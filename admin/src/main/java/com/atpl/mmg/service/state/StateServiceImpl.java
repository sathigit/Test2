package com.atpl.mmg.service.state;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.state.StateDAO;
import com.atpl.mmg.domain.organization.OrganizationDomain;
import com.atpl.mmg.domain.state.StateDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.state.StateMapper;
import com.atpl.mmg.model.organization.OrganizationModel;
import com.atpl.mmg.model.state.StateModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("stateService")
@SuppressWarnings("rawtypes")
public class StateServiceImpl implements StateService, Constants {

	@Autowired
	StateDAO stateDAO;

	@Autowired
	StateMapper stateMapper;

	private static final Logger logger = LoggerFactory.getLogger(StateServiceImpl.class);

	public StateServiceImpl() {
		// constructor
	}

	/**
	 * Author:Vidya S K Modified Date: 19/2/2020 Description: save state
	 * 
	 */
	@Override
	public String addState(StateModel stateModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
				"addState in StatesServiceImpl ")+ JsonUtil.toJsonString(stateModel));

		StateDomain stateDomain = new StateDomain();
		BeanUtils.copyProperties(stateModel, stateDomain);
		return stateDAO.addState(stateDomain);

	}

	@SuppressWarnings("unchecked")
	@Override
	public ListDto getStates(int countryId,Map<String,String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
				"getStatesOnCountry in StatesServiceImpl "));
		List<StateDomain> stateDomain = stateDAO.getStates(countryId,lowerBound, upperBound);
		List<StateModel> stateModel =stateMapper.entityList(stateDomain);
		ListDto listDto = new ListDto();
		listDto.setList(stateModel);
		totalSize = stateDAO.getStatesCountonCountryId(countryId).getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	/**
	 * Author:Vidya S K Modified Date: 19/2/2020 Description: update state
	 * 
	 */
	@Override
	public String updateState(StateModel stateModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
				"updateState in StatesServiceImpl ")+ JsonUtil.toJsonString(stateModel));

		StateDomain stateDomain = new StateDomain();
		BeanUtils.copyProperties(stateModel, stateDomain);
		return stateDAO.UpdateState(stateDomain);

	}

	@Override
	public StateModel getState(int stateId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
				"getStateByStateId in StatesServiceImpl ")+ JsonUtil.toJsonString(stateId));

		StateDomain stateDomain = stateDAO.getState(stateId);
		StateModel stateModel = new StateModel();
		if (stateDomain == null)
			throw new NOT_FOUND("State not found");
		BeanUtils.copyProperties(stateDomain, stateModel);
		return stateModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListDto getStates(Map<String,String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
				"getStates in StatesServiceImpl "));
		List<StateDomain> stateDomain = stateDAO.getStates(lowerBound, upperBound);
		List<StateModel> stateModel =stateMapper.entityList(stateDomain);
		ListDto listDto = new ListDto();
		listDto.setList(stateModel);
		totalSize = stateDAO.getStatesCount().getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;

	}

	@Override
	public StateModel getState(String stateName) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.STATE.name(), SeverityTypes.DEBUG.ordinal(),
				"getStateByName in StatesServiceImpl ")+ JsonUtil.toJsonString(stateName));
		StateDomain stateDomain = stateDAO.getState(stateName);
		StateModel stateModel = new StateModel();
		if (stateDomain == null)
			throw new NOT_FOUND("State not found");
		BeanUtils.copyProperties(stateDomain, stateModel);
		return stateModel;
	}

}
