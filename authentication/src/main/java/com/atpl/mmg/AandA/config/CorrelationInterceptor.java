package com.atpl.mmg.AandA.config;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class CorrelationInterceptor extends HandlerInterceptorAdapter {

	private static final String CORRELATION_ID_HEADERNAME = "X-Correlation-Id";
	private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		final String correlationId = getCorrelationIdFromHeader(request);
		MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
		return true;
	}

	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, final Exception ex) throws Exception {
		MDC.remove(CORRELATION_ID_LOG_VAR_NAME);
	}

	private String getCorrelationIdFromHeader(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String correlationId = request.getHeader(CORRELATION_ID_HEADERNAME);
		if (StringUtils.isBlank(correlationId)) {
			correlationId = generateUniqueCorrelationId();
		}
		return null;
	}

	private String generateUniqueCorrelationId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

}
