package com.atpl.mmg.mapper.state;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.state.StateDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.state.StateModel;

@Component
public class StateMapper extends AbstractModelMapper<StateModel, StateDomain> {

	@Override
	public Class<StateModel> entityType() {
		return StateModel.class;
	}

	@Override
	public Class<StateDomain> modelType() {
		return StateDomain.class;
	}
	
	

}
