package com.atpl.mmg.controller.dbupdate;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.dbupdate.DBUpdate;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.service.dbupdate.DBUpdateService;



@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class DBUpdateController implements Constants {

	@Autowired
	DBUpdateService dbUpdateService;

	@RequestMapping(value = "/db/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> dbUpdate(@RequestBody DBUpdate dbUpdate) throws Exception {
		return prepareSuccessResponse( dbUpdateService.dbUpdate(dbUpdate));
	}
	
}
