package com.anchor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anchor.Dao.AdminMapper;
import com.anchor.Entity.Admin;
import com.anchor.Entity.AnchorMes;
import com.anchor.Entity.Log;

@Service("AdminService")
public class AdminServiceImpl implements AdminService {
	
	
	private @Autowired AdminMapper AdminMapper;

	@Override
	public Admin login(String username, String password) {
		// TODO Auto-generated method stub
		return AdminMapper.login(username, password);
	}

	@Override
	public List<AnchorMes> queryAll(Integer page,Integer count) {
		// TODO Auto-generated method stub
		return AdminMapper.queryAll(page,count);
	}

	@Override
	public Integer deleteUser(Integer id) {
		// TODO Auto-generated method stub
		return AdminMapper.deleteUser(id);
	}

	@Override
	public Integer deleteAnchor(Integer id) {
		// TODO Auto-generated method stub
		return AdminMapper.deleteAnchor(id);
	}

	@Override
	public Integer deleteWallet(Integer id) {
		// TODO Auto-generated method stub
		return AdminMapper.deleteWallet(id);
	}

	@Override
	public Integer deleteLog(Integer id) {
		// TODO Auto-generated method stub
		return AdminMapper.deleteLog(id);
	}

	@Override
	public Integer updateWalletState(Integer id, Integer state) {
		// TODO Auto-generated method stub
		return AdminMapper.updateWalletState(id, state);
	}

	@Override
	public List<AnchorMes> queryAllSouName(String data,Integer page,Integer count) {
		// TODO Auto-generated method stub
		return AdminMapper.queryAllSouName(data,page,count);
	}

	@Override
	public List<AnchorMes> queryAllSouPhone(String data,Integer page,Integer count) {
		// TODO Auto-generated method stub
		return AdminMapper.queryAllSouPhone(data,page,count);
	}

	@Override
	public Integer queryMaxPage() {
		// TODO Auto-generated method stub
		return AdminMapper.queryMaxPage();
	}

	@Override
	public Integer queryMaxPageName(String data) {
		// TODO Auto-generated method stub
		return AdminMapper.queryMaxPageName(data);
	}

	@Override
	public Integer queryMaxPagePhone(String data) {
		// TODO Auto-generated method stub
		return AdminMapper.queryMaxPagePhone(data);
	}

	@Override
	public List<Log> querySumMoney() {
		// TODO Auto-generated method stub
		return AdminMapper.querySumMoney();
	}

	@Override
	public Integer updataSessionId(String sessionId, String username, String password) {
		// TODO Auto-generated method stub
		return AdminMapper.updataSessionId(sessionId, username, password);
	}

	@Override
	public Admin queryById(Integer id) {
		// TODO Auto-generated method stub
		return AdminMapper.queryById(id);
	}

	

}
