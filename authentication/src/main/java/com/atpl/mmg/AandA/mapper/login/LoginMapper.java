package com.atpl.mmg.AandA.mapper.login;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.login.LoginDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.login.LoginModel;

@Component
public class LoginMapper extends AbstractModelMapper<LoginModel, LoginDomain> {

	@Override
	public Class<LoginModel> entityType() {
		return LoginModel.class;
	}

	@Override
	public Class<LoginDomain> modelType() {
		return LoginDomain.class;
	}

}
