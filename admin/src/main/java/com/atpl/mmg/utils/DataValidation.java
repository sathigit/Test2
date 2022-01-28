package com.atpl.mmg.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public static final String PASSWORD_PATTERN  = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
	private static final String ID_PATTERN = "[0-9]{8}";
	
	public boolean isValidate(String string,String pattern) {
		if(null == string)
			return false;
		return string.matches(pattern);
	}

	public boolean isEmailValid(final String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();

	}

	public boolean isIdValid(final String id) {
		Pattern pattern = Pattern.compile(ID_PATTERN);
		matcher = pattern.matcher(id);
		return matcher.matches();

	}

	public boolean isPhoneNumberValid(final String phone) {
		Pattern pattern = Pattern.compile(PHONENUMBER_PATTERN);
		matcher = pattern.matcher(phone);
		return matcher.matches();
	}

	//
	// public boolean isPhoneNumberValid(CharSequence phone) {
	// Pattern pattern = Pattern.compile(PHONENUMBER_PATTERN);
	// matcher = pattern.matcher(phone);
	// return matcher.matches();
	// }
	public boolean isNameValid(final String name) {
		Pattern pattern = Pattern.compile(FIRSTNAME_PATTERN);
		matcher = pattern.matcher(name);
		return matcher.matches();

	}

	public boolean isAddressValid(final String address) {
		Pattern pattern = Pattern.compile(ADDRESS_PATTERN);
		matcher = pattern.matcher(address);
		return matcher.matches();

	}

	public boolean isAccountNoValid(final String accountNo) {
		Pattern pattern = Pattern.compile(BANKACCOUNTNUMBER_PATTERN);
		matcher = pattern.matcher(accountNo);
		return matcher.matches();

	}

	public boolean isIFSCCodeValid(final String ifsc) {
		Pattern pattern = Pattern.compile(BANKIFSCCODE_PATTERN);
		matcher = pattern.matcher(ifsc);
		return matcher.matches();

	}

	public boolean isDOBValid(final String dob) {
		Pattern pattern = Pattern.compile(DOB_PATTERN);
		matcher = pattern.matcher(dob);
		return matcher.matches();

	}

	public boolean isAadharNoValid(final String aadharNo) {
		Pattern pattern = Pattern.compile(AADHARNUMBER_PATTERN);
		matcher = pattern.matcher(aadharNo);
		return matcher.matches();

	}

	public boolean isPANNoValid(final String pan) {
		Pattern pattern = Pattern.compile(PANNUMBER_PATTERN);
		matcher = pattern.matcher(pan);
		return matcher.matches();

	}

	public boolean isDLNoValid(final String dl) {
		Pattern pattern = Pattern.compile(DLNUMBER_PATTERN);
		matcher = pattern.matcher(dl);
		return matcher.matches();

	}

	// public boolean isPhoneNumberValid(BigInteger mobileNumber) {
	// Pattern pattern = Pattern.compile(PHONENUMBER_PATTERN);
	// String matcher = pattern.toString();
	// return matcher.matches(matcher);
	// }

}
