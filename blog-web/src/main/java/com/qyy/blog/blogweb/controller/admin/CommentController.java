package com.qyy.blog.blogweb.controller.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qyy.blog.blogfacade.service.CommentService;
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
public class CommentController {

    @Reference
    private CommentService commentService;

    @GetMapping("/comments")
    public String toComment(HttpServletRequest request){

        request.setAttribute("path","comments");
        return "admin/comment";
    }

    @GetMapping("/comments/list")
    @ResponseBody
    public Result commentsList(@RequestParam Map<String, Object> params){

        if(StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))){
            return ResultGenerator.genFailResult("请求参数有误！");
        }
        return ResultGenerator.genSuccessResult(commentService.getCommentsPage(params));
    }

    @PostMapping("/comments/delete")
    @ResponseBody
    public Result commentDel(@RequestBody Integer[] ids){

        log.info("后台管理-删除评论-接口参数列表 {}",ids);
        long start = System.currentTimeMillis();
        log.info("后台管理-删除评论-调用删除接口开始");

        Boolean aBoolean = commentService.deleteBatch(ids);

        long end = System.currentTimeMillis();
        log.info("后台管理-删除评论-调用删除接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return ResultGenerator.genSuccessResult("删除评论成功");
        }else {
            return ResultGenerator.genFailResult("删除评论失败");
        }
    }

    @PostMapping("/comments/checkDone")
    @ResponseBody
    public Result commentCheck(@RequestBody Integer[] ids){

        log.info("后台管理-审核评论-接口参数列表 {}",ids);
        long start = System.currentTimeMillis();
        log.info("后台管理-审核评论-调用接口开始");

        Boolean aBoolean = commentService.checkDone(ids);

        long end = System.currentTimeMillis();
        log.info("后台管理-审核评论-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return ResultGenerator.genSuccessResult("审核评论成功");
        }else {
            return ResultGenerator.genFailResult("审核评论失败");
        }
    }
    @PostMapping("/comments/reply")
    @ResponseBody
    public Result commentReply(@RequestParam Map<String,Object> params){

        log.info("后台管理-回复评论-接口参数列表 {}",params);
        long start = System.currentTimeMillis();
        log.info("后台管理-回复评论-调用接口开始");
        Long commentId = Long.parseLong((String) params.get("commentId"));
        String replyBody =  (String) params.get("replyBody");
        if(commentId==0 || StringUtils.isEmpty(replyBody)){
            return ResultGenerator.genFailResult("参数有误，请重新输入");
        }

        Boolean aBoolean = commentService.reply(commentId, replyBody);


        long end = System.currentTimeMillis();
        log.info("后台管理-回复评论-调用接口结束-耗时：{} ms ,返回结果是 {}",end-start,aBoolean);

        if(aBoolean){
            return ResultGenerator.genSuccessResult("评论回复成功");
        }else {
            return ResultGenerator.genFailResult("评论回复失败");
        }
    }


}
