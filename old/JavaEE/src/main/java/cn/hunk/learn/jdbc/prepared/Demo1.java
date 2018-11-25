package cn.hunk.learn.jdbc.prepared;

import cn.hunk.learn.jdbc.DbUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * PreparedStatement执行sql语句
 * Created by hunk on 2015/8/10.
 */
public class Demo1 {

    /**
     * 增加
     */
    @Test
    public void testInsert(){
        Connection conn = null ;
        PreparedStatement stmt = null;

        try {
            // 1.获取连接
            conn = DbUtil.getConnection();
            // 2.准备预编译的sql
            String sql ="insert into student(name,gender)values(?,?)";
            // 3.执行预编译的sql语句
            stmt = conn.prepareStatement(sql);

            // 4.设置参数
            /**
             * 参数一：参数位置 从1开始
             */
            stmt.setString(1,"李四");
            stmt.setString(2,"男");

            // 5.发送参数，执行sql
            int count = stmt.executeUpdate();
            System.out.println(String.format("影响了%d条数据",count));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn,stmt);
        }
    }
    /**
     * 更新
     */
    @Test
    public void testUpdate(){
        Connection conn = null ;
        PreparedStatement stmt = null;

        try {
            // 1.获取连接
            conn = DbUtil.getConnection();
            // 2.准备预编译的sql语句
            String sql = "update student set name=? where id=?";
            // 3.执行预编译的sql语句（检查语法）
            stmt = conn.prepareStatement(sql);
            // 4.设置参数
            stmt.setString(1,"王五");
            stmt.setInt(2,9);
            // 5.发送参数，执行sql
            int count = stmt.executeUpdate();
            System.out.println(String.format("影响了%d条数据",count));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn,stmt);
        }
    }
    /**
     * 删除
     */
    @Test
    public void testDelete(){
        Connection conn = null ;
        PreparedStatement stmt = null;

        try {
            // 1.获取连接
            conn = DbUtil.getConnection();
            // 2.准备预编译的sql
            String sql = "delete from student where id=?";
            // 3.执行预编译的sql语句（检查语法）
            stmt = conn.prepareStatement(sql);
            // 4.设置参数
            /**
             * 参数一： 参数位置  从1开始
             */
            stmt.setInt(1,10);
            // 5.发送参数，执行sql
            int count = stmt.executeUpdate();
            System.out.println(String.format("影响了%d条数据",count));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn,stmt);
        }
    }
    /**
     * 查询
     */
    @Test
    public void testQuery(){
        Connection conn = null ;
        PreparedStatement stmt = null;
        ResultSet rs = null ;
        try {
            // 1.获取连接
            conn = DbUtil.getConnection();
            // 2.准备预编译的sql
            String sql = "select * from student";
            // 3.预编译
            stmt = conn.prepareStatement(sql);
            // 4.执行sql
            rs = stmt.executeQuery();
            // 5.遍历rs
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                System.out.println(id + ", " + name + ", " + gender);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn,stmt);
        }
    }
}
