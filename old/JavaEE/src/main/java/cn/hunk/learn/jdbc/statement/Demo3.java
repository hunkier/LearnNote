package cn.hunk.learn.jdbc.statement;

import cn.hunk.learn.jdbc.DbUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 使用Statement执行DQL语句（查询操作）
 * Created by hunk on 2015/8/10.
 */
public class Demo3 {

    /**
     * 查询所有数据
     */
    @Test
    public void test1(){
        Connection conn = null ;
        Statement stmt = null;
        ResultSet rs = null ;

        try {
            // 获取连接
            conn = DbUtil.getConnection();
            // 创建Statement
            stmt = conn.createStatement();
            // 准备sql
            String sql = "select * from  student ";
            // 执行sql
            rs = stmt.executeQuery(sql);
            // 遍历结果
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                System.out.println(id + ", " + name + ", " + gender);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        } finally {
            DbUtil.close(conn,stmt,rs);
        }
    }
}
