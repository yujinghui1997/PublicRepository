package com.anchor.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.util.Base64;
import com.anchor.Entity.Anchor;
import com.anchor.Entity.Log;
import com.anchor.Entity.User;
import com.anchor.Entity.Wallet;
import com.anchor.Service.AnchorService;
import com.anchor.Service.LogService;
import com.anchor.Service.LoginService;
import com.anchor.Service.WalletService;
import com.anchor.Service.WxPayService;
import com.anchor.Util.BASE64Util;
import com.anchor.Util.PayUtil;
import com.anchor.Util.PreventRepeat;

@Controller
@RequestMapping("wallet")
public class WalletController {

	private @Resource WxPayService WxPayService;
	private @Resource LoginService LoginService;
	private @Resource WalletService WalletService;
	private @Resource AnchorService anchorService;
	private @Resource LogService LogService;

	/**
	 * 提现
	 * 
	 * @param money
	 * @param id
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 */
	@RequestMapping("withdraw")
	@ResponseBody
	@PreventRepeat
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> withdraw(HttpServletRequest request, @RequestParam("money") BigDecimal money,
			@RequestParam("id") Integer id) throws KeyManagementException, UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Wallet wallet = WalletService.queryByUid(id);
		BigDecimal money2 = wallet.getMoney();
		// 用户余额 小于等于0 只有10 提现100 提现 X<=0
		if (money2.compareTo(BigDecimal.ZERO) < 1 || money2.compareTo(money) == -1
				|| money.compareTo(BigDecimal.ZERO) < 1) {
			System.out.println("违法参数");
			map.put("mes", "系统错误");
		}
		if (wallet.getState()==1) {
			map.put("mes", "账户已被冻结！");
			return map;
		}
		User user = LoginService.queryById(id);// openid
		Anchor anchor=new Anchor();
		anchor.setUid(id);
		List<Anchor> queryByTiao = anchorService.queryByTiao(anchor);
		String tradeNo = PayUtil.getTradeNo();// 商户订单号
		// 微信 提现 以分 为单位
		BigDecimal bai = new BigDecimal("100");
		money = money.multiply(bai);
		String orderAmount = money.toString();
		// WxPayUtil.enterprisePayment(tradeNo,"于景辉",money.intValue(),"172.16.15.9",user.getOpenid(),request);
		Integer result = WxPayService.transferPay(request, user.getOpenid(), tradeNo, orderAmount);
		//Integer result =3;
		if (result == 1) {
			System.out.println("转账成功");
			money = money.divide(bai);
			WalletService.updateUserMoney(money, id);// 扣除用户的钱
			wallet = WalletService.queryByUid(id);
			Log log=new Log();
			log.setRealName(queryByTiao.get(0).getRealName());
			log.setPhone(user.getPhone());
			log.setVxNumber(queryByTiao.get(0).getVxNumber());
			log.setHsNickname(queryByTiao.get(0).getHsNickname());
			log.setHsNumber(queryByTiao.get(0).getHsNumber());
			log.setMoney(wallet.getMoney());
			log.setTiXian(money);
			log.setUid(id);
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dealTime=sdf.format(date);
			log.setDealTime(dealTime);
			LogService.addLog(log);
			map.put("mes1", "提现成功");
			map.put("money", wallet.getMoney());
		}
		if (result == 0) {
			System.out.println("转账失败");
			map.put("mes", "系统繁忙");
		}
		return map;
	}
	
	/**
	 * 发工资
	 * @param money
	 * @return
	 */
	@RequestMapping("fa")
	@ResponseBody
	@PreventRepeat
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> fa(@RequestParam("money") String moneyStr,@RequestParam("id") Integer id){
		moneyStr=BASE64Util.decode(moneyStr);
		BigDecimal money=new BigDecimal(moneyStr);
		Map<String, Object> map=new HashMap<>();
		Wallet wallet = WalletService.queryByUid(id);
		if (wallet.getState()==1) {
			map.put("err", "该账户处于冻结状态！");
			map.put("mes", "2");
			return map;
		}
		Integer result= WalletService.updateUserMoney(new BigDecimal("-"+money), id);
		if (result==1) {
			map.put("mes", 1);
		}else {
			map.put("mes", 0);
		}
		return map;
	}

	
	
}
