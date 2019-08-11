package com.shixun.config;

import com.shixun.util.CheckToken;
import com.shixun.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * token验证拦截器
 */
@Component
public class MyHandlerInterceptor extends HandlerInterceptorAdapter {

    @Value("${spring.jwt.privateKey}")
    private String key;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //获取方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //获取发送请求的人信息 （username）
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useranme = authentication.getName();
        //判断访问的后台接口 有没有 checkToken 注解 没有直接放行
        if (method.isAnnotationPresent(CheckToken.class)) {
            CheckToken checkToken = method.getAnnotation(CheckToken.class);
            // 判断该注解需不需要验证 不需要直接放行
            if (checkToken.required()) {
                HttpSession session = request.getSession();
                String token = (String) session.getAttribute(useranme);
                if (token == null) {
                    throw new RuntimeException("非法请求 Token cannot is null");
                }
                //验证token是否合法
                boolean isVerify = JwtUtil.isVerify(token, key, useranme);
                if (!isVerify) {
                    throw new RuntimeException("非法访问 Token is err");
                }
            }
            return true;
        }
        return true;
    }

}
