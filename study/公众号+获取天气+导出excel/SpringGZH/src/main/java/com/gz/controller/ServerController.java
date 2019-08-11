package com.gz.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("get")
public class ServerController {

	@RequestMapping("data")
	public void get(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(request.getParameter("name"));
		
		System.out.println("服务端");
		String result = "";
		try {
			InputStreamReader in = new InputStreamReader(request.getInputStream(),"utf-8");
			int i;
			char c;
			while ((i = in.read()) != -1) {
					c = (char)i;
					result += c;
			}
			System.out.println(JSONObject.parseObject(result));
			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
			writer.write("123");
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
