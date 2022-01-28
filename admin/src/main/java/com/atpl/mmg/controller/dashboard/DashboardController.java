package com.atpl.mmg.controller.dashboard;

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

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.dashboard.ReportDashboard;
import com.atpl.mmg.service.dashboard.DashboardService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class DashboardController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	DashboardService dashboardService;

	@RequestMapping(value = "/viewDashboardVehicle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getVehicle() throws Exception {
		return prepareSuccessResponse(dashboardService.getVehicle());
	}

	@RequestMapping(value = "/viewDashboardDriver", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDriver() throws Exception {
		return prepareSuccessResponse(dashboardService.getDriver());
	}

	@RequestMapping(value = "/dashboard/customer/trip/vehicle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getHomePageCounts() throws Exception {
		return prepareSuccessResponse(dashboardService.getHomePageCounts());
	}

	@RequestMapping(value = "/dasboard/quotation/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getQuotation(@PathVariable("customerId") String customerId) throws Exception {
		return prepareSuccessResponse(dashboardService.getQuotation(customerId));
	}

	@RequestMapping(value = "/dashboard/earnings/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getTotalEarningAndDetails(@PathVariable("roleId") int roleId,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(dashboardService.getTotalEarning(roleId, reqParam));
	}
	
	@RequestMapping(value = "/dashboard/route", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRouteCount(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(dashboardService.getRouteCount(reqParam));
	}
	
	@RequestMapping(value = "/dashboard/route/state/city", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRouteCountOnStateCity(@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(dashboardService.getRouteCountOnStateCity(reqParam));
	}

	@RequestMapping(value = "/dashboard/route/{routeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRouteReport(@PathVariable("routeId") String routeId,@RequestBody ReportDashboard reportDashboard) throws Exception {
		return prepareSuccessResponse(dashboardService.getRouteReport(routeId,reportDashboard));
	}

	
}
