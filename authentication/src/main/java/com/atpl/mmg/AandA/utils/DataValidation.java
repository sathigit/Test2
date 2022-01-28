package com.atpl.mmg.AandA.utils;

import java.util.regex.Matcher;

import org.springframework.stereotype.Component;
@Component
public class DataValidation {
	
	private Matcher matcher;

	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";	
	public static final String PHONENUMBER_PATTERN = "(0/91)?[6-9][0-9]{9}";
	public static final String FIRSTNAME_PATTERN = "^[\\p{L} .'-]+$";
	public static final String ADDRESS_PATTERN = "^[#.0-9a-zA-Z\\s,-]+$";
	public static final String BANKACCOUNTNUMBER_PATTERN = "[0-9]{9,18}";
	public static final String BANKIFSCCODE_PATTERN = "^[A-Za-z]{4}0[A-Z0-9a-z]{6}$";
	public static final String DOB_PATTERN = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";
	public static final String AADHARNUMBER_PATTERN = "^[2-9]{1}[0-9]{11}$";
	public static final String PANNUMBER_PATTERN = "[A-Z]{5}\\d{4}[A-Z]{1}";
	public static final String DLNUMBER_PATTERN = "^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$";
	public static final String PASSWORD_PATTERN  = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#@$!%*?&])[A-Za-z\\d@#$!%*?&]{8,}$";
	public static final String GSTNUMBER_PATTERN = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}";
	
	public boolean isValidate(String string,String pattern) {
		if(null == string)
			return false;
		return string.matches(pattern);
	}
	
}
