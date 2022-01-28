package com.atpl.mmg.AandA.common;

public class RequestCorrelation {
	
	public static final String CORRELATION_ID = "correlationId";
	
	private static final ThreadLocal<String> id = new ThreadLocal<String>();
	
	private static final ThreadLocal<Long> startTime = new ThreadLocal<Long>();

	public static String getId() {
		return id.get();
	}

	public static void setId(String correlationId) {
		id.set(correlationId);
	}

	public static Long getStartTime() {
		return startTime.get();
	}
	
	public static void setStartTime(Long apiStartTime) {
		startTime.set(apiStartTime);
	}
}