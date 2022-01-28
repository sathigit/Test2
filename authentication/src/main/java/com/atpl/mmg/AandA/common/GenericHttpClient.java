package com.atpl.mmg.AandA.common;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.atpl.mmg.AandA.exception.MmgRestException.ACCESS_DENIED;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_PARSE_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.FORBIDDEN;

public class GenericHttpClient {

	private static final Logger logger = LoggerFactory.getLogger(GenericHttpClient.class);

	public static Map<String, Object> doHttpPost(final String url, Map<String, String> headers, final String reqBody,
			String serviceName) throws Exception {
		final CloseableHttpResponse closeableHttpResponse = ApiUtility.doHttpPost(url, headers, reqBody);
		return getResponse(reqBody, url, headers, closeableHttpResponse, HttpPost.METHOD_NAME, serviceName);
	}

	public static Map<String, Object> doHttpGet(final String url, Map<String, String> headers, String serviceName)
			throws Exception {
		final CloseableHttpResponse closeableHttpResponse = ApiUtility.doHttpGet(url, headers);
		return getResponse(null, url, headers, closeableHttpResponse, HttpGet.METHOD_NAME, serviceName);
	}
	
	public static Map<String, Object> doHttpDelete(final String url, Map<String, String> headers, String serviceName)
			throws Exception {
		final CloseableHttpResponse closeableHttpResponse = ApiUtility.doHttpDelete(url, headers);
		return getResponse(null, url, headers, closeableHttpResponse, HttpDelete.METHOD_NAME, serviceName);
	}
	
	public static Map<String, Object> doHttpPut(final String url, Map<String, String> headers, final String reqBody,
			String serviceName) throws Exception {
		final CloseableHttpResponse closeableHttpResponse = ApiUtility.doHttpPut(url, headers, reqBody);
		return getResponse(reqBody, url, headers, closeableHttpResponse, HttpPut.METHOD_NAME, serviceName);
	}

	
	private static Map<String, Object> getResponse(final String reqBody, final String url, Map<String, String> headers,
			final CloseableHttpResponse closeableHttpResponse, String method, String serviceName) throws Exception {
		if (closeableHttpResponse == null) {
			logger.error("Backend Server Url Error" + url);
			/*
			 * Loggly.sendLogglyEvent( Loggly.mapReqResLogglyData(reqBody, null, headers,
			 * method, url, HttpStatus.INTERNAL_SERVER_ERROR.value(), serviceName));
			 */
			throw new BACKEND_SERVER_ERROR();
		}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		final int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		String response = JsonUtil.getString(closeableHttpResponse);
		logger.info("");
		/*
		 * Loggly.sendLogglyEvent( Loggly.mapReqResLogglyData(reqBody, response,
		 * headers, method, url, statusCode, serviceName));
		 */
		JSONObject responseBody = null;
		if(!(url.contains("/oauth/loadAuthentication")|| url.contains("/oauth/token")) ) {
		if (statusCode == HttpStatus.UNAUTHORIZED.value())
			throw new ACCESS_DENIED();
		else if (statusCode == HttpStatus.FORBIDDEN.value())
			throw new FORBIDDEN();
		}
		
		try {
			responseBody = JsonUtil.convertValue(response, JSONObject.class);
		} catch (Exception e) {
			throw new BACKEND_PARSE_ERROR();
		}

		map.put("response", responseBody);
		map.put("statusCode", statusCode);
		return map;
	}
}