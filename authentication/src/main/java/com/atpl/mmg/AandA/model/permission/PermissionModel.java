package com.atpl.mmg.AandA.model.permission;

import java.math.BigInteger;

public class PermissionModel {
private BigInteger id;
private BigInteger permissionId;
private String permissionName;
private BigInteger roleId;

public BigInteger getId() {
	return id;
}
public void setId(BigInteger id) {
	this.id = id;
}
public BigInteger getPermissionId() {
	return permissionId;
}
public void setPermissionId(BigInteger permissionId) {
	this.permissionId = permissionId;
}
public String getPermissionName() {
	return permissionName;
}
public void setPermissionName(String permissionName) {
	this.permissionName = permissionName;
}
public BigInteger getRoleId() {
	return roleId;
}
public void setRoleId(BigInteger roleId) {
	this.roleId = roleId;
}

}
