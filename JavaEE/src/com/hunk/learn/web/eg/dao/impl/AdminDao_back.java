package com.hunk.learn.web.eg.dao.impl;

import com.hunk.learn.web.eg.dao.IAdminDao;
import com.hunk.learn.web.eg.entity.Admin;
import com.hunk.learn.web.eg.util.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 2.数据访问层接口的实现类
 * （引入DbUtils组件简化jdbc操作）
 * Created by hunk on 2015/8/11.
 */
public class AdminDao_back implements IAdminDao{

    private Connection con;
    private QueryRunner qr = new QueryRunner();
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     * 保存
     *
     * @param admin
     */
    @Override
    public void save(Admin admin) {
        String sql = "insert into admin(userName, pwd) values(?,?)";

        try {
            con = JdbcUtil.getConnection();
            qr.update(sql, admin.getUserName(),admin.getPwd());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(con,pstmt,rs);
        }
    }

    /**
     * 根据用户名密码查询
     *
     * @param admin
     * @return
     */
    @Override
    public Admin findByNameAndPwd(Admin admin) {
        String sql = "select * from admin where userName=? and pwd=?";
        Admin ad = null ;

        try {
            con = JdbcUtil.getConnection();
            ad = qr.query(con,sql,new BeanHandler<Admin>(Admin.class),admin.getUserName(),admin.getPwd());
            return  ad;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(con,pstmt,rs);
        }
    }

    /**
     * 检查用户名是否存在
     *
     * @param name 要检查的用户名
     * @return true 表示用户名已经存在；否则用户名不存在
     */
    @Override
    public boolean userExists(String name) {
        String sql = "select * from admin where userName=?";
        Admin ad = null ;

        try {
            con = JdbcUtil.getConnection();
            Integer in = qr.query(con,sql,new ScalarHandler<Integer>(), name);
            if (null!=in){
                return  true;
            }else{
                return  false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(con,pstmt,rs);
        }
    }
}
