package com.qyy.blog.blogservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qyy.blog.blogfacade.entity.Blog;
import com.qyy.blog.blogfacade.entity.BlogComment;
import com.qyy.blog.blogfacade.service.CommentService;
import com.qyy.blog.blogfacade.util.PageQueryUtil;
import com.qyy.blog.blogfacade.util.PageResult;
import com.qyy.blog.blogservice.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Boolean addComment(BlogComment blogComment) {
        return null;
    }

    @Override
    public PageResult getCommentsPage(Map<String, Object> params) {

        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("start",(page-1)*limit);
        params.put("limit",limit);
        params.put("page",page);

        int totalTotalCount = commentDao.getTotalComments(params);

        List<BlogComment> commentList = commentDao.findCommentList(params);


        return new PageResult(commentList,totalTotalCount,limit,page);
    }

    @Override
    public int getTotalComments() {
        return commentDao.getTotalComments(null);
    }

    @Override
    public Boolean checkDone(Integer[] ids) {
        return commentDao.checkDone(ids)>0;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {

        return commentDao.deleteBatch(ids)>0;
    }

    @Override
    public Boolean reply(Long commentId, String replyBody) {
        return commentDao.reply(commentId,replyBody)>0;
    }

    @Override
    public PageResult getCommentPageByBlogIdAndPageNum(Long blogId, int page) {
        return null;
    }
}
