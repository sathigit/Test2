package com.atpl.mmg.AandA.controller.forgotPassword;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.service.forgotPassword.ForgotPasswordService;

@RestController
@RequestMapping("v1")
@SuppressWarnings("rawtypes")
@Deprecated
public class ForgotPasswordController implements Constants {

	@Autowired
	ForgotPasswordService forgotPasswordService;

	@RequestMapping(value = "/getMobileNumber/{mobileNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getprofile(@PathVariable("mobileNumber") String mobileNumber)
			throws Exception {
		return prepareSuccessResponse(forgotPasswordService.getCustomer(mobileNumber));
	}

	@RequestMapping(value = "/profile/mobileNumber/{mobileNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getMobileNumberByProfile(@PathVariable("mobileNumber") String mobileNumber)
			throws Exception {
		return prepareSuccessResponse(forgotPasswordService.getProfile(mobileNumber));
	}
}
