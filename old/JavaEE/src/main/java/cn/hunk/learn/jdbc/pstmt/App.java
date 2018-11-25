package cn.hunk.learn.jdbc.pstmt;

import cn.hunk.learn.jdbc.DbUtil;
import org.junit.Test;

import java.sql.*;

/**
 * day18
 * Created by hunk on 2015/8/11.
 */
public class App {

    // 全局参数
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     * 没有防止sql注入的案例
     */
    @Test
    public void testLogin(){
        // 1.0 模拟用户的登录名，密码
        String userName = "tom";
//        String pwd = "8881";
        String pwd = " ' or 1=1 -- ";

        // SQL 语句
        String sql = "select * from admin where userName='" + userName + "' and pwd='" + pwd + "' ";
        System.out.println(sql);

        try {
            // 1.1 加载驱动，创建连接
            con = DbUtil.getConnection();
            // 1.2 创建Statement对象
            stmt = con.createStatement();
            // 1.3 执行查询
            rs = stmt.executeQuery(sql);
            // 业务判断
            while (rs.next()){
                System.out.println("登录陈功，编号：" + rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            DbUtil.close(con,stmt);
        }
    }
/**
     * 使用PreparedStatement防止sql注入的案例
     */
    @Test
    public void testLogin2(){
        // 1.0 模拟用户的登录名，密码
        String userName = "tom";
//        String pwd = "8881";
        String pwd = " ' or 1=1 -- ";

        // SQL 语句
        String sql = "select * from admin where userName=? and pwd=?";
        System.out.println(sql);

        try {
            // 1.1 加载驱动，创建连接
            con = DbUtil.getConnection();
            // 1.2 创建Statement对象
//            stmt = con.createStatement();
            pstmt = con.prepareStatement(sql);
            // 1.3 设置参数
            pstmt.setString(1,userName);
            pstmt.setString(2,pwd);
            // 1.4 执行查询
            rs = pstmt.executeQuery();
            // 业务判断
            while (rs.next()){
                System.out.println("登录陈功，编号：" + rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            DbUtil.close(con,pstmt);
        }
    }

}
