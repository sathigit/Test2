package com.atpl.mmg.AandA.service.datamigration;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.atpl.mmg.AandA.model.datamigration.ProfileImageTemplateModel;

public interface MigrationService {

	public String saveProfilesByRoles(MultipartFile file,String roleId) throws Exception;
	
	public String saveOnboardRequest(MultipartFile file) throws Exception;
	
	public List<ProfileImageTemplateModel> saveImagesByRole(MultipartFile file,String roleId) throws Exception;
	
}
