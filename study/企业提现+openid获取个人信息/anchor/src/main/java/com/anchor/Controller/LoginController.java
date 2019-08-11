package com.anchor.Controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.anchor.Entity.User;
import com.anchor.Entity.Wallet;
import com.anchor.Service.LogService;
import com.anchor.Service.LoginService;
import com.anchor.Service.WalletService;
import com.anchor.Util.PreventRepeat;
import com.anchor.Util.UserOpenId;
import com.anchor.Util.WechatGetUserInfoUtil;

@Controller
@RequestMapping("user")
public class LoginController {

	private @Resource LoginService loginService;
	private @Resource WalletService WalletService;
	
	
	
	// 查看用户身份
	@RequestMapping("queryUserType")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	@PreventRepeat
	public Map<String, Object> getWxUserOpenid(@RequestParam("id") Integer id) {
		Map<String, Object> map=new HashMap<String, Object>();
		User queryById = loginService.queryById(id);
		if (queryById.getType().equals("anchor")) {
			Wallet wallet = WalletService.queryByUid(id);
			map.put("type", 1);
			map.put("money",wallet.getMoney());
		}
		
		if (queryById.getType().equals("user")) {
			map.put("type", 0);
		}
		return map;
	}

	
	
	// 获取openid 钱
	@RequestMapping("login")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	@PreventRepeat
	public Map<String, Object> login(@RequestParam("code") String code, @RequestParam("APPID") String APPID,
			@RequestParam("APPSecret") String APPSecret) {
		Map<String, Object> map = UserOpenId.getOpenId(code, APPID, APPSecret);
		String openid = (String) map.get("openid");
		User user = loginService.queryByOpenId(openid);
		if (user != null) {
			map.put("mes", "ok");
			map.put("id", user.getId());
		} else {
			map.put("mes", "err");
		}
		
		return map;
	}

	// 授权
	@RequestMapping("shouquan")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
	@PreventRepeat
	public Map<String, Object> shouquan(@RequestParam("code") String code, @RequestParam("APPID") String APPID,
			@RequestParam("APPSecret") String APPSecret,@RequestParam("en") String en,@RequestParam("iv") String iv) {
		Map<String, Object> map = UserOpenId.getOpenId(code, APPID, APPSecret);
		String openid = (String) map.get("openid");
		String sessionkey = (String) map.get("session_key");
		User user = loginService.queryByOpenId(openid);
		if (user==null) {
			JSONObject userInfoJSON=WechatGetUserInfoUtil.getUserInfo(en,sessionkey,iv);
			if (userInfoJSON!=null) {
				String phone=(String)userInfoJSON.get("phoneNumber");
				User queryByPhone = loginService.queryByPhone(phone);
				if (queryByPhone==null) {
					User user1=new User();
					user1.setOpenid(openid);
					user1.setPhone(phone);
					user1.setType("user");
					loginService.addOpenId(user1);
				}else {
					loginService.updateByPhone(phone, openid);
				}
				
			}
			
		}
		user = loginService.queryByOpenId(openid);
		map.put("id", user.getId());
		return map;
	}

}
