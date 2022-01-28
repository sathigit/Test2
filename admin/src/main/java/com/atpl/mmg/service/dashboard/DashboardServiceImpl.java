package com.atpl.mmg.service.dashboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.ApiUtility;
import com.atpl.mmg.common.GenericHttpClient;
import com.atpl.mmg.common.MmgEnum;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.constant.Role;
import com.atpl.mmg.dao.dashboard.DashboardDAO;
import com.atpl.mmg.dao.quotation.QuotationDAO;
import com.atpl.mmg.dao.route.RouteDao;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.quotation.QuotationDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.COUNT_NOT_FOUND;
import com.atpl.mmg.model.dashboard.DashboardModel;
import com.atpl.mmg.model.dashboard.ReportDashboard;
import com.atpl.mmg.model.faredist.EarningsModel;
import com.atpl.mmg.model.route.RouteDashboard;
import com.atpl.mmg.service.auth.AdminAuthService;
import com.atpl.mmg.service.faredist.BookingCommonService;
import com.atpl.mmg.service.fdtrans.FareDistributionTransactionService;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.DateUtility;

@Service("dashboardService")
public class DashboardServiceImpl implements DashboardService, Constants {

	@Autowired
	QuotationDAO quotationDAO;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	FareDistributionTransactionService fareDistributionTransactionService;

	@Autowired
	BookingCommonService bookingCommonService;

	@Autowired
	AdminAuthService authService;

	@Autowired
	DashboardDAO dashboardDAO;
	
	@Autowired
	RouteDao routeDao;

