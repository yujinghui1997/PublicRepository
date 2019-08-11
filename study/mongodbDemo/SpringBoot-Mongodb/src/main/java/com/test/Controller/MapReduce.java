package com.test.Controller;

import java.util.Iterator;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("map")
public class MapReduce {

	
	private @Resource MongoTemplate mongoTemplate;
	
	@RequestMapping("reduce")
	public void reduce() {
		Query query=Query.query(Criteria.where("sex").is("男"));
		String inputCollectionName="student";
		String mapFunction="classpath:static/js/mapFunction.js";
		String reduceFunction="classpath:static/js/reduceFunction.js";
		MapReduceResults<mapValue> results = mongoTemplate.mapReduce(query,inputCollectionName, mapFunction, reduceFunction, mapValue.class);
		Iterator<mapValue> iterator = results.iterator();
		while (iterator.hasNext()) {
			mapValue mapValue =  iterator.next();
			System.out.println(mapValue);
		}
	}
}

class mapValue{
	private String id;
	private Values value;
	public String getId() {
		return id;
	}
	public Values getValue() {
		return value;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setValue(Values value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "mapValue [id=" + id + ", value=" + value + "]";
	}
}



class Values{
	private String sex;
	private String money;
	public String getSex() {
		return sex;
	}
	public String getMoney() {
		return money;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "Values [sex=" + sex + ", money=" + money + "]";
	}
	
	
}
