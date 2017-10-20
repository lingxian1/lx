package com.exam.config;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            String uid = null;
            String token = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    uid = cookie.getValue();
                }
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
            System.out.println("context:" + request.getContextPath());
            System.out.println("uri:" + request.getRequestURI());
            System.out.println("method:" + request.getMethod());
            System.out.println("URL:" + request.getRequestURL());
            System.out.println();
            String s = check(token, uid);
            if(s.equals("ERROR")||s.equals("TIMEOUT")){
                return false;
            }
            if (request.getRequestURI().toLowerCase().contains(s)) {
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/error1");//重定向
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public String check(String token, String uid) {
        String status = new EasyToken().checkToken(new Token(uid, token));
        return status;
    }
}
