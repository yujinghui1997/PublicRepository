package com.gz.entity;

import java.io.Serializable;

public class TextMessage implements Serializable {

	private Integer id; //id
	private String ToUserName;//开发者微信号
	private String FromUserName;//发送信息的用户（openid）
	private String CreateTime;//消息发送时间
	private String MsgType;//消息文本类型
	private String Content;//消息内容
	private String MsgId;//消息id
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	@Override
	public String toString() {
		return "TextMessage [id=" + id + ", ToUserName=" + ToUserName + ", FromUserName=" + FromUserName
				+ ", CreateTime=" + CreateTime + ", MsgType=" + MsgType + ", Content=" + Content + ", MsgId=" + MsgId
				+ "]";
	}
	
	
	
	
	
	
	
}
