package com.atpl.mmg.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.atpl.mmg.common.ApiUtility;
import com.atpl.mmg.common.GenericHttpClient;
import com.atpl.mmg.common.MmgEnum;
import com.atpl.mmg.common.RequestCorrelation;
import com.atpl.mmg.common.Status;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.CommonUtils;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(basePackages = { "com.atpl.mmg" })
@SuppressWarnings("rawtypes")
public class MMGFilter implements Constants, Filter{
	@Autowired
	MMGProperties mmgProperties;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(MMGFilter.class);

	@SuppressWarnings("unused")
	public void init(FilterConfig config) throws ServletException {
		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext());
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("In doFilter");
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		String currentCorrId = request.getHeader(RequestCorrelation.CORRELATION_ID);
		if (!currentRequestIsAsyncDispatcher(request)) {
			if (currentCorrId == null) {
				currentCorrId = UUID.randomUUID().toString();
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"No correlationId found in Header. Generated " + currentCorrId));
			} else {
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADMIN_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(), "Found correlationId in Header :" + currentCorrId));
			}
			RequestCorrelation.setId(currentCorrId);
		}

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers",
				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,X-API-KEY,X-API-SOURCE,Authorization");
		response.addHeader("Access-Control-Expose-Headers",
				"Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addIntHeader("Access-Control-Max-Age", 3600);
		response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		
		String url = request.getRequestURL().toString();
		String state = null; 
		String city = null;
		String vehicleCategory = null,aadharNumber=null,accountNumber=null,panNumber=null,organisation=null,termsAndConditionsOnRole=null;
		if (url.contains("swagger") || url.contains("api-docs")
				|| request.getRequestURI().equals(request.getContextPath() + "/health")) {
			chain.doFilter(request, response);
		} else {
			String xApiKey = request.getHeader(ACCESS_KEY_HEADER);
			String xApiSource = request.getHeader(ACCESS_SOURCE_HEADER);
			String accessToken = request.getHeader(BEARER_TOKEN);
			
			String jsonResponse = null;
			if(!request.getMethod().equalsIgnoreCase("OPTIONS")) {
				jsonResponse = validateRequest(xApiKey,response);
			}
			if(url.contains("/State/country/")){
				 state = request.getRequestURI().substring(request.getContextPath().length());
			}
			if(url.contains("city")){
				 city = request.getRequestURI().substring(request.getContextPath().length());
			}
			if(url.contains("/vehicleImage/category/id")){
				vehicleCategory = request.getRequestURI().substring(request.getContextPath().length());
			}
			if(url.contains("/validateAadharNumber/")){
				aadharNumber = request.getRequestURI().substring(request.getContextPath().length());
			}
			if(url.contains("/validateAccountNumber/")){
				accountNumber = request.getRequestURI().substring(request.getContextPath().length());
			}
			if(url.contains("/validatePanNumber/")){
				panNumber = request.getRequestURI().substring(request.getContextPath().length());
			}
			if(url.contains("/organization")){
				organisation = request.getRequestURI().substring(request.getContextPath().length());
			}
			if(url.contains("/terms/conditions/role")){
				organisation = request.getRequestURI().substring(request.getContextPath().length());
			}
			
			if (jsonResponse == null && !request.getMethod().equalsIgnoreCase("OPTIONS")) {
				mmgProperties.setAccessTokenKey(accessToken);
				String requestPath = request.getRequestURI().substring(request.getContextPath().length());
				
				String skipToken[] = mmgProperties.getSkipToken().split(",");
				if(null == xApiSource || xApiSource.isEmpty() || !Arrays.asList(skipToken).contains(xApiSource)) {
					String xByPassToken = request.getHeader(BY_PASS_TOKEN);
					if(!"Yes".equalsIgnoreCase(xByPassToken))
						try {
							jsonResponse = validateApiToken(accessToken, requestPath, Arrays.asList("/v1/country","/v1/goodsType", "/v1/dashboard/customer/trip/vehicle", 
									"/v1/weight", state, city, vehicleCategory,aadharNumber,accountNumber,panNumber,organisation,"/v1/getTotalFranchise", "/v1/getTotalFleet", "/v1/boarding/request", "/v1/vehicleCategory/",
									"/v1/blood", "/v1/bank", "/v1/vehicleCategory/Id/","/v1/dashboard/customer/trip/vehicle","/v1/getDriverType","/v1/getLicenceCat"),response);
						} catch (Exception e) {
							logger.error("Exception: " + e.getMessage());
							response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
							jsonResponse = getBackendServerError(e);
						}
				}
			}
			
			if (jsonResponse != null) {
			//	response.setStatus(401);
				response.getWriter().println(jsonResponse);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	private boolean currentRequestIsAsyncDispatcher(HttpServletRequest request) {
		return request.getDispatcherType().equals(DispatcherType.ASYNC);
	}

	public void destroy() {
	}
	
	public String validateApiToken(String accessToken, String requestPath, List<String> exludePathList,HttpServletResponse resp) throws Exception {
		GenericRes response = new GenericRes();
		if (!exludePathList.contains(requestPath)) {
			Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(mmgProperties.getOauthUrl() + "v1/oauth/loadAuthentication",
					ApiUtility.getHeaders("", accessToken), MmgEnum.OAUTH.name());

			int statusCode = (int) httpResponse.get("statusCode");
			JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
			if (jsonResponse != null) {
				if (statusCode != HttpStatus.OK.value()) {
					response.setExCode("INVALID_ACCESS_TOKEN");
					response.setResCode(1012);
					response.setMessage(jsonResponse.get("error_description").toString());
					response.setStatus(Status.FAILURE);
					resp.setStatus(HttpStatus.UNAUTHORIZED.value());
					return CommonUtils.getJson(response);
				}
			} 
			
		}
		return null;
	}


	public String validateRequest(String xApiKey,HttpServletResponse resp) {
		GenericRes response = new GenericRes();
		if (!mmgProperties.getHeaderApiKey().equalsIgnoreCase(xApiKey)) {
			response.setExCode("INVALID_HEADER_API_KEY");
			response.setResCode(1011);
			response.setMessage("Invalid Header Api Key");
			response.setStatus(Status.FAILURE);
			resp.setStatus(HttpStatus.UNAUTHORIZED.value());
			return CommonUtils.getJson(response);
		}
		return null;
	}

	private String getBackendServerError(Exception e) {
		GenericRes response = new GenericRes(e);
		return CommonUtils.getJson(response);
	}

}