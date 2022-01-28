package com.atpl.mmg.log;

/**
 *
 * @author Raghu M
 */
public enum ServiceTypes {
	/**
	 * DBUpdate Service
	 */
	DB_UPDATE_SERVICE("DB_UPDATE_SERVICE"),
	/**
	 * ADMIN Service
	 */
	ADMIN_SERVICE("ADMIN_SERVICE"),
	/**
	 * City Service
	 */
	CITY("CITY"),
	/**
	 * Country Service
	 */
	COUNTRY("COUNTRY"),
	/**
	 * State Service
	 */
	STATE("STATE"),

	/**
	 * Bank Service
	 */
	BANK("BANK"),
	/**
	 * Blood Service
	 */
	BLOOD("BLOOD"),
	/**
	 * Weight Service
	 */
	WEIGHT("WEIGHT"),
	/**
	 * Fare Distribution Type Service
	 */
	FARE_DISTRIBUTION_TYPE("FARE_DISTRIBUTION_TYPE"),

	/**
	 * Fare Distribution Service
	 */
	FARE_DISTRIBUTION("FARE_DISTRIBUTION"),
	
	/**
	 * Terms and condition Service
	 */
	TERMS_CONDITIONS("TERMS_CONDITIONS"),

	
	/**
	 * Driver Type Service
	 */
	DRIVER_TYPE("DRIVER_TYPE"),
	
	/**
	 * Goods Type Service
	 */
	GOODS_TYPE("GOODS_TYPE"),
	
	/**
	 * Packaging Service
	 */
	PACKAGE("PACKAGE"),
	/**
	 * License Category Service
	 */
	
	LICENSE_CATEGORY("LICENSE_CATEGORY"),
	
	/**
	 * BDO Service
	 */
	
	BDO("BDO"),
	/**
	 * BDM Service
	 */
	
	BDM("BDM"),
	/**
	 * Lead remarks
	 */
	LEAD_REMARKS("LEAD_REMARKS"),
	/**
	 * Lead Profession
	 */
	LEAD_PROFESSION("LEAD_PROFESSION"),
	/**
	 * Lead Status
	 */
	LEAD_STATUS("LEAD_STATUS"),
	/**
	 * Organisation
	 */
	ORGANISATION("ORGANISATION"),
	/**
	 * VehicleCategory
	 */
	VEHICLE_CATEGORY("VEHICLE_CATEGORY"),
	/**
	 * Route
	 */
	ROUTE("ROUTE"),
	/**
	 * Booked Item
	 */
	BOOKED_ITEM("BOOKED_ITEM");

	private final String name;

	private ServiceTypes(final String c) {
		name = c;
	}

	public String getType() {
		return name;
	}
}