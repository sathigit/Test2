package com.atpl.mmg.AandA.service.profileImage;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.atpl.mmg.AandA.model.profileImage.ProfileImageModel;

public interface ProfileImageService {
	
	public String saveImage(String profileId,int roleId, String category,MultipartFile file,Map<String,String> req) throws Exception;
	
	public List<ProfileImageModel> getImage(String profileId,Map<String, String> req) throws Exception;
	
	public List<ProfileImageModel> getAllImage(Map<String, String> req) throws Exception;
	
//	public String updateImage(String profileId,int roleId,String category,String uuid,MultipartFile file)throws Exception;
	
}
