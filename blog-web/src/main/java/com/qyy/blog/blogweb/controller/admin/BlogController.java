package com.qyy.blog.blogweb.controller.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qyy.blog.blogfacade.entity.Blog;
import com.qyy.blog.blogfacade.entity.BlogCategory;
import com.qyy.blog.blogfacade.service.BlogService;
import com.qyy.blog.blogfacade.service.CategoryService;
import com.qyy.blog.blogfacade.util.Result;
import com.qyy.blog.blogfacade.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class BlogController {

    @Reference
    private CategoryService categoryService;

    @Reference
    private BlogService blogService;

    @GetMapping("/blogs/edit")
    public String editBlog(HttpServletRequest request){

        request.setAttribute("path","edit");

        List<BlogCategory> allCategories = categoryService.getAllCategories();

        request.setAttribute("categories",allCategories);

        return "admin/edit";
    }

    @GetMapping("/blogs/edit/{id}")
    public String editBlog(HttpServletRequest request,@PathVariable("id") long blogId){
        log.info("后台管理-修改文章-接口参数列表 blogId = {}",blogId);
        long start = System.currentTimeMillis();
        log.info("后台管理-修改文章-获取当前文章信息开始");

        Blog blog = blogService.getBlogById(blogId);
        if(null==blog){
            return "error/error_404";
        }
        List<BlogCategory> allCategories = categoryService.getAllCategories();

        long end = System.currentTimeMillis();
        log.info("后台管理-修改文章-获取当前文章信息开始-耗时：{} ms ,返回结果是 blog {}，allCategories {}",end-start,blog.toString(),allCategories);


        request.setAttribute("path","edit");
        request.setAttribute("blog",blog);
        request.setAttribute("categories",allCategories);
        return "admin/edit";
    }

    @GetMapping("/blogs")
    public String blogs(HttpServletRequest request){
        request.setAttribute("path","blogs");
        return "admin/blog";
    }

    @GetMapping("/blogs/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        //PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(blogService.getBlogsPage(params));
    }

    @PostMapping("/blogs/save")
    @ResponseBody
    public Result blogSave(Blog blog){

        log.info("后台管理-新增文章-接口参数列表 {}",blog.toString());
        long start = System.currentTimeMillis();
        log.info("后台管理-新增文章-调用新增接口开始");

        String saveBlog = blogService.saveBlog(blog);

        long end = System.currentTimeMillis();
        log.info("后台管理-新增文章-调用新增接口结束-耗时：{} ms ,返回结果是 {}",end-start,saveBlog);


        if("success".equals(saveBlog)){
            return ResultGenerator.genSuccessResult("新增文章成功");
        }else {
            return ResultGenerator.genFailResult(saveBlog);
        }
    }

    @PostMapping("/blogs/update")
    @ResponseBody
    public Result blogUpdate(Blog blog){
        log.info("后台管理-修改文章-接口参数列表 {}",blog.toString());
        long start = System.currentTimeMillis();
        log.info("后台管理-修改文章-调用修改接口开始");

        String updateBlog = blogService.updateBlog(blog);

        long end = System.currentTimeMillis();
        log.info("后台管理-新增文章-调用新增接口结束-耗时：{} ms ,返回结果是 {}",end-start,updateBlog);

        if("success".equals(updateBlog)){
            return ResultGenerator.genSuccessResult("修改文章成功");
        }else {
            return ResultGenerator.genFailResult(updateBlog);
        }
    }

    @PostMapping("/blogs/delete")
    @ResponseBody
    public Result blogDel(@RequestBody Integer[] ids){

        log.info("后台管理-删除文章-接口参数列表 {}",ids);
        long start = System.currentTimeMillis();
        log.info("后台管理-删除文章-调用删除接口开始");
        Boolean aBoolean = blogService.deleteBatch(ids);
        long end = System.currentTimeMillis();
        log.info("后台管理-新增文章-调用新增接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return ResultGenerator.genSuccessResult("删除文章成功");
        }else {
            return ResultGenerator.genFailResult("删除文章失败");
        }
    }
}
