package com.atpl.mmg.service.download;

import java.util.Map;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface DownloadService {

	public byte[] downloadEarnings(String type, int roleId,@RequestParam Map<String, String> reqParam) throws Exception;

	public MultiValueMap<String, String> getHeaders(String type);
	
	public byte[] exportGeography(String geographyType,@RequestParam Map<String, String> reqParam) throws Exception;
	
	public String importGeography(MultipartFile file,String geographyType) throws Exception;
}
