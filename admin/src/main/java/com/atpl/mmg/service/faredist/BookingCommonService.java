package com.atpl.mmg.service.faredist;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.atpl.mmg.common.ApiUtility;
import com.atpl.mmg.common.GenericHttpClient;
import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.common.MmgEnum;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.domain.dbupdate.DBUpdate;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.COUNT_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.model.booking.BookingModel;
import com.atpl.mmg.model.dashboard.ParcelDashboardModel;
import com.atpl.mmg.utils.CommonUtils;

@Component
public class BookingCommonService {

	@Autowired
	MMGProperties mmgProperties;

	public Map<BigInteger, BookingModel> getBookingByStatus(String status) throws Exception {
		List<BookingModel> bookingModelList = null;
		Map<BigInteger, BookingModel> bookingMap = new HashMap<>();
		/** tieups micro service **/
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getCustomerUrl() + "v2/booking/status/" + status,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.CUSTOMER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value()) {
				bookingModelList = JsonUtil.parseJsonArray(jsonResponse.getJSONArray("data").toString(),
						BookingModel.class);
				for (BookingModel bookingModel : bookingModelList) {
					bookingMap.put(bookingModel.getId(), bookingModel);
				}
			} else
				throw new NOT_FOUND("Booking list is empty");
			return bookingMap;
		} else
			throw new NOT_FOUND("Booking not found");
	}

	public void updateStatusByBookingId(BigInteger id) throws Exception {
		DBUpdate dbUpdate = new DBUpdate();
		dbUpdate.setTableName("bookingsv2");

		Map<String, Object> expression = new HashMap<String, Object>();
		expression.put("isFdDistributed", true);
		dbUpdate.setExpression(expression);

		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", id);
		dbUpdate.setConditions(conditions);

		String reqBody = JsonUtil.toJsonString(dbUpdate);

		Map<String, Object> httpResponse = GenericHttpClient.doHttpPost(
				mmgProperties.getCustomerUrl() + "v1" + "/db/update",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()), reqBody,
				MmgEnum.CUSTOMER.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new UPDATE_FAILED("Update failed for booking");
		}
	}

	public int tripCount() throws Exception {
		int tripCount = 0;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getCustomerUrl() + "v1" + "/dashboard/trip",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), null), MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new COUNT_NOT_FOUND("Trip not found");
			else
				tripCount = jsonResponse.getInt("data");
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return tripCount;
	}

	public ParcelDashboardModel getMasterTripCount(String routeId, String startDate, String endDate) throws Exception {
		ParcelDashboardModel parcelDashboardModel = new ParcelDashboardModel();
		Map<String, Object> httpResponse = null;
		if (!CommonUtils.isNullCheck(startDate) && !CommonUtils.isNullCheck(endDate))
			httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getCustomerUrl() + "v1/dashboard/parcel/trip?routeId=" + routeId + "&startDate="
							+ startDate + "&endDate=" + endDate,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.CUSTOMER.name());
		else
			httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getCustomerUrl() + "v1/dashboard/parcel/trip?routeId=" + routeId,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.CUSTOMER.name());

		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Count not found");
			else {
				parcelDashboardModel = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(),
						ParcelDashboardModel.class);
			}
		return parcelDashboardModel;
	}

	public ParcelDashboardModel getMasterTripTotalWeight(String routeId, String startDate, String endDate)
			throws Exception {
		ParcelDashboardModel parcelDashboardModel = new ParcelDashboardModel();
		Map<String, Object> httpResponse = null;
		if (!CommonUtils.isNullCheck(startDate) && !CommonUtils.isNullCheck(endDate))
			httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getCustomerUrl() + "v2/parcel/trip/total/weight/" + routeId + "?startDate="
							+ startDate + "&endDate=" + endDate,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.FRANCHISE.name());
		else
			httpResponse = GenericHttpClient.doHttpGet(
					mmgProperties.getCustomerUrl() + "v2/parcel/trip/total/weight/" + routeId,
					ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
					MmgEnum.FRANCHISE.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null)
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Count not found");
			else {
				parcelDashboardModel = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(),
						ParcelDashboardModel.class);
			}
		return parcelDashboardModel;
	}
}
