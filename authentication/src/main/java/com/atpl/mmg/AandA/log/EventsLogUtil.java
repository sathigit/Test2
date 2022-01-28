package com.atpl.mmg.AandA.log;

import java.util.Calendar;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.common.RequestCorrelation;


public class EventsLogUtil {

	/**
	 * Call this while sending events
	 * 
	 * @param service
	 * @param sev
	 * @param msg
	 */
	public static String prepareLogEvent(final String service, final int sev, final String msg) {

		final EventMsg<Object> eventMsg = new EventMsg<>();

		eventMsg.setTxnId(RequestCorrelation.getId());
		eventMsg.setTimestamp(Calendar.getInstance().getTime());
		eventMsg.setService(service);
		eventMsg.setSev(sev);
		eventMsg.setMsg(msg);
		eventMsg.setTxnId(RequestCorrelation.getId());

		try {
			return JsonUtil.toJsonString(eventMsg);
		} catch (Exception e) {
			// do nothing
		}
		return null;
	}

	public static String prepareLogEvent(final String service, final int sev, final String msg, String correlationId) {

		final EventMsg<Object> eventMsg = new EventMsg<>();

		eventMsg.setTxnId(RequestCorrelation.getId());
		eventMsg.setTimestamp(Calendar.getInstance().getTime());
		eventMsg.setService(service);
		eventMsg.setSev(sev);
		eventMsg.setMsg(msg);
		eventMsg.setTxnId(correlationId);

		try {
			return JsonUtil.toJsonString(eventMsg);
		} catch (Exception e) {
			// do nothing
		}
		return null;
	}
}
