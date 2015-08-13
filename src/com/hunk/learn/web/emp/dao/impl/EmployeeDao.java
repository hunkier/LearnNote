package com.hunk.learn.web.emp.dao.impl;

import com.hunk.learn.web.JdbcUtils;
import com.hunk.learn.web.emp.dao.IEmployeeDao;
import com.hunk.learn.web.emp.entity.Employee;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by hunkier on 15/8/13.
 */
public class EmployeeDao implements IEmployeeDao {
    /**
     * 查询所有的员工
     *
     * @return
     */
    @Override
    public List<Employee> getAll() {
        try {
            return JdbcUtils.getQueryRunner().query("select * from employee",new BeanListHandler<Employee>(Employee.class));
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
}
