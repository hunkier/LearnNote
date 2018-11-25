package cn.hunk.learn.jdbc.jdbc;

import cn.hunk.learn.jdbc.DbUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用的dao，自己写的所有的dao都继承此类
 * 此类定义了2个通用的方法
 * 1.更新
 * 2.查询
 * Created by hunk on 2015/8/12.
 */
public class BaseDao {
    // 初始化参数
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     * 更新的通用方法
     * @param sql   更新的sql语句(update/insert/delete)
     * @param paramsValues  sql语句中占位符对应的值(如果没有占位符，传入null)
     */
    public void update(String sql, Object[] paramsValues){
        try {
            // 获取连接
            conn = DbUtil.getConnection();
            // 创建执行命令的stmt对象
            pstmt = conn.prepareStatement(sql);
            // 参数元数据：得到占位符参数的个数
            int count = pstmt.getParameterMetaData().getParameterCount();
            // 设置占位符参数的值
            if (null!=paramsValues && paramsValues.length>0){
                //循环给参数赋值
                for (int i = 0; i < count; i++) {
                    pstmt.setObject(i+1,paramsValues[i]);
                }
            }
            // 执行更新
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn,pstmt,rs);
        }
    }
    public <T> List<T> query(String sql, Object[] paramsValues, Class<T> clazz){
        try {
            // 返回的集合
            List<T> list = new ArrayList<T>();
            // 对象
            T t = null ;
            // 1.获取连接
            conn = DbUtil.getConnection();
            // 2.创建执行命令的stmt对象
            pstmt = conn.prepareStatement(sql);
            // 3.获取占位符参数的个数，并设置每个参数的值
            if (null!=paramsValues && paramsValues.length>0){
                for (int i = 0; i < paramsValues.length; i++) {
                    pstmt.setObject(i+1, paramsValues[i]);
                }
            }
            // 4.执行查询
            rs = pstmt.executeQuery();
            // 5.获取结果集元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 获取列的个数
            int columnCount = rsmd.getColumnCount();
            // 6.遍历rs
            while (rs.next()){
                // 要封装的对象
                t = clazz.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    // 7.获取每一列的列名称
                    String columnName = rsmd.getColumnName(i+1);
                    // 8.获取每一列的列名称，对应的值
                    Object value = rs.getObject(columnName);
                    // 封装:设置到t对象的属性中  【BeanUtils组件】
                    BeanUtils.copyProperty(t,columnName,value);
                }
                // 把封装完毕的对象，添加到list集合中
                list.add(t);
            }
            return  list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn,pstmt,rs);
        }
    }
}
