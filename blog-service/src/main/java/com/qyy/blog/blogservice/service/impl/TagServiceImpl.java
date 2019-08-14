package com.qyy.blog.blogservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qyy.blog.blogfacade.entity.BlogCategory;
import com.qyy.blog.blogfacade.entity.BlogTag;
import com.qyy.blog.blogfacade.entity.BlogTagCount;
import com.qyy.blog.blogfacade.service.TagService;
import com.qyy.blog.blogfacade.util.PageQueryUtil;
import com.qyy.blog.blogfacade.util.PageResult;
import com.qyy.blog.blogservice.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public PageResult getBlogTagPage(Map<String,Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("start",(page-1)*limit);
        params.put("limit",limit);
        params.put("page",page);

        int totalTags = tagDao.getTotalTags();
        List<BlogTag> tagList = tagDao.findTagList(params);

        return new PageResult(tagList,totalTags,limit,page);
    }

    @Override
    public int getTotalTags() {
        return tagDao.getTotalTags();
    }

    @Override
    public Boolean saveTag(String tagName) {

        return tagDao.saveTag(tagName)>0;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return tagDao.deleteBatch(ids)>0;
    }

    @Override
    public List<BlogTagCount> getBlogTagCountForIndex() {
        return null;
    }
}
