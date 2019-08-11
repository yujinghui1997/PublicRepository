package com.test.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.Entity.ClassInfo;
import com.test.Entity.User;
import com.test.Repository.UserRepository;

@RestController
@RequestMapping("index")
public class MongoController {

	private @Resource MongoTemplate mongoTemplate;
	private @Resource UserRepository UserRepository;
	
	@RequestMapping("insert") 
	public User insert(){
		User user=new User();
		user.setName("于景辉");
		User save = mongoTemplate.save(user);
		return save;
	}
	/**
	 * mongoTemplate查询方式
	 * @return
	 */
	@RequestMapping("query") 
	public User  query(){
		Query query=Query.query(Criteria.where("_id").is(1L));
		User user=mongoTemplate.findOne(query, User.class);
		return user;
	}
	
	/**
	 * UserRepository方式查询
	 * @return
	 */
	@RequestMapping("query2") 
	public Optional<User>  query2(){
		Optional<User> findById2= UserRepository.findById(1L);
		return findById2;
	}
	
	/**
	 * DBref 查询
	 */
	@RequestMapping("study")
	public void study() {
		ClassInfo classInfo=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(1L)), ClassInfo.class);
		mongoTemplate.save(new User("于景辉",classInfo));
	}
	
	
	
	
	
	
	

	/**
	 * DBref 移除
	 */
	@RequestMapping("remove")
	public void remove() {
		Query query=Query.query(Criteria.where("_id").lt(100L));
		mongoTemplate.remove(query,User.class);
	}
	
}
