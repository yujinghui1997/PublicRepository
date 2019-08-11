package com.test.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "person")
public class Person {

	@Id
	private String id;
	@Field(value = "name")
	private String name;
	@Field(value = "sex")
	private String sex;
	@Field(value = "phone")
	private String phone;
	@Field(value = "age")
	private Integer age;
	
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSex() {
		return sex;
	}
	public String getPhone() {
		return phone;
	}
	public Integer getAge() {
		return age;
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
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	
}
