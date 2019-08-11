package com.anchor.Dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.anchor.Entity.Wallet;

public interface WalletMapper {

	/**
	 * 通过uid 查询用户的钱包
	 * @param uid
	 * @return
	 */
	Wallet queryByUid(@Param("uid") Integer uid);
	
	/**
	 * 新增钱包
	 * @param wallet
	 * @return
	 */
	Integer addWallet(Wallet wallet);
	
	/**
	 * 减少用户余额
	 * @param money
	 * @return
	 */
	Integer updateUserMoney(@Param("money") BigDecimal money,@Param("uid") Integer uid);
}
