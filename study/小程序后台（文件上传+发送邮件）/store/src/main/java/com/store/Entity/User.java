package com.store.Entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class User  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;//id
	private String name;//姓名
	private String email;//邮箱
	private String password;//密码
	private BigDecimal money;//金额
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", money=" + money
				+ "]";
	}

	
	
}
