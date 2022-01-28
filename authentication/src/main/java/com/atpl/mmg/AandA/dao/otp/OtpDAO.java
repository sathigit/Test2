package com.atpl.mmg.AandA.dao.otp;

import com.atpl.mmg.AandA.domain.otp.Otp;

public interface OtpDAO {

	public Otp save(Otp otp) throws Exception;

	public Otp update(Otp otp) throws Exception;

	public Otp getOtp(String phoneNumber,String email) throws Exception;
	
	public String deleteOtp(int id) throws Exception;
	
	public String updateIsChecked(int id) throws Exception;

}