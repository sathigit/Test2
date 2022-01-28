package com.atpl.mmg.AandA.dao.login;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.AandA.domain.login.LoginDomain;

public interface LoginDAO {

	public String deleteProle(int proleId) throws Exception;

	public LoginDomain authenticate(LoginDomain logindomain) throws Exception;
	
	public LoginDomain customerAuthenticate(LoginDomain logindomain) throws Exception;
	
	public LoginDomain mobileauthenticate(LoginDomain logindomain) throws Exception;

	public List<LoginDomain> getPermission(int roleId) throws Exception;

	public LoginDomain getMobileNumberProfile(BigInteger mobileNumber) throws Exception;

	public String savePermission(LoginDomain loginDomain) throws Exception;

	public LoginDomain roleCount(int id) throws Exception;

	public List<LoginDomain> getRoleName(int id) throws Exception;

	public LoginDomain rolePath(int id) throws Exception;
	
	public LoginDomain getProle(int id, int roleId) throws Exception ;
	
}
