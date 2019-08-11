package com.store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.Dao.SettingMapper;

@Service("SettingService")
public class SettingServiceImpl implements SettingService {

	private @Autowired SettingMapper SettingMapper;


	@Override
	public Integer updatePassword(Integer id, String oldpass, String newpass) {
		// TODO Auto-generated method stub
		return SettingMapper.updatePassword(id, oldpass, newpass);
	}


	@Override
	public Integer updateName(Integer id, String name) {
		// TODO Auto-generated method stub
		return SettingMapper.updateName(id, name);
	}

	
	
	
	

}
