package com.store.Entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Ad  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;//id
	private String photo;//广告头像
	private String title;//广告标题
	private String txt;//广告简介
	private String content;//广告内容
	private String state;//广告状态
	private Integer uid;//用户id
	private Integer type;//默认2 代表广告
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public String getPhoto() {
		return photo;
	}
	public String getTitle() {
		return title;
	}
	public String getTxt() {
		return txt;
	}

	public String getContent() {
		return content;
	}
	public String getState() {
		return state;
	}
	public Integer getUid() {
		return uid;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "Ad [id=" + id + ", photo=" + photo + ", title=" + title + ", txt=" + txt + ", content=" + content
				+ ", state=" + state + ", uid=" + uid + ", type=" + type + "]";
	}

	
	

}
