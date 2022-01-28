package com.atpl.mmg.controller.employee;

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

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.employee.BdoModel;
import com.atpl.mmg.service.employee.BdoService;

/* Author:Sindhu
 * creationDate:17-11-2019
 * modifiedDate:25-02-2020
 * Description:Bdo Mapping to Bdm*/

@RestController
@RequestMapping("/v1")
@SuppressWarnings({"rawtypes","unused"})
public class BdoController {

	private static final Logger logger = LoggerFactory.getLogger(BdoController.class);

	@Autowired
	BdoService bdoService;

	@RequestMapping(value = "/saveBdo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveProfile(@RequestBody BdoModel bdoModel) throws Exception {
		return prepareSuccessResponse(bdoService.saveBdo(bdoModel));
	}

	@RequestMapping(value = "/bdoList/{bdmId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getBdoList(@PathVariable("bdmId") int bdmId,@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(bdoService.getBdoList(bdmId,reqParam));
	}

	@RequestMapping(value = "/bdoCount/{bdmId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getbdoCount(@PathVariable("bdmId") int bdmId) throws Exception {
		return prepareSuccessResponse(bdoService.getbdoCount(bdmId));
	}

	@RequestMapping(value = "/getbdoFranchise/{bdoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getbdoFranchise(@PathVariable("bdoId") int bdoId) throws Exception {
		return prepareSuccessResponse(bdoService.getbdoFranchise(bdoId));
	}

	@RequestMapping(value = "/getbdo/{franchiseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getbdo(@PathVariable("franchiseId") String franchiseId) throws Exception {
		return prepareSuccessResponse(bdoService.getbdo(franchiseId));
	}

}
