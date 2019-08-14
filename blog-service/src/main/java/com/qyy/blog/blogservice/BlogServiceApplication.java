package com.qyy.blog.blogservice;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@DubboComponentScan(basePackages = "com.qyy.blog.blogservice.service.impl")
//@ImportResource("classpath:dubbo.xml")
@EnableDubbo
@MapperScan(basePackages = "com.qyy.blog.blogservice.dao")
public class BlogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServiceApplication.class, args);
    }

}
