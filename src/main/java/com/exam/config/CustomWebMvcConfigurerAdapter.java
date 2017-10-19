package com.exam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new ManagerInterceptor()).addPathPatterns("/errorQuestion/**");  //对来自/user/** 这个链接来的请求进行拦截
        InterceptorRegistration ir = registry.addInterceptor(new ManagerInterceptor());
        ir.excludePathPatterns("/signin");
        ir.excludePathPatterns("/error1");
        ir.excludePathPatterns("/");
        ir.excludePathPatterns("/manager");
    }
}
