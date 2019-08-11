package com.test.Util;

import java.util.Map;

import org.apache.activemq.store.memory.MemoryTransactionStore.Tx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.Service.MailServer;

/**
 * 消息队列消费者
 * @author Administrator
 *
 */
@Component
public class Consumer {

	private @Autowired MailServer mailServer;
	
	@JmsListener(destination = "email.message")
	public void receiceQueue(String text) {
		Map<String, Object> map= JSONObject.parseObject(text);
		String to = (String) map.get("to");
		String subject = (String) map.get("subject");
		String content = (String) map.get("content");
		System.out.println(text);
		mailServer.sendTxt(to, subject, content);
	}
	
	
}
