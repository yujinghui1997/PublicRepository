package com.gz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gz.entity.TextMessage;

@Mapper
public interface TextMessageMapper {

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
