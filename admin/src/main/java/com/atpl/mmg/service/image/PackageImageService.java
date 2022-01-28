package com.atpl.mmg.service.image;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PackageImageService {
	
	public String savePackageImage(String packageDimensionId, String category,MultipartFile file,Map<String, String> req) throws Exception;

}
