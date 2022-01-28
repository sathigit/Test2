package com.atpl.mmg.AandA.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.atpl.mmg.AandA.constant.Constants;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils implements Constants {

	static ObjectMapper mapper = new ObjectMapper();
	private static String addStr = "globalPORTALAccessKEY";
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	private static HttpSession session = null;
	private static final long LIMIT = 1000000L;
	private static long last = 0;

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

	public static String getBlobData(Blob blob) {
		byte[] bdata = null;
		try {
			bdata = blob.getBytes(1, (int) blob.length());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(bdata);
	}

	public static List<Double> Get3Smallest(Double array[], int n) {
		Double firstmin = 6.00;
		Double secmin = 8.00;
		Double thirdmin = 5000.00;
		for (int i = 0; i < n; i++) {
			/*
			 * Check if current element is less than firstmin, then update first, second and
			 * third
			 */
			if (array[i] < firstmin) {
				thirdmin = secmin;
				secmin = firstmin;
				firstmin = array[i];
			}

			/*
			 * Check if current element is less than secmin then update second and third
			 */
			else if (array[i] < secmin) {
				thirdmin = secmin;
				secmin = array[i];
			}

			/*
			 * Check if current element is less than then update third
			 */
			else if (array[i] < thirdmin)
				thirdmin = array[i];
		}

		List<Double> mindistance = new ArrayList<Double>();
		mindistance.add(firstmin);
		mindistance.add(secmin);
		mindistance.add(thirdmin);

		return mindistance;
	}

	public static Double[] combine(Double[] a, Integer[] b) {
		int length = (a.length + b.length);
		Double[] result = new Double[length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static XSSFWorkbook createExcelWorkbook(String[] columnsHead, String sheetName) {
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet(sheetName);

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setFontName("Arial");

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		for (int count = 0; count < columnsHead.length; count++) {
			Cell cell = headerRow.createCell(count);
			cell.setCellValue(columnsHead[count]);
			cell.setCellStyle(headerCellStyle);
		}

		for (int i = 0; i < columnsHead.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}

	public static MultiValueMap<String, String> getFileHeaders(String fileName) {
		final MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-disposition", "attachment; filename=" + fileName);
		return headers;
	}

	public static List<Double> getNearestFranchise(Double distance[], int n, int maxDistance) {
		Double minDistance = 0.00;
		List<Double> nearestDistance = new ArrayList<Double>();
		for (int i = 0; i < n; i++) {
			if (distance[i] <= maxDistance) {
				minDistance = distance[i];
				nearestDistance.add(minDistance);
			}
		}
		Collections.sort(nearestDistance);
		return nearestDistance;
	}

	public static String getLastString(String str,String splitPattern) {
		String[] arrOfString = str.split(splitPattern);
		String vName = null;
		for (String a : arrOfString) {
			vName = a;
		}
		return vName;
	}
	
	public static boolean isNullCheck(String str) {
		boolean isNull = false;
		if(null == str || str.trim().length() == 0) {
			isNull = true;
		}
		return isNull;
	}
	
}
