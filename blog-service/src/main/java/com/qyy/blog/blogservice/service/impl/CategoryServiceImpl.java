package com.qyy.blog.blogservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qyy.blog.blogfacade.entity.Blog;
import com.qyy.blog.blogfacade.entity.BlogCategory;
import com.qyy.blog.blogfacade.service.CategoryService;
import com.qyy.blog.blogfacade.util.PageQueryUtil;
import com.qyy.blog.blogfacade.util.PageResult;
import com.qyy.blog.blogservice.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageResult getBlogCategoryPage(Map<String,Object> params) {

        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("start",(page-1)*limit);
        params.put("limit",limit);
        params.put("page",page);

        int totalCategoryCount = categoryDao.getTotalCategories(params);

        List<BlogCategory> categoryList = categoryDao.getCategoriesByPage(params);

        return new PageResult(categoryList,totalCategoryCount,limit,page);

    }

    @Override
    public int getTotalCategories() {
        return categoryDao.getTotalCategories(null);
    }

    @Override
    public Boolean saveCategory(String categoryName, String categoryIcon) {

        BlogCategory category = categoryDao.selectCategoryByName(categoryName);
        if(category!=null){
            return false;
        }
        return categoryDao.saveCategory(categoryName,categoryIcon)>0;
    }

    @Override
    public Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon) {
        return categoryDao.updateCategory(categoryId,categoryName,categoryIcon)>0;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {

        return categoryDao.deleteBatch(ids)>0;
    }

    @Override
    public List<BlogCategory> getAllCategories() {
        return categoryDao.getAllCategories(null);
    }
}
