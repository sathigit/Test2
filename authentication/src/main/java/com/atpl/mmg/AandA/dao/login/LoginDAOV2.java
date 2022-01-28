package com.atpl.mmg.AandA.dao.login;

import java.util.List;

import com.atpl.mmg.AandA.domain.login.LoginDomain;

public interface LoginDAOV2 {

	public LoginDomain authenticate(LoginDomain logindomain) throws Exception;
	
	public LoginDomain getTotalRole(String profileId) throws Exception;
	
	public List<LoginDomain>  getRoleNames(String profileId) throws Exception;
}
