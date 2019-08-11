package com.store.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.Entity.Task;

public interface TaskService {

	

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	Integer addTask(Task Task);
	
	/**
	 * 查询用户所有的任务
	 * @param uid
	 * @return
	 */
	List<Task> queryAllTask(@Param("uid") Integer uid);
	
	
	/**
	 * 查询所有的任务
	 * @param uid
	 * @return
	 */
	List<Task> queryAll();
	
	/**
	 * 查询单件任务详细信息
	 * @param id
	 * @return
	 */
	Task queryOneTask(@Param("id") Integer id);
	
	/**
	 * 关闭任务
	 * @param id
	 * @return
	 */
	Integer updateTask(@Param("id") Integer id);
	

}
