package com.example.demo.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
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
            System.out.println(token);
            return true;
        }catch (TokenExpiredException Exception){
            response.sendError(401);
            System.out.println("no");
            return false;
        }
        catch (Exception e){
            response.sendError(401);
            System.out.println("null");
            return false;
        }
    }
}

