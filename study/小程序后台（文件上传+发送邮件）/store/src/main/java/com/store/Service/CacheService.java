package com.store.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.Entity.Cache;
import com.store.Entity.Shop;

public interface CacheService {

	

	/**
	 * 添加商品缓存
	 * @param shop
	 * @return
	 */
	Integer addCache(Cache cache);
	
	/**
	 * 查询用户所有缓存
	 * @param uid
	 * @return
	 */
	List<Cache> queryAllCache(@Param("uid") Integer uid);
	
	/**
	 * 查询用户缓存
	 * @param uid
	 * @return
	 */
	List<Cache> queryOneCache(@Param("uid") Integer uid,@Param("type") String type);
	
	/**
	 * 修改用户的图片缓存
	 * @param type
	 * @param name
	 * @param uid
	 * @return
	 */
	Integer updateCache(@Param("type") String type,@Param("name") String name,@Param("uid") Integer uid);
	
	/**
	 * 删除用户的缓存
	 * @param uid
	 * @return
	 */
	Integer deleteCache(@Param("uid") Integer uid);

}
