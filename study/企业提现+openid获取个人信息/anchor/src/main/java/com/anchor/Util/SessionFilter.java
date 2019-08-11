package com.anchor.Util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;


public class SessionFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = request.getRequestURI();
        Object user = session.getAttribute("id");
        if (user == null) {
            request.getRequestDispatcher("/anchor/login.jsp").forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
