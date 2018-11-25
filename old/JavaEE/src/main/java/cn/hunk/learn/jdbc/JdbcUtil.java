package cn.hunk.learn.jdbc;

import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * jdbc工具类
 * Created by hunkier on 15/8/8.
 */
public class JdbcUtil {
    private static String url = null ;
    private static String user = null ;
    private static String password = null ;
    private static String driverClass  = null ;
    private static String temp = "%s?user=%s&password=%s&useUnicode=true&characterEncoding=utf-8";

    /**
     * 静态代码块（只执行一次）
     */
    static{
        try {
            //读取db.properties文件
            Properties props = new Properties();
            /**
             * 使用类路径的读取方式
             *  / : 斜杠表示classpath的跟目录
             *      在java项目下，classpath的根目录
             *      在web项目下，classpath的根目录从WEB-INF/classes目录开始的
             */
            InputStream in = JdbcUtil.class.getResource("/com/hunk/learn/jdbc/db.properties").openStream();

            // 加载文件
            props.load(in);
            //读取信息
            url = props.getProperty("url");
            user = props.getProperty("user");
            password = props.getProperty("password");
            driverClass = props.getProperty("driverClass");
            Class.forName(driverClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 抽取获取连接对象的方法
     * @return
     */
    public static Connection getConnection(){

        try {
            //  Connection conn = DriverManager.getConnection(url,user,password);
            url = String.format(temp,url,user,password);
            Connection conn = DriverManager.getConnection(url);
            return  conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void close(Connection conn, Statement stmt){
        if (null!=stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if (null!=conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs){
        if (null!=rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        close(conn,stmt);
    }

    @Test
    public void test(){
        getConnection();
        System.out.println(url);
        System.out.println(user);
        System.out.println(password);
        System.out.println(driverClass);
    }
}
