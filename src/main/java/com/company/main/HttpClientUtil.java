package com.company.main;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 【微信access_token获取】
 * 		一、请求：
 * 			Https GET请求
 * 			https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid= {APPID} &secret= {APPSECRET}
 * 		二、请求参数：
 * 			参数			是否必须		说明
 *			grant_type	是			获取access_token填写client_credential
 *			appid		是			第三方用户唯一凭证，即appID
 *			secret		是			第三方用户唯一凭证密钥，即appsecret
 *		三、返回值：
 *			参数				说明
 *			access_token	获取到的凭证
 *			expires_in		凭证有效时间，单位：秒
 *
 * @author LiShan
 * Since: 2018-07-25 06:21:55
 */
public class HttpClientUtil {

	/*
	 * 旧的设计方案
	 */
    public static String sendRequestGet(String url, Map<String,String> params,String encoding) throws ParseException, IOException{
        String body = "";
        
        //装填参数
        List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
        if(params!=null){
            for (Entry<String, String> e : params.entrySet()) {
                nvpList.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            }
        }
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvpList, encoding);
        String paramsStr = EntityUtils.toString(urlEncodedFormEntity);

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvpList.toString());
        System.out.println("请求参数："+paramsStr);

        //创建post方式请求对象
        HttpGet httpGet = new HttpGet(url + "?" + paramsStr);
//        //设置header信息
//        //指定报文头【Content-type】、【User-Agent】
//        httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
//        httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//        
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpGet);
        
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        
        //释放链接
        response.close();
        System.out.println(body);
        return body;
    }
    
    private static String ACCESS_TOKEN = "access_token";
    private static String ERRMSG = "errmsg";
    public static String getWX_accessToken() throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/token")
				.setParameter("grant_type","client_credential")
				.setParameter("appid", PropertiesUtil.getWX_appid())
				.setParameter("secret", PropertiesUtil.getWX_appsecret())
				.build();
		String jsonResult = sendRequestGet(uri);
		
    	return getAccessTokenFrom(jsonResult);
    }
    
    private static String getAccessTokenFrom(String jsonStr) throws Exception {
    	HashMap<String, String> map = JacksonUtil.json2Hashmap(jsonStr);
    	if(map.get(ERRMSG)!=null)
    		throw new Exception(map.get("errcode") + ":" + map.get(ERRMSG));
    	if(map.get(ACCESS_TOKEN)==null)
    		throw new Exception("未知错误，即没错误代码，也没access_token");
    	return map.get(ACCESS_TOKEN);
    }
    
    /*
     * 新的设计方案
     */
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
