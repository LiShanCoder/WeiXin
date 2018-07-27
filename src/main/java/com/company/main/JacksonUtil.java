package com.company.main;

import java.util.HashMap;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

	public static HashMap<String,String> json2Hashmap(String jsonStr){
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, String.class, String.class);

		try {
			return objectMapper.readValue(jsonStr, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
