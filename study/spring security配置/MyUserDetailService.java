package com.shixun.config;

import com.shixun.entity.Admin;
import com.shixun.entity.Permission;
import com.shixun.service.AdminService;
import com.shixun.service.RolePermissionService;
import com.shixun.util.BASE64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * spring security 安全框架
 * 用户自定义逻辑认证
 * @author Administrator
 */
@Component
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private AdminService adminService;//认证数据库
	@Autowired
	private RolePermissionService rolePermissionService;//查询权限
	@Autowired
	private PasswordEncode passwordEncode;//密码加密

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//通过过姓名查询管理员
		Admin admin = adminService.queryAdminByUsername(username);
		if (admin==null) {//用户不存在
			System.out.println("用户不存在");
			throw new UsernameNotFoundException("用户不存在");
		}
		//密码 加密
		String password = passwordEncode.encode(BASE64Util.decode(admin.getPassword()));
		//该管理员的权限集合
		List<Permission> list = rolePermissionService.queryAllByAid(admin.getId());
		List<SimpleGrantedAuthority> permissions = new ArrayList<SimpleGrantedAuthority>();
		for (Permission permission : list) {
			permissions.add(new SimpleGrantedAuthority(permission.getCode()));
		}
		//返回该管理员
		User user = new User(username, password, permissions);
		return user;
	}

}
