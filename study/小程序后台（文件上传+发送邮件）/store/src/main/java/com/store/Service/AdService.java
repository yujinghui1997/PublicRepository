package com.store.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.Entity.Ad;

public interface AdService {

	

	/**
	 * 添加用户
	 * @param user
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
	 * 查询所有的广告
	 * @param uid
	 * @return
	 */
	List<Ad> queryAll();
	
	/**
	 * 查询单件广告详细信息
	 * @param id
	 * @return
	 */
	Ad queryOneAd(@Param("id") Integer id);

}
