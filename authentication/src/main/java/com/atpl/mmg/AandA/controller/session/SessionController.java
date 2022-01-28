package com.atpl.mmg.AandA.controller.session;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.model.session.SessionModel;
import com.atpl.mmg.AandA.service.session.SessionService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class SessionController implements Constants {

	@Autowired
	SessionService sessionService;

	@RequestMapping(value = "/session", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveSession(@RequestBody SessionModel sessionModel) throws Exception {
		return prepareSuccessResponse(sessionService.saveSession(sessionModel));
	}

	@RequestMapping(value = "/session", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateSession(@RequestBody SessionModel sessionModel) throws Exception {
		return prepareSuccessResponse(sessionService.updateSession(sessionModel));
	}

	@RequestMapping(value = "/session/{sessionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getSession(@PathVariable("sessionId") String sessionId) throws Exception {
		return prepareSuccessResponse(sessionService.getSession(sessionId));
	}

	@RequestMapping(value = "/session/last/access/time", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getLastAccessTime() throws Exception {
		return prepareSuccessResponse(sessionService.getLastAccessTime());
	}
}
