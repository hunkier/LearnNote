package cn.hunk.learn.web.emp.dao.impl;

import cn.hunk.learn.web.emp.entity.Admin;
import cn.hunk.learn.web.JdbcUtils;
import cn.hunk.learn.web.emp.dao.IAdminDao;
import cn.hunk.learn.web.emp.entity.Admin;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * Created by hunkier on 15/8/13.
 */
public class AdminDao implements IAdminDao {
    /**
     * 根据用户名密码查询
     *
     * @param admin
     * @return
     */
    @Override
    public Admin findByNameAndPwd(Admin admin) {
        String sql = "select * from admin where userName=? and pwd=?";
        try {
            return JdbcUtils.getQueryRunner().query(sql,new BeanHandler<Admin>(Admin.class),admin.getUserName(),admin.getPwd());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
