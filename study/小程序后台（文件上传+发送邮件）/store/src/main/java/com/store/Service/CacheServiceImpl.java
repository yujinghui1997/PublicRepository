package com.store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.Dao.CacheMapper;
import com.store.Dao.ShopMapper;
import com.store.Entity.Cache;
import com.store.Entity.Shop;

@Service("CacheService")
public class CacheServiceImpl implements CacheService {

	private @Autowired CacheMapper CacheMapper;

	@Override
	public Integer addCache(Cache cache) {
		// TODO Auto-generated method stub
		return CacheMapper.addCache(cache);
	}

	@Override
	public List<Cache> queryAllCache(Integer uid) {
		// TODO Auto-generated method stub
		return CacheMapper.queryAllCache(uid);
	}

	@Override
	public List<Cache> queryOneCache(Integer uid, String type) {
		// TODO Auto-generated method stub
		return CacheMapper.queryOneCache(uid, type);
	}

	@Override
	public Integer updateCache(String type, String name, Integer uid) {
		// TODO Auto-generated method stub
		return CacheMapper.updateCache(type, name, uid);
	}

	@Override
	public Integer deleteCache(Integer uid) {
		// TODO Auto-generated method stub
		return CacheMapper.deleteCache(uid);
	}

	
	
	
	

}
