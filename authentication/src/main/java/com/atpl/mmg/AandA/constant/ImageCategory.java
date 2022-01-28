package com.atpl.mmg.AandA.constant;

import java.util.HashMap;
import java.util.Map;

public enum ImageCategory {
	
	PROFILE("PROFILE"),PANCARD("PANCARD"), AADHAR("AADHAR"),PASSBOOK("PASSBOOK"),LICENSE("LICENSE"),OTHER("OTHER");
	
	private static Map<String, ImageCategory> codeToCategoryMapping;
	private String code;
	
	private ImageCategory(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}

	public static ImageCategory getImageCategory(String category) {
		if (codeToCategoryMapping == null) {
			initMapping();
		}
		return codeToCategoryMapping.get(category);
	}

	private static void initMapping() {
		codeToCategoryMapping = new HashMap<String, ImageCategory>();
		for (ImageCategory s : values()) {
			codeToCategoryMapping.put(s.code, s);
		}
	}
	
}
