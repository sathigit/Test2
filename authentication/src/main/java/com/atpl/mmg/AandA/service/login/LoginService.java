package com.atpl.mmg.AandA.service.login;

import java.util.List;

import com.atpl.mmg.AandA.model.login.LoginModel;

public interface LoginService {
	
	public LoginModel authenticate(LoginModel loginModel) throws Exception;

	public List<LoginModel> getCustomerPermission(int roleId) throws Exception;

	public List<LoginModel> getDriverPermission(int roleId) throws Exception;

	public List<LoginModel> getFleetOperatorPermission(int roleId) throws Exception;

	public List<LoginModel> getOwnerPermission(int roleId) throws Exception;

	public String logout(LoginModel loginModel) throws Exception;

	public String savePermission(LoginModel loginModel) throws Exception;

	public LoginModel roleCount(int id) throws Exception;

	public List<LoginModel> getRoleName(int id) throws Exception;
	
	public LoginModel rolePath(int id) throws Exception;
	
	public String deleteProle(int proleId) throws Exception ;
	
	public LoginModel getProle(int id, int roleId) throws Exception;
	
	public LoginModel getAccessToken(LoginModel loginModel) throws Exception ;
	
	public String deleteToken(String accessToken,boolean isDriverActive) throws Exception;
}
