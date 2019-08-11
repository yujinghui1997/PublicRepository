package com.anchor.Entity;

import java.math.BigDecimal;

public class Wallet {

	
	private Integer id;
	private BigDecimal money;
	private Integer uid;
	private Integer state;
	
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
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "Wallet [id=" + id + ", money=" + money + ", uid=" + uid + ", state=" + state + "]";
	}
	
	
	
}
