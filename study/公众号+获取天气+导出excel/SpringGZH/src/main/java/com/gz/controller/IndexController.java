package com.gz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/getdata")
	public String get(ModelMap modelMap) {
		modelMap.addAttribute("get", "于景辉");
		return "get";
	}
	
}
