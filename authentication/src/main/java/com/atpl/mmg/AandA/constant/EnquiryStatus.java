package com.atpl.mmg.AandA.constant;

import java.util.HashMap;
import java.util.Map;

public enum EnquiryStatus {
	
	ACTIVE("ACTIVE"),INACTIVE("INACTIVE"),APPROVAL("APPROVAL"),ONHOLD("ONHOLD"),PENDING("PENDING"),INPROCESS("INPROCESS"),COMPLETED("COMPLETED");
	
	private static Map<String, EnquiryStatus> codeToEnquiryMapping;
	private String code;
	
	private EnquiryStatus(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}

	public static EnquiryStatus getEnquiryStatus(String enquiryStatus) {
		if (codeToEnquiryMapping == null) {
			initMapping();
		}
		return codeToEnquiryMapping.get(enquiryStatus);
	}

	private static void initMapping() {
		codeToEnquiryMapping = new HashMap<String, EnquiryStatus>();
		for (EnquiryStatus s : values()) {
			codeToEnquiryMapping.put(s.code, s);
		}
	}
	

}
