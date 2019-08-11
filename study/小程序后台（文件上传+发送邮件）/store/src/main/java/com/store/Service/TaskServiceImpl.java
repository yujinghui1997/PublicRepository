package com.store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.Dao.TaskMapper;
import com.store.Entity.Task;

@Service("TaskService")
public class TaskServiceImpl implements TaskService {

	private @Autowired TaskMapper TaskMapper;

	@Override
	public Integer addTask(Task Task) {
		// TODO Auto-generated method stub
		return TaskMapper.addTask(Task);
	}

	@Override
	public List<Task> queryAllTask(Integer uid) {
		// TODO Auto-generated method stub
		return TaskMapper.queryAllTask(uid);
	}

	@Override
	public Task queryOneTask(Integer id) {
		// TODO Auto-generated method stub
		return TaskMapper.queryOneTask(id);
	}

	@Override
	public List<Task> queryAll() {
		// TODO Auto-generated method stub
		return TaskMapper.queryAll();
	}

	@Override
	public Integer updateTask(Integer id) {
		// TODO Auto-generated method stub
		return TaskMapper.updateTask(id);
	}
	
	

}
