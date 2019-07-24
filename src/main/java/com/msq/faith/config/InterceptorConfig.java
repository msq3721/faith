package com.msq.faith.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //需要拦截的路径，/**表示需要拦截所有请求
        String[]addPathPatterns={"/user/index/**"};
        String[] addAdminPatterns={"/admin/**"};

        //不需要拦截的路径
        String [] excludePathPaterns={
                "/boot/put",
                "/myservlet"
        };
        String [] excludeAdminPaterns={
                "/admin/login",
                "/admin/login/login"
        };
        //注册一个登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(addPathPatterns)
                .excludePathPatterns(excludePathPaterns);
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns(addAdminPatterns)
                .excludePathPatterns(excludeAdminPaterns);
        //注册一个权限拦截器  如果有多个拦截器 ，只需要添加以下一行代码
        //registry.addInterceptor(new LoginInterceptor())
        // .addPathPatterns(addPathPatterns)
        // .excludePathPatterns(excludePathPatterns);

    }
}