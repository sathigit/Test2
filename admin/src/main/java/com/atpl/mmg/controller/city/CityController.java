package com.atpl.mmg.controller.city;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.city.CityModel;
import com.atpl.mmg.service.city.CityService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class CityController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired
	CityService cityService;

	@RequestMapping(value = "/city", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addCity(@RequestBody CityModel cityModel) throws Exception {
		return prepareSuccessResponse(cityService.addCity(cityModel));
	}

	@RequestMapping(value = "/city", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCities(@RequestParam(name = "getCities", required = false) boolean getCities,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(cityService.getAllCities(getCities,reqParam));
	}

	@RequestMapping(value = "/city/{stateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getBankById(@PathVariable("stateId") int stateId,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(cityService.getCities(stateId, reqParam));
	}

	@RequestMapping(value = "/city", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateCity(@RequestBody CityModel cityModel) throws Exception {
		return prepareSuccessResponse(cityService.updateCity(cityModel));
	}

	@RequestMapping(value = "/city/id/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCity(@PathVariable("cityId") int cityId) throws Exception {
		return prepareSuccessResponse(cityService.getCity(cityId));
	}

	@RequestMapping(value = "/city/name/id/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCityId(@PathVariable("name") String name) throws Exception {
		return prepareSuccessResponse(cityService.getCityId(name));
	}

	@RequestMapping(value = "/city/alias", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateAlias(@RequestBody CityModel cityModel) throws Exception {
		return prepareSuccessResponse(cityService.updateAlias(cityModel));
	}

}
