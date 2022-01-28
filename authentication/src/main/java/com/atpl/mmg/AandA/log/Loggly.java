package com.atpl.mmg.AandA.log;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.common.RequestCorrelation;

public class Loggly {

	private static final Logger logger = LogManager.getLogger(Loggly.class);
	public static void sendLogglyEvent(final String eventString) {
		logger.debug(eventString);
	}

	public static String mapReqResLogglyData(String reqBody, String resBody, Map<String, String> headers, String method,
			String url, int statusCode, String serviceName) {

		final LogglyData logglyDataTO = new LogglyData();
		logglyDataTO.setTxnId(RequestCorrelation.getId());
		logglyDataTO.setService(serviceName);

		logglyDataTO.setReqBody(reqBody);
		logglyDataTO.setReqHeaders(headers);
		logglyDataTO.setReqPath(url);
		logglyDataTO.setReqMethod(method);

		logglyDataTO.setResBody(resBody);
		logglyDataTO.setStatusCode(statusCode);

		String log = null;
		try {
			log = JsonUtil.toJsonString(logglyDataTO);
		} catch (Exception e) {
			// wont happen as only string can go bad, but it is handled as
			// string
		}

		return log;
	}
}
