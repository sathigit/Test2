package com.atpl.mmg.AandA.service.otp;

import com.atpl.mmg.AandA.domain.otp.Otp;

public interface OtpService {
	public Otp save(Otp otp) throws Exception;

	public Otp update(Otp otp) throws Exception;

	public Otp getOtp(String phoneNumber) throws Exception;
	
	public Otp getOtp(String phoneNumber, String otp) throws Exception;

}
