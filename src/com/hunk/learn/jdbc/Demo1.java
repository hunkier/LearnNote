package com.hunk.learn.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * jdbc连接数据库
 * Created by hunk on 2015/8/7.
 */
public class Demo1 {
    // 连接数据库的URL
    private String url = "jdbc:mysql://localhost:3306/day17";
                        // jdbc协议：数据库子协议：主机：端口/连接的数据库
    private String user = "root";
    private String password = "123456";

    /**
     * 第一种方法
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        // 1.创建驱动程序类对象
        Driver driver = new com.mysql.jdbc.Driver();// 新版本

        // 设置用户名和密码
        Properties properties = new Properties();
        properties.setProperty("user",user);
        properties.setProperty("password",password);

        // 2.连接数据库，返回连接对象
        Connection conn = driver.connect(url,properties);

        System.out.println(conn);
    }
    /**
     * 使用驱动管理器类连接数据库（注册了两侧，没必要）
     * @throws Exception
     */
    @Test
    public void test2() throws Exception{
        Driver driver = new com.mysql.jdbc.Driver();

        // 1.注册驱动程序（可以注册多个驱动程序）
        DriverManager.registerDriver(driver);

        // 2.连接数据库，返回连接对象
        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);
    }
    /**
     * （推荐使用这种方式连接数据库）
     * 推荐使用加载驱动程序类 来 注册驱动程序
     * @throws Exception
     */
    @Test
    public void test3() throws Exception{
       // 通过得到字节码对象的方式加载静态代码块，从而注册驱动程序
        Class.forName("com.mysql.jdbc.Driver");

        // 2.连接到具体的数据库
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }
}
