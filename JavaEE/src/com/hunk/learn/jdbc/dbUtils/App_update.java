package com.hunk.learn.jdbc.dbUtils;

import com.hunk.learn.jdbc.DbUtil;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created by hunk on 2015/8/12.
 */
public class App_update {
    private Connection conn;

    /**
     * 更新
     * @throws Exception
     */
    @Test
    public void testUpdate()throws  Exception{
        String sql = "delete from admin where id=?";
        // 连接对象
        conn = DbUtil.getConnection();
        // 创建DbUtils核心工具类对象
        QueryRunner qr = new QueryRunner();
        qr.update(conn,sql,26);

        // 关闭
        DbUtils.close(conn);
    }

    /**
     * 批处理
     * @throws Exception
     */
    @Test
    public void testBatch()throws  Exception{
        String sql = "insert into admin (userName,pwd) values(?,?)";
        conn = DbUtil.getConnection();
        QueryRunner qr = new QueryRunner();
        // 批量插入
        qr.batch(conn, sql, new Object[][]{{"jack1","888"},{"jack2","999"}});
        // 关闭
        DbUtils.close(conn);
    }
}
