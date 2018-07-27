package com.company.main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 【微信配置文件读取】
 * 1.有Properties.class基础的功能 2.简化load
 * properites文件步骤（后期可加入：直接在servletContext中，预先读取WeiXin.properties文件），简化读取微信相关key的步骤
 * 
 * @author LiShan
 * Since: 2018-07-25 10:36:46
 */
public class PropertiesUtil {
	//	（还可以使用与servletContext结合的方案）
	private static Properties properties;
	static{
		init();
	}

	/*
	 * 微信需要的配置的key，列表
	 */
	static final String TOKEN = "TOKEN";
	static final String APPID = "APPID";
	static final String APPSECRET = "APPSECRET";

	/*
	 * 初始化
	 */
	private static void init() {
		InputStream input = null;
		try {
			input = PropertiesUtil.class.getResourceAsStream("/WeiXin.properties");
			if(input==null)
				throw new RuntimeException("没有找到微信配置文件");
			
			properties = new Properties();
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("加载配置文件失败", e);
		} finally {
			try {
				input.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

	/*
	 * 较旧的设计方案
	 */
	@Deprecated
	public static String getValue(InputStream in, String key) throws Exception {
		Properties prop = new Properties();
		prop.load(in);
		return prop.getProperty(key);
	}
	@Deprecated
	public static String getValue(String filePath, String key) throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		return getValue(in, key);
	}
	@Deprecated
	public static String getWX_token(InputStream in) throws Exception {
		return getValue(in, TOKEN);
	}


	/*
	 * 较新的设计方案
	 */
	public static void reload() {
		init();
	}
	public static String getValue(String key) {
		return properties.getProperty(key);
	}
	public static String getWX_token() {
		return properties.getProperty(TOKEN);
	}
	public static String getWX_appid() {
		return properties.getProperty(APPID);
	}
	public static String getWX_appsecret() {
		return properties.getProperty(APPSECRET);
	}
}
