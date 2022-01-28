package com.atpl.mmg.AandA.mapper.forgotPassword;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.forgotPassword.ForgotPasswordDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.forgotPassword.ForgotPasswordModel;

@Component
public class ForgotPasswordMapper extends AbstractModelMapper<ForgotPasswordModel,ForgotPasswordDomain>
{

	@Override
	public Class<ForgotPasswordModel> entityType() {
		return ForgotPasswordModel.class;
	}

	@Override
	public Class<ForgotPasswordDomain> modelType() {
	
	return	ForgotPasswordDomain.class;
	}

}
