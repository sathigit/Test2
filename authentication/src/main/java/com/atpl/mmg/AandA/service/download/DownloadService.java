package com.atpl.mmg.AandA.service.download;

import java.util.Map;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

public interface DownloadService {
	byte[] downloadProfile(int roleId,@RequestParam Map<String, String> reqParam) throws Exception;
	MultiValueMap<String, String> getHeaders(String type);
}
