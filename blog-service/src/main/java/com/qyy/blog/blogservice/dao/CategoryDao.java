package com.qyy.blog.blogservice.dao;

import com.qyy.blog.blogfacade.entity.BlogCategory;
import com.qyy.blog.blogfacade.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Mapper
public interface CategoryDao {

    int getTotalCategories(Map<String,Object> map);

    List<BlogCategory> getAllCategories(PageQueryUtil pageUtil);

    List<BlogCategory> getCategoriesByPage(Map<String,Object> map);

    BlogCategory selectCategoryById(Integer blogCategoryId);

    void updateCategoryRank(BlogCategory category);

    int saveCategory(@Param("name") String categoryName,@Param("icon") String categoryIcon);

    BlogCategory selectCategoryByName(String categoryName);

    int updateCategory(@Param("id") Integer categoryId, @Param("name") String categoryName,@Param("icon") String categoryIcon);

    int deleteBatch(Integer[] ids);
}
