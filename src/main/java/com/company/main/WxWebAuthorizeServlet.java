package com.company.main;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;

/**
 * 【微信网页授权】scope=snsapi_base
 * 	1.获取code
 * 		微信网页上配置：网页服务->网页帐号->网页授权获取用户基本信息		修改，设置授权域名（例如：4ccb917c.ngrok.io）
 * 		让用户请求 https://open.weixin.qq.com/connect/oauth2/authorize 获取code服务（参数中有公众号appid、回调地址redirect_uri，等微信回调）
 * 		微信回调请求上有code、state参数
 * 	2.获取access_token、openid
 * 		本系统请求 https://api.weixin.qq.com/sns/oauth2/access_token 获取access_token、openid
 * @author LiShan
 * Since: 2018-07-26 04:32:45
 */
public class WxWebAuthorizeServlet extends HttpServlet {
	private static final long serialVersionUID = -632067892860379824L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println( getWx_openid(req) );
	}

	/**
	 * [只用于登陆页面一次]
	 * 请求获得openid，和另一种access_token（与接入微信不同）
	 * https://api.weixin.qq.com/sns/oauth2/access_token?appid= {APPID} &secret= {SECRET} &code= {CODE} &grant_type=authorization_code
	 */
	private static String getWx_openid(HttpServletRequest req){
		try {
			String code = req.getParameter("code");
			if(code==null)
				throw new Exception("回调code参数缺失");
			URI uri = new URIBuilder("https://api.weixin.qq.com/sns/oauth2/access_token")
					.setParameter("appid", PropertiesUtil.getWX_appid())
					.setParameter("secret", PropertiesUtil.getWX_appsecret())
					.setParameter("code", code)
					.setParameter("grant_type", "authorization_code")
					.build();
			String jsonStr = HttpClientUtil.sendRequestGet(uri);
			HashMap<String, String> map = JacksonUtil.json2Hashmap(jsonStr);
			return map.get("openid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
