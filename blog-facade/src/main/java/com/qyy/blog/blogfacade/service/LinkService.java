package com.qyy.blog.blogfacade.service;


import com.qyy.blog.blogfacade.entity.BlogLink;
import com.qyy.blog.blogfacade.util.PageQueryUtil;
import com.qyy.blog.blogfacade.util.PageResult;

import java.util.List;
import java.util.Map;

public interface LinkService {
    /**
     * 查询友链的分页数据
     *
     * @param params
     * @return
     */
    PageResult getBlogLinkPage(Map<String,Object> params);

    int getTotalLinks();

    Boolean saveLink(BlogLink link);

    BlogLink selectById(Integer id);

    Boolean updateLink(BlogLink tempLink);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回友链页面所需的所有数据
     *
     * @return
     */
    Map<Byte, List<BlogLink>> getLinksForLinkPage();

    BlogLink getLinkById(Integer id);
}
