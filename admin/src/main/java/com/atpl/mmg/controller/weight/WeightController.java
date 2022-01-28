package com.atpl.mmg.controller.weight;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

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

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.service.weight.WeightService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class WeightController {

	private static final Logger logger = LoggerFactory.getLogger(WeightController.class);
	@Autowired
	WeightService weightService;

	@RequestMapping(value = "/weight", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getWeights(@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(weightService.getWeight(reqParam));
	}

	@RequestMapping(value = "/weight/{goodsId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getWeights(@PathVariable("goodsId") int goodsId) throws Exception {
		return prepareSuccessResponse(weightService.getWeight(goodsId));
	}
	
	@RequestMapping(value = "/weight/name/{weight}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getWeights(@PathVariable("weight") String weight) throws Exception {
		return prepareSuccessResponse(weightService.getWeight(weight));
	}

}
