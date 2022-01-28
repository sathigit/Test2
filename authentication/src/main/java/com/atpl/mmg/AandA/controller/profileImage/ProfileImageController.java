package com.atpl.mmg.AandA.controller.profileImage;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.service.profileImage.ProfileImageService;

@RestController
@RequestMapping("/v2")
@SuppressWarnings({ "rawtypes", "unused" })
public class ProfileImageController {
	private static final Logger logger = LoggerFactory.getLogger(ProfileImageController.class);

	@Autowired
	ProfileImageService imageService;

	@PostMapping(value = "/image/role/{roleId}")
	@ResponseBody
	public ResponseEntity<GenericRes> saveImage(@PathVariable("roleId") int roleId, @RequestParam MultipartFile file,
			@RequestParam("profileId") String profileId, @RequestParam("category") String category,
			@RequestParam Map<String, String> req, RedirectAttributes redirectAttributes) throws Exception {
		return prepareSuccessResponse(imageService.saveImage(profileId, roleId, category, file, req));
	}

	@RequestMapping(value = "/image/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getImage(@PathVariable("profileId") String profileId,
			@RequestParam Map<String, String> reqParam, HttpServletRequest req) throws Exception {
		return prepareSuccessResponse(imageService.getImage(profileId, reqParam));
	}

	@RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getImage(@RequestParam Map<String, String> reqParam, HttpServletRequest req) throws Exception {
		return prepareSuccessResponse(imageService.getAllImage(reqParam));
	}

	/*
	 * @PostMapping("/update/image/role/{roleId}/{uuid}") public
	 * ResponseEntity<GenericRes> updateImage( @RequestParam("profileId") String
	 * profileId,@PathVariable("roleId") int roleId,@RequestParam MultipartFile
	 * file,
	 * 
	 * @RequestParam("category") String category, @PathVariable("uuid") String uuid,
	 * RedirectAttributes redirectAttributes) throws Exception { return
	 * prepareSuccessResponse(imageService.updateImage(profileId,roleId,category,
	 * uuid, file)); }
	 */

}
