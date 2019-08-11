package com.store.Dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.store.Entity.User;

@Mapper
public interface UserMapper {

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	Integer addUser(User user);
	
	
	/**
	 * 通过邮箱查询用户
	 * @param email
	 * @return
	 */
	User queryUserByEmail(@Param("email") String email);
	
	
	/**
	 * 登录
	 * @param email
	 * @param password
	 * @return
	 */
	User login(@Param("email") String email,@Param("password") String password);
	
	/**
	 * 通过id 查询用户
	 * @param id
	 * @return
	 */
	User queryById(@Param("id") Integer id);
	
	/**
	 * 修改用户的金币
	 * @param uid
	 * @param money
	 * @return
	 */
	Integer updateMoney(@Param("uid") Integer uid,@Param("money") BigDecimal money);
	
	
}
