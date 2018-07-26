package com.company.main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author LiShan
 * Since: 2018-07-26 02:43:07
 */
public class WxWebAuthorizeTest {
	public static void main(String[] args) throws Exception {
		String result = fun1();
		System.out.println(result);
		
	}
	
	/**
	 * 微信网页授权，第一步中（引导用户进入授权页面），授权页面的url拼接工具
	 */
	static String fun1() throws UnsupportedEncodingException {
		//	参数					是否必须		说明
		//	appid				是			公众号的唯一标识
		//	redirect_uri		是			授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
		//	response_type		是			返回类型，请填写code
		//	scope				是			应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
		//	state				否			重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
		//	#wechat_redirect	是			无论直接打开还是做页面302重定向时候，必须带此参数
		String appid = "wx5ac27f8a71baf859";
		String redirect_url = URLEncoder.encode("http://4ccb917c.ngrok.io"+"/WeiXin/authorization", "UTF-8");
//		String redirect_url = "https%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60";
		String response_type = "code";
		String scope = "snsapi_base";
//		String scope = "snsapi_userinfo";
		String state = "test110";
		return "https://open.weixin.qq.com/connect/oauth2/authorize"
				+ "?appid="				+appid
				+ "&redirect_uri="		+redirect_url
				+ "&response_type="		+response_type
				+ "&scope="				+scope
				+ "&state="				+state
				+ "#wechat_redirect";
	}
}
