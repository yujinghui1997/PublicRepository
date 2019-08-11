package com.test.Util;

import javax.annotation.Resource;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.test.Util.AutoIncrement;
import com.test.Entity.Sequence;

/**
 * 实现Mongodb ID字增
 * @author Administrator
 */
@Component
public class SaveIdListener extends AbstractMongoEventListener<Object> {
	@Resource
	MongoTemplate mongoTemplate;
	
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Object> event) {
		Object source = event.getSource();
		if (source != null) {
			ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
				public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
					ReflectionUtils.makeAccessible(field);
					// 如果字段添加了我们自定义的AutoIncKey注解
					if (field.isAnnotationPresent(AutoIncrement.class)) {
						// 设置自增ID
						field.set(source, getNextId(source.getClass().getSimpleName()));
					}
				}
			});
		}
	}
	/**
	 * 返回下一个自增的ID
	 * 
	 * @param collName 集合名称（一般规则是，类名的第一个字母小写，然后按照驼峰书写法）
	 * @return Long 序列值
	 */
	private Long getNextId(String collName) {
		Query query = new Query(Criteria.where("name").is(collName));
		Update update = new Update();
		update.inc("seqid", 1);
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.upsert(true);
		options.returnNew(true);
		Sequence seq = mongoTemplate.findAndModify(query, update, options, Sequence.class);
		return seq.getSeqid();
	}

}
