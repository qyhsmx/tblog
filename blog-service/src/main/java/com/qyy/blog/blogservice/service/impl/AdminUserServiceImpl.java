package com.qyy.blog.blogservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qyy.blog.blogfacade.entity.AdminUser;
import com.qyy.blog.blogfacade.service.AdminUserService;
import com.qyy.blog.blogfacade.util.MD5Util;
import com.qyy.blog.blogservice.dao.AdminUserDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author qyhsmx@outlook.com
 * @date ${date}
 */
//@Service
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    AdminUserDao adminUserDao;

    @Override
    public AdminUser login(String userName, String password) {

        String md5EncodePwd = MD5Util.MD5Encode(password,"UTF-8");


        return adminUserDao.loginByNameAndPwd(userName,md5EncodePwd);
    }

    @Override
    public AdminUser getUserDetailById(Integer loginUserId) {

        return adminUserDao.getUserDetailById(loginUserId);
    }

    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        String md5EncodeNew = MD5Util.MD5Encode(newPassword, "UTF-8");
        String md5EncodeOld = MD5Util.MD5Encode(originalPassword, "UTF-8");
        AdminUser detail = adminUserDao.getUserDetailById(loginUserId);
        if(!md5EncodeOld.equals(detail.getLoginPassword())){
            return false;
        }else {
            return adminUserDao.updatePassword(loginUserId,md5EncodeNew)>0;
        }
    }

    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        return adminUserDao.updateName(loginUserId, loginUserName, nickName)>0;
    }
}
