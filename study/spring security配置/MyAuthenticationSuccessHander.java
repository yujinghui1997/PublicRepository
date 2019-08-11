package com.shixun.config;

import com.shixun.util.JwtUtil;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 自定义认证逻辑通过后
 * 处理认证成功后的业务
 * 根据用户生成token
 */
@Component
public class MyAuthenticationSuccessHander implements AuthenticationSuccessHandler {

    //私钥
    @Value("${spring.jwt.privateKey}")
    private  String key;
    //失效时间
    @Value("${spring.jwt.expTime}")
    private  long expTime;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        //取出登陆用户信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        //生成token
        String token = JwtUtil.createJWT(expTime,key,username);
        session.setAttribute(username,token);
        response.sendRedirect("/api/admin/goadmin.do");
    }
    
    
}
