package com.atpl.mmg.AandA.dao.forgotPassword;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.AandA.domain.forgotPassword.ForgotPasswordDomain;

public interface ForgotPasswordDAO {

	public ForgotPasswordDomain getCustomer(String mobileNumber) throws Exception;

	public ForgotPasswordDomain getProfile(String mobileNumber) throws Exception;


	public ForgotPasswordDomain getCustomerEmailId(String emailId) throws Exception;

	public ForgotPasswordDomain getprofileByEmail(String emailId) throws Exception;

	public ForgotPasswordDomain getCustomerByMobileNumberOrEmailId(String mobileNumber, String emailId)
			throws Exception;

	public ForgotPasswordDomain getProfileByMobileNumberOrEmailId(String mobileNumber, String emailId,int roleId)
			throws Exception;
	
	public List<ForgotPasswordDomain> getProfileByMobileNumberOrEmailId(BigInteger mobileNumber, String emailId)
			throws Exception;
	
	public ForgotPasswordDomain getProfileByEmailId(String emailId,int roleId)
			throws Exception;
	
	public ForgotPasswordDomain getProfileByMobileNumber(BigInteger mobileNumber,int roleId) throws Exception;
}
