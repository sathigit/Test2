package com.atpl.mmg.AandA.controller.dashboard;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.service.dashboard.DashboardService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class DashboardController implements Constants {

	@Autowired
	DashboardService dashboardService;

	@RequestMapping(value = "/dashboard/profileCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getprofile(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(dashboardService.getProfileCount(reqParam));
	}

	@RequestMapping(value = "/dashboard/profile/role/{roleId}/status/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getprofile(@PathVariable("roleId") int roleId,
			@PathVariable("status") boolean status) throws Exception {
		return prepareSuccessResponse(dashboardService.getProfileCountByRoleAndSts(roleId, status, 0, 0, 0));
	}

	@RequestMapping(value = "/dashboard/profileCount/franchise/{franchiseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getprofileCountByFranchiseId(@PathVariable("franchiseId") String franchiseId)
			throws Exception {
		return prepareSuccessResponse(dashboardService.getprofileCountByFranchiseId(franchiseId));
	}

	@RequestMapping(value = "/dashboard/role/{roleId}/bdm/profile/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDashboardByBdmId(@PathVariable("roleId") int roleId,
			@PathVariable("profileId") String profileId) throws Exception {
		return prepareSuccessResponse(dashboardService.getDashboardByBdmId(roleId, profileId));
	}

	@RequestMapping(value = "/dashboard/customerlead", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerLeadByStsId(@RequestParam Map<String, String> reqParam)
			throws Exception {
		return prepareSuccessResponse(dashboardService.getCustomerLeadByStsId(reqParam));
	}

	@RequestMapping(value = "/dashboard/customerlead/uploadedBy/{uploadedById}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerLead(@PathVariable("uploadedById") String uploadedById)
			throws Exception {
		return prepareSuccessResponse(dashboardService.getCustomerLead(uploadedById));
	}

	@RequestMapping(value = "/dashboard/customerlead/assigned/{assignedId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerLeadByAssignedId(@PathVariable("assignedId") String assignedId,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(dashboardService.getCustomerLeadByAssignedId(assignedId, reqParam));
	}

}
