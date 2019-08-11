package com.store.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.store.Entity.Ad;
import com.store.Entity.Cache;
import com.store.Entity.Car;
import com.store.Entity.Shop;
import com.store.Entity.Task;
import com.store.Entity.User;
import com.store.Service.AdService;
import com.store.Service.CacheService;
import com.store.Service.CarService;
import com.store.Service.ShopService;
import com.store.Service.TaskService;
import com.store.Service.UserService;
import com.store.Util.LocalLock;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.javassist.expr.NewArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("deal")
public class DealController {

	private @Resource CarService CarService;
	private @Resource UserService UserService;
	private @Resource ShopService ShopService;
	
	
	/**
	 * 添加购物车
	 * @param id
	 * @param uid
	 * @return
	 */
	@LocalLock(key = "addcar:arg[0]")
	@RequestMapping(value = "addcar/{sid}/{uid}")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> addCar(@PathVariable("sid") Integer sid,@PathVariable("uid") Integer uid){
		Map<String, Object> map=new HashMap<>();
		Shop shop = ShopService.queryOneShop(sid);
		if (shop.getUid().equals(uid)) {
			map.put("mes", "不能购买自己的商品！");
			return map;
		}
		Car queryBySid = CarService.queryBySid(sid);
		if (queryBySid!=null) {
			if (queryBySid.getUid().equals(uid)) {
				map.put("mes", "该商品已存在！");
				return map;
			}else {
				map.put("mes", "已被其他买家预定！");
				return map;
			}
		}
		
		Car car=new Car();
		car.setSid(sid);
		car.setUid(uid);
		Integer result = CarService.addCar(car);
		if(result==1) {
			map.put("mes", "添加成功！");
		}else {
			map.put("mes", "系统异常！");
		}
		return map;
	}
		
	/**
	 * 查询用户的购物车
	 * @param id
	 * @return
	 */
	@RequestMapping(value="querycar/{uid}")
	public Map<String, Object> querycar(@PathVariable("uid") Integer uid){
		Map<String, Object> map=new HashMap<>();
		List<Shop> list = CarService.queryUserAllCar(uid);
		map.put("shop",list);
		return map;
	} 
	
	
	/**
	 * 用户开始支付
	 * @param uid
	 * @return
	 */
	@LocalLock(key = "pay:arg[0]")
	@RequestMapping(value="pay/{uid}")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> pay(@PathVariable("uid") Integer uid){
		Map<String, Object> map=new HashMap<String, Object>();
		User user = UserService.queryById(uid);
		BigDecimal money = user.getMoney();
		BigDecimal sum=new BigDecimal("0.00");
		List<Shop> list = CarService.queryUserAllCar(uid);
		if (list.size()==0) {
			return map;
		}
		for (Shop shop : list) {
			sum=sum.add(shop.getPrice());
		}
		if (sum.compareTo(money)==1||money.compareTo(BigDecimal.ZERO)<1) {
			map.put("mes", "余额不足");
			return map;
		}
		UserService.updateMoney(uid, new BigDecimal("-"+sum));//扣除买家的钱
		for (Shop shop : list) {
			UserService.updateMoney(shop.getUid(), shop.getPrice());//增加卖家的钱
		}
		CarService.deleteCar(uid);//清空购物车
		Integer [] ids=new Integer[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ids[i]=list.get(i).getId();
		}
		ShopService.downShop(ids);//下架商品
		map.put("mes", "支付成功");
		return map;
	}
	
	/**
	 * 删除购物车
	 * @param sid
	 * @param uid
	 * @return
	 */
	@LocalLock(key = "deletecar:arg[0]")
	@RequestMapping(value="deletecar/{sid}/{uid}")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> deletecar(@PathVariable("sid") Integer sid,@PathVariable("uid") Integer uid){
		Map<String, Object> map=new  HashMap<>();
		Integer deleteOneCar = CarService.deleteOneCar(sid, uid);
		if (deleteOneCar==1) {
			map.put("mes", "删除成功！");
		}else {
			map.put("mes", "系统错误！");
		}
		return map;
	}
	
	/**
	 *前往付款
	 * @param sid
	 * @param uid
	 * @return
	 */
	@LocalLock(key = "payOne:arg[0]")
	@RequestMapping(value = "payone/{sid}/{uid}")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> payOne(@PathVariable("sid") Integer sid,@PathVariable("uid") Integer uid){
		Map<String, Object> map=new HashMap<>();
		Shop shop = ShopService.queryOneShop(sid);
		User user = UserService.queryById(uid);
		BigDecimal money = user.getMoney();
		if (shop.getPrice().compareTo(money)==1||money.compareTo(BigDecimal.ZERO)<1) {
			map.put("mes", "余额不足！");
			return map;
		}
		UserService.updateMoney(uid, new BigDecimal("-"+shop.getPrice()));//扣除用户的钱
		UserService.updateMoney(shop.getUid(), shop.getPrice());//增加卖家的钱
		Integer [] ids=new Integer[1];
		ids[0]=sid;
		ShopService.downShop(ids);//下架商品
		Car queryBySid = CarService.queryBySid(sid);
		if (queryBySid!=null) {
			CarService.deleteOneCar(sid, uid);
		}
		map.put("mes", "支付成功");
		return map;
	}
	
	
	
}
