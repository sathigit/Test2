
package com.atpl.mmg.AandA.service.forgotPassword;

import com.atpl.mmg.AandA.model.forgotPassword.ForgotPasswordModel;

public interface ForgotPasswordService {

	public ForgotPasswordModel getCustomer(String mobileNumber) throws Exception;
	
	public ForgotPasswordModel getProfile(String mobileNumber) throws Exception;


}
