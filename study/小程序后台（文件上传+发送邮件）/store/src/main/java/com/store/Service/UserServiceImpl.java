package com.store.Service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.Dao.UserMapper;
import com.store.Entity.User;

@Service("UserService")
public class UserServiceImpl implements UserService {

	private @Autowired UserMapper UserMapper;
	
	@Override
	public Integer addUser(User user) {
		// TODO Auto-generated method stub
		return UserMapper.addUser(user);
	}

	@Override
	public User queryUserByEmail(String email) {
		// TODO Auto-generated method stub
		return UserMapper.queryUserByEmail(email);
	}

	@Override
	public User login(String email, String password) {
		// TODO Auto-generated method stub
		return UserMapper.login(email, password);
	}

	@Override
	public User queryById(Integer id) {
		// TODO Auto-generated method stub
		return UserMapper.queryById(id);
	}

	@Override
	public Integer updateMoney(Integer uid, BigDecimal money) {
		// TODO Auto-generated method stub
		return UserMapper.updateMoney(uid, money);
	}

}
