package com.atpl.mmg.AandA.constant;

public enum AddressType {
	HOME("HOME"),OFFICE("OFFICE");
	
	private String code;
	
	private AddressType(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}
}
