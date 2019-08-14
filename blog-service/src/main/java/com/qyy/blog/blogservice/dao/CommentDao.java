package com.qyy.blog.blogservice.dao;

import com.qyy.blog.blogfacade.entity.BlogComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Mapper
public interface CommentDao {

    int getTotalComments(Map<String,Object> map);

    List<BlogComment> findCommentList(Map<String,Object> map);

    int deleteBatch(Integer[] ids);

    int checkDone(Integer[] ids);

    int reply(@Param("id") Long commentId, @Param("body") String replyBody);
}
