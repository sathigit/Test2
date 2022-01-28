package com.atpl.mmg.dao.state;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.state.StateDomain;


public interface StateDAO {
	
	public String addState(StateDomain stateDomain) throws Exception;
	
	public List<StateDomain> getStates(int countryId,int lowerBound,int upperBound) throws Exception;
	
	public String UpdateState(StateDomain stateDomain)throws Exception;
	
	public StateDomain getState(int stateId) throws Exception;
	
	public StateDomain getState(String stateName) throws Exception;
	
	public List<StateDomain> getStates(int lowerBound,int upperBound) throws Exception;

	public DashboardDomain getStatesCount() throws Exception;
	
	public DashboardDomain getStatesCountonCountryId(int countryId) throws Exception;

}
