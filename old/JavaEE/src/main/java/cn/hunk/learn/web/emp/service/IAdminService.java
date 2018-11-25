package cn.hunk.learn.web.emp.service;

import cn.hunk.learn.web.emp.entity.Admin;

/**
 * 3.管理员业务逻辑层
 * Created by hunk on 2015/8/13.
 */
public interface IAdminService {
    /**
     * 根据用户名密码查询
     * @param admin
     * @return
     */
    public Admin findByNameAndPwd(Admin admin);
}
