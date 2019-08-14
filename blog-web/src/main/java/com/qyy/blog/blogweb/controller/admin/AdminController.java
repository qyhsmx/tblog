package com.qyy.blog.blogweb.controller.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qyy.blog.blogfacade.entity.AdminUser;
import com.qyy.blog.blogfacade.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Reference
    AdminUserService adminUserService;

    @Reference
    private BlogService blogService;

    @Reference
    private CommentService commentService;

    @Reference
    private TagService tagService;

    @Reference
    private LinkService linkService;

    @Reference
    private CategoryService categoryService;

    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }

    @GetMapping({"","/","index","index.html"})
    public String index(HttpServletRequest request){

        //blog count
        request.setAttribute("path","index");
        request.setAttribute("blogCount",blogService.getTotalBlogs());
        request.setAttribute("commentCount",commentService.getTotalComments());
        request.setAttribute("categoryCount",categoryService.getTotalCategories());
        request.setAttribute("tagCount",tagService.getTotalTags());
        request.setAttribute("linkCount",linkService.getTotalLinks());

        return "admin/index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode")String verifyCode,
                        HttpSession session){

        //校验
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "admin/login";
        }

        //登陆逻辑

        AdminUser adminUser = adminUserService.login(userName, password);
        if(adminUser != null){
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            return "redirect:/admin/index";
        }else{
            session.setAttribute("errorMsg", "登陆失败");
            return "admin/login";

        }
    }

    @GetMapping("/profile")
    public String toUpdatePwd(HttpServletRequest request){

        log.info("后台管理-修改密码-接口参数列表{}",request);
        long start = System.currentTimeMillis();
        log.info("后台管理-修改密码-查询当前密码开始");

        Integer loginUserId = (Integer)request.getSession().getAttribute("loginUserId");
        AdminUser detail = adminUserService.getUserDetailById(loginUserId);
        request.setAttribute("loginUserName", detail.getLoginUserName());
        request.setAttribute("nickName", detail.getNickName());
        request.setAttribute("path","profile");

        long end = System.currentTimeMillis();
        log.info("后台管理-修改密码-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,detail.toString());

        return "admin/profile";
    }

    @PostMapping("/profile/name")
    @ResponseBody
    public String updateName(@RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName,HttpServletRequest request){

        log.info("后台管理-修改用户名-接口参数列表{} {}",loginUserName,nickName);
        long start = System.currentTimeMillis();
        log.info("后台管理-修改用户名-调用接口开始");

        int loginUserId = (Integer)request.getSession().getAttribute("loginUserId");
        Boolean aBoolean = adminUserService.updateName(loginUserId, loginUserName, nickName);

        long end = System.currentTimeMillis();
        log.info("后台管理-修改用户名-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return "success";
        }else {
            return "fail";
        }
    }
    @PostMapping("/profile/password")
    @ResponseBody
    public String updatePwd(@RequestParam("originalPassword") String originalPassword,
                             @RequestParam("newPassword") String newPassword,HttpServletRequest request){

        log.info("后台管理-修改密码-接口参数列表{} {}",originalPassword,newPassword);
        long start = System.currentTimeMillis();
        log.info("后台管理-修改密码-调用接口开始");

        int loginUserId = (Integer)request.getSession().getAttribute("loginUserId");

        Boolean aBoolean = adminUserService.updatePassword(loginUserId, originalPassword, newPassword);

        long end = System.currentTimeMillis();
        log.info("后台管理-修改密码-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return "success";
        }else {
            return "fail";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        log.info("正在登出系统。。。。。");
        HttpSession session = request.getSession();
        session.removeAttribute("loginUserId");
        session.removeAttribute("loginUser");
        log.info("登出系统成功");
        return "redirect:admin/login";
    }





}
