package cn.hunk.learn.web.page.test;

import cn.hunk.learn.web.JdbcUtils;
import cn.hunk.learn.web.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by hunk on 2015/8/13.
 */
public class AddData {
    /**
     * 添加测试数据
     */
    @Test
    public void  save(){
        QueryRunner qr = JdbcUtils.getQueryRunner();
        try {
//            qr.update("insert into dept(deptName) values('技术部')");
            String sql = "insert into employee(empName,deptId) values(?,1)";
            for (int i = 1; i <60; i++) {
                qr.update(sql,"员工" +i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
