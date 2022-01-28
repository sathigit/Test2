package com.atpl.mmg.AandA.common;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Utility class for JSON to object conversion and vice versa.
 *
 * Note : please review the ObjectMapper configuration before using this utility
 * for your module.
 *
 * 
 * @author RAGHU M
 *
 */
public class JsonUtil {

	private static final Integer PRETTY_PRINT_INDENT_FACTOR = 4;
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private static final ObjectWriter OBJECT_WRITER_DEFAULT = OBJECT_MAPPER.writer();

	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		OBJECT_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	public static final <T> T fromJson(final byte[] s, final Class<T> type) throws Exception {
		final ObjectReader reader = OBJECT_MAPPER.readerFor(type);
		return reader.readValue(s);
	}

	public static final <T> T fromJson(final String s, final Class<T> type) throws Exception {
		final ObjectReader reader = OBJECT_MAPPER.readerFor(type);
		return reader.readValue(s);
	}

	public static final String toJsonString(final Object o) throws Exception {
		return OBJECT_WRITER_DEFAULT.writeValueAsString(o);
	}

	public static final String subJson(final String json, final String identifier) throws Exception {
		final JsonNode node = OBJECT_MAPPER.readValue(json, JsonNode.class);
		return node.findValues(identifier).get(0).asText();
	}

	public static final <T> T convertValue(final Object obj, final Class<T> type) {
		return OBJECT_MAPPER.convertValue(obj, type);
	}

	@SuppressWarnings("unchecked")
	public static final <T> List<T> parseJsonArray(final String json, final Class<T> type)
			throws IOException, ClassNotFoundException {
		Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + type.getName() + ";");
		T[] objects = OBJECT_MAPPER.readValue(json, arrayClass);
		return Arrays.asList(objects);
	}

	@SuppressWarnings("rawtypes")
	public static final <T> Map parseObjectToMap(final T obj, final Class<Map> class1)
			throws IOException, ClassNotFoundException {
		Map map = OBJECT_MAPPER.convertValue(obj, Map.class);
		return map;
	}

	public static final <T> T convertValueToPojo(final Object obj, final Class<T> type)
			throws IOException, ClassNotFoundException {
		byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(obj);
		T t = OBJECT_MAPPER.readValue(bytes, type);
		return t;
	}

	// Validate json
	public static final <T> String validateJsonObject(final T dto, final Class<T> type)
			throws IOException, ClassNotFoundException {
		String response = "";
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<T>> violations = validator.validate(dto);

		for (ConstraintViolation<T> violation : violations) {
			if (!violation.getMessage().isEmpty()) {
				response = response.concat(violation.getMessage() + ", ");
			}
		}
		return response;
	}

	// TODO: Need to optimize this logic.
	public static final String toJsonArray(final List<String> l) {
		final StringBuilder result = new StringBuilder("[");

		final Iterator<String> it = l.iterator();
		int index = 0;
		while (it.hasNext()) {
			if ((index > 0) && it.hasNext()) {
				result.append(",");
			}
			result.append(it.next());
			++index;
		}
		result.append("]");

		return result.toString();
	}

	// get uuid
	public static final String getUUID(final String str) {

		byte[] bytes = null;
		try {
			bytes = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		UUID uuid = UUID.nameUUIDFromBytes(bytes);
		return uuid.toString();
	}

	public static JSONObject getJsonObj(CloseableHttpResponse httpResponse) throws Exception {
		String result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8.name());
		JSONObject object = new JSONObject(result);
		return object;
	}

	public static JSONObject getJson(CloseableHttpResponse httpResponse) throws IOException, JSONException {
		try {
			String result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8.name());
			JSONObject object = new JSONObject(result);
			return object;
		} catch (Exception e) {
			throw e;
		}
	}

	public static String getString(CloseableHttpResponse httpResponse) throws Exception {
		try {
			String result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8.name());
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

}