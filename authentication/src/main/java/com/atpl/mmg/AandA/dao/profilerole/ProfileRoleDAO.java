package com.atpl.mmg.AandA.dao.profilerole;

import java.util.List;

import com.atpl.mmg.AandA.domain.profilerole.ProfileRoleDomain;

public interface ProfileRoleDAO {

	public ProfileRoleDomain save(ProfileRoleDomain profileRole) throws Exception;

	public List<ProfileRoleDomain> getProfileRole(int roleId) throws Exception;
	
	public ProfileRoleDomain getRoleDet(String profileId,int roleId) throws Exception;
	
	public List<ProfileRoleDomain> getRoleIdDetails(String profileId,boolean isActive) throws Exception;
	
	public List<ProfileRoleDomain> getProfileRoleByProfileId(String profileId) throws Exception;
	
	public List<ProfileRoleDomain> getAllprofileRoles() throws Exception;
}
