package com.qyy.blog.blogservice.dao;

import com.qyy.blog.blogfacade.entity.BlogLink;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Mapper
public interface LinkDao  {
    int getTotalLinks();
    List<BlogLink> findLinkList(Map<String,Object> map);

    int saveLink(BlogLink link);

    int updateLink(BlogLink tempLink);

    int deleteBatch(Integer[] ids);

    BlogLink getLinkById(Integer id);
}
