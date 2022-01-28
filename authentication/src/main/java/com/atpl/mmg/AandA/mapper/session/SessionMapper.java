package com.atpl.mmg.AandA.mapper.session;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.session.SessionDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.session.SessionModel;
@Component
public class SessionMapper extends AbstractModelMapper<SessionModel, SessionDomain> {

	@Override
	public Class<SessionModel> entityType() {
		// TODO Auto-generated method stub
		return SessionModel.class;
	}

	@Override
	public Class<SessionDomain> modelType() {
		// TODO Auto-generated method stub
		return SessionDomain.class;
	}

	

}
