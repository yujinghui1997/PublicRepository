package com.test.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.test.Util.AutoIncrement;

@Document(collection = "classinfo")
public class ClassInfo {

	@Id
	@AutoIncrement
	private Long id;
	@Field("name")
	private String name;
	
	public ClassInfo(String name) {
		this.name=name;
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
	
	
	
}
