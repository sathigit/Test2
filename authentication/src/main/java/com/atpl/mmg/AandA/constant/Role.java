package com.atpl.mmg.AandA.constant;

import java.util.HashMap;
import java.util.Map;

public enum Role {
	CUSTOMER("1"), DRIVER("2"), FLEET_OPERATOR("3"), WAREHOUSE("4"),OWNER("5"),EMPLOYEE("6"),
	TIEUPS("7"), FAREESTIMATION("8"), FRANCHISE("9"), FLEETDRIVER("11"),COORDINATOR("12"),FIELDOFFICER("13"),HELPCENTERLEVEL2("14"),HELPCENTER("15"),HELPCENTERLEVEL3("16"),OPERATIONAL_TEAM("18"), ENTERPRISE("19"), BDM("20"), BDO("21"),CHANNEL_PARTNER("22");

	private static Map<String, Role> codeToRoleMapping;
	private String code;

	private Role(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}

	public static Role getRole(String roleId) {
		if (codeToRoleMapping == null) {
			initMapping();
		}
		return codeToRoleMapping.get(roleId);
	}

	private static void initMapping() {
		codeToRoleMapping = new HashMap<String, Role>();
		for (Role s : values()) {
			codeToRoleMapping.put(s.code, s);
		}
	}

}
