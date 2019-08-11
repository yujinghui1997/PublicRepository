package com.anchor.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anchor.Dao.LoginMapper;
import com.anchor.Entity.User;

@Service("LoginService")
public class LoginServiceImpl implements LoginService{

	private @Autowired LoginMapper LoginMapper;
	
	@Override
	public User queryByOpenId(String openid) {
		// TODO Auto-generated method stub
		return LoginMapper.queryByOpenId(openid);
	}

	@Override
	public Integer addOpenId(User user) {
		// TODO Auto-generated method stub
		return LoginMapper.addOpenId(user);
	}

	@Override
	public User queryById(Integer id) {
		// TODO Auto-generated method stub
		return LoginMapper.queryById(id);
	}

	@Override
	public User queryByPhone(String Phone) {
		// TODO Auto-generated method stub
		return LoginMapper.queryByPhone(Phone);
	}

	@Override
	public Integer updateByPhone(String phone, String openid) {
		// TODO Auto-generated method stub
		return LoginMapper.updateByPhone(phone, openid);
	}
	
}
