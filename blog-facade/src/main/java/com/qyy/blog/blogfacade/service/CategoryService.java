package com.qyy.blog.blogfacade.service;


import com.qyy.blog.blogfacade.entity.BlogCategory;
import com.qyy.blog.blogfacade.util.PageQueryUtil;
import com.qyy.blog.blogfacade.util.PageResult;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    /**
     * 查询分类的分页数据
     *
     * @param params
     * @return
     */
    PageResult getBlogCategoryPage( Map<String,Object> params);

    int getTotalCategories();

    /**
     * 添加分类数据
     *
     * @param categoryName
     * @param categoryIcon
     * @return
     */
    Boolean saveCategory(String categoryName, String categoryIcon);

    Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon);

    Boolean deleteBatch(Integer[] ids);

    List<BlogCategory> getAllCategories();
}
