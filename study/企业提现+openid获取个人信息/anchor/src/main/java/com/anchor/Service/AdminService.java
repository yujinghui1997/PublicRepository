package com.anchor.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anchor.Entity.Admin;
import com.anchor.Entity.AnchorMes;
import com.anchor.Entity.Log;

public interface AdminService {

	

	/**
	 * 管理员登录
	 * @param username  账号
 	 * @param password  密码
	 * @return
	 */
	Admin login(@Param("username") String username,@Param("password") String password);
	
	/**
	 * 管理员登录
	 * @param username  账号
 	 * @param password  密码
	 * @return
	 */
	Admin queryById(@Param("id") Integer id);
	
	
	/**
	 * 删除主播 user表
	 * @param id
	 * @return
	 */
	Integer deleteUser(@Param("id") Integer id);
	/**
	 * 删除主播 anchor表
	 * @param id
	 * @return
	 */
	Integer deleteAnchor(@Param("id") Integer id);
	/**
	 * 删除主播 wallet表
	 * @param id
	 * @return
	 */
	Integer deleteWallet(@Param("id") Integer id);
	/**
	 * 删除主播 log表
	 * @param id
	 * @return
	 */
	Integer deleteLog(@Param("id") Integer id);
	
	/**
	 * 修改钱包状态
	 * @param id
	 * @param state
	 * @return
	 */
	Integer updateWalletState(@Param("uid") Integer id,@Param("state") Integer state);
	

	/**
	 * 查询所有主播
	 * @return
	 */
	List<AnchorMes> queryAll(@Param("page") Integer page,@Param("count") Integer count);
	
	/**
	 * 查询所有主播 搜索姓名
	 * @return
	 */
	List<AnchorMes> queryAllSouName(@Param("data") String data,@Param("page") Integer page,@Param("count") Integer count);
	
	/**
	 * 查询所有主播 搜索手机号
	 * @return
	 */
	List<AnchorMes> queryAllSouPhone(@Param("data") String data,@Param("page") Integer page,@Param("count") Integer count);
	
	
	/**
	 * 查询最大页数
	 * @return
	 */
	Integer queryMaxPage();
	
	/**
	 * 查询最大页数 搜索姓名
	 * @return
	 */
	Integer queryMaxPageName(@Param("data") String data);
	
	/**
	 * 查询最大页数 搜索手机号
	 * @return
	 */
	Integer queryMaxPagePhone(@Param("data") String data);
	

	/**
	 * 查询每隔人的提现总额
	 * @return
	 */
	List<Log> querySumMoney();
	
	/**
	 * 获取session id 
	 * @param sessionId
	 * @param username
	 * @param password
	 * @return
	 */
	Integer updataSessionId(@Param("sessionId") String sessionId,@Param("username") String username,@Param("password") String password);
}
