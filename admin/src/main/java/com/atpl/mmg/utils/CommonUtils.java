package com.atpl.mmg.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CommonUtils implements Constants {

	static ObjectMapper mapper = new ObjectMapper();
	private static String addStr = "globalPORTALAccessKEY";
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	private static HttpSession session = null;
	
	public static String encriptString(String strToEncript) {

		String returnString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strToEncript.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			returnString = sb.toString();
			return returnString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return returnString;
		}
	}

	public static String encriptURL(String urlString) {

		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("url", urlString);
		map.put("accessKey", addStr);

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(map.toString().getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String generateRandomId() {
		return UUID.randomUUID().toString();
	}

	public static String getJson(Object obj) {
		try {
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("getJsonResponse:Error in json processing: ", e);
		}
		return "";
	}

	public static Object getObject(String str) throws IOException {
		try {
			return mapper.readValue(str, Object.class);
		} catch (JsonProcessingException e) {
			logger.error("getJsonResponse:Error in json processing: ", e);
		}
		return "";
	}

	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());
		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});
		return sortedEntries;
	}

	public static List<Integer> getMonthYearSeries(int start, int end) {
		int series = start;
		List<Integer> list = new LinkedList<Integer>();
		while (!(series == end)) {
			list.add(series);
			if (series / 10000 == 12) {
				series = series + 1;
				series = series - 110000;
			} else {
				series = series + 10000;
			}
		}
		list.add(end);
		return list;
	}

	public String termsAndConditionVersion(String version) {
		double v = 0.0;
		String[] arrOfStr = version.split(" ");
		for (String a : arrOfStr) {
			version = a;
		}
		v = Double.parseDouble(version);
		System.out.println(v);
		v = v + 0.1;
		System.out.println(v);
		DecimalFormat df = new DecimalFormat("0.0");
		version = df.format(v);
		version = "v " + version;
		return version;
	}

	public static HttpSession geSsession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		if (session == null)
			session = attr.getRequest().getSession(true);
		return session;
	}

	public static String getUserId(String userName) {
		HttpSession session = CommonUtils.geSsession();
		return (String) session.getAttribute(userName);
	}

	public static List<Integer> stringToListOfIntegerConversion(String string) {
		String[] items = string.split("\\s*,\\s*");
		List<String> listOfString = Arrays.asList(items);
		List<Integer> listOfInteger = convertStringListToIntList(listOfString, Integer::parseInt);
		return listOfInteger;
	}

	public static List<String> stringToListOfStringConversion(String string) {
		String str[] = string.split(",");
		List<String> stringList = new ArrayList<String>();
		stringList = Arrays.asList(str);
		return stringList;
	}

	public static List<Double> stringToListOfDoubleConversion(String string) {
		String[] items = string.split("\\s*,\\s*");
		List<String> listOfString = Arrays.asList(items);
		List<Double> listOfInteger = convertStringListToIntList(listOfString, Double::parseDouble);
		return listOfInteger;
	}

	public static <T, U> List<U> convertStringListToIntList(List<T> listOfString, Function<T, U> function) {
		return listOfString.stream().map(function).collect(Collectors.toList());
	}

	public static String listOfIntgerToStringConversion(List<Integer> listOfString) {
		StringBuilder commaSepValueBuilder = new StringBuilder();
		for (int i = 0; i < listOfString.size(); i++) {
			commaSepValueBuilder.append(listOfString.get(i));
			if (i != listOfString.size() - 1) {
				commaSepValueBuilder.append(",");
			}
		}
		String cList = commaSepValueBuilder.toString();
		return cList;

	}

	public static String listOfStringToStringConversion(List<String> listOfString) {
		StringBuilder commaSepValueBuilder = new StringBuilder();
		for (int i = 0; i < listOfString.size(); i++) {
			commaSepValueBuilder.append(listOfString.get(i));
			if (i != listOfString.size() - 1) {
				commaSepValueBuilder.append(",");
			}
		}
		String cList = commaSepValueBuilder.toString();
		return cList;

	}

	public static double amountSplit(double percentage, double totalAmount) {
		double amount = (percentage / 100) * totalAmount;
		/*
		 * with using Floor Method (Math.floor(amount))
		 * 
		 */
		return (Math.round(amount * 100.0) / 100.0);

	}

	public static String removeEndingStringAndAppendwithDots(String string) {
		List<String> listOfString = stringToListOfStringConversion(string);
		for (int i = 0; i <= 1; i++) {
			listOfString.remove(listOfString.size() - 1);
		}
		string = listOfStringToStringConversion(listOfString);
		string += "...";
		return string;
	}

	public static Map<String, Integer> getPagination(Map<String, String> reqParam) {
		Map<String, Integer> paginationCount = new HashMap<>();
		int lowerBound = 0;
		int upperBound = 0;
		int pageNo = 0, pageSize = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("pageNo")) {
				pageNo = Integer.parseInt(reqParam.get("pageNo"));
			}

			if (reqParam.containsKey("pageSize")) {
				pageSize = Integer.parseInt(reqParam.get("pageSize"));
			}

			if (reqParam.containsKey("pageNo") && reqParam.containsKey("pageSize")) {
				if (pageNo <= 0 || pageSize <= 0)
					throw new NOT_FOUND("PageNo or page size should be grater than zero");
			}
		}

		lowerBound = pageNo - 1;

		if (pageNo > 1) {
			lowerBound = (pageNo - 1) * pageSize;
		}
		upperBound = pageSize;

		paginationCount.put("lowerBound", lowerBound);
		paginationCount.put("upperBound", upperBound);

		return paginationCount;
	}

	public static String convertJsonStringtoString(String jsonString) {
		jsonString = jsonString.substring(1, jsonString.length() - 1);
		jsonString = jsonString.replaceAll("\\\\\"", "\"");
		return jsonString;
	}

	public static boolean isNullCheck(String str) {
		boolean isNull = false;
		if (null == str || str.trim().length() == 0) {
			isNull = true;
		}
		return isNull;
	}

	public static <T, U> List<U> convertIntListToStringList(List<T> listOfInteger, Function<T, U> function) {
		return listOfInteger.stream().map(function).collect(Collectors.toList());
	}

	private static List<String> addAllStringToList(String[] strArr) {
		List<String> stringList = new ArrayList<String>();
		if (0 < strArr.length) {
			for (String str : strArr) {
				stringList.add(str);
			}
		}
		return stringList;
	}

	public static String convertStringArrayToString(String[] strFirstArray, String[] strSecondArray) {
		List<String> concatenatedString = new ArrayList<String>();
		if(null !=strFirstArray ) {
		if (0 < strFirstArray.length)
			concatenatedString.addAll(addAllStringToList(strFirstArray));
		}
		if(null !=strSecondArray ) {
		if (0 < strSecondArray.length)
			concatenatedString.addAll(addAllStringToList(strSecondArray));
		}
		String csvString = String.join(",", concatenatedString);
		System.out.println(csvString);
		return csvString;
	}

	public static MultiValueMap<String, String> getFileHeaders(String fileName) {
		final MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-disposition", "attachment; filename=" + fileName);
		return headers;
	}

}
