package com.test.Service;

import org.springframework.stereotype.Service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.test.Entity.User;

@Service
public class UserServiceImpl implements UserService {

	/**
	 * 添加缓存
	 * @CacheRefresh 自动刷新缓存
	 */
	@Override
	@CacheRefresh(refresh = 5)
	@Cached(name = "usercache.",key = "#id",expire = 8,cacheType = CacheType.BOTH)
	public User getUser(Integer id) {
		System.out.println("********************");
		// TODO Auto-generated method stub
		User user=new User();
		user.setId(1);
		user.setName("李白dsadas");
		user.setSex("男");
		return user;
	}

	/**
	 * 更新缓存
	 */
	@Override
	@CacheUpdate(name = "usercache.",key = "#user.id",value = "#user")
	public void update(User user) {
		// TODO Auto-generated method stub
	}

	/**
	 * 移除缓存
	 */
	@Override
	@CacheInvalidate(name = "usercache.",key = "#id")
	public void del(Integer id) {
		// TODO Auto-generated method stub
		
	}


	

}
