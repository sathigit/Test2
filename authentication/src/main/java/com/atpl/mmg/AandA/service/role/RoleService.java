package com.atpl.mmg.AandA.service.role;

import java.util.Map;

import com.atpl.mmg.AandA.model.profile.ListDto;
import com.atpl.mmg.AandA.model.role.RoleModel;

@SuppressWarnings("rawtypes")
public interface RoleService {

	public ListDto getRole(Map<String, String> reqParam) throws Exception;

	public RoleModel getRoleNameBasedProfile(int id) throws Exception;

	public RoleModel getRoleName(int id) throws Exception;
	
	public String saveRole(RoleModel roleModel) throws Exception;
	
	public String updateRole(RoleModel roleModel) throws Exception;
	
	public String deleteRole(int id)throws Exception;
}
