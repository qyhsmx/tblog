package com.qyy.blog.blogweb.intercepter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Component
public class WebIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取uri地址
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        if(requestURI.startsWith("/admin") && null==session.getAttribute("loginUser")){
            session.setAttribute("errorMsg","请重新登陆！");
            response.sendRedirect(request.getContextPath()+"/admin/login");
            return false;
        }else{
            session.removeAttribute("errorMsg");
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
