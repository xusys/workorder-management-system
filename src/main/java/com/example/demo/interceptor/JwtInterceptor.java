package com.example.demo.interceptor;

import com.example.demo.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的token
        String token=request.getHeader("token");
        try{
            JwtUtil.verifyToken(token);
            return true;
        }catch (Exception Exception){
            response.sendError(401);
            return false;
        }
    }
}

