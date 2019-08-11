package com.store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.Dao.ShopMapper;
import com.store.Entity.Shop;

@Service("ShopService")
public class ShopServiceImpl implements ShopService {

	private @Autowired ShopMapper ShopMapper;

	@Override
	public Integer addShop(Shop shop) {
		// TODO Auto-generated method stub
		return ShopMapper.addShop(shop);
	}

	@Override
	public List<Shop> queryAllShop(Integer uid) {
		// TODO Auto-generated method stub
		return ShopMapper.queryAllShop(uid);
	}

	@Override
	public Shop queryOneShop(Integer id) {
		// TODO Auto-generated method stub
		return ShopMapper.queryOneShop(id);
	}

	@Override
	public List<Shop> queryAll() {
		// TODO Auto-generated method stub
		return ShopMapper.queryAll();
	}

	@Override
	public Integer downShop(Integer[] ids) {
		// TODO Auto-generated method stub
		return ShopMapper.downShop(ids);
	}
	
	

}
