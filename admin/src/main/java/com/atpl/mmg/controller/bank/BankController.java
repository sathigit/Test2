package com.atpl.mmg.controller.bank;

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
import com.atpl.mmg.model.bank.BankModel;
import com.atpl.mmg.service.bank.BankService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class BankController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(BankController.class);

	@Autowired
	BankService bankService;

	@RequestMapping(value = "/bank", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveBank(@RequestBody BankModel bankModel) throws Exception {
		return prepareSuccessResponse(bankService.saveBank(bankModel));
	}

	@RequestMapping(value = "/bank", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getBank(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(bankService.getBankList(reqParam));
	}

	@RequestMapping(value = "/bank", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateBank(@RequestBody BankModel bankModel) throws Exception {
		return prepareSuccessResponse(bankService.updateBank(bankModel));
	}

	@RequestMapping(value = "/bank/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getBankById(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(bankService.getBank(id));
	}

	@RequestMapping(value = "/bank/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteBank(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(bankService.deleteBank(id));
	}

}
