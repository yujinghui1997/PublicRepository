package com.store.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.store.Entity.Ad;

@Mapper
public interface AdMapper {

	
	/**
	 * 添加广告
	 * @param Ad
	 * @return
	 */
	Integer addAd(Ad Ad);
	
	/**
	 * 查询用户所有的广告
	 * @param uid
	 * @return
	 */
	List<Ad> queryAllAd(@Param("uid") Integer uid);
	
	/**
	 * 查询单件广告详细信息
	 * @param id
	 * @return
	 */
	Ad queryOneAd(@Param("id") Integer id);
	
	/**
	 * 查询所有的广告
	 * @param uid
	 * @return
	 */
	List<Ad> queryAll();
}
