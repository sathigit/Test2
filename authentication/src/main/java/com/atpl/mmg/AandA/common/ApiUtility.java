package com.atpl.mmg.AandA.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_READ_TIMEOUT;

public class ApiUtility {

	private static final Logger logger = LoggerFactory.getLogger(ApiUtility.class);

	public static CloseableHttpResponse doHttpPost(final String url, Map<String, String> headers, final String content)
			throws Exception {

		CloseableHttpResponse httpResponse = null;
		try {
			final HttpClientBuilder hcb = HttpClientBuilder.create();
			final CloseableHttpClient client = hcb.build();
			String correlationId = RequestCorrelation.getId();
			RequestConfig rc = RequestConfig.custom().setConnectTimeout(Constants.API_TIME_OUT)
					.setSocketTimeout(Constants.API_TIME_OUT).build();

			HttpRequestBase hrb = new HttpPost(url);
			hrb.setConfig(rc);
			if (headers != null) {
				Iterator<Map.Entry<String, String>> headersIterator = headers.entrySet().iterator();
				while (headersIterator.hasNext()) {
					Map.Entry<String, String> e = headersIterator.next();
					hrb.setHeader(e.getKey(), e.getValue());
					if (null != correlationId)
						hrb.setHeader(RequestCorrelation.CORRELATION_ID, correlationId);
				}
			}
			logger.info("start REST request to {} with correlationId {}", url, correlationId);
			if (content != null) {
				((HttpPost) hrb).setEntity(new StringEntity(content, StandardCharsets.UTF_8.name()));
			}
			httpResponse = client.execute(hrb);
		} catch (Exception e) {
			try {
				logger.error("Exception in doHttpPost Url" + url + e.getMessage());
			} catch (Exception e1) {
			}
			if (e instanceof ConnectTimeoutException) {
				throw new BACKEND_SERVER_READ_TIMEOUT();
			}
		}
		return httpResponse;
	}

	public static CloseableHttpResponse doHttpGet(final String url, Map<String, String> headers) throws Exception {
		CloseableHttpResponse httpResponse = null;
		try {
			final HttpClientBuilder hcb = HttpClientBuilder.create();
			final CloseableHttpClient client = hcb.build();
			String correlationId = RequestCorrelation.getId();
			RequestConfig rc = RequestConfig.custom().setConnectTimeout(Constants.API_TIME_OUT)
					.setSocketTimeout(Constants.API_TIME_OUT).build();

			HttpRequestBase hrb = new HttpGet(url);
			hrb.setConfig(rc);
			if (headers != null) {
				Iterator<Map.Entry<String, String>> headersIterator = headers.entrySet().iterator();
				while (headersIterator.hasNext()) {
					Map.Entry<String, String> e = headersIterator.next();
					hrb.setHeader(e.getKey(), e.getValue());
					if (null != correlationId)
						hrb.setHeader(RequestCorrelation.CORRELATION_ID, correlationId);
				}
			}
			logger.info("start REST request to {} with correlationId {}", url, correlationId);
			httpResponse = client.execute(hrb);
		} catch (Exception e) {
			try {
				logger.error("Exception in doHttpGet Url" + url + e.getMessage());
			} catch (Exception e1) {
			}
			if (e instanceof ConnectTimeoutException) {
				throw new BACKEND_SERVER_READ_TIMEOUT();
			}
		}
		return httpResponse;
	}

	public static CloseableHttpResponse doHttpPut(final String url, Map<String, String> headers, final String content)
			throws Exception {

		CloseableHttpResponse httpResponse = null;
		try {
			final HttpClientBuilder hcb = HttpClientBuilder.create();
			final CloseableHttpClient client = hcb.build();
			String correlationId = RequestCorrelation.getId();
			RequestConfig rc = RequestConfig.custom().setConnectTimeout(Constants.API_TIME_OUT)
					.setSocketTimeout(Constants.API_TIME_OUT).build();

			HttpRequestBase hrb = new HttpPut(url);
			hrb.setConfig(rc);
			if (headers != null) {
				Iterator<Map.Entry<String, String>> headersIterator = headers.entrySet().iterator();
				while (headersIterator.hasNext()) {
					Map.Entry<String, String> e = headersIterator.next();
					hrb.setHeader(e.getKey(), e.getValue());
					if (null != correlationId)
						hrb.setHeader(RequestCorrelation.CORRELATION_ID, correlationId);
				}
			}
			logger.info("start REST request to {} with correlationId {}", url, correlationId);
			if (content != null) {
				((HttpPut) hrb).setEntity(new StringEntity(content, StandardCharsets.UTF_8.name()));
			}
			httpResponse = client.execute(hrb);
		} catch (Exception e) {
			try {
				logger.error("Exception in doHttpPut Url" + url + e.getMessage());
			} catch (Exception e1) {
			}
			if (e instanceof ConnectTimeoutException) {
				throw new BACKEND_SERVER_READ_TIMEOUT();
			}
		}
		return httpResponse;
	}

	public static CloseableHttpResponse doHttpDelete(final String url, Map<String, String> headers) throws Exception {

		CloseableHttpResponse httpResponse = null;
		try {
			final HttpClientBuilder hcb = HttpClientBuilder.create();
			final CloseableHttpClient client = hcb.build();
			String correlationId = RequestCorrelation.getId();
			RequestConfig rc = RequestConfig.custom().setConnectTimeout(Constants.API_TIME_OUT)
					.setSocketTimeout(Constants.API_TIME_OUT).build();

			HttpRequestBase hrb = new HttpDelete(url);
			hrb.setConfig(rc);
			if (headers != null) {
				Iterator<Map.Entry<String, String>> headersIterator = headers.entrySet().iterator();
				while (headersIterator.hasNext()) {
					Map.Entry<String, String> e = headersIterator.next();
					hrb.setHeader(e.getKey(), e.getValue());
					if (null != correlationId)
						hrb.setHeader(RequestCorrelation.CORRELATION_ID, correlationId);
				}
			}
			logger.info("start REST request to {} with correlationId {}", url, correlationId);
			httpResponse = client.execute(hrb);
		} catch (Exception e) {
			try {
				logger.error("Exception in doHttpDelete Url" + url + e.getMessage());
			} catch (Exception e1) {
			}
			if (e instanceof ConnectTimeoutException) {
				throw new BACKEND_SERVER_READ_TIMEOUT();
			}
		}
		return httpResponse;
	}

	public static Map<String, String> getHeaders(String apiKey, String apiToken) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=UTF-8");
		headers.put("x-api-key", apiKey);
		if (null != apiToken)
			headers.put("authorization", apiToken);

		headers.put("x-bypass-token", "Yes");
		return headers;
	}

	public static Map<String, String> getOauthHeaders(String userName, String password) {
		Map<String, String> headers = new HashMap<String, String>();
		String auth = userName + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		headers.put("Content-Type", "application/json; charset=UTF-8");
		headers.put("Authorization", authHeader);

		return headers;
	}
}
