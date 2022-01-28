package com.atpl.mmg.AandA.exception;

import static org.apache.http.HttpStatus.SC_OK;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpStatus;

/**
 *
 * @author Raghu M
 * @since 2020-02-09
 */

public class MmgRestException extends RuntimeException {
	private static final long serialVersionUID = -977217199574702703L;

	public static int INTERNAL_ERROR_CODE = -1;
	protected int httpErrorCode = SC_OK;
	protected int errorCode = Integer.MAX_VALUE;

	protected String exReason;

	protected Map<String, Object> meta;
	protected Map<String, String> dictionary;

	public MmgRestException() {
		super();
		this.meta = new LinkedHashMap<String, Object>();
	}

	public MmgRestException(String message) {
		super(message);
		this.meta = new LinkedHashMap<String, Object>();
	}

	public MmgRestException(String message, Throwable t) {
		super(message, t);
		this.meta = new LinkedHashMap<String, Object>();
	}

	public MmgRestException(String message, Throwable t, int errCode) {
		this(message, t);
		errorCode = errCode;
	}

	public MmgRestException(String message, Throwable t, int errCode, int httpErrCode) {
		this(message, t);
		errorCode = errCode;
		httpErrorCode = httpErrCode;
	}

	public MmgRestException(int errCode, int httpErrCode, String message) {
		this(message);
		errorCode = errCode;
		httpErrorCode = httpErrCode;
	}

	public MmgRestException(int errCode, int httpErrCode, String message, Throwable t) {
		this(message, t);
		errorCode = errCode;
		httpErrorCode = httpErrCode;
	}

	public String getExReason() {
		return exReason;
	}

	public void setExReason(String exReason) {
		this.exReason = exReason;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getHttpErrorCode() {
		return httpErrorCode;
	}

	public void setHttpErrorCode(int httpErrorCode) {
		this.httpErrorCode = httpErrorCode;
	}

	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	public Map<String, String> getDictionary() {
		return dictionary;
	}

	public void setDictionary(Map<String, String> dictionary) {
		this.dictionary = dictionary;
	}

	/**
	 * Common Exceptions
	 */
	public static final class BACKEND_SERVER_ERROR extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public BACKEND_SERVER_ERROR() {
			super(1000, HttpStatus.SC_INTERNAL_SERVER_ERROR, String.format("Backend Server Error, Please Retry."));
		}
	}

