package com.qyy.blog.blogweb.controller.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qyy.blog.blogfacade.entity.BlogLink;
import com.qyy.blog.blogfacade.service.LinkService;
import com.qyy.blog.blogfacade.util.Result;
import com.qyy.blog.blogfacade.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class LinkController {
    @Reference
    private LinkService linkService;

    @GetMapping("/links")
    public String toLinkPage(HttpServletRequest request){
        request.setAttribute("path","links");
        return "admin/link";
    }

    @GetMapping("/links/list")
    @ResponseBody
    public Result linkListByPage(@RequestParam Map<String,Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        return ResultGenerator.genSuccessResult(linkService.getBlogLinkPage(params));
    }
    @PostMapping("/links/save")
    @ResponseBody
    public Result linkSave(BlogLink link){

        log.info("后台管理-新增友情链接-接口参数列表 {}",link.toString());
        long start = System.currentTimeMillis();
        log.info("后台管理-新增友情链接-调用接口开始");
        Boolean aBoolean = linkService.saveLink(link);
        long end = System.currentTimeMillis();
        log.info("后台管理-新增友情链接-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);
        if(aBoolean){
            return ResultGenerator.genSuccessResult("新增友情链接成功");
        }else {
            return ResultGenerator.genFailResult("新增友情链接失败");
        }
    }

    @GetMapping("/links/info/{id}")
    @ResponseBody
    public Result linkInfo(@PathVariable("id") Integer id){
        log.info("后台管理-修改友情链接-获取当前选中信息-接口参数列表 ID= {}",id);
        long start = System.currentTimeMillis();
        log.info("后台管理-修改友情链接-获取当前选中信息-调用接口开始");
        BlogLink blogLink = linkService.getLinkById(id);
        long end = System.currentTimeMillis();
        log.info("后台管理-修改友情链接-获取当前选中信息-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,blogLink.toString());
        if(blogLink!=null){
            return ResultGenerator.genSuccessResult(blogLink);
        }else {
            return ResultGenerator.genFailResult("为获取到信息");
        }
    }

    @PostMapping("/links/update")
    @ResponseBody
    public Result linkUpdate(BlogLink link){

        log.info("后台管理-修改友情链接-接口参数列表 {}",link.toString());
        long start = System.currentTimeMillis();
        log.info("后台管理-修改友情链接-调用接口开始");
        Boolean aBoolean = linkService.updateLink(link);
        long end = System.currentTimeMillis();
        log.info("后台管理-修改友情链接-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);
        if(aBoolean){
            return ResultGenerator.genSuccessResult("修改友情链接成功");
        }else {
            return ResultGenerator.genFailResult("修改友情链接失败");
        }
    }

    @PostMapping("/links/delete")
    @ResponseBody
    public Result tagDel(@RequestBody Integer[] ids){
        log.info("后台管理-删除友情链接-接口参数列表 {}",ids);
        long start = System.currentTimeMillis();
        log.info("后台管理-删除友情链接-调用接口开始");

        Boolean aBoolean = linkService.deleteBatch(ids);

        long end = System.currentTimeMillis();
        log.info("后台管理-删除友情链接-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return ResultGenerator.genSuccessResult("删除友情链接成功");
        }else {
            return ResultGenerator.genFailResult("删除友情链接失败");
        }
    }

}
