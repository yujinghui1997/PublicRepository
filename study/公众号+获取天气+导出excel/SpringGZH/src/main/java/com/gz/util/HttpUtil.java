package com.gz.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.InflaterInputStream;

import net.sf.json.JSONObject;

public class HttpUtil {

	// 获取天气
	public static Map<String, Object> getWeather(String province, String city, String county) {
		Map<String, Object> map = null;
		String provinceNew = province;// 省
		String cityNew = city;// 市
		String countyNew = county;// 区
		String url = "https://wis.qq.com/weather/common";// 请求地址
		String param = "";// 参数
		if ("" == countyNew) {
			param = "source=xw&weather_type=forecast_1h|forecast_24h|index|alarm|limit|tips" + "&province="
					+ provinceNew + "&city=" + cityNew;
		} else {
			param = "source=xw&weather_type=forecast_1h|forecast_24h|index|alarm|limit|tips" + "&province="
					+ provinceNew + "&city=" + cityNew + "" + "&county=" + countyNew;
		}
		url = url + "?" + param;
		try {
			// 创建链接
			URL url2 = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");// 请求方法
			connection.connect();// 建立连接
			// 接受服務器返回的数据
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			String text = result.toString();
			reader.close();
			JSONObject json = JSONObject.fromObject(text);
			JSONObject fromObject = JSONObject.fromObject(json.get("data"));
			String alarm = fromObject.get("alarm").toString();
			String forecast_1h = fromObject.get("forecast_1h").toString();
			String forecast_24h = fromObject.get("forecast_24h").toString();
			String index = fromObject.get("index").toString();
			String limit = fromObject.get("limit").toString();
			String tips = fromObject.get("tips").toString();
			map = new HashMap<String, Object>();
			map.put("alarm", alarm);
			map.put("forecast_1h", forecast_1h);
			map.put("forecast_24h", forecast_24h);
			map.put("index", index);
			map.put("limit", limit);
			map.put("tips", tips);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
