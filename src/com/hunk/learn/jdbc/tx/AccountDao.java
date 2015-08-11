package com.hunk.learn.jdbc.tx;

import com.hunk.learn.jdbc.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by hunk on 2015/8/11.
 */
public class AccountDao {
    // 全局参数
    private Connection con;
    private PreparedStatement pstmt;

    /**
     * 1.转账，没有使用事务
     */
    public void trans1(){
        String sql_zs = "update account set money=money-1000 where accountName='张三'";
        String sql_ls = "update account set money=money+1000 where accountName='李四'";

        try {
            con = DbUtil.getConnection();  // 默认开启的隐士事务
            con.setAutoCommit(true);

            /*** 第一次执行SQL ***/
            pstmt = con.prepareCall(sql_zs);
            pstmt.executeUpdate();

            /*** 第二次执行SQL ***/
            pstmt = con.prepareCall(sql_ls);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(con,pstmt);
        }

    }
    /**
     * 1.转账，没有使用事务
     */
    public void trans2(){
        String sql_zs = "update account set money=money-1000 where accountName='张三'";
        String sql_ls = "update account set money=money+1000 where accountName='李四'";

        try {
            con = DbUtil.getConnection();  // 默认开启的隐士事务
            con.setAutoCommit(false);

            /*** 第一次执行SQL ***/
            pstmt = con.prepareCall(sql_zs);
            pstmt.executeUpdate();

            /*** 第二次执行SQL ***/
            pstmt = con.prepareCall(sql_ls);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DbUtil.close(con,pstmt);
        }

    }
    /**
     * 1.转账，没有使用事务
     */
    public void trans(){
        String sql_zs = "update account set money=money-1000 where accountName='张三'";
        String sql_ls = "update account set money=money+1000 where accountName='李四'";

        String sql_zs2 = "update account set money=money-500 where accountName='张三'";
        String sql_ls2 = "update account set money=money+500 where accountName='李四'";

        try {
            con = DbUtil.getConnection();  // 默认开启的隐士事务
            con.setAutoCommit(false);

            /*** 第一次转账 ***/
            pstmt = con.prepareCall(sql_zs);
            pstmt.executeUpdate();
            pstmt = con.prepareCall(sql_ls);
            pstmt.executeUpdate();

            /*** 第二次转账 ***/
            pstmt = con.prepareCall(sql_zs2);
            pstmt.executeUpdate();
            pstmt = con.prepareCall(sql_ls2);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DbUtil.close(con,pstmt);
        }

    }
}
