package com.store.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.store.Entity.Task;

@Mapper
public interface TaskMapper {

	
	/**
	 * 添加任务
	 * @param Task
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
	 * 查询单件任务详细信息
	 * @param id
	 * @return
	 */
	Task queryOneTask(@Param("id") Integer id);
	
	/**
	 * 查询所有的任务
	 * @param uid
	 * @return
	 */
	List<Task> queryAll();
	
	/**
	 * 关闭任务
	 * @param id
	 * @return
	 */
	Integer updateTask(@Param("id") Integer id);
}
