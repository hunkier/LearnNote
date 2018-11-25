package cn.hunk.learn.web.emp.service.impl;

import cn.hunk.learn.web.emp.dao.IAdminDao;
import cn.hunk.learn.web.emp.dao.impl.AdminDao;
import cn.hunk.learn.web.emp.entity.Admin;
import cn.hunk.learn.web.emp.service.IAdminService;
import cn.hunk.learn.web.emp.dao.IAdminDao;
import cn.hunk.learn.web.emp.dao.impl.AdminDao;
import cn.hunk.learn.web.emp.entity.Admin;
import cn.hunk.learn.web.emp.service.IAdminService;

/**
 * Created by hunkier on 15/8/13.
 */
public class AdminService implements IAdminService {
    IAdminDao adminDao = new AdminDao();

    /**
     * 根据用户名密码查询
     *
     * @param admin
     * @return
     */
    @Override
    public Admin findByNameAndPwd(Admin admin) {
        return adminDao.findByNameAndPwd(admin);
    }
}
