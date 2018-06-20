package com.company.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String errJson = "{\"errcode\":40013,\"errmsg\":\"invalid appid hint: [NCnA803501466]\"}";
		String corrJson = "{\"access_token\":\"10_Ugow4\",\"expires_in\":7200}";
		
		ObjectMapper objectMapper = new ObjectMapper();  
		JavaType jvt = objectMapper.getTypeFactory().constructParametricType(HashMap.class,String.class,String.class);  
        Map<String,String> urMap = objectMapper.readValue(corrJson, jvt);
        System.out.println(urMap);
        String errcode = urMap.get("errcode");
        String accessToken = urMap.get("access_token");
        if(errcode!=null&&!errcode.equals("0")) {
        	System.out.println("请求失败");
        }else if(accessToken!=null) {
        	System.out.println("获得到access_token");
        	System.out.println(accessToken);
        }
	}
	
	public static HashMap<String,String> json2Hashmap(String jsonStr){
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, String.class, String.class);
		HashMap<String,String> resultMap = null;
		try {
			resultMap = objectMapper.readValue(jsonStr, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}
