package com.sqlclass.bms.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.sqlclass.bms.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReaderInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("userObj");

        if(user.getIsadmin() == 0) {    
            System.out.println("读者不能进管理员界面!");
            response.sendRedirect(request.getContextPath() + "/index.html");
            return false;
        }
        return true;
    }
}