package cn.hunk.learn.jdbc.batch;

import cn.hunk.learn.jdbc.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 封装所有的与数据库操作
 * database：day18
 * Created by hunk on 2015/8/11.
 */
public class AdminDao {
    // 全局参数
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     * 批量保存管理员
     * @param list
     */
    public void save(List<Admin> list){
        String sql = "insert into admin(userName, pwd) values(?,?)";

        try {
            // 获取连接
            conn = DbUtil.getConnection();
            // 创建PreparedStatement
            pstmt = conn.prepareStatement(sql);// 预编译sql语句
            for (int i = 0; i < list.size(); i++) {
                Admin admin = list.get(i);
                // 设置参数
                pstmt.setString(1,admin.getUserName());
                pstmt.setString(2,admin.getPwd());
                // 添加批处理
                pstmt.addBatch();   // 不需要传入sql

                // 测试： 每5条执行一次批处理
                if (i%5 == 0){
                    // 批量执行
                    pstmt.executeBatch();
                    // 清空批处理
                    pstmt.clearBatch();
                }
            }

            // 批量执行
            pstmt.executeBatch();
            // 清空批处理
            pstmt.clearBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn,pstmt,rs);
        }
    }
}
