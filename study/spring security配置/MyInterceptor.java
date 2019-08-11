package com.shixun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *token 检验token
 */
@Configuration
public class MyInterceptor implements WebMvcConfigurer {

    @Autowired
    private  MyHandlerInterceptor myHandlerInterceptor;//token 验证
    /**
     * 添加token拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/api/**");//配置拦截规则
    }

}

