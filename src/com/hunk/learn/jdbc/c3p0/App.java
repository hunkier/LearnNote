package com.hunk.learn.jdbc.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by hunkier on 15/8/12.
 */
public class App {
    /**
     * 硬编码方式，使用C3P0连接池管理连接
     * @throws Exception
     */
    @Test
    public void testCode() throws Exception{
        // 创建连接池核心工具类
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // 设置连接参数：url、驱动、用户密码、初始连接数、最大连接数
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/day18");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setUser("hunk");
        dataSource.setPassword("123456");
        dataSource.setInitialPoolSize(3);
        dataSource.setMaxPoolSize(6);
        dataSource.setMaxIdleTime(1000);

        // ----> 从连接池中，获取连接对象
        Connection conn = dataSource.getConnection();

        // 执行更新
        conn.prepareStatement("delete from admin where id=7").executeUpdate();
        // 关闭
        conn.close();
    }

    /**
     * 2.XML配置方式，使用C3P0连接池管理
     * @throws Exception
     */
    @Test
    public void testXML() throws Exception{
       // 创建c3p0的配置文件【c3p0-config.xml】
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        PreparedStatement pstmt = null ;

        String sql = "insert into employee (empName,deptId) values(?,?)";
        // 获取连接
        Connection conn = dataSource.getConnection();
        for (int i = 0; i < 11; i++) {
            // 执行更新
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"Rose"+i);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();
        }
        pstmt.close();
        conn.close();
    }
}
