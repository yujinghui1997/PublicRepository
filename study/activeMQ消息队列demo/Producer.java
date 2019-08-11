package com.test.Util;

import javax.annotation.Resource;
import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息队列生产者
 * @author Administrator
 *
 */
@Component
public class Producer {
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@Autowired
	private Queue queue;
	
	/**
	 * 生产消息
	 * @param mes
	 */
	public void send(String mes) {
		this.jmsMessagingTemplate.convertAndSend(this.queue,mes);
	}
}
