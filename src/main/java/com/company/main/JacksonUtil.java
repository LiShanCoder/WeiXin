package com.company.main;

import java.util.HashMap;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

	public static HashMap<String,String> json2Hashmap(String jsonStr) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, String.class, String.class);

		return objectMapper.readValue(jsonStr, javaType);
	}
}
