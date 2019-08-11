package com.anchor.Service;

import java.util.List;
import java.util.logging.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anchor.Dao.LogMapper;
import com.anchor.Entity.Log;

@Service("LogService")
public class LogServiceImpl implements LogService {

	private @Autowired LogMapper LogMapper;
	
	@Override
	public Integer addLog(Log log) {
		// TODO Auto-generated method stub
		return LogMapper.addLog(log);
	}

	@Override
	public List<Log> queryAll(Integer page,Integer count) {
		// TODO Auto-generated method stub
		return LogMapper.queryAll(page,count);
	}

	@Override
	public Integer queryMaxPage() {
		// TODO Auto-generated method stub
		return LogMapper.queryMaxPage();
	}

	@Override
	public Integer updateLog(String phone, String hsNickname, Integer uid) {
		// TODO Auto-generated method stub
		return LogMapper.updateLog(phone, hsNickname, uid);
	}

}
