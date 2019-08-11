package com.store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.Dao.AdMapper;
import com.store.Entity.Ad;

@Service("AdService")
public class AdServiceImpl implements AdService {

	private @Autowired AdMapper AdMapper;

	@Override
	public Integer addAd(Ad Ad) {
		// TODO Auto-generated method stub
		return AdMapper.addAd(Ad);
	}

	@Override
	public List<Ad> queryAllAd(Integer uid) {
		// TODO Auto-generated method stub
		return AdMapper.queryAllAd(uid);
	}

	@Override
	public Ad queryOneAd(Integer id) {
		// TODO Auto-generated method stub
		return AdMapper.queryOneAd(id);
	}

	@Override
	public List<Ad> queryAll() {
		// TODO Auto-generated method stub
		return AdMapper.queryAll();
	}
	
	

}
