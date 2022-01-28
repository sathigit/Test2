package com.atpl.mmg.service.state;

import java.util.Map;

import com.atpl.mmg.model.state.StateModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface StateService {

	public String addState(StateModel stateModel) throws Exception;

	public ListDto getStates(int countryId,Map<String,String> reqParam) throws Exception;

	public String updateState(StateModel stateModel) throws Exception;

	public StateModel getState(int stateId) throws Exception;
	
	public StateModel getState(String stateId) throws Exception;

	public ListDto getStates(Map<String,String> reqParam) throws Exception;
}
