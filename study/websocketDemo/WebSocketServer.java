package com.yjh.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{uid}/{sid}")
@Component
public class WebSocketServer {

	// 静态变量，用来记录当前在线连接数。
	private static int onlineCount = 0;
	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
	// 定义一个用来存放session的容器
	private static Map<String, Session> sessionMap = new HashMap<String, Session>();

	
	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("uid") String sid) {
		sessionMap.put(sid, session);
		webSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		try {
			session.getBasicRemote().sendText("连接成功");
		} catch (Exception e) {
			// log.error("websocket IO异常");
		}
	}

	/**
	 * 收到客户端消息后调用的方法 一对一
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, @PathParam("uid") String uid, @PathParam("sid") String sid) {
		System.out.println(sid);
		// 服务器发送消息
		try {
			if (sid.equals("0")) {
				for (Entry<String, Session> item : sessionMap.entrySet()) {
					item.getValue().getBasicRemote().sendText("大家好");
				}
				return;
			}
			Session session = (Session) sessionMap.get(sid);
			if (session != null) {
				session.getBasicRemote().sendText("对方想邀请你吃饭");
			} else {
				session = (Session) sessionMap.get(uid);
				session.getBasicRemote().sendText("对方不在线");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(@PathParam("uid") String uid) {
		sessionMap.remove(uid);//移除session
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
	}

	/**
	 * 给所有人发送消息
	 * @param message
	 * @throws IOException 
	 */
	public static void send(String message) throws IOException {
		//遍历所有用户的session
		for (Entry<String, Session> session : sessionMap.entrySet()) {
			session.getValue().getBasicRemote().sendText(message);
		}
	}
	
	/**
	 * 给个人发送消息
	 * @param message
	 * @throws IOException 
	 */
	public static void send(String message,String id) throws IOException {
		//给指定session发送消息
		Session session = (Session) sessionMap.get(id);
		session.getBasicRemote().sendText(message);
	}
	
	
	
	
	
	/**
	 * 发生错误
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	
	
	// 返回当前连接数
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}
	// 增加连接数
	public static synchronized void addOnlineCount() {
		WebSocketServer.onlineCount++;
	}
	// 减少连接数
	public static synchronized void subOnlineCount() {
		WebSocketServer.onlineCount--;
	}

}
