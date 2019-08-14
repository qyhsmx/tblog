package com.qyy.blog.blogservice.dao;

import com.qyy.blog.blogfacade.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
@Mapper
public interface AdminUserDao {

    AdminUser loginByNameAndPwd(String username,String password);

    AdminUser getUserDetailById(Integer loginUserId);

    int updateName(@Param("id") Integer loginUserId, @Param("name")String loginUserName, @Param("nick")String nickName);

    int updatePassword(@Param("id") Integer loginUserId, @Param("pwd") String md5EncodeNew);
}
