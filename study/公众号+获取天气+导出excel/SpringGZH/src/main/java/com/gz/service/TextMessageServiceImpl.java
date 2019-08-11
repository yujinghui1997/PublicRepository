package com.gz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gz.dao.TextMessageMapper;
import com.gz.entity.TextMessage;

@Service
public class TextMessageServiceImpl implements TextMessageService {

	private @Autowired TextMessageMapper textMessageMapper;
	
	@Override
	public Integer addText(TextMessage textMessage) {
		// TODO Auto-generated method stub
		return textMessageMapper.addText(textMessage);
	}

	@Override
	public List<TextMessage> getAllTextMessage() {
		// TODO Auto-generated method stub
		return textMessageMapper.getAllTextMessage();
	}

}
