package com.atpl.mmg.constant;

public interface Constants {

	public static String ACCESS_KEY_HEADER = "X-API-KEY";
	public static String ACCESS_SOURCE_HEADER = "X-API-SOURCE";
	public static String BEARER_TOKEN = "authorization";
	public static String BY_PASS_TOKEN = "X-BYPASS-TOKEN";
	public final static String EXCEL_TYPE = "Excel";
	public final static String PDF_TYPE = "Pdf";
	public final static int API_TIME_OUT = 30000;
	/**
	 * Email configuration
	 */

	public static String EMAIL_SENDER = "MoveMyGoods <donotreply@arohaka.com>";
	public static String EMAIL_CONFIGURATION_SET = "mmgemails";
	public static int KERBWEIGHT = 3500;
	public static int INDIA = 101;
	public static String ZONE = "Asia/Calcutta";
	public final static String OPERATIONAL_TEAM_QUOTATION_MESSAGE = "New Quotation Information: \n"
			+ "QuotationId:  %s \n Source: %s \n"
			+ " Destination:  %s \n CustomerName: %s \n ContactNumber: %d \n From MMG";
	public final static String BDM_Or_BDM_QUOTATION_MESSAGE = "Your New Quotation is" + " %s Arrived";
	public final static String OPERATIONAL_MOBILENUMBER = "8088900661";
	public static int MASTER_FRANCHISE_CHITYID = 1750;
	public static double PERCENTAGE = 100.0;
	public static String IS_PERCENTAGE = "Percentage";
	public static String FIXED_COST = "Fixed Cost";
	public static String START_DATE = "startDate";
	public static String END_DATE = "endDate";
	public static String ATPL_HEPL_NUMBER = "+91 901 902 903 6";
	public static String ATPL_ADDRESS = "#36, JCST Layout, M-Block,Kuvempunagara, Mysore 570023";
	public static String ATPL_EMAILID = "info@arohaka.com";
	public static String SOURCE = "source";
	public static String DESTINATION = "destination";
	public static String PACKAGE_DIMENSION_IMAGE_PATH ="https://s3.ap-south-1.amazonaws.com/";

	/**
	 * Master Datas
	 */
	public static int TRUCK_COUNT = 0;
	public static int CUSTOMER_COUNT = 0;
	public static int TRIP_COUNT = 0;
	
	public static int CONVERT_TONS = 1000;
	
	/**
	 * Offline / Online DataIdentifier
	 */
	public final static String CITY_DATA_IDENTIFIER = "id,name,state_id,pincode,alias,shortName,isGovtPreferred";
	public static String CITY = "City";
	public static String STATE = "State";
	public static String COUNTRY = "Country";
	
}
