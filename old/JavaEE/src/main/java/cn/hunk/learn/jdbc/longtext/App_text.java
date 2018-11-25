package cn.hunk.learn.jdbc.longtext;

import cn.hunk.learn.jdbc.DbUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

/**
 * Created by hunk on 2015/8/11.
 */
public class App_text {
    // 全局参数
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     * 保存大文本数据类型  （写longtext）
     */
    @Test
    public void testSaveText(){
        String sql = "insert into test(context) values(?)";

        try {
            // 连接
            con = DbUtil.getConnection();
            // PreparedStatement
            pstmt = con.prepareStatement(sql);
            // 设置参数
            // 先获取文件路径
            String path = App_text.class.getResource("tips.txt").getPath();
            FileReader reader = new FileReader(new File(path));
            pstmt.setCharacterStream(1,reader);
            // 执行sql
            pstmt.executeUpdate();

            // 关闭
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(con,pstmt,rs);
        }
    }

    /**
     * 2.读取大文本数据类型  （读longtext）
     */
    @Test
    public void testGetAsText(){
        String sql = "select * from test where context is not null";

        try {
            // 连接
            con = DbUtil.getConnection();
            // PreparedStatement
            pstmt = con.prepareStatement(sql);
            // 读取
            rs = pstmt.executeQuery();

            while (rs.next()){
                // 获取长文本数据，方式1：
                // Reader r = rs.getCharacterStream("context");

                // 获取长文本数据，方式2
                System.out.println(rs.getString("context"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(con,pstmt,rs);
        }
    }
}
