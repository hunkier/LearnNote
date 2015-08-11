package com.hunk.learn.web.eg.util;

import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * jdbc工具类
 * Created by hunk on 2015/8/10.
 */
public class JdbcUtil {
    private static String url = null ;
    private static String user = null ;
    private static String password = null ;
    private static String driverClass = null ;
    private static String temp = null ;
    private static String URL = null ;

    /**
     * 静态代码块中 （只加载一次）
     */
    static{

        try {
            // 读取db.properties文件
            Properties props = new Properties();
            /**
             *  .代表java命令运行的目录
             *  在java项目下，. java命令的运行目录从项目的根目录开始
             *  在web项目下， . java命令的运行目录从tomcat/bin目录开始
             *  所以不能用.
             */
            //FileInputStream in = new FileInputStream("./src/db.properties");

            /**
             * 使用类路径的读取方式
             *   /  ：斜杠表示classpath的根目录
             *      在java项目下，classpath的根目录从bin目录开始
             *      在web项目下，classpath的根目录从WEB-INF/classes目录开始
             */
            InputStream in = JdbcUtil.class.getResourceAsStream("/com/hunk/learn/web/eg/db.properties");
            // 加载文件
            props.load(in);
            // 读取信息
            url = props.getProperty("url");
            user = props.getProperty("user");
            password = props.getProperty("password");
            driverClass = props.getProperty("driverClass");
            temp = props.getProperty("temp");

            URL = String.format(temp,url,user,password);
            // 加载驱动
            Class.forName(driverClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Test
    public void test(){
        System.out.println(url);
        System.out.println(user);
        System.out.println(password);
        System.out.println(driverClass);
        System.out.println(temp);
        System.out.println(URL);
        System.out.println(getConnection());
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){

        try {
//            System.out.println(JdbcUtil.class.getResource("/").getPath());
//            System.out.println(url);
//            System.out.println(URL);
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    /**
     * 关闭数据库连接
     * @param conn
     * @param stmt
     * @param rs
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        if (null!=rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw  new RuntimeException(e);
            }
        }
        if (null!=stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw  new RuntimeException(e);
            }
        }
        if (null!=conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw  new RuntimeException(e);
            }
        }
    }

    /**
     * 关闭数据库连接
     * @param conn
     * @param stmt
     */
    public static void close(Connection conn, Statement stmt){
        close(conn,stmt,null);
    }
}
