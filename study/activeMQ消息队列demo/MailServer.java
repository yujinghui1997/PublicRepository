package com.test.Service;

import java.util.Map;

public interface MailServer {

	//发送简单模板
	boolean sendTxt(String to,String subject,String content);
	//发送页面模板
	boolean sendHtml(String to,String subject,String template,Map<String, Object> map);
}
