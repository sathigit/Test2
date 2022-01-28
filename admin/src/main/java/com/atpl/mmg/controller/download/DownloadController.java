package com.atpl.mmg.controller.download;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareResponse;
import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.service.download.DownloadService;
import com.atpl.mmg.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class DownloadController {

	@Autowired
	private DownloadService downloadService;

	/*
	 * To download list of customers to Excel/Pdf
	 */
	@RequestMapping(value = "/download/earnings/type/{type}/role/{roleId}", method = RequestMethod.GET, produces = {
			"application/vnd.ms-excel", "application/pdf" })
	public ResponseEntity<byte[]> downloadCustomerReport(@PathVariable("type") String type,
			@PathVariable("roleId") int roleId, @RequestParam Map<String, String> reqParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return prepareResponse(downloadService.downloadEarnings(type, roleId, reqParam),
				downloadService.getHeaders(type));
	}

	@RequestMapping(value = "/export/geography/{geographyType}", method = RequestMethod.GET, produces = {
			"application/vnd.ms-excel" })
	public ResponseEntity<byte[]> exportGeography(@PathVariable("geographyType") String geographyType,
			@RequestParam Map<String, String> reqParam, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return prepareResponse(downloadService.exportGeography(geographyType, reqParam),
				CommonUtils.getFileHeaders(geographyType+"_geography.xlsx"));
	}

	@PostMapping(value = "/import/geography/{geographyType}")
	@ResponseBody
	public ResponseEntity<GenericRes> importGeography(@RequestParam("file") MultipartFile file,@PathVariable("geographyType") String geographyType,
			RedirectAttributes redirectAttributes) throws Exception {
		return prepareSuccessResponse(downloadService.importGeography(file,geographyType));
	}

}
