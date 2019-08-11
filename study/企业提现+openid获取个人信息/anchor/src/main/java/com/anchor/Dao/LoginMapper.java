package com.anchor.Dao;

import org.apache.ibatis.annotations.Param;

import com.anchor.Entity.User;

public interface LoginMapper {

	/**
	 * 通过openid 查询
	 * @param openid
	 * @return
	 */
	User queryByOpenId(@Param("openid") String openid);
	
	
	
	/**
	 * 通过Phone 查询
	 * @param phone
	 * @return
	 */
	User queryByPhone(@Param("phone") String Phone);
	
	/**
	 * 修改用户 通过 phone
	 * @param phone
	 * @param openid
	 * @return
	 */
	Integer updateByPhone(@Param("phone") String phone,@Param("openid") String openid);
	
	/**
	 * 新增openid
	 * @param user
	 * @return
	 */
	Integer addOpenId(User user);
	
	/**
	 * 通过id 查询
	 * @param openid
	 * @return
	 */
	User queryById(@Param("id") Integer id);
}
