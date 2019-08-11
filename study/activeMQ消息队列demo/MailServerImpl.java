package com.test.Service;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServerImpl implements MailServer {

	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private TemplateEngine templateEngine;//thymeleaf 模板
	
	@Value("${spring.mail.username}")
	private String from;
	
	//简单文本
	@Override
	public boolean sendTxt(String to, String subject, String content) {
		MimeMessage message = javaMailSender.createMimeMessage();
	try {
		MimeMessageHelper helper=new MimeMessageHelper(message,true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content,true);
			javaMailSender.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

	
	
	//发送页面模板
	@Override
	public boolean sendHtml(String to, String subject, String template, Map<String, Object> map) {
		Context context=new Context();
		context.setVariables(map);
		String text=this.templateEngine.process(template, context);
		return this.sendTxt(to, subject, text);
	}

}
