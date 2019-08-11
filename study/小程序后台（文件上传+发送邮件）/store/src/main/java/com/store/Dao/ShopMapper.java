package com.store.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.store.Entity.Shop;

@Mapper
public interface ShopMapper {

	
	/**
	 * 添加商品
	 * @param shop
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
	 * 查询单件商品详细信息
	 * @param id
	 * @return
	 */
	Shop queryOneShop(@Param("id") Integer id);
	
	/**
	 * 查询所有的商品
	 * @param uid
	 * @return
	 */
	List<Shop> queryAll();
	
	/**
	 * 下架商品
	 * @param ids
	 * @return
	 */
	Integer downShop(@Param("ids") Integer[] ids);
}
