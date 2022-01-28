package com.atpl.mmg.AandA.service.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.AandA.dao.otp.OtpDAO;
import com.atpl.mmg.AandA.domain.otp.Otp;
import com.atpl.mmg.AandA.exception.MmgRestException.INVALID_OTP;

@Service("OtpService")
public class OtpServiceImpl implements OtpService{

	@Autowired
	OtpDAO otpDao;
	
	@Override
	public Otp save(Otp otp) throws Exception {
		return otpDao.save(otp);
	}

	@Override
	public Otp update(Otp otp) throws Exception {
		return otpDao.update(otp);
	}

	@Override
	public Otp getOtp(String phoneNumber) throws Exception {
		return otpDao.getOtp(phoneNumber,null);
	}

	@Override
	public Otp getOtp(String phoneNumber, String otp) throws Exception {
		Otp otpObj = otpDao.getOtp(phoneNumber,null);
		if (null != otpObj) {
			if(otpObj.getOtp().equalsIgnoreCase(otp))
				return otpObj;
			else
				throw new INVALID_OTP();
		}
		else
			throw new INVALID_OTP();
	}
	
	

}
