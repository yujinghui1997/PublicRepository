package com.test.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.test.Util.AutoIncrement;

@Document(collection = "student")
public class Student {

	@Id
	private String id;
	@Field("name")
	private String name;
	@Field("sex")
	private String sex;
	@Field("money")
	private Integer money;
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSex() {
		return sex;
	}
	public Integer getMoney() {
		return money;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex + ", money=" + money + "]";
	}
	
	
	
}
