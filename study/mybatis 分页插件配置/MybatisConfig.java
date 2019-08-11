package com.shixun.config;


import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * MyBatis PageHelper 插件配置
 * @author yjh
 */
@Configuration
public class MybatisConfig {

	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("dialect", "Mysql");
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
	}
	
}
