package com.atpl.mmg.AandA.exception;

import static com.atpl.mmg.AandA.common.Status.FAILURE;
import static com.atpl.mmg.AandA.common.Status.SUCCESS;
import static com.atpl.mmg.AandA.exception.MmgRestException.INTERNAL_ERROR_CODE;
import static org.apache.http.HttpStatus.SC_OK;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.atpl.mmg.AandA.common.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @author Raghu M
 * @since 2020-02-09
 */

@JsonInclude(Include.NON_NULL)
public class GenericRes<T> {

	@JsonIgnore
	private Integer httpStatusCode = SC_OK;

	private String exCode;
	private Integer resCode;
	//private int status = 200;
	private Status status = SUCCESS;
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	private String id;
	private Object pigyBack;
	private T data;
	private String reqId;
	private String url;

	private String message;
	

	@JsonIgnore
	private Throwable exception;

	private Map<String, Object> meta;
	private Map<String, String> dictionary;

	public GenericRes() {
		super();
	}

	public GenericRes(Throwable exception) {
		resCode = Integer.MAX_VALUE;
		if (exception == null) {
			return;
		}

		exCode = exception.getClass().getSimpleName();
		this.setException(exception);
		if (exception instanceof MmgRestException) {
			MmgRestException ae = (MmgRestException) exception;
			status =  FAILURE;
			resCode = ae.getErrorCode();
			message = ae.getMessage();
		
			this.meta = ae.getMeta();
			this.dictionary = ae.getDictionary();
			this.httpStatusCode = ae.getHttpErrorCode();
		} else if (exception instanceof MethodArgumentNotValidException) {
			resCode = HttpStatus.SC_BAD_REQUEST;
			message = exception.getMessage();
			status = FAILURE;
		} else {
			resCode = INTERNAL_ERROR_CODE;
			message = exception.getMessage();
			status = FAILURE;
		}
	}

	public GenericRes(T data) {
		this.data = data;
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getExCode() {
		return exCode;
	}

	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

	public Integer getResCode() {
		return resCode;
	}

	public void setResCode(Integer resCode) {
		this.resCode = resCode;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getPigyBack() {
		return pigyBack;
	}

	public void setPigyBack(Object pigyBack) {
		this.pigyBack = pigyBack;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	public Map<String, String> getDictionary() {
		return dictionary;
	}

	public void setDictionary(Map<String, String> dictionary) {
		this.dictionary = dictionary;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
