package com.atpl.mmg.AandA.service.login;

import java.util.List;

import com.atpl.mmg.AandA.model.login.LoginModel;

public interface LoginServiceV2 {
	
	public LoginModel authenticate(LoginModel loginModel) throws Exception;

	public LoginModel getTotalRole(String profileId) throws Exception;
	
	public List<LoginModel>  getRoleNames(String profileId) throws Exception;
	
	public LoginModel getAccessToken(LoginModel loginModel) throws Exception;
	
	public String deleteToken(String accessToken,boolean isDriverActive) throws Exception;
}
