package com.atpl.mmg.log;



import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.atpl.mmg.common.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class LogglyData implements Serializable {

	
	private String txnId;
	private String service;
	private String timestamp;
	private Status severity;
	private String reqBody;
	private Map<String, String> reqHeaders;
	private String reqPath;
	private String reqMethod;
	private String resBody;
	private Map<String, String> resHeaders;
	private String errorMsg;
	private int statusCode;
	private long timeTakenInMillis;
	private Map<String, String> securityContext;
	
	public LogglyData() {
		timestamp = new Date().toString();
		severity = Status.INFORMATION;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Status getSeverity() {
		return severity;
	}

	public void setSeverity(Status severity) {
		this.severity = severity;
	}

	public String getReqBody() {
		return reqBody;
	}

	public void setReqBody(String reqBody) {
		this.reqBody = reqBody;
	}

	public Map<String, String> getReqHeaders() {
		return reqHeaders;
	}

	public void setReqHeaders(Map<String, String> reqHeaders) {
		this.reqHeaders = reqHeaders;
	}

	public String getReqPath() {
		return reqPath;
	}

	public void setReqPath(String reqPath) {
		this.reqPath = reqPath;
	}

	public String getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
	}

	public String getResBody() {
		return resBody;
	}

	public void setResBody(String resBody) {
		this.resBody = resBody;
	}

	public Map<String, String> getResHeaders() {
		return resHeaders;
	}

	public void setResHeaders(Map<String, String> resHeaders) {
		this.resHeaders = resHeaders;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public long getTimeTakenInMillis() {
		return timeTakenInMillis;
	}

	public void setTimeTakenInMillis(long timeTakenInMillis) {
		this.timeTakenInMillis = timeTakenInMillis;
	}

	public Map<String, String> getSecurityContext() {
		return securityContext;
	}

	public void setSecurityContext(Map<String, String> securityContext) {
		this.securityContext = securityContext;
	}
}
