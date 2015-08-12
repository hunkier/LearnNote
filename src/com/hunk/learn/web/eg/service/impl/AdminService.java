package com.hunk.learn.web.eg.service.impl;

import com.hunk.learn.web.eg.dao.IAdminDao;
import com.hunk.learn.web.eg.dao.impl.AdminDao;
import com.hunk.learn.web.eg.entity.Admin;
import com.hunk.learn.web.eg.exception.UserExistException;
import com.hunk.learn.web.eg.service.IAdminService;

/**
 * 3.业务逻辑层实现
 * Created by hunk on 2015/8/11.
 */
public class AdminService implements IAdminService{
    // 调用的dao
    private IAdminDao dao = new AdminDao();
    /**
     * 注册
     *
     * @param admin
     * @throws UserExistException
     */
    @Override
    public void register(Admin admin) throws UserExistException {

        try {
            // 1.先根据用户名查询用户是否存在
            boolean flag = dao.userExists(admin.getUserName());

            // 2.如果用户存在，不允许注册
            if (flag){
                // 不允许注册，给调用者提示
                throw new UserExistException("用户名已经存在，注册失败！");
            }

            // 3.用户不存在，可以注册
            dao.save(admin);
        } catch (UserExistException e) {
            e.printStackTrace();
            throw  new UserExistException(e.getMessage());
        }
    }

    /**
     * 登录
     *
     * @param admin
     * @return
     */
    @Override
    public Admin login(Admin admin) {
        try {
            return dao.findByNameAndPwd(admin);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
