package com.atpl.mmg.AandA.controller.bankaccount;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.model.bankaccount.BankAccountModel;
import com.atpl.mmg.AandA.model.bankaccount.BankAccountModelDTO;
import com.atpl.mmg.AandA.service.bankaccount.BankAccountService;


@RestController
@RequestMapping("/v2")
@SuppressWarnings("rawtypes")
public class BankAccountController implements Constants {
	
	@Autowired
	BankAccountService bankAccountService;


	@RequestMapping(value = "/bank/account", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody BankAccountModel bankAccountModel) throws Exception {
		return prepareSuccessResponse(bankAccountService.save(bankAccountModel));
	}

	@RequestMapping(value = "/bank/account/profile/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getBankAccounts(@PathVariable("profileId") String profileId,@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(bankAccountService.getBankAccounts(profileId,reqParam));
	}
	
	@RequestMapping(value = "/bank/account/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getBankAccountsByUuid(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(bankAccountService.getBankAccountsByUuid(uuid));
	}
	
	@RequestMapping(value = "/bank/account", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateBank(@RequestBody BankAccountModelDTO bankAccountModelDTO)
			throws Exception {
		return prepareSuccessResponse(bankAccountService.updateBank(bankAccountModelDTO));
	}

	@RequestMapping(value = "/bank/account/activate/{uuid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> activateBankAccount(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(bankAccountService.activateDeactivateBank(uuid,true));
	}
	
	@RequestMapping(value = "/bank/account/deactivate/{uuid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deactivateBankAccount(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(bankAccountService.activateDeactivateBank(uuid,false));
	}
	

}
