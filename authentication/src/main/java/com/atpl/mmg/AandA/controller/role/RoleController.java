package com.atpl.mmg.AandA.controller.role;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

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

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.model.role.RoleModel;
import com.atpl.mmg.AandA.service.role.RoleService;

@SuppressWarnings({ "rawtypes", "unused" })
@RestController
@RequestMapping("/v1")
public class RoleController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	RoleService roleService;

	/*@RequestMapping(value = "/rolename/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRoleNameBasedProfile(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(roleService.getRoleNameBasedProfile(id));
	} */

	@RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getprofile(@PathVariable("roleId") int roleId) throws Exception {
		return prepareSuccessResponse(roleService.getRoleName(roleId));
	}

	@RequestMapping(value = "/role", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveRole(@RequestBody RoleModel roleModel) throws Exception {
		return prepareSuccessResponse(roleService.saveRole(roleModel));
	}

	@RequestMapping(value = "/role/allRoles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRole(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(roleService.getRole(reqParam));
	}

	@RequestMapping(value = "/role", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateRole(@RequestBody RoleModel roleModel) throws Exception {
		return prepareSuccessResponse(roleService.updateRole(roleModel));
	}

	@RequestMapping(value = "/deleteRole/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteBank(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(roleService.deleteRole(id));
	}

}
