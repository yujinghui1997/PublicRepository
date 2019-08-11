package com.store.Entity;

import java.io.Serializable;

public class Cache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private String type;
	private String name;
	private Integer uid;
	public Integer getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public Integer getUid() {
		return uid;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "Cache [id=" + id + ", type=" + type + ", name=" + name + ", uid=" + uid + "]";
	}
	
	
	

}
