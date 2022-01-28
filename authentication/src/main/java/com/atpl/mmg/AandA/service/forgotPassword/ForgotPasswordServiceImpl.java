package com.atpl.mmg.AandA.service.forgotPassword;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.AandA.dao.forgotPassword.ForgotPasswordDAO;
import com.atpl.mmg.AandA.domain.forgotPassword.ForgotPasswordDomain;
import com.atpl.mmg.AandA.mapper.forgotPassword.ForgotPasswordMapper;
import com.atpl.mmg.AandA.model.forgotPassword.ForgotPasswordModel;

@Service("forgotPasswordService")
public class ForgotPasswordServiceImpl implements ForgotPasswordService {


	@Autowired
	ForgotPasswordDAO forgotPasswordDAO;

	@Autowired
	ForgotPasswordMapper forgotPasswordmapper;

	public int rand1, rand2;

	public ForgotPasswordServiceImpl() {

	}

	@Override
	public ForgotPasswordModel getCustomer(String mobileNumber) throws Exception {
		ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO.getCustomer(mobileNumber);
		ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel();
		if (forgotPasswordDomain == null)
			return null;
		BeanUtils.copyProperties(forgotPasswordDomain, forgotPasswordModel);
		return forgotPasswordModel;
	}

	@Override
	public ForgotPasswordModel getProfile(String mobileNumber) throws Exception {
		ForgotPasswordDomain forgotPasswordDomain = forgotPasswordDAO.getProfile(mobileNumber);
		ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel();
		if (forgotPasswordDomain == null)
			return null;
		BeanUtils.copyProperties(forgotPasswordDomain, forgotPasswordModel);
		return forgotPasswordModel;
	}

}
