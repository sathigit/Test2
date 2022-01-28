package com.atpl.mmg.AandA.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author Raghu M
 * @since 2020-02-09
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class HttpResponseUtils {
    
	private static final MultiValueMap<String, String> COMMON_HTTP_HEADERS_MAP = new LinkedMultiValueMap<String, String>();

	static {
		COMMON_HTTP_HEADERS_MAP.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		COMMON_HTTP_HEADERS_MAP.add("Access-Control-Allow-Origin", "*");
	}

	public static final MultiValueMap<String, String> getCommonHttpHeaders() {
		return COMMON_HTTP_HEADERS_MAP;
	}

	public static final <T> ResponseEntity<byte[]> prepareResponse(final byte[] body,
			MultiValueMap<String, String> header) {
		return new ResponseEntity<byte[]>(body, header, HttpStatus.OK);
	}
//    
//    public static final <T> ResponseEntity<T> prepareResponse(final T body) {
//        return new ResponseEntity<T>(body, getCommonHttpHeaders(), HttpStatus.OK);
//    }

	public static final ResponseEntity<GenericRes> prepareErrorResponse(final MmgRestException ex) {
		return prepareErrorResponse(ex, ex.getHttpErrorCode());
	}

	public static final ResponseEntity<GenericRes> prepareErrorResponse(final Throwable ex, final int statusCode) {
		return prepareErrorResponse(ex, statusCode, null);
	}

	public static final ResponseEntity<GenericRes> prepareErrorResponse(final Throwable ex, final int statusCode,
			final String message) {
		GenericRes<Void> genericRes = new GenericRes<Void>(ex);
		if (message != null) { // case when message has to be overridden by custom message
			genericRes.setMessage(message);
		}
		return new ResponseEntity<GenericRes>(genericRes, getCommonHttpHeaders(), HttpStatus.valueOf(statusCode));
	}

	public static final <T> ResponseEntity<GenericRes> prepareSuccessResponse(final T data) {
		return prepareSuccessResponse(data, null, null);
	}

	public static final <T> ResponseEntity<GenericRes> prepareSuccessResponse(final T data, final String message) {
		return prepareSuccessResponse(data, message, null);
	}

	public static final <T> ResponseEntity<GenericRes> prepareSuccessResponse(final T data,
			final MultiValueMap<String, String> headers) {
		return prepareSuccessResponse(data, null, headers);
	}

	public static final <T> ResponseEntity<GenericRes> prepareSuccessResponse(final T data, final String message,
			final MultiValueMap<String, String> headers) {
		final GenericRes response = new GenericRes();
		response.setData(data);
		response.setMessage(message);
		if (headers != null && !headers.isEmpty()) {
			headers.putAll(getCommonHttpHeaders());
			return new ResponseEntity<GenericRes>(response, headers, HttpStatus.OK);
		}
		return new ResponseEntity<GenericRes>(response, getCommonHttpHeaders(), HttpStatus.OK);
	}
}
