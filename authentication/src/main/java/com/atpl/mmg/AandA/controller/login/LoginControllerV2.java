package com.atpl.mmg.AandA.controller.login;

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

import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.model.login.LoginModel;
import com.atpl.mmg.AandA.service.login.LoginServiceV2;

@RestController
@RequestMapping("/v2")
@SuppressWarnings("rawtypes")
public class LoginControllerV2 {

	@Autowired
	LoginServiceV2 loginServiceV2;
	
	@Autowired
	MMGProperties mmgProperties;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> authenticate(@RequestBody LoginModel loginModel) throws Exception {
		return prepareSuccessResponse(loginServiceV2.authenticate(loginModel));
	}
	
	@RequestMapping(value = "/login/roleCount/profile/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getTotalRole(@PathVariable("profileId") String profileId) throws Exception {
		return prepareSuccessResponse(loginServiceV2.getTotalRole(profileId));
	}

	@RequestMapping(value = "/login/roleNames/profile/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRoleNames(@PathVariable("profileId") String profileId) throws Exception {
		return prepareSuccessResponse(loginServiceV2.getRoleNames(profileId));
	}
	
	@RequestMapping(value = "/login/accessToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getAccessToken(@RequestBody LoginModel loginModel) throws Exception {
		return prepareSuccessResponse(loginServiceV2.getAccessToken(loginModel));
	}
	
	@RequestMapping(value = "/login/deleteToken", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteToken () throws Exception {
		return prepareSuccessResponse(loginServiceV2.deleteToken(mmgProperties.getAccessTokenKey(),false));
	}

}
