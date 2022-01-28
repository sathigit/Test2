package com.atpl.mmg.controller.fdtrans;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

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

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.fdtrans.FareDistributionTransactionModel;
import com.atpl.mmg.service.fdtrans.FareDistributionTransactionService;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/v1")
public class FareDistributionTransactionController {

	@Autowired
	FareDistributionTransactionService fareDistributionTransactionService;

	@RequestMapping(value = "/fare/distribution/transaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody FareDistributionTransactionModel fareDistributionTransactionModel)
			throws Exception {
		return prepareSuccessResponse(fareDistributionTransactionService.save(fareDistributionTransactionModel));
	}

	@RequestMapping(value = "/earnings/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getEarnings(@PathVariable("roleId") int roleId,@RequestParam Map<String, String> reqParam) throws Exception {
			return prepareSuccessResponse(fareDistributionTransactionService.getEarnings(roleId,reqParam));
	}
	
	@RequestMapping(value = "/earnings/details/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getTotalEarningAndDetails(@PathVariable("roleId") int roleId,@RequestParam Map<String, String> reqParam) throws Exception {
			return prepareSuccessResponse(fareDistributionTransactionService.getTotalEarningAndDetails(roleId,reqParam));
	}
}
