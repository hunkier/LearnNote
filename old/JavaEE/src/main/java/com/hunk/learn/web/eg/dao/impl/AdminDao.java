package com.hunk.learn.web.eg.dao.impl;

import com.hunk.learn.web.eg.dao.IAdminDao;
import com.hunk.learn.web.eg.entity.Admin;
import com.hunk.learn.web.eg.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 2.数据访问层接口的实现类
 * Created by hunk on 2015/8/11.
 */
public class AdminDao implements IAdminDao{

    private Connection con;
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
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,admin.getUserName());
            pstmt.setString(2,admin.getPwd());
            // 执行
            pstmt.executeUpdate();

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
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,admin.getUserName());
            pstmt.setString(2,admin.getPwd());
            // 执行
            rs = pstmt.executeQuery();
            if (rs.next()){
                ad = new Admin();
                ad.setId(rs.getInt("id"));
                ad.setUserName(rs.getString("userName"));
                ad.setPwd(rs.getString("pwd"));

            }
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
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,name);
            // 执行
            rs = pstmt.executeQuery();
            if (rs.next()){
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
