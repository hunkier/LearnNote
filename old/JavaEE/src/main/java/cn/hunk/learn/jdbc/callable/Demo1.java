package cn.hunk.learn.jdbc.callable;

import cn.hunk.learn.jdbc.DbUtil;
import org.junit.Test;

import java.sql.*;

/**
 * 使用CablleStatement调用存储过程
 * Created by hunk on 2015/8/10.
 */
public class Demo1 {

    /**
     * 创建存储过程
     */
    @Test
    public void testCreateProceduce(){

        Connection conn = null ;
        Statement stmt = null ;

        try {
            // 1.获取连接
            conn = DbUtil.getConnection();
            // 2.sql语句
            String sql ="delimiter $" +
                    " create procedure pro_findById(in sid int) " +
                    "begin " +
                    "select * from student where id=sid" +
                    " end $";
            // 3.获取Statement
            stmt = conn.createStatement();
            // 4.执行sql语句
            int count = stmt.executeUpdate(sql);
            System.out.println(String.format("影响了%d条数据",count));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // 5.关闭连接
            DbUtil.close(conn,stmt);
        }

    }

    /**
     * 调用存储过程
     */
    @Test
    public void testCallProcedure(){
        Connection conn = null ;
        CallableStatement stmt = null ;
        ResultSet rs = null ;

        try {
            // 1.获取连接
            conn = DbUtil.getConnection();
            // 2.准备sql
            String sql = "call pro_findById(?)";
            // 3.预编译
            stmt = conn.prepareCall(sql);
            // 4.设置参数
            stmt.setInt(1,6);
            // 5.发送参数，执行sql语句
            rs = stmt.executeQuery();
            // 6.遍历结果
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                System.out.println(id+","+name+","+gender);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        } finally {
            // 7.关闭连接
            DbUtil.close(conn,stmt,rs);
        }
    }

    /**
     * 执行带有输出参数的存储过程
     */
    @Test
    public void test2(){
        Connection conn = null ;
        CallableStatement stmt = null ;
        ResultSet rs = null ;

        try {
            // 1.获取连接
            conn = DbUtil.getConnection();
            // 2.准备sql
            String sql = "call pro_findById2(?,?)";
            // 3.预编译
            stmt = conn.prepareCall(sql);
            // 4.设置参数
            stmt.setInt(1,6);
            /**
             * 参数一：参数位置
             * 参数二：存储过程中输出的jdbc类型
             */
            stmt.registerOutParameter(2, Types.VARCHAR);
            // 5.发送参数，执行sql语句
            rs = stmt.executeQuery();// 结果不是返回到结果集中，而是返回到输出参数中
            // 6.得到输出参数的值
            /**
             * 索引值：预编译sql中的输出参数的位置
             */
            String result = stmt.getString(2);// getXX方法专门用于获取存储过程中的输出参数
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        } finally {
            // 7.关闭连接
            DbUtil.close(conn,stmt,rs);
        }
    }
}
