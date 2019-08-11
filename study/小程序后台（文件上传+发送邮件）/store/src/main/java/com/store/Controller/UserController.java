package com.store.Controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store.Entity.Shop;
import com.store.Entity.User;
import com.store.Service.ShopService;
import com.store.Service.UserService;
import com.store.Util.LocalLock;
import com.store.Util.Name;

@RestController
@RequestMapping(value = "user")
@Transactional(propagation = Propagation.REQUIRED)
public class UserController {

	private @Resource UserService userService;
	private @Autowired JavaMailSender mailSender;
	private @Resource ShopService ShopService;
	

	@Value("${spring.mail.username}")
	private String email;
	
	/**
	 * 发送邮箱验证码
	 * @param qq
	 * @return
	 * @throws MessagingException
	 */
	@RequestMapping(value = "sendemail/{qq}")
	//@LocalLock(key = "sendemail:arg[0]")
	public Map<String, Object>  sendEmail(@PathVariable("qq") String qq) throws MessagingException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean qqyz=qq.contains("@qq.com");
		if (qqyz==false) {
			map.put("mes", 0);
			return map;
		}
		int yzm=(int)(Math.random()*100000);
		String yzmStr=String.valueOf(yzm);
		boolean kg=true;
		while (kg) {
			if (yzmStr.length()>=6) {
				kg=false;
			}
			if (yzmStr.length()<6) {
				int yzm2=(int)(Math.random()*10);
				yzmStr+=String.valueOf(yzm2);
			}
		}
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom(email);
		message.setTo(qq);
		message.setSubject("微信商城验证");
		message.setText("【微信商城】-邮箱注册验证："+yzmStr);
		mailSender.send(message);
		map.put("mes", yzmStr);
		return map;
	}
	
	
	/**
	 * 注册
	 * @param email 邮箱
	 * @param password 密码
	 * @return
	 */
	@LocalLock(key = "book:arg[0]")
	@RequestMapping(value="regis/{email}/{password}")
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> regis(@PathVariable("email") String email,@PathVariable("password") String password,HttpSession session){
		Map<String, Object> map=new HashMap<>();
		boolean qqyz=email.contains("@qq.com");
		if (qqyz==false) {
			map.put("mes", 0);
			return map;
		}
		User user = userService.queryUserByEmail(email);
		if (user!=null) {//该邮箱已被注册！
			map.put("mes", 1);
			return map;
		}else {
			User user2=new User();
			user2.setEmail(email);
			user2.setPassword(password);
			user2.setMoney(new BigDecimal("10000"));
			String name = Name.getName();
			user2.setName(name);
			userService.addUser(user2);
		}
		user=userService.queryUserByEmail(email);
		session.setAttribute("id", user.getId());
		map.put("mes", 2);
		map.put("user", user);
		return map;
	}
	
	
	/**
	 * 登录
	 * @param email
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "login/{email}/{password}")
	public Map<String, Object> login(@PathVariable("email") String email,@PathVariable("password") String password){
		Map<String, Object> map=new HashMap<>();
		boolean qqyz=email.contains("@qq.com");
		if (qqyz==false) {
			map.put("mes", 0);
			return map;
		}
		User user = userService.login(email, password);
		if (user==null) {
			map.put("mes", 1);
			return map;
		}else {
			map.put("mes", 2);
			map.put("user", user);
			return map;
		}
	}
	
	/**
	 * 通过id查询用户
	 * @return
	 */
	@RequestMapping(value = "querybyid/{id}")
	public Map<String, Object> queryById(@PathVariable("id") Integer id){
		Map<String, Object> map=new HashMap<>();
		User user = userService.queryById(id);
		map.put("user", user);
		return map;
	}
	
	
	
	

}
