package com.gz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gz.entity.TextMessage;
import com.gz.service.TextMessageService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootGongZhongHaoApplicationTests {

	private @Autowired TextMessageService textMessageService;
	@Test
	public void contextLoads() {
		TextMessage textMessage = new TextMessage();
		textMessage.setContent("1");
		textMessage.setCreateTime("1997-08-07 14:12:12");
		textMessage.setFromUserName("1");
		textMessage.setMsgId("1");
		textMessage.setMsgType("1");
		textMessage.setToUserName("1");
		textMessageService.addText(textMessage);
		
	}

}
