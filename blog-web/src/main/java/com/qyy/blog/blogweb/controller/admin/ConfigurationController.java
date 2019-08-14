package com.qyy.blog.blogweb.controller.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qyy.blog.blogfacade.service.ConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Controller
@RequestMapping("/admin")
public class ConfigurationController {

    @Reference
    private ConfigService configService;

    @GetMapping("/configurations")
    public String configurations(HttpServletRequest request){
        request.setAttribute("path","configurations");
        request.setAttribute("configurations",configService.getAllConfigs());
        return "admin/configuration";
    }
}
