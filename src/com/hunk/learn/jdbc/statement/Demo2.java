package com.hunk.learn.jdbc.statement;

import com.hunk.learn.jdbc.DbUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

/**
 * 使用Statement执行DML语句
 * Created by hunk on 2015/8/7.
 */
public class Demo2 {
    // 连接数据库的URL
    private String url = "jdbc:mysql://localhost:3306/day17";
    // jdbc协议：数据库子协议：主机：端口/连接的数据库
    private String user = "root";
    private String password = "123456";




    /**
     * 插入数据
     */
    @Test
    public void testInsert(){
        Connection conn = null ;
        Statement stmt = null ;
        try {
            // 1.获取连接
            conn = DbUtil.getConnection();

            // 2.创建Statement
            stmt = conn.createStatement();

            // 3.sql语句
            String sql = "insert into student(name,gender) values('张三','男'),('李四','女')";

            // 4.执行sql语句
            int count = stmt.executeUpdate(sql);
            System.out.println(String.format("影响了%s条数据",count));

        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            // 5.关闭连接
            DbUtil.close(conn,stmt);
        }
    }



    /**
     * 修改数据
     */
    @Test
    public void testUpdate(){
        Connection conn = null ;
        Statement stmt = null ;
        // 模拟输入
        int id =3 ;
        try {
            // 1.获取连接
            conn = DbUtil.getConnection();

            // 2.创建Statement
            stmt = conn.createStatement();

            // 3.sql语句
            String sql = "update student set name='赵六' where id="+id;

            // 4.执行sql语句
            int count = stmt.executeUpdate(sql);
            System.out.println(String.format("影响了%s条数据",count));

        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            // 5.关闭连接
            DbUtil.close(conn,stmt);
        }
    }


    /**
     * 删除数据
     */
    @Test
    public void testDelete(){
        Connection conn = null ;
        Statement stmt = null ;
        // 模拟输入
        int id =3 ;
        try {
            // 1.获取连接
            conn = DbUtil.getConnection();

            // 2.创建Statement
            stmt = conn.createStatement();

            // 3.sql语句
            String sql = "delete from  student  where id="+id;

            // 4.执行sql语句
            int count = stmt.executeUpdate(sql);
            System.out.println(String.format("影响了%s条数据",count));

        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException(e);
        }finally {
            // 5.关闭连接
            DbUtil.close(conn,stmt);
        }
    }


}
