package cn.hunk.learn.jdbc;



import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * jdbc工具类
 * Created by hunk on 2015/8/10.
 */
public class DbUtil {
    private static String url = null;
    private static String user = null;
    private static String password = null;
    private static String driverClass = null;
    private static String temp = "%s?user=%s&password=%s&useUnicode=true&characterEncoding=utf-8";

    static {
        Properties prop = new Properties();
//        String path = DbUtil.class.getResource("/").getPath();
//        System.out.println( DbUtil.class.getResource("/").getPath());
        try {
            // 获取配置文件路径
             String path = DbUtil.class.getResource("/com/hunk/learn/jdbc/db.properties").getPath();
            // 加载配置
            prop.load(new FileInputStream(path));
            // 读取配置文件中属性
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
            driverClass = prop.getProperty("driverClass");
            url = String.format(temp,url,user,password);
            // 加载数据库驱动
            Class.forName(driverClass);
        } catch (Exception e) {
            e.printStackTrace();
            new RuntimeException(e);
        }
//        System.out.println(path);
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
                throw new RuntimeException(e);
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
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 关闭数据库连接
     * @param conn
     * @param stmt
     */
    public static void close(Connection conn,Statement stmt){
        close(conn,stmt,null);
    }


    public static void main(String[] args) {
        test();
    }

    public static  void test(){
        System.out.println(url);
        System.out.println(user);
        System.out.println(password);
        System.out.println(driverClass);
    }

}
