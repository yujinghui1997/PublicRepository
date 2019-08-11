package com.anchor.Service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anchor.Entity.Anchor;

public interface AnchorService {

	
	/**
	 * 新增主播
	 * @param anchor
	 * @return
	 */
	Integer addAnchor(Anchor anchor);
	
	/**
	 * 通过火山昵称查询主播
	 * @param nickname
	 * @return
	 */
	List<Anchor> queryByTiao(Anchor anchor);
	
	/**
	 * 修改用户的身份 为 主播
	 * @param anchor
	 * @param type
	 * @return
	 */
	Integer updateType(@Param("phone") String phone,@Param("type") String type);
	

	/**
	 * 通过id 修改  手机号
	 * @param phone
	 * @param id
	 * @return
	 */
	Integer updatePhoneById(@Param("phone") String phone,@Param("id") Integer id);
	
	
	/**
	 * 通过uid 修改  火山昵称
	 * @param phone
	 * @param id
	 * @return                                                                    
	 */
	Integer updateHsNicknameByUid(@Param("hsNickname") String hsNickname,@Param("uid") Integer id);
}
