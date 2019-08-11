package com.store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.Dao.CarMapper;
import com.store.Dao.ShopMapper;
import com.store.Entity.Cache;
import com.store.Entity.Car;
import com.store.Entity.Shop;

@Service("CarService")
public class CarServiceImpl implements CarService {

	private @Autowired CarMapper CarMapper;

	@Override
	public Integer addCar(Car car) {
		// TODO Auto-generated method stub
		return CarMapper.addCar(car);
	}

	@Override
	public Car queryBySid(Integer sid) {
		// TODO Auto-generated method stub
		return CarMapper.queryBySid(sid);
	}

	@Override
	public List<Shop> queryUserAllCar(Integer uid) {
		// TODO Auto-generated method stub
		return CarMapper.queryUserAllCar(uid);
	}

	@Override
	public Integer deleteCar(Integer uid) {
		// TODO Auto-generated method stub
		return CarMapper.deleteCar(uid);
	}

	@Override
	public Integer deleteOneCar(Integer sid, Integer uid) {
		// TODO Auto-generated method stub
		return CarMapper.deleteOneCar(sid, uid);
	}

	

	
	
	
	

}
