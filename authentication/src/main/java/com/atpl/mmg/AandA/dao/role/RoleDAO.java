package com.atpl.mmg.AandA.dao.role;

import java.util.List;

import com.atpl.mmg.AandA.domain.role.RoleDomain;

public interface RoleDAO {
	public RoleDomain getRoleNameBasedProfile(int id) throws Exception;

	public RoleDomain getRoleName(int id) throws Exception;
	
	public RoleDomain getRoleCount() throws Exception;

	public List<RoleDomain> getRole(int lowerBound,int upperBound) throws Exception;

	public String saveRole(RoleDomain roleDomain) throws Exception;

	public String updateRole(RoleDomain roleDomain) throws Exception;

	public String deleteRole(int id) throws Exception;

}
