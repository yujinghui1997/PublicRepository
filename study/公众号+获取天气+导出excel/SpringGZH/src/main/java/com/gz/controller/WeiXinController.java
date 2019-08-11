package com.gz.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.ibatis.javassist.expr.NewArray;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.gz.entity.TextMessage;
import com.gz.service.TextMessageService;
import com.gz.util.CheckUtil;
import com.gz.util.MessageUtil;
import io.swagger.annotations.ApiOperation;

/**
 * 微信公众号认证
 * 
 * @author Administrator
 */
@RestController
@RequestMapping("weixin")
public class WeiXinController {

	@Autowired
	private TextMessageService textMessageService;
	@Value("${spring.token}")
	private String token;
	@Value("${spring.sdkAddress}")
	private String sdkAddress;
	@Value("${spring.sdkKey}")
	private String sdkKey;
	@Value("${spring.videoAddress}")
	private String videoAddress;
	@Value("${spring.videoKey}")
	private String videoKey;

	// 公众号token认证
	@RequestMapping(value = "authentication", method = RequestMethod.GET)
	public void auth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 接收微信服务器以Get请求发送的4个参数
		String signature = request.getParameter("signature");// 签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");//
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce, token)) {
			out.print(echostr); // 校验通过，原样返回echostr参数内容
		}
	}

	// 公众号发送信息
	@RequestMapping(value = "authentication", method = RequestMethod.POST)
	public void ommunication(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			// 客户端发给服务器的消息
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String toUserName = map.get("ToUserName");// 开发者微信
			String fromUserName = map.get("FromUserName");// 发送消息人的openid
			String msgType = map.get("MsgType");// 消息类型
			String content = map.get("Content");// 消息内容
			String msgId = map.get("MsgId");
			TextMessage textMessage = new TextMessage();
			textMessage.setContent(content);
			textMessage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			textMessage.setFromUserName(fromUserName);
			textMessage.setMsgId(msgId);
			textMessage.setMsgType(msgType);
			textMessage.setToUserName(toUserName);
			textMessageService.addText(textMessage);
			String message = null;
			if ("text".equals(msgType)) { // 对文本消息进行处理
				// 服务器发给客户端的消息
				TextMessage text = new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setMsgType("text");
				text.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				if (content.equals("1")) {
					text.setContent("链接：" + sdkAddress + "  提取码：" + sdkKey);
				} 
				if (content.equals("2")) {
					text.setContent("链接：" + videoAddress + "  提取码：" + videoKey);
				}
				if (content.equals("?")) {
					text.setContent("欢迎关注" + "Spring知识库公众号\n\n" 
							+ " 在这里可以了解JAVA全栈式开发相关技术\n\n"
							+"技术之路遥远漫长，让我们一起前行！\n\n"
							+ " 回复序号：\n" 
							+ "\t1、 开源工具 \n"
							+ "\t2、 学习视频 \n "
							+ "\t?、菜单"
							+ " 有任何问题或者合作联系我 \n"
							+ " VX：yjh970804\n"
							+ " QQ：1553739947\n"
							+ "Email：yujinghui54@gmail.com");
				}
				message = MessageUtil.textMessageToXML(text);
			} else {
				// 服务器发给客户端的消息
				TextMessage text = new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setMsgType("text");
				text.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				text.setContent("欢迎关注" + "Spring知识库公众号\n\n" 
						+ " 在这里可以了解JAVA全栈式开发相关技术\n\n"
						+"技术之路遥远漫长，让我们一起前行！\n\n"
						+ " 回复序号：\n" 
						+ "\t1、 开源工具 \n"
						+ "\t2、 学习视频 \n "
						+ "\t?、菜单"
						+ " 有任何问题或者合作联系我 \n"
						+ " VX：yjh970804\n"
						+ " QQ：1553739947\n"
						+ "Email：yujinghui54@gmail.com");
				message = MessageUtil.textMessageToXML(text);
			}
			out.print(message); // 将回应发送给微信服务器
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

}
