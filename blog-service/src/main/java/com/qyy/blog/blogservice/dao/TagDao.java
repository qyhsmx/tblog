package com.qyy.blog.blogservice.dao;

import com.qyy.blog.blogfacade.entity.BlogTag;

import java.util.List;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
public interface TagDao {

    int getTotalTags();

    List<BlogTag> findTagList(Map<String,Object> map);

    BlogTag selectByTagName(String tag);

    void batchInsertTags(List<BlogTag> insertTags);

    int saveTag(String tagName);

    int deleteBatch(Integer[] ids);
}
