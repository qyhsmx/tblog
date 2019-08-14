package com.qyy.blog.blogweb.controller.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qyy.blog.blogfacade.service.TagService;
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
public class TagController {

    @Reference
    private TagService tagService;

    @GetMapping("/tags")
    public String toTagPage(HttpServletRequest request){
        request.setAttribute("path","tags");
        return "admin/tag";
    }

    @GetMapping("/tags/list")
    @ResponseBody
    public Result tagListByPage(@RequestParam Map<String,Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        return ResultGenerator.genSuccessResult(tagService.getBlogTagPage(params));
    }

    @PostMapping("/tags/save")
    @ResponseBody
    public Result tagSave(@RequestParam("tagName") String tagName){
        log.info("后台管理-新增标签-接口参数列表 {}",tagName);
        long start = System.currentTimeMillis();
        log.info("后台管理-新增标签-调用接口开始");

        Boolean aBoolean = tagService.saveTag(tagName);

        long end = System.currentTimeMillis();
        log.info("后台管理-新增标签-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return ResultGenerator.genSuccessResult("新增标签成功");
        }else {
            return ResultGenerator.genFailResult("新增标签失败");
        }
    }

    @PostMapping("/tags/delete")
    @ResponseBody
    public Result tagDel(@RequestBody Integer[] ids){
        log.info("后台管理-删除标签-接口参数列表 {}",ids);
        long start = System.currentTimeMillis();
        log.info("后台管理-删除标签-调用接口开始");

        Boolean aBoolean = tagService.deleteBatch(ids);

        long end = System.currentTimeMillis();
        log.info("后台管理-删除标签-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return ResultGenerator.genSuccessResult("删除标签成功");
        }else {
            return ResultGenerator.genFailResult("删除标签失败");
        }
    }
}
