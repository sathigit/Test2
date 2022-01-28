package com.atpl.mmg.controller.state;

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
import com.atpl.mmg.model.state.StateModel;
import com.atpl.mmg.service.state.StateService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class StateController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(StateController.class);

	@Autowired
	StateService stateService;

	@RequestMapping(value = "/state", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveState(@RequestBody StateModel stateModel) throws Exception {
		return prepareSuccessResponse(stateService.addState(stateModel));
	}

	@RequestMapping(value = "/State/country/{countryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getStates(@PathVariable("countryId") int countryId,@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(stateService.getStates(countryId,reqParam));
	}

	@RequestMapping(value = "/state", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateState(@RequestBody StateModel stateModel) throws Exception {
		return prepareSuccessResponse(stateService.updateState(stateModel));
	}

	@RequestMapping(value = "/state/{stateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getState(@PathVariable("stateId") int stateId) throws Exception {
		return prepareSuccessResponse(stateService.getState(stateId));
	}
	
	@RequestMapping(value = "/state/name/{stateName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getState(@PathVariable("stateName") String stateName) throws Exception {
		return prepareSuccessResponse(stateService.getState(stateName));
	}

	@RequestMapping(value = "/State", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getStates(@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(stateService.getStates(reqParam));
	}

}
