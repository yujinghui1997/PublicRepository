package com.store.Entity;

import java.io.Serializable;

public class Car implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer sid;//商品id
	private Integer uid;//用户id
	public Integer getId() {
		return id;
	}
	public Integer getSid() {
		return sid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "Car [id=" + id + ", sid=" + sid + ", uid=" + uid + "]";
	}
	
	

}
