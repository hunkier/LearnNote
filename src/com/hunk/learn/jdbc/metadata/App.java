package com.hunk.learn.jdbc.metadata;

import com.hunk.learn.jdbc.DbUtil;
import org.junit.Test;

import java.sql.*;

/**
 * Created by hunk on 2015/8/12.
 */
public class App {
    /**
     * 1.数据库元数据
     * @throws Exception
     */
    @Test
    public void testDB() throws Exception{
        // 获取连接
        Connection conn = DbUtil.getConnection();
        // 获取数据库元数据
        DatabaseMetaData metaData = conn.getMetaData();

        System.out.println(metaData.getUserName());
        System.out.println(metaData.getURL());
        System.out.println(metaData.getDatabaseProductName());
    }

    /**
     * 2.参数元数据
     * @throws Exception
     */
    @Test
    public void testParams() throws Exception{
        // 获取连接
        Connection conn = DbUtil.getConnection();
        // SQL
        String sql = "select * from dept where deptid=? and deptName=?";
        // Object[] values = {"tom","8888"};

        PreparedStatement pstmt = conn.prepareStatement(sql);

        //参数元数据
        ParameterMetaData p_metaDate = pstmt.getParameterMetaData();
        // 获取参数的个数
        int count = p_metaDate.getParameterCount();

        System.out.println(count);
    }

    /**
     * 3.结果集元数据
     * @throws Exception
     */
    @Test
    public void testRs()throws Exception{
        String sql = "select * from dept ";

        // 获取连接
        Connection conn = DbUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        //得到结果集元数据（目标：通过结果集元数据，得到列的名称）
        ResultSetMetaData rs_metaData = rs.getMetaData();

        // 迭代每一行结果
        while (rs.next()){
            // 1.获取列的个数
            int count = rs_metaData.getColumnCount();
            // 2.遍历，获取每一列的列名称
            for (int i = 0; i < count; i++) {
                // 得到列的名称
                String columnName = rs_metaData.getColumnName(i+1);
                // 获取每一行的每一列的值
                Object columnValue = rs.getObject(columnName);
                System.out.print(columnName + " = " + columnValue + ", \t");
            }
            System.out.println();
        }
    }
}
