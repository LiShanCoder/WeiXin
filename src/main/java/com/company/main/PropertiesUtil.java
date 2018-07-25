package com.company.main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 1.有Properties.class基础的功能 2.简化load
 * properites文件步骤（后期可加入：直接在servletContext中，预先读取WeiXin.properties文件），简化读取微信相关key的步骤
 * 
 * @author LiShan
 * Since: 2018-07-25 10:36:46
 */
public class PropertiesUtil {
	//	（还可以使用与servletContext结合的方案）
	private static InputStream propInput;
	private static Properties properties;
	static{
		initInput();
		if(propInput==null)
			throw new RuntimeException("没有找到微信配置文件");
		initProperties();
	}

	/*
	 * 微信需要的配置的key，列表
	 */
	static final String TOKEN = "TOKEN";

	/*
	 * 初始化
	 */
	private static void initInput() {
		propInput = PropertiesUtil.class.getResourceAsStream("/WeiXin.properties");
	}
	private static void initProperties() {
		properties = new Properties();
		try {
			properties.load(propInput);
		} catch (IOException e) {
			throw new RuntimeException("加载配置文件错误", e);
		}
	}

	/*
	 * 较旧的设计方案
	 */
	public static String getValue(InputStream in, String key) throws Exception {
		Properties prop = new Properties();
		prop.load(in);
		return prop.getProperty(key);
	}
	public static String getValue(String filePath, String key) throws Exception {
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		return getValue(in, key);
	}
	public static String getWX_token(InputStream in) throws Exception {
		return getValue(in, TOKEN);
	}


	/*
	 * 较新的设计方案
	 */
	public static void reload() {
		initInput();
		if(propInput==null)
			throw new RuntimeException("没有找到微信配置文件");
		initProperties();
	}
	public static String getValue(String key) {
		return properties.getProperty(key);
	}
	public static String getWX_token() {
		return properties.getProperty(TOKEN);
	}
}
