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
import com.atpl.mmg.model.report.RevenueModel;
import com.atpl.mmg.service.report.RevenueService;

@RestController
@RequestMapping("/v1")
public class RevenueController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(RevenueController.class);

	@Autowired
	RevenueService revenueService;

	@RequestMapping(value = "/revenue", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addrevenue(@RequestBody RevenueModel revenueModel) throws Exception {
		return prepareSuccessResponse(revenueService.addrevenue(revenueModel));
	}

	@RequestMapping(value = "/revenueSource", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addrevenueSource(@RequestBody RevenueModel revenueModel) throws Exception {
		return prepareSuccessResponse(revenueService.addrevenueSource(revenueModel));
	}

	@RequestMapping(value = "/revenueSource", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRevenueSource() throws Exception {
		return prepareSuccessResponse(revenueService.getRevenueSource());
	}

	@RequestMapping(value = "/revenueSource/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRevenueSource(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(revenueService.getRevenueSource(id));
	}

	@RequestMapping(value = "/revenueSource", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateRevenueSource(@RequestBody RevenueModel revenueModel) throws Exception {
		return prepareSuccessResponse(revenueService.updateRevenueSource(revenueModel));
	}

	@RequestMapping(value = "/revenueSource/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteRevenueSource(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(revenueService.deleteRevenueSource(id));
	}

}
