package com.test.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.Entity.Person;
import com.test.Repository.PersonRepository;

@RestController
@RequestMapping("per")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	//Repository 增加数据
	@RequestMapping("add")
	public Object add() {
		Person person=new Person();
		person.setName("于景辉");
		person.setSex("男");
		person.setPhone("18733553312");
		person.setAge(22);
		Person save = personRepository.save(person);
		return save;
	}
	//添加人
	@RequestMapping("add2")
	public Object add2() {
		List<Person> list = new ArrayList<Person>();
		for (int i = 1; i <=20; i++) {
			Person person=new Person();
			person.setName("李白"+i);
			person.setSex("男");
			person.setPhone("187__"+i);
			person.setAge(i);
			list.add(person);
		}
		Collection<Person> insert= mongoTemplate.insert(list,Person.class);
		return insert;
	}
	
	//查询年龄大于5 小于15 的人
	@RequestMapping("get")
	public Object get() {
		//return mongoTemplate.findAll(Person.class);
		Query query = Query.query(Criteria.where("age").gt(5).lt(15));
		return mongoTemplate.find(query, Person.class);
	}
	
	//修改多条数据
	@RequestMapping("up")
	public Object up() {
		//return mongoTemplate.findAll(Person.class);
		Query query = Query.query(Criteria.where("age").gt(5).lt(15));
		Update update = Update.update("phone", "155").set("name", "梦之泪伤");
		return mongoTemplate.updateMulti(query, update, Person.class);
	}
	
	
	
}
