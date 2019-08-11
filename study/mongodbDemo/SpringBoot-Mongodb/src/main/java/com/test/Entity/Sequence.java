package com.test.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "sequence")
public class Sequence {

	@Id
	private String id;
	@Field("c_name")
	private String name;
	@Field("s_id")
	private Long seqid;
	
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Long getSeqid() {
		return seqid;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSeqid(Long seqid) {
		this.seqid = seqid;
	}
	
	
	
	
}
