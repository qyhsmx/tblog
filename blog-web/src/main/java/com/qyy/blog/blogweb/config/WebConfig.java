package com.qyy.blog.blogweb.config;

import com.qyy.blog.blogweb.intercepter.WebIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer {


    @Autowired
    private WebIntercepter webIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(webIntercepter).addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/dist/**")
                .excludePathPatterns("/admin/plugins/**");

    }



}
