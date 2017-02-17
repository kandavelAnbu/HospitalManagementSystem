package com.objectfrontier.training.habs.api;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static String toJson(Object input) {
		try {
			return mapper.writeValueAsString(input);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
