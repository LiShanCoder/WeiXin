package com.company.main;

import java.io.IOException;
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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	
	/**
	 * 获取access_token
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
	 */
	public static void main(String[] args) throws ParseException, IOException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("grant_type","client_credential");
		paramMap.put("appid", APPID);
		paramMap.put("secret", APPSECRET);
//		map.put("grant_type","c");
//		map.put("appid","w");
//		map.put("secret","2");
		String respJson = sendRequestGet("https://api.weixin.qq.com/cgi-bin/token", paramMap, "UTF-8");
		HashMap<String, String> respMap = JacksonUtil.json2Hashmap(respJson);
        String errcode = respMap.get("errcode");
        String accessToken = respMap.get("access_token");
        if(errcode!=null&&!errcode.equals("0")) {
        	System.out.println("请求失败");
        }else if(accessToken!=null) {
        	System.out.println("获得到access_token:\n"+accessToken);
        	access_token=accessToken;
        }
        System.out.println(access_token);
	}
	private static String APPID = "wxf99e416819915ef1";
	private static String APPSECRET = "260bbabbb305acc502e5816033813d43";
	
	private static String access_token = "";

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

}
