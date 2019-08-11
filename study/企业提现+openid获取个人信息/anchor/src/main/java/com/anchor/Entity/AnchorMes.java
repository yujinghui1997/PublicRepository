package com.anchor.Entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class AnchorMes implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private String realName;//真实姓名
	private String phone;//手机号
	private String vxNumber;//微信号
	private String hsNickname;//火山昵称
	private String hsNumber;//火山号
	private BigDecimal money;//余额
	private BigDecimal tiXian;//已提现
	private Integer state;//钱包状态
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getTiXian() {
		return tiXian;
	}
	public void setTiXian(BigDecimal tiXian) {
		this.tiXian = tiXian;
	}
	@Override
	public String toString() {
		return "AnchorMes [id=" + id + ", realName=" + realName + ", phone=" + phone + ", vxNumber=" + vxNumber
				+ ", hsNickname=" + hsNickname + ", hsNumber=" + hsNumber + ", money=" + money + ", tiXian=" + tiXian
				+ ", state=" + state + "]";
	}
	
	

}
