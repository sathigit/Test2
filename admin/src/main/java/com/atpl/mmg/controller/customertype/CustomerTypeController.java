package com.atpl.mmg.controller.customertype;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.customertype.CustomerTypeModel;
import com.atpl.mmg.service.customertype.CustomerTypeService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class CustomerTypeController implements Constants {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerTypeController.class);

	@Autowired
	CustomerTypeService customerTypeService;


	@RequestMapping(value = "/customer/type", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody CustomerTypeModel CustomerTypeModel)
			throws Exception {
		return prepareSuccessResponse(customerTypeService.save(CustomerTypeModel));
	}

	@RequestMapping(value = "/customer/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerType(@RequestParam( value="status", required = false) Boolean status) throws Exception {
		return prepareSuccessResponse(customerTypeService.getCustomerType(status));
	}

	@RequestMapping(value = "/customer/type/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerTypeById(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(customerTypeService.getCustomerTypeById(id));
	}

	@RequestMapping(value = "/customer/type/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> delete(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(customerTypeService.delete(id));
	}
	
	@RequestMapping(value = "/customer/type/name/{typeName}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> delete(@PathVariable("typeName") String typeName) throws Exception {
		return prepareSuccessResponse(customerTypeService.getCustomerTypeByName(typeName));
	}

	@RequestMapping(value = "/customer/type", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> update(@RequestBody CustomerTypeModel CustomerTypeModel)
			throws Exception {
		return prepareSuccessResponse(customerTypeService.update(CustomerTypeModel));
	}
	
	@RequestMapping(value = "/customer/type/{id}/status/{status}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateStatus(@PathVariable("id") int id,@PathVariable("status") boolean status) throws Exception {
		return prepareSuccessResponse(customerTypeService.updateStatus(id,status));
	}


}
