package com.qyy.blog.blogservice.dao;

import com.qyy.blog.blogfacade.entity.BlogTagRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Mapper
public interface TagRelationDao {
    int batchInsert(@Param("relationList")List<BlogTagRelation> blogTagRelations);

    void deleteByBlogId(Long blogId);
}
