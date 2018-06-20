package com.company.main;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesTest {

	public static void main(String[] args) throws Exception {
		Properties prop = new Properties();
		
		InputStream inStream = new FileInputStream("src/main/resources/WeiXin.properties");
		
		prop.load(inStream);
		
		String val = prop.getProperty("key");
		System.out.println(val);
		
//		Servlet的方法中使用，获取WEB-INF/classes的绝对路径
//		String s = Thread.currentThread().getContextClassLoader().getResource("/").getPath();  
//		System.out.println(s);
	}
	
}
