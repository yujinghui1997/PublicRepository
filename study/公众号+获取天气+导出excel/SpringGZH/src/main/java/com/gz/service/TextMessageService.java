package com.gz.service;

import java.util.List;

import com.gz.entity.TextMessage;

public interface TextMessageService {

	/**
	 * 新增消息
	 * @param textMessage
	 * @return
	 */
	Integer addText(TextMessage textMessage);
	
	/**
	 * 查询所有消息
	 * @return
	 */
	List<TextMessage> getAllTextMessage();
}
