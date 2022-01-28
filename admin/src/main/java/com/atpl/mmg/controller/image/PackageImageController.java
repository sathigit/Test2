package com.atpl.mmg.controller.image;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.service.image.PackageImageService;

@RestController
@RequestMapping("/v1")
public class PackageImageController {

	@Autowired
	PackageImageService imageService;

	@PostMapping(value = "/package/image/{packageId}")
	@ResponseBody
	public ResponseEntity<GenericRes> saveImage(@PathVariable("packageId") String packageId,
			@RequestParam MultipartFile file, @RequestParam("category") String category,
			@RequestParam Map<String, String> req, RedirectAttributes redirectAttributes) throws Exception {
		return prepareSuccessResponse(imageService.savePackageImage(packageId, category, file,req));
	}
}
