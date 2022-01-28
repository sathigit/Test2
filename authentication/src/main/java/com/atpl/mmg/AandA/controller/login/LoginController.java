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
import com.atpl.mmg.AandA.filter.JwtTokenUtil;
import com.atpl.mmg.AandA.model.login.LoginModel;
import com.atpl.mmg.AandA.model.session.SessionModel;
import com.atpl.mmg.AandA.service.login.LoginService;
import com.atpl.mmg.AandA.service.session.SessionService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
@Deprecated
public class LoginController {
	@Autowired
	LoginService loginservice;

	@Autowired
	SessionService sessionService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	MMGProperties mmgProperties;

	@RequestMapping(value = "/r1/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> authenticate(@RequestBody LoginModel loginModel) throws Exception {
		return prepareSuccessResponse(loginservice.authenticate(loginModel));

	}

	@RequestMapping(value = "/getCustomerPermission/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCitiesgetCustomerPermission(@PathVariable("roleId") int roleId)
			throws Exception {
		return prepareSuccessResponse(loginservice.getCustomerPermission(roleId));
	}

	@RequestMapping(value = "/getDriverPermission/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDriverPermission(@PathVariable("roleId") int roleId) throws Exception {
		return prepareSuccessResponse(loginservice.getDriverPermission(roleId));
	}

	@RequestMapping(value = "/getFleetOperatorPermission/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getFleetOperatorPermission(@PathVariable("roleId") int roleId) throws Exception {
		return prepareSuccessResponse(loginservice.getFleetOperatorPermission(roleId));
	}

	@RequestMapping(value = "/getOwnerPermission/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getOwnerPermission(@PathVariable("roleId") int roleId) throws Exception {
		return prepareSuccessResponse(loginservice.getOwnerPermission(roleId));
	}

	@RequestMapping(value = "/logout", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> logout(@RequestBody SessionModel sessionModel) throws Exception {
		return prepareSuccessResponse(sessionService.updateSession(sessionModel));
	}
	
	@RequestMapping(value = "/logout/session", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> logoutSession(@RequestBody SessionModel sessionModel) throws Exception {
		return prepareSuccessResponse(sessionService.updateSession(sessionModel));
	}

	@RequestMapping(value = "/savePermission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> savePermission(@RequestBody LoginModel loginModel) throws Exception {
		return prepareSuccessResponse(loginservice.savePermission(loginModel));

	}

	@RequestMapping(value = "/roleCount/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> roleCount(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(loginservice.roleCount(id));
	}

	@RequestMapping(value = "/getRoleName/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRoleName(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(loginservice.getRoleName(id));
	}

	@RequestMapping(value = "/getPath/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> rolePath(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(loginservice.rolePath(id));
	}

	@RequestMapping(value = "/Prole/{proleId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteProle(@RequestBody @PathVariable("proleId") int proleId) throws Exception {
		return prepareSuccessResponse(loginservice.deleteProle(proleId));

	}

	@RequestMapping(value = "/getProle/{id}/{proleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> rolePath(@PathVariable("id") int id, @PathVariable("proleId") int proleId)
			throws Exception {
		return prepareSuccessResponse(loginservice.getProle(id, proleId));
	}

	@RequestMapping(value = "/login/refreshToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getAccessToken(@RequestBody LoginModel loginModel) throws Exception {
		return prepareSuccessResponse(loginservice.getAccessToken(loginModel));

	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteToken () throws Exception {
		return prepareSuccessResponse(loginservice.deleteToken(mmgProperties.getAccessTokenKey(),false));

	}
}
