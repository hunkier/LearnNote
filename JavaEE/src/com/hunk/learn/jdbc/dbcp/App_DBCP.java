package com.hunk.learn.jdbc.dbcp;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by hunk on 2015/8/12.
 */
public class App_DBCP {

    /**
     * 1.硬编码方式实现连接池
     * @throws Exception
     */
    @Test
    public void testDbcp()throws  Exception{
        // DBCP连接池核心累
        BasicDataSource dataSource = new BasicDataSource();
        // 连接池参数配置：初始化连接池、最大连接数  /  连接字符串、驱动、用户、密码
        dataSource.setUrl("jdbc:mysql://localhost:3306/day18"); // 数据库连接字符串
        dataSource.setDriverClassName("com.mysql.jdbc.Driver"); // 数据库驱动
        dataSource.setUsername("hunk");                         // 数据库连接用户
        dataSource.setPassword("123456");                       // 数据库连接密码
        dataSource.setInitialSize(3);                           // 初始化连接
        dataSource.setMaxActive(6);                             // 最大连接
        dataSource.setMaxIdle(3000);                            // 最大空闲时间

        // 获取连接
        Connection con = dataSource.getConnection();
        con.prepareStatement("delete from admin where id=3");
        con.close();
    }

    /**
     * 2.【推荐】配置方式实现连接池，便于维护
     * @throws Exception
     */
    @Test
    public void testProp()throws  Exception{
        // 加载prop配置文件
        Properties prop = new Properties();
        // 获取问价流
        InputStream inputStream = App_DBCP.class.getResourceAsStream("db.properties");
        // 加载属性配置文件
        prop.load(inputStream);
        // 根据prop配置，直接创建数据源对象
        DataSource dataSource = BasicDataSourceFactory.createDataSource(prop);


        // 获取连接
        Connection conn = dataSource.getConnection();
        conn.prepareStatement("delete  from admin where id=4").executeUpdate();
        // 关闭
        conn.close();



    }
}
