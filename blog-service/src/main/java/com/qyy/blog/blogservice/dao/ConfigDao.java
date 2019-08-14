package com.qyy.blog.blogservice.dao;

import com.qyy.blog.blogfacade.entity.BlogConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Mapper
public interface ConfigDao {

    List<BlogConfig> getAllConfigs();

}
