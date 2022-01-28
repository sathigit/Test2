package com.atpl.mmg.AandA.controller;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.domain.DBUpdate;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.service.DBUpdateService;



@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class DBUpdateController implements Constants {

	@Autowired
	DBUpdateService dbUpdateService;

	@RequestMapping(value = "/db/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveProfile(@RequestBody DBUpdate dbUpdate) throws Exception {
		return prepareSuccessResponse( dbUpdateService.dbUpdate(dbUpdate));
	}
	
}
