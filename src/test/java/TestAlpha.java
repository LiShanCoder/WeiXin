import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;

import com.company.main.HttpClientUtil;
import com.company.main.JacksonUtil;

public class TestAlpha {

	@Test
	public void test() {
	}
	/*
	 * HttpClientUtil.class测试
	 */
	@Test
	public void test1() throws Exception {
		String APPID = "w";
		String APPSECRET = "260bbabbb305acc502e5816033813d43";
		String access_token = "";
		
		//使用示例（旧）
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("grant_type","client_credential");
		paramMap.put("appid", APPID);
		paramMap.put("secret", APPSECRET);
		
		String respJson = HttpClientUtil.sendRequestGet("https://api.weixin.qq.com/cgi-bin/token", paramMap, "UTF-8");
		
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

	@Test
	public void test2() throws Exception {
		//使用示例
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/token")
				.setParameter("grant_type","client_credential")
				.setParameter("appid", "")
				.setParameter("secret", "")
				.build();
		String result = HttpClientUtil.sendRequestGet(uri);
		System.out.println(result);
	}
	
	@Test
	public void test3() throws Exception  {
		String access_token = HttpClientUtil.getWX_accessToken();
		System.out.println(access_token);
	}
	
	/*
	 * JacksonUtil.class测试
	 */
	@Test
	public void test4() throws Exception {
		String corrJson = "{\"access_token\":\"10_Ugow4\",\"expires_in\":7200}";
		
        Map<String,String> urMap = JacksonUtil.json2Hashmap(corrJson);
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
	@Test
	public void test5() throws Exception {
		String errJson = "{\"errcode\":40013,\"errmsg\":\"invalid appid hint: [NCnA803501466]\"}";

        Map<String,String> urMap = JacksonUtil.json2Hashmap(errJson);
        
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
	
	/*
	 * PropertiesUtil.class
	 */
	@Test
	public void test6() throws Exception {
		InputStream inStream = new FileInputStream("src/main/resources/WeiXin.properties");
		
		Properties prop = new Properties();
		prop.load(inStream);
		
		String val = prop.getProperty("key");
		System.out.println(val);
		
//		Servlet的方法中使用，获取WEB-INF/classes的绝对路径
//		String s = Thread.currentThread().getContextClassLoader().getResource("/").getPath();  
//		System.out.println(s);
	}
}
