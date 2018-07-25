package com.company.main;

public class HttpClientTest {
	
	public static void main(String[] args) throws Exception {
		String access_token = HttpClientUtil.getAccessToken();
		System.out.println(access_token);
	}



}
