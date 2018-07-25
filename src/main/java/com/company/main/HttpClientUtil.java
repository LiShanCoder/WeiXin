package com.company.main;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	public static void main(String[] args) throws Exception {
		//使用示例
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/token")
				.setParameter("grant_type","client_credential")
				.setParameter("appid", "")
				.setParameter("secret", "")
				.build();
		String result = sendRequestGet(uri);
		System.out.println(result);
	}

    public static String sendRequestGet(URI uri) throws Exception{
    	HttpGet request = new HttpGet(uri);
        //响应体 = CloseableHttpClient.发送请求(请求体)
        CloseableHttpResponse response = HttpClients.createDefault().execute(request);
        
        //获取结果实体
        String result = getRespBody(response.getEntity());

        //释放链接
        response.close();
        return result;
    }
    
    private static String getRespBody(HttpEntity entity) throws Exception {
    	if(entity==null)
    		return null;
    	
    	String result = EntityUtils.toString(entity, "UTF-8");
    	EntityUtils.consume(entity);
    	return result;
    }
}
