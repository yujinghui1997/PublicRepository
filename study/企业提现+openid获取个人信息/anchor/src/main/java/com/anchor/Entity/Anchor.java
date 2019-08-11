package com.anchor.Entity;

import java.io.Serializable;

public class Anchor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private String realName;//真实姓名
	private String vxNumber;//微信号
	private String hsNickname;//火山昵称
	private String hsNumber;//火山号
	private Integer uid;//uid
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getVxNumber() {
		return vxNumber;
	}
	public void setVxNumber(String vxNumber) {
		this.vxNumber = vxNumber;
	}
	public String getHsNickname() {
		return hsNickname;
	}
	public void setHsNickname(String hsNickname) {
		this.hsNickname = hsNickname;
	}
	public String getHsNumber() {
		return hsNumber;
	}
	public void setHsNumber(String hsNumber) {
		this.hsNumber = hsNumber;
	}
	@Override
	public String toString() {
		return "Anchor [id=" + id + ", realName=" + realName + ", vxNumber=" + vxNumber + ", hsNickname=" + hsNickname
				+ ", hsNumber=" + hsNumber + ", uid=" + uid + "]";
	}
	
	
	

}
