package com.store.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.store.Entity.Cache;
import com.store.Entity.Shop;

@Mapper
public interface SettingMapper {

	

	/**
	 * 修改用户密码
	 * @param id
	 * @param oldpass
	 * @param newpass
	 * @return
	 */
	Integer updatePassword(@Param("id") Integer id,@Param("oldpass") String oldpass,@Param("newpass") String newpass);
	
	/**
	 * 修改用户名字
	 * @param id
	 * @param name
	 * @return
	 */
	Integer updateName(@Param("id") Integer id,@Param("name") String name);
}
