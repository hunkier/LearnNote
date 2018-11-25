package cn.hunk.learn.jdbc.dbUtils;

import cn.hunk.learn.jdbc.DbUtil;
import cn.hunk.learn.jdbc.jdbc.*;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 查询
 * Created by hunk on 2015/8/12.
 */
public class App_query {
    private Connection con;

    /**
     * 一、查询，自定义结果集封装数据
     * @throws Exception
     */
    @Test
    public void testQuery()throws  Exception{
        String sql = "select * from admin where id=?";
        // 获取连接
        con = DbUtil.getConnection();
        //创建DbUtils核心工具类对象
        QueryRunner qr = new QueryRunner();
        // 查询
        Admin admin = qr.query(con, sql, new ResultSetHandler<Admin>() {
            // 如何封装一个Admin对象
            @Override
            public Admin handle(ResultSet resultSet) throws SQLException {
                if (resultSet.next()){
                    Admin admin = new Admin();
                    admin.setId(resultSet.getInt("id"));
                    admin.setUserName(resultSet.getString("userName"));
                    admin.setPwd(resultSet.getString("pwd"));
                    return admin;
                }
                return null;
            }
        },29);
        System.out.println(admin);
        DbUtils.close(con);
    }

    /**
     * 二、查询，使用组件提供的结果集对象封装数据
     * 1）BeanHandler：查询返回单个对象
     * @throws Exception
     */
    @Test
    public void testQueryOne() throws  Exception{
        String sql = "select * from admin where id=?";
        // 获取连接
        con = DbUtil.getConnection();
        // 创建DbUtils核心工具类对象
        QueryRunner qr = new QueryRunner();
        // 查询返回单个对象
        Admin admin = qr.query(con,sql,new BeanHandler<Admin>(Admin.class),29);
        System.out.println(admin);
        DbUtils.close(con);
    }

    /**
     * 2)BeanListHandler：查询返回list集合，集合元素是指定对象
     * @throws Exception
     */
    @Test
    public void testQueryMany() throws  Exception{
        String sql = "select * from admin";
        con = DbUtil.getConnection();
        QueryRunner qr = new QueryRunner();
        // 查询全部数据
        List<Admin> list = qr.query(con,sql,new BeanListHandler<Admin>(Admin.class));
        System.out.println(list);
        DbUtils.close(con);
    }

    /**
     * 3) ArrayHandler,查询返回结果记录的第一行，封装对象数组，即返回：Object[]
     * 4) ArrayListHandler，把查询的每一行都封装为对象数组，在添加到list集合中
     * 5) ScalarHandler， 查询返回结果记录的第一行第一列(在聚合函数统计的时候用)
     * 6) MapHandler, 查询返回结果的第一条记录封装为map
     * @throws Exception
     */
    @Test
    public void testArray()throws  Exception{
        String sql = "select * from admin";
        con = DbUtil.getConnection();
        QueryRunner qr = new QueryRunner();
        // 查询
        // Object[] obj = qr.query(con,sql,new ArrayHandler());
        // List<Object[]> list = qr.query(con,sql,new ArrayListHandler());
        // Integer num = qr.query(con,sql,new ScalarHandler<Integer>());
        Map<String,Object> map = qr.query(con,sql,new MapHandler());
        System.out.println(map);

        DbUtils.close(con);
    }

}
