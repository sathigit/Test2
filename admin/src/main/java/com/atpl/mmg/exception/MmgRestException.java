package com.atpl.mmg.exception;

import static org.apache.http.HttpStatus.SC_OK;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpStatus;

import com.atpl.mmg.exception.MmgRestException;

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
			super(1007, HttpStatus.SC_NOT_FOUND, message);
		}
	}

	public static final class DELETE_FAILED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public DELETE_FAILED(String message) {
			super(1008, HttpStatus.SC_NOT_FOUND, message);
		}
	}

	public static final class NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public NOT_FOUND(String message) {
			super(1009, HttpStatus.SC_NOT_FOUND, message);
		}
	}

	public static final class COUNT_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public COUNT_NOT_FOUND(String message) {
			super(1010, HttpStatus.SC_NOT_FOUND, message);
		}
	}

	public static final class GOOGLE_MAP_API_KEY_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public GOOGLE_MAP_API_KEY_NOT_FOUND(String message) {
			super(1011, HttpStatus.SC_BAD_REQUEST, message);
		}
	}

	/**
	 * Profile Exceptions : The Error codes can be used to handle in App or website
	 * Profile Error code range - 3001 to 3200
	 */
	public static final class PROFILE_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

//		public PROFILE_NOT_FOUND(String id) {
//			super(3001, HttpStatus.SC_NOT_FOUND, String.format("Profile not found", id));
//		}
		public PROFILE_NOT_FOUND() {
			super(3002, HttpStatus.SC_NOT_FOUND, String.format("Profile not found"));
		}
	}

	public static final class FRANCHISE_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public FRANCHISE_NOT_FOUND(String id) {
			super(3003, HttpStatus.SC_NOT_FOUND, String.format("Franchise not found", id));
		}

		public FRANCHISE_NOT_FOUND() {
			super(3004, HttpStatus.SC_NOT_FOUND, String.format("Franchise not found"));
		}
	}

	public static final class FIRSTNAME_PATTERN_NOT_MATCH extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public FIRSTNAME_PATTERN_NOT_MATCH() {
			super(3005, HttpStatus.SC_NOT_FOUND, String.format("Please specify a Valid First name"));
		}
	}

	public static final class EMAILID_PATTERN_NOT_MATCH extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public EMAILID_PATTERN_NOT_MATCH() {
			super(3006, HttpStatus.SC_NOT_FOUND, String.format("Please specify a Valid EmailId"));
		}
	}

	public static final class PAN_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PAN_NUMBER_ALREADY_EXIST() {
			super(3007, HttpStatus.SC_NOT_FOUND, String.format("User already exist with PanNumber"));
		}
	}

	public static final class ACCOUNT_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public ACCOUNT_NUMBER_ALREADY_EXIST() {
			super(3008, HttpStatus.SC_NOT_FOUND, String.format("User already exist with Account Number"));
		}
	}

	public static final class AADHAR_NUMBER_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public AADHAR_NUMBER_ALREADY_EXIST() {
			super(3009, HttpStatus.SC_NOT_FOUND, String.format("User already exist with aadhar Number"));
		}
	}

	public static final class PAN_PATTERN_NOT_MATCH extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PAN_PATTERN_NOT_MATCH() {
			super(3010, HttpStatus.SC_NOT_FOUND, String.format("Please specify the valid pan number"));
		}
	}

	/**
	 * Quotation Exceptions : The Error codes can be used to handle in App or
	 * website Quotation Error code range - 3201 to 3300
	 */
	public static final class QUOTATION_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public QUOTATION_NOT_FOUND() {
			super(3202, HttpStatus.SC_NOT_FOUND, String.format("Quotation not found"));
		}
	}

	/**
	 * /* Document and Image Upload Exceptions : The Error codes can be used to
	 * handle in App or website Document and Image upload Request Error code range -
	 * 3301 to 4000
	 */
	public static final class EMPTY_FILE extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public EMPTY_FILE(String msg) {
			super(3301, HttpStatus.SC_NO_CONTENT, String.format(msg));
		}
	}

	public static final class FILE_UPLOAD_FAILED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public FILE_UPLOAD_FAILED() {
			super(3302, HttpStatus.SC_NOT_FOUND, String.format("Failed to upload image. Please try later.."));
		}

		public FILE_UPLOAD_FAILED(String msg) {
			super(3303, HttpStatus.SC_NOT_FOUND, String.format(msg));
		}
	}

	/**
	 * Terms and Conditions Exceptions : The Error codes can be used to handle in
	 * App or website Terms and Conditions Request Error code range - 3401 to 3420
	 */

	public static final class T_C_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public T_C_NOT_FOUND() {
			super(3401, HttpStatus.SC_NOT_FOUND, String.format("Terms and Condition  details not available"));
		}

	}

	public static final class T_C_CONTENT_VALIDATE extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public T_C_CONTENT_VALIDATE(String msg) {
			super(3402, HttpStatus.SC_BAD_REQUEST, String.format(msg));
		}
	}

	public static final class T_C_REQ_PARAM_VALIDATE extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public T_C_REQ_PARAM_VALIDATE(String msg) {
			super(3403, HttpStatus.SC_BAD_REQUEST, String.format(msg));
		}
	}

	/**
	 * Terms and Conditions Exceptions : The Error codes can be used to handle in
	 * App or website Terms and Conditions Request Error code range - 3421 to 3430
	 */

	public static final class FARE_DISTRIBUTION_TYPE_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public FARE_DISTRIBUTION_TYPE_NOT_FOUND() {
			super(3421, HttpStatus.SC_NOT_FOUND, String.format("Fare distribution type not available"));
		}

	}

	public static final class ALREADY_EXCEEDED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public ALREADY_EXCEEDED() {
			super(3422, HttpStatus.SC_NOT_FOUND, String.format("100 percentage already reached"));
		}

		public ALREADY_EXCEEDED(String msg) {
			super(3423, HttpStatus.SC_NOT_FOUND,
					String.format("100 percentage already reached in " + msg + " FD Type "));
		}

	}

	public static final class UPDATE_OTHER_ROLE_PERCENTAGE extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public UPDATE_OTHER_ROLE_PERCENTAGE() {
			super(3424, HttpStatus.SC_NOT_FOUND, String.format("Please edit the other role Percentage"));
		}

		public UPDATE_OTHER_ROLE_PERCENTAGE(String role, String name) {
			super(3425, HttpStatus.SC_NOT_FOUND,
					String.format("Please edit the " + role + "roles percentage in " + name + " FD Type"));
		}

	}

	public static final class ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public ALREADY_EXIST(String name) {
			super(3426, HttpStatus.SC_NOT_FOUND, String.format("Already exist this " + name + " FD Type"));
		}

		public ALREADY_EXIST(String name, boolean isFranchise) {
			super(3427, HttpStatus.SC_NOT_FOUND, String.format("Already exist " + name + " FD Type in this franchise"));
		}

	}

	public static final class FARE_DISTRBUTION_ALREADY_EXIST extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public FARE_DISTRBUTION_ALREADY_EXIST(String name) {
			super(3428, HttpStatus.SC_NOT_FOUND,
					String.format(name + " Fare Distribution Type Already exist in franchises,You can't delete "));
		}

	}

	/**
	 * Vehicle category Exceptions : The Error codes can be used to handle in App or
	 * website Terms and Conditions Request Error code range - 3431 to 4000
	 */

	public static final class VEHICLECATEGORY_AlREADY_EXISTS extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public VEHICLECATEGORY_AlREADY_EXISTS(String msg) {
			super(3431, HttpStatus.SC_BAD_REQUEST, String.format(msg));
		}

	}

	/**
	 * Package Type Exceptions : The Error codes can be used to handle in App or
	 * website Terms and Conditions Request Error code range - 4001 to 4010
	 */

	public static final class PACKAGE_TYPE_AlREADY_EXISTS extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PACKAGE_TYPE_AlREADY_EXISTS(String msg) {
			super(4001, HttpStatus.SC_BAD_REQUEST, String.format(msg));
		}

	}

	public static final class PACKAGE_TYPE_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PACKAGE_TYPE_NOT_FOUND() {
			super(4002, HttpStatus.SC_NOT_FOUND, String.format("Package Type not found!!"));
		}

	}

	/**
	 * Package Dimension Exceptions : The Error codes can be used to handle in App
	 * or website Terms and Conditions Request Error code range - 4011 to 4020
	 */

	public static final class PACKAGE_DIMENSION_ALREADY_EXISTS extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PACKAGE_DIMENSION_ALREADY_EXISTS(String msg) {
			super(4011, HttpStatus.SC_BAD_REQUEST, String.format(msg));
		}

	}

	public static final class PACKAGE_DIMENSION_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PACKAGE_DIMENSION_NOT_FOUND() {
			super(4012, HttpStatus.SC_NOT_FOUND, String.format("Package Dimension not found!!"));
		}

	}

	/**
	 * Package Image Exceptions : The Error codes can be used to handle in App or
	 * website Terms and Conditions Request Error code range - 4031 to 4040
	 */

	public static final class PACKAGE_IMAGE_ALREADY_EXISTS extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public PACKAGE_IMAGE_ALREADY_EXISTS() {
			super(4031, HttpStatus.SC_BAD_REQUEST, String.format("Package images already exists!!"));
		}

	}

	/**
	 * Route Exceptions : The Error codes can be used to handle in App or website
	 * Route Request Error code range - 4041 to 4050
	 */

	public static final class ROUTE_CREATE_ACCESS_DENIED extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public ROUTE_CREATE_ACCESS_DENIED(String msg) {
			super(4041, HttpStatus.SC_BAD_REQUEST, String.format(msg));
		}

	}

	/**
	 * Customer Type Exceptions : The Error codes can be used to handle in App or
	 * website Terms and Conditions Request Error code range - 4051 to 4055
	 */

	public static final class CUSTOMER_TYPE_AlREADY_EXISTS extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public CUSTOMER_TYPE_AlREADY_EXISTS(String msg) {
			super(4051, HttpStatus.SC_BAD_REQUEST, String.format(msg));
		}

	}

	public static final class CUSTOMER_TYPE_NOT_FOUND extends MmgRestException {
		private static final long serialVersionUID = 1L;

		public CUSTOMER_TYPE_NOT_FOUND() {
			super(4052, HttpStatus.SC_NOT_FOUND, String.format("Customer Type not found!!"));
		}

	}

}