package com.store.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.Entity.Ad;
import com.store.Entity.Car;
import com.store.Entity.Shop;

public interface CarService {

	

	/**
	 * 添加购物车
	 * @param car
	 * @return
	 */
	Integer addCar(Car car);
	
	
	/**
	 * 通过id 查询购物车
	 * @param id
	 * @return
	 */
	Car queryBySid(@Param("sid") Integer sid);

	
	/**
	 * 查询用户的购物车里的所有商品
	 * @param uid
	 * @return
	 */
	List<Shop> queryUserAllCar(@Param("uid") Integer uid);
	

	/**
	 * 清空购物车
	 * @param uid
	 * @return
	 */
	Integer deleteCar(@Param("uid") Integer uid);
	
	/**
	 * 删除购物车里的一件商品
	 * @param sid
	 * @param uid
	 * @return
	 */
	Integer deleteOneCar(@Param("sid") Integer sid,@Param("uid") Integer uid);
}
