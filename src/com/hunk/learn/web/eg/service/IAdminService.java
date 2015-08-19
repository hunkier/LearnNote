package com.hunk.learn.web.eg.service;

import com.hunk.learn.web.eg.entity.Admin;
import com.hunk.learn.web.eg.exception.UserExistException;

/**
 * 业务逻辑层接口设计
 * Created by hunk on 2015/8/11.
 */
public interface IAdminService {

    /**
     * 注册
     * @param admin
     * @throws UserExistException
     */
    public void register(Admin admin)throws UserExistException;

    /**
     * 登录
     * @param admin
     * @return
     */
    public Admin login(Admin admin);

}
