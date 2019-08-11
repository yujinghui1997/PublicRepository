package com.test.Util;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpUtil {

	
	/*
	 * 工具类依赖
	 * <!--阿里解析 json数据--> <!--
	 * https://mvnrepository.com/artifact/com.alibaba/fastjson --> 
	 *<dependency>
	 * 				<groupId>com.alibaba</groupId>
	 * 			 	<artifactId>fastjson</artifactId>
	 * 				<version>1.2.57</version> 
	 *</dependency>
	 *
	 *<!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpclient</artifactId>
			<version>4.5.9</version>
		</dependency>
	 */
	
	/**
	 * 普通的get请求
	 * @param url 地址
	 * @param param 参数
	 * @param head 请求头信息
	 */
	public static String get(String url,Map<String, String> param,Map<String, String> head) {
		//发送请求的客户端
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//请求的配置信息
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(10000)
				.setConnectionRequestTimeout(10000)
				.setSocketTimeout(10000)
				.build();
		//返回参数
		String result = "";
		//响应
		CloseableHttpResponse response = null;
		//添加参数
		try {
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
			HttpGet get = new HttpGet(uri);//创建get请求
			get.setConfig(requestConfig);
			if (head != null) {
				for (Map.Entry<String, String> h : head.entrySet()) {
					get.setHeader(h.getKey(), h.getValue());
				}
			}
			get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
			response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(),"utf-8");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			return result;
		}
		
	}
	
	
	/**
	 * post 请求
	 * @param url
	 * @param param
	 */
	public static String post(String url,Map<String, String> param) {
		//发送请求的客户端
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//请求的配置信息
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(10000)
				.setConnectionRequestTimeout(10000)
				.setSocketTimeout(10000)
				.build();
		//返回参数
		String result = "";
		//响应
		CloseableHttpResponse response = null;
		//添加参数
		try {
			HttpPost post = new HttpPost(url);//创建get请求
			post.setConfig(requestConfig);//添加配置信息
			//添加参数
			if (param != null) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					list.add(new BasicNameValuePair(key, param.get(key)));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
				post.setEntity(entity);
			}
			response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(),"utf-8");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			return result;
		}
	}
	
	
	/**
	 * post 请求 发送json 参数
	 * @param url
	 * @param param
	 * @return 
	 */
	public static String postJson(String url,Map<String, Object> param) {
		String json = JSON.toJSONString(param);
		//发送请求的客户端
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//请求的配置信息
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(10000)
				.setConnectionRequestTimeout(10000)
				.setSocketTimeout(10000)
				.build();
		//返回参数
		String result = "";
		//响应
		CloseableHttpResponse response = null;
		//添加参数
		try {
			HttpPost post = new HttpPost(url);//创建get请求
			post.setConfig(requestConfig);//添加配置信息
			StringEntity entity = new StringEntity(json,ContentType.APPLICATION_JSON);
			post.setEntity(entity);
			response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(),"utf-8");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			return result;
		}
	}
	
	

}
