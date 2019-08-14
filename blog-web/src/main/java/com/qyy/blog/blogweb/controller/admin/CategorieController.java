package com.qyy.blog.blogweb.controller.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qyy.blog.blogfacade.service.CategoryService;
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
public class CategorieController {

    @Reference
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String toCategory(HttpServletRequest request){
        request.setAttribute("path","categories");
        return "admin/category";
    }

    @GetMapping("/categories/list")
    @ResponseBody
    public Result categoryList(@RequestParam Map<String,Object> params){

        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        //PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(categoryService.getBlogCategoryPage(params));

    }

    @PostMapping("/categories/save")
    @ResponseBody
    public Result categorySave(@RequestParam Map<String,Object> params){
        log.info("后台管理-新增分类-接口参数列表 {}",params);
        long start = System.currentTimeMillis();
        log.info("后台管理-新增分类-调用接口开始");

        String categoryName = params.get("categoryName").toString();
        String categoryIcon = params.get("categoryIcon").toString();

        Boolean aBoolean = categoryService.saveCategory(categoryName, categoryIcon);

        long end = System.currentTimeMillis();
        log.info("后台管理-新增分类-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return ResultGenerator.genSuccessResult("新增分类成功");
        }else {
            return ResultGenerator.genFailResult("新增分类失败");
        }
    }

    @PostMapping("/categories/update")
    @ResponseBody
    public Result categoryUpdate(@RequestParam Map<String,Object> params){
        log.info("后台管理-修改分类-接口参数列表 {}",params);
        long start = System.currentTimeMillis();
        log.info("后台管理-修改分类-调用接口开始");
        Integer categoryId = Integer.parseInt(params.get("categoryId").toString());
        String categoryName = params.get("categoryName").toString();
        String categoryIcon = params.get("categoryIcon").toString();

        Boolean aBoolean = categoryService.updateCategory(categoryId, categoryName, categoryIcon);

        long end = System.currentTimeMillis();
        log.info("后台管理-修改分类-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return ResultGenerator.genSuccessResult("修改分类成功");
        }else {
            return ResultGenerator.genFailResult("修改分类失败");
        }
    }
    @PostMapping("/categories/delete")
    @ResponseBody
    public Result categoryDel(@RequestBody Integer[] ids){
        log.info("后台管理-删除分类-接口参数列表 {}",ids);
        long start = System.currentTimeMillis();
        log.info("后台管理-删除分类-调用接口开始");

        Boolean aBoolean = categoryService.deleteBatch(ids);

        long end = System.currentTimeMillis();
        log.info("后台管理-删除分类-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return ResultGenerator.genSuccessResult("删除分类成功");
        }else {
            return ResultGenerator.genFailResult("删除分类失败");
        }
    }


}
