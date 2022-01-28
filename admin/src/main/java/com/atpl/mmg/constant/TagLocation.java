package com.atpl.mmg.constant;

import java.util.HashMap;
import java.util.Map;

public enum TagLocation {

	SOURCE("SOURCE"), DESTINATION("DESTINATION");

	private String code;
	private static Map<String,TagLocation> codeToMapping;

	private TagLocation(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}
	
	public static TagLocation getTagLocation(String tagLocation) {
		if(codeToMapping == null) {
			initMapping();
		}
		return codeToMapping.get(tagLocation);
	}


	private static void initMapping() {
		codeToMapping = new HashMap<String, TagLocation>();
		
		for(TagLocation loc: values()) {
			codeToMapping.put(loc.code, loc);
		}
	}
	

}
