package com.atpl.mmg.AandA.controller.otp;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.domain.otp.Otp;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.service.otp.OtpService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class OTPController {

	@Autowired
	OtpService otpService;
	
	@RequestMapping(value = "/otp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveOtp(@RequestBody Otp otp) throws Exception {
		return prepareSuccessResponse(otpService.save(otp));
	}
	
	@RequestMapping(value = "/otp/{mobileNumber}/{otp}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getOtp(@PathVariable("mobileNumber") String mobileNumber,@PathVariable("otp") String otp) throws Exception {
		return prepareSuccessResponse(otpService.getOtp(mobileNumber, String.valueOf(otp)));
	}
}
