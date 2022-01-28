package com.atpl.mmg.controller.quotation;

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
import com.atpl.mmg.controller.organization.OrganizationController;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.quotation.QuotationModel;
import com.atpl.mmg.service.quotation.QuotationService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class QuotationController implements Constants {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	QuotationService quotationService;

	@RequestMapping(value = "/quotation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveQuotation(@RequestBody QuotationModel quotationModel) throws Exception {
		return prepareSuccessResponse(quotationService.saveQuotation(quotationModel));
	}

	@RequestMapping(value = "/quotation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getQuotation() throws Exception {
		return prepareSuccessResponse(quotationService.getQuotation());
	}

	@RequestMapping(value = "/quotation", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateQuotation(@RequestBody QuotationModel quotationModel) throws Exception {
		return prepareSuccessResponse(quotationService.updateQuotation(quotationModel));
	}

	@RequestMapping(value = "/quotation/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getQuotation(@PathVariable("id") String id) throws Exception {
		return prepareSuccessResponse(quotationService.getQuotations(id));
	}

	@RequestMapping(value = "/quotation/customer/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getQuotationByCustomer(@PathVariable("customerId") String customerId)
			throws Exception {
		return prepareSuccessResponse(quotationService.getQuotationByCustomer(customerId));
	}

	/* Quotation Send via Email */
	@RequestMapping(value = "/quotation/email", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> sendQuotation(@RequestBody QuotationModel quotationModel) throws Exception {
		return prepareSuccessResponse(quotationService.sendQuotation(quotationModel));
	}

	@RequestMapping(value = "/quotation/{status}/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getQuotation(@PathVariable("status") String status,
			@PathVariable("cityId") int cityId) throws Exception {
		return prepareSuccessResponse(quotationService.getQuotation(status, cityId));
	}

}
