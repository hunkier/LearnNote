package com.hunk.learn.web.emp.dao;

import com.hunk.learn.web.emp.entity.Admin;

/**
 * 2.管理员数据访问层接口设计
 * Created by hunk on 2015/8/13.
 */
public interface IAdminDao {
    /**
     * 根据用户名密码查询
     * @param admin
     * @return
     */
    public Admin findByNameAndPwd(Admin admin);
}
