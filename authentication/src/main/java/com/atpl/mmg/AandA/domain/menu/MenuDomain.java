package com.atpl.mmg.AandA.domain.menu;

import java.io.Serializable;
import java.sql.Blob;

public class MenuDomain implements Serializable {
private static final long serialVersionUID = 455847714679474741L;
	
	private String roleId;
	private String name;
	private Blob menus;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Blob getMenus() {
		return menus;
	}
	public void setMenus(Blob menus) {
		this.menus = menus;
	}

	
}
