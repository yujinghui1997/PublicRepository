package com.test.Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public class MapUtil {

	static String AK = "IkZymi6enZdFBLsXn0ll1IdgGiNtUQ9A"; // 百度地图密钥

	/**
	 * @param addr  根据输入的地址 查询的地图
	 * @return
	 * @throws IOException
	 */
	public static Object[] getCoordinate(String addr) throws IOException {
		String lng = null;// 经度
		String lat = null;// 纬度
		String address = null;
		try {
			address = java.net.URLEncoder.encode(addr, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// String key = "IkZymi6enZdFBLsXn0ll1IdgGiNtUQ9A";
		String url = String.format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, AK);
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				br = new BufferedReader(insr);
				String data = null;
				int count = 1;
				while ((data = br.readLine()) != null) {
					if (count == 5) {
						lng = (String) data.subSequence(data.indexOf(":") + 1, data.indexOf(","));// 经度
						count++;
					} else if (count == 6) {
						lat = data.substring(data.indexOf(":") + 1);// 纬度
						count++;
					} else {
						count++;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (insr != null) {
				insr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return new Object[] { lng, lat };
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 以下根据IP地址获取地图
	/**
	 *
	 * @param content        请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encodingString 服务器端请求编码。如GBK,UTF-8等
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, Object> getAddresses(String content, String encodingString) {
		// 调用淘宝API
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
		Map<String, Object> map = getResult(urlStr, content, encodingString);
		if (map != null) {
			return map;
		}
		return null;
	}

	/**
	 * @param urlStr         请求的地址
	 * @param content        请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encodingString 服务器端请求编码。如GBK,UTF-8等
	 * @return
	 */
	private static Map<String, Object> getResult(String urlStr, String content, String encodingString) {
		Map<String, Object> map = new HashMap<String, Object>();
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			// 新建连接实例
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接超时时间，单位毫秒
			// connection.setConnectTimeout(20000);
			// 设置读取数据超时时间，单位毫秒
			// connection.setReadTimeout(20000);
			// 是否打开输出流
			connection.setDoOutput(true);
			// 是否打开输入流
			connection.setDoInput(true);
			// 提交方法 POST|GET
			connection.setRequestMethod("POST");
			// 是否缓存
			connection.setUseCaches(false);
			// 打开连接端口
			connection.connect();
			// 打开输出流往对端服务器写数据
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			// 写数据，即提交表单 name=xxx&pwd=xxx
			out.writeBytes(content);
			// 刷新
			out.flush();
			// 关闭输出流
			out.close();
			// 往对端写完数据对端服务器返回数据 ,以BufferedReader流来读取
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), encodingString));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			String str = buffer.toString();
			JSONObject json = JSONObject.fromObject(str);
			String country = JSONObject.fromObject(json.get("data")).get("country").toString();
			String region = JSONObject.fromObject(json.get("data")).get("region").toString();
			String city = JSONObject.fromObject(json.get("data")).get("city").toString();
			String county = JSONObject.fromObject(json.get("data")).get("county").toString();
			String isp = JSONObject.fromObject(json.get("data")).get("isp").toString();
			String area = JSONObject.fromObject(json.get("data")).get("area").toString();
			map.put("country", country);// 国家
			map.put("area", area);// 地区
			map.put("region", region + "省");// 省份
			map.put("city", city + "市");// 城市
			map.put("county", county);// 互联网提供商
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}



}
