package com.company.main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 第一步：填写服务器配置
 * 		在微信官网上填写，要连接的URL、使用的Token
 * @author LiShan
 *
 */
public class WxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String TOKEN = "lishan";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//参数:			描述:
		//signature		微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		//timestamp		时间戳
		//nonce			随机数
		//echostr		随机字符串
		String signature 	= request.getParameter("signature");
		String timestamp 	= request.getParameter("timestamp");
		String nonce 		= request.getParameter("nonce");
		String echostr 		= request.getParameter("echostr");
		
		if(isFromWX(TOKEN, timestamp, nonce, signature)) {
			//4) 验证通过，原样返回echostr参数内容
			response.setContentType("encoding=utf-8");
			response.getWriter().print(echostr);
		}
		
	}

	/**<pre>
	 * 第二步：验证消息的确来自微信服务器
	 * 1）将token、timestamp、nonce三个参数进行[字典序排序]
	 * 2）将三个参数字符串，拼接成[一个字符串]，进行[sha1加密] 
	 * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @return 如验证通过，返回true
	 */
	private boolean isFromWX(String token, String timestamp, String nonce, String signature) {
		if(timestamp==null || nonce==null || signature==null) {
			return false;
		}
		
		String[] tmpArr = {token,timestamp,nonce};
		//1）3字符串，字典排序
		List<String> sortList = Arrays.asList(tmpArr);
		Collections.sort(sortList);
		//2）对排序后的3字符串，拼接成一个串
		StringBuffer toOneStr = new StringBuffer();
		for(String s : sortList) {
			toOneStr.append(s);
		}
		//   sha1加密
		String decode = DigestUtils.sha1Hex(toOneStr.toString());
		//3）与微信传来的signature比较，相同时校验成功
		if(signature!=null && signature.equals(decode)) {
			return true;
		}
		return false;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
