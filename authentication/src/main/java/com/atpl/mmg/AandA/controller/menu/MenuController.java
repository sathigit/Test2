package com.atpl.mmg.AandA.controller.menu;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.service.menu.MenuService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class MenuController {

	@Autowired
	MenuService menuService;
	
	@RequestMapping(value = "/menu/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRoleNameBasedProfile(@PathVariable("roleId") String roleId) throws Exception {
		return prepareSuccessResponse(menuService.getMenus(roleId));
	}
}
