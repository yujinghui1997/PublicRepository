package com.anchor.Util;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class UserOpenId {

	
	/**
	 * 请求微信服务器 获取openid
	 * @param code
	 * @param APPID
	 * @param APPSecret
	 * @return
	 */
	public static Map<String, Object> getOpenId( String code,String APPID,String APPSecret){
		Map< String, Object> map=null;
		StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?");
		url.append("appid=");//appid设置
		url.append(APPID);
		url.append("&secret=");//secret设置
		url.append(APPSecret);
		url.append("&js_code=");//code设置
		url.append(code);
		url.append("&grant_type=authorization_code");
		try {
	        HttpClient client =HttpClientBuilder.create().build();//构建一个Client
	        HttpGet get = new HttpGet(url.toString());    //构建一个GET请求
	        HttpResponse response = client.execute(get);//提交GET请求
	        HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
	        String content = EntityUtils.toString(result);   
	        JSONObject res = JSONObject.fromObject(content);//把信息封装为json
		    map = MdzwUtils.parseJSON2Map(res); //把信息封装到map
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return map;
	}
}
