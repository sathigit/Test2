package com.atpl.mmg.constant;

import java.util.HashMap;
import java.util.Map;

public enum MeasuringUnit {

	INCHES("INCHES"), CMS("CMS"), FEET("FEET");
	
	private static Map<String, MeasuringUnit> codeToMeasuringUnitMapping;
	private String code;

	private MeasuringUnit(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}

	public static MeasuringUnit getMeasuringUnit(String measuringUnit) {
		if (codeToMeasuringUnitMapping == null) {
			initMapping();
		}
		return codeToMeasuringUnitMapping.get(measuringUnit);
	}

	private static void initMapping() {
		codeToMeasuringUnitMapping = new HashMap<String, MeasuringUnit>();
		for (MeasuringUnit s : values()) {
			codeToMeasuringUnitMapping.put(s.code, s);
		}
	}

	
}
