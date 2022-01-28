/* Author:Vinutha S.R
 * Creationdate: 20-11-2019
 * Description:  Download Reports in Excel and Pdf format
 * */
package com.atpl.mmg.AandA.controller.download;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.service.download.DownloadService;
import com.atpl.mmg.AandA.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class DownloadController {

	@Autowired
	private DownloadService downloadService;

	/*
	 * To download list of customers to Excel/Pdf
	 */
	@RequestMapping(value = "/download/profile/role/{roleId}", method = RequestMethod.GET, produces = {
			"application/vnd.ms-excel" })
	public ResponseEntity<byte[]> downloadCustomerReport(@PathVariable("roleId") int roleId,
			@RequestParam Map<String, String> reqParam, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return prepareResponse(downloadService.downloadProfile(roleId, reqParam),
				CommonUtils.getFileHeaders(Role.getRole(roleId + "") + "_List.xlsx"));
	}

}
