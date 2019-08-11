package com.anchor.Service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anchor.Dao.WalletMapper;
import com.anchor.Entity.Wallet;

@Service("WalletService ")
public class WalletServiceImpl implements WalletService {

	private @Autowired WalletMapper WalletMapper;
	@Override
	public Wallet queryByUid(Integer uid) {
		// TODO Auto-generated method stub
		return WalletMapper.queryByUid(uid);
	}

	@Override
	public Integer addWallet(Wallet wallet) {
		// TODO Auto-generated method stub
		return WalletMapper.addWallet(wallet);
	}

	@Override
	public Integer updateUserMoney(BigDecimal money, Integer uid) {
		// TODO Auto-generated method stub
		return WalletMapper.updateUserMoney(money, uid);
	}

}
