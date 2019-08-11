package com.test.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.test.Util.AutoIncrement;

@Document(collection = "user")
public class User {

	@Id
	@AutoIncrement
	private Long id=0L;
	@Field("name")
	private String name;
	@DBRef
	private ClassInfo classinfo;
	
	public User(String name, ClassInfo classinfo) {
		this.name = name;
		this.classinfo = classinfo;
	}
	public User() {
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ClassInfo getClassinfo() {
		return classinfo;
	}
	public void setClassinfo(ClassInfo classinfo) {
		this.classinfo = classinfo;
	}
	
	
}
