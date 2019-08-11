package com.anchor.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anchor.Entity.Log;

public interface LogService {

	/**
	 * 增加日志
	 * @param log
	 * @return
	 */
	Integer addLog(Log log);
	
	/**
	 * 查询所有日志
	 * @return
	 */
	List<Log> queryAll(@Param("page") Integer page,@Param("count") Integer count);
	
	/**
	 * 查询最大页数
	 * @return
	 */
	Integer queryMaxPage();
	
	/**
	 * 修改日志
	 * @param phone
	 * @param hsNickname
	 * @param uid
	 * @return
	 */
	Integer updateLog(@Param("phone") String phone,@Param("hsNickname") String hsNickname,@Param("uid") Integer uid);
}