	private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);

	@Override
	public DashboardModel getVehicle() throws Exception {
		DashboardModel dashboardModel = new DashboardModel();
		dashboardModel.setTotal(franchiseVehicleCount());
		/* dashboardModel.setTotal(franchiseVehicleCount() + fleetVehicleCount()); */
		return dashboardModel;
	}

	/**
	 * microService to Franchise : To fetch the Vehicle Count.
	 */
	private int franchiseVehicleCount() throws Exception {
		int vehicleCount = 0;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFranchiseUrl() + "v1" + "/total/Vehicle",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new COUNT_NOT_FOUND("Vehicle not found");
			else {
				JSONObject array = jsonResponse.getJSONObject("data");
				vehicleCount = (int) array.get("total");
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return vehicleCount;

	}

	/**
	 * microService to FleetOperator : To fetch the Vehicle Count.
	 */
	private int fleetVehicleCount() throws Exception {
		int vehicleCount = 0;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFleetUrl() + "v1" + "/getFleetVehicleCount",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FLEETOPERATOR.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new COUNT_NOT_FOUND("Vehicle not found");
			else {
				JSONObject array = jsonResponse.getJSONObject("data");
				vehicleCount = (int) array.get("total");
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return vehicleCount;

	}

	@Override
	public int getTrip() throws Exception {
		return bookingCommonService.tripCount() + Constants.TRIP_COUNT;
		/* return franchiseTripCount() + fleetTripCount(); */
	}

	/**
	 * microService to FleetOperator : To fetch the Trip Count.
	 */
	private int fleetTripCount() throws Exception {
		int tripCount = 0;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFleetUrl() + "v1" + "/total/trip/count",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.FLEETOPERATOR.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new COUNT_NOT_FOUND("Trip not found");
			else {
				JSONObject array = jsonResponse.getJSONObject("data");
				tripCount = (int) array.get("total");
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return tripCount;
	}

	@Override
	public DashboardModel getDriver() throws Exception {
		DashboardModel dashboardModel = new DashboardModel();
		dashboardModel.setTotal(authService.getDashboardByRole(Role.DRIVER.getCode(), true));
		/* dashboardModel.setTotal(franchiseDriverCount() + fleetDriverCount()); */
		return dashboardModel;

	}

	/**
	 * microService to FleetOperator : To fetch the driver count.
	 */
	private int fleetDriverCount() throws Exception {
		int tripCount = 0;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getFleetUrl() + "v1" + "/total/driver/count",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.FLEETOPERATOR.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new COUNT_NOT_FOUND("Driver not found");
			else {
				JSONObject array = jsonResponse.getJSONObject("data");
				tripCount = (int) array.get("total");
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return tripCount;
	}

	@Override

	public int dashboardVehicle() throws Exception {
		DashboardModel dashboardModel = new DashboardModel();
		dashboardModel = getVehicle();
		return dashboardModel.getTotal() + Constants.TRUCK_COUNT;
	}

	@Override
	public DashboardModel getHomePageCounts() throws Exception {

		DashboardModel dashboardModel = new DashboardModel();
		int customer = authService.getDashboardByRole(Role.CUSTOMER.getCode(), true);
		int branches = authService.getDashboardByRole(Role.FRANCHISE.getCode(), true);
		Integer branch = Integer.valueOf(branches);
		dashboardModel.setCustomer(customer + Constants.CUSTOMER_COUNT);
		dashboardModel.setBranches(branch);
		dashboardModel.setVehicle(dashboardVehicle());
		dashboardModel.setTrip(getTrip());
		return dashboardModel;
	}

	@Override
	public int getQuotation(String customerId) throws Exception {
		QuotationDomain quotationDomain = quotationDAO.getDashboard(customerId);
		return quotationDomain.getTotal();
	}

	@Override
	public DashboardModel getTotalEarning(int roleId, Map<String, String> reqParam) throws Exception {
		DashboardModel dashboardModel = new DashboardModel();
		EarningsModel earningsModel = fareDistributionTransactionService.getTotalEarningAndDetails(roleId, reqParam);
		if (earningsModel != null)
			dashboardModel.setTotalEarnings(earningsModel.getTotalEarnings());
		return dashboardModel;
	}

	@Override
	public DashboardModel getRouteCount(Map<String, String> reqParam) throws Exception {
		Boolean isActive = null;
		DashboardDomain dashboardDomain = new DashboardDomain();
		String startDate = null, endDate = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("routeId")) {
				String routeId = reqParam.get("routeId");
				if (reqParam.containsKey("startDate")) {
					startDate = reqParam.get("startDate");
				}
				if (reqParam.containsKey("endDate")) {
					endDate = reqParam.get("endDate");
				}
				int count = 0;
				count = bookingCommonService.getMasterTripCount(routeId, startDate, endDate).getTotal();
				dashboardDomain.setTotal(count);
			}
			if (reqParam.containsKey("isActive")) {
				isActive = Boolean.valueOf(reqParam.get("isActive"));
				dashboardDomain = dashboardDAO.getRouteCount(isActive);
			}
		}
		DashboardModel dashBoardmodel = new DashboardModel();
		BeanUtils.copyProperties(dashboardDomain, dashBoardmodel);
		return dashBoardmodel;
	}

	@Override
	public DashboardModel getRouteCountOnStateCity(Map<String, String> reqParam) throws Exception {
		Boolean isActive = null;
		String source = null, destination = null;
		int stateId = 0, cityId = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("isActive")) {
				isActive = Boolean.valueOf(reqParam.get("isActive"));
			}
			if (reqParam.containsKey("source")) {
				source = reqParam.get("source");
			}
			if (reqParam.containsKey("destination")) {
				destination = reqParam.get("destination");
			}
			if (reqParam.containsKey("cityId")) {
				cityId = Integer.valueOf(reqParam.get("cityId"));
			}
			if (reqParam.containsKey("stateId")) {
				stateId = Integer.valueOf(reqParam.get("stateId"));
			}
		}
		DashboardModel dashBoardmodel = new DashboardModel();
		DashboardDomain dashboardDomain = new DashboardDomain();
		dashboardDomain = dashboardDAO.getRouteCountOnStateCity(isActive, source, destination, cityId, stateId);
		BeanUtils.copyProperties(dashboardDomain, dashBoardmodel);
		return dashBoardmodel;
	}

	@Override
	public RouteDashboard getRouteReport(String routeId,ReportDashboard reportDashboard) throws Exception {
		RouteDashboard routeDashboard = new RouteDashboard();
		String strDate = null, endDate = null;
		if (!CommonUtils.isNullCheck(routeId)) {
			routeDao.getRouteById(routeId);
		}
		if (reportDashboard.isMonthly() || reportDashboard.isWeekly() || reportDashboard.isYearly()) {
			reportDashboard = DateUtility.getStartAndEndDate(reportDashboard);
		}
		DateFormat dateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD);
		if (null != reportDashboard.getStartDate()) {
			strDate = dateFormat.format(reportDashboard.getStartDate());
		}
		if (null != reportDashboard.getEndDate()) {
			endDate = dateFormat.format(reportDashboard.getEndDate());
		}
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("routeId", routeId);
		reqParam.put("startDate", strDate);
		reqParam.put("endDate", endDate);
		routeDashboard.setTotalTrips(getRouteCount(reqParam).getTotal());
		routeDashboard.setTotalWeight(bookingCommonService
				.getMasterTripTotalWeight(routeId, strDate, endDate).getTotal());
		routeDashboard.setTotalEarnings(0.0);
		return routeDashboard;
	}

}
