package com.atpl.mmg.controller.blood;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.service.blood.BloodService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class BloodController implements Constants {
	private static final Logger logger = LoggerFactory.getLogger(BloodController.class);

	@Autowired
	BloodService bloodService;

	@RequestMapping(value = "/blood", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getBloodGroup(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(bloodService.getBloodGroup(reqParam));
	}

	@RequestMapping(value = "/blood/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getBloodGroupById(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(bloodService.getBloodGroupById(id));
	}

}
