package com.anchor.Controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anchor.Entity.Anchor;
import com.anchor.Entity.User;
import com.anchor.Entity.Wallet;
import com.anchor.Service.AnchorService;
import com.anchor.Service.LogService;
import com.anchor.Service.LoginService;
import com.anchor.Service.WalletService;
import com.anchor.Util.PreventRepeat;

@Controller
@RequestMapping("anchor")
@Transactional(propagation = Propagation.REQUIRED)
public class AnchorController {

	
	
	private @Resource AnchorService anchorService;
	private @Resource LoginService LoginService;
	private @Resource WalletService WalletService;
	private @Resource LogService LogService;
	
	
	/**
	 * 添加主播
	 * @param phone
	 * @param realName
	 * @param vxNumber
	 * @param hsNickname
	 * @param hsNumber
	 * @return
	 */
	@RequestMapping("addAnchor")
	@ResponseBody
	@PreventRepeat
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> addAnchor(@RequestParam("phone") String phone,
			@RequestParam("realName") String realName,
			@RequestParam("vxNumber") String vxNumber,
			@RequestParam("hsNickname") String hsNickname,
			@RequestParam("hsNumber") String hsNumber){
		Map<String, Object> map=new HashMap<String, Object>();
		if (phone==null||realName==null||vxNumber==null||hsNickname==null||hsNumber==null) {
			map.put("mes", "系统异常！");
			return map;
		}
		User user = LoginService.queryByPhone(phone);
		if (user==null) {
			User user1=new User();
			user1.setPhone(phone);
			user1.setType("anchor");
			LoginService.addOpenId(user1);
			user=LoginService.queryByPhone(phone);//添加 用户表   
			Anchor anchor=new Anchor();
			anchor.setHsNickname(hsNickname);
			anchor.setRealName(realName);
			anchor.setHsNumber(hsNumber);
			anchor.setVxNumber(vxNumber);
			anchor.setUid(user.getId());
			anchorService.addAnchor(anchor);//添加主播信息表
			Wallet wallet=new Wallet();
			wallet.setMoney(new BigDecimal("0.00"));
			wallet.setUid(user.getId());
			WalletService.addWallet(wallet);//添加 钱包表
			map.put("mes", "添加成功");
		}else {
			anchorService.updateType(phone, "anchor");//修改用户身份
			Anchor anchor=new Anchor();
			anchor.setHsNickname(hsNickname);
			anchor.setRealName(realName);
			anchor.setHsNumber(hsNumber);
			anchor.setVxNumber(vxNumber);
			anchor.setUid(user.getId());
			anchorService.addAnchor(anchor);//添加主播信息表
			Wallet wallet=new Wallet();
			wallet.setMoney(new BigDecimal("0.00"));
			wallet.setUid(user.getId());
			WalletService.addWallet(wallet);//添加 钱包表
			map.put("mes", "添加成功");
		}
		return map;
	}
	
	/**
	 * 手机号查重
	 * @param phone
	 * @return
	 */
	@RequestMapping("queryPhone")
	@ResponseBody
	public Map<String, Object> queryPhone(@RequestParam("phone") String phone){
		Map<String, Object> map=new HashMap<String, Object>();
		User user = LoginService.queryByPhone(phone);
		if (user!=null) {
			if (user.getType().equals("anchor")) {
				map.put("mes", "手机号已存在!");
			}
			if (user.getType().equals("user")) {
				map.put("mes", "√");
			}
		}
		if (user==null) {
			map.put("mes", "√");
		}
		
		return map;
	}
	
	
	/**
	 * 昵称查重
	 * @param nickname
	 * @return
	 */
	@RequestMapping("queryHsNickname")
	@ResponseBody
	public Map<String, Object> queryHsNickname(@RequestParam("nickname") String nickname){
		Map<String, Object> map=new HashMap<>();
		Anchor anchor=new Anchor();
		anchor.setHsNickname(nickname);
		List<Anchor> queryByTiao = anchorService.queryByTiao(anchor);
		if (queryByTiao.size()>0) {
			map.put("mes",1);
		}else {
			map.put("mes",0);
		}
		return map;
	}
	
	/**
	 * 微信号查重
	 * @param nickname
	 * @return
	 */
	@RequestMapping("queryVxNumber")
	@ResponseBody
	public Map<String, Object> queryVxNumber(@RequestParam("vxNumber") String vxNumber){
		Map<String, Object> map=new HashMap<>();
		Anchor anchor=new Anchor();
		anchor.setVxNumber(vxNumber);
		List<Anchor> queryByTiao = anchorService.queryByTiao(anchor);
		if (queryByTiao.size()>0) {
			map.put("mes",1);
		}else {
			map.put("mes",0);
		}
		return map;
	}
	
	
	/**
	 * 火山号查重
	 * @param nickname
	 * @return
	 */
	@RequestMapping("queryHsNumber")
	@ResponseBody
	public Map<String, Object> queryHsNumber(@RequestParam("hsNumber") String hsNumber){
		Map<String, Object> map=new HashMap<>();
		Anchor anchor=new Anchor();
		anchor.setHsNumber(hsNumber);
		List<Anchor> queryByTiao = anchorService.queryByTiao(anchor);
		if (queryByTiao.size()>0) {
			map.put("mes",1);
		}else {
			map.put("mes",0);
		}
		return map;
	}
	
	
	/**
	 * 查询手机号 和 昵称
	 * @param phone
	 * @param name
	 * @return
	 */
	@RequestMapping("queryPhoneAndName")
	@ResponseBody
	public Map<String, Object> queryPhoneAndName(@RequestParam("phone") String phone,@RequestParam("name") String name,@RequestParam("id") Integer id ){
		Map< String, Object> map=new HashMap<>();
		User user = LoginService.queryByPhone(phone);
		if (user!=null) {
			if (user.getType().equals("anchor")&&user.getId()!=id) {
				map.put("mes", "phone");
				return map;
			}
			if (user.getType().equals("anchor")&&user.getId()==id) {
				map.put("mes", 0);
				return map;
			}
			if (user.getType().equals("user")) {
				map.put("mes", 0);
			}
		}
		if (user==null) {
			map.put("mes", 0);
		}
		
		Anchor anchor=new Anchor();
		anchor.setHsNickname(name);
		List<Anchor> queryByTiao = anchorService.queryByTiao(anchor);
		if (queryByTiao.size()>0) {
			if (queryByTiao.get(0).getUid()!=id) {
				map.put("mes","name");
			}else {
				map.put("mes",0);
			}
		}else {
			map.put("mes",0);
		}
		return map;
	}
	
	/**
	 * 修改主播的 手机号 和 火山昵称
	 * @param phone
	 * @param name
	 * @param id
	 * @return
	 */
	@RequestMapping("updateAnchorMes")
	@ResponseBody
	@PreventRepeat
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> updateAnchorMes(@RequestParam("phone") String phone,
			@RequestParam("name") String name,
			@RequestParam("id") Integer id){
		Map<String, Object> map=new HashMap<>();
		Integer result = anchorService.updatePhoneById(phone, id);
		Integer result1 = anchorService.updateHsNicknameByUid(name, id);
		LogService.updateLog(phone, name, id);
		if (result==1&&result1==1) {
			map.put("mes", 1);
		}else {
			map.put("mes", 0);
		}
		return map;
 	}
	
	
	
	
}
