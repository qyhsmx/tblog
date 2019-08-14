package com.qyy.blog.blogservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qyy.blog.blogfacade.entity.BlogLink;
import com.qyy.blog.blogfacade.entity.BlogTag;
import com.qyy.blog.blogfacade.service.LinkService;
import com.qyy.blog.blogfacade.util.PageQueryUtil;
import com.qyy.blog.blogfacade.util.PageResult;
import com.qyy.blog.blogservice.dao.LinkDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;

    @Override
    public PageResult getBlogLinkPage(Map<String,Object> params) {

        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("start",(page-1)*limit);
        params.put("limit",limit);
        params.put("page",page);

        int totalLinks = linkDao.getTotalLinks();
        List<BlogLink> linkList = linkDao.findLinkList(params);

        return new PageResult(linkList,totalLinks,limit,page);
    }

    @Override
    public int getTotalLinks() {
        return linkDao.getTotalLinks();
    }

    @Override
    public Boolean saveLink(BlogLink link) {

        return linkDao.saveLink(link)>0;
    }

    @Override
    public BlogLink selectById(Integer id) {
        return null;
    }

    @Override
    public Boolean updateLink(BlogLink tempLink) {
        return linkDao.updateLink(tempLink)>0;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return linkDao.deleteBatch(ids)>0;
    }

    @Override
    public Map<Byte, List<BlogLink>> getLinksForLinkPage() {
        return null;
    }

    @Override
    public BlogLink getLinkById(Integer id) {
        return linkDao.getLinkById(id);
    }
}
