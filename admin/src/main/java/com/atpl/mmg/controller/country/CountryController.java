package com.atpl.mmg.controller.country;

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
import com.atpl.mmg.model.country.CountryModel;
import com.atpl.mmg.service.country.CountryService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class CountryController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

	@Autowired
	CountryService countryService;

	@RequestMapping(value = "/country", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addCountry(@RequestBody CountryModel countryModel) throws Exception {
		return prepareSuccessResponse(countryService.addCountry(countryModel));
	}

	@RequestMapping(value = "/country", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCountry(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(countryService.getCountry(reqParam));
	}

	@RequestMapping(value = "/country/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> UpdateCountry(@RequestBody CountryModel countryModel) throws Exception {
		return prepareSuccessResponse(countryService.UpdateCountry(countryModel));
	}

	@RequestMapping(value = "/country/id/{countryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCountry(@PathVariable("countryId") int countryId) throws Exception {
		return prepareSuccessResponse(countryService.getCountry(countryId));
	}

	@RequestMapping(value = "/country/{countryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCountry(@PathVariable("countryName") String countryname) throws Exception {
		return prepareSuccessResponse(countryService.getCountry(countryname));
	}

	@RequestMapping(value = "/country/{countryId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> DeleteCountry(@PathVariable("countryId") int countryId) throws Exception {
		return prepareSuccessResponse(countryService.DeleteCountry(countryId));
	}

}
