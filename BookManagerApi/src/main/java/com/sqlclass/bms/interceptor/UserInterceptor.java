package com.sqlclass.bms.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.sqlclass.bms.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("userObj");

        if(user == null) {  
            System.out.println("没有登录!不能访问!");
            response.sendRedirect(request.getContextPath() + "/index.html");
            return false;
        }

        return true;
    }

}