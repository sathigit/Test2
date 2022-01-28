package com.atpl.mmg.model.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class RoleModel {
	private int id;
	private String roleName;

	public RoleModel() {

	}

	public RoleModel(int id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}

	public RoleModel(String roleName) {
		this.roleName = roleName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
