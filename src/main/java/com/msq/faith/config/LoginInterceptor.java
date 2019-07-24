package com.msq.faith.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override //进入Controller之前执行该方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录拦截的业务逻辑
        System.out.println("-------登录请求拦截器--------------");
        System.out.println(request.getRequestURI().toString());

        Object object = request.getSession().getAttribute("general_user");
        if (object == null) {
            System.out.println("用户没有登录");
            request.getRequestDispatcher("/index").forward(request,response);
            return false;
        }

        //继续提交请求，false 请求不提交了
        return true;

    }
}
