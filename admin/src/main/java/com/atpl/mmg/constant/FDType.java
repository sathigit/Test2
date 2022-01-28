package com.atpl.mmg.constant;

import java.util.HashMap;
import java.util.Map;

public enum FDType {

	TRIP("Trip"), INSURANCE("Insurance"), LABOUR("Labour");

	private static Map<String, FDType> codeToRoleMapping;
	private String code;

	private FDType(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}

	public static FDType getFDType(String FDTypeId) {
		if (codeToRoleMapping == null) {
			initMapping();
		}
		return codeToRoleMapping.get(FDTypeId);
	}

	private static void initMapping() {
		codeToRoleMapping = new HashMap<String, FDType>();
		for (FDType s : values()) {
			codeToRoleMapping.put(s.code, s);
		}
	}

}
