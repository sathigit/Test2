package com.atpl.mmg.AandA.dao.profileImage;

import java.util.List;

import com.atpl.mmg.AandA.domain.profileImage.ProfileImageDomain;

public interface ProfileImageDAO {
	
	public String saveImage(ProfileImageDomain franchiseDocumentDomain) throws Exception;
	
	public ProfileImageDomain getImage(String profileId,int roleId,String category,boolean isActive) throws Exception;
	
	public List<ProfileImageDomain> getImage(String profileId,boolean isActive) throws Exception;
	
	public List<ProfileImageDomain> getActiveImageByCategoryAndRole(String profileId,String category,int roleId) throws Exception;
	
	public List<ProfileImageDomain> getImageByCategoryOnIsactive(String profileId, String category, boolean isActive,int roleId) throws Exception;
	
	public List<ProfileImageDomain> getAllImages(String profileId) throws Exception;
	
	public List<ProfileImageDomain> getAllImagesOnRole(int roleId) throws Exception;
	
	public String updateImageIsActive(String uuid,boolean isActive) throws Exception;

	public String updateImage(ProfileImageDomain documentUploadDomain)throws Exception;
}
