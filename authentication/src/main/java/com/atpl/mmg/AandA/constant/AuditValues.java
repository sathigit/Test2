package com.atpl.mmg.AandA.constant;

public enum AuditValues {
	
	EMAIL_EDIT("EMAIL_EDIT"),MOBILE_NO_EDIT("MOBILE_NO_EDIT");
	
	private String code;
	
	private AuditValues(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}
}
