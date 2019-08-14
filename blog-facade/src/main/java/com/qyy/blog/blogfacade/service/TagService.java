package com.qyy.blog.blogfacade.service;


import com.qyy.blog.blogfacade.entity.BlogTagCount;
import com.qyy.blog.blogfacade.util.PageQueryUtil;
import com.qyy.blog.blogfacade.util.PageResult;

import java.util.List;
import java.util.Map;

public interface TagService {

    /**
     * 查询标签的分页数据
     *
     * @param params
     * @return
     */
    PageResult getBlogTagPage(Map<String,Object> params);

    int getTotalTags();

    Boolean saveTag(String tagName);

    Boolean deleteBatch(Integer[] ids);

    List<BlogTagCount> getBlogTagCountForIndex();
}
