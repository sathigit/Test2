package com.atpl.mmg.controller.specialvehicles;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.specialvehicles.SpecialCategoryModel;
import com.atpl.mmg.service.specialvehicles.SpecialCategoryService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class SpecialCategoryController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(SpecialCategoryController.class);

	@Autowired
	SpecialCategoryService specialCategoryService;

	@RequestMapping(value = "/saveSpecialCategory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addSpecialCategory(@RequestBody SpecialCategoryModel specialCategoryModel)
			throws Exception {
		return prepareSuccessResponse(specialCategoryService.addSpecialCategory(specialCategoryModel));
	}

	@RequestMapping(value = "/specialCategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getSpecialCategory() throws Exception {
		return prepareSuccessResponse(specialCategoryService.getSpecialCategory());
	}

	@RequestMapping(value = "/special/category", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> UpdateSpecialCategory(@RequestBody SpecialCategoryModel specialCategoryModel)
			throws Exception {
		return prepareSuccessResponse(specialCategoryService.UpdateSpecialCategory(specialCategoryModel));
	}

	@RequestMapping(value = "/special/category/{specialCategoryId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteSpecialCategory(@PathVariable("specialCategoryId") int specialCategoryId)
			throws Exception {
		return prepareSuccessResponse(specialCategoryService.deleteSpecialCategory(specialCategoryId));
	}

}
