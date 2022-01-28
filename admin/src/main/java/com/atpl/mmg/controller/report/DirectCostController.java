package com.atpl.mmg.controller.report;

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
import com.atpl.mmg.model.report.DirectCostModel;
import com.atpl.mmg.service.report.DirectCostService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class DirectCostController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

	@Autowired
	DirectCostService directCostService;

	@RequestMapping(value = "/directCost", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addDirectCost(@RequestBody DirectCostModel directCostModel) throws Exception {
		return prepareSuccessResponse(directCostService.addDirectCost(directCostModel));
	}

	@RequestMapping(value = "/directCostSource", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addExpenseSource(@RequestBody DirectCostModel directCostModel) throws Exception {
		return prepareSuccessResponse(directCostService.addExpenseSource(directCostModel));
	}

	@RequestMapping(value = "/directCostSource/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDirectCostSource(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(directCostService.getDirectCostSource(id));
	}

	@RequestMapping(value = "/directCostSource", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRevenueSource() throws Exception {
		return prepareSuccessResponse(directCostService.getDirectCostSource());
	}

	@RequestMapping(value = "/directCostSource", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateDirectCost(@RequestBody DirectCostModel directCostModel) throws Exception {
		return prepareSuccessResponse(directCostService.updateDirectCost(directCostModel));
	}

	@RequestMapping(value = "/directCostSource/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteDirectCost(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(directCostService.deleteDirectCost(id));
	}

}
