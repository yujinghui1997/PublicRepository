package com.gz.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.poi.ss.formula.eval.ConcatEval;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gz.util.HttpUtil;

@RestController
@RequestMapping("wea")
public class WeatherController {

	// 获取天气信息
	@RequestMapping("get")
	public Object getWeather() {
		String province = "河北省";
		String city = "秦皇岛市";
		String county = "海港区";
		Map<String, Object> map = HttpUtil.getWeather(province, city, county);
		Set<String> set = map.keySet();
		for (String string : set) {
			System.out.println("key==" + string + "  value==" + map.get(string));
		}
		return map;
	}

	@RequestMapping("aa")
	public void add() throws Exception {
		String url1 = "http://localhost:8080/user/sendemail/1553739947@qq.com";
		HttpClient http = null;
		GetMethod request = null;
		for (int i = 0; i < 10; i++) {
			http = new HttpClient();
			request = new GetMethod(url1);
			// 设置编码格式
			request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			http.executeMethod(request);
			String string = new String(request.getResponseBody(), "utf-8");
			System.out.println(string);
		}
		// 关闭连接，释放资源
		request.releaseConnection();
		((SimpleHttpConnectionManager) http.getHttpConnectionManager()).shutdown();

	}

}
