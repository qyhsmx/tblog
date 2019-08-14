package com.qyy.blog.blogweb.controller.front;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author qyhsmx@outlook.com
 * @date 2019.8.1
 */

/**
 * 前台的接口
 */
@RestController
public class BlogFrontController {

    @GetMapping({"/","/index","/index.html"})
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("blog/amaze/index");
        return mv;
    }

}
