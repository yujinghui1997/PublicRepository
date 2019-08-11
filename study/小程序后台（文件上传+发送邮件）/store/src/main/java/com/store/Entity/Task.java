package com.store.Entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Task  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;//id
	private String photo;//任务头像
	private String title;//任务标题
	private String txt;//任务简介
	private BigDecimal price;//任务价格
	private String content;//任务内容
	private String state;//任务状态
	private Integer uid;//用户id
	private Integer type;//默认 1  代表任务
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
	public BigDecimal getPrice() {
		return price;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public void setPrice(BigDecimal price) {
		this.price = price;
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
		return "Task [id=" + id + ", photo=" + photo + ", title=" + title + ", txt=" + txt + ", price=" + price
				+ ", content=" + content + ", state=" + state + ", uid=" + uid + ", type=" + type + "]";
	}
	

}
