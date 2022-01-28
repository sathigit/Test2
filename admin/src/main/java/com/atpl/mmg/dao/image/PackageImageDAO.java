package com.atpl.mmg.dao.image;

import java.util.List;

import com.atpl.mmg.domain.image.PackageImageDomain;

public interface PackageImageDAO {
	
	public String save(PackageImageDomain packageImageDomain) throws Exception;
	
	public List<PackageImageDomain> getImage(String packageId, boolean status) throws Exception;
	
	public String updateImageIsActive(String uuid,boolean isActive) throws Exception;

}
