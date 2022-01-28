package com.atpl.mmg.AandA.model.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuModel implements Serializable {

	private static final long serialVersionUID = 455847714679474741L;
	private String roleId;
	private String name;
	private List<MenuTreeModel> menus = new ArrayList<MenuTreeModel>();

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

	public List<MenuTreeModel> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuTreeModel> menus) {
		this.menus = menus;
	}

	

	
}
