package com.test.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.Entity.Student;
import com.test.Entity.User;

@RestController
@RequestMapping("stu")
public class StudentController {

	private @Resource MongoTemplate mongoTemplate;
	
	@RequestMapping("add")
	public void add() {
		List<Student> list=new ArrayList<Student>();
		for (int i = 0; i < 100; i++) {
			Student student=new Student();
			student.setMoney(i);
			student.setName("李白"+i);
			if (i<=50) {
				student.setSex("男");
			}else {
				student.setSex("女");
			}
			list.add(student);
		}
		mongoTemplate.insert(list,Student.class);
	}
}
