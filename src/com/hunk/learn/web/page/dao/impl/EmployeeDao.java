package com.hunk.learn.web.page.dao.impl;

import com.hunk.learn.web.page.JdbcUtils;
import com.hunk.learn.web.page.dao.IEmployeeDao;
import com.hunk.learn.web.page.entity.Employee;
import com.hunk.learn.web.page.utils.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * 2.数据访问层实现
 * Created by hunkier on 15/8/12.
 */
public class EmployeeDao implements IEmployeeDao {
    /**
     * 分页查询数据
     *
     * @param pb
     */
    @Override
    public void getAll(PageBean<Employee> pb) {

    }

    /**
     * 查询总记录数
     *
     * @return
     */
    @Override
    public int getTotalCount() {

        String sql = "";
        try {
            QueryRunner qr = JdbcUtils.getQueryRunner();
            Long count = qr.query(sql,new ScalarHandler<Long>());
            return count.intValue();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new  RuntimeException(e);
        }
    }
}
