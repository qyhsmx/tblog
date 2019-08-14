package com.qyy.blog.blogservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qyy.blog.blogfacade.entity.Blog;
import com.qyy.blog.blogfacade.entity.BlogCategory;
import com.qyy.blog.blogfacade.entity.BlogTag;
import com.qyy.blog.blogfacade.entity.BlogTagRelation;
import com.qyy.blog.blogfacade.service.BlogService;
import com.qyy.blog.blogfacade.service.CategoryService;
import com.qyy.blog.blogfacade.util.PageQueryUtil;
import com.qyy.blog.blogfacade.util.PageResult;
import com.qyy.blog.blogfacade.vo.BlogDetailVO;
import com.qyy.blog.blogfacade.vo.SimpleBlogListVO;
import com.qyy.blog.blogservice.dao.BlogDao;
import com.qyy.blog.blogservice.dao.CategoryDao;
import com.qyy.blog.blogservice.dao.TagDao;
import com.qyy.blog.blogservice.dao.TagRelationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogDao blogDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TagRelationDao relationDao;

    @Override
    public String saveBlog(Blog blog) {

        //select categoryName by categoryID
        BlogCategory category = categoryDao.selectCategoryById(blog.getBlogCategoryId());
        if(category==null){
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        }else {
            blog.setBlogCategoryName(category.getCategoryName());
            category.setCategoryRank(category.getCategoryRank()+1);
        }

        //deal with tags
        String[] arrayTags = blog.getBlogTags().split(",");

        if(arrayTags.length>6){
            return "标签过多";
        }
        if(blogDao.saveBlog(blog)>0) {

            List<BlogTag> insertTags = new ArrayList<>();
            List<BlogTag> allTags = new ArrayList<>();

            for (String tag : arrayTags) {
                BlogTag selectTag = tagDao.selectByTagName(tag);
                if (selectTag == null) {
                    BlogTag blogTag = new BlogTag();
                    blogTag.setTagName(tag);
                    insertTags.add(blogTag);
                }else {
                    allTags.add(selectTag);
                }
            }

            //batch insert new tags
            if(!CollectionUtils.isEmpty(insertTags)){
                tagDao.batchInsertTags(insertTags);
            }
            //upate category
            categoryDao.updateCategoryRank(category);

            //update tag relations
            List<BlogTagRelation> relations = new ArrayList<>();

            allTags.addAll(insertTags);
            for(BlogTag t : allTags){
                BlogTagRelation relation = new BlogTagRelation();
                relation.setBlogId(blog.getBlogId());
                relation.setTagId(t.getTagId());
                relations.add(relation);
            }
            if(relationDao.batchInsert(relations)>0){
                return "success";
            }
        }
        return "保存失败";
    }

    @Override
    public PageResult getBlogsPage(Map<String, Object> params) {


        /*
            param 参数
            page: "page",
            rows: "limit",
            order: "order",
        * */

        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("start",(page-1)*limit);
        params.put("limit",limit);
        params.put("page",page);

        int totalBlogCount = blogDao.getTotalBlogCount(params);

        List<Blog> blogList = blogDao.findBlogList(params);

        return new PageResult(blogList,totalBlogCount,limit,page);

    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return blogDao.deleteBatch(ids)>0;

    }

    /**
     * 获取总个数
     * @return
     */
    @Override
    public int getTotalBlogs() {
        return blogDao.getTotalBlogCount(null);
    }

    @Override
    public Blog getBlogById(Long blogId) {
        Blog blog = blogDao.getBlogById(blogId);
        return blog;
    }

    @Override
    public String updateBlog(Blog blog) {

        Blog blogForUpdate = blogDao.getBlogById(blog.getBlogId());

        if(blogForUpdate==null){
            return "该文章不存在";
        }
        blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogSubUrl(blog.getBlogSubUrl());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        blogForUpdate.setEnableComment(blog.getEnableComment());

        BlogCategory blogCategory = categoryDao.selectCategoryById(blog.getBlogCategoryId());
        if(blogCategory==null){
            blogCategory.setCategoryId(0);
            blogCategory.setCategoryName("默认分类");
        }else{
            blogForUpdate.setBlogCategoryId(blogCategory.getCategoryId());
            blogForUpdate.setBlogCategoryName(blogCategory.getCategoryName());
            blogCategory.setCategoryRank(blogCategory.getCategoryRank()+1);
        }

        String[] tags = blog.getBlogTags().split(",");
        if(tags.length>6){
            return "标签限制数量为6";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());

        //新增的tag对象
        List<BlogTag> tagListForInsert = new ArrayList<>();
        //所有的tag对象，用于建立关系数据
        List<BlogTag> allTagsList = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            BlogTag tag = tagDao.selectByTagName(tags[i]);
            if (tag == null) {
                //不存在就新增
                BlogTag tempTag = new BlogTag();

                tagListForInsert.add(tempTag);tempTag.setTagName(tags[i]);
            } else {
                allTagsList.add(tag);
            }
        }

        //新增标签数据不为空->新增标签数据
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            tagDao.batchInsertTags(tagListForInsert);
        }
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        //新增关系数据
        allTagsList.addAll(tagListForInsert);
        for (BlogTag tag : allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(tag.getTagId());
            blogTagRelations.add(blogTagRelation);
        }

        categoryDao.updateCategoryRank(blogCategory);
        relationDao.deleteByBlogId(blog.getBlogId());
        relationDao.batchInsert(blogTagRelations);

        if (blogDao.updateByPrimaryKeySelective(blogForUpdate) > 0) {
            return "success";
        }
        return "修改失败";
    }

    @Override
    public PageResult getBlogsForIndexPage(int page) {
        return null;
    }

    @Override
    public List<SimpleBlogListVO> getBlogListForIndexPage(int type) {
        return null;
    }

    @Override
    public BlogDetailVO getBlogDetail(Long blogId) {
        return null;
    }

    @Override
    public PageResult getBlogsPageByTag(String tagName, int page) {
        return null;
    }

    @Override
    public PageResult getBlogsPageByCategory(String categoryId, int page) {
        return null;
    }

    @Override
    public PageResult getBlogsPageBySearch(String keyword, int page) {
        return null;
    }

    @Override
    public BlogDetailVO getBlogDetailBySubUrl(String subUrl) {
        return null;
    }
}
