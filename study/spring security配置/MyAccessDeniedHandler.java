package com.shixun.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户访问一个没有权限访问的接口
 * 返回错误json信息
 * @author Administrator
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 返回json形式的错误信息     
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().println("{\"code\":403,\"message\":\"access denied！\",\"data\":\"权限不足，拒绝访问\"}");
		response.getWriter().flush();
	}

}
