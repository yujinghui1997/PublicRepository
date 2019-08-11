package com.store.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.Entity.Shop;

public interface ShopService {

	

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	Integer addShop(Shop shop);
	
	/**
	 * 查询用户所有的商品
	 * @param uid
	 * @return
	 */
	List<Shop> queryAllShop(@Param("uid") Integer uid);
	
	
	/**
	 * 查询所有的商品
	 * @param uid
	 * @return
	 */
	List<Shop> queryAll();
	
	/**
	 * 查询单件商品详细信息
	 * @param id
	 * @return
	 */
	Shop queryOneShop(@Param("id") Integer id);
	
	

	/**
	 * 下架商品
	 * @param ids
	 * @return
	 */
	Integer downShop(@Param("ids") Integer[] ids);

}
