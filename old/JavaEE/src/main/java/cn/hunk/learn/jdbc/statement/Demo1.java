package cn.hunk.learn.jdbc.statement;

import cn.hunk.learn.jdbc.DbUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 使用Statement对象执行静态sql语句
 * Created by hunk on 2015/8/7.
 */
public class Demo1 {
    // 连接数据库的URL
    private String url = "jdbc:mysql://localhost:3306/day17";
    // jdbc协议：数据库子协议：主机：端口/连接的数据库
    private String user = "hunk";
    private String password = "123456";

    /**
     * 执行DDL语句（创建表）
     */
    @Test
    public void test1(){
        Statement stmt = null ;
        Connection conn = null ;
        try {

            /*
            // 1.驱动注册
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获取连接对象
//            conn = DriverManager.getConnection(url,user,password);

            conn = DriverManager.getConnection(url,user,password);
            */
            conn = DbUtil.getConnection();
            // 3.创建Statement
            stmt = conn.createStatement();
            // 4.准备sql
            String sql ="CREATE TABLE student\n" +
                    "(\n" +
                    "    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                    "    name VARCHAR(20),\n" +
                    "    gender VARCHAR(2)\n" +
                    ");\n";

            // 5.发送sql语句，执行sql语句，得到返回结果
            int count = stmt.executeUpdate(sql);

            // 6.输出
            System.out.println("影响了" + count + "行！");

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
           //  DbUtil.close(conn,stmt);
            // 7.关闭连接（顺序：后打开的先关闭）
            if (null!=stmt){
                try {
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                    throw  new RuntimeException(e);
                }
            }
            if (null!=conn){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
