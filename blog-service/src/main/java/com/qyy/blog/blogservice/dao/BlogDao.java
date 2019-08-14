package com.qyy.blog.blogservice.dao;

import com.qyy.blog.blogfacade.entity.Blog;
import com.qyy.blog.blogfacade.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Mapper
public interface BlogDao  {

    int getTotalBlogCount(Map<String, Object> pageUtil);
    List<Blog> findBlogList(Map<String, Object> pageUtil);
    int saveBlog(Blog blog);
    Blog getBlogById(Long blogId);
    int updateByPrimaryKeySelective(Blog blogForUpdate);

    int deleteBatch(Integer[] ids);
}
