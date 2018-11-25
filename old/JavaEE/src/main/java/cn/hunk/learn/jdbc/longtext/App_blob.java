package cn.hunk.learn.jdbc.longtext;

import cn.hunk.learn.jdbc.DbUtil;
import cn.hunk.learn.web.contact.util.JdbcUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;


/**
 * Created by hunk on 2015/8/11.
 */
public class App_blob {
    // 全局参数
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     * 二进制数据类型  （写longblob）
     */
    @Test
    public void testSaveText(){

        String sql = "insert into test(img) values(?)";

        try {
            // 连接
            con = DbUtil.getConnection();
            // PreparedStatement
            pstmt = con.prepareStatement(sql);
            // 获取图片流
            InputStream in = App_text.class.getResourceAsStream("7.jpg");
            pstmt.setBinaryStream(1,in);
            // 执行保存图片
            pstmt.execute();

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(con,pstmt,rs);
        }
    }
    /**
     * 二进制数据类型  （写longblob）
     */
    @Test
    public void testGetAsText(){

        String sql = "select img from test where img is not null ";

        try {
            // 连接
            con = DbUtil.getConnection();
            // PreparedStatement
            pstmt = con.prepareStatement(sql);
            // 执行保存图片
            rs = pstmt.executeQuery();

            while (rs.next()){
                // 获取图片流
                InputStream in = rs.getBinaryStream("img");
                // 图片输出流
                File file = new File(App_text.class.getResource("/").getPath()+"/1.jpg");
                System.out.println(file.getAbsolutePath());
                FileOutputStream out = new FileOutputStream(file);
                int len = -1;
                byte b[] = new byte[1024];
                while((len = in.read(b)) != -1){
                    out.write(b,0,len);
                }
                // 关闭
                out.close();
                in.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(con,pstmt,rs);
        }
    }
}
