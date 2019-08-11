package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;


//springboot 启动的注解
@SpringBootApplication
//扫包的注解
@ComponentScan(basePackages = {"com.test"})
//允许创建缓存
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.test")
public class App  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		System.out.println("-------------------");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(App.class);
	}
}
