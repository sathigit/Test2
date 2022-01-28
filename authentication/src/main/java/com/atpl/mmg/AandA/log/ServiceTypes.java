package com.atpl.mmg.AandA.log;

/**
 *
 * @author Raghu M
 */
public enum ServiceTypes {
	/**
	 * Db Update Service
	 */
	DB_UPDATE_SERVICE("DB_UPDATE_SERVICE"),
	/**
	 * Audit Service
	 */
	AUDIT_SERVICE("AUDIT_SERVICE"),
	/**
	 * Profile Service
	 */
	PROFILE_SERVICE("PROFILE_SERVICE"),
	/**
	 * Profile Image Service
	 */
	PROFILE_IMAGE_SERVICE("PROFILE_IMAGE_SERVICE"),
	/**
	 * Image Service
	 */
	IMAGE_SERVICE("IMAGE_SERVICE"),
	/**
	 * ForgotPassword Service
	 */
	FORGOT_PASSWORD_SERVICE("FORGOTPASSWORD_SERVICE"),

	/**
	 * Login Service
	 */
	LOGIN_SERVICE("LOGIN_SERVICE"),
	/**
	 * Bank_Account Service
	 */
	BANK_ACCOUNT_SERVICE("BANK_ACCOUNT_SERVICE"),
	/**
	 * Reason Service
	 */
	REASON_SERVICE("REASON_SERVICE"),

	/**
	 * BoardingEnquiry Service
	 */
	BOARDING_ENQUIRY_SERVICE("BOARDING_ENQUIRY_SERVICE"),

	/**
	 * BoardingEnquiry Service
	 */
	ENQUIRY_REASON_SERVICE("ENQUIRY_REASON_SERVICE"),

	/**
	 * Franchise Service
	 */
	FRANCHISE_SERVICE("FRANCHISE_SERVICE"),
	/**
	 * Fleet Service
	 */
	FLEET_SERVICE("FLEET_SERVICE"),
	/**
	 * Enterprise Service
	 */
	ENTERPRISE_SERVICE("ENTERPRISE_SERVICE"),
	/**
	 * Warehouse Service
	 */
	WAREHOUSE_SERVICE("WAREHOUSE_SERVICE"),
	/**
	 * Customer_lead Service
	 */
	CUSTOMER_LEAD("CUSTOMER_LEAD"),
	/**
	 * Role Service
	 */
	ROLE("ROLE"),
	/**
	 * Address Service
	 */
	ADDRESS("ADDRESS");

	private final String name;

	private ServiceTypes(final String c) {
		name = c;
	}

	public String getType() {
		return name;
	}
}