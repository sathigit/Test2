package com.atpl.mmg.AandA.constant;

public interface Constants {

	public static String ACCESS_KEY_HEADER = "X-API-KEY";
	public static String ACCESS_SOURCE_HEADER = "X-API-SOURCE";
	public static String BEARER_TOKEN ="authorization";
	public static String PROFILE_SECURITY_HEADER ="X-API-PROFILE";
	public static String BY_PASS_TOKEN = "X-BYPASS-TOKEN";
	public final static String EXCEL_TYPE = "Excel";
	public final static String PDF_TYPE = "Pdf";
	public final static int API_TIME_OUT=30000;
	public final static int INDIA=101;
	public static String ZONE = "Asia/Calcutta";
	
	/**
	 * Email configuration
	 */
	
	public static String EMAIL_SENDER = "MoveMyGoods <donotreply@arohaka.com>";
	public static String EMAIL_CONFIGURATION_SET = "mmgemails";
	public final static String CUSTOMER_REG_MESSAGE = "Welcome to MOVE MY GOODS \nThank you \nHappy to be of service \nFor queries-901 902 903 6";
	public final static String CUSTOMER_OTP_MESSAGE = "Your Move My Goods OTP: %s \n" + " Please do not share this OTP with anyone"
			+ "\n" + " 8VT64X0l83p";
	
	public static String PROFILE_SOURCE = "app";
	
	public static String OAUTH_PASSWORD_GRANT = "password";
	public static String OAUTH_REFRESH_TOKEN_GRANT = "refresh_token";
	public final static String FRANCHISE_NOT_FOUND = "Thank you for your interest.We regret to inform you that operations in this location will commence shortly.";
	
	public static String ENQUIRY_COMPLETED ="Your  KYC documents for %s onboarding request have been successfully verified.Call 901 902 903 6 for support.";
	public static String ENQUIRY_FOLLOWUP = " Your onboarding %s enquiry request has been accepted and processing from Move My Goods.Please contact Move My Goods Help Center for assistance.Contact No: 901 902 903 6.";
	public static String ENQUIRY_ONHOLD = " Your MMG %s onboarding request is kept on HOLD.Call 901 902 903 6 for more details.";
	public static String ENQUIRY_INPROCESS = " Your  KYC documents for %s onboarding request have been successfully verified.Call 901 902 903 6 for support.";
	public static String ENQUIRY_PENDING = "Dear %1$s,Congratulations!, Your MoveMyGoods %2$s request has been accepted. To proceed further, kindly visit MMG office along with the KYC details. Location: https://goo.gl/maps/6YuGtTR4qo3phqt47 .Call 901 902 903 6 for support";
	
}
