package com.anchor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anchor.Dao.AnchorMapper;
import com.anchor.Entity.Anchor;

@Service("AnchorService")
public class AnchorServiceImpl implements AnchorService{

	
	private @Autowired AnchorMapper AnchorMapper;
	
	@Override
	public Integer addAnchor(Anchor anchor) {
		// TODO Auto-generated method stub
		return AnchorMapper.addAnchor(anchor);
	}

	@Override
	public List<Anchor> queryByTiao(Anchor anchor) {
		// TODO Auto-generated method stub
		return AnchorMapper.queryByTiao(anchor);
	}

	@Override
	public Integer updateType(String phone, String type) {
		// TODO Auto-generated method stub
		return AnchorMapper.updateType(phone, type);
	}

	@Override
	public Integer updatePhoneById(String phone, Integer id) {
		// TODO Auto-generated method stub
		return AnchorMapper.updatePhoneById(phone, id);
	}

	@Override
	public Integer updateHsNicknameByUid(String hsNickname, Integer id) {
		// TODO Auto-generated method stub
		return AnchorMapper.updateHsNicknameByUid(hsNickname, id);
	}

	

}