	public static final class BACKEND_SERVER_READ_TIMEOUT extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public BACKEND_SERVER_READ_TIMEOUT() {
			super(1001, HttpStatus.SC_GATEWAY_TIMEOUT,
					String.format("Backend Server read timeout Error, Please Retry."));
		}
	}

	public static final class ACCESS_DENIED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public ACCESS_DENIED() {
			super(1002, HttpStatus.SC_UNAUTHORIZED, String.format("Access denied Error"));
		}
	}

	public static final class FORBIDDEN extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public FORBIDDEN() {
			super(1003, HttpStatus.SC_FORBIDDEN, String.format("Forbidden Error"));
		}
	}

	public static final class SC_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public SC_NOT_FOUND() {
			super(1004, HttpStatus.SC_NOT_FOUND, String.format("Not Found"));
		}
	}

	public static final class BACKEND_PARSE_ERROR extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public BACKEND_PARSE_ERROR() {
			super(1005, HttpStatus.SC_INTERNAL_SERVER_ERROR, String.format("Backend parse Error"));
		}
	}

	public static final class SAVE_FAILED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public SAVE_FAILED(String message) {
			super(1006, HttpStatus.SC_NOT_FOUND, message);
		}
	}

	public static final class UPDATE_FAILED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public UPDATE_FAILED(String message) {
			super(1007, HttpStatus.SC_BAD_REQUEST, message);
		}
	}

	public static final class COUNT_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public COUNT_NOT_FOUND(String message) {
			super(1008, HttpStatus.SC_NOT_FOUND, message);
		}
	}

	public static final class NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public NOT_FOUND(String message) {
			super(1009, HttpStatus.SC_NOT_FOUND, message);
		}
	}

	public static final class DELETE_FAILED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public DELETE_FAILED(String message) {
			super(1010, HttpStatus.SC_NOT_FOUND, message);
		}
	}

	/*
	 * Error code 1011, 1012 and 1013 is used in MMGFilter as it is hard coded there
	 */

	/**
	 * Password Exceptions : The Error codes can be used to handle in App or website
	 * Profile Error code range - 2001 to 2015
	 */
	public static final class PASSWORD_PATTERN_NOT_MATCHED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PASSWORD_PATTERN_NOT_MATCHED() {
			super(2001, HttpStatus.SC_NOT_FOUND, String.format(
					"Password does Not Match. Password pattern should be minimum of eight characters, at least one uppercase letter, one lowercase letter,one number and one special symbol"));
		}
	}

	public static final class PASSWORD_MISMATCH extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PASSWORD_MISMATCH() {
			super(2002, HttpStatus.SC_NOT_FOUND, String.format("Please make sure your passwords match"));
		}
	}

	public static final class OLD_PASSWORD__MISMATCH extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public OLD_PASSWORD__MISMATCH() {
			super(2003, HttpStatus.SC_NOT_FOUND, String.format("Invalid Old Password"));
		}
	}

	public static final class PASSWORD_NOT_UPDATED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PASSWORD_NOT_UPDATED() {
			super(2004, HttpStatus.SC_NOT_FOUND, String.format("Unable to update password"));
		}
	}

	public static final class OLD_PASSWORD_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public OLD_PASSWORD_NOT_FOUND() {
			super(2005, HttpStatus.SC_NOT_FOUND, String.format("Please enter old password"));
		}
	}

	/**
	 * Profile Exceptions : The Error codes can be used to handle in App or website
	 * Profile Error code range - 2016 to 2050
	 */
	public static final class PROFILE_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PROFILE_NOT_FOUND() {
			super(2017, HttpStatus.SC_NOT_FOUND, String.format("Profile not found"));
		}
	}

	public static final class CUSTOMER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public CUSTOMER_ALREADY_EXIST(String phoneNumber) {
			super(2018, HttpStatus.SC_NOT_FOUND,
					String.format("User already exist with mobile number "+ phoneNumber));
		}

		public CUSTOMER_ALREADY_EXIST() {
			super(2019, HttpStatus.SC_NOT_FOUND, String.format("User already exist with mobile number or email"));
		}
	}

	public static final class FIRSTNAME_PATTERN_NOT_MATCH extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public FIRSTNAME_PATTERN_NOT_MATCH() {
			super(2020, HttpStatus.SC_NOT_FOUND, String.format("Please specify a Valid First name"));
		}
	}

	public static final class EMAILID_PATTERN_NOT_MATCH extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public EMAILID_PATTERN_NOT_MATCH() {
			super(2021, HttpStatus.SC_NOT_FOUND, String.format("Please specify a Valid EmailId"));
		}
	}

	public static final class USER_NOT_EXISTS extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public USER_NOT_EXISTS() {
			super(2023, HttpStatus.SC_NOT_FOUND, String.format("User does not exists"));
		}

		public USER_NOT_EXISTS(String userName) {
			super(2022, HttpStatus.SC_NOT_FOUND,
					String.format("User does not exist with mobile number/emailId", userName));
		}
	}

	public static final class STATE_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public STATE_NOT_FOUND() {
			super(2023, HttpStatus.SC_NOT_FOUND, String.format("State not found"));
		}
	}

	public static final class CITY_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public CITY_NOT_FOUND() {
			super(2024, HttpStatus.SC_NOT_FOUND, String.format("City not found"));
		}
	}

	public static final class COUNTRY_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public COUNTRY_NOT_FOUND() {
			super(2025, HttpStatus.SC_NOT_FOUND, String.format("Country not found"));
		}
	}

	public static final class PAN_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PAN_NUMBER_ALREADY_EXIST() {
			super(2026, HttpStatus.SC_NOT_FOUND, String.format("User already exist with PanNumber"));
		}
	}

	public static final class ACCOUNT_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public ACCOUNT_NUMBER_ALREADY_EXIST() {
			super(2027, HttpStatus.SC_NOT_FOUND, String.format("User already exist with Account Number"));
		}
	}

	public static final class AADHAR_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public AADHAR_NUMBER_ALREADY_EXIST() {
			super(2028, HttpStatus.SC_NOT_FOUND, String.format("User already exist with aadhar Number"));
		}
	}

	public static final class GST_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public GST_NUMBER_ALREADY_EXIST() {
			super(2029, HttpStatus.SC_NOT_FOUND, String.format("User already exist with GstNumber"));
		}
	}

	public static final class PROFILES_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PROFILES_NOT_FOUND() {
			super(2030, HttpStatus.SC_NOT_FOUND, String.format("Profiles not found"));
		}

		public PROFILES_NOT_FOUND(String Message) {
			super(2031, HttpStatus.SC_NOT_FOUND, Message);
		}
	}

	public static final class USER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public USER_ALREADY_EXIST() {
			super(2032, HttpStatus.SC_NOT_FOUND, String.format("User already exist with mobile number or email"));
		}

		public USER_ALREADY_EXIST(String phoneNumber) {
			super(2033, HttpStatus.SC_NOT_FOUND, String.format("User already exist with [%s]", phoneNumber));
		}

		public USER_ALREADY_EXIST(String phoneNumber, boolean status) {
			super(2034, HttpStatus.SC_NOT_FOUND, String
					.format("User already exist : [%s],but account is inactive. Please contact mmg", phoneNumber));
		}
	}

	public static final class GST_PATTERN_NOT_MATCH extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public GST_PATTERN_NOT_MATCH() {
			super(2035, HttpStatus.SC_NOT_FOUND, String.format("Please specify a valid gst number"));
		}
	}

	public static final class LICENSE_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public LICENSE_NUMBER_ALREADY_EXIST() {
			super(2036, HttpStatus.SC_NOT_FOUND, String.format("User already exist with LicenseNumber"));
		}
	}

	public static final class BADGE_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public BADGE_NUMBER_ALREADY_EXIST() {
			super(2037, HttpStatus.SC_NOT_FOUND, String.format("User already exist with BadgeNumber"));
		}
	}

	public static final class DL_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public DL_NUMBER_ALREADY_EXIST() {
			super(2038, HttpStatus.SC_NOT_FOUND, String.format("User already exist with DLNumber"));
		}
	}
	
	public static final class REGISTRATION_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public REGISTRATION_NUMBER_ALREADY_EXIST() {
			super(2039, HttpStatus.SC_NOT_FOUND, String.format("User already exist with registrationNumber"));
		}
	}

	public static final class USER_EXIST_DIFF_ROLE extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public USER_EXIST_DIFF_ROLE(String mobileNumber) {
			super(2040, HttpStatus.SC_NOT_FOUND, String
					.format("User already exist : [%s] with different role. Please contact operation team to add other role to the same user", mobileNumber));
		}
	}
	
	public static final class FRANCHISE_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;
		
		public FRANCHISE_NOT_FOUND() {
			super(2040, HttpStatus.SC_NOT_FOUND, String.format("Franchise details not available"));
		}

	}
	
	/**
	 * /** Role Exceptions : The Error codes can be used to handle in App or website
	 * Profile Error code range - 2051 to 2060
	 */
	public static final class ROLE_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public ROLE_NOT_FOUND(String id) {
			super(2051, HttpStatus.SC_NOT_FOUND, String.format("No role info available"));
		}
	}

	/**
	 * Promocode Exceptions : The Error codes can be used to handle in App or
	 * website Profile Error code range - 2061 to 2075
	 */
	public static final class PROMOCODE_ALREADY_GENERATED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PROMOCODE_ALREADY_GENERATED() {
			super(2061, HttpStatus.SC_NOT_FOUND, String.format("Promocode is Already Generated"));
		}

	}

	/**
	 * Login Exceptions : The Error codes can be used to handle in App or website
	 * Profile Error code range - 2076-3000
	 */
	public static final class INVALID_PASSWORD extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public INVALID_PASSWORD() {
			super(2076, HttpStatus.SC_NOT_FOUND, String.format("Invalid password"));
		}
	}

	public static final class INACTIVE_ACCOUNT extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public INACTIVE_ACCOUNT() {
			super(2077, HttpStatus.SC_NOT_FOUND,
					String.format("Your account has been disabled.Please contact MMG helpcenter 901 902 903 6 !!!"));
		}
	}

	public static final class MULTIPLE_ROLES extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public MULTIPLE_ROLES(String userName) {
			super(2078, HttpStatus.SC_NOT_FOUND, String
					.format("Multiple Roles available for the user name: " + userName + ".Please provide role Id"));
		}
	}

	/**
	 * Oauth Exceptions : The Error codes can be used to handle in App or website
	 * Oauth Error code range - 3001 - 3010
	 */
	public static final class INVALID_TOKEN extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public INVALID_TOKEN(String msg) {
			super(3001, HttpStatus.SC_UNAUTHORIZED, String.format(msg));
		}
	}

	/**
	 * Image Exception: The Error codes can be used to handle in App or website
	 * Image Error code range - 3021 - 4030
	 */
	public static final class FILE_UPLOAD_FAILED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public FILE_UPLOAD_FAILED() {
			super(3021, HttpStatus.SC_NOT_FOUND, String.format("Failed to upload image. Please try later.."));
		}

		public FILE_UPLOAD_FAILED(String msg) {
			super(3022, HttpStatus.SC_NOT_FOUND, String.format(msg));
		}
	}

	public static final class EMPTY_FILE extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public EMPTY_FILE(String msg) {
			super(3023, HttpStatus.SC_NO_CONTENT, String.format(msg));
		}
	}

	public static final class VALIDATE_FILE extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public VALIDATE_FILE(String message) {
			super(3024, HttpStatus.SC_NOT_FOUND, message);
		}
	}
	
	public static final class PHONENUMBER_PATTERN_NOT_MATCH extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PHONENUMBER_PATTERN_NOT_MATCH() {
			super(3025, HttpStatus.SC_NOT_FOUND, String.format("Please specify a Valid Mobile number"));
		}
	}
	
	/**
	 * Otp Exceptions : The Error codes can be used to handle in App or website
	 * Otp Error code range - 3031 - 3050
	 */
	public static final class OTP_NOT_SAVED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public OTP_NOT_SAVED() {
			super(3031, HttpStatus.SC_NOT_FOUND, String.format("Unable to save OTP"));
		}
	}
	
	public static final class INVALID_OTP extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public INVALID_OTP() {
			super(3032, HttpStatus.SC_NOT_FOUND, String.format("Invalid otp"));
		}
	}
	
	public static final class OTP_EXPIRED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public OTP_EXPIRED() {
			super(3033, HttpStatus.SC_BAD_REQUEST, String.format("Otp expired!!"));
		}
	}
	
	public static final class OTP_VALIDATE_NOT_CHECKED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public OTP_VALIDATE_NOT_CHECKED() {
			super(3034, HttpStatus.SC_NOT_FOUND, String.format("Otp is not validated!!"));
		}
	}
	
	public static final class PROFILE_VALIDATE extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PROFILE_VALIDATE(String msg) {
			super(3035, HttpStatus.SC_BAD_REQUEST, String.format(msg));
		}
	}

	public static final class INACTIVE_REASON_VALIDATE extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public INACTIVE_REASON_VALIDATE(String msg) {
			super(3036, HttpStatus.SC_BAD_REQUEST, String.format(msg));
		}
	}
	
	/**
	 * Header exception: The error codes can be used to handle in app or website
	 * Header error code range - 3051 - 3060 - 
	 */
	public static final class INVALID_PROFILE_SECURITY_KEY extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public INVALID_PROFILE_SECURITY_KEY() {
			super(3051, HttpStatus.SC_NOT_FOUND, String.format("Invalid profile security key"));
		}
	}
	/**
	 * Boarding request exception: The error codes can be used to handle in app or website
	 * Boarding request error code range - 3061 - 3070  
	 */
	public static final class VALIDATE_BOARDING_ENQUIRY extends MmgRestException {
		private static final long serialVersionUID = 1L;
		
		public VALIDATE_BOARDING_ENQUIRY(String message) {
			super(3061, HttpStatus.SC_BAD_REQUEST, message);
		}
	}
	
	public static final class ENQUIRY_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public ENQUIRY_NOT_FOUND() {
			super(3062, HttpStatus.SC_NOT_FOUND, String.format("No enquiry request info available"));
		}
	}
	
	public static final class VALIDATE_ENQUIRY_REASON extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public VALIDATE_ENQUIRY_REASON(String msg) {
			super(3063, HttpStatus.SC_NOT_FOUND, String.format(msg));
		}
	}
	
	/**
	 * Image Category request exception: The error codes can be used to handle in app or website
	 * Category  error code range - 3071 - 3080  
	 */
	public static final class IMAGE_CATEGORY_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;
		
		public IMAGE_CATEGORY_NOT_FOUND(String message) {
			super(3071, HttpStatus.SC_NOT_FOUND, message);
		}
	}

	public static final class IMAGE_ALREADY_EXISTS extends MmgRestException {
		private static final long serialVersionUID = 1L;
		
		public IMAGE_ALREADY_EXISTS(String message) {
			super(3072, HttpStatus.SC_NOT_FOUND, message);
		}
	}
	
	public static final class IMAGE_ROLE_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;
		
		public IMAGE_ROLE_NOT_FOUND(String message) {
			super(3073, HttpStatus.SC_BAD_REQUEST, message);
		}
	}
	
	/**
	/**
	 * FareDistribution Exceptions : The Error codes can be used to handle in App or website
	 * FareDistribution Request Error code range - 3081 to 3090
	 */
	
	public static final class FARE_DISTRIBUTION_NOT_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public FARE_DISTRIBUTION_NOT_EXIST() {
			super(3081, HttpStatus.SC_NOT_FOUND, String.format("Fare distribution not there in this franchis, Please create FD for this franchise"));
		}
	}
	
	public static final class FARE_DISTRIBUTION_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public FARE_DISTRIBUTION_EXIST() {
			super(3082, HttpStatus.SC_NOT_FOUND, String.format("Please activate FD for this franchise"));
		}
	}
	/**
	/**
	 * Address Exceptions : The Error codes can be used to handle in App or website
	 * Address Request Error code range - 3091 to 3100
	 */
	public static final class ADDRESS_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public ADDRESS_ALREADY_EXIST(String type) {
			super(3091, HttpStatus.SC_NOT_FOUND, type);
		}
	}
}
